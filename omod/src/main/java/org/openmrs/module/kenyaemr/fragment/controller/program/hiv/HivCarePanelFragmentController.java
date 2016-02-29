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

package org.openmrs.module.kenyaemr.fragment.controller.program.hiv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.module.kenyacore.program.ProgramManager;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastCd4CountCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastCd4PercentageCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastDiagnosisCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastWhoStageCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.InitialArtRegimenCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.InitialArtStartDateCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.OnCPTCalculation;
import org.openmrs.module.kenyaemr.regimen.RegimenChangeHistory;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 * Controller for HIV care summary
 */
public class HivCarePanelFragmentController {

	public void controller(@FragmentParam("patient") Patient patient,
						   @FragmentParam("complete") Boolean complete,
						   FragmentModel model,
						   @SpringBean RegimenManager regimenManager,
						   @SpringBean ProgramManager programManager) {

		Map<String, CalculationResult> calculationResults = new HashMap<String, CalculationResult>();

		
		/*
		 * Encounter details
		 */
		Date dateArt = new Date();
		List<Encounter> artEncounters = Context.getEncounterService()
				.getEncounters(patient);
		for (Encounter en : artEncounters) {
			if (en.getEncounterType().getUuid()
					.equals("0cb4417d-b98d-4265-92aa-c6ee3d3bb317")) {
				if (dateArt.after(en.getEncounterDatetime())) {
					dateArt = en.getEncounterDatetime();
				}
			}
		}
		model.addAttribute("initialArtStartDate", new SimpleDateFormat(
				"dd-MMMM-yyyy").format(dateArt));
		
		if (complete != null && complete.booleanValue()) {
			calculationResults.put("initialArtRegimen", EmrCalculationUtils.evaluateForPatient(InitialArtRegimenCalculation.class, null, patient));
//			calculationResults.put("initialArtStartDate", EmrCalculationUtils.evaluateForPatient(InitialArtStartDateCalculation.class, null, patient));
		}

		calculationResults.put("lastWHOStage", EmrCalculationUtils.evaluateForPatient(LastWhoStageCalculation.class, null, patient));

		model.addAttribute("patient", patient);

//		calculationResults.put("lastCD4Count", EmrCalculationUtils.evaluateForPatient(LastCd4CountCalculation.class, null, patient));
		calculationResults.put("lastCD4Percent", EmrCalculationUtils.evaluateForPatient(LastCd4PercentageCalculation.class, null, patient));

		Obs cdList = getLatestObs(patient, Dictionary.CD4_COUNT);
		String cd4Count = "";
		if (cdList != null) {
			cd4Count=cdList.getValueText().toString();
		}
		model.addAttribute("cd4Count", cd4Count);
		
		Obs cdPerList = getLatestObs(patient, Dictionary.CD4_PERCENT);
		String cd4PerCount = "";
		if (cdPerList != null) {
			cd4PerCount=cdPerList.getValueText().toString();
		}
		model.addAttribute("cd4PerCount", cd4PerCount);
		
		Obs viralLoad = getLatestObs(patient, Dictionary.HIV_VIRAL_LOAD);
		String viralLoadResult = "";
		if (viralLoad != null) {
			viralLoadResult=viralLoad.getValueText().toString();
		}
		model.addAttribute("viralLoadResult", viralLoadResult);
		
		String listAllDiag = "";
		
		Obs diagList = getAllLatestObs(patient, Dictionary.HIV_CARE_DIAGNOSIS);
		Obs consultationObs =   getAllLatestObs(patient, Dictionary.CONSULTATION_DETAIL);
		if(consultationObs!=null){
			EncounterWrapper wrappedG = new EncounterWrapper(
					consultationObs.getEncounter());
			List<Obs> obsGroupList = wrappedG.allObs(consultationObs.getConcept());
			for (Obs obsG : obsGroupList) {
				if (diagList != null) {
					List<Obs> obsList = Context.getObsService().getObservationsByPersonAndConcept(patient, Dictionary.getConcept(Dictionary.HIV_CARE_DIAGNOSIS));
					
					for (Obs obs : obsList) {
						if(obs.getObsGroupId() == obsG.getObsId()){
							if (listAllDiag.isEmpty()) {
								listAllDiag = listAllDiag.concat(obs
										.getValueCoded().getName().toString());
							} else {
								listAllDiag = listAllDiag.concat(", "
										+ obs.getValueCoded().getName().toString());
							}
							
						}
					}
				}
			}
			
		}
		


		model.addAttribute("listAllDiag", listAllDiag);		
		
//		calculationResults.put("lastDiagnosis", EmrCalculationUtils.evaluateForPatient(LastDiagnosisCalculation.class, null, patient));
		calculationResults.put("onCpt", EmrCalculationUtils.evaluateForPatient(OnCPTCalculation.class, null, patient));
		
		model.addAttribute("calculations", calculationResults);

		
		Concept medSet = regimenManager.getMasterSetConcept("ARV");
		RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, medSet);
		model.addAttribute("regimenHistory", history);
		
		model.addAttribute("graphingConcepts", Dictionary.getConcepts(Dictionary.WEIGHT_KG, Dictionary.CD4_COUNT, Dictionary.CD4_PERCENT, Dictionary.HIV_VIRAL_LOAD,Dictionary.OI_COUNT));
		
		PatientProgram currentEnrollment = null;
		Program program=Context.getProgramWorkflowService().getProgramByUuid("96ec813f-aaf0-45b2-add6-e661d5bf79d6");
		
		// Gather all program enrollments for this patient and program
				List<PatientProgram> enrollments = programManager.getPatientEnrollments(patient, program);
				for (PatientProgram enrollment : enrollments) {
					if (enrollment.getActive()) {
						currentEnrollment = enrollment;
					}
				}

		model.addAttribute("currentEnrollment", currentEnrollment);
		
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

}