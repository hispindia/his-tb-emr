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

public class TbpatientswithNewCategoryCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
	Concept newpat=Dictionary.getConcept(Dictionary.NEW_PATIENT);
	Concept newmdr=Dictionary.getConcept(Dictionary.NEW_MDR);
	Concept newplhiv=Dictionary.getConcept(Dictionary.NEW_PLHIV);
	CalculationResultMap ret = new CalculationResultMap();
	CalculationResultMap lastoutcomeClassiffication = Calculations.lastObs(
			registration_group, cohort, context);
		for (Integer ptId : cohort) {
			ObsResult obsResultsClassification = (ObsResult) lastoutcomeClassiffication
					.get(ptId);
			boolean onNewCategory = false;
			if (obsResultsClassification != null
					&& obsResultsClassification.getValue().getValueCoded() != null) {

				if (obsResultsClassification.getValue().getValueCoded().equals(newpat) ||
					obsResultsClassification.getValue().getValueCoded().equals(newmdr)||
					obsResultsClassification.getValue().getValueCoded().equals(newplhiv)) {

					onNewCategory = true;

				}

			}
		 	 
			
			ret.put(ptId, new BooleanResult(onNewCategory , this, context));
		}
		return ret;
	}

}

