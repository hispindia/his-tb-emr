package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
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

public class TbpatientwithsmearnegativeCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ 
		Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);
		Set<Integer> inTbProgram = Filters.inProgram(tbProgram, cohort, context);
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept labresult=Dictionary.getConcept(Dictionary.AFB_NOT_SEEN)	;	
		Concept cultureresult=Dictionary.getConcept(Dictionary.POSITIVE)	;	
		Concept solidresultcolonies=Dictionary.getConcept(Dictionary.COLONIES)	;	
		Concept solidresultsinglepositive=Dictionary.getConcept(Dictionary.SINGLE_POSITIVE)	;	
		Concept solidresultdoublepositive=Dictionary.getConcept(Dictionary.DOUBLE_POSITIVE)	;	
		Concept solidresulttriplepositive=Dictionary.getConcept(Dictionary.TRIPLE_POSITIVE)	;	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		
		CalculationResultMap lastlabClassiffication = Calculations.lastObs(labtest, inTbProgram, context);
		
		CalculationResultMap lastcultureClassiffication = Calculations.lastObs(culturetest, inTbProgram, context);
		CalculationResultMap lastcultureliquidClassiffication = Calculations.lastObs(cultureliquidtest, inTbProgram, context);
	
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			
			boolean oncultureTest = false;
			ObsResult obsResultsClassification = (ObsResult) lastlabClassiffication.get(ptId);
			
			ObsResult obsResultsCulture = (ObsResult) lastcultureClassiffication.get(ptId);
			
			ObsResult obsResultLastCultureLiquidResults = (ObsResult) lastcultureliquidClassiffication.get(ptId);
			
		if ((obsResultsClassification != null))  {
			
			if(obsResultsCulture != null)
			{ if(!(obsResultsClassification.getValue().getValueCoded()==null)&& (!(obsResultsCulture.getValue().getValueCoded()==null)))
				{if ((obsResultsClassification.getValue().getValueCoded().equals(labresult))
						&& ((obsResultsCulture.getValue().getValueCoded().equals(solidresultcolonies)||
								obsResultsCulture.getValue().getValueCoded().equals(solidresultsinglepositive)||
								obsResultsCulture.getValue().getValueCoded().equals(solidresultdoublepositive)||
								obsResultsCulture.getValue().getValueCoded().equals(solidresulttriplepositive))))
				{
					
					oncultureTest = true;
					
				}
				}
			}
			if(obsResultLastCultureLiquidResults!=null)
			{if(!(obsResultsClassification.getValue().getValueCoded()==null)&& (!(obsResultLastCultureLiquidResults.getValue().getValueCoded()==null)))
				{if((obsResultsClassification.getValue().getValueCoded().equals(labresult))
						&& (((obsResultLastCultureLiquidResults.getValue().getValueCoded().equals(cultureresult)))))
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

