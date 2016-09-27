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

package org.openmrs.module.kenyaemr.fragment.controller.patient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.antlr.treewalker.PreOrderTraversal;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.Program;
import org.openmrs.Visit;
import org.openmrs.VisitAttribute;
import org.openmrs.VisitAttributeType;
import org.openmrs.VisitType;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculation;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.ResultUtil;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.calculation.library.RecordedDeceasedCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.validator.TelephoneNumberValidator;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.module.kenyaui.form.AbstractWebForm;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.MethodParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for creating and editing patients in the registration app
 */
public class EditPatientFragmentController {

	// We don't record cause of death, but data model requires a concept
	private static final String CAUSE_OF_DEATH_PLACEHOLDER = Dictionary.UNKNOWN;

	/**
	 * Main controller method
	 * 
	 * @param patient
	 *            the patient (may be null)
	 * @param person
	 *            the person (may be null)
	 * @param model
	 *            the model
	 */
	public void controller(
			@FragmentParam(value = "patient", required = false) Patient patient,
			@FragmentParam(value = "person", required = false) Person person,
			FragmentModel model) {

		if (patient != null && person != null) {
			throw new RuntimeException(
					"A patient or person can be provided, but not both");
		}

		Person existing = patient != null ? patient : person;

		model.addAttribute("command", newEditPatientForm(existing));

		model.addAttribute("occupationConcept",
				Dictionary.getConcept(Dictionary.OCCUPATION));

		model.addAttribute("entryPointList",
				Dictionary.getConcept(Dictionary.METHOD_OF_ENROLLMENT));
		

		model.addAttribute("townShipList",
				Dictionary.getConcept(Dictionary.TOWNSHIP));
		model.addAttribute("treatmentList",
				Dictionary.getConcept(Dictionary.TREATMENT_CENTER));
		Obs savedEntryPointConcept;
		savedEntryPointConcept = getLatestObs(patient,
				Dictionary.METHOD_OF_ENROLLMENT);
		if (savedEntryPointConcept != null) {
			model.addAttribute("pointentry",
					savedEntryPointConcept.getValueCoded());
		} else {
			model.addAttribute("pointentry", 0);
		}
		Obs tbHistory ;
		tbHistory = getLatestObs(patient,
				Dictionary.TB_PATIENT);
		if (tbHistory != null) {
			model.addAttribute("tbHistory",
					tbHistory.getValueCoded());
		} else {
			model.addAttribute("tbHistory", 0);
		}
		//Second line drug
		Obs secondlineDrug ;
		secondlineDrug = getLatestObs(patient,
				Dictionary.SECOND_LINE_DRUG);
		if (secondlineDrug != null) {
			model.addAttribute("secondlineDrug",
					secondlineDrug.getValueCoded());
		} else {
			model.addAttribute("secondlineDrug", 0);
		}
	/*
		model.addAttribute("tbRegimenType",
				Dictionary.getConcept(Dictionary.TB_FORM_REGIMEN));
	*/	
		model.addAttribute("outcome",
				Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME));
		
		model.addAttribute("getResultList",
				Dictionary.getConcept(Dictionary.TB_GENE_RESULT));
		
		Obs otherOccupation ;
		otherOccupation = getLatestObs(patient,
				Dictionary.OCCUPATION);
		if (otherOccupation != null) {
			model.addAttribute("statusother",
					otherOccupation.getValueCoded());
		} else {
			model.addAttribute("statusother", 0);
		}
		
		if (patient != null) {
			model.addAttribute("recordedAsDeceased",
					hasBeenRecordedAsDeceased(patient));
		} else {
			model.addAttribute("recordedAsDeceased", false);
		}

		// Create a list of cause of death answer concepts
		List<Concept> causeOfDeathOptions = new ArrayList<Concept>();
		causeOfDeathOptions.add(Dictionary.getConcept(Dictionary.UNKNOWN));
		model.addAttribute("causeOfDeathOptions", causeOfDeathOptions);

		// Algorithm to generate system generated patient Identifier
		Calendar now = Calendar.getInstance();
		String shortName = Context
				.getAdministrationService()
				.getGlobalProperty(
						OpenmrsConstants.GLOBAL_PROPERTY_PATIENT_IDENTIFIER_PREFIX);

		String noCheck = shortName
				+ String.valueOf(now.get(Calendar.YEAR)).substring(2, 4)
				+ String.valueOf(now.get(Calendar.MONTH) + 1)
				+ String.valueOf(now.get(Calendar.DATE))

				+ String.valueOf(now.get(Calendar.HOUR))
				+ String.valueOf(now.get(Calendar.MINUTE))
				+ String.valueOf(now.get(Calendar.SECOND))
				+ String.valueOf(new Random().nextInt(9999 - 999 + 1));

		if (patient != null) {
			PatientWrapper wrapper = new PatientWrapper(patient);
			model.addAttribute("patientIdentifier",
					wrapper.getSystemPatientId());
			model.addAttribute("patientId", wrapper.getTarget().getPatientId());
			model.addAttribute("labPatient", wrapper.getLabPatient());
		} else {
			model.addAttribute("patientIdentifier", noCheck + "-"
					+ generateCheckdigit(noCheck));
			model.addAttribute("patientId", null);
			model.addAttribute("labPatient", "");
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

	
	/**
	 * Checks if a patient has been recorded as deceased by a program
	 * 
	 * @param patient
	 *            the patient
	 * @return true if patient was recorded as deceased
	 */
	protected boolean hasBeenRecordedAsDeceased(Patient patient) {
		PatientCalculation calc = CalculationUtils.instantiateCalculation(
				RecordedDeceasedCalculation.class, null);
		return ResultUtil.isTrue(Context.getService(
				PatientCalculationService.class)
				.evaluate(patient.getId(), calc));
	}

	/**
	 * Using the Luhn Algorithm to generate check digits
	 * 
	 * @param idWithoutCheckdigit
	 * 
	 * @return idWithCheckdigit
	 */
	private static int generateCheckdigit(String input) {
		int factor = 2;
		int sum = 0;
		int n = 10;
		int length = input.length();

		if (!input.matches("[\\w]+"))
			throw new RuntimeException("Invalid character in patient id: "
					+ input);
		// Work from right to left
		for (int i = length - 1; i >= 0; i--) {
			int codePoint = input.charAt(i) - 48;
			// slight openmrs peculiarity to Luhn's algorithm
			int accum = factor * codePoint - (factor - 1)
					* (int) (codePoint / 5) * 9;

			// Alternate the "factor"
			factor = (factor == 2) ? 1 : 2;

			sum += accum;
		}

		int remainder = sum % n;
		return (n - remainder) % n;
	}

	/**
	 * Saves the patient being edited by this form
	 * 
	 * @param form
	 *            the edit patient form
	 * @param ui
	 *            the UI utils
	 * @return a simple object { patientId }
	 */
	public SimpleObject savePatient(
			@MethodParam("newEditPatientForm") @BindParams EditPatientForm form,
			UiUtils ui) {
		ui.validate(form, form, null);

		Patient patient = form.save();

		// if this patient is the current user i need to refresh the current
		// user
		if (patient.getPersonId().equals(
				Context.getAuthenticatedUser().getPerson().getPersonId())) {
			Context.refreshAuthenticatedUser();
		}

		return SimpleObject.create("id", patient.getId());
	}

	/**
	 * Creates an edit patient form
	 * 
	 * @param person
	 *            the person
	 * @return the form
	 */
	public EditPatientForm newEditPatientForm(
			@RequestParam(value = "personId", required = false) Person person) {
		if (person != null && person.isPatient()) {
			return new EditPatientForm((Patient) person); // For editing
															// existing patient
		} else if (person != null) {
			return new EditPatientForm(person); // For creating patient from
												// existing person
		} else {
			return new EditPatientForm(); // For creating patient and person
											// from scratch
		}

	}

	/**
	 * The form command object for editing patients
	 */
	public class EditPatientForm extends AbstractWebForm {

		private Person original;
		private Location location;
		private PersonName personName;
		private Date birthdate;
		private Boolean birthdateEstimated;
		private String gender;
		private PersonAddress personAddress;
		private Concept maritalStatus;
		private Concept occupation;
		private Concept entryPoint;
		private String entrySourceId;
		private Obs savedOccupation;
		private Obs savedEntryPoint;
		private Concept tbHistoryStatus;
		private Obs savedTBHistoryStatus;
		private Boolean dead = false;
		private Date deathDate;

		private String drTBSuspectNumber;
		private String systemPatientId;
		private String currentTownshipTBNumber;
		private String previousTownshipTBNumber;
		private String telephoneContact;

		private String fatherName;
		private String otherOccupation;
		private String checkInType;

		private String nationalId;
		private Concept placeOfBirth;
		private Obs savedPlaceOfBirth;
		private String dateOfRegistration;
		private Concept township;
		private Obs savedTownship;
		
		private String previousRegimenType;
		private Date previousRegimenStartDate;
		private Concept previousRegimenStartDateType;
		private Obs savedPreviousRegimenStartDateType;
		private Concept previousTBOutcome;
		private Obs savedPreviousTBOutcome;
		private Date previousTBOutcomeDate;
		
		private String genSampleId;
		private Date genSpecificationDate;
		private String genSpecificationPlace;
	
		private Concept genResult;
		private Obs savedGenResult;
		private Date genResultDate;
		private String labPatient;
		//On second line drug
		private Concept onSecondlineDrug;
		private Obs savedSecondlineStatus;
		private String specifysecondLine;
		 //Treatment Center
		private Concept treatmentCenter;
		private Obs saveTreatmentCenter;
	
		
		/**
		 * Creates an edit form for a new patient
		 */
		public EditPatientForm() {
			location = Context.getService(KenyaEmrService.class)
					.getDefaultLocation();

			personName = new PersonName();
			personAddress = new PersonAddress();
		}

		/**
		 * Creates an edit form for an existing patient
		 */
		public EditPatientForm(Person person) {
			this();

			original = person;

			if (person.getPersonName() != null) {
				personName = person.getPersonName();
			} else {
				personName.setPerson(person);
			}

			if (person.getPersonAddress() != null) {
				personAddress = person.getPersonAddress();
			} else {
				personAddress.setPerson(person);
			}

			gender = person.getGender();
			birthdate = person.getBirthdate();
		//	birthdateEstimated = person.getBirthdateEstimated();
			dead = person.isDead();
			deathDate = person.getDeathDate();

			PersonWrapper wrapper = new PersonWrapper(person);
			telephoneContact = wrapper.getTelephoneContact();
		}

		/**
		 * Creates an edit form for an existing patient
		 */
		public EditPatientForm(Patient patient) {
			this((Person) patient);

			PatientWrapper wrapper = new PatientWrapper(patient);

			drTBSuspectNumber = wrapper.getDrTBSuspectNumber();
			systemPatientId = wrapper.getSystemPatientId();

			fatherName = wrapper.getFatherName();
			nationalId = wrapper.getNationalId();
			entrySourceId = wrapper.getEntrySourceId();
			currentTownshipTBNumber = wrapper.getCurrentTownshipTBNumber();
			previousTownshipTBNumber = wrapper.getPreviousTownshipTBNumber();
			genSampleId = wrapper.getGenSampleId() ;
			specifysecondLine=wrapper.getSecondline();
			
			try {
				String datestr = wrapper.getGenSpecificationDate();
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MMMM-yyyy");
				genSpecificationDate = (Date) formatter.parse(datestr);
			} catch (Exception e) {
			}
		
			genSpecificationPlace = wrapper.getGenSpecificationPlace();
			
			previousRegimenType = wrapper.getPreviousRegimenType();
			
			try {
				String datestr = wrapper.getPreviousRegimenStartDate();
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd-MMMM-yyyy");
				previousRegimenStartDate = (Date) formatter.parse(datestr);
			} catch (Exception e) {
			}
		
			saveTreatmentCenter = getLatestObs(patient, Dictionary.TREATMENT_CENTER);
			if (saveTreatmentCenter != null) {
				treatmentCenter = saveTreatmentCenter.getValueCoded();
				
			}
			savedOccupation = getLatestObs(patient, Dictionary.OCCUPATION);
			if (savedOccupation != null) {
				occupation = savedOccupation.getValueCoded();
				otherOccupation = savedOccupation.getValueText();
			}

			savedEntryPoint = getLatestObs(patient,
					Dictionary.METHOD_OF_ENROLLMENT);
			if (savedEntryPoint != null) {
				entryPoint = savedEntryPoint.getValueCoded();
			}
	
		/*	savedTownship = getLatestObs(patient,
					Dictionary.TOWNSHIP);
			if (savedTownship != null) {
				township = savedTownship.getValueCoded();
			}*/
			List<Obs>saveTownship=Context.getObsService().getObservationsByPersonAndConcept(patient, Dictionary.getConcept(Dictionary.TOWNSHIP));
			if(saveTownship!=null)
			{for(Obs townshipp:saveTownship)
			{
				if(townshipp.getEncounter()==null)
				{
				township = townshipp.getValueCoded();
			}
			}
			}
			savedPlaceOfBirth = getLatestObs(patient,
					Dictionary.LOCATION_OF_BIRTH);
			if (savedPlaceOfBirth != null) {
				placeOfBirth = savedPlaceOfBirth.getValueCoded();
			}

			savedTBHistoryStatus = getLatestObs(patient, Dictionary.TB_PATIENT);
			if(savedTBHistoryStatus != null){
				tbHistoryStatus = savedTBHistoryStatus.getValueCoded();
			}
			
			savedSecondlineStatus = getLatestObs(patient, Dictionary.SECOND_LINE_DRUG);
			if(savedSecondlineStatus != null){
				onSecondlineDrug = savedSecondlineStatus.getValueCoded();
			}
			
			savedPreviousRegimenStartDateType = getLatestObs(patient,
					Dictionary.TB_REGIMEN_START_DATE_TYPE);
			if (savedPreviousRegimenStartDateType != null) {
				previousRegimenStartDateType = savedPreviousRegimenStartDateType.getValueCoded();
			}
			
			savedPreviousTBOutcome = getLatestObs(patient, Dictionary.PREVIOUS_TREATMENT_OUTCOME);
			if(savedPreviousTBOutcome!=null){
				previousTBOutcome = savedPreviousTBOutcome.getValueCoded();
				previousTBOutcomeDate = savedPreviousTBOutcome.getValueDate();
			}
			
			savedGenResult = getLatestObs(patient, Dictionary.TB_GENE_RESULT);
			if(savedGenResult!=null){
				genResult = savedGenResult.getValueCoded();
				genResultDate = savedGenResult.getValueDate();
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

		/**
		 * @see org.springframework.validation.Validator#validate(java.lang.Object,
		 *      org.springframework.validation.Errors)
		 */
		@Override
		public void validate(Object target, Errors errors) {

			require(errors, "personName.givenName");
			// require(errors, "personName.familyName");

			if (personName.getGivenName().length() > 50) {
				errors.rejectValue("personName.givenName",
						"Expected length of Name is exceeding");
			}
			;

		//	require(errors, "fatherName");
		//	require(errors, "currentTownshipTBNumber");
			
			if (fatherName.length() > 50) {
				errors.rejectValue("fatherName",
						"Expected length of Name is exceeding");
			}

			if (nationalId.length() > 50) {
				errors.rejectValue("nationalId",
						"Expected length of National Id is exceeding");
			}

			if (personAddress.getAddress1().length() > 200) {
				errors.rejectValue("personAddress.address1",
						"Length of Address is exceeding it's limit");
			}
			/*
			if (personAddress.getAddress2().length() <1 ) {
				errors.rejectValue("personAddress.address2",
						"Required");
			}*/

			if (personAddress.getAddress2().length() > 200) {
				errors.rejectValue("personAddress.address2",
						"Length of Address is exceeding it's limit");
			}
			
			if (personAddress.getAddress3().length() > 300) {
				errors.rejectValue("personAddress.address3",
						"Length of Address is exceeding it's limit");
			}
			
			require(errors, "gender");
			require(errors, "birthdate");
			require(errors, "drTBSuspectNumber");
			if(tbHistoryStatus!=null){
				if(tbHistoryStatus.getConceptId().toString().equals("1065")){
					require(errors, "previousTownshipTBNumber");
				}
			}
			

			if(genSpecificationDate!=null && genResultDate!=null){
				if(genResultDate.before(genSpecificationDate)){
					errors.rejectValue("genResultDate",
							"Can not be before Specimen collection date");
				}
				long diff = genResultDate.getTime() - genSpecificationDate.getTime();
				int dayDiff = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				if(dayDiff > 30){
					errors.rejectValue("genSpecificationDate",
							"Result date can not be after 30 days");
				}
			}
			
			
			
			
			
			// Require death details if patient is deceased
			if (dead) {
				require(errors, "deathDate");

				if (deathDate != null) {
					if (birthdate != null && deathDate.before(birthdate)) {
						errors.rejectValue("deathDate",
								"Cannot be before birth date");
					}
					if (deathDate.after(new Date())) {
						errors.rejectValue("deathDate",
								"Cannot be in the future");
					}
				}
			} else if (deathDate != null) {
				errors.rejectValue("deathDate",
						"Must be empty if patient not deceased");
			}

		      	if (StringUtils.isNotBlank(telephoneContact)) {
				validateField(errors, "telephoneContact",
						new TelephoneNumberValidator());
			}

			validateField(errors, "personAddress");

				
			validateIdentifierField(
					errors,
					"drTBSuspectNumber",
					CommonMetadata._PatientIdentifierType.DR_TB_SUSPECT_NUMBER);
			
			// check birth date against future dates and really old dates
			if (birthdate != null) {
				if (birthdate.after(new Date()))
					errors.rejectValue("birthdate", "error.date.future");
				else {
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.YEAR, -120); // person cannot be older than
												// 120 years old
					if (birthdate.before(c.getTime())) {
						errors.rejectValue("birthdate",
								"error.date.nonsensical");
					}
				}
			}
		}

		/**
		 * Validates an identifier field
		 * 
		 * @param errors
		 * @param field
		 * @param idTypeUuid
		 */
		protected void validateIdentifierField(Errors errors, String field,
				String idTypeUuid) {
			String value = (String) errors.getFieldValue(field);

			if (StringUtils.isNotBlank(value)) {
				PatientIdentifierType idType = MetadataUtils.existing(
						PatientIdentifierType.class, idTypeUuid);
				if (!value.matches(idType.getFormat())) {
					errors.rejectValue(field, idType.getFormatDescription());
				}

				PatientIdentifier stub = new PatientIdentifier(value, idType,
						null);

				if (original != null && original.isPatient()) { // Editing an
																// existing
																// patient
					stub.setPatient((Patient) original);
				}

				if (Context.getPatientService()
						.isIdentifierInUseByAnotherPatient(stub)) {
					errors.rejectValue(field, "In use by another patient");
				}
			}
		}

		/**
		 * @see org.openmrs.module.kenyaui.form.AbstractWebForm#save()
		 */
		@Override
		public Patient save() {
			Patient toSave;
			boolean isNewPatient = false;

			if (original != null && original.isPatient()) { // Editing an
															// existing patient
				toSave = (Patient) original;
			} else if (original != null) {
				toSave = new Patient(original); // Creating a patient from an
												// existing person
			} else {
				toSave = new Patient(); // Creating a new patient and person
				isNewPatient = true;
			}

			toSave.setGender(gender);
			toSave.setBirthdate(birthdate);
	//		toSave.setBirthdateEstimated(birthdateEstimated);
			toSave.setDead(dead);
			toSave.setDeathDate(deathDate);
			toSave.setCauseOfDeath(dead ? Dictionary
					.getConcept(CAUSE_OF_DEATH_PLACEHOLDER) : null);

			if (anyChanges(toSave.getPersonName(), personName, "givenName")) {
				if (toSave.getPersonName() != null) {
					voidData(toSave.getPersonName());
				}
				personName.setGivenName(personName.getGivenName());
				personName.setFamilyName("(NULL)");
				toSave.addName(personName);
			}

			// toSave.

			if (anyChanges(toSave.getPersonAddress(), personAddress,
					"address1", "address2", "address5", "address6",
					"countyDistrict", "address3", "cityVillage",
					"stateProvince", "country", "postalCode", "address4")) {
				if (toSave.getPersonAddress() != null) {
					voidData(toSave.getPersonAddress());
				}
				toSave.addAddress(personAddress);
			}

			PatientWrapper wrapper = new PatientWrapper(toSave);

			wrapper.getPerson().setTelephoneContact(telephoneContact);
			// wrapper.setNationalIdNumber(nationalIdNumber, location);
			// wrapper.setPatientClinicNumber(patientClinicNumber, location);
			wrapper.setDrTBSuspectNumber(drTBSuspectNumber,
					location);
			
			wrapper.setSystemPatientId(systemPatientId, location);

			wrapper.getPerson().setFatherName(fatherName);
			wrapper.getPerson().setNationalId(nationalId);
			wrapper.getPerson().setEntrySourceId(entrySourceId);
			wrapper.getPerson().setPreviousTownshipTBNumber(previousTownshipTBNumber);
			wrapper.getPerson().setCurrentTownshipTBNumber(currentTownshipTBNumber);
			wrapper.getPerson().setGenSampleId(genSampleId);
			wrapper.getPerson().setGenSpecificationPlace(genSpecificationPlace);
			wrapper.getPerson().setPreviousRegimenType(previousRegimenType);
			if(onSecondlineDrug!=null)
			{
				if(onSecondlineDrug.getConceptId()==1065)
					{
					
					wrapper.getPerson().setSecondline(specifysecondLine);
					
					}
			}
			
			
			if(labPatient!=null){
				wrapper.getPerson().setLabPatient(labPatient);
			}
			else{
				wrapper.getPerson().setLabPatient("false");
			}

			DateFormat testDate = new SimpleDateFormat("dd-MMMM-yyyy");
			if(genSpecificationDate!=null){
				Date capturedTestDate = genSpecificationDate;
				wrapper.setGenSpecificationDate(testDate
							.format(capturedTestDate));
			}
			
			if(previousRegimenStartDate!=null){
				Date startDate = previousRegimenStartDate;
				wrapper.setPreviousRegimenStartDate(testDate
							.format(startDate));
			}

			// Make sure everyone gets an OpenMRS ID
			PatientIdentifierType openmrsIdType = MetadataUtils.existing(
					PatientIdentifierType.class,
					CommonMetadata._PatientIdentifierType.OPENMRS_ID);
			PatientIdentifier openmrsId = toSave
					.getPatientIdentifier(openmrsIdType);

			if (openmrsId == null) {
				String generated = Context.getService(
						IdentifierSourceService.class).generateIdentifier(
						openmrsIdType, "Registration");
				openmrsId = new PatientIdentifier(generated, openmrsIdType,
						location);
				toSave.addIdentifier(openmrsId);

				if (!toSave.getPatientIdentifier().isPreferred()) {
					openmrsId.setPreferred(true);
				}
			}

			Patient ret = Context.getPatientService().savePatient(toSave);

			// Explicitly save all identifier objects including voided
			for (PatientIdentifier identifier : toSave.getIdentifiers()) {
				Context.getPatientService().savePatientIdentifier(identifier);
			}

			// Save remaining fields as obs
			List<Obs> obsToSave = new ArrayList<Obs>();
			List<Obs> obsToVoid = new ArrayList<Obs>();

			handleOncePerPatientObs(ret, obsToSave, obsToVoid,
					Dictionary.getConcept(Dictionary.METHOD_OF_ENROLLMENT),
					savedEntryPoint, entryPoint);
			
			handleOncePerPatientObs(ret, obsToSave, obsToVoid,
					Dictionary.getConcept(Dictionary.TOWNSHIP),
					savedTownship, township);
			
			handleOncePerPatientObs(ret, obsToSave, obsToVoid,
					Dictionary.getConcept(Dictionary.LOCATION_OF_BIRTH),
					savedPlaceOfBirth, placeOfBirth);

			handleOncePerPatientObs(ret, obsToSave, obsToVoid,
					Dictionary.getConcept(Dictionary.TB_REGIMEN_START_DATE_TYPE),
					savedPreviousRegimenStartDateType, previousRegimenStartDateType);
			
			handleOncePerPatientObs(ret, obsToSave, obsToVoid,
					Dictionary.getConcept(Dictionary.TB_PATIENT),
					savedTBHistoryStatus, tbHistoryStatus);
			handleOncePerPatientObs(ret, obsToSave, obsToVoid,
					Dictionary.getConcept(Dictionary.SECOND_LINE_DRUG),
					savedSecondlineStatus, onSecondlineDrug);
			
			handleOncePerPatientObs(ret, obsToSave, obsToVoid,
					Dictionary.getConcept(Dictionary.TREATMENT_CENTER),
					saveTreatmentCenter,treatmentCenter);
			if (occupation != null) {
				if (occupation.getName().toString().equals("Other")) {
					handleOncePerPatientObs(
							ret,
							obsToSave,
							obsToVoid,
							Dictionary.getConcept(Dictionary.OCCUPATION),
							savedOccupation, occupation,
							otherOccupation, null);
				}

				else {
					handleOncePerPatientObs(
							ret,
							obsToSave,
							obsToVoid,
							Dictionary.getConcept(Dictionary.OCCUPATION),
							savedOccupation, occupation, null,
							null);
				}
			}
			
			if(genResult != null){
				handleOncePerPatientObs(
						ret,
						obsToSave,
						obsToVoid,
						Dictionary.getConcept(Dictionary.TB_GENE_RESULT),
						savedGenResult, genResult, null,
						genResultDate);
			}
			
			if(previousTBOutcome != null){
				handleOncePerPatientObs(
						ret,
						obsToSave,
						obsToVoid,
						Dictionary.getConcept(Dictionary.PREVIOUS_TREATMENT_OUTCOME),
						savedPreviousTBOutcome, previousTBOutcome, null,
						previousTBOutcomeDate);
			}
			
			for (Obs o : obsToVoid) {
				Context.getObsService().voidObs(o, "KenyaEMR edit patient");
			}

			for (Obs o : obsToSave) {
				Context.getObsService().saveObs(o, "KenyaEMR edit patient");
			}

			if (checkInType.equals("1")) {
			}
			else{	
				
				Date curDate = new Date();
				SimpleDateFormat mysqlDateTimeFormatter = new SimpleDateFormat(
						"dd-MMM-yy HH:mm:ss");
				Date date = null;
				try {
					date = mysqlDateTimeFormatter.parse(dateOfRegistration
							+ " " + curDate.getHours() + ":" + curDate.getMinutes()
							+ ":" + curDate.getSeconds());
				} catch (ParseException e) {
					date = curDate;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Visit visit = new Visit();
				visit.setPatient(ret);
				visit.setStartDatetime(date);
				visit.setVisitType(MetadataUtils.existing(VisitType.class,
						CommonMetadata._VisitType.OUTPATIENT));
				visit.setLocation(Context.getService(KenyaEmrService.class)
						.getDefaultLocation());

				if (isNewPatient) {
					VisitAttributeType attrType = Context.getService(
							VisitService.class).getVisitAttributeTypeByUuid(
							CommonMetadata._VisitAttributeType.NEW_PATIENT);
					if (attrType != null) {
						VisitAttribute attr = new VisitAttribute();
						attr.setAttributeType(attrType);
						attr.setVisit(visit);
						attr.setDateCreated(date);
						attr.setValue(true);
						visit.addAttribute(attr);
					}
				}
				Visit visitSave = Context.getVisitService().saveVisit(visit);
				
			}

			return ret;
		}

		/**
		 * Handles saving a field which is stored as an obs
		 * 
		 * @param patient
		 *            the patient being saved
		 * @param obsToSave
		 * @param obsToVoid
		 * @param question
		 * @param savedObs
		 * @param newValue
		 */
		protected void handleOncePerPatientObs(Patient patient,
				List<Obs> obsToSave, List<Obs> obsToVoid, Concept question,
				Obs savedObs, Concept newValue) {
			if (!OpenmrsUtil.nullSafeEquals(
					savedObs != null ? savedObs.getValueCoded() : null,
					newValue)) {
				// there was a change
				if (savedObs != null && newValue == null) {
					// treat going from a value to null as voiding all past
					// civil status obs
					obsToVoid.addAll(Context.getObsService()
							.getObservationsByPersonAndConcept(patient,
									question));
				}
				if (newValue != null) {
					Obs o = new Obs();
					o.setPerson(patient);
					o.setConcept(question);
					o.setObsDatetime(new Date());
					o.setLocation(Context.getService(KenyaEmrService.class)
							.getDefaultLocation());
					o.setValueCoded(newValue);
					obsToSave.add(o);
				}
			}
		}
		
		protected void handleOncePerPatientObs(Patient patient,
				List<Obs> obsToSave, List<Obs> obsToVoid, Concept question,
				Obs savedObs, Concept newValue, String textValue,
				Date textDate) {
			
			if (!OpenmrsUtil.nullSafeEquals(
					savedObs != null ? savedObs.getValueCoded() : null,
					newValue)) {
				// there was a change
				if (savedObs != null && newValue == null) {
					// treat going from a value to null as voiding all past
					// civil status obs
					obsToVoid.addAll(Context.getObsService()
							.getObservationsByPersonAndConcept(patient,
									question));
				}
				
				if (newValue != null) {
					Obs o = new Obs();
					o.setPerson(patient);
					o.setConcept(question);
					o.setObsDatetime(new Date());
					o.setLocation(Context.getService(KenyaEmrService.class)
							.getDefaultLocation());
					if(newValue!=null){
						o.setValueCoded(newValue);	
					}
					
					if(textValue!=null){
						o.setValueText(textValue);	
					}
					if (textDate != null) {
						o.setValueDate(textDate);
					}
					obsToSave.add(o);
				}
			}
		}

		public boolean isInHivProgram() {
			if (original == null || !original.isPatient()) {
				return false;
			}
			ProgramWorkflowService pws = Context.getProgramWorkflowService();
			Program hivProgram = MetadataUtils.existing(Program.class,
					HivMetadata._Program.HIV);
			for (PatientProgram pp : pws.getPatientPrograms((Patient) original,
					hivProgram, null, null, null, null, false)) {
				if (pp.getActive()) {
					return true;
				}
			}
			return false;
		}

		/**
		 * @return the original
		 */
		public Person getOriginal() {
			return original;
		}

		/**
		 * @param original
		 *            the original to set
		 */
		public void setOriginal(Patient original) {
			this.original = original;
		}

		/**
		 * @return the personName
		 */
		public PersonName getPersonName() {
			return personName;
		}

		/**
		 * @param personName
		 *            the personName to set
		 */
		public void setPersonName(PersonName personName) {
			this.personName = personName;
		}

	
		public Concept getEntryPoint() {
			return entryPoint;
		}

		public void setEntryPoint(Concept entryPoint) {
			this.entryPoint = entryPoint;
		}
		
		public String getOtherOccupation() {
			return otherOccupation;
		}

		public void setOtherOccupation(String otherOccupation) {
			this.otherOccupation = otherOccupation;
		}

		public Obs getSavedEntryPoint() {
			return savedEntryPoint;
		}


		public void setSavedEntryPoint(Obs savedEntryPoint) {
			this.savedEntryPoint = savedEntryPoint;
		}

		public String getDrTBSuspectNumber() {
			return drTBSuspectNumber;
		}

		public void setDrTBSuspectNumber(String drTBSuspectNumber) {
			this.drTBSuspectNumber = drTBSuspectNumber;
		}

		public String getSystemPatientId() {
			return systemPatientId;
		}

		public void setSystemPatientId(String systemPatientId) {
			this.systemPatientId = systemPatientId;
		}

		public String getCheckInType() {
			return checkInType;
		}

		public void setCheckInType(String checkInType) {
			this.checkInType = checkInType;
		}

	
		/**
		 * @return the patientClinicNumber
		 * 
		 *         public String getPatientClinicNumber() { return
		 *         patientClinicNumber; }
		 */
		/**
		 * @param patientClinicNumber
		 *            the patientClinicNumber to set
		 * 
		 *            public void setPatientClinicNumber(String
		 *            patientClinicNumber) { this.patientClinicNumber =
		 *            patientClinicNumber; }
		 */

		/**
		 * @return the nationalIdNumber
		 * 
		 *         public String getNationalIdNumber() { return
		 *         nationalIdNumber; }
		 */
		/**
		 * @param nationalIdNumber
		 *            the nationalIdNumber to set
		 * 
		 *            public void setNationalIdNumber(String nationalIdNumber) {
		 * 
		 *            this.nationalIdNumber = nationalIdNumber; }
		 */
		/**
		 * @return the birthdate
		 */
		public Date getBirthdate() {
			return birthdate;
		}

		/**
		 * @param birthdate
		 *            the birthdate to set
		 */
		public void setBirthdate(Date birthdate) {
			this.birthdate = birthdate;
		}

		/**
		 * @return the birthdateEstimated
		 */
		public Boolean getBirthdateEstimated() {
			return birthdateEstimated;
		}

		/**
		 * @param birthdateEstimated
		 *            the birthdateEstimated to set
		 */
		public void setBirthdateEstimated(Boolean birthdateEstimated) {
			this.birthdateEstimated = birthdateEstimated;
		}

		/**
		 * @return the gender
		 */
		public String getGender() {
			return gender;
		}

		/**
		 * @param gender
		 *            the gender to set
		 */
		public void setGender(String gender) {
			this.gender = gender;
		}

		/**
		 * @return the personAddress
		 */
		public PersonAddress getPersonAddress() {
			return personAddress;
		}

		/**
		 * @param personAddress
		 *            the personAddress to set
		 */
		public void setPersonAddress(PersonAddress personAddress) {
			this.personAddress = personAddress;
		}

		/**
		 * @return the maritalStatus
		 */
		public Concept getMaritalStatus() {
			return maritalStatus;
		}

		/**
		 * @param maritalStatus
		 *            the maritalStatus to set
		 */
		public void setMaritalStatus(Concept maritalStatus) {
			this.maritalStatus = maritalStatus;
		}


		/**
		 * @return the occupation
		 */
		public Concept getOccupation() {
			return occupation;
		}

		/**
		 * @param occupation
		 *            the occupation to set
		 */
		public void setOccupation(Concept occupation) {
			this.occupation = occupation;
		}

		/**
		 * @return the telephoneContact
		 */
		public String getTelephoneContact() {
			return telephoneContact;
		}

		/**
		 * @param telephoneContact
		 *            the telephoneContact to set
		 */
		public void setTelephoneContact(String telephoneContact) {
			this.telephoneContact = telephoneContact;
		}

		public Boolean getDead() {
			return dead;
		}

		public void setDead(Boolean dead) {
			this.dead = dead;
		}

		public Date getDeathDate() {
			return deathDate;
		}

		public void setDeathDate(Date deathDate) {
			this.deathDate = deathDate;
		}

	
		public String getFatherName() {
			return fatherName;
		}

		public void setFatherName(String fatherName) {
			this.fatherName = fatherName;
		}

		public String getNationalId() {
			return nationalId;
		}

		public void setNationalId(String nationalId) {
			this.nationalId = nationalId;
		}

		public String getDateOfRegistration() {
			return dateOfRegistration;
		}

		public void setDateOfRegistration(String dateOfRegistration) {
			this.dateOfRegistration = dateOfRegistration;
		}

		public String getEntrySourceId() {
			return entrySourceId;
		}

		public void setEntrySourceId(String entrySourceId) {
			this.entrySourceId = entrySourceId;
		}

		public String getCurrentTownshipTBNumber() {
			return currentTownshipTBNumber;
		}

		public void setCurrentTownshipTBNumber(String currentTownshipTBNumber) {
			this.currentTownshipTBNumber = currentTownshipTBNumber;
		}

		public String getPreviousTownshipTBNumber() {
			return previousTownshipTBNumber;
		}

		public void setPreviousTownshipTBNumber(String previousTownshipTBNumber) {
			this.previousTownshipTBNumber = previousTownshipTBNumber;
		}

		public Concept getTbHistoryStatus() {
			return tbHistoryStatus;
		}

		public void setTbHistoryStatus(Concept tbHistoryStatus) {
			this.tbHistoryStatus = tbHistoryStatus;
		}

		public Obs getSavedOccupation() {
			return savedOccupation;
		}

		public void setSavedOccupation(Obs savedOccupation) {
			this.savedOccupation = savedOccupation;
		}

		public Obs getSavedTBHistoryStatus() {
			return savedTBHistoryStatus;
		}

		public void setSavedTBHistoryStatus(Obs savedTBHistoryStatus) {
			this.savedTBHistoryStatus = savedTBHistoryStatus;
		}

		public Concept getTownship() {
			return township;
		}

		public void setTownship(Concept township) {
			this.township = township;
		}

		public Obs getSavedTownship() {
			return savedTownship;
		}

		public void setSavedTownship(Obs savedTownship) {
			this.savedTownship = savedTownship;
		}

		public String getPreviousRegimenType() {
			return previousRegimenType;
		}

		public void setPreviousRegimenType(String previousRegimenType) {
			this.previousRegimenType = previousRegimenType;
		}

		public Date getPreviousRegimenStartDate() {
			return previousRegimenStartDate;
		}

		public void setPreviousRegimenStartDate(Date previousRegimenStartDate) {
			this.previousRegimenStartDate = previousRegimenStartDate;
		}

		public Concept getPreviousRegimenStartDateType() {
			return previousRegimenStartDateType;
		}

		public void setPreviousRegimenStartDateType(Concept previousRegimenStartDateType) {
			this.previousRegimenStartDateType = previousRegimenStartDateType;
		}

		public Obs getSavedPreviousRegimenStartDateType() {
			return savedPreviousRegimenStartDateType;
		}

		public void setSavedPreviousRegimenStartDateType(
				Obs savedPreviousRegimenStartDateType) {
			this.savedPreviousRegimenStartDateType = savedPreviousRegimenStartDateType;
		}

		public Concept getPreviousTBOutcome() {
			return previousTBOutcome;
		}

		public void setPreviousTBOutcome(Concept previousTBOutcome) {
			this.previousTBOutcome = previousTBOutcome;
		}

		public Obs getSavedPreviousTBOutcome() {
			return savedPreviousTBOutcome;
		}

		public void setSavedPreviousTBOutcome(Obs savedPreviousTBOutcome) {
			this.savedPreviousTBOutcome = savedPreviousTBOutcome;
		}

		public Date getPreviousTBOutcomeDate() {
			return previousTBOutcomeDate;
		}

		public void setPreviousTBOutcomeDate(Date previousTBOutcomeDate) {
			this.previousTBOutcomeDate = previousTBOutcomeDate;
		}

		public String getGenSampleId() {
			return genSampleId;
		}

		public void setGenSampleId(String genSampleId) {
			this.genSampleId = genSampleId;
		}

		public Date getGenSpecificationDate() {
			return genSpecificationDate;
		}

		public void setGenSpecificationDate(Date genSpecificationDate) {
			this.genSpecificationDate = genSpecificationDate;
		}

		public String getGenSpecificationPlace() {
			return genSpecificationPlace;
		}

		public void setGenSpecificationPlace(String genSpecificationPlace) {
			this.genSpecificationPlace = genSpecificationPlace;
		}

		public Concept getGenResult() {
			return genResult;
		}

		public void setGenResult(Concept genResult) {
			this.genResult = genResult;
		}

		public Obs getSavedGenResult() {
			return savedGenResult;
		}

		public void setSavedGenResult(Obs savedGenResult) {
			this.savedGenResult = savedGenResult;
		}

		public Date getGenResultDate() {
			return genResultDate;
		}

		public void setGenResultDate(Date genResultDate) {
			this.genResultDate = genResultDate;
		}

		public String getLabPatient() {
			return labPatient;
		}

		public void setLabPatient(String labPatient) {
			this.labPatient = labPatient;
		}


		public Obs getSavedPlaceOfBirth() {
			return savedPlaceOfBirth;
		}

		public void setSavedPlaceOfBirth(Obs savedPlaceOfBirth) {
			this.savedPlaceOfBirth = savedPlaceOfBirth;
		}

		public Concept getPlaceOfBirth() {
			return placeOfBirth;
		}

		public void setPlaceOfBirth(Concept placeOfBirth) {
			this.placeOfBirth = placeOfBirth;
		}

		public Concept getOnSecondlineDrug() {
			return onSecondlineDrug;
		}

		public void setOnSecondlineDrug(Concept onSecondlineDrug) {
			this.onSecondlineDrug = onSecondlineDrug;
		}

	

		public Obs getSavedSecondlineStatus() {
			return savedSecondlineStatus;
		}

		public void setSavedSecondlineStatus(Obs savedSecondlineStatus) {
			this.savedSecondlineStatus = savedSecondlineStatus;
		}

		public String getSpecifysecondLine() {
			return specifysecondLine;
		}

		public void setSpecifysecondLine(String specifysecondLine) {
			this.specifysecondLine = specifysecondLine;
		}

		public Obs getSaveTreatmentCenter() {
			return saveTreatmentCenter;
		}

		public void setSaveTreatmentCenter(Obs saveTreatmentCenter) {
			this.saveTreatmentCenter = saveTreatmentCenter;
		}

		public Concept getTreatmentCenter() {
			return treatmentCenter;
		}

		public void setTreatmentCenter(Concept treatmentCenter) {
			this.treatmentCenter = treatmentCenter;
		}

	
		
		

	}

}