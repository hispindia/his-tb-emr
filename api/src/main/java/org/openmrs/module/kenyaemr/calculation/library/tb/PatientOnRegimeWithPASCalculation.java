package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

public class PatientOnRegimeWithPASCalculation extends AbstractPatientCalculation{
	
	
	 
		@Override
		public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
		{
			CalculationResultMap ret = new CalculationResultMap();
			for (Integer ptId : cohort) {
				boolean onRegimewithPAS = false;
				 KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
			 	  List<DrugOrderProcessed> drugorderprocess= kenyaEmrService.getPatientWithPASregime() ;
			 	 
			 	  for(DrugOrderProcessed order: drugorderprocess)
			 	  {
			 		 
			 	  if((ptId.equals(order.getPatient().getPatientId())))
			 		 {  
			 		 onRegimewithPAS=true; 
			 		 
			 		 }
			 	  
			 	 
			 	  }
			 	 
				
				ret.put(ptId, new BooleanResult(onRegimewithPAS, this, context));
			}
			return ret;
		}

}
