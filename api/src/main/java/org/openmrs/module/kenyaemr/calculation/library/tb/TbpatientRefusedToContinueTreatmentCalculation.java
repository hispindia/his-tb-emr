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

public class TbpatientRefusedToContinueTreatmentCalculation extends AbstractPatientCalculation
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
Concept reasonnoteval = Dictionary.getConcept(Dictionary.REASON_NOT_EVALUATED_TREATMENT_STOPPED);
Concept reason = Dictionary.getConcept(Dictionary.PATIENT_DECISION_TO_STOP);
Concept treatmentoutcome = Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
Concept reasonOutcome = Dictionary.getConcept(Dictionary.TREATMENT_STOPPED);

CalculationResultMap ret = new CalculationResultMap();

for (Integer ptId : cohort) {
	boolean refusetoContinue = false;
	boolean outcomeValue = false;
	// check if a patient is alive
	if (alive.contains(ptId)) {

		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(Context.getPatientService().getPatient(ptId), treatmentoutcome );
		List<Obs> obstwo = Context.getObsService()
				.getObservationsByPersonAndConcept(Context.getPatientService().getPatient(ptId), reasonnoteval );
		
		for(Obs o : obs ) {
			if(o.getValueCoded()==reasonOutcome)
			{
				
				refusetoContinue = true;
			}
		}
		if(refusetoContinue = true)
		{
			for(Obs o : obstwo ) {
				if(o.getValueCoded()==reason){
					
					outcomeValue = true;
				}
			}
		}
		
		
	}
	ret.put(ptId, new BooleanResult(outcomeValue, this, context));
}
return ret;
}
}



