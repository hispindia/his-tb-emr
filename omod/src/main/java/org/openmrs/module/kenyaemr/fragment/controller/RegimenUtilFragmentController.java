/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.fragment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata._EncounterType;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
import org.openmrs.module.kenyaemr.regimen.Regimen;
import org.openmrs.module.kenyaemr.regimen.RegimenChange;
import org.openmrs.module.kenyaemr.regimen.RegimenChangeHistory;
import org.openmrs.module.kenyaemr.regimen.RegimenComponent;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.regimen.RegimenOrder;
import org.openmrs.module.kenyaemr.regimen.RegimenValidator;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.form.ValidatingCommandObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.MethodParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Various actions for regimen related functions
 */
public class RegimenUtilFragmentController {

	protected static final Log log = LogFactory.getLog(RegimenUtilFragmentController.class);

	/**
	 * Changes the patient's current regimen
	 * @param command the command object
	 * @param ui the UI utils
	 * @return the patient's current regimen
	 */
	public void changeRegimen(@MethodParam("newRegimenChangeCommandObject") @BindParams RegimenChangeCommandObject command, UiUtils ui,
			@RequestParam(value = "durgList", required = false) String[] durgList,
			HttpServletRequest request){
		ui.validate(command, command, null);
		Encounter encounter=command.apply(request);
		saveExtraRowForArv(durgList,request,command.getPatient(),encounter);
	}

	/**
	 * Undoes the last regimen change for the given patient
	 * @param patient the patient
	 * @return the patient's current regimen
	 */
	public void undoLastChange(@RequestParam("patient") Patient patient, HttpSession session, @RequestParam("category") String category, @SpringBean RegimenManager regimenManager, @SpringBean KenyaUiUtils kenyaUi) {
		Concept masterSet = regimenManager.getMasterSetConcept(category);
		RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, masterSet);
		history.undoLastChange();

		kenyaUi.notifySuccess(session, "Removed last regimen change");
	}

	/**
	 * Helper method to create a new form object
	 * @return the form object
	 */
	public RegimenChangeCommandObject newRegimenChangeCommandObject(@SpringBean RegimenManager regimenManager) {
		return new RegimenChangeCommandObject(regimenManager);
	}

	/**
	 * Change types
	 */
	public enum RegimenChangeType {
		START,
		CHANGE,
		//STOP,
		CONTINUE,
		RESTART
	}

	/**
	 * Command object for regimen changes
	 */
	public class RegimenChangeCommandObject extends ValidatingCommandObject {

		private RegimenManager regimenManager;

		private Patient patient;

		private String category;

		private RegimenChangeType changeType;
		
		private Date changeDate;

		private Concept changeReason;
		
		private String changeReasonNonCoded;

		private Regimen regimen;

		public RegimenChangeCommandObject(RegimenManager regimenManager) {
			this.regimenManager = regimenManager;
		}

		/**
		 * @see org.springframework.validation.Validator#validate(java.lang.Object,org.springframework.validation.Errors)
		 */
		@Override
		public void validate(Object target, Errors errors) {
			require(errors, "patient");
			require(errors, "category");
			require(errors, "changeType");
			require(errors, "changeDate");

			// Reason is only required for stopping or changing
			if (changeType == RegimenChangeType.CHANGE) {
				require(errors, "changeReason");

				if (changeReason != null) {
					Concept otherNonCoded = Dictionary.getConcept(Dictionary.OTHER_NON_CODED);

					if (changeReason.equals(otherNonCoded)) {
						require(errors, "changeReasonNonCoded");
					}
				}
			}

			if (category != null && changeDate != null) {
				// Get patient regimen history
				Concept masterSet = regimenManager.getMasterSetConcept(category);
				RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, masterSet);
				RegimenChange lastChange = history.getLastChange();
				boolean onRegimen = lastChange != null && lastChange.getStarted() != null;

				// Can't start if already started
				if ((changeType == RegimenChangeType.START || changeType == RegimenChangeType.RESTART) && onRegimen) {
					errors.reject("Can't start regimen for patient who is already on a regimen");
				}

				// Changes must be in order
				if (lastChange != null && OpenmrsUtil.compare(changeDate, lastChange.getDate()) <= 0) {
					errors.rejectValue("changeDate", "Change date must be after all other changes");
				}

				// Don't allow future dates
				if (OpenmrsUtil.compare(changeDate, new Date()) > 0) {
					errors.rejectValue("changeDate", "Change date can't be in the future");
				}
			}

			// Validate the regimen
			if (changeType != RegimenChangeType.CONTINUE) {
				try {
					errors.pushNestedPath("regimen");
					ValidationUtils.invokeValidator(new RegimenValidator(), regimen, errors);
				} finally {
					errors.popNestedPath();
				}
			}
		}
		
		/**
		 * Applies this regimen change
		 */
		public Encounter apply(HttpServletRequest request) {
			Concept masterSet = regimenManager.getMasterSetConcept(category);
			RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, masterSet);
			RegimenChange lastChange = history.getLastChange();
			RegimenOrder baseline = lastChange != null ? lastChange.getStarted() : null;
			Encounter encounter=null;
			KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
			if (baseline == null) {
				encounter=createEncounterForBaseLine(patient);
				Integer count=1;
				for (RegimenComponent component : regimen.getComponents()) {
					String drugConceptId=request.getParameter("drug"+count);
					Concept drugConcept=null;
					if(drugConceptId!=null){
				    drugConcept=Context.getConceptService().getConceptByUuid(drugConceptId.substring(2));
					}
					DrugOrder o = component.toDrugOrder(patient,changeDate,encounter);
					Order order=Context.getOrderService().saveOrder(o);
				
					DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
					drugOrderProcessed.setDrugOrder(Context.getOrderService().getDrugOrder(order.getOrderId()));
					drugOrderProcessed.setPatient(patient);
					drugOrderProcessed.setCreatedDate(new Date());
					drugOrderProcessed.setProcessedStatus(false);
					if(o.getConcept().equals(drugConcept)){
						Integer duration=Integer.parseInt(request.getParameter("duration"+count));
						drugOrderProcessed.setDurationPreProcess(duration);	
					}
					String drugRegimen=request.getParameter("selectedOption1");
					drugOrderProcessed.setDrugRegimen(drugRegimen);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);
					count++;
				}
			}
			else {
				List<DrugOrder> noChanges = new ArrayList<DrugOrder>();
				List<DrugOrder> toChangeDose = new ArrayList<DrugOrder>();
				List<DrugOrder> toStart = new ArrayList<DrugOrder>();

				if (regimen != null) {
					for (RegimenComponent component : regimen.getComponents()) {
						changeRegimenHelper(baseline, component, noChanges, toChangeDose, toStart);
					}
				}

				OrderService os = Context.getOrderService();
				
				if (changeType == RegimenChangeType.CHANGE) {
				List<DrugOrder> toStop = new ArrayList<DrugOrder>(baseline.getDrugOrders());
				toStop.removeAll(noChanges);
				for (DrugOrder o : toStop) {
					DrugOrderProcessed drugOrderProcess=kenyaEmrService.getDrugOrderProcessed(o);
					if(drugOrderProcess!=null){
					drugOrderProcess.setDiscontinuedDate(changeDate);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcess);
					}
					
					o.setDiscontinued(true);
					o.setDiscontinuedDate(changeDate);
					o.setDiscontinuedBy(Context.getAuthenticatedUser());
					o.setDiscontinuedReason(changeReason);
					o.setDiscontinuedReasonNonCoded(changeReasonNonCoded);
					os.saveOrder(o);
				}
				
				for (DrugOrder o : noChanges) {
					DrugOrderProcessed drugOrderProcess=kenyaEmrService.getDrugOrderProcessed(o);
					if(drugOrderProcess!=null){
					drugOrderProcess.setDiscontinuedDate(changeDate);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcess);
					}
					
					Concept concept=o.getConcept();
					String drug1=request.getParameter("drug1");
					String drug2=request.getParameter("drug2");
					String drug3=request.getParameter("drug3");
					String drug4=request.getParameter("drug4");
					Concept drug1Concept=Context.getConceptService().getConceptByUuid(drug1.substring(2));
					Concept drug2Concept=Context.getConceptService().getConceptByUuid(drug2.substring(2));
					Concept drug3Concept=Context.getConceptService().getConceptByUuid(drug3.substring(2));
					Concept drug4Concept=new Concept();
					if(StringUtils.isNotBlank(drug4)){
					drug4Concept=Context.getConceptService().getConceptByUuid(drug4.substring(2));
					}
					if(concept.equals(drug1Concept)){
					DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
					drugOrderProcessed.setDrugOrder(o);
					drugOrderProcessed.setPatient(patient);
					drugOrderProcessed.setCreatedDate(new Date());
					drugOrderProcessed.setProcessedStatus(false);
					Integer duration=Integer.parseInt(request.getParameter("duration1"));
					drugOrderProcessed.setDurationPreProcess(duration);
					String drugRegimen=request.getParameter("selectedOption1");
					drugOrderProcessed.setDrugRegimen(drugRegimen);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);
					}
					else if(concept.equals(drug2Concept)){
					DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
					drugOrderProcessed.setDrugOrder(o);
					drugOrderProcessed.setPatient(patient);
					drugOrderProcessed.setCreatedDate(new Date());
					drugOrderProcessed.setProcessedStatus(false);
					Integer duration=Integer.parseInt(request.getParameter("duration2"));
					drugOrderProcessed.setDurationPreProcess(duration);
					String drugRegimen=request.getParameter("selectedOption1");
					drugOrderProcessed.setDrugRegimen(drugRegimen);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);	
					}
					else if(concept.equals(drug3Concept)){
					DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
					drugOrderProcessed.setDrugOrder(o);
					drugOrderProcessed.setPatient(patient);
					drugOrderProcessed.setCreatedDate(new Date());
					drugOrderProcessed.setProcessedStatus(false);
					Integer duration=Integer.parseInt(request.getParameter("duration3"));
					drugOrderProcessed.setDurationPreProcess(duration);
					String drugRegimen=request.getParameter("selectedOption1");
					drugOrderProcessed.setDrugRegimen(drugRegimen);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);	
					}
					else if(concept.equals(drug4Concept)){
					DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
					drugOrderProcessed.setDrugOrder(o);
					drugOrderProcessed.setPatient(patient);
					drugOrderProcessed.setCreatedDate(new Date());
					drugOrderProcessed.setProcessedStatus(false);
					Integer duration=Integer.parseInt(request.getParameter("duration4"));
					drugOrderProcessed.setDurationPreProcess(duration);
					String drugRegimen=request.getParameter("selectedOption1");
					drugOrderProcessed.setDrugRegimen(drugRegimen);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);	
					}
				}
				
				}
				
				if (changeType == RegimenChangeType.CONTINUE) {
					List<DrugOrder> continu = new ArrayList<DrugOrder>(baseline.getDrugOrders());	
					for (DrugOrder o : continu){
						    String drugRegimen="";
						    DrugOrderProcessed drugOrderProcess=kenyaEmrService.getDrugOrderProcessed(o);
						    if(drugOrderProcess!=null){
						    drugRegimen=drugOrderProcess.getDrugRegimen();
							drugOrderProcess.setDiscontinuedDate(changeDate);
							kenyaEmrService.saveDrugOrderProcessed(drugOrderProcess);
							}
						    else{
						    	List<DrugOrderProcessed> drugOrderProcessCompleted=kenyaEmrService.getDrugOrderProcessedCompleted(o);
						    	for(DrugOrderProcessed drugOrderProcessCompletd:drugOrderProcessCompleted){
						    		drugRegimen=drugOrderProcessCompletd.getDrugRegimen();	
						    	}
						    }
							DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
							drugOrderProcessed.setDrugOrder(o);
							drugOrderProcessed.setPatient(patient);
							drugOrderProcessed.setCreatedDate(new Date());
							drugOrderProcessed.setProcessedStatus(false);
							Integer duration=Integer.parseInt(request.getParameter("duration"+o.getConcept().getName()));
							drugOrderProcessed.setDurationPreProcess(duration);
							drugOrderProcessed.setDrugRegimen(drugRegimen);
							kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);
					}
						
				}

				encounter=createEncounterForBaseLine(patient);
				for (DrugOrder o : toStart) {
					o.setEncounter(encounter);
					o.setPatient(patient);
					o.setStartDate(changeDate);
					o.setOrderType(os.getOrderType(OpenmrsConstants.ORDERTYPE_DRUG));
					Order order=os.saveOrder(o);
					
					Concept concept=o.getConcept();
					String drug1=request.getParameter("drug1");
					String drug2=request.getParameter("drug2");
					String drug3=request.getParameter("drug3");
					String drug4=request.getParameter("drug4");
					Concept drug1Concept=Context.getConceptService().getConceptByUuid(drug1.substring(2));
					Concept drug2Concept=Context.getConceptService().getConceptByUuid(drug2.substring(2));
					Concept drug3Concept=Context.getConceptService().getConceptByUuid(drug3.substring(2));
					Concept drug4Concept=new Concept();
					if(StringUtils.isNotBlank(drug4)){
					drug4Concept=Context.getConceptService().getConceptByUuid(drug4.substring(2));
					}
					if(concept.equals(drug1Concept)){
					DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
					drugOrderProcessed.setDrugOrder(o);
					drugOrderProcessed.setPatient(patient);
					drugOrderProcessed.setCreatedDate(new Date());
					drugOrderProcessed.setProcessedStatus(false);
					Integer duration=Integer.parseInt(request.getParameter("duration1"));
					drugOrderProcessed.setDurationPreProcess(duration);
					String drugRegimen=request.getParameter("selectedOption1");
					drugOrderProcessed.setDrugRegimen(drugRegimen);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);
					}
					else if(concept.equals(drug2Concept)){
					DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
					drugOrderProcessed.setDrugOrder(o);
					drugOrderProcessed.setPatient(patient);
					drugOrderProcessed.setCreatedDate(new Date());
					drugOrderProcessed.setProcessedStatus(false);
					Integer duration=Integer.parseInt(request.getParameter("duration2"));
					drugOrderProcessed.setDurationPreProcess(duration);
					String drugRegimen=request.getParameter("selectedOption1");
					drugOrderProcessed.setDrugRegimen(drugRegimen);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);	
					}
					else if(concept.equals(drug3Concept)){
					DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
					drugOrderProcessed.setDrugOrder(o);
					drugOrderProcessed.setPatient(patient);
					drugOrderProcessed.setCreatedDate(new Date());
					drugOrderProcessed.setProcessedStatus(false);
					Integer duration=Integer.parseInt(request.getParameter("duration3"));
					drugOrderProcessed.setDurationPreProcess(duration);
					String drugRegimen=request.getParameter("selectedOption1");
					drugOrderProcessed.setDrugRegimen(drugRegimen);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);	
					}
					else if(concept.equals(drug4Concept)){
					DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
					drugOrderProcessed.setDrugOrder(o);
					drugOrderProcessed.setPatient(patient);
					drugOrderProcessed.setCreatedDate(new Date());
					drugOrderProcessed.setProcessedStatus(false);
					Integer duration=Integer.parseInt(request.getParameter("duration4"));
					drugOrderProcessed.setDurationPreProcess(duration);
					String drugRegimen=request.getParameter("selectedOption1");
					drugOrderProcessed.setDrugRegimen(drugRegimen);
					kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);	
					}
				}
			}
			return encounter;
		}
		
		/**
		 * Gets the patient
		 * @return the patient
		 */
		public Patient getPatient() {
			return patient;
		}
		
		/**
		 * Sets the patient
		 * @param patient the patient
		 */
		public void setPatient(Patient patient) {
			this.patient = patient;
		}

		/**
		 * Gets the regimen category
		 * @return the regimen category
		 */
		public String getCategory() {
			return category;
		}

		/**
		 * Sets the regimen category
		 * @param category the category
		 */
		public void setCategory(String category) {
			this.category = category;
		}

		/**
		 * Gets the change type
		 * @return the change type
		 */
		public RegimenChangeType getChangeType() {
			return changeType;
		}

		/**
		 * Sets the change type
		 * @param changeType the change type
		 */
		public void setChangeType(RegimenChangeType changeType) {
			this.changeType = changeType;
		}

		/**
		 * Gets the change date
		 * @return the change date
		 */
		public Date getChangeDate() {
			return changeDate;
		}
		
		/**
		 * Set the change date
		 * @param changeDate the change date
		 */
		public void setChangeDate(Date changeDate) {
			this.changeDate = changeDate;
		}
		
		/**
		 * Gets the change reason
		 * @return the change reason
		 */
		public Concept getChangeReason() {
			return changeReason;
		}
		
		/**
		 * Sets the change reason
		 * @param changeReason the change reason
		 */
		public void setChangeReason(Concept changeReason) {
			this.changeReason = changeReason;
		}

		/**
		 * Gets the non-coded change reason
		 * @return the non-coded change reason
		 */
		public String getChangeReasonNonCoded() {
			return changeReasonNonCoded;
		}

		/**
		 * Sets the non-coded change reason
		 * @param changeReasonNonCoded the non-coded change reason
		 */
		public void setChangeReasonNonCoded(String changeReasonNonCoded) {
			this.changeReasonNonCoded = changeReasonNonCoded;
		}

		/**
		 * Gets the regimen
		 * @return the regimen
		 */
		public Regimen getRegimen() {
			return regimen;
		}

		/**
		 * Sets the regimen
		 * @param regimen the regimen
		 */
		public void setRegimen(Regimen regimen) {
			this.regimen = regimen;
		}
	}

	/**
	 * Analyzes the current regimen order and the new regimen component to decide which orders must be changed
	 * @param baseline the current regimen order
	 * @param component the new regimen component
	 * @param noChanges
	 * @param toChangeDose
	 * @param toStart
	 */
	private void changeRegimenHelper(RegimenOrder baseline, RegimenComponent component, List<DrugOrder> noChanges, List<DrugOrder> toChangeDose,
									 List<DrugOrder> toStart) {

		List<DrugOrder> sameGeneric = baseline.getDrugOrders(component.getDrugRef());

		boolean anyDoseChanges = false;
		for (DrugOrder o : sameGeneric) {
			if (o.getDose().equals(component.getDose()) && o.getUnits().equals(component.getUnits()) && OpenmrsUtil.nullSafeEquals(o.getFrequency(), component.getFrequency())) {
				noChanges.add(o);
			} else {
				toChangeDose.add(o);
				anyDoseChanges = true;
			}
		}
		if (anyDoseChanges || sameGeneric.size() == 0) {
			toStart.add(component.toDrugOrder(null, null,null));
		}
	}
	
    public Encounter createEncounterForBaseLine(Patient patient){
    	Encounter encounter = new Encounter();
		Location location = new Location(1);
		User user = Context.getAuthenticatedUser();
		List<Visit> visits=Context.getVisitService().getActiveVisitsByPatient(patient);
		int visitSize=visits.size();
		
		encounter.setPatient(patient);
		encounter.setCreator(user);
		encounter.setProvider(user);
		encounter.setEncounterDatetime(new Date());
		encounter.setEncounterType(Context.getEncounterService().getEncounterTypeByUuid(_EncounterType.REGIMEN_ORDER));
		encounter.setLocation(location);
		if(visitSize==1){
			for(Visit visit:visits){
		encounter.setVisit(visit);
			}
		}
		
		encounter=Context.getEncounterService().saveEncounter(encounter);
		return encounter;
    }
	
	public void saveExtraRowForArv(String[] durgList,HttpServletRequest request,Patient patient,Encounter encounter) {
		int count=6;
		
		for(String drug:durgList){
			String drugConceptId=request.getParameter("drug"+count);
			double dose=Integer.parseInt(request.getParameter("dose"+count));
			String unit=request.getParameter("unit"+count);
			String frequency=request.getParameter("frequency"+count);
			Integer duration=Integer.parseInt(request.getParameter("duration"+count));
			
			if(drugConceptId!=null){
			DrugOrder order = new DrugOrder();
			order.setOrderType(Context.getOrderService().getOrderType(OpenmrsConstants.ORDERTYPE_DRUG));
			order.setEncounter(encounter);
			order.setPatient(patient);
			order.setStartDate(new Date());
			order.setConcept(Context.getConceptService().getConceptByUuid(drugConceptId.substring(2)));
			order.setDose(dose);
			order.setUnits(unit);
			order.setFrequency(frequency);
			Context.getOrderService().saveOrder(order);
			
			KenyaEmrService kes = (KenyaEmrService) Context.getService(KenyaEmrService.class);
			
			DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
			drugOrderProcessed.setDrugOrder(order);
			drugOrderProcessed.setPatient(patient);
			drugOrderProcessed.setCreatedDate(new Date());
			drugOrderProcessed.setProcessedStatus(false);
			drugOrderProcessed.setDurationPreProcess(duration);
			drugOrderProcessed.setDrugRegimen("");
			kes.saveDrugOrderProcessed(drugOrderProcessed);
			count++;
			}
		}
	}
}