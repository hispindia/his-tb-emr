package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;

public class TbpatientonMedication6mnthsagoCalculation  extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			int count=0;
			boolean onVisit = false;	
			Patient patient=Context.getPatientService().getPatient(ptId);
			List<Order> order=Context.getOrderService().getOrdersByPatient(patient);
			 for(Order ordd: order){
				 
		 		if(ordd.getPatient().getId().equals(ptId))
				{  //SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		 			
		 	int mnths=((new Date().getYear())-( ordd.getStartDate().getYear()))*12+(new Date().getMonth()- ordd.getStartDate().getMonth());
		 			
		 			
		 			
		 			if(mnths==6)
		 			{
		 				onVisit=true;
		 				
		 				
		 			}
		 			
		 			
		 			
		 			
				}
				}
			 
			 ret.put(ptId, new BooleanResult(onVisit, this, context));
			 
			 }
			
			
		
		return ret;
	}

}
