package org.openmrs.module.kenyaemr.fragment.controller.field;

import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugInfo;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.springframework.web.bind.annotation.RequestParam;

public class DrugInfoFragmentController {
	public SimpleObject drugDetails(@RequestParam("drugName") String drugName,
			UiUtils ui){
	KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
	DrugInfo drugInfo=kenyaEmrService.getDrugInfo(drugName);
	SimpleObject simplePatientt= new SimpleObject();
	if(drugInfo!=null){
	simplePatientt.put("drugName", drugInfo.getDrugName());
	simplePatientt.put("toxicity", drugInfo.getToxicity());
	simplePatientt.put("riskFactor", drugInfo.getRiskFactor());
	simplePatientt.put("suggestedManagement", drugInfo.getSuggestedManagement());
	simplePatientt.put("drugInteraction", drugInfo.getDrugInteraction());
	simplePatientt.put("suggestedManagementInteraction", drugInfo.getSuggestedManagementInteraction());
	}
	else{
		simplePatientt.put("drugName", "");
		simplePatientt.put("toxicity", "");
		simplePatientt.put("riskFactor", "");
		simplePatientt.put("suggestedManagement", "");
		simplePatientt.put("drugInteraction", "");
		simplePatientt.put("suggestedManagementInteraction", "");
	}
	return simplePatientt;	
}

}
