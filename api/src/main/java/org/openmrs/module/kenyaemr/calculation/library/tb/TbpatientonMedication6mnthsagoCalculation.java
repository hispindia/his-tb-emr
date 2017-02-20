package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.openmrs.module.reporting.common.DateUtil;

public class TbpatientonMedication6mnthsagoCalculation extends
		AbstractPatientCalculation {
	
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort,
			Map<String, Object> params, PatientCalculationContext context) {
		
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) { 
			int count = 0;
			boolean onVisit = false;
			Patient patient = Context.getPatientService().getPatient(ptId);
			List<Order> order = Context.getOrderService().getOrdersByPatient(
					patient);
			SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yy");
			
			Date start = DateUtil.getStartOfMonth(context.getNow());
			Calendar calendar = Calendar.getInstance();
			Date endDate = context.getNow(); 
			calendar.setTime(start);
			calendar.add(Calendar.MONTH, -6); 
			Date startDate = calendar.getTime();
			
			
			for (Order ordd : order) {

				if (ordd.getPatient().getId().equals(ptId)) { 
          
				Date ord=null;Date reportstart=null;Date reportend=null;
						try {
							ord=sdf.parse(sdf.format(ordd.getStartDate()));
							reportstart=sdf.parse(sdf.format(startDate));
							reportend=sdf.parse(sdf.format(endDate));
							
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
				
					if(ord.after(reportstart) && ord.before(reportend) ||ord.equals(reportstart) ||ord.equals(reportend))
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
