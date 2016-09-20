package org.openmrs.module.kenyaemr.fragment.controller.program;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
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
import org.openmrs.module.kenyaemr.model.VisitAndAppointment;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class TreatmentCardRegisterFragmentController {
	public void controller(
			@RequestParam(value = "patientId", required = false) Person person,
			@RequestParam(value = "patientId", required = false) Patient patient,
			@RequestParam("returnUrl") String returnUrl, FragmentModel model) {

		KenyaEmrService kenyaEmrService = (KenyaEmrService) Context
				.getService(KenyaEmrService.class);
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

		PatientIdentifier mdrTBSuspectId = patient
				.getPatientIdentifier(Context.getPatientService()
						.getPatientIdentifierTypeByUuid(
								"d59d0f67-4a05-4e41-bfad-342da68feb6f"));
		if (mdrTBSuspectId != null) {
			model.addAttribute("mdrTBSuspectNumber",mdrTBSuspectId);
		} else {
			model.addAttribute("mdrTBSuspectNumber", " ");
		}
		
		PatientIdentifier mdrTBRegistrationId = patient
				.getPatientIdentifier(Context.getPatientService()
						.getPatientIdentifierTypeByUuid(
								"d8ee3b8c-a8fc-4d6b-af6a-9423be5f8906"));
		if (mdrTBRegistrationId != null) {
			model.addAttribute("mdrTBRegistrationNumber", mdrTBRegistrationId);
		} else {
			model.addAttribute("mdrTBRegistrationNumber", " ");
		}
		
		PatientIdentifier mdrTBPatientId = patient
				.getPatientIdentifier(Context.getPatientService()
						.getPatientIdentifierTypeByUuid(
								"90e2e35a-ce2a-43d5-8790-2dc139d7ace1"));
		if (mdrTBPatientId != null) {
			model.addAttribute("mdrTBPatientId", mdrTBPatientId);
		} else {
			model.addAttribute("mdrTBPatientId", " ");
		}
    /*Record vitals value
     * 
     */
		Integer height =0;Integer weight =0;
		List <Obs> heightValue=Context.getObsService().getObservationsByPersonAndConcept(patient,Dictionary.getConcept(Dictionary.HEIGHT_CM));
		for(Obs o:heightValue)
		{
			if(o!=null)
			{
				height=o.getValueNumeric().intValue();
				
			}
		}
		model.addAttribute("height", height);
		List <Obs> weightValue=Context.getObsService().getObservationsByPersonAndConcept(patient,Dictionary.getConcept(Dictionary.WEIGHT_KG));
		for(Obs o:weightValue)
		{
			if(o!=null)
			{
				weight=o.getValueNumeric().intValue();
				
			}
		}
		model.addAttribute("weight", weight);
		String registrationGroupVal = "";
		Obs registrationGroup = getLatestObs(patient,
				Dictionary.REGISTRATION_GROUP);
		if (registrationGroup != null) {
			registrationGroupVal = registrationGroup.getValueCoded().getName()
					.toString();
		}
		model.addAttribute("registrationGroup", registrationGroupVal);
      /* Co-morbidity values
       * 
       */
		
		String diabities="";String diabitiesss="";
		Obs Diabetiec = getLatestObs(patient,
				Dictionary.COMORBIDITY_TB_ENROLL_FORM);
		if(Diabetiec!=null)
		{
			EncounterWrapper wrapped = new EncounterWrapper(
					Diabetiec.getEncounter());
			List<Obs> obsList = wrapped.allObs(Diabetiec.getConcept());
			for (Obs obs : obsList) {
			if (diabities.isEmpty()) {
					diabities = diabities.concat(obs.getValueCoded().toString());
					if(diabities.equals("142474")||diabities.equals("142473"))
					{
						diabitiesss="Yes";
					}
					else
					{
						diabitiesss="No";
					}
					
				} 
			else {
					diabities = diabities.concat(", "+ obs.getValueCoded().toString());
					String[] valueList = diabities.split("\\s*,\\s*");
					for (String diabname : valueList)
					{
					if(diabname.equals("142474")||diabname.equals("142473"))
					{
						diabitiesss="Yes";
						break;
					}
					
					else
					{
						diabitiesss="No";
					}
					}
					
				}
			}
		}
	
		model.addAttribute("diabities", diabitiesss);
		/*Other disease values
		 * 
		 */
		String otherDiseaseVal = "";

		Obs otherDises = getAllLatestObs(patient, Dictionary.COMORBIDITY_TB_ENROLL_FORM);
		if (otherDises != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					otherDises.getEncounter());
			List<Obs> obsList = wrapped.allObs(otherDises.getConcept());

			for (Obs obs : obsList) {
				if (otherDiseaseVal.isEmpty()) {
					if(obs.getValueCoded().getConceptId()!=142473 && obs.getValueCoded().getConceptId()!=142474)
					{
						otherDiseaseVal = otherDiseaseVal.concat(obs.getValueCoded().getName()
					
							.toString());
					}
				} else {
					if(obs.getValueCoded().getConceptId()!=142473 && obs.getValueCoded().getConceptId()!=142474)
					{
					otherDiseaseVal = otherDiseaseVal.concat(", "
							+ obs.getValueCoded().getName().toString());
					}
					
				}
			}
		}

		model.addAttribute("otherDiseaseVal", otherDiseaseVal);
		/*Treatment initiation center*/
		String treatmentCenterVal = "";
		Obs treatmentCenter = getLatestObs(patient,
				Dictionary.INFANT_NAME);
		if (treatmentCenter != null) {
		    treatmentCenterVal=Context.getLocationService().getLocation(Integer.parseInt(treatmentCenter.getValueText())).toString();
			
		}
		model.addAttribute("treatmentCenterVal", treatmentCenterVal);
		
		
		String registrationDateVal = "";
		Obs registrationDate = getLatestObs(patient,
				Dictionary.MDR_TB_RGISTRATION_DATE);
		if (registrationDate != null) {
			registrationDateVal = new SimpleDateFormat("dd-MMMM-yyyy")
					.format(registrationDate.getValueDate());
		}
		model.addAttribute("registrationDateVal", registrationDateVal);

		String townshipVal = "";
		Obs township = getLatestObs(patient, Dictionary.TOWNSHIP);
		if (township != null) {
			townshipVal = township.getValueCoded().getName().toString();
		}
		model.addAttribute("townshipVal", townshipVal);
       /*secondline
        * 
        */
 
		String onSecondLine = "";
		Obs secndline = getLatestObs(patient, Dictionary.SECOND_LINE_DRUG);
		if (secndline != null) {
			onSecondLine = secndline.getValueCoded().getName().toString();
		}
		model.addAttribute("onSecondLine", onSecondLine);
		/* Condition for previous treatment episodes
		 * 
		 * 
		 */
		String tbHistory = "";
		Obs tbhistry = getLatestObs(patient, Dictionary.TB_PATIENT);
		if (tbhistry != null) {
			tbHistory = tbhistry.getValueCoded().getName().toString();
		}
		model.addAttribute("tbHistory", tbHistory);
		String tbDiseaseClasificationVal = "";
		Obs tbDiseaseClasification = getLatestObs(patient,
				Dictionary.SITE_OF_TUBERCULOSIS_DISEASE);
		if (tbDiseaseClasification != null) {
			tbDiseaseClasificationVal = tbDiseaseClasification.getValueCoded()
					.getName().toString();
		}
		model.addAttribute("tbDiseaseClasificationVal",
				tbDiseaseClasificationVal);

		String tbSiteVal = "";

		Obs tbSite = getAllLatestObs(patient, Dictionary.TB_SITE);
		if (tbSite != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					tbSite.getEncounter());
			List<Obs> obsList = wrapped.allObs(tbSite.getConcept());

			for (Obs obs : obsList) {
				if (tbSiteVal.isEmpty()) {
					tbSiteVal = tbSiteVal.concat(obs.getValueCoded().getName()
							.toString());
				} else {
					tbSiteVal = tbSiteVal.concat(", "
							+ obs.getValueCoded().getName().toString());
				}
			}
		}

		model.addAttribute("tbSiteVal", tbSiteVal);

		String outcomeVal = "";
		Obs outcome = getLatestObs(patient,
				Dictionary.PREVIOUS_TREATMENT_OUTCOME);
		if (outcome != null) {
			outcomeVal = outcome.getValueCoded().getName().toString();
		}
		model.addAttribute("outcomeVal", outcomeVal);

		String regimenStartDateTypeVal = "";
		Obs regimenStartDateType = getLatestObs(patient,
				Dictionary.TB_REGIMEN_START_DATE_TYPE);
		if (regimenStartDateType != null) {
			regimenStartDateTypeVal = regimenStartDateType.getValueCoded()
					.getName().toString();
		}
		model.addAttribute("regimenStartDateTypeVal", regimenStartDateTypeVal);

		/*model.addAttribute("systemLocation",
				Context.getService(KenyaEmrService.class).getDefaultLocation());*/

		String dotProviderVal = "";
		Obs dotProvider = getAllLatestObs(patient,
				Dictionary.DOT_PROVIDER_NAME_TB_ENROLL_FORM);
		if (dotProvider != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					dotProvider.getEncounter());
			List<Obs> obsList = wrapped.allObs(dotProvider.getConcept());

			for (Obs obs : obsList) {
				if (dotProviderVal.isEmpty()) {
					dotProviderVal = dotProviderVal.concat(obs.getValueText());
				} else {
					dotProviderVal = dotProviderVal.concat(", "
							+ obs.getValueText());
				}
			}
		}
		model.addAttribute("dotProviderVal", dotProviderVal);

		String supervisorVal = "";
		Obs supervisor = getAllLatestObs(patient,
				Dictionary.MDR_TB_DOT_SUPERVISOR);
		if (supervisor != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					supervisor.getEncounter());
			List<Obs> obsList = wrapped.allObs(supervisor.getConcept());

			for (Obs obs : obsList) {
				if (supervisorVal.isEmpty()) {
					supervisorVal = supervisorVal.concat(obs.getValueText());
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
			hivTestDateVal = new SimpleDateFormat("dd-MMMM-yyyy")
					.format(hivTestDate.getValueDate());
		}
		model.addAttribute("hivTestDateVal", hivTestDateVal);

		String hivTestResultVal = "";
		Obs hivTestResult = getLatestObs(patient, Dictionary.RESULT_OF_HIV_TEST);
		if (hivTestResult != null) {
			hivTestResultVal = hivTestResult.getValueCoded().getName()
					.toString();
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
			cptStartedVal = cptStarted.getValueCoded().getName().toString();
		}
		model.addAttribute("cptStartedVal", cptStartedVal);

		String artStartDateVal = "";
		Obs artStartDate = getLatestObs(patient, Dictionary.ART_STARTED_DATE);
		if (artStartDate != null) {
			artStartDateVal = new SimpleDateFormat("dd-MMMM-yyyy")
					.format(artStartDate.getValueDate());
		}
		model.addAttribute("artStartDateVal", artStartDateVal);

		String cptStartDateVal = "";
		Obs cptStartDate = getLatestObs(patient, Dictionary.INFANT_CPT_DATE);
		if (cptStartDate != null) {
			cptStartDateVal = new SimpleDateFormat("dd-MMMM-yyyy")
					.format(cptStartDate.getValueDate());
		}
		model.addAttribute("cptStartDateVal", cptStartDateVal);

		/*
		 * District/Township MDR-TB Committee recommendation
		 */
		Obs committeeRecommendationDetail = getAllLatestObs(patient,
				Dictionary.COMMITTEE_RECOMMENDATION_DETAILS);
		Obs committeeRecommendationDate = getAllLatestObs(patient,
				Dictionary.COMMITTEE_RECOMMENDATION_DATE);
		Obs committeeRecommendationNextDate = getAllLatestObs(patient,
				Dictionary.COMMITTEE_RECOMMENDATION_NEXT_DATE);
		Obs committeeRecommendationDecision = getAllLatestObs(patient,
				Dictionary.COMMITTEE_RECOMMENDATION_DECISION);

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
					List<Obs> obsList = wrapped
							.allObs(committeeRecommendationDate.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							committeeRecommendationDateVal = new SimpleDateFormat(
									"dd-MMMM-yyyy").format(obs.getValueDate());
						}
					}
				}

				if (committeeRecommendationNextDate != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							committeeRecommendationNextDate.getEncounter());
					List<Obs> obsList = wrapped
							.allObs(committeeRecommendationNextDate
									.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							committeeRecommendationNextDateVal = new SimpleDateFormat(
									"dd-MMMM-yyyy").format(obs.getValueDate());
						}
					}
				}

				if (committeeRecommendationDecision != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							committeeRecommendationDecision.getEncounter());
					List<Obs> obsList = wrapped
							.allObs(committeeRecommendationDecision
									.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							committeeRecommendationDecisionVal = committeeRecommendationDecisionVal
									.concat(obs.getValueCoded().getName()
											.toString());
						}
					}
				}

				String val = committeeRecommendationDateVal + ", "
						+ committeeRecommendationDecisionVal + ", "
						+ committeeRecommendationNextDateVal;
				recommendationIndexList.put(recommendationIndex, val);
				recommendationIndex++;
			}
		}
		model.addAttribute("recommendationIndexList", recommendationIndexList);

		String tbOutcomeDateVal = "";
		Obs tbOutcomeDate = getLatestObs(patient, Dictionary.TB_OUTCOME_DATE);
		if (tbOutcomeDate != null) {
			tbOutcomeDateVal = new SimpleDateFormat("dd-MMMM-yyyy")
					.format(tbOutcomeDate.getValueDate());
		}
		model.addAttribute("tbOutcomeDateVal", tbOutcomeDateVal);

		String tbOutcomeVal = "";
		Obs tbOutcome = getLatestObs(patient,
				Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		if (tbOutcome != null) {
			tbOutcomeVal = tbOutcome.getValueCoded().getName().toString();
		}
		model.addAttribute("tbOutcomeVal", tbOutcomeVal);
		
		model.addAttribute("graphingConcepts", Dictionary.getConcepts(
				Dictionary.SPUTUM_SMEAR_TEST,
				Dictionary.CULTURE_SOLID,
				Dictionary.CULTURE_LIQUID,Dictionary.UREA_TEST,
				Dictionary.CREATININE,Dictionary.URIC_ACID,Dictionary.TSH,
				Dictionary.SUGAR));
		
		Map<Integer, String> regimenList = new HashMap<Integer, String>();
		  Integer regimenIndex =0 ;

		List<DrugOrder> orderList = Context.getOrderService()
				.getDrugOrdersByPatient(patient);

		List<Encounter> encounterList = Context.getEncounterService()
				.getEncounters(patient);
		for (Encounter en : encounterList) {
			String regName = "";
			String regName0 = " ";
			String regName1 = " ";
			String regName2 = " ";
			String regName3 = " ";
			String regName4 = " ";
			String regName5 = " ";
			String regName6 = " ";
			String regName7 = " ";
			String regName8 = " ";
			String regName9 = " ";
			String regName10 = " ";
			String regName11 = " ";
			String regName12 = " ";
			String changeStopReason = "";
			if (en.getEncounterType().getUuid()
					.equals("00d1b629-4335-4031-b012-03f8af3231f8")) {
				DrugOrderProcessed drugOrderProcessed = new DrugOrderProcessed();
				List<Order> orderListByEn = Context.getOrderService()
						.getOrdersByEncounter(en);
			
				for (Order o : orderListByEn) {
					
					DrugOrder dr = Context.getOrderService().getDrugOrder(
							o.getOrderId());
					DrugOrderProcessed dop = kenyaEmrService
							.getLastDrugOrderProcessed(dr);
					String regNames = dr.getConcept().getName().getName();
				
					String[] doseArray = dop.getDose().split("/");
					Integer count = 0;
					for (String druName : regNames.split("-")) {
					
						if (druName.equals("H")) {
							// regName0 = druName+"(" +
							// doseArray[count]+" "+dr.getUnits()+" "+dr.getFrequency()+")";
							regName0 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
							
						} else if (druName.equals("R")) {
							regName1 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
						} else if (druName.equals("Z")) {
							regName2 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
							
						} else if (druName.equals("E")) {
							regName3 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
						} else if (druName.equals("S")) {
							regName4 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
						} else if (druName.equals("Km")) {
							regName5 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
						} else if (druName.equals("Am")) {
							regName6 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
							
						} else if (druName.equals("Cm")) {
							regName7 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
						} else if (druName.equals("Lfx")) {
							regName8 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
						} else if (druName.equals("Eto")) {
							regName9 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
						} else if (druName.equals("Cs")) {
							regName10 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
						} else if (druName.equals("Sodium PAS")) {
							regName11 = "(" + doseArray[count] + " "
									+ dr.getUnits() + " " + dr.getFrequency() + " " + dop.getNoOfTablet()
									+ ")";
						}
						count++;
						
					}
					
					
					regName = regName0 + "," + regName1 + "," + regName2 + ","
							+ regName3 + "," + regName4 + "," + regName5 + ","
							+ regName6 + "," + regName7 + "," + regName8 + ","
							+ regName9 + "," + regName10 + "," + regName11;
					
					regimenList.put(regimenIndex, new SimpleDateFormat(
							"dd-MMMM-yyyy").format(en.getEncounterDatetime())
							+ ", " + regName);
					
				}
				
				regimenIndex++;
				
			}
			
		}
		
		model.addAttribute("regimenList", regimenList);
		
		
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
		
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		
		List<Visit> visits=Context.getVisitService().getVisitsByPatient(patient);
		List<VisitAndAppointment> visitAndAppointments=new LinkedList<VisitAndAppointment>();
		for(Visit visit:visits){
		Obs nextAppointment=kenyaEmrService.getObsForNextAppointmentByPerson(person,visit.getEncounters());
		VisitAndAppointment visitAndAppointment=new VisitAndAppointment();
		visitAndAppointment.setCurrentVisit(sdf.format(visit.getStartDatetime()));
		if(nextAppointment!=null){
		visitAndAppointment.setNextAppointMent(sdf.format( nextAppointment.getValueDatetime()));
		}
		else{
			visitAndAppointment.setNextAppointMent("");	
		}
		visitAndAppointments.add(visitAndAppointment);
		}
		model.addAttribute("visitAndAppointments", visitAndAppointments);

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

		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(0);
		}
		return null;
	}

}