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

package org.openmrs.module.kenyaemr.reporting.library.shared.common;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Program;
import org.openmrs.api.PatientSetService;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.cohort.definition.CalculationCohortDefinition;
import org.openmrs.module.kenyacore.report.cohort.definition.DateObsValueBetweenCohortDefinition;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.library.DeceasedPatientsCalculation;
import org.openmrs.module.kenyaemr.calculation.library.InProgramCalculation;
import org.openmrs.module.kenyaemr.calculation.library.RecordedDeceasedCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.OnAlternateFirstLineArtCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.PatientOnRegimeWithPASCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TypeOfPatientWitMDRTBnumber;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.EncounterCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.GenderCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.ProgramEnrollmentCohortDefinition;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * Library of common cohort definitions
 */
@Component
public class CommonCohortLibrary {

	/**
	 * Patients who are female
	 * @return the cohort definition
	 */
	public CohortDefinition females() {
		GenderCohortDefinition cd = new GenderCohortDefinition();
		cd.setName("females");
		cd.setFemaleIncluded(true);
		return cd;
	}

	/**
	 * Patients who are male
	 * @return the cohort definition
	 */
	public CohortDefinition males() {
		GenderCohortDefinition cd = new GenderCohortDefinition();
		cd.setName("males");
		cd.setMaleIncluded(true);
		return cd;
	}

	/**
	 * Patients who at most maxAge years old on ${effectiveDate}
	 * @return the cohort definition
	 */
	public CohortDefinition agedAtMost(int maxAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged at most " + maxAge);
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMaxAge(maxAge);
		return cd;
	}

	/**
	 * Patients who are at least minAge years old on ${effectiveDate}
	 * @return the cohort definition
	 */
	public CohortDefinition agedAtLeast(int minAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged at least " + minAge);
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMinAge(minAge);
		return cd;
	}

	/**
	 * Patients who are female and at least 18 years old on ${effectiveDate}
	 * @return the cohort definition
	 */
	public CohortDefinition femalesAgedAtLeast18() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("females aged at least 18");
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.addSearch("females", ReportUtils.map(females()));
		cd.addSearch("agedAtLeast18", ReportUtils.map(agedAtLeast(18), "effectiveDate=${effectiveDate}"));
		cd.setCompositionString("females AND agedAtLeast18");
		return cd;
	}

	/**
	 * Patients who have an encounter between ${onOrAfter} and ${onOrBefore}
	 * @param types the encounter types
	 * @return the cohort definition
	 */
	public CohortDefinition hasEncounter(EncounterType... types) {
		EncounterCohortDefinition cd = new EncounterCohortDefinition();
		cd.setName("has encounter between dates");
		cd.setTimeQualifier(TimeQualifier.ANY);
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		if (types.length > 0) {
			cd.setEncounterTypeList(Arrays.asList(types));
		}
		return cd;
	}

	/**
	 * Patients who have an obs between ${onOrAfter} and ${onOrBefore}
	 * @param question the question concept
	 * @param answers the answers to include
	 * @return the cohort definition
	 */
	public CohortDefinition hasObs(Concept question, Concept... answers) {
		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.setName("has obs between dates");
		cd.setQuestion(question);
		cd.setOperator(SetComparator.IN);
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		if (answers.length > 0) {
			cd.setValueList(Arrays.asList(answers));
		}
		return cd;
	}

	/**
	 * Patients who transferred in between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition transferredIn() {
		Concept transferInDate = Dictionary.getConcept(Dictionary.TRANSFER_IN_DATE);

		DateObsValueBetweenCohortDefinition cd = new DateObsValueBetweenCohortDefinition();
		cd.setName("transferred in between dates");
		cd.setQuestion(transferInDate);
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		return cd;
	}

	/**
	 * Patients who transferred in between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition transferredOut() {
		Concept reasonForDiscontinue = Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION);
		Concept transferredOut = Dictionary.getConcept(Dictionary.TRANSFERRED_OUT);

		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.setName("transferred out between dates");
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.setQuestion(reasonForDiscontinue);
		cd.setOperator(SetComparator.IN);
		cd.setValueList(Collections.singletonList(transferredOut));
		return cd;
	}

	/**
	 * Patients who were enrolled on the given programs between ${enrolledOnOrAfter} and ${enrolledOnOrBefore}
	 * @param programs the programs
	 * @return the cohort definition
	 */
	public CohortDefinition enrolled(Program... programs) {
		ProgramEnrollmentCohortDefinition cd = new ProgramEnrollmentCohortDefinition();
		cd.setName("enrolled in program between dates");
		cd.addParameter(new Parameter("enrolledOnOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("enrolledOnOrBefore", "Before Date", Date.class));
		if (programs.length > 0) {
			cd.setPrograms(Arrays.asList(programs));
		}
		return cd;
	}

	/**
	 * Patients who were enrolled on the given programs (excluding transfers) between ${onOrAfter} and ${onOrBefore}
	 * @param programs the programs
	 * @return the cohort definition
	 */
	public CohortDefinition enrolledExcludingTransfers(Program... programs) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("enrolled excluding transfers in program between dates");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(programs), "enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("transferIn", ReportUtils.map(transferredIn(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("completeProgram", ReportUtils.map(compltedProgram(), "completedOnOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND NOT (transferIn OR completeProgram)");
		return cd;
	}

	/**
	 * Patients who were enrolled on the given programs (excluding transfers) on ${onOrBefore}
	 * @param programs the programs
	 * @return the cohort definition
	 */
	public CohortDefinition enrolledExcludingTransfersOnDate(Program... programs) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("enrolled excluding transfers in program on date in this facility");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(programs), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("transferIn", ReportUtils.map(transferredIn(), "onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND NOT transferIn");
		return cd;

	}

	/**
	 * Patients who are pregnant on ${onDate}
	 * @return the cohort definition
	 */
	public CohortDefinition pregnant() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new OnAlternateFirstLineArtCalculation());
		cd.setName("pregnant on date");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		return cd;
	}

	/**
	 * Patients who are in the specified program on ${onDate}
	 * @param program the program
	 * @return
	 */
	public CohortDefinition inProgram(Program program) {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new InProgramCalculation());
		cd.setName("in " + program.getName() + " on date");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		cd.addCalculationParameter("program", program);
		return cd;
	}

	/**
	 * Patients who were dispensed the given medications between ${onOrAfter} and ${onOrBefore}
	 * @param concepts the drug concepts
	 * @return the cohort definition
	 */
	public CohortDefinition medicationDispensed(Concept... concepts) {
		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.setName("dispensed medication between");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.setQuestion(Dictionary.getConcept(Dictionary.MEDICATION_ORDERS));
		cd.setValueList(Arrays.asList(concepts));
		cd.setOperator(SetComparator.IN);
		return cd;
	}

	/**
	 * Patients who completed program ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition compltedProgram() {
		ProgramEnrollmentCohortDefinition cd = new ProgramEnrollmentCohortDefinition();
		cd.setName("Those patients who completed program on date");
		cd.addParameter(new Parameter("completedOnOrBefore", "Complete Date", Date.class));
		cd.setPrograms(Arrays.asList(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV)));
		return cd;
	}
        
        public CohortDefinition compltedProgram(Program program) {
		ProgramEnrollmentCohortDefinition cd = new ProgramEnrollmentCohortDefinition();
		cd.setName("Those patients who completed program on date");
		cd.addParameter(new Parameter("completedOnOrBefore", "Complete Date", Date.class));
		cd.setPrograms(Arrays.asList(program));
		return cd;
	}

	/**
	 * Patients who are Deceased
	 * @return the cohort definition
	 */
	public  CohortDefinition deceasedPatients() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new DeceasedPatientsCalculation());
		cd.setName("deceases patients on date");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		return cd;
	}

	/**
	 * Patients who ahve been marked as dead in discontinuation forms but NOT YET deceased
	 * @return cohort definition
	 */
	public CohortDefinition markedAsDeadButNotDeceased() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new RecordedDeceasedCalculation());
		cd.setName("marked as dead patients on date");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		return cd;
	}

	public CohortDefinition treatmentOutcome_Cure() {
        Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept outcomresult=Dictionary.getConcept(Dictionary.CURE_OUTCOME);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with cure outcome");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("givencureOutcome", ReportUtils.map(hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("givencureOutcome");
		return cd;

			
		
	}

	public CohortDefinition treatmentOutcome_TreatmentCompleted() {
		    Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
			Concept outcomresult=Dictionary.getConcept(Dictionary.TREATMENT_COMPLETE);
			CompositionCohortDefinition cd = new CompositionCohortDefinition();
			cd.setName("Total enrolled with treatment complete outcome");
			cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
			cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
			
			cd.addSearch("givencompletedOutcome", ReportUtils.map(hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
			
			cd.setCompositionString("givencompletedOutcome");
			return cd;
	}

	public CohortDefinition treatmentOutcome_Failure() {
        Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept outcomresult=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_FAILURE);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment failure outcome");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("givenfailureOutcome", ReportUtils.map(hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("givenfailureOutcome");
		return cd;
	}

	public CohortDefinition treatmentOutcome_Defaulted() {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept outcomresult=Dictionary.getConcept(Dictionary.LOSS_TO_FOLLOW_UP);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment loss to follow up outcome");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("givenlosstofollowupOutcome", ReportUtils.map(hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("givenlosstofollowupOutcome");
		return cd;
	}

	public CohortDefinition treatmentOutcome_Died() {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept outcomresult=Dictionary.getConcept(Dictionary.DIED);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment  outcome died");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("givenOutcomeDied", ReportUtils.map(hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("givenOutcomeDied");
		return cd;
	}
	
	public CohortDefinition treatmentOutcome_Transferedout() {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept outcomresult=Dictionary.getConcept(Dictionary.MOVE_TO_XDR);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment  outcome transferred");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("givenOutcometransferredout", ReportUtils.map(hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("givenOutcometransferredout");
		return cd;
	}

	public CohortDefinition treatmentOutcome_Enroll() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled"  );
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("cureOutcome", ReportUtils.map(treatmentOutcome_Cure(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredoutOutcome", ReportUtils.map(treatmentOutcome_Transferedout(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("diedOutcome", ReportUtils.map(treatmentOutcome_Died(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("defaultedOutcome", ReportUtils.map(treatmentOutcome_Defaulted(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("failureOutcome", ReportUtils.map(treatmentOutcome_Failure(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("CompletedOutcome", ReportUtils.map(treatmentOutcome_TreatmentCompleted(), "onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND NOT cureOutcome AND NOT transferredoutOutcome  AND NOT diedOutcome AND NOT  defaultedOutcome AND NOT failureOutcome AND NOT CompletedOutcome");
		return cd;
	}

	public CohortDefinition  conventionaldDst() {
		Concept tbgeneoutcome=Dictionary.getConcept(Dictionary.TB_GENE_RESULT);
		Concept outcomresult=Dictionary.getConcept(Dictionary.RIFAMPCIN_RESISTANT);
		Concept cultureDstoutcome=Dictionary.getConcept(Dictionary.CULTURE_DRUG_R);
		Concept cultureDstresult=Dictionary.getConcept(Dictionary.RESULT_RESISTANT);
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment  outcome transferred");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("givenxpertResult", ReportUtils.map(hasObs(tbgeneoutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		cd.addSearch("givenDSTResult", ReportUtils.map(hasObs(cultureDstoutcome,cultureDstresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("givenxpertResult OR givenDSTResult");
		return cd;
	}

	public CohortDefinition enrollTbnum() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new TypeOfPatientWitMDRTBnumber());
		cd.setName("Patients havinfg MDR TB registration number ");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));

		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with tb registration number");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("tbregnumber", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.setCompositionString("tbregnumber");

		return comp;
	}

}