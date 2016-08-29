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
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyaemr.Dictionary;

public class TbpatientwithlosstoFollowupCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ 
		Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		Concept losstofolloupIR=Dictionary.getConcept(Dictionary.TREATMENT_AFTER_LOSS_FOLLOW_UP_IR);
		Concept losstofolloupRR=Dictionary.getConcept(Dictionary.TREATMENT_AFTER_LOSS_FOLLOW_UP_RR);
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			Patient patient = Context.getPatientService().getPatient(ptId);
			boolean onlosstofollowupCategory = false;
			List<Obs> obs = Context.getObsService()
					.getObservationsByPersonAndConcept(patient, registration_group);
			
			for (Obs o : obs) {
				if (o.getValueCoded() == losstofolloupIR|| o.getValueCoded() == losstofolloupRR) 
				{
					onlosstofollowupCategory = true;
				}
			}
		 	 
			
			ret.put(ptId, new BooleanResult(onlosstofollowupCategory , this, context));
		}
		return ret;
	}

}

