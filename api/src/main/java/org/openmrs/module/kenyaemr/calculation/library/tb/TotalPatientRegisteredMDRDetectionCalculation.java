package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.metadata.TbMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.DateUtil;

public class TotalPatientRegisteredMDRDetectionCalculation extends AbstractPatientCalculation {

	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 * @should return null for patients who have never started ARVs
	 * @should return null for patients who aren't currently on ARVs
	 * @should return whether patients have changed regimens
	 */
	@Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> arg1, PatientCalculationContext context) {

		
		CalculationResultMap ret = new CalculationResultMap();
       Concept patientregistered=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yy");
		Date start = DateUtil.getStartOfMonth(context.getNow());
		Date endDate = context.getNow(); 
		for (Integer ptId : cohort) {
			boolean onEnrolled = false;
			 Patient  patient=Context.getPatientService().getPatient(ptId);
			 Set <PatientIdentifier> patients=patient.getIdentifiers();
			 CalculationResultMap lastClassiffication = Calculations.lastObs(patientregistered, cohort, context);
			 for(PatientIdentifier pat: patients){Date ord=null;Date reportstart=null;Date reportend=null;
				 ObsResult obsResultsClassification = (ObsResult) lastClassiffication.get(ptId);
				 if(obsResultsClassification!=null)
					{ 
					try {
						ord=sdf.parse(sdf.format(obsResultsClassification.getValue().getObsDatetime()));
						reportstart=sdf.parse(sdf.format(start));
						reportend=sdf.parse(sdf.format(endDate));
						
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
			
					  
					  } 
		 		if(pat.getIdentifierType().getId()==8)
				{    if((obsResultsClassification!=null) &&(!(obsResultsClassification.getValue().getObsDatetime()==null)) )
				     { if ((ord.after(reportstart)&& ord.before(reportend)|| ord.equals(reportstart) || ord.equals(reportend)))
				    		 {
				    	 onEnrolled = true;
				    		 }
				      }
				}
			 
			 }
			
			ret.put(ptId, new BooleanResult(onEnrolled, this, context));
		}
		return ret;
    }

	
}
