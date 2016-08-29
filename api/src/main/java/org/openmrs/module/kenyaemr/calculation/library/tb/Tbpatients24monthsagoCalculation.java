package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;



public class Tbpatients24monthsagoCalculation  extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			int count=0;
			boolean onVisit = false;	boolean onMonth = false;
			Patient patient=Context.getPatientService().getPatient(ptId);
			
			List<Visit>v=Context.getVisitService().getVisitsByPatient(patient);
			 for(Visit vis: v){
				 
		 		if(vis.getPatient().getId().equals(ptId))
				{  //SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		 			
		 	int mnths=((new Date().getYear())-( vis.getStartDatetime().getYear()))*12+(new Date().getMonth()- vis.getStartDatetime().getMonth());
		 			
		 			
		 			
		 			if(mnths<=24)
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
