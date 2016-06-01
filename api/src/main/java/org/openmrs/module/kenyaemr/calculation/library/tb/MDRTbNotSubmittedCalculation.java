package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.TbConstants;

public class MDRTbNotSubmittedCalculation extends AbstractPatientCalculation
 {

/**
* @see org.openmrs.module.kenyacore.calculation.PatientFlagCalculation#getFlagMessage()
*/


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
Concept decisionTownship = Dictionary
		.getConcept(Dictionary.DECISION_TOWNSHP_LEVEL_ASSESSMENT);
Concept nottoSubmit = Dictionary.getConcept(Dictionary.NOT_TO_SUBMIT_COMITTEE);


CalculationResultMap ret = new CalculationResultMap();
for (Integer ptId : cohort) {
	boolean notsubmit = false;

	// check if a patient is alive
	if (alive.contains(ptId)) {

		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(Context.getPatientService().getPatient(ptId), decisionTownship);
		List<Obs> requiredObs = new LinkedList<Obs>();
		
		for(Obs o : obs ) {
			if(o.getValueCoded()==nottoSubmit){
				requiredObs.add(o);
				notsubmit = true;
			}
		}
		
		
		
	}
	ret.put(ptId, new BooleanResult(notsubmit, this, context));
}
return ret;
}
}
