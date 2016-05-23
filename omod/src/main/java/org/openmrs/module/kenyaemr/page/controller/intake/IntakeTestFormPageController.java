/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.page.controller.intake;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.kenyacore.form.FormDescriptor;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyacore.program.ProgramDescriptor;
import org.openmrs.module.kenyacore.program.ProgramManager;
import org.openmrs.module.kenyaemr.EmrConstants;
import org.openmrs.module.kenyaemr.EmrWebConstants;
import org.openmrs.module.kenyaemr.page.controller.clinician.ClinicianViewPatientPageController;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.session.Session;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Homepage for the intake app
 */
@AppPage(EmrConstants.APP_INTAKE)
public class IntakeTestFormPageController {
	protected final Log log = LogFactory.getLog(ClinicianViewPatientPageController.class);
	
	public void controller(@RequestParam("patientId") Patient patient,
	        @RequestParam(required = false, value = "formUuid") String formUuid,
	        Session session,
	        UiUtils ui,
	        PageModel model,
		    PageRequest pageRequest,
		    @RequestParam(required = false, value = "visitId") Visit visit,
		    @SpringBean KenyaUiUtils kenyaUi,
		    @SpringBean FormManager formManager,   
			@RequestParam(value="newVisit",required=false) String newVisit) {
		
		
		if (patient != null && StringUtils.isNotBlank(newVisit) && "true".equalsIgnoreCase(newVisit)) {
			List<Visit> visits = Context.getVisitService().getActiveVisitsByPatient(patient);
			for(Visit v : visits) {
				if(v.getVisitType().getName().equalsIgnoreCase(EmrWebConstants.VISIT_TYPE_NEW_PATIENT)){
					v.setStopDatetime(Calendar.getInstance().getTime());
					Context.getVisitService().saveVisit(v);
					break;
				}
			}
		}

		if ("".equals(formUuid)) {
			formUuid = null;
			}
	
			AppDescriptor thisApp = kenyaUi.getCurrentApp(pageRequest);

			List<FormDescriptor> oneTimeFormDescriptors = formManager.getCommonFormsForPatient(thisApp, patient);
			List<SimpleObject> oneTimeForms = new ArrayList<SimpleObject>();
			for (FormDescriptor formDescriptor : oneTimeFormDescriptors) {
			Form form = formDescriptor.getTarget();
			oneTimeForms.add(ui.simplifyObject(form));
			}

			model.addAttribute("visits", Context.getVisitService().getVisitsByPatient(patient));
			model.addAttribute("visitsCount", Context.getVisitService().getVisitsByPatient(patient).size());

			Form form = null;
			String selection = null;
			if (visit != null) {
				selection = "visit-" + visit.getVisitId();
			}
			else if (formUuid != null) {
				selection = "form-" + formUuid;
				form = Context.getFormService().getFormByUuid(formUuid);
				List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, null, null, false);
				Encounter encounter = encounters.size() > 0 ? encounters.get(0) : null;
				model.addAttribute("encounter", encounter);
			}
			else {
				selection = "section-" + null;
			}

			Visit activeVisit = null;
			List<Visit> activeVisitList = Context.getVisitService().getActiveVisitsByPatient(patient);
			if(visit == null){
				if (activeVisitList != null && !activeVisitList.isEmpty()) {
					visit = activeVisitList.get(0);
				}
			}

			for(Visit v : activeVisitList){
				activeVisit=v;
			}
			
			model.addAttribute("form", form);
			model.addAttribute("visit", visit);
			model.addAttribute("selection", selection);
			model.addAttribute("visit", visit);
			model.addAttribute("activeVisit", activeVisit);
	}
	
	



	
	
	
	
	
	
	
	
	
	
}