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
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.reporting.common.DateUtil;
public class TbpatientwithDiedOutcomeAt12to15mnthCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ 	Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
	Concept outcomresult=Dictionary.getConcept(Dictionary.DIED);
		CalculationResultMap ret = new CalculationResultMap();
		CalculationResultMap lastoutcomeClassiffication = Calculations.lastObs(
				tboutcome, cohort, context);
		SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yy");
       
		Date start = DateUtil.getStartOfMonth(context.getNow());

		Date endDate = context.getNow();
		for (Integer ptId : cohort) {
			Patient patient = Context.getPatientService().getPatient(ptId);
			boolean onDiedOutcome = false;
			Date ord=null;Date reportstart=null;Date reportend=null;Date monthStart=null;
			ObsResult obsResultsClassification = (ObsResult) lastoutcomeClassiffication
					.get(ptId);
			if (obsResultsClassification != null
					&& obsResultsClassification.getValue().getValueCoded() != null) {
				if(obsResultsClassification.getValue().getEncounter().getVisit()!=null)
				{if(obsResultsClassification.getValue().getEncounter().getVisit().getStopDatetime()==null)
				{			 		
				try {	
					ord=sdf.parse(sdf.format(obsResultsClassification.getValue().getObsDatetime()));
					reportstart = sdf.parse(sdf.format(start));
					reportend = sdf.parse(sdf.format(endDate));
					
					
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				if (obsResultsClassification.getValue().getValueCoded()
						.equals(outcomresult)) {

					{ if(ord.after(reportstart) && ord.before(reportend)||ord.equals(reportstart) ||ord.equals(reportend))
						
					{ 
					onDiedOutcome = true;
					}
				}

				}
				}
				}
			}
			ret.put(ptId, new BooleanResult(onDiedOutcome , this, context));
		}
		return ret;
	}

}



