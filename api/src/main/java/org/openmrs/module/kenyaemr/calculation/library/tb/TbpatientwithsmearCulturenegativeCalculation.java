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

public class TbpatientwithsmearCulturenegativeCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ 
		Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);
		Set<Integer> inTbProgram = Filters.inProgram(tbProgram, cohort, context);
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept cultureresult=Dictionary.getConcept(Dictionary.NEGATIVE)	;	
		Concept smearresult=Dictionary.getConcept(Dictionary.AFB_NOT_SEEN)	;	
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
				{if ((obsResultsClassification.getValue().getValueCoded().equals(smearresult))
						&& (((obsResultsCulture.getValue().getValueCoded().equals(cultureresult)))))
				{
					
					oncultureTest = true;
					
				}
				}
			}
			if(obsResultLastCultureLiquidResults!=null)
			{ if(!(obsResultsClassification.getValue().getValueCoded()==null)&& (!(obsResultLastCultureLiquidResults.getValue().getValueCoded()==null)))
				
				{
				if((obsResultsClassification.getValue().getValueCoded().equals(smearresult))
				
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

