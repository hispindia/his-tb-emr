package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import org.openmrs.module.reporting.common.DateUtil;


public class TbpatientwithDiedOutcomeAt6mnthCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ 	Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
	Concept outcomresult=Dictionary.getConcept(Dictionary.DIED);
		CalculationResultMap ret = new CalculationResultMap();
		SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yy");
		
		Date start = DateUtil.getStartOfMonth(context.getNow());
		Calendar calendar = Calendar.getInstance();
		Date endDate = context.getNow(); 
		calendar.setTime(start);
		calendar.add(Calendar.MONTH, -6); 
		Date startDate = calendar.getTime();
		for (Integer ptId : cohort) {
			Patient patient = Context.getPatientService().getPatient(ptId);
			boolean onDiedOutcome = false;
			List<Obs> obs = Context.getObsService()
					.getObservationsByPersonAndConcept(patient, tboutcome);
			
			for (Obs o : obs) {Date ord=null;Date reportstart=null;Date reportend=null; Date monthStart=null;
			try {
				ord=sdf.parse(sdf.format(o.getObsDatetime()));
				reportstart=sdf.parse(sdf.format(start));
				reportend=sdf.parse(sdf.format(endDate));
				monthStart=sdf.parse(sdf.format(startDate));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
				if (o.getValueCoded() == outcomresult) 
				{
					if( ord.after(monthStart) && ord.before(reportend)||ord.equals(monthStart) ||ord.equals(reportend))
					{ 
						onDiedOutcome = true;
				    }
				}
			
			}
		 	 
			
			ret.put(ptId, new BooleanResult(onDiedOutcome , this, context));
		}
		return ret;
	}

}

