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

public class TbpatientDefaultedOutcomeCalculation extends
AbstractPatientCalculation {
@Override
public CalculationResultMap evaluate(Collection<Integer> cohort,
	Map<String, Object> params, PatientCalculationContext context) {
Concept tboutcome = Dictionary
		.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
Concept Defaultedoutcomresult = Dictionary.getConcept(Dictionary.LOSS_TO_FOLLOW_UP);
CalculationResultMap ret = new CalculationResultMap();
CalculationResultMap lastoutcomeClassiffication = Calculations.lastObs(
		tboutcome, cohort, context);
for (Integer ptId : cohort) {
	boolean ondefaultedOutcome = false;
	ObsResult obsResultsClassification = (ObsResult) lastoutcomeClassiffication
			.get(ptId);
	if (obsResultsClassification != null
			&& obsResultsClassification.getValue().getValueCoded() != null) {

	if (obsResultsClassification.getValue().getValueCoded()
				.equals(Defaultedoutcomresult)) {
		System.out.println("defgautl");
			ondefaultedOutcome = true;

		}

	}
	ret.put(ptId, new BooleanResult(ondefaultedOutcome, this, context));

}
return ret;
}
}