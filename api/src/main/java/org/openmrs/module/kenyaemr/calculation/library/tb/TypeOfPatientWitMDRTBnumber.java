package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

public class TypeOfPatientWitMDRTBnumber extends AbstractPatientCalculation{
	
	
	 
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean withMDRtbregNumber = false;
			Patient pat=Context.getPatientService().getPatient(ptId);
			Set<PatientIdentifier> patientIdentifier = pat.getIdentifiers();
			
		 	 
		 	
		 	for (PatientIdentifier p : patientIdentifier) {
		 		 
		 	if (p.getIdentifierType().getId() == 8)
		 		 {  
		 		 withMDRtbregNumber=true; 
		 		 
		 		 }
		 	  
		 	 
		 	}
		 	
			
			ret.put(ptId, new BooleanResult( withMDRtbregNumber, this, context));
		}
		return ret;
	}

}
