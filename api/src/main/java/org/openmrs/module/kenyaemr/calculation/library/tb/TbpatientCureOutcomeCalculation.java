package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import org.openmrs.module.reporting.common.DateUtil;

public class TbpatientCureOutcomeCalculation extends AbstractPatientCalculation {
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort,
			Map<String, Object> params, PatientCalculationContext context) {
		Concept tboutcome = Dictionary
				.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept cureoutcomresult = Dictionary
				.getConcept(Dictionary.CURE_OUTCOME);
		CalculationResultMap ret = new CalculationResultMap();
		CalculationResultMap lastoutcomeClassiffication = Calculations.lastObs(
				tboutcome, cohort, context);
		for (Integer ptId : cohort) {
			boolean onCureOutcome = false;
			ObsResult obsResultsClassification = (ObsResult) lastoutcomeClassiffication
					.get(ptId);
			
			if (obsResultsClassification != null
					&& obsResultsClassification.getValue().getValueCoded() != null) {
				if(obsResultsClassification.getValue().getEncounter().getVisit()!=null)
				{if(obsResultsClassification.getValue().getEncounter().getVisit().getStopDatetime()==null)
				{
				if (obsResultsClassification.getValue().getValueCoded()
						.equals(cureoutcomresult)) {

					onCureOutcome = true;

				}
				}
				}
			}
			ret.put(ptId, new BooleanResult(onCureOutcome, this, context));

		}
		return ret;
	}
}