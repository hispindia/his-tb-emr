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
public class TbTreatmentFailureCalculation extends AbstractPatientCalculation
		implements PatientFlagCalculation {

	/**
	 * @see org.openmrs.module.kenyacore.calculation.PatientFlagCalculation#getFlagMessage()
	 */
	@Override
	public String getFlagMessage() {
		return "Treatment failure";
	}

	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection,
	 *      java.util.Map,
	 *      org.openmrs.calculation.patient.PatientCalculationContext)
	 * @should determine whether patients treatment is failing
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort,
			Map<String, Object> parameterValues,
			PatientCalculationContext context) {

		// Get all patients who are alive and in TB program
		Set<Integer> alive = Filters.alive(cohort, context);

		// Get concepts
		Concept tbSputumSmearTest = Dictionary
				.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept tbSputumCultureTest = Dictionary
				.getConcept(Dictionary.SPUTUM_CULTURE);
		Concept solidCulture = Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept liquidCulture = Dictionary
				.getConcept(Dictionary.CULTURE_LIQUID);
		Concept positive = Dictionary
				.getConcept(Dictionary.POSITIVE);
		Concept afbntseen=Dictionary
				.getConcept(Dictionary.AFB_NOT_SEEN);
		Concept negative = Dictionary
				.getConcept(Dictionary.NEGATIVE);
		Concept trace=Dictionary.getConcept(Dictionary.TRACE);
		Concept singlePositive=Dictionary.getConcept(Dictionary.SINGLE_POSITIVE);
		Concept doublePositive=Dictionary.getConcept(Dictionary.DOUBLE_POSITIVE);
		Concept triplePositive=Dictionary.getConcept(Dictionary.TRIPLE_POSITIVE);
		Concept colonies=Dictionary.getConcept(Dictionary.COLONIES);
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean treatmentFailure = false;
			boolean obsSmearPositive=false;
			boolean obsCulturePositive=false;
			
			int positiveCount = 0;
			Patient patient = Context.getPatientService().getPatient(ptId);
			// check if a patient is alive
			if (alive.contains(ptId)) {

				List<Obs> obsSmear = Context.getObsService()
						.getObservationsByPersonAndConcept(patient, tbSputumSmearTest);
				List<Obs> obs = Context.getObsService()
						.getObservationsByPerson(patient);
				List<Obs> obsCulture = Context.getObsService()
						.getObservationsByPersonAndConcept(patient, tbSputumCultureTest);
				
				List<Obs> obsSolidCulture = Context.getObsService()
						.getObservationsByPersonAndConcept(patient, solidCulture);
				List<Obs> obsLiquidCulture = Context.getObsService()
						.getObservationsByPersonAndConcept(patient, liquidCulture);
				List<Obs> requiredObs = new LinkedList<Obs>();
				
				for (Obs o : obs) 
				{
					if (o.getConcept().equals(solidCulture)||o.getConcept().equals(liquidCulture)) 
					{
						requiredObs.add(o);
					}
				}
				
				
				for (Obs o : obsSmear) { 
					if(positiveCount > 5){
						obsSmearPositive=true;
						break;
					}
					else if(!(o.getValueCoded()==null))
					{
					
					 if(o.getValueCoded().equals(trace)||o.getValueCoded().equals(singlePositive)||
							 o.getValueCoded().equals(doublePositive)||o.getValueCoded().equals(triplePositive)){
						positiveCount++;
					}
					else if(o.getValueCoded().equals(afbntseen)){
						if(positiveCount>1){
							obsSmearPositive=true;
						}
						break;
					}
					}
				}
				 
				 
				
				positiveCount = 0;
				for (Obs o : requiredObs) {
					
					
					if(positiveCount > 5){
						obsCulturePositive=true;
						break;
					}
					else if(!(o.getValueCoded()==null))
					{
					if(o.getValueCoded().equals(positive)||o.getValueCoded().equals(colonies)||
							o.getValueCoded().equals(singlePositive)||o.getValueCoded().equals(doublePositive)||
							o.getValueCoded().equals(triplePositive)	){
						positiveCount++;
					}
					else if(o.getValueCoded().equals(negative)){
						if(positiveCount>1){
							obsCulturePositive=true;
						}
						break;
					}
					}
					}
			
				
				
				
				if(obsCulturePositive==true||obsSmearPositive==true)
				{ 
					treatmentFailure=true;
				}
				
				
			}
			/*
			 * Need Clarification on Treatment failure
			 * */
			ret.put(ptId, new BooleanResult(treatmentFailure, this, context));
		}
		return ret;
	}
}
