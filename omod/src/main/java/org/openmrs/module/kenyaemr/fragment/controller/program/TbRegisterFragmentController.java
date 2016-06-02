package org.openmrs.module.kenyaemr.fragment.controller.program;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
import org.openmrs.module.kenyaemr.regimen.RegimenChange;
import org.openmrs.module.kenyaemr.regimen.RegimenChangeHistory;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.regimen.RegimenOrder;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class TbRegisterFragmentController {
	public void controller(
			@RequestParam(value = "patientId", required = false) Person person,
			@RequestParam(value = "patientId", required = false) Patient patient,
			@RequestParam("returnUrl") String returnUrl,@SpringBean RegimenManager regimenManager,
			FragmentModel model) {
		/*
		 * Constant value across all visit
		 */
		KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
		
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
				+ ", " +cultureDstPtoEtoVal+ ", " +cultureDstCMVal+ ", " +cultureDstKmAmkVal+ ", " +cultureDstOtherVal;
				cultureDstList.put(cultureDstIndex, val);
				cultureDstIndex++;
			}
		}
		model.addAttribute("cultureDstList", cultureDstList);
	
		
		/*
		 * Smear culture 
		 */
		
		List<Obs> sputumSmear = getAllLatestObsList(patient, Dictionary.SPUTUM_SMEAR_TEST);
		List<Obs> cultureSolid = getAllLatestObsList(patient, Dictionary.CULTURE_SOLID);
		List<Obs> cultureLiquid = getAllLatestObsList(patient, Dictionary.CULTURE_LIQUID);
		List<Obs> cultureSputum = getAllLatestObsList(patient, Dictionary.SPUTUM_CULTURE);
	
		List<Visit> visitList = Context.getVisitService().getVisitsByPatient(patient);
		
		Map<Integer, String> smearCultureIndexList = new HashMap<Integer, String>();
		Integer visitIndex = 0;
		if (visitList != null) {
			for (Visit v : visitList) {
				String sputumSmearVal = "";
				String cultureVal = "";
				String sputumSmearDateVal = "";
				String cultureDateVal = "";
				String visitDate = new SimpleDateFormat("dd-MMMM-yyyy").format(v.getStartDatetime());

				if (sputumSmear != null) {
					for (Obs obs : sputumSmear) {
						if (obs.getEncounter().getVisit().equals(v)) {
							sputumSmearVal = obs.getValueCoded().getName().toString();
							sputumSmearDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getObsDatetime());
						}
					}
				}

				if (cultureSolid != null) {
					for (Obs obs : cultureSolid) {
						if (obs.getEncounter().getVisit().equals(v)) {
							cultureVal = obs.getValueCoded().getName().toString();
							cultureDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getObsDatetime());
						}
					}
				}
				else if(cultureLiquid != null){
					for (Obs obs : cultureLiquid) {
						if (obs.getEncounter().getVisit().equals(v)) {
							cultureVal = obs.getValueCoded().getName().toString();
							cultureDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getObsDatetime());
						}
					}
				}
				else if(cultureSputum != null){
					for (Obs obs : cultureSputum) {
						if (obs.getEncounter().getVisit().equals(v)) {
							cultureVal = obs.getValueCoded().getName().toString();
							cultureDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getObsDatetime());
						}
					}
				}
				
				String category="TB";
				Concept masterSet = regimenManager.getMasterSetConcept(category);
				RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, masterSet);
				RegimenChange lastChange = history.getLastChange();
				RegimenOrder baseline = lastChange != null ? lastChange.getStarted() : null;
				List<DrugOrder> drugOrders = new ArrayList<DrugOrder>(baseline.getDrugOrders());
				for (DrugOrder drugOrder : drugOrders){
					DrugOrderProcessed dop=kenyaEmrService.getLastDrugOrderProcessed(drugOrder);	
				}

				String val = visitDate + ", " + sputumSmearVal+ ", " + sputumSmearDateVal + ", " + cultureVal+", "+cultureDateVal ;
				smearCultureIndexList.put(visitIndex, val);
				visitIndex++;
			}
		}

		model.addAttribute("smearCultureIndexList", smearCultureIndexList);		
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
	

	private List<Obs> getAllLatestObsList(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(patient, concept);
		int count = obs.size() - 1;
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs;
		}
		return null;
	}	
	
}