package org.openmrs.module.kenyaemr.fragment.controller.field;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugInfo;
import org.openmrs.ui.framework.UiUtils;
import org.springframework.web.bind.annotation.RequestParam;

public class DrugInfoFragmentController {
	public JSONObject drugDetails(@RequestParam("drugNames") String drugCodes,
			UiUtils ui){
	JSONObject drugsInfoDetailsJson = new JSONObject();
	JSONArray drugsInfoDetailsJsonArray = new JSONArray();
	KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
	   for (String drugCode: drugCodes.split("/")){
		   JSONObject drugInfoDetailsJson = new JSONObject();
		   DrugInfo drugInfo=kenyaEmrService.getDrugInfo(drugCode);  
		   if(drugInfo!=null){
			   drugInfoDetailsJson.put("drugName", drugInfo.getDrugName());
			   drugInfoDetailsJson.put("toxicity", drugInfo.getToxicity());
			   drugInfoDetailsJson.put("riskFactor", drugInfo.getRiskFactor());
			   drugInfoDetailsJson.put("suggestedManagement", drugInfo.getSuggestedManagement());
			   drugInfoDetailsJson.put("drugInteraction", drugInfo.getDrugInteraction());
			   drugInfoDetailsJson.put("suggestedManagementInteraction", drugInfo.getSuggestedManagementInteraction());
				}
				else{
					drugInfoDetailsJson.put("drugName", "");
					drugInfoDetailsJson.put("toxicity", "");
					drugInfoDetailsJson.put("riskFactor", "");
					drugInfoDetailsJson.put("suggestedManagement", "");
					drugInfoDetailsJson.put("drugInteraction", "");
					drugInfoDetailsJson.put("suggestedManagementInteraction", "");
				}
		   drugsInfoDetailsJsonArray.add(drugInfoDetailsJson);
	   }
	   drugsInfoDetailsJson.put("drugsInfoDetailsJson", drugsInfoDetailsJsonArray);
	
	return drugsInfoDetailsJson;	
}

}
