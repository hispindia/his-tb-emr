package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.Dictionary;

public class TbPatientsARTCalculation extends AbstractPatientCalculation
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

//Get all patients who are alive and in TB program
Set<Integer> alive = Filters.alive(cohort, context);

//Get concepts
Concept artstarted = Dictionary
		.getConcept(Dictionary.ART_STARTED);
Concept yes = Dictionary.getConcept(Dictionary.YES);


CalculationResultMap ret = new CalculationResultMap();
for (Integer ptId : cohort) {
	boolean status = false;

	// check if a patient is alive
	if (alive.contains(ptId)) {

		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(Context.getPatientService().getPatient(ptId), artstarted);
		List<Obs> requiredObs = new LinkedList<Obs>();
		
		for(Obs o : obs ) {
			if(o.getValueCoded()==yes){
				requiredObs.add(o);
				status = true;
			}
		}
		
		
		
	}
	ret.put(ptId, new BooleanResult(status, this, context));
}
return ret;
}
}

