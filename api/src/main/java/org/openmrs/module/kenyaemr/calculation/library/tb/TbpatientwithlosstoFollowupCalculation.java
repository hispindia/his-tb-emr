package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyaemr.Dictionary;

public class TbpatientwithlosstoFollowupCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ 
		Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		Concept losstofolloupIR=Dictionary.getConcept(Dictionary.TREATMENT_AFTER_LOSS_FOLLOW_UP_IR);
		Concept losstofolloupRR=Dictionary.getConcept(Dictionary.TREATMENT_AFTER_LOSS_FOLLOW_UP_RR);
		CalculationResultMap ret = new CalculationResultMap();
		CalculationResultMap lastoutcomeClassiffication = Calculations.lastObs(
				registration_group, cohort, context);
		for (Integer ptId : cohort) {
			boolean onlosstofollowupCategory = false;
			ObsResult obsResultsClassification = (ObsResult) lastoutcomeClassiffication
					.get(ptId);
			if (obsResultsClassification != null
					&& obsResultsClassification.getValue().getValueCoded() != null) {

				if (obsResultsClassification.getValue().getValueCoded()
						.equals(losstofolloupIR)||obsResultsClassification.getValue().getValueCoded()
						.equals(losstofolloupRR)) {

					onlosstofollowupCategory = true;

				}

			}
		 	 
			ret.put(ptId, new BooleanResult(onlosstofollowupCategory , this, context));
		}
		return ret;
	}

}

