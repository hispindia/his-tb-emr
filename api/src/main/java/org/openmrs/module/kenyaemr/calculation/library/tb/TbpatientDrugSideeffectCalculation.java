package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.regimen.RegimenChange;
import org.openmrs.module.kenyaemr.regimen.RegimenChangeHistory;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.regimen.RegimenOrder;
import org.openmrs.ui.framework.annotation.SpringBean;

public class TbpatientDrugSideeffectCalculation extends AbstractPatientCalculation
{

/**
* @see org.openmrs.module.kenyacore.calculation.PatientFlagCalculation#getFlagMessage()
*/


/**
* @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection,
*      java.util.Map,
*      org.openmrs.calculation.patient.PatientCalculationContext)
* @should determine whether patients need culture test
*/


@Override

public CalculationResultMap evaluate(Collection<Integer> cohort,
	Map<String, Object> parameterValues,
	PatientCalculationContext context) {
	
	
	
//Get all patients who are alive and in TB program
Set<Integer> alive = Filters.alive(cohort, context);



//Get concepts
Concept drugsideefffects = Dictionary
		.getConcept(Dictionary.DRUG_SIDE_EFFECTS);
Concept dermaitis = Dictionary.getConcept(Dictionary.DERMAT_TIS);
Concept maculo = Dictionary.getConcept(Dictionary.MACULO_ERUPTION);
Concept photo = Dictionary.getConcept(Dictionary.PHOTO_SENSITIVE);
Concept flushin = Dictionary.getConcept(Dictionary.FLUSHIN_RECTION);
Concept texure = Dictionary.getConcept(Dictionary.CHANGES_SKIN_TEXTURE);
Concept colour = Dictionary.getConcept(Dictionary.CHANGES_SKIN_COLOUR);
Concept fungal = Dictionary.getConcept(Dictionary.FUNGAL_INFECTION);
Concept leuko = Dictionary.getConcept(Dictionary.LEUKO_PANEIA);
Concept thrmbo = Dictionary.getConcept(Dictionary.THROMBO_CYTOPENIA);
Concept hyper = Dictionary.getConcept(Dictionary.HYPER_GLYCAEMIA);
Concept dys = Dictionary.getConcept(Dictionary.DYS_AND_HYPER_GLYCAEMIA);
Concept lacitic = Dictionary.getConcept(Dictionary.LACITIC_ACIDIOSIS);
Concept hypothy = Dictionary.getConcept(Dictionary.HYPO_THYROID);
Concept gyno = Dictionary.getConcept(Dictionary.GYNO_COMASTIA);
Concept alo = Dictionary.getConcept(Dictionary.ALO_PECIA);
Concept elevated = Dictionary.getConcept(Dictionary.ELEVATED_LIVER_ENZYMES);
Concept jaun = Dictionary.getConcept(Dictionary.JAUN_DICE);
Concept loss = Dictionary.getConcept(Dictionary.LOSS_OF_APETITE);
Concept nausea = Dictionary.getConcept(Dictionary.NAUSEA_VOMITING);
Concept gast = Dictionary.getConcept(Dictionary.GASTR_ITIS);
Concept diar = Dictionary.getConcept(Dictionary.DIA_RROHEA);
Concept abdom = Dictionary.getConcept(Dictionary.ABDOMINAL_PAIN);
Concept abnormal = Dictionary.getConcept(Dictionary.ABNORMAL_METALLIC_TASTE);
Concept artha = Dictionary.getConcept(Dictionary.ARTHA_LGIA);
Concept myal= Dictionary.getConcept(Dictionary.MYAL_LGIA);
Concept tendo = Dictionary.getConcept(Dictionary.TENDO_NITIS);
Concept gout = Dictionary.getConcept(Dictionary.GOUT_URIC);
Concept electro = Dictionary.getConcept(Dictionary.ELECTRO_DISTRBANCE);
Concept renal = Dictionary.getConcept(Dictionary.RENAL_FAILURE);
Concept nephro = Dictionary.getConcept(Dictionary.NEPHRO_TOXICITY);
Concept hearing = Dictionary.getConcept(Dictionary.HEARING_LOSS);
Concept peripheral = Dictionary.getConcept(Dictionary.PERIPHERAL_NEURO);
Concept psycho = Dictionary.getConcept(Dictionary.PSYCHOTIC_SYMPTOM);
Concept seiz = Dictionary.getConcept(Dictionary.SEI_ZURES);
Concept suici = Dictionary.getConcept(Dictionary.SUIC_IDAL);
Concept ana = Dictionary.getConcept(Dictionary.ANA_PHYL);
Concept hypersyn = Dictionary.getConcept(Dictionary.HYPER_SYNDROME);
Concept optic = Dictionary.getConcept(Dictionary.OPTIC_NEURITIS);
Concept visual = Dictionary.getConcept(Dictionary.VISUAL_DISTURBANCE);
Concept retro = Dictionary.getConcept(Dictionary.RETRO_NEUTRITIS);
Concept qt = Dictionary.getConcept(Dictionary.QT_PROLONGTON);
Concept anaemia = Dictionary.getConcept(Dictionary.ANAEMIA_CELL);
Concept hept = Dictionary.getConcept(Dictionary.HEPT_ATIS);
Concept arth = Dictionary.getConcept(Dictionary.ARTH_RATIS);
Concept vesti = Dictionary.getConcept(Dictionary.VESTI_TOXICITY);
Concept sleep = Dictionary.getConcept(Dictionary.SLEEP_DISTURB);
Concept head = Dictionary.getConcept(Dictionary.HEAD_ACHE);
Concept dep = Dictionary.getConcept(Dictionary.DEP_RESSION);




CalculationResultMap ret = new CalculationResultMap();


for (Integer ptId : cohort) {
	boolean drugsideffect = false;
	boolean base = false;
	boolean sideeffect=false;
	// check if a patient is alive
	if (alive.contains(ptId)) {
		Concept masterSet = Context.getConceptService().getConceptByUuid("160021AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		RegimenChangeHistory history = RegimenChangeHistory.forPatient(Context.getPatientService().getPatient(ptId), masterSet);
		RegimenChange lastChange = history.getLastChange();
		RegimenOrder baseline = lastChange != null ? lastChange.getStarted() : null;
		if(baseline!=null)
		{
			base=true;
		}
		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(Context.getPatientService().getPatient(ptId), drugsideefffects );
		List<Obs> requiredObs = new LinkedList<Obs>();
		
		for(Obs o : obs ) {
			if(o.getValueCoded()==dermaitis||o.getValueCoded()==maculo||o.getValueCoded()==photo||o.getValueCoded()==flushin||
			   o.getValueCoded()==texure||o.getValueCoded()==colour||o.getValueCoded()==fungal||o.getValueCoded()==leuko||
			   o.getValueCoded()==thrmbo||o.getValueCoded()==hyper||o.getValueCoded()==dys||o.getValueCoded()==lacitic||
			   o.getValueCoded()==hypothy||o.getValueCoded()==gyno||o.getValueCoded()==alo||o.getValueCoded()==elevated||
			   o.getValueCoded()==jaun||o.getValueCoded()==loss||o.getValueCoded()==nausea||o.getValueCoded()==gast||
			   o.getValueCoded()==diar||o.getValueCoded()==abdom||o.getValueCoded()==abnormal||o.getValueCoded()==artha||
			   o.getValueCoded()==myal||o.getValueCoded()==tendo||o.getValueCoded()==gout||o.getValueCoded()==electro||
			   o.getValueCoded()==renal||o.getValueCoded()==nephro||o.getValueCoded()==hearing||o.getValueCoded()==peripheral||
			   o.getValueCoded()==psycho||o.getValueCoded()==seiz||o.getValueCoded()==suici||o.getValueCoded()==ana||
			   o.getValueCoded()==hypersyn||o.getValueCoded()==optic||o.getValueCoded()==visual||o.getValueCoded()==retro||
			   o.getValueCoded()==qt||o.getValueCoded()==anaemia||o.getValueCoded()==hept||o.getValueCoded()==arth||
			   o.getValueCoded()==vesti||o.getValueCoded()==sleep||o.getValueCoded()==head||o.getValueCoded()==dep)
			{
				requiredObs.add(o);
				drugsideffect = true;
			}
		}
		if(base==true && drugsideffect==true)
		{
			sideeffect=true;
		}
		
		
	}
	ret.put(ptId, new BooleanResult(sideeffect, this, context));
}
return ret;
}
}



