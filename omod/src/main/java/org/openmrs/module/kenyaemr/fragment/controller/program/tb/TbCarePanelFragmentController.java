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

package org.openmrs.module.kenyaemr.fragment.controller.program.tb;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbDiseaseClassificationCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbPatientClassificationCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentNumberCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentOutcomeCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentOutcomeDate;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentDrugSensitivity;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentDrugRegimen;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentStartDateCalculation;
import org.openmrs.module.kenyaemr.regimen.RegimenChangeHistory;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for TB care summary
 */
public class TbCarePanelFragmentController {

	public void controller(@FragmentParam("patient") Patient patient,
						   @FragmentParam("complete") Boolean complete,
						   FragmentModel model,
						   @SpringBean RegimenManager regimenManager) {

		Map<String, Object> calculationResults = new HashMap<String, Object>();
/*
		CalculationResult result = EmrCalculationUtils.evaluateForPatient(TbDiseaseClassificationCalculation.class, null, patient);
		calculationResults.put("tbDiseaseSite", result != null ? result.getValue() : null);

		result = EmrCalculationUtils.evaluateForPatient(TbPatientClassificationCalculation.class, null, patient);
		calculationResults.put("tbPatientStatus", result != null ? result.getValue() : null);

	//	result = EmrCalculationUtils.evaluateForPatient(TbTreatmentNumberCalculation.class, null, patient);
	//	calculationResults.put("tbTreatmentNumber", result != null ? result.getValue() : null);
		
		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentOutcomeCalculation.class, null, patient);
		calculationResults.put("tbTreatmentOutcome", result != null ? result.getValue() : null);

		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentOutcomeDate.class, null, patient);
		calculationResults.put("tbTreatmentOutcomeDate", result != null ? result.getValue() : null);
		
		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentDrugSensitivity.class, null, patient);
		calculationResults.put("tbTreatmentDrugSensitivity", result != null ? result.getValue() : null);

		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentDrugRegimen.class, null, patient);
		calculationResults.put("tbTreatmentDrugRegimen", result != null ? result.getValue() : null);
		
		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentStartDateCalculation.class, null, patient);
		calculationResults.put("tbTreatmentDrugStartDate", result != null ? result.getValue() : null);
		
		model.addAttribute("calculations", calculationResults);
*/
//		Dictionary.SPUTUM_SMEAR_TEST,
//		Dictionary.CULTURE_SOLID, Dictionary.CULTURE_LIQUID
		
		List<Obs> sputumSmear = getAllLatestObs(patient, Dictionary.SPUTUM_SMEAR_TEST);
		List<Obs> cultureSolid = getAllLatestObs(patient, Dictionary.CULTURE_SOLID);
		List<Obs> cultureLiquid = getAllLatestObs(patient, Dictionary.CULTURE_LIQUID);
	//	List<Obs> cultureSputum = getAllLatestObs(patient, Dictionary.SPUTUM_CULTURE);
	
		List<Visit> visitList = Context.getVisitService().getVisitsByPatient(patient);
		
		Map<Integer, String> smearCultureIndexList = new HashMap<Integer, String>();
		Integer visitIndex = visitList.size();
		int flag=0;
		if (visitList != null) {
			for (Visit v : visitList) {
				String sputumSmearVal = "";
				String solidcultureVal = "";String liquidcultureVal="";
				String visitDate = new SimpleDateFormat("dd-MMMM-yyyy").format(v.getStartDatetime());

				if (sputumSmear != null) {
					for (Obs obs : sputumSmear) {
						if (obs.getEncounter().getVisit().equals(v)) {
							if(obs.getValueCoded()!=null){
							sputumSmearVal = obs.getValueCoded().getName().toString();
							}
						}
					}
				}

				if (cultureSolid != null) {
					for (Obs obs : cultureSolid) {
						if (obs.getEncounter().getVisit().equals(v)) {
							if(obs.getValueCoded()!=null){
							solidcultureVal = obs.getValueCoded().getName().toString();
							flag=1;
							}
							
						}
					}
				}
				if(cultureLiquid != null){
					for (Obs obs : cultureLiquid) {
						if (obs.getEncounter().getVisit().equals(v)) {
							if(obs.getValueCoded()!=null){
							liquidcultureVal = obs.getValueCoded().getName().toString();
							flag=0;
							}
						}
					}
				}
		/*		if(cultureSputum != null){
					for (Obs obs : cultureSputum) {
						if (obs.getEncounter().getVisit().equals(v)) {
							if(obs.getValueCoded()!=null){
							cultureVal = obs.getValueCoded().getName().toString();
							}
						}
					}
				}*/
				
				visitIndex--;
				String val="";
				
				if(flag==1)
				{
					val = visitDate + ", " + sputumSmearVal+ ", " + solidcultureVal;
				}
				else
				{
					val = visitDate + ", " + sputumSmearVal+ ", " + liquidcultureVal;
				}
				
				
				smearCultureIndexList.put(visitIndex, val);
			}
		}
//		for(int i=visitIndex;i< 24; i++){
//			smearCultureIndexList.put(i, " , , ");
//		}
		model.addAttribute("smearCultureIndexList", smearCultureIndexList);
				
		Concept medSet = regimenManager.getMasterSetConcept("TB");
		RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, medSet);
		
		model.addAttribute("regimenHistory", history);
	}
	


	private List<Obs> getAllLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(patient, concept);
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs;
		}
		return null;
	}	

	
}