package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyaemr.Dictionary;

public class TbpatientFailureOutcomeCalculation extends
AbstractPatientCalculation {
@Override
public CalculationResultMap evaluate(Collection<Integer> cohort,
	Map<String, Object> params, PatientCalculationContext context) {
Concept tboutcome = Dictionary
		.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
Concept failureoutcomresult = Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_FAILURE);
CalculationResultMap ret = new CalculationResultMap();
CalculationResultMap lastoutcomeClassiffication = Calculations.lastObs(
		tboutcome, cohort, context);
for (Integer ptId : cohort) {
	boolean onfailureOutcome = false;
	ObsResult obsResultsClassification = (ObsResult) lastoutcomeClassiffication
			.get(ptId);
	if (obsResultsClassification != null
			&& obsResultsClassification.getValue().getValueCoded() != null) {
		if(obsResultsClassification.getValue().getEncounter().getVisit()!=null)
		{if(obsResultsClassification.getValue().getEncounter().getVisit().getStopDatetime()==null)
		{
		if (obsResultsClassification.getValue().getValueCoded()
				.equals(failureoutcomresult)) {

			onfailureOutcome = true;

		}
		}
		}
	}
	ret.put(ptId, new BooleanResult(onfailureOutcome, this, context));

}
return ret;
}
}