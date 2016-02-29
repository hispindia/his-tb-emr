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

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaemr.wrapper.Enrollment;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * HIV program enrollment summary fragment
 */
public class HivEnrollmentSummaryFragmentController {
	private Obs savedEntryPoint;
	private String otherEntryPoint;
	private String entryPoint;
	public String controller(@FragmentParam("patientProgram") PatientProgram patientProgram,
						   @FragmentParam(value = "encounter", required = false) Encounter encounter,
						   @FragmentParam("showClinicalData") boolean showClinicalData,
						   FragmentModel model) {
		
		Map<String, Object> dataPoints = new LinkedHashMap<String, Object>();
		
		dataPoints.put("Enrolled Date", patientProgram.getDateEnrolled());
		
		Patient patient = patientProgram.getPatient();
		savedEntryPoint = getLatestObs(patient, Dictionary.METHOD_OF_ENROLLMENT);
		if (savedEntryPoint != null) {
			entryPoint = savedEntryPoint.getValueCoded().getName().toString();
			otherEntryPoint = savedEntryPoint.getValueText();
			if(otherEntryPoint!=null){
			dataPoints.put("Entry Point", entryPoint + otherEntryPoint);
			}
			else {
				dataPoints.put("Entry Point", entryPoint );
			}
		}
/*		
		if (encounter != null) {
			EncounterWrapper wrapper = new EncounterWrapper(encounter);

			Obs o = wrapper.firstObs(Dictionary.getConcept(Dictionary.METHOD_OF_ENROLLMENT));
			if (o != null) {
				dataPoints.put("Entry point", o.getValueCoded());
			}
		}

		if (showClinicalData) {
			Enrollment enrollment = new Enrollment(patientProgram);

			Obs o = enrollment.firstObs(Dictionary.getConcept(Dictionary.CURRENT_WHO_STAGE));
			if (o != null) {
				dataPoints.put("WHO stage", o.getValueCoded());
			}
		}
*/
		model.put("dataPoints", dataPoints);
		return "view/dataPoints";
	}
	
	private Obs getLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(patient, concept);
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(0);
		}
		return null;
	}
}