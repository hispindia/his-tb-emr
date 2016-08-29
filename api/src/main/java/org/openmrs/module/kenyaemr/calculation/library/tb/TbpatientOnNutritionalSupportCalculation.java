package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.metadata.TbMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

public class TbpatientOnNutritionalSupportCalculation extends AbstractPatientCalculation {
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> stringObjectMap, PatientCalculationContext context) {

		Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);

		Set<Integer> alive = Filters.alive(cohort, context);
		Set<Integer> inTbProgram = Filters.inProgram(tbProgram, alive, context);
		
		Concept patientNutrionalSupport = Dictionary.getConcept(Dictionary.NUTRITIONAL_SUPPORT);
		Concept yes=  Dictionary.getConcept(Dictionary.YES);
		

		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean NutrionalSupport = false;

			// check if a patient is alive
			if (alive.contains(ptId)) {

				List<Obs> obs = Context.getObsService()
						.getObservationsByPersonAndConcept(Context.getPatientService().getPatient(ptId), patientNutrionalSupport);
				
				
				for(Obs o : obs ) {
					if(o.getValueCoded()==yes){
						
						NutrionalSupport = true;
					}
				}
				
				
				
			}
			ret.put(ptId, new BooleanResult(NutrionalSupport, this, context));
		}
		return ret;
	}


}
