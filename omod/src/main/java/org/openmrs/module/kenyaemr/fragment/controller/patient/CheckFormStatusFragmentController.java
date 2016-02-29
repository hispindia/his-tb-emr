package org.openmrs.module.kenyaemr.fragment.controller.patient;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.SimpleObject;
import org.springframework.web.bind.annotation.RequestParam;

public class CheckFormStatusFragmentController {
	public SimpleObject checkStatus(@RequestParam("patient") Patient patient,
			HttpServletRequest request) {
		KenyaEmrService kenyaEmrService = (KenyaEmrService) Context
				.getService(KenyaEmrService.class);
		List<Encounter> encounterList = Context.getEncounterService()
				.getEncounters(patient);
		
		SimpleObject statusForm= new SimpleObject();
		Boolean flag=false;
		for (Encounter en : encounterList) {
			if(en.getForm()!=null){
				if (en.getForm().getUuid()
						.equals("8e4e1abf-7c08-4ba8-b6d8-19a9f1ccb6c9")
						|| en.getForm().getUuid()
								.equals("7efa0ee0-6617-4cd7-8310-9f95dfee7a82")
						|| en.getForm().getUuid()
								.equals("5286ae88-85bb-46e8-a2f7-6361f463ffd4")
						|| en.getForm().getUuid()
								.equals("d1db31d0-b415-4788-a233-e4000bf4d108")) {
					flag= true;
				}
			}
		}
	statusForm.put("flag", flag);
	return statusForm;
	}
}
