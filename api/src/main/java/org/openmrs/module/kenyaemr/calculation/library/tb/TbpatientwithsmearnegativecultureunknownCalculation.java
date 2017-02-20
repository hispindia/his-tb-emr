package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Program;
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

public class TbpatientwithsmearnegativecultureunknownCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ 
		Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);
		Set<Integer> inTbProgram = Filters.inProgram(tbProgram, cohort, context);
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		
		Concept labresult=Dictionary.getConcept(Dictionary.AFB_NOT_SEEN)	;	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		
		CalculationResultMap lastlabClassiffication = Calculations.lastObs(labtest, inTbProgram, context);
		
		CalculationResultMap lastcultureClassiffication = Calculations.lastObs(culturetest, inTbProgram, context);
		CalculationResultMap lastcultureliquidClassiffication = Calculations.lastObs(cultureliquidtest, inTbProgram, context);
	
		CalculationResultMap ret = new CalculationResultMap();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

		Date start = DateUtil.getStartOfMonth(context.getNow());

		Date endDate = context.getNow();
		for (Integer ptId : cohort) {
			
			boolean oncultureTest = false;
			ObsResult obsResultsClassification = (ObsResult) lastlabClassiffication.get(ptId);
			
			ObsResult obsResultsCulture = (ObsResult) lastcultureClassiffication.get(ptId);
			
			ObsResult obsResultLastCultureLiquidResults = (ObsResult) lastcultureliquidClassiffication.get(ptId);
			Date obssmear = null;
			Date reportstart = null;
			Date reportend = null;
			try {
				if ((obsResultsClassification != null)) {
				
					if(obsResultsCulture != null && obsResultsCulture.getValue().getValueCoded()==null)
					{ obssmear = sdf.parse(sdf
							.format(obsResultsClassification.getValue()
									.getObsDatetime()));
					reportstart = sdf.parse(sdf.format(start));
					reportend = sdf.parse(sdf.format(endDate));
							
					}
					else if(obsResultLastCultureLiquidResults!=null && obsResultLastCultureLiquidResults.getValue().getValueCoded()==null )
					{
						obssmear = sdf.parse(sdf
								.format(obsResultsClassification.getValue()
										.getObsDatetime()));
						reportstart = sdf.parse(sdf.format(start));
						reportend = sdf.parse(sdf.format(endDate));
					}
				}

			} catch (ParseException e) {

				e.printStackTrace();
			}
		if (obsResultsClassification != null)  {
			
				if(obsResultsCulture != null && obsResultsCulture.getValue().getValueCoded()==null)
				{ 
					if(obsResultsClassification.getValue().getValueCoded().equals(labresult))
						{ 
						if (obssmear.after(reportstart)&& obssmear.before(reportend)
							|| obssmear.equals(reportstart) || obssmear.equals(reportend))
						{ 
							oncultureTest = true;
						}
						}
						
				}
				else if(obsResultLastCultureLiquidResults!=null && obsResultLastCultureLiquidResults.getValue().getValueCoded()==null )
				{
					if((obsResultsClassification.getValue().getValueCoded().equals(labresult)))
					{ 
					if ((obssmear.after(reportstart)
							&& obssmear.before(reportend)
							|| obssmear.equals(reportstart) || obssmear
								.equals(reportend)))
					{ 
						oncultureTest = true;
					}
					}
				}
			
		
		}
	ret.put(ptId, new BooleanResult(oncultureTest , this, context));
		
		}
		 	 
			
			
		
		return ret;
	}
	
}

