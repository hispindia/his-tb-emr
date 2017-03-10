package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.reporting.common.DateUtil;

public class TotalPatientOnMedicationCalculation extends AbstractPatientCalculation {

	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 * @should return null for patients who have never started ARVs
	 * @should return null for patients who aren't currently on ARVs
	 * @should return whether patients have changed regimens
	 */
	@Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> arg1, PatientCalculationContext context) {
		SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yy");
		Date start = DateUtil.getStartOfMonth(context.getNow());
		Date endDate = context.getNow(); 
		
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean onMedication = false;
			Patient patient=Context.getPatientService().getPatient(ptId);
			List<Order> order=Context.getOrderService().getOrdersByPatient(patient);
		
			 for(Order orderdrug: order){
				
		 		if(orderdrug.getPatient().getId().equals(ptId))
				{ 
		 			Date ord=null;Date reportstart=null;Date reportend=null;
					try {
						ord=sdf.parse(sdf.format(orderdrug.getStartDate()));
						reportstart=sdf.parse(sdf.format(start));
						reportend=sdf.parse(sdf.format(endDate));
						
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
		if(ord.after(reportstart) && ord.before(reportend) ||ord.equals(reportstart) ||ord.equals(reportend))
					{
			           onMedication=true;
					}
				}
			 
			 }
			
			ret.put(ptId, new BooleanResult(onMedication, this, context));
		}
		return ret;
    }

	
}