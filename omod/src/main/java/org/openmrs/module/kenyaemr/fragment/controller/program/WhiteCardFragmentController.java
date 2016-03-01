package org.openmrs.module.kenyaemr.fragment.controller.program;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class WhiteCardFragmentController {
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

		Obs savedEntryPoint = getLatestObs(patient,
				Dictionary.METHOD_OF_ENROLLMENT);
		model.addAttribute("savedEntryPoint", savedEntryPoint);
		
		if (savedEntryPoint != null && savedEntryPoint.getValueDate() != null) {
			model.addAttribute("savedEntryPointValueDate",
					new SimpleDateFormat("dd-MMMM-yyyy").format(savedEntryPoint
							.getValueDate()));
		} else {
			model.addAttribute("savedEntryPointValueDate", "");
		}

		/*
		 * Personal History
		 */

		String listAllRiskFactor = "";
		String iduStatusValue = "";
		String iduNameValue = "";
		String literate = "";
		String employed = "";
		String alcoholic = "";
		String alcoholicType = "";
		String income = "";

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

		Obs iduStatusObs = getAllLatestObs(patient,
				Dictionary.IDU_PERSONAL_HISTORY);
		if (iduStatusObs != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					iduStatusObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(iduStatusObs.getConcept());
			for (Obs obs : obsList) {
				iduStatusValue = iduStatusValue.concat(obs.getValueCoded()
						.getName().toString());
			}
		}
		model.addAttribute("iduStatusValue", iduStatusValue);

		Obs iduStatusNameObs = getAllLatestObs(patient,
				Dictionary.IDU_NAME_PERSONAL_HISTORY);
		if (iduStatusNameObs != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					iduStatusNameObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(iduStatusNameObs.getConcept());
			for (Obs obs : obsList) {
				iduNameValue = iduNameValue.concat(obs.getValueCoded()
						.getName().toString());
			}
		}
		model.addAttribute("iduNameValue", iduNameValue);

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

		Obs alcoholicObs = getAllLatestObs(patient, Dictionary.ALCOHOLIC);
		if (alcoholicObs != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					alcoholicObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(alcoholicObs.getConcept());
			for (Obs obs : obsList) {
				alcoholic = alcoholic.concat(obs.getValueCoded().getName()
						.toString());
			}
		}
		model.addAttribute("alcoholic", alcoholic);

		Obs alcoholicTypeObs = getAllLatestObs(patient,
				Dictionary.ALCOHOLIC_TYPE);
		if (alcoholicTypeObs != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					alcoholicTypeObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(alcoholicTypeObs.getConcept());
			for (Obs obs : obsList) {
				alcoholicType = alcoholicType.concat(obs.getValueCoded()
						.getName().toString());
			}
		}
		model.addAttribute("alcoholicType", alcoholicType);

		Obs incomeObs = getAllLatestObs(patient, Dictionary.PATIENT_INCOME);
		if (incomeObs != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					incomeObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(incomeObs.getConcept());
			for (Obs obs : obsList) {
				income = obs.getValueNumeric().toString();
			}
		}
		model.addAttribute("income", income);

		/*
		 * Family History
		 */
		String civilStatusVal = "";

		Obs civilStatus = getAllLatestObs(patient, Dictionary.CIVIL_STATUS);
		if (civilStatus != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					civilStatus.getEncounter());
			List<Obs> obsList = wrapped.allObs(civilStatus.getConcept());
			for (Obs obs : obsList) {
				civilStatusVal = civilStatusVal.concat(obs.getValueCoded()
						.getName().toString());
			}
		}
		model.addAttribute("civilStatusVal", civilStatusVal);

		Obs spName = getAllLatestObs(patient, Dictionary.SPOUSE_NAME);
		Obs spAge = getAllLatestObs(patient, Dictionary.SPOUSE_AGE);
		Obs spAgeUnit = getAllLatestObs(patient, Dictionary.DURATION_UNITS);
		Obs spGender = getAllLatestObs(patient, Dictionary.SPOUSE_GENDER);
		Obs spInfected = getAllLatestObs(patient, Dictionary.HIV_INFECTED);
		Obs spArt = getAllLatestObs(patient, Dictionary.SPOUSE_ART);
		Obs familyFormGroup = getAllLatestObs(patient,
				Dictionary.FAMILY_FORM_GROUP);

		Map<Integer, String> familyMembers = new HashMap<Integer, String>();
		Integer index = 0;
		if (familyFormGroup != null) {
			EncounterWrapper wrappedObsGroup = new EncounterWrapper(
					familyFormGroup.getEncounter());
			List<Obs> obsGroupList = wrappedObsGroup.allObs(familyFormGroup
					.getConcept());
			for (Obs obsG : obsGroupList) {
				String spNameVal = "";
				String spAgeVal = "";
				String spAgeUnitVal = "";
				String spGenderVal = "";
				String spInfectedVal = "";
				String spArtVal = "";

				if (spName != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							spName.getEncounter());
					List<Obs> obsList = wrapped.allObs(spName.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							spNameVal = spNameVal.concat(obs.getValueText()
									.toString());
						}
					}
				}

				if (spAge != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							spAge.getEncounter());
					List<Obs> obsList = wrapped.allObs(spAge.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							spAgeVal = spAgeVal.concat(obs.getValueNumeric()
									.toString());
						}
					}
				}

				if (spAgeUnit != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							spAgeUnit.getEncounter());
					List<Obs> obsList = wrapped.allObs(spAgeUnit.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							spAgeUnitVal = spAgeUnitVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}

				if (spGender != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							spGender.getEncounter());
					List<Obs> obsList = wrapped.allObs(spGender.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							spGenderVal = spGenderVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}

				if (spInfected != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							spInfected.getEncounter());
					List<Obs> obsList = wrapped.allObs(spInfected.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							spInfectedVal = spInfectedVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}

				if (spArt != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							spArt.getEncounter());
					List<Obs> obsList = wrapped.allObs(spArt.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							spArtVal = spArtVal.concat(obs.getValueCoded()
									.getName().toString());
						}
					}
				}
				String val = spNameVal + ", " + spAgeVal + ", " + spAgeUnitVal
						+ ", " + spGenderVal + ", " + spInfectedVal + " ,"
						+ spArtVal;
				familyMembers.put(index, val);
				index++;

			}
		}
		model.addAttribute("familyMembers", familyMembers);

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

		
		
		
		Obs drugHistoryGroup = getAllLatestObs(patient,
				Dictionary.DRUG_HISTORY_GROUP);
		Obs artReceivedPlace = getAllLatestObs(patient,
				Dictionary.DRUG_HISTORY_ART_RECEIVED_PLACE);
		Obs drugName = getAllLatestObs(patient, Dictionary.DRUG_NAME);
		Obs drugDuration = getAllLatestObs(patient, Dictionary.DRUG_DURATION);
		
		Map<Integer, String> drugMembers = new HashMap<Integer, String>();
		Integer indexDrug = 0;
		if (drugHistoryGroup != null) {
			EncounterWrapper wrappedObsGroup = new EncounterWrapper(
					drugHistoryGroup.getEncounter());
			List<Obs> obsGroupList = wrappedObsGroup.allObs(drugHistoryGroup
					.getConcept());
			for (Obs obsG : obsGroupList) {
				String artReceivedPlaceValue = "";
				String drugDurationVal = "";
				String drugNameVal = "";
	
				
				if (drugName != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							drugName.getEncounter());
					List<Obs> obsList = wrapped.allObs(drugName.getConcept());

					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							drugNameVal = drugNameVal.concat(obs
									.getValueCoded().getName().toString());
						} 
					}
				}
				
				if (drugDuration != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							drugDuration.getEncounter());
					List<Obs> obsList = wrapped.allObs(drugDuration
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							drugDurationVal = drugDurationVal.concat(obs
									.getValueNumeric().toString());
						}	
					}
				}
				

				if (artReceivedPlace != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							artReceivedPlace.getEncounter());
					List<Obs> obsList = wrapped.allObs(artReceivedPlace.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							artReceivedPlaceValue = artReceivedPlaceValue.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}


				String val = drugNameVal + ", " + drugDurationVal + ", " + artReceivedPlaceValue;
				drugMembers.put(indexDrug, val);
				indexDrug++;

			}
		}
		model.addAttribute("drugMembers", drugMembers);
		
	
/*		
		Obs drugFormGroup = getAllLatestObs(patient,
				Dictionary.DRUG_HISTORY_GROUP);

		Map<Integer, String> drugList = new HashMap<Integer, String>();
		Integer drugIndex = 0;
		if (drugFormGroup != null) {
			EncounterWrapper wrappedObsGroup = new EncounterWrapper(
					drugFormGroup.getEncounter());
			List<Obs> obsGroupList = wrappedObsGroup.allObs(drugFormGroup
					.getConcept());
			for (Obs obsG : obsGroupList) {
				String drugNameVal = "";
				

				if (drugName != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							drugName.getEncounter());
					List<Obs> obsList = wrapped.allObs(drugName.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							drugNameVal = drugNameVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}



				String val = drugNameVal;
				drugList.put(drugIndex, val);
				drugIndex++;

			}
		} */
		

		/*
		 * Personal History
		 */
		Obs obstetricHistoryDetail = getAllLatestObs(patient,
				Dictionary.OBSTETRIC_HIS_DETAIL);
		Obs infantName = getAllLatestObs(patient, Dictionary.INFANT_NAME);
		Obs infantBirtdate = getAllLatestObs(patient,
				Dictionary.INFANT_BIRTDATE);
		Obs infantFeedingPractice = getAllLatestObs(patient,
				Dictionary.INFANT_FEEDING_METHOD);
		Obs infantCptDate = getAllLatestObs(patient, Dictionary.INFANT_CPT_DATE);
		Obs infantTestType = getAllLatestObs(patient,
				Dictionary.INFANT_TEST_TYPE);
		Obs infantResult = getAllLatestObs(patient,
				Dictionary.RESULT_OF_HIV_TEST);
		Obs infantResultDate = getAllLatestObs(patient,
				Dictionary.DATE_OF_PARTNER_HIV_DIAGNOSIS);
		Obs infantStatus = getAllLatestObs(patient, Dictionary.INFANT_STATUS);
		Obs infantUniqueId = getAllLatestObs(patient,
				Dictionary.INFANT_UNIQUE_ID);

		Map<Integer, String> infantList = new HashMap<Integer, String>();
		Integer infantIndex = 0;
		if (obstetricHistoryDetail != null) {
			EncounterWrapper wrappedObsGroup = new EncounterWrapper(
					obstetricHistoryDetail.getEncounter());
			List<Obs> obsGroupList = wrappedObsGroup
					.allObs(obstetricHistoryDetail.getConcept());
			for (Obs obsG : obsGroupList) {
				String infantNameVal = "";
				String infantBirtdateVal = "";
				String infantFeedingPracticeVal = "";
				String infantCptDateVal = "";
				String infantTestTypeVal = "";
				String infantResultVal = "";
				String infantResultDateVal = "";
				String infantStatusVal = "";
				String infantUniqueIdVal = "";

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

				if (infantBirtdate != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantBirtdate.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantBirtdate
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantBirtdateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());
						}
					}
				}

				if (infantFeedingPractice != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantFeedingPractice.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantFeedingPractice
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantFeedingPracticeVal = infantFeedingPracticeVal
									.concat(obs.getValueCoded().getName().toString());
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

				if (infantTestType != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantTestType.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantTestType
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantTestTypeVal = infantTestTypeVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}

				if (infantResult != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantResult.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantResult
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantResultVal = infantResultVal.concat(obs
									.getValueCoded().getName().toString());
						}
					}
				}

				if (infantResultDate != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantResultDate.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantResultDate
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantResultDateVal = new SimpleDateFormat("dd-MMMM-yyyy").format(obs.getValueDate());	
						}
					}
				}

				if (infantStatus != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantStatus.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantStatus
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantStatusVal = infantStatusVal.concat(obs
									.getValueText().toString());
						}
					}
				}

				if (infantUniqueId != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantUniqueId.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantUniqueId
							.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantUniqueIdVal = infantUniqueIdVal.concat(obs
									.getValueText().toString());
						}
					}
				}

				String val = infantNameVal + ", " + infantBirtdateVal + ", "
						+ infantFeedingPracticeVal + ", " + infantCptDateVal
						+ ", " + infantTestTypeVal + ", " + infantResultVal + ", " + infantResultDateVal 
						+ ", " + infantStatusVal +", " +infantUniqueIdVal;
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
		 * Get regimen history
		 * */
		Map<Integer, String> regimenList = new HashMap<Integer, String>();
		Integer regimenIndex = 0;
		
		List<DrugOrder> orderList =  Context.getOrderService().getDrugOrdersByPatient(patient);
		
		List<Encounter> encounterList =  Context.getEncounterService().getEncounters(patient);
		for(Encounter en : encounterList ){
			String regName = "";
			String changeStopReason = "";
			if(en.getEncounterType().getUuid().equals("00d1b629-4335-4031-b012-03f8af3231f8")){
				List<Order> orderListByEn =  Context.getOrderService().getOrdersByEncounter(en);
					for(Order o : orderListByEn){
						DrugOrder dr = Context.getOrderService().getDrugOrder(o.getOrderId());
						if(regName.equals("")){
							regName = regName.concat(dr.getConcept().getName() + "(" + dr.getDose()+dr.getUnits()+" "+dr.getFrequency()+")");	
						}
						else{
							regName = regName.concat(" + " +dr.getConcept().getName() + "(" + dr.getDose()+dr.getUnits()+" "+dr.getFrequency()+")");
						}
						if(dr.getDiscontinuedReason()!=null){
							changeStopReason = dr.getDiscontinuedReason().getName().toString();	
						}
					}
					
					if(regName!=""){
						regimenList.put(regimenIndex,new SimpleDateFormat("dd-MMMM-yyyy").format(en.getDateCreated()) + ", " + changeStopReason+ ", "+ new SimpleDateFormat("dd-MMMM-yyyy").format(en.getDateCreated()) + ", "+regName  );
						regimenIndex++;
					}
				}
		}
		model.addAttribute("regimenList", regimenList);

		
//		List<DrugOrder> completedOrders = new ArrayList<DrugOrder>();


			
//		model.put("completedOrders", completedOrders);
		
		/*
		 * Varaible for each visit
		 */
		if(patient.getAge() > 12){
			model.addAttribute("graphingConcepts", Dictionary.getConcepts(
					Dictionary.RETURN_VISIT_DATE, Dictionary.WEIGHT_KG,
					Dictionary.HEIGHT_CM, Dictionary.CURRENT_WHO_STAGE,
					Dictionary.PERFORMANCE,
					Dictionary.PREGNANCY_STATUS,
					Dictionary.EXPECTED_DATE_OF_DELIVERY,
					Dictionary.METHOD_OF_FAMILY_PLANNING, Dictionary.OI_TB_FORM,
					Dictionary.MEDICATION_ORDERS, Dictionary.TB_PATIENT,
					Dictionary.CD4_COUNT,
					Dictionary.HIV_VIRAL_LOAD));
		}
		else{
			model.addAttribute("graphingConcepts", Dictionary.getConcepts(
					Dictionary.RETURN_VISIT_DATE, Dictionary.WEIGHT_KG,
					Dictionary.HEIGHT_CM, Dictionary.CURRENT_WHO_STAGE,
					Dictionary.PERFORMANCE,
					Dictionary.PREGNANCY_STATUS,
					Dictionary.EXPECTED_DATE_OF_DELIVERY,
					Dictionary.METHOD_OF_FAMILY_PLANNING, Dictionary.OI_TB_FORM,
					Dictionary.MEDICATION_ORDERS, Dictionary.TB_PATIENT,
					Dictionary.CD4_PERCENT,
					Dictionary.HIV_VIRAL_LOAD));
		}
		

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