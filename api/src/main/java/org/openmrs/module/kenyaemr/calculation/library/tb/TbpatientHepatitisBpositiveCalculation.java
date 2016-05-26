package org.openmrs.module.kenyaemr.calculation.library.tb;



import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.TbConstants;
import org.openmrs.module.kenyaemr.calculation.BaseEmrCalculation;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.TbMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

public class TbpatientHepatitisBpositiveCalculation extends AbstractPatientCalculation {
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> stringObjectMap, PatientCalculationContext context) {

		Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);

		Set<Integer> alive = Filters.alive(cohort, context);
		Set<Integer> inTbProgram = Filters.inProgram(tbProgram, alive, context);
		
		Concept tbComorbidity = Dictionary.getConcept(Dictionary.COMORBIDITY_TB_ENROLL_FORM);
		Concept hepatitisb=  Dictionary.getConcept(Dictionary.HEPATITIS_B);
		

		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean hepatitisbpositive = false;

			// check if a patient is alive
			if (alive.contains(ptId)) {

				List<Obs> obs = Context.getObsService()
						.getObservationsByPersonAndConcept(Context.getPatientService().getPatient(ptId), tbComorbidity);
				List<Obs> requiredObs = new LinkedList<Obs>();
				
				for(Obs o : obs ) {
					if(o.getValueCoded()==hepatitisb){
						requiredObs.add(o);
						hepatitisbpositive = true;
					}
				}
				
				
				
			}
			ret.put(ptId, new BooleanResult(hepatitisbpositive, this, context));
		}
		return ret;
	}


}
