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

package org.openmrs.module.kenyaemr.fragment.controller.patient;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.ResultUtil;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.form.FormDescriptor;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.library.RecordedDeceasedCalculation;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Patient summary fragment
 */
public class PatientSummaryFragmentController {

	public void controller(@FragmentParam("patient") Patient patient,
			@FragmentParam("patient") Person person,
			@SpringBean FormManager formManager,
			@SpringBean KenyaUiUtils kenyaUi, PageRequest pageRequest,
			UiUtils ui, FragmentModel model) {

		AppDescriptor currentApp = kenyaUi.getCurrentApp(pageRequest);

		// Get all common per-patient forms as simple objects
		List<SimpleObject> forms = new ArrayList<SimpleObject>();
		for (FormDescriptor formDescriptor : formManager
				.getCommonFormsForPatient(currentApp, patient)) {
			forms.add(ui.simplifyObject(formDescriptor.getTarget()));
		}

		model.addAttribute("patient", patient);
		model.addAttribute("recordedAsDeceased",
				hasBeenRecordedAsDeceased(patient));
		model.addAttribute("forms", forms);

		// Patient address
		model.addAttribute("patientAdd", person.getPersonAddress());

		/*
		 * Get Entry point
		 */
		if (getLatestObs(patient, Dictionary.METHOD_OF_ENROLLMENT) != null) {
			Obs savedEntryPoint = getLatestObs(patient,
					Dictionary.METHOD_OF_ENROLLMENT);
			model.addAttribute("savedEntryPoint", savedEntryPoint);
		} else {
			model.addAttribute("savedEntryPoint", "");
		}

		PatientWrapper wrapperPatient = new PatientWrapper(patient);
		PersonWrapper wrapperPerson = new PersonWrapper(person);

		model.addAttribute("patientWrap", wrapperPatient);
		model.addAttribute("personWrap", wrapperPerson);

		/*
		 * Obstetric History
		 */
		String pregStatusVal = "";

		Obs pregStatus = getLatestObs(patient, Dictionary.PREGNANCY_STATUS);
		if (pregStatus != null) {
			pregStatusVal = pregStatus.getValueCoded().getName().toString();
		}
		model.addAttribute("pregStatusVal", pregStatusVal);
//Pregnancy
		String deliveredStatusVal = "";

		Obs deliveryStatus = getLatestObs(patient, Dictionary.DELIVERY_STATUS);
		if (deliveryStatus != null) {
			deliveredStatusVal = deliveryStatus.getValueCoded().getConceptId().toString();
			
		}
		model.addAttribute("deliveredStatusVal", deliveredStatusVal);
		/*
		 * Drug History
		 */
		String drugAllergiesVal = "";
		String drugAllergiesName = "";
		String otherReasonVal = "";

		Obs drugAllergies = getLatestObs(patient,
				Dictionary.DRUG_ALLERGY_TB_ENROLL_FORM);
		if (drugAllergies != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					drugAllergies.getEncounter());
			List<Obs> obsList = wrapped.allObs(drugAllergies.getConcept());
			for (Obs obs : obsList) {
				drugAllergiesVal = drugAllergiesVal.concat(obs.getValueCoded()
						.getName().toString());
			}
		}

		Obs drugName = getLatestObs(patient, Dictionary.ALLERGY_DRUG);
		if (drugName != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					drugName.getEncounter());
			List<Obs> obsList = wrapped.allObs(drugName.getConcept());
			for (Obs obs : obsList) { 
				drugAllergiesName = drugAllergiesName.concat(obs
						.getValueCoded().getName().toString());
				 
			}
		}
		
		Obs otherRegion = getLatestObs(patient, Dictionary.OTHER_REGION);
		if(otherRegion!=null){
		otherReasonVal=otherRegion.getValueText();
		}
		
		model.addAttribute("drugAllergiesVal", drugAllergiesVal);
		model.addAttribute("drugAllergiesName", drugAllergiesName);
		model.addAttribute("otherReasonVal",otherReasonVal);

		String listAllRiskFactor = "";
		String comorbitidyList = "";

		Obs comorbidity = getLatestObs(patient,
				Dictionary.COMORBIDITY_TB_ENROLL_FORM);
		
		if (comorbidity != null) {
			
			EncounterWrapper wrapped = new EncounterWrapper(
					comorbidity.getEncounter());
			List<Obs> obsList = wrapped.allObs(comorbidity.getConcept());

			for (Obs obs : obsList) {
				if (comorbitidyList.isEmpty()) {
					comorbitidyList = comorbitidyList.concat(obs
							.getValueCoded().getName().toString());
				} else {
					comorbitidyList = comorbitidyList.concat(", "
							+ obs.getValueCoded().getName().toString());
				}
			}
		}

		model.addAttribute("comorbitidyList", comorbitidyList);

		Obs dotProviderName = getLatestObs(patient,
				Dictionary.DOT_PROVIDER_NAME_TB_ENROLL_FORM);
		Obs dotProviderContact = getLatestObs(patient,
				Dictionary.DOT_PROVIDER_CONTACT_TB_ENROLL_FORM);
		Obs providerGroup = getLatestObs(patient,
				Dictionary.DOT_PROVIDER_DETAILS_SECTION);

		Map<Integer, String> dotMembers = new HashMap<Integer, String>();
		Integer index = 0;
		if (providerGroup != null) {
			EncounterWrapper wrappedObsGroup = new EncounterWrapper(
					providerGroup.getEncounter());
			List<Obs> obsGroupList = wrappedObsGroup.allObs(providerGroup
					.getConcept());
			for (Obs obsG : obsGroupList) {
				String dotName = "";
				int dotContact = 0;

				if (dotProviderName != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							dotProviderName.getEncounter());
					List<Obs> obsList = wrapped.allObs(dotProviderName
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							dotName = obs.getValueText().toString();
						}
					}
				}

				if (dotProviderContact != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							dotProviderContact.getEncounter());
					List<Obs> obsList = wrapped.allObs(dotProviderContact
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							dotContact = obs.getValueNumeric().intValue();
						}
					}
				}

				String val = dotName + ", " + dotContact;
				dotMembers.put(index, val);
				if(index==0){
					break;
				}
				index++;
			}
		}

		model.addAttribute("dotMembers", dotMembers);
		
		Concept finSupportconcept=Context.getConceptService().getConceptByUuid("162717AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept nutSupportconcept=Context.getConceptService().getConceptByUuid("5484AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		List<Obs> finObss=Context.getObsService().getObservationsByPersonAndConcept(patient, finSupportconcept);
		List<Obs> nutObss=Context.getObsService().getObservationsByPersonAndConcept(patient, nutSupportconcept);
		if(finObss.size()!=0){
			model.addAttribute("fin", finObss.get(0).getValueCoded().getName());	
		}
		else{
			model.addAttribute("fin", "");		
		}
		
		if(nutObss.size()!=0){
			model.addAttribute("nut", nutObss.get(0).getValueCoded().getName());	
		}
		else{
			model.addAttribute("nut", "");	
		}

	}

	/**
	 * Checks if a patient has been recorded as deceased by a program
	 * 
	 * @param patient
	 *            the patient
	 * @return true if patient was recorded as deceased
	 */
	protected boolean hasBeenRecordedAsDeceased(Patient patient) {
		PatientCalculation calc = CalculationUtils.instantiateCalculation(
				RecordedDeceasedCalculation.class, null);
		return ResultUtil.isTrue(Context.getService(
				PatientCalculationService.class)
				.evaluate(patient.getId(), calc));
	}

	private Obs getLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(patient, concept);
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(0);
		}
		return null;
	}

	private Obs getAllLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(patient, concept);
		int count = obs.size() - 1;
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(count);
		}
		return null;
	}

}