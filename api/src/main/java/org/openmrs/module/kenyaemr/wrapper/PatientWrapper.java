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

package org.openmrs.module.kenyaemr.wrapper;

import java.util.Date;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.module.kenyacore.wrapper.AbstractPatientWrapper;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;

/**
 * Wrapper class for patients. Unfortunately this can't extend both AbstractPatientWrapper and PersonWrapper so we add a
 * PersonWrapper as a property.
 */
public class PatientWrapper extends AbstractPatientWrapper {

	private PersonWrapper person;

	/**
	 * Creates a new wrapper
	 * @param target the target
	 */
	public PatientWrapper(Patient target) {
		super(target);

		this.person = new PersonWrapper(target);
	}

	/**
	 * Gets the person wrapper
	 * @return the wrapper
	 */
	public PersonWrapper getPerson() {
		return person;
	}

	/**
	 * Gets the medical record number
	 * @return the identifier value
	 */
	public String getMedicalRecordNumber() {
		return getAsIdentifier(CommonMetadata._PatientIdentifierType.OPENMRS_ID);
	}

	/**
	 * Gets the patient clinic number
	 * @return the identifier value
	 */
	public String getPatientClinicNumber() {
		return getAsIdentifier(CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
	}

	/**
	 * Sets the patient clinic number
	 * @param value the identifier value
	 * @param location the identifier location
	 */
	public void setPatientClinicNumber(String value, Location location) {
		setAsIdentifier(CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER, value, location);
	}
	
	public void setMDRTBRegistrationNumber(String value, Location location) {
		setAsIdentifier(CommonMetadata._PatientIdentifierType.MDR_TB_REGISTRATION_NUMBER, value, location);
	}

	/**
	 * Gets the MDR TB REGISTRATION_NUMBER
	 * @return the identifier value
	 */
	public String getMDRTBRegistration() {
		return getAsIdentifier(CommonMetadata._PatientIdentifierType.MDR_TB_REGISTRATION_NUMBER);
	}
	
	
	public void setDrTBSuspectNumber(String value, Location location) {
		setAsIdentifier(CommonMetadata._PatientIdentifierType.DR_TB_SUSPECT_NUMBER, value, location);
	}

	/**
	 * Gets the DR TB suspect Number
	 * @return the identifier value
	 */
	public String getDrTBSuspectNumber() {
		return getAsIdentifier(CommonMetadata._PatientIdentifierType.DR_TB_SUSPECT_NUMBER);
	}
	
	/**
	 * Sets the SystemPatientId
	 * @param value the identifier value
	 * @param location the identifier location
	 */	
	public void setSystemPatientId(String value, Location location) {
		setAsIdentifier(CommonMetadata._PatientIdentifierType.SYSTEM_PATIENT_ID, value, location);
	}

	/**
	 * Gets the SystemPatientId
	 * @return the identifier value
	 */
	public String getSystemPatientId() {
		return getAsIdentifier(CommonMetadata._PatientIdentifierType.SYSTEM_PATIENT_ID);
	}
	
	
	/**
	 * Gets the national id number
	 * @return the identifier value
	 */
	public String getNationalIdNumber() {
		return getAsIdentifier(CommonMetadata._PatientIdentifierType.NATIONAL_ID);
	}

	/**
	 * Sets the national id number
	 * @param value the identifier value
	 * @param location the identifier location
	 */
	public void setNationalIdNumber(String value, Location location) {
		setAsIdentifier(CommonMetadata._PatientIdentifierType.NATIONAL_ID, value, location);
	}

	/**
	 * Gets the address of next of kin
	 * @return the address
	 */
	public String getNextOfKinAddress() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.NEXT_OF_KIN_ADDRESS);
	}

	/**
	 * Sets the address of next of kin
	 * @param value the address
	 */
	public void setNextOfKinAddress(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.NEXT_OF_KIN_ADDRESS, value);
	}

	/**
	 * Gets the telephone contact of next of kin
	 * @return the telephone number
	 */
	public String getNextOfKinContact() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.NEXT_OF_KIN_CONTACT);
	}

	/**
	 * Sets the telephone contact of next of kin
	 * @param value telephone number
	 */
	public void setNextOfKinContact(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.NEXT_OF_KIN_CONTACT, value);
	}

	/**
	 * Gets the name of next of kin
	 * @return the name
	 */
	public String getNextOfKinName() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.NEXT_OF_KIN_NAME);
	}

	/**
	 * Sets the name of next of kin
	 * @param value the name
	 */
	public void setNextOfKinName(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.NEXT_OF_KIN_NAME, value);
	}

	/**
	 * Gets the relationship of next of kin
	 * @return the relationship
	 */
	public String getNextOfKinRelationship() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.NEXT_OF_KIN_RELATIONSHIP);
	}

	/**
	 * Sets the relationship of next of kin
	 * @param value the relationship
	 */
	public void setNextOfKinRelationship(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.NEXT_OF_KIN_RELATIONSHIP, value);
	}

	/**
	 * Gets the sub chief name
	 * @return the name
	 */
	public String getSubChiefName() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.SUBCHIEF_NAME);
	}
	/**
	 * Sets the sub chief name
	 * @param value the name
	 */
	public void setSubChiefName(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.SUBCHIEF_NAME, value);
	}
	
	/**
	 * Gets the previous clinic name
	 * @return the name
	 */
	public String getPreviousClinicName() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.PREVIOUS_CLINIC_NAME);
	}

	/**
	 * Sets the previous clinic name
	 * @param value the name
	 */
	public void setPreviousClinicName(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.PREVIOUS_CLINIC_NAME, value);
	}
	
	/**
	 * Gets the previous hiv test performed status
	 * @return the name
	 */
	public String getPreviousHivTestStatus() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.HIV_TEST_PERFORMED);
	}

	/**
	 * Sets the previous hiv test performed status
	 * @param value the name
	 */
	public void setPreviousHivTestStatus(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.HIV_TEST_PERFORMED, value);
	}
	
	/**
	 * Gets the previous hiv test performed place
	 * @return the name
	 */
	public String getPreviousHivTestPlace() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.HIV_TEST_PERFORMED_PLACE);
	}

	/**
	 * Sets the previous hiv test performed place
	 * @param value the name
	 */
	public void setPreviousHivTestPlace(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.HIV_TEST_PERFORMED_PLACE, value);
	}
	
	/**
	 * Gets the previous hiv test performed date
	 * @return the date
	 */
	public String getPreviousHivTestDate() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.HIV_TEST_PERFORMED_DATE);
	}

	/**
	 * Sets the previous hiv test performed date
	 * @param value the date
	 */
	public void setPreviousHivTestDate(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.HIV_TEST_PERFORMED_DATE, value);
	}
	
	public String getFatherName() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.FATHER_NAME);
	}

	public void setFatherName(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.FATHER_NAME, value);
	}
	
	public String getNationalId() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.NATIONAL_ID);
	}

	public void setNationalId(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.NATIONAL_ID, value);
	}
	
	public String getPlaceOfBirth() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.PLACE_OF_BIRTH);
	}

	public void setPlaceOfBirth(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.PLACE_OF_BIRTH, value);
	}
	
	/**
	 * Sets the Entry source ID
	 * @param value the string value
	 */	
	public void setEntrySourceId(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.ENTRY_SORUCE_ID, value);
	}

	/**
	 * Gets the SystemPatientId
	 * @return the string value
	 */
	public String getEntrySourceId() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.ENTRY_SORUCE_ID);
	}
	
	public void setCurrentTownshipTBNumber(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.CURRENT_TOWNSHIP_NUMBER, value);
	}

	
	public String getCurrentTownshipTBNumber() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.CURRENT_TOWNSHIP_NUMBER);
	}
	
	public void setPreviousTownshipTBNumber(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.PREVIOUS_TOWNSHIP_NUMBER, value);
	}

	
	public String getPreviousTownshipTBNumber() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.PREVIOUS_TOWNSHIP_NUMBER);
	}
	
	public String getGenSampleId() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.GEN_SAMPLE_ID);
	}

	public void setGenSampleId(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.GEN_SAMPLE_ID, value);
	}
	
	public String getGenSpecificationDate() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.GEN_SPECIMEN_COLLECTION_DATE);
	}

	public void setGenSpecificationDate(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.GEN_SPECIMEN_COLLECTION_DATE, value);
	}

	public String getGenSpecificationPlace() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.GEN_SPECIMEN_COLLECTION_PLACE);
	}

	public void setGenSpecificationPlace(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.GEN_SPECIMEN_COLLECTION_PLACE, value);
	}
	
	public String getPreviousRegimenType() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.PREVIOUS_REGIMEN);
	}

	public void setPreviousRegimenType(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.PREVIOUS_REGIMEN, value);
	}
	
	public String getPreviousRegimenStartDate() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.PREVIOUS_REGIMEN_DATE);
	}

	public void setPreviousRegimenStartDate(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.PREVIOUS_REGIMEN_DATE, value);
	}

	public String getLabPatient() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.LAB_PATIENT);
	}

	public void setLabPatient(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.LAB_PATIENT, value);
	}
   
	public String getSecondline() {
		return getAsAttribute(CommonMetadata._PersonAttributeType.SECONDLINE_DRUG);
	}

	public void setSecondline(String value) {
		setAsAttribute(CommonMetadata._PersonAttributeType.SECONDLINE_DRUG, value);
	}
   
}