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

package org.openmrs.module.kenyaemr.calculation.library.tb;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.TbConstants;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.TbMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Calculate whether patients are due for a sputum test. Calculation returns
 * true if the patient is alive, and screened for tb, and has cough of any
 * duration probably 2 weeks during the 2 weeks then there should have been no
 * sputum results recorded
 */
public class NeedsTbCultureTestCalculation extends AbstractPatientCalculation
		implements PatientFlagCalculation {

	/**
	 * @see org.openmrs.module.kenyacore.calculation.PatientFlagCalculation#getFlagMessage()
	 */
	@Override
	public String getFlagMessage() {
		return "Due for Sputum Culture";
	}

	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection,
	 *      java.util.Map,
	 *      org.openmrs.calculation.patient.PatientCalculationContext)
	 * @should determine whether patients need culture test
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort,
			Map<String, Object> parameterValues,
			PatientCalculationContext context) {

		// Get all patients who are alive and in TB program
		Set<Integer> alive = Filters.alive(cohort, context);

		// Get concepts
		Concept tbSputumCultureTest = Dictionary
				.getConcept(Dictionary.SPUTUM_CULTURE);
		Concept labOrder = Dictionary.getConcept(Dictionary.LAB_ORDER);
		Concept solidCulture = Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept liquidCulture = Dictionary
				.getConcept(Dictionary.CULTURE_LIQUID);

		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean needsSputum = true;
			Patient patient = Context.getPatientService().getPatient(ptId);
			// check if a patient is alive
			if (alive.contains(ptId)) {

				List<Obs> obs = Context.getObsService()
						.getObservationsByPersonAndConcept(patient, labOrder);
				List<Obs> requiredObs = new LinkedList<Obs>();
				for (Obs o : obs) {
					if (o.getValueCoded() == tbSputumCultureTest
							|| o.getValueCoded() == solidCulture
							|| o.getValueCoded() == liquidCulture) {
						requiredObs.add(o);
					}
				}

				int visitCount = requiredObs.size();

				Date today = new Date();
				if (visitCount > 0) {
					long diff = today.getTime()
							- requiredObs.get(0).getObsDatetime().getTime();
					int dayDiff = (int) TimeUnit.DAYS.convert(diff,
							TimeUnit.MILLISECONDS);
					if (visitCount == 1 || visitCount == 2 || visitCount == 7
							|| visitCount == 8 || visitCount == 10
							|| visitCount == 11 || visitCount == 12
							|| visitCount == 13) {
						if (dayDiff <= TbConstants.MONTH_TWO_SPUTUM_TEST) {
							needsSputum = false;
						}
					}
					else if(visitCount == 4){
						if (dayDiff <= TbConstants.MONTHLY_SPUTUM_TEST) {
							needsSputum = false;
						}
					}
					else if(visitCount == 3 || visitCount == 5){
						if (dayDiff <= TbConstants.MONTH_THREE_SPUTUM_TEST) {
							needsSputum = false;
						}
					}
					else if(visitCount == 6 || visitCount == 9){
						if (dayDiff <= TbConstants.MONTH_FOUR_SPUTUM_TEST) {
							needsSputum = false;
						}
					}
				}
			}
			ret.put(ptId, new BooleanResult(needsSputum, this, context));
		}
		return ret;
	}
}