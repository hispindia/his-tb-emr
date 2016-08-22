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

package org.openmrs.module.kenyaemr.page.controller.chart;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.kenyacore.form.FormDescriptor;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyacore.program.ProgramDescriptor;
import org.openmrs.module.kenyacore.program.ProgramManager;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.EmrConstants;
import org.openmrs.module.kenyaemr.EmrWebConstants;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.session.Session;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Viewing a patient's record, in the chart app
 */
@AppPage(EmrConstants.APP_CHART)
public class ChartViewPatientPageController {

	public void controller(@RequestParam(required = false, value = "visitId") Visit visit,
	                       @RequestParam(required = false, value = "formUuid") String formUuid,
	                       @RequestParam(required = false, value = "programId") Program program,
						   @RequestParam(required = false, value = "section") String section,
	                       PageModel model,
	                       UiUtils ui,
	                       Session session,
						   PageRequest pageRequest,
						   @SpringBean KenyaUiUtils kenyaUi,
						   @SpringBean FormManager formManager,
						   @SpringBean ProgramManager programManager) {

		if ("".equals(formUuid)) {
			formUuid = null;
		}

		Patient patient = (Patient) model.getAttribute(EmrWebConstants.MODEL_ATTR_CURRENT_PATIENT);
		recentlyViewed(patient, session);

		AppDescriptor thisApp = kenyaUi.getCurrentApp(pageRequest);

		List<FormDescriptor> oneTimeFormDescriptors = formManager.getCommonFormsForPatient(thisApp, patient);
		List<SimpleObject> oneTimeForms = new ArrayList<SimpleObject>();
		for (FormDescriptor formDescriptor : oneTimeFormDescriptors) {
			Form form = formDescriptor.getTarget();
			oneTimeForms.add(ui.simplifyObject(form));
		}
		model.addAttribute("oneTimeForms", oneTimeForms);

		Collection<ProgramDescriptor> progams = programManager.getPatientPrograms(patient);

		model.addAttribute("programs", progams);
		model.addAttribute("programSummaries", programSummaries(patient, progams, programManager, kenyaUi));
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
		else if (program != null) {
			selection = "program-" + program.getProgramId();
		}
		else {
			if (StringUtils.isEmpty(section)) {
				section = "overview";
			}
			selection = "section-" + section;
		}

		model.addAttribute("form", form);
		model.addAttribute("visit", visit);
		model.addAttribute("program", program);
		model.addAttribute("section", section);
		model.addAttribute("selection", selection);
		
		/*
		 * Culture and DST History
		 */
		
		Obs cultureDstDetail = getAllLatestObs(patient,
				Dictionary.CULTURE_DRUG_GROUP);
		Obs cultureDstDate = getAllLatestObs(patient, Dictionary.CULTURE_DRUG_DATE);
		Obs cultureDstS = getAllLatestObs(patient, Dictionary.CULTURE_DRUG_S);
		Obs cultureDstH = getAllLatestObs(patient, Dictionary.CULTURE_DRUG_H);
		Obs cultureDstR = getAllLatestObs(patient, Dictionary.CULTURE_DRUG_R);
		Obs cultureDstE = getAllLatestObs(patient, Dictionary.CULTURE_DRUG_E);
		Obs cultureDstPtoEto = getAllLatestObs(patient, Dictionary.CULTURE_DRUG_PTO_ETO);
		Obs cultureDstCM = getAllLatestObs(patient, Dictionary.CULTURE_DRUG_CM);
		Obs cultureDstKmAmk = getAllLatestObs(patient, Dictionary.CULTURE_DRUG_KM_AMK);
		Obs cultureDstOther = getAllLatestObs(patient, Dictionary.OTHER_NON_CODED);
		Obs cultureNumber = getAllLatestObs(patient, Dictionary.CULTURE_NUMBER);
		Obs cultureDstFq = getAllLatestObs(patient, Dictionary.OFLOXACIN);
		
				
		Map<Integer, String> cultureDstList = new HashMap<Integer, String>();
		Integer cultureDstIndex = 0;
		if (cultureDstDetail != null) {
			EncounterWrapper wrappedObsGroup = new EncounterWrapper(
					cultureDstDetail.getEncounter());
			List<Obs> obsGroupList = wrappedObsGroup
					.allObs(cultureDstDetail.getConcept());
			for (Obs obsG : obsGroupList) {
				String cultureDstDateVal = "";
				String cultureDstSVal = "";
				String cultureDstHVal = "";
				String cultureDstRVal = "";
				String cultureDstEVal = "";
				String cultureDstPtoEtoVal = "";
				String cultureDstCMVal = "";
				String cultureDstKmAmkVal = "";
				String cultureDstOtherVal = "";
				String cultureNumberVal = "";
				String cultureDstFqVal = "";
				

				if (cultureNumber != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureNumber.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureNumber.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureNumberVal = cultureNumberVal.concat(obs
									.getValueText());
						}
					}
				}
				
				if (cultureDstDate != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstDate.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstDate.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());
						}
					}
				}
				
				if (cultureDstFq != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstFq.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstFq.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstFqVal =  cultureDstFqVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}
				

				if (cultureDstS != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstS.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstS.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstSVal = cultureDstSVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}

				if (cultureDstH != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstH.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstH.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstHVal = cultureDstHVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}
				
				if (cultureDstR != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstR.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstR.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstRVal = cultureDstRVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}

				if (cultureDstE != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstE.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstE.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstEVal = cultureDstEVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}
				
				if (cultureDstPtoEto != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstPtoEto.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstPtoEto.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstPtoEtoVal = cultureDstPtoEtoVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}
				
				if (cultureDstCM != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstCM.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstCM.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstCMVal = cultureDstCMVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}
				
				if (cultureDstKmAmk != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstKmAmk.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstKmAmk.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstKmAmkVal = cultureDstKmAmkVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}

				if (cultureDstOther != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							cultureDstOther.getEncounter());
					List<Obs> obsList = wrapped.allObs(cultureDstOther.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							cultureDstOtherVal = cultureDstOtherVal.concat(obs
									.getValueText().toString());
						}
					}
				}

				String val = cultureDstDateVal + ", " + cultureDstSVal+ ", " + cultureDstHVal + ", " +cultureDstRVal+ ", " +cultureDstEVal
				+ ", " +cultureDstPtoEtoVal+ ", " +cultureDstCMVal+ ", " +cultureDstKmAmkVal+ ", " +cultureDstOtherVal
				+ ", " + cultureNumberVal + ", " + cultureDstFqVal;
				cultureDstList.put(cultureDstIndex, val);
				cultureDstIndex++;
			}
		}
		model.addAttribute("cultureDstList", cultureDstList);
		
	}

	/**
	 * Adds this patient to the user's recently viewed list
	 * @param patient the patient
	 * @param session the session
	 */
	private void recentlyViewed(Patient patient, Session session) {
		String attrName = EmrConstants.APP_CHART + ".recentlyViewedPatients";

		LinkedList<Integer> recent = session.getAttribute(attrName, LinkedList.class);
		if (recent == null) {
			recent = new LinkedList<Integer>();
			session.setAttribute(attrName, recent);
		}
		recent.removeFirstOccurrence(patient.getPatientId());
		recent.add(0, patient.getPatientId());
		while (recent.size() > 10)
			recent.removeLast();
	}

	/**
	 * Creates a one line summary for each program
	 * @return the map of programs to summaries
	 */
	private Map<Program, String> programSummaries(Patient patient, Collection<ProgramDescriptor> programs, ProgramManager programManager, KenyaUiUtils kenyaUi) {
		Map<Program, String> summaries = new HashMap<Program, String>();

		for (ProgramDescriptor descriptor : programs) {
			Program program = descriptor.getTarget();
			List<PatientProgram> allEnrollments = programManager.getPatientEnrollments(patient, program);
			PatientProgram lastEnrollment = allEnrollments.get(allEnrollments.size() - 1);
			String summary = lastEnrollment.getActive()
					? "Enrolled on " + kenyaUi.formatDate(lastEnrollment.getDateEnrolled())
					: "Completed on " + kenyaUi.formatDate(lastEnrollment.getDateCompleted());

			summaries.put(descriptor.getTarget(), summary);
		}

		return summaries;
	}
	
	private Obs getAllLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(patient, concept);
		int count = obs.size() - 1;
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(count);
		}
		return null;
	}	

	
}