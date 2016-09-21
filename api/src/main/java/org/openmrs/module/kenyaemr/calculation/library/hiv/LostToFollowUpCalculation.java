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

package org.openmrs.module.kenyaemr.calculation.library.hiv;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.HivConstants;
import org.openmrs.module.kenyaemr.TbConstants;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.metadata.TbMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils.daysSince;

/**
 * Calculates whether a patient has been lost to follow up. Calculation returns true if patient
 * is alive, enrolled in the HIV program, but hasn't had an encounter in LOST_TO_FOLLOW_UP_THRESHOLD_DAYS days
 */
public class LostToFollowUpCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {

	@Override
	public String getFlagMessage() {
		return "Lost to Followup";
	}

	/**
	 * Evaluates the calculation
	 * @should calculate false for deceased patients
	 * @should calculate false for patients not in HIV program
	 * @should calculate false for patients with an encounter in last LOST_TO_FOLLOW_UP_THRESHOLD_DAYS days days since appointment date
	 * @should calculate true for patient with no encounter in last LOST_TO_FOLLOW_UP_THRESHOLD_DAYS days days since appointment date
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> arg1, PatientCalculationContext context) {

		Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);

		Set<Integer> alive = Filters.alive(cohort, context);
		Set<Integer> inTbProgram = Filters.inProgram(tbProgram, alive, context);

		CalculationResultMap lastEncounters = Calculations.lastEncounter(null, inTbProgram, context);
		
        
		CalculationResultMap ret = new CalculationResultMap();
		
		for (Integer ptId : cohort) {
			boolean lost = false;
			Patient pat=Context.getPatientService().getPatient(ptId);
			
			List<Visit>visss=Context.getVisitService().getVisitsByPatient(pat);
			List<Visit>v=Context.getVisitService().getActiveVisitsByPatient(pat);
			
			
			if (inTbProgram.contains(ptId))
			{
			for(Visit viss:visss)
			{
				if(viss.getStopDatetime()!=null)
				{ for(Visit viii:v)
				{
					long dd=(viii.getStartDatetime().getTime()-viss.getStopDatetime().getTime())/(24 * 60 * 60 * 1000);
					
					if(dd > TbConstants.MONTH_THREE_SPUTUM_TEST)
					{
						lost=true;
						break;
					}	
				}
				break;
				}
				
			}		
						
			
			
			

		}
			ret.put(ptId, new SimpleResult(lost, this, context));
		}
		return ret;
	}
}
