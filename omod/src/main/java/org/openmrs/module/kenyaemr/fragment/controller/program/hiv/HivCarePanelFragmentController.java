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
import org.openmrs.module.kenyaemr.calculation.library.hiv.InHivProgramCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastCd4CountCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastCd4PercentageCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastDiagnosisCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastWhoStageCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.StartedOnCptCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.InitialArtRegimenCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.InitialArtStartDateCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.OnArtCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.StartedOnArtCalculation;
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

		model.addAttribute("patient", patient);

		Obs isHiv = getLatestObs(patient, Dictionary.HIV_TEST);
		String isHivcount = "";
		if (isHiv != null) {
			isHivcount=isHiv.getValueCoded().getName().toString();
			
		}
		model.addAttribute("isHivcount", isHivcount);
		
		calculationResults.put("hivTestdate", EmrCalculationUtils.evaluateForPatient(InHivProgramCalculation.class, null, patient));
		
		Obs hivResult = getLatestObs(patient, Dictionary.RESULT_OF_HIV_TEST);
		String hivresult = "";
		if (hivResult != null) {
			hivresult=hivResult.getValueCoded().getName().toString();
			
		}
		model.addAttribute("hivresult", hivresult);
		
		
        
		Obs startArt = getLatestObs(patient, Dictionary.ART_STARTED);
			String ArtStarted = "";
			if (startArt != null) {
				ArtStarted=startArt.getValueCoded().getName().toString();
				
			}
			model.addAttribute("ArtStarted", ArtStarted);
			calculationResults.put("artstart", EmrCalculationUtils.evaluateForPatient(StartedOnArtCalculation.class, null, patient));
		Obs currentregime = getLatestObs(patient, Dictionary.CURRENT_REGIME);
			String RegimeStart = "";
			if (currentregime != null) {
				RegimeStart=currentregime.getValueCoded().getName().toString();
				
			}
			model.addAttribute("RegimeStart", RegimeStart);
		
			Obs startcpt = getLatestObs(patient, Dictionary.CPT_STARTED);
			String CPTStart = "";
			if (startcpt != null) {
				CPTStart=startcpt.getValueCoded().getName().toString();
				
			}
			model.addAttribute("CPTStart", CPTStart);
			calculationResults.put("cptstart", EmrCalculationUtils.evaluateForPatient(StartedOnCptCalculation.class, null, patient));
		
			model.addAttribute("calculations", calculationResults);
		
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