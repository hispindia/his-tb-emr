package org.openmrs.module.kenyaemr.fragment.controller.program;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class TreatmentCardRegisterFragmentController {
	public void controller(
			@RequestParam(value = "patientId", required = false) Person person,
			@RequestParam(value = "patientId", required = false) Patient patient,
			@RequestParam("returnUrl") String returnUrl,
			FragmentModel model) {
		/*
		 * Constant value across all visit
		 */
		model.addAttribute("returnUrl", returnUrl);
		model.addAttribute("patientName", person.getGivenName());
		model.addAttribute("patientAge", person.getAge());
		model.addAttribute("birthDate", new SimpleDateFormat("dd-MMMM-yyyy")
				.format(person.getBirthdate()));
		model.addAttribute("patientGender", person.getGender());
		model.addAttribute("address", person.getPersonAddress());

		PatientWrapper wrapperPatient = new PatientWrapper(patient);
		PersonWrapper wrapperPerson = new PersonWrapper(person);

		model.addAttribute("patientWrap", wrapperPatient);
		model.addAttribute("personWrap", wrapperPerson);
		
		PatientIdentifier mdrTBRegistrationId = patient.getPatientIdentifier(Context.getPatientService().getPatientIdentifierTypeByUuid("d8ee3b8c-a8fc-4d6b-af6a-9423be5f8906"));
		if(mdrTBRegistrationId!=null){
			model.addAttribute("mdrTBRegistrationNumber", mdrTBRegistrationId);
		}
		else{
			model.addAttribute("mdrTBRegistrationNumber", null);
		}
		
		String registrationGroupVal = "";
		Obs registrationGroup = getLatestObs(patient, Dictionary.REGISTRATION_GROUP);
		if (registrationGroup != null) {
			registrationGroupVal = registrationGroup.getValueCoded().getName().toString();
		}
		model.addAttribute("registrationGroup", registrationGroupVal);
		
		String registrationDateVal = "";
		Obs registrationDate = getLatestObs(patient, Dictionary.MDR_TB_RGISTRATION_DATE);
		if (registrationDate != null) {
			registrationDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(registrationDate.getValueDate());
		}
		model.addAttribute("registrationDateVal", registrationDateVal);
		
		String townshipVal = "";
		Obs township = getLatestObs(patient, Dictionary.TOWNSHIP);
		if (township != null) {
			townshipVal = township.getValueCoded().getName().toString();
		}
		model.addAttribute("townshipVal", townshipVal);
		
		String tbDiseaseClasificationVal = "";
		Obs tbDiseaseClasification = getLatestObs(patient, Dictionary.SITE_OF_TUBERCULOSIS_DISEASE);
		if (tbDiseaseClasification != null) {
			tbDiseaseClasificationVal = tbDiseaseClasification.getValueCoded().getName().toString();
		}
		model.addAttribute("tbDiseaseClasificationVal", tbDiseaseClasificationVal);
		
		String tbSiteVal = "";

		Obs tbSite = getAllLatestObs(patient, Dictionary.TB_SITE);
		if (tbSite != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					tbSite.getEncounter());
			List<Obs> obsList = wrapped.allObs(tbSite.getConcept());

			for (Obs obs : obsList) {
				if (tbSiteVal.isEmpty()) {
					tbSiteVal = tbSiteVal.concat(obs
							.getValueCoded().getName().toString());
				} else {
					tbSiteVal = tbSiteVal.concat(", "
							+ obs.getValueCoded().getName().toString());
				}
			}
		}

		model.addAttribute("tbSiteVal", tbSiteVal);

		String outcomeVal = "";
		Obs outcome = getLatestObs(patient, Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		if (outcome != null) {
			outcomeVal = outcome.getValueCoded().getName().toString();
		}
		model.addAttribute("outcomeVal", outcomeVal);		
		
		String regimenStartDateTypeVal = "";
		Obs regimenStartDateType = getLatestObs(patient, Dictionary.TB_REGIMEN_START_DATE_TYPE);
		if (regimenStartDateType != null) {
			regimenStartDateTypeVal = regimenStartDateType.getValueCoded().getName().toString();
		}
		model.addAttribute("regimenStartDateTypeVal", regimenStartDateTypeVal);		
		
		model.addAttribute("systemLocation", Context.getService(KenyaEmrService.class).getDefaultLocation());
		
		String dotProviderVal = "";
		Obs dotProvider = getAllLatestObs(patient, Dictionary.DOT_PROVIDER_NAME_TB_ENROLL_FORM);
		if (dotProvider != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					dotProvider.getEncounter());
			List<Obs> obsList = wrapped.allObs(dotProvider.getConcept());

			for (Obs obs : obsList) {
				if (dotProviderVal.isEmpty()) {
					dotProviderVal = dotProviderVal.concat(obs
							.getValueText());
				} else {
					dotProviderVal = dotProviderVal.concat(", "
							+ obs.getValueText());
				}
			}
		}
		model.addAttribute("dotProviderVal", dotProviderVal);


		String supervisorVal = "";
		Obs supervisor = getAllLatestObs(patient, Dictionary.MDR_TB_DOT_SUPERVISOR);
		if (supervisor != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					supervisor.getEncounter());
			List<Obs> obsList = wrapped.allObs(supervisor.getConcept());

			for (Obs obs : obsList) {
				if (supervisorVal.isEmpty()) {
					supervisorVal = supervisorVal.concat(obs
							.getValueText());
				} else {
					supervisorVal = supervisorVal.concat(", "
							+ obs.getValueText());
				}
			}
		}
		model.addAttribute("supervisorVal", supervisorVal);
		
		String contactCaseVal = "";
		Obs contactCase = getLatestObs(patient, Dictionary.CONTACT_MDR_CASE);
		if (contactCase != null) {
			contactCaseVal = contactCase.getValueCoded().getName().toString();
		}
		model.addAttribute("contactCaseVal", contactCaseVal);		
		
		String hivTestVal = "";
		Obs hivTest = getLatestObs(patient, Dictionary.HIV_TEST);
		if (hivTest != null) {
			hivTestVal = hivTest.getValueCoded().getName().toString();
		}
		model.addAttribute("hivTestVal", hivTestVal);		
		
		String hivTestDateVal = "";
		Obs hivTestDate = getLatestObs(patient, Dictionary.HIV_TEST_DATE);
		if (hivTestDate != null) {
			hivTestDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(hivTestDate.getValueDate());
		}
		model.addAttribute("hivTestDateVal", hivTestDateVal);
		
		String hivTestResultVal = "";
		Obs hivTestResult = getLatestObs(patient, Dictionary.RESULT_OF_HIV_TEST);
		if (hivTestResult != null) {
			hivTestResultVal = hivTestResult.getValueCoded().getName().toString();
		}
		model.addAttribute("hivTestResultVal", hivTestResultVal);		
		
		String artStartedVal = "";
		Obs artStarted = getLatestObs(patient, Dictionary.ART_STARTED);
		if (artStarted != null) {
			artStartedVal = artStarted.getValueCoded().getName().toString();
		}
		model.addAttribute("artStartedVal", artStartedVal);		
		
		String cptStartedVal = "";
		Obs cptStarted = getLatestObs(patient, Dictionary.CPT_STARTED);
		if (cptStarted != null) {
			artStartedVal = cptStarted.getValueCoded().getName().toString();
		}
		model.addAttribute("cptStartedVal", cptStartedVal);		
		
		String artStartDateVal = "";
		Obs artStartDate = getLatestObs(patient, Dictionary.ART_STARTED_DATE);
		if (artStartDate != null) {
			artStartDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(artStartDate.getValueDate());
		}
		model.addAttribute("artStartDateVal", artStartDateVal);
		
		String cptStartDateVal = "";
		Obs cptStartDate = getLatestObs(patient, Dictionary.INFANT_CPT_DATE);
		if (cptStartDate != null) {
			cptStartDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(cptStartDate.getValueDate());
		}
		model.addAttribute("cptStartDateVal", cptStartDateVal);
		

		/*
		 * District/Township MDR-TB Committee recommendation
		 */
		Obs committeeRecommendationDetail = getAllLatestObs(patient,
				Dictionary.COMMITTEE_RECOMMENDATION_DETAILS);
		Obs committeeRecommendationDate = getAllLatestObs(patient, Dictionary.COMMITTEE_RECOMMENDATION_DATE);
		Obs committeeRecommendationNextDate = getAllLatestObs(patient, Dictionary.COMMITTEE_RECOMMENDATION_NEXT_DATE);
		Obs committeeRecommendationDecision = getAllLatestObs(patient, Dictionary.COMMITTEE_RECOMMENDATION_DECISION);

		
		Map<Integer, String> recommendationIndexList = new HashMap<Integer, String>();
		Integer recommendationIndex = 0;
		if (committeeRecommendationDetail != null) {
			EncounterWrapper wrappedObsGroup = new EncounterWrapper(
					committeeRecommendationDetail.getEncounter());
			List<Obs> obsGroupList = wrappedObsGroup
					.allObs(committeeRecommendationDetail.getConcept());
			for (Obs obsG : obsGroupList) {
				String committeeRecommendationDateVal = "";
				String committeeRecommendationDecisionVal = "";
				String committeeRecommendationNextDateVal = "";

				if (committeeRecommendationDate != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							committeeRecommendationDate.getEncounter());
					List<Obs> obsList = wrapped.allObs(committeeRecommendationDate.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							committeeRecommendationDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());
						}
					}
				}


				if (committeeRecommendationNextDate != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							committeeRecommendationNextDate.getEncounter());
					List<Obs> obsList = wrapped.allObs(committeeRecommendationNextDate
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							committeeRecommendationNextDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());
						}
					}
				}

				if (committeeRecommendationDecision != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							committeeRecommendationDecision.getEncounter());
					List<Obs> obsList = wrapped.allObs(committeeRecommendationDecision.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							committeeRecommendationDecisionVal = committeeRecommendationDecisionVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}


				String val = committeeRecommendationDateVal + ", " + committeeRecommendationDecisionVal+ ", " + committeeRecommendationNextDateVal;
				recommendationIndexList.put(recommendationIndex, val);
				recommendationIndex++;
			}
		}
		model.addAttribute("recommendationIndexList", recommendationIndexList);
		
		String tbOutcomeDateVal = "";
		Obs tbOutcomeDate = getLatestObs(patient, Dictionary.TB_OUTCOME_DATE);
		if (tbOutcomeDate != null) {
			tbOutcomeDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(tbOutcomeDate.getValueDate());
		}
		model.addAttribute("tbOutcomeDateVal", tbOutcomeDateVal);
		
		String tbOutcomeVal = "";
		Obs tbOutcome = getLatestObs(patient, Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		if (tbOutcome != null) {
			tbOutcomeVal = tbOutcome.getValueCoded().getName().toString();
		}
		model.addAttribute("tbOutcomeVal", tbOutcomeVal);		
		
		
		model.addAttribute("graphingConcepts", Dictionary.getConcepts(Dictionary.TUBERCULOSIS_TREATMENT_NUMBER, Dictionary.TUBERCULOSIS_DRUG_TREATMENT_START_DATE,Dictionary.TB_FORM_REGIMEN, Dictionary.CURRENT_WHO_STAGE));
	}
	
	private Obs getLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(patient, concept);
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(0);
		}
		return null;
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