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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata._EncounterType;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
import org.openmrs.module.kenyaemr.regimen.Regimen;
import org.openmrs.module.kenyaemr.regimen.RegimenChange;
import org.openmrs.module.kenyaemr.regimen.RegimenChangeHistory;
import org.openmrs.module.kenyaemr.regimen.RegimenComponent;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.regimen.RegimenOrder;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.form.ValidatingCommandObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.MethodParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.validation.Errors;
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
			@RequestParam(value = "srNo", required = false) String[] srNo,
			HttpServletRequest request){
		ui.validate(command, command, null);
		Encounter encounter=command.apply(request,srNo);
		//command.saveExtraRowForArv(durgList,request,command.getPatient(),encounter);
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
		Start,
		Change,
		Substitute,
		Switch,
		Stop,
		Continue,
		Restart
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

			// Reason is only required for stopping or changing/
			/*
			if (changeType == RegimenChangeType.Change || changeType == RegimenChangeType.Switch || changeType == RegimenChangeType.Substitute) {
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
				if ((changeType == RegimenChangeType.Start || changeType == RegimenChangeType.Restart) && onRegimen) {
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
			if (changeType != RegimenChangeType.Continue) {
				try {
					errors.pushNestedPath("regimen");
					ValidationUtils.invokeValidator(new RegimenValidator(), regimen, errors);
				} finally {
					errors.popNestedPath();
				}
			}*/
		}
		
		/**
		 * Applies this regimen change
		 */
		public Encounter apply(HttpServletRequest request,String[] srNo) {
			Concept masterSet = regimenManager.getMasterSetConcept(category);
			RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, masterSet);
			RegimenChange lastChange = history.getLastChange();
			RegimenOrder baseline = lastChange != null ? lastChange.getStarted() : null;
			Encounter encounter=null;
			KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
			
			Date curDate = new Date();
			SimpleDateFormat mysqlDateTimeFormatter = new SimpleDateFormat(
					"dd-MMM-yy HH:mm:ss");
			Date date = new Date();
			String modifiedDate= new SimpleDateFormat("dd-MMM-yyyy").format(changeDate);
			try {
				date = mysqlDateTimeFormatter.parse(modifiedDate
						+ " " + curDate.getHours() + ":" + curDate.getMinutes()
						+ ":" + curDate.getSeconds());
			} catch (ParseException e) {
				date = curDate;
				e.printStackTrace();
			}
			
			if (baseline == null) {
				encounter=createEncounterForBaseLine(patient);
					for (String srn : srNo) {
				Concept drugConcept=null;
				String drugConcepString=request.getParameter("drugConcept"+srn);
				String noOfTabletString=request.getParameter("noOfTablet"+srn);
				String routeString=request.getParameter("route"+srn);
				String durationString=request.getParameter("duration"+srn);
				
				String doseString=request.getParameter("strength"+srn);
				String unitsString=request.getParameter("type"+srn);
				String frequencyString=request.getParameter("frequncy"+srn);
				
				Integer drugConceptId=null;
				Integer noOfTablet=null;
				Integer route=null;
				Integer duration=null;
				String dose=null;
				String units=null;
				String frequency=null;
				if(drugConcepString!="" && noOfTabletString!="" && routeString!="" && durationString!="" && doseString!="" && unitsString!="" && frequencyString!=""){
				drugConceptId=Integer.parseInt(drugConcepString);
				noOfTablet=Integer.parseInt(request.getParameter("noOfTablet"+srn));
				route=Integer.parseInt(routeString);
				duration=Integer.parseInt(durationString);
				dose=request.getParameter("strength"+srn);
				units=request.getParameter("type"+srn);
				frequency=request.getParameter("frequncy"+srn);
				}
				
				if(drugConceptId!=null){
					 drugConcept=Context.getConceptService().getConcept(drugConceptId);
				}
				
				List<ConceptAnswer> conceptAnswers=kenyaEmrService.getConceptAnswerByAnsweConcept(drugConcept);
				String typeOfRegimen="";
				Map<String,Concept> conMap=new LinkedHashMap<String,Concept>();
				for(ConceptAnswer conceptAnswer:conceptAnswers){
					//First line regimen_TB
					if(conceptAnswer.getConcept().getUuid().equals("aa5303a5-6f8f-4a3d-a4e1-22dc5c7b10ae")){
						typeOfRegimen=conceptAnswer.getConcept().getName().getName();
					}
					//Second line drug_TB
					else if(conceptAnswer.getConcept().getUuid().equals("b488dfb0-c12f-41a3-833a-76f37f492864")){
						typeOfRegimen=conceptAnswer.getConcept().getName().getName();		
					}
				}
				
				if(drugConcept!=null){
				DrugOrder drugOrder = new DrugOrder();
				drugOrder.setOrderType(Context.getOrderService().getOrderType(OpenmrsConstants.ORDERTYPE_DRUG));
				drugOrder.setEncounter(encounter);
				drugOrder.setPatient(patient);
				drugOrder.setStartDate(date);
				drugOrder.setConcept(drugConcept);
				//drugoOrder.setDrug();
				//drugoOrder.setDose(dose);
				drugOrder.setUnits(units);
				drugOrder.setFrequency(frequency);
				//drugOrder.setQuantity(noOfTablet);
				
				Order order=Context.getOrderService().saveOrder(drugOrder);
				
				DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
				drugOrderProcessed.setDrugOrder(Context.getOrderService().getDrugOrder(order.getOrderId()));
				drugOrderProcessed.setPatient(patient);
				drugOrderProcessed.setCreatedDate(new Date());
				drugOrderProcessed.setProcessedStatus(false);
				drugOrderProcessed.setDose(dose);
				drugOrderProcessed.setNoOfTablet(noOfTablet);
				drugOrderProcessed.setRoute(Context.getConceptService().getConcept(route));
				drugOrderProcessed.setDurationPreProcess(duration);	
				drugOrderProcessed.setRegimenConcept(drugConcept);
				drugOrderProcessed.setRegimenChangeType(changeType.name());
				drugOrderProcessed.setTypeOfRegimen(typeOfRegimen);	
				Integer srnn=Integer.parseInt(srn);
				if(srnn<7){
				String regimenNo=request.getParameter("regimenNo");
				drugOrderProcessed.setRegimenNo(regimenNo);
				if(regimenNo.equals("Standard Regimen 1")){
				drugOrderProcessed.setRegimenName("6(Amk Z Lfx Eto Cs)/14(Lfx Eto Cs Z)");
				}
				else if(regimenNo.equals("Standard Regimen 2")){
					drugOrderProcessed.setRegimenName("6(Amk Z Lfx Eto Cs PAS)/14(Lfx Eto Cs Z PAS)");	
				}
				}
				kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);
				}
			   }
			}
			else {
				if (changeType == RegimenChangeType.Continue) {
					List<DrugOrder> continu = new ArrayList<DrugOrder>(baseline.getDrugOrders());	
					for (DrugOrder drugOrder : continu){
						    DrugOrderProcessed dop=new DrugOrderProcessed();
						    DrugOrderProcessed drugOrderProcess=kenyaEmrService.getDrugOrderProcessed(drugOrder);
						    if(drugOrderProcess!=null){
						    	dop=drugOrderProcess;
							drugOrderProcess.setDiscontinuedDate(new Date());
							kenyaEmrService.saveDrugOrderProcessed(drugOrderProcess);
							}
						    else{
						    	List<DrugOrderProcessed> drugOrderProcessCompleted=kenyaEmrService.getDrugOrderProcessedCompleted(drugOrder);
						    	for(DrugOrderProcessed drugOrderProcessCompletd:drugOrderProcessCompleted){
						    		dop=drugOrderProcessCompletd;
						    	}
						    }
							DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
							drugOrderProcessed.setDrugOrder(dop.getDrugOrder());
							drugOrderProcessed.setPatient(patient);
							drugOrderProcessed.setCreatedDate(new Date());
							drugOrderProcessed.setProcessedStatus(false);
							drugOrderProcessed.setDose(dop.getDose());
							drugOrderProcessed.setNoOfTablet(dop.getNoOfTablet());
							drugOrderProcessed.setRoute(dop.getRoute());
							Integer duration=Integer.parseInt(request.getParameter("duration"+drugOrder.getConcept().getName()));
							drugOrderProcessed.setDurationPreProcess(duration);
							drugOrderProcessed.setRegimenConcept(dop.getRegimenConcept());
							drugOrderProcessed.setRegimenChangeType(changeType.name());
							drugOrderProcessed.setTypeOfRegimen(dop.getTypeOfRegimen());	
							drugOrderProcessed.setRegimenNo(dop.getRegimenNo());
							drugOrderProcessed.setRegimenName(dop.getRegimenName());
							kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);
					}	
				}
				else if(changeType == RegimenChangeType.Change){
				encounter=createEncounterForBaseLine(patient);
				if (srNo != null) {
					for (String srn : srNo) {
				Concept drugConcept=null;
				String drugConcepString=request.getParameter("drugConcept"+srn);
				String noOfTabletString=request.getParameter("noOfTablet"+srn);
				String routeString=request.getParameter("route"+srn);
				String durationString=request.getParameter("duration"+srn);
				
				String doseString=request.getParameter("strength"+srn);
				String unitsString=request.getParameter("type"+srn);
				String frequencyString=request.getParameter("frequncy"+srn);
				
				Integer drugConceptId=null;
				Integer noOfTablet=null;
				Integer route=null;
				Integer duration=null;
				String dose=null;
				String units=null;
				String frequency=null;
				if(drugConcepString!="" && noOfTabletString!="" && routeString!="" && durationString!="" && doseString!="" && unitsString!="" && frequencyString!=""){
				drugConceptId=Integer.parseInt(drugConcepString);
				noOfTablet=Integer.parseInt(request.getParameter("noOfTablet"+srn));
				route=Integer.parseInt(routeString);
				duration=Integer.parseInt(durationString);
				dose=request.getParameter("strength"+srn);
				units=request.getParameter("type"+srn);
				frequency=request.getParameter("frequncy"+srn);
				}

				if(drugConceptId!=null){
					 drugConcept=Context.getConceptService().getConcept(drugConceptId);
				}
				
				List<ConceptAnswer> conceptAnswers=kenyaEmrService.getConceptAnswerByAnsweConcept(drugConcept);
				String typeOfRegimen="";
				Map<String,Concept> conMap=new LinkedHashMap<String,Concept>();
				for(ConceptAnswer conceptAnswer:conceptAnswers){
					//First line regimen_TB
					if(conceptAnswer.getConcept().getUuid().equals("aa5303a5-6f8f-4a3d-a4e1-22dc5c7b10ae")){
						typeOfRegimen=conceptAnswer.getConcept().getName().getName();
					}
					//Second line drug_TB
					else if(conceptAnswer.getConcept().getUuid().equals("b488dfb0-c12f-41a3-833a-76f37f492864")){
						typeOfRegimen=conceptAnswer.getConcept().getName().getName();		
					}
				}
				
				for(DrugOrder drugOrder:baseline.getDrugOrders()){
					DrugOrderProcessed dop=kenyaEmrService.getLastDrugOrderProcessedNotDiscontinued(drugOrder);
					if(dop!=null){
					if(dop.getDrugOrder().getConcept().equals(drugConcept)){
						if(dop.getDose().equals(dose) && dop.getDrugOrder().getUnits().equals(units) && dop.getDrugOrder().getFrequency().equals(frequency) && dop.getRoute().equals(route)){
						dop.setDiscontinuedDate(new Date());
						kenyaEmrService.saveDrugOrderProcessed(dop);
						DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
						drugOrderProcessed.setDrugOrder(dop.getDrugOrder());
						drugOrderProcessed.setPatient(patient);
						drugOrderProcessed.setCreatedDate(new Date());
						drugOrderProcessed.setProcessedStatus(false);
						drugOrderProcessed.setDose(dose);
						drugOrderProcessed.setNoOfTablet(noOfTablet);
						drugOrderProcessed.setRoute(Context.getConceptService().getConcept(route));
						drugOrderProcessed.setDurationPreProcess(duration);	
						drugOrderProcessed.setRegimenConcept(drugConcept);
						drugOrderProcessed.setRegimenChangeType(changeType.name());
						drugOrderProcessed.setTypeOfRegimen(typeOfRegimen);	
						drugOrderProcessed.setRegimenNo(request.getParameter("regimenNo"));
						Integer srnn=Integer.parseInt(srn);
						if(srnn<7){
						String regimenNo=request.getParameter("regimenNo");
						drugOrderProcessed.setRegimenNo(regimenNo);
						if(regimenNo.equals("Standard Regimen 1")){
						drugOrderProcessed.setRegimenName("6(Amk Z Lfx Eto Cs)/14(Lfx Eto Cs Z)");
						}
						else if(regimenNo.equals("Standard Regimen 2")){
							drugOrderProcessed.setRegimenName("6(Amk Z Lfx Eto Cs PAS)/14(Lfx Eto Cs Z PAS)");	
						}
						}
						kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);
						}
						else{
							dop.setDiscontinuedDate(new Date());
							kenyaEmrService.saveDrugOrderProcessed(dop);
							drugOrder.setDiscontinued(true);
							drugOrder.setDiscontinuedDate(date);
							drugOrder.setDiscontinuedBy(Context.getAuthenticatedUser());
							drugOrder.setDiscontinuedReason(changeReason);
							drugOrder.setDiscontinuedReasonNonCoded(changeReasonNonCoded);
							Context.getOrderService().saveOrder(drugOrder);	
							
						
							DrugOrder drugOder = new DrugOrder();
							drugOder.setOrderType(Context.getOrderService().getOrderType(OpenmrsConstants.ORDERTYPE_DRUG));
							drugOder.setEncounter(encounter);
							drugOder.setPatient(patient);
							drugOder.setStartDate(date);
							drugOder.setConcept(drugConcept);
							drugOder.setUnits(units);
							drugOder.setFrequency(frequency);
							//drugOrder.setQuantity(noOfTablet);
							
							Order order=Context.getOrderService().saveOrder(drugOder);
							
							DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
							drugOrderProcessed.setDrugOrder(Context.getOrderService().getDrugOrder(order.getOrderId()));
							drugOrderProcessed.setPatient(patient);
							drugOrderProcessed.setCreatedDate(new Date());
							drugOrderProcessed.setProcessedStatus(false);
							drugOrderProcessed.setDose(dose);
							drugOrderProcessed.setNoOfTablet(noOfTablet);
							drugOrderProcessed.setRoute(Context.getConceptService().getConcept(route));
							drugOrderProcessed.setDurationPreProcess(duration);	
							drugOrderProcessed.setRegimenConcept(drugConcept);
							drugOrderProcessed.setRegimenChangeType(changeType.name());
							drugOrderProcessed.setTypeOfRegimen(typeOfRegimen);	
							drugOrderProcessed.setRegimenNo(request.getParameter("regimenNo"));
							Integer srnn=Integer.parseInt(srn);
							if(srnn<7){
							String regimenNo=request.getParameter("regimenNo");
							drugOrderProcessed.setRegimenNo(regimenNo);
							if(regimenNo.equals("Standard Regimen 1")){
							drugOrderProcessed.setRegimenName("6(Amk Z Lfx Eto Cs)/14(Lfx Eto Cs Z)");
							}
							else if(regimenNo.equals("Standard Regimen 2")){
								drugOrderProcessed.setRegimenName("6(Amk Z Lfx Eto Cs PAS)/14(Lfx Eto Cs Z PAS)");	
							}
							}
							kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);
						}
					}
					else{
						/*
						System.out.println("yyyyyyyyyyyyyyy");
						dop.setDiscontinuedDate(new Date());
						kenyaEmrService.saveDrugOrderProcessed(dop);
						drugOrder.setDiscontinued(true);
						drugOrder.setDiscontinuedDate(date);
						drugOrder.setDiscontinuedBy(Context.getAuthenticatedUser());
						drugOrder.setDiscontinuedReason(changeReason);
						drugOrder.setDiscontinuedReasonNonCoded(changeReasonNonCoded);
						Context.getOrderService().saveOrder(drugOrder);	*/	
					 }
				   }
				}
								
				List<String> stringg=new ArrayList<String>();
				for(DrugOrder drugOrder:baseline.getDrugOrders()){
					stringg.add(drugOrder.getConcept().getName().getName());	
				}
				
				if(drugConcept!=null){
				if(stringg.contains(drugConcept.getName().getName())){
				}
				else{
				encounter=createEncounterForBaseLine(patient);
				DrugOrder drugOder = new DrugOrder();
				drugOder.setOrderType(Context.getOrderService().getOrderType(OpenmrsConstants.ORDERTYPE_DRUG));
				drugOder.setEncounter(encounter);
				drugOder.setPatient(patient);
				drugOder.setStartDate(date);
				drugOder.setConcept(drugConcept);
				drugOder.setUnits(units);
				drugOder.setFrequency(frequency);
				//drugOder.setQuantity(noOfTablet);
				
				Order order=Context.getOrderService().saveOrder(drugOder);
				
				DrugOrderProcessed drugOrderProcessed=new DrugOrderProcessed();
				drugOrderProcessed.setDrugOrder(Context.getOrderService().getDrugOrder(order.getOrderId()));
				drugOrderProcessed.setPatient(patient);
				drugOrderProcessed.setCreatedDate(new Date());
				drugOrderProcessed.setProcessedStatus(false);
				drugOrderProcessed.setDose(dose);
				drugOrderProcessed.setNoOfTablet(noOfTablet);
				drugOrderProcessed.setRoute(Context.getConceptService().getConcept(route));
				drugOrderProcessed.setDurationPreProcess(duration);	
				drugOrderProcessed.setRegimenConcept(drugConcept);
				drugOrderProcessed.setRegimenChangeType(changeType.name());
				drugOrderProcessed.setTypeOfRegimen(typeOfRegimen);	
				drugOrderProcessed.setRegimenNo(request.getParameter("regimenNo"));
				Integer srnn=Integer.parseInt(srn);
				if(srnn<7){
				String regimenNo=request.getParameter("regimenNo");
				drugOrderProcessed.setRegimenNo(regimenNo);
				if(regimenNo.equals("Standard Regimen 1")){
				drugOrderProcessed.setRegimenName("6(Amk Z Lfx Eto Cs)/14(Lfx Eto Cs Z)");
				}
				else if(regimenNo.equals("Standard Regimen 2")){
					drugOrderProcessed.setRegimenName("6(Amk Z Lfx Eto Cs PAS)/14(Lfx Eto Cs Z PAS)");	
				}
				}
				kenyaEmrService.saveDrugOrderProcessed(drugOrderProcessed);
				}
				}
			  }
			 }
			}
			else if(changeType == RegimenChangeType.Stop){
				OrderService os = Context.getOrderService();
				
				List<DrugOrder> toStop = new ArrayList<DrugOrder>(baseline.getDrugOrders());
				for (DrugOrder o : toStop) {
					DrugOrderProcessed dop=kenyaEmrService.getDrugOrderProcessed(o);
					if(dop!=null){
					dop.setDiscontinuedDate(new Date());
					kenyaEmrService.saveDrugOrderProcessed(dop);
					}
					o.setDiscontinued(true);
					o.setDiscontinuedDate(date);
					o.setDiscontinuedBy(Context.getAuthenticatedUser());
					o.setDiscontinuedReason(changeReason);
					o.setDiscontinuedReasonNonCoded(changeReasonNonCoded);
					os.saveOrder(o);
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
//		encounter.setEncounterDatetime(new Date());
		encounter.setEncounterType(Context.getEncounterService().getEncounterTypeByUuid(_EncounterType.REGIMEN_ORDER));
		encounter.setLocation(location);
		if(visitSize==1){
			for(Visit visit:visits){
		encounter.setVisit(visit);
		Date curDate = new Date();
		SimpleDateFormat mysqlDateTimeFormatter = new SimpleDateFormat(
				"dd-MMM-yy HH:mm:ss");
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("dd-MMM-yyyy").format(visit.getStartDatetime());
		try {
			date = mysqlDateTimeFormatter.parse(modifiedDate
					+ " " + curDate.getHours() + ":" + curDate.getMinutes()
					+ ":" + curDate.getSeconds());
		} catch (ParseException e) {
			date = curDate;
			e.printStackTrace();
		}
		encounter.setEncounterDatetime(date);
			}
		}
		else{
			encounter.setEncounterDatetime(new Date());
		}
		
		encounter=Context.getEncounterService().saveEncounter(encounter);
		return encounter;
    }
}