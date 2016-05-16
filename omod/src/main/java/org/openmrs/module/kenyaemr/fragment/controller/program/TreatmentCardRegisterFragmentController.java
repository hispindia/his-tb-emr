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
		 * Obstetric History
		 * */
		String pregStatusVal = "";
		String eddVal = "";
		String ancNumberVal = "";
		
		Obs pregStatus = getLatestObs(patient, Dictionary.PREGNANCY_STATUS);
		if (pregStatus != null) {
				pregStatusVal = pregStatus.getValueCoded().getName().toString();
		}
		model.addAttribute("pregStatusVal", pregStatusVal);
		
		Obs edd = getLatestObs(patient, Dictionary.EXPECTED_DATE_OF_DELIVERY);
		if (edd != null) {
			eddVal = new SimpleDateFormat("dd-MMMM-yyyy").format(edd.getValueDate());
		}
		model.addAttribute("eddVal", eddVal);
		
		Obs ancNumber = getLatestObs(patient, Dictionary.ANTENATAL_CASE_NUMBER);
		if (ancNumber != null) {
			ancNumberVal = ancNumber.getValueNumeric().toString();
		}
		model.addAttribute("ancNumberVal", ancNumberVal);
		
		
		/*
		 * Personal History
		 */

		String listAllRiskFactor = "";
		String literate = "";
		String employed = "";

		Obs riskFactor = getAllLatestObs(patient, Dictionary.HIV_RISK_FACTOR);
		if (riskFactor != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					riskFactor.getEncounter());
			List<Obs> obsList = wrapped.allObs(riskFactor.getConcept());

			for (Obs obs : obsList) {
				if (listAllRiskFactor.isEmpty()) {
					listAllRiskFactor = listAllRiskFactor.concat(obs
							.getValueCoded().getName().toString());
				} else {
					listAllRiskFactor = listAllRiskFactor.concat(", "
							+ obs.getValueCoded().getName().toString());
				}
			}
		}

		model.addAttribute("listAllRiskFactor", listAllRiskFactor);
		
		Obs literateObs = getAllLatestObs(patient, Dictionary.LITERATE);
		if (literateObs != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					literateObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(literateObs.getConcept());
			for (Obs obs : obsList) {
				literate = literate.concat(obs.getValueCoded().getName()
						.toString());
			}
		}
		model.addAttribute("literate", literate);

		Obs employedObs = getAllLatestObs(patient, Dictionary.EMPLOYED);
		if (employedObs != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					employedObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(employedObs.getConcept());
			for (Obs obs : obsList) {
				employed = employed.concat(obs.getValueCoded().getName()
						.toString());
			}
		}
		model.addAttribute("employed", employed);

		
		/*
		 * Drug History
		 */
		String artReceivedVal = "";

		Obs artReceived = getAllLatestObs(patient,
				Dictionary.DRUG_HISTORY_ART_RECEIVED);
		if (artReceived != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					artReceived.getEncounter());
			List<Obs> obsList = wrapped.allObs(artReceived.getConcept());
			for (Obs obs : obsList) {
				artReceivedVal = artReceivedVal.concat(obs.getValueCoded()
						.getName().toString());
			}
		}
		model.addAttribute("artReceivedVal", artReceivedVal);

		String artReceivedTypeValue = "";

		Obs artReceivedType = getAllLatestObs(patient,
				Dictionary.DRUG_HISTORY_ART_RECEIVED_TYPE);
		if (artReceivedType != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					artReceivedType.getEncounter());
			List<Obs> obsList = wrapped.allObs(artReceivedType.getConcept());
			for (Obs obs : obsList) {
				artReceivedTypeValue = artReceivedTypeValue.concat(obs
						.getValueCoded().getName().toString());
			}
		}
		model.addAttribute("artReceivedTypeValue", artReceivedTypeValue);

		String artReceivedPlaceValue = "";

		Obs artReceivedPlace = getAllLatestObs(patient,
				Dictionary.DRUG_HISTORY_ART_RECEIVED_PLACE);
		if (artReceivedPlace != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					artReceivedPlace.getEncounter());
			List<Obs> obsList = wrapped.allObs(artReceivedPlace.getConcept());
			for (Obs obs : obsList) {
				artReceivedPlaceValue = artReceivedPlaceValue.concat(obs
						.getValueCoded().getName().toString());
			}
		}
		model.addAttribute("artReceivedPlaceValue", artReceivedPlaceValue);

		String drugStartDateVal = "";
		Obs drugStartDate = getAllLatestObs(patient, Dictionary.ART_START_DATE_DRUG_HISTORY);
		if (drugStartDate != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					drugStartDate.getEncounter());
			List<Obs> obsList = wrapped.allObs(drugStartDate
					.getConcept());
			for (Obs obs : obsList) {
				drugStartDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());
			}
		}
		
		model.addAttribute("drugStartDateVal", drugStartDateVal);
		
		String drugDurationVal = "";
		Obs drugDuration = getAllLatestObs(patient, Dictionary.DRUG_DURATION);
		if (drugDuration != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					drugDuration.getEncounter());
			List<Obs> obsList = wrapped.allObs(drugDuration
					.getConcept());
			for (Obs obs : obsList) {
					drugDurationVal = drugDurationVal.concat(obs
							.getValueNumeric().toString());
			}
		}
		
		model.addAttribute("drugDurationVal", drugDurationVal);

		String drugNameVal = "";
		Obs drugName = getAllLatestObs(patient, Dictionary.DRUG_NAME);
		if (drugName != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					drugName.getEncounter());
			List<Obs> obsList = wrapped.allObs(drugName.getConcept());

			for (Obs obs : obsList) {
				if (drugNameVal.isEmpty()) {
					drugNameVal = drugNameVal.concat(obs
							.getValueCoded().getName().toString());
				} else {
					drugNameVal = drugNameVal.concat(", "
							+ obs.getValueCoded().getName().toString());
				}
			}
		}
		model.addAttribute("drugNameVal", drugNameVal);
		
		
		/*
		 * Personal History
		 */
		Obs obstetricHistoryDetail = getAllLatestObs(patient,
				Dictionary.OBSTETRIC_HIS_DETAIL);
		Obs infantName = getAllLatestObs(patient, Dictionary.INFANT_NAME);
		Obs infantCptDate = getAllLatestObs(patient, Dictionary.INFANT_CPT_DATE);

		Map<Integer, String> infantList = new HashMap<Integer, String>();
		Integer infantIndex = 0;
		if (obstetricHistoryDetail != null) {
			EncounterWrapper wrappedObsGroup = new EncounterWrapper(
					obstetricHistoryDetail.getEncounter());
			List<Obs> obsGroupList = wrappedObsGroup
					.allObs(obstetricHistoryDetail.getConcept());
			for (Obs obsG : obsGroupList) {
				String infantNameVal = "";
				String infantCptDateVal = "";

				if (infantName != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantName.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantName.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantNameVal = infantNameVal.concat(obs
									.getValueText().toString());
						}
					}
				}


				if (infantCptDate != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantCptDate.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantCptDate
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantCptDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());
						}
					}
				}


				String val = infantNameVal + ", " + infantCptDateVal;
				infantList.put(infantIndex, val);
				infantIndex++;

			}
		}
		model.addAttribute("infantList", infantList);
		
		
		/*
		 * End of follow up for Antiretroviral therapy
		 * */
		
		String programDiscontinuationReasonVal = "";
		String reasonConcept = "" ; 
		String dataPlaceVal ="";
		
		Obs programDiscontinuationReason = getLatestObs(patient,
				Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION);
		if (programDiscontinuationReason != null) {
				programDiscontinuationReasonVal = programDiscontinuationReason.getValueCoded().getName().toString();
				reasonConcept = programDiscontinuationReason.getValueCoded().toString();
			
		}
		model.addAttribute("programDiscontinuationReasonVal", programDiscontinuationReasonVal);
		
		if(reasonConcept.equals("5240")){
			Obs dataPlace = getAllLatestObs(patient,
					Dictionary.DATE_LAST_VISIT);
			if (dataPlace != null) {
				EncounterWrapper wrapped = new EncounterWrapper(
						dataPlace.getEncounter());
				List<Obs> obsList = wrapped.allObs(dataPlace.getConcept());
				for (Obs obs : obsList) {
					dataPlaceVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());
				}
			}
		}
		else if(reasonConcept.equals("160034")){
			Obs dataPlace = getAllLatestObs(patient,
					Dictionary.DEATH_DATE);
			if (dataPlace != null) {
				EncounterWrapper wrapped = new EncounterWrapper(
						dataPlace.getEncounter());
				List<Obs> obsList = wrapped.allObs(dataPlace.getConcept());
				for (Obs obs : obsList) {
					dataPlaceVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());
				}
			}
		}
		else if(reasonConcept.equals("159492")){
			Obs datePlace = getAllLatestObs(patient,
					Dictionary.DATE_TRANSFERRED_OUT);
			if (datePlace != null) {
				EncounterWrapper wrapped = new EncounterWrapper(
						datePlace.getEncounter());
				List<Obs> obsList = wrapped.allObs(datePlace.getConcept());
				for (Obs obs : obsList) {
					dataPlaceVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());
				}
			}

			Obs place = getAllLatestObs(patient,
					Dictionary.TRANSFERRED_OUT_TO);
			if (place != null) {
				EncounterWrapper wrapped = new EncounterWrapper(
						place.getEncounter());
				List<Obs> obsList = wrapped.allObs(place.getConcept());
				for (Obs obs : obsList) {
					dataPlaceVal = dataPlaceVal + " / Place : "+ obs.getValueText().toString();
				}
			}
		}
		
		model.addAttribute("dataPlaceVal", dataPlaceVal);

		
		/*
		 * Encounter details
		 * */
		Date dateArt = new Date();
		List<Encounter> artEncounters = Context.getEncounterService().getEncounters(patient);
		for (Encounter en : artEncounters) {
			if(en.getEncounterType().getUuid().equals("0cb4417d-b98d-4265-92aa-c6ee3d3bb317")){
				if(dateArt.after(en.getEncounterDatetime())){
					dateArt =  en.getEncounterDatetime();
				}
			}
		}
		model.addAttribute("artInitiationDate",new SimpleDateFormat("dd-MMMM-yyyy").format(dateArt));
		
		Date dateArtEligible = new Date();
		List<Encounter> artEligibleEncounters = Context.getEncounterService().getEncounters(patient);
		for (Encounter en : artEligibleEncounters) {
			if(en.getEncounterType().getUuid().equals("de78a6be-bfc5-4634-adc3-5f1a280455cc")){
				if(dateArtEligible.after(en.getEncounterDatetime())){
					dateArtEligible =  en.getEncounterDatetime();
				}
			}
		}
		model.addAttribute("artEligibleDate",new SimpleDateFormat("dd-MMMM-yyyy").format(dateArtEligible));
		
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