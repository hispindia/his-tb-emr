package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyaemr.Dictionary;


public class TbpatientwithDiedOutcomeAt6mnthCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ 	Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
	Concept outcomresult=Dictionary.getConcept(Dictionary.DIED);
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			Patient patient = Context.getPatientService().getPatient(ptId);
			boolean onDiedOutcome = false;
			List<Obs> obs = Context.getObsService()
					.getObservationsByPersonAndConcept(patient, tboutcome);
			
			for (Obs o : obs) {
				if (o.getValueCoded() == outcomresult) 
				{
					onDiedOutcome = true;
				}
			}
		 	 
			
			ret.put(ptId, new BooleanResult(onDiedOutcome , this, context));
		}
		return ret;
	}

}

