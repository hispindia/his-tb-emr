package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.openmrs.module.reporting.common.DateUtil;



public class Tbpatients24monthsagoCalculation  extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{
		CalculationResultMap ret = new CalculationResultMap();
		SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yy");
		
		Date start = DateUtil.getStartOfMonth(context.getNow());
		Calendar calendar = Calendar.getInstance();
		Date endDate = context.getNow(); 
		calendar.setTime(start);
		calendar.add(Calendar.MONTH, -24); 
		Date startDate = calendar.getTime();
		for (Integer ptId : cohort) {
			int count=0;
			boolean onVisit = false;	boolean onMonth = false;
			Patient patient=Context.getPatientService().getPatient(ptId);
			List<Order> order = Context.getOrderService().getOrdersByPatient(
					patient);
			//List<Visit>v=Context.getVisitService().getVisitsByPatient(patient);
			 for(Order ordd : order){
				 
		 		if(ordd.getPatient().getId().equals(ptId))
				{  //SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		 			Date visitDate=null;Date reportstart=null;Date reportend=null;
		 			try {
		 				visitDate=sdf.parse(sdf.format(ordd.getStartDate()));
						reportstart=sdf.parse(sdf.format(startDate));
						reportend=sdf.parse(sdf.format(endDate));
						
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
		 			if(visitDate.after(reportstart) && visitDate.before(reportend) ||visitDate.equals(reportstart) ||visitDate.equals(reportend))
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
