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
	   for (String drugCode: drugCodes.split("-")){
		   JSONObject drugInfoDetailsJson = new JSONObject();
		   DrugInfo drugInfo=kenyaEmrService.getDrugInfo(drugCode);  
		   if(drugInfo!=null){
			   drugInfoDetailsJson.put("drugCode", drugInfo.getDrugCode());
			   drugInfoDetailsJson.put("adverseEffect", drugInfo.getAdverseEffect());
				}
				else{
					drugInfoDetailsJson.put("drugCode", "");
					drugInfoDetailsJson.put("adverseEffect", "");
				}
		   drugsInfoDetailsJsonArray.add(drugInfoDetailsJson);
	   }
	   drugsInfoDetailsJson.put("drugsInfoDetailsJson", drugsInfoDetailsJsonArray);
	
	return drugsInfoDetailsJson;	
}

}
