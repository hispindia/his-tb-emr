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

package org.openmrs.module.kenyaemr.reporting.library.shared.tb;

import org.openmrs.Concept;
import org.openmrs.Program;
import org.openmrs.api.PatientSetService;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.cohort.definition.CalculationCohortDefinition;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.library.hiv.cqi.PatientLastVisitCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.MissedLastTbAppointmentCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.PatientOnRegimeWithEitherPASCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.PatientOnRegimeWithPASCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.PatientOnRegimeWithoutPASCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbInitialTreatmentCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentStartDateCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientonMedication6mnthsagoCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.Tbpatients24monthsagoCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.Tbpatients36monthsagoCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientswithFailuretreatmentCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientswithNewCategoryCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientswithRelapseCategoryCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientswithsmearpositiveculturenegativeCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithDiedOutcomeAt6mnthCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithLossTofollowupOutcomeCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithNonConverterCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithOtherCategoryCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithSmearCulturepositiveCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithStandardMDRcasesCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithTransferredInCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithTransferredOutOutComeCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithlosstoFollowupCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithsmearCulturenegativeCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithsmearnegativeCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithsmearpositiveCultureunknownCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithsmearnegativecultureunknownCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithsmearunknownCulturenegativeCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithsmearunknownCulturepositiveCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithsmearunknownCultureunknownCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbpatientwithwithsmearpositiveculturepositiveCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TotalPatientOnMedicationCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TotalPatientRegisteredCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.metadata.TbMetadata;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.HivCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art.ArtCohortLibrary;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.DateObsCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.evaluation.parameter.Parameterizable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Library of TB related cohort definitions
 */
@Component
public class TbCohortLibrary {

	@Autowired
	private CommonCohortLibrary commonCohorts;

	@Autowired
	private HivCohortLibrary hivCohortLibrary;

	@Autowired
	private ArtCohortLibrary artCohortLibrary;

	/**
	 * Patients who were enrolled in TB program (including transfers) between ${enrolledOnOrAfter} and ${enrolledOnOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition enrolled() {
		return commonCohorts.enrolled(MetadataUtils.existing(Program.class, TbMetadata._Program.TB));
	}

	/**
	 * Patients who were screened for TB between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition screenedForTb() {
		Concept tbDiseaseStatus = Dictionary.getConcept(Dictionary.TUBERCULOSIS_DISEASE_STATUS);
		Concept diseaseSuspected = Dictionary.getConcept(Dictionary.DISEASE_SUSPECTED);
		Concept diseaseDiagnosed = Dictionary.getConcept(Dictionary.DISEASE_DIAGNOSED);
		Concept noSignsOrSymptoms = Dictionary.getConcept(Dictionary.NO_SIGNS_OR_SYMPTOMS_OF_DISEASE);
		return commonCohorts.hasObs(tbDiseaseStatus, diseaseSuspected, diseaseDiagnosed, noSignsOrSymptoms);
	}

	/**
	 * Patients screened for TB using ICF form
	 * @return CohortDefinition
	 */
	public CohortDefinition screenedForTbUsingICF() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientLastVisitCalculation());
		cd.setName("Patients who had tb screens in last visit");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));

		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Screened for tb in last visit using ICF form and some observations saved");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingICF", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("obsSaved", ReportUtils.map(screenedForTb(), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		comp.setCompositionString("usingICF AND obsSaved");

		return comp;
	}

	/**
	 * TB patients who died TB between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition died() {
		Concept tbTreatmentOutcome = Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept died = Dictionary.getConcept(Dictionary.DIED);
		return commonCohorts.hasObs(tbTreatmentOutcome, died);
	}

	/**
	 *
	 */
	public CohortDefinition startedTbTreatmentBetweenDates() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TotalPatientOnMedicationCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with Relapse ( IR, RR)");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("started TB treatment 12 months ago");
		//Concept tbStartDate = Dictionary.getConcept(Dictionary.TUBERCULOSIS_DRUG_TREATMENT_START_DATE);
		cd.setName("Patients who started Tb treatment between dates");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrAfter}"));
		cd.setCompositionString("usingRegime");
		return cd;
	}

	/**
	 * TB patients who started treatment in the month a year before ${onDate}
	 * @return the cohort definition
	 */
	public CohortDefinition started12MonthsAgo() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("started TB treatment 12 months ago");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolledInTbProgram", ReportUtils.map(enrolled(), "enrolledOnOrBefore=${onDate}"));
		
		cd.addSearch("startedTreatmentInTheMonthAyYearBefore", ReportUtils.map(startedTbTreatmentBetweenDates(), "onOrAfter=${onDate-12m},onOrBefore=${onDate}"));
		cd.setCompositionString("enrolledInTbProgram AND startedTreatmentInTheMonthAyYearBefore");
		
		return cd;
	}
	// TB patients who started treatment 24 month ago
	public CohortDefinition started24MonthsAgo() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("started TB treatment 24 months ago");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		cd.addSearch("enrolledInTbProgram", ReportUtils.map(enrolled(), "enrolledOnOrBefore=${onDate}"));
		cd.addSearch("startedTreatmentInTheMonthAyYearBefore", ReportUtils.map(startedTbTreatmentBetweenDates(), "onOrAfter=${onDate-25},onOrBefore=${onDate-24}"));
		cd.setCompositionString("enrolledInTbProgram AND startedTreatmentInTheMonthAyYearBefore");
		return cd;
	}
	// TB patients who started treatment 36 month ago
	public CohortDefinition started36MonthsAgo() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("started TB treatment 36 months ago");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		cd.addSearch("enrolledInTbProgram", ReportUtils.map(enrolled(), "enrolledOnOrBefore=${onDate}"));
		cd.addSearch("startedTreatmentInTheMonthYearBefore", ReportUtils.map(startedTbTreatmentBetweenDates(), "onOrAfter=${onDate-37},onOrBefore=${onDate-36}"));
		cd.setCompositionString("enrolledInTbProgram AND startedTreatmentInTheMonthYearBefore");
		return cd;
	}
	/**
	 * TB patients started treatment in the month a year before and died between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition diedAndStarted12MonthsAgo() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("started TB treatment 12 months ago and died");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("died", ReportUtils.map(died(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("started12MonthsAgo", ReportUtils.map(started12MonthsAgo(), "onDate=${onOrBefore}"));
		cd.setCompositionString("died AND started12MonthsAgo");
		return cd;
	}

	/**
	 * TB patients who completed treatment between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition completedTreatment() {
		Concept tbTreatmentOutcome = Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept complete = Dictionary.getConcept(Dictionary.TREATMENT_COMPLETE);
		return commonCohorts.hasObs(tbTreatmentOutcome, complete);
	}

	/**
	 * TB patients defaulted (i.e. who missed last appointment) on ${onDate}
	 * @return the cohort definition
	 */
	public CohortDefinition defaulted() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new MissedLastTbAppointmentCalculation());
		cd.setName("defaulted");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		return cd;
	}

	/**
	 * patients in TB and HIV and on CPT between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition inTbAndHivProgramsAndOnCtxProphylaxis() {
		Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
		Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		Concept[] drugs = { Dictionary.getConcept(Dictionary.SULFAMETHOXAZOLE_TRIMETHOPRIM) };
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("inHivProgram", ReportUtils.map(commonCohorts.enrolled(hivProgram), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("inTbProgram", ReportUtils.map(commonCohorts.enrolled(tbProgram), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("onCtx", ReportUtils.map(commonCohorts.medicationDispensed(drugs), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inHivProgram AND inTbProgram AND onCtx");
		return cd;
	}

	/**
	 * patients tested for HIV(concept id 1169 or 1594217) and are tb patients ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition testedForHivAndInTbProgram() {
		Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("inTbProgram", ReportUtils.map(commonCohorts.enrolled(tbProgram), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("hivTested", ReportUtils.map(hivCohortLibrary.testedForHiv(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inTbProgram AND hivTested");
		return cd;
	}

	/**
	 * patients tested for HIV(concept id 1169 or 1594217) and are tb patients and the results turned out to be positive ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition testedHivPositiveAndInTbProgram() {
		Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("inTbProgram", ReportUtils.map(commonCohorts.enrolled(tbProgram), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("hivTestedPositive", ReportUtils.map(hivCohortLibrary.testedHivStatus(Dictionary.getConcept(Dictionary.POSITIVE)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inTbProgram AND hivTestedPositive");
		return cd;
	}

	/**
	 * Patients who have Tb re treatment   between ${onOrAfter} and ${onOrBefore}
	 */
	public CohortDefinition tbRetreatments() {
		Concept patientClassification = Dictionary.getConcept(Dictionary.TYPE_OF_TB_PATIENT);
		Concept retreatment = Dictionary.getConcept(Dictionary.RETREATMENT_AFTER_DEFAULT_TUBERCULOSIS);
		return commonCohorts.hasObs(patientClassification,retreatment);
	}

	/**
	 * Patients who have expulmonary Tb    between ${onOrAfter} and ${onOrBefore}
	 */
	public CohortDefinition extraPulmonaryTbPatients() {
		Concept diseaseClassification = Dictionary.getConcept(Dictionary.SITE_OF_TUBERCULOSIS_DISEASE);
		Concept extraPulmonary = Dictionary.getConcept(Dictionary.MYCROBACTERIUM_TUBERCULOSIS_EXTRAPULMONARY);
		return commonCohorts.hasObs(diseaseClassification,extraPulmonary);
	}

	/**
	 * Patients who have pulmonary Tb    between ${onOrAfter} and ${onOrBefore}
	 */
	public CohortDefinition pulmonaryTbPatients() {
		Concept diseaseClassification = Dictionary.getConcept(Dictionary.SITE_OF_TUBERCULOSIS_DISEASE);
		Concept pulmonary = Dictionary.getConcept(Dictionary.PULMONARY_TB);
		return commonCohorts.hasObs(diseaseClassification,pulmonary);
	}

	/**
	 * Patients who have smear negative   between ${onOrAfter} and ${onOrBefore}
	 */
	public CohortDefinition smearNegativePatients() {
		Concept cultureResults = Dictionary.getConcept(Dictionary.SPUTUM_CULTURE);
		Concept smearNegative = Dictionary.getConcept(Dictionary.NEGATIVE);
		return commonCohorts.hasObs(cultureResults,smearNegative);
	}

	/**
	 * Patients who have smear positive   between ${onOrAfter} and ${onOrBefore}
	 */
	public CohortDefinition smearPositivePatients() {
		Concept cultureResults = Dictionary.getConcept(Dictionary.RESULTS_TUBERCULOSIS_CULTURE);
		Concept smearPositive = Dictionary.getConcept(Dictionary.POSITIVE);
		return commonCohorts.hasObs(cultureResults,smearPositive);
	}

	/**
	 * Patients who have pulmonary Tb and  smear negative   between ${onOrAfter} and ${onOrBefore}
	 */
	public CohortDefinition pulmonaryTbSmearNegative() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("pulmonaryTbPatients", ReportUtils.map(pulmonaryTbPatients(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("smearNegative", ReportUtils.map(smearNegativePatients(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("pulmonaryTbPatients AND smearNegative");
		return cd;
	}

	/**
	 * Patients who have pulmonary Tb and  smear positive   between ${onOrAfter} and ${onOrBefore}
	 */
	public CohortDefinition pulmonaryTbSmearPositive() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("pulmonaryTbPatients", ReportUtils.map(pulmonaryTbPatients(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("smearPositive", ReportUtils.map(smearPositivePatients(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("pulmonaryTbPatients AND smearPositive");
		return cd;
	}

	/**
	 * Patients have new Tb detected cases   between ${onOrAfter} and ${onOrBefore}
	 */
	public CohortDefinition tbNewDetectedCases() {
		Concept patientClassification = Dictionary.getConcept(Dictionary.TYPE_OF_TB_PATIENT);
		Concept newDetects = Dictionary.getConcept(Dictionary.SMEAR_POSITIVE_NEW_TUBERCULOSIS_PATIENT);
		return commonCohorts.hasObs(patientClassification,newDetects);
	}

	/**
	 * Patients who have smear NOT done
	 */
	public CohortDefinition pulmonaryTbSmearNotDone() {
		Concept cultureResults = Dictionary.getConcept(Dictionary.RESULTS_TUBERCULOSIS_CULTURE);
		Concept smearNotDone = Dictionary.getConcept(Dictionary.NOT_DONE);
		return commonCohorts.hasObs(cultureResults,smearNotDone);
	}

	/**
	 * Patients in tb program and smear not done and have pulmonary tb results at 2 months
	 * @return the cohort definition
	 */
	public CohortDefinition ptbSmearNotDoneResultsAtMonths() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("pulmonaryTbPatients", ReportUtils.map(pulmonaryTbPatients(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("smearNotDone", ReportUtils.map(pulmonaryTbSmearNotDone(), "onOrBefore=${onOrBefore}"));
		cd.setCompositionString("pulmonaryTbPatients AND smearNotDone");
		return cd;
	}

	/**
	 * Patients who started tb results n months later
	 * @return cohort definition
	 */
	public CohortDefinition startedTbTreatmentResultsAtMonths(Integer months) {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new TbTreatmentStartDateCalculation());
		cd.setName("patients Tb results at"+ months + "months");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		cd.addCalculationParameter("months", months);
		return cd;
	}
	
	/**
	 * Patients who have completed their initial tb treatment
	 * @return cohort definition
	 */
	public CohortDefinition completedInitialTreatment() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new TbInitialTreatmentCalculation());
		cd.setName("patients who completed tb initial treatment on date");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		return cd;
	}

	/**
	 * Total enrolled patients into tb program and have ptb smear not done results at 2 months
	 * @return cohort definition
	 */
	public CohortDefinition totalEnrolledPtbSmearNotDoneResultsAtMonths(int highMonths, int leastMonths  ) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled in 2 months between "+leastMonths+" and "+highMonths);
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("ptbSmearNotDoneResultsAt2Months", ReportUtils.map(ptbSmearNotDoneResultsAtMonths(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("enrolledLMonthsAgo", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrBefore-"+ leastMonths +"m},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("enrolledHMonthsAgo", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrBefore-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("resultsAt2Months", ReportUtils.map(startedTbTreatmentResultsAtMonths(2), "onDate=${onOrBefore}"));
		cd.setCompositionString("ptbSmearNotDoneResultsAt2Months AND enrolledLMonthsAgo AND enrolledHMonthsAgo AND resultsAt2Months");
		return cd;
	}

	/**
	 * Patient who finalized their initial treatment
	 * return the cohort definition
	 */
	public CohortDefinition ptbSmearNotDoneResults2MonthsFinalizedInitialtreatment() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Finalized Initial treatment 8 to 12 months");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("ptbSmearNotDoneResultsAt2Months", ReportUtils.map(totalEnrolledPtbSmearNotDoneResultsAtMonths(12, 8), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(completedInitialTreatment(), "onDate=${onOrBefore}"));
		cd.setCompositionString("ptbSmearNotDoneResultsAt2Months AND finalizedInitialTreatment");
		return cd;
	}

	/**
	 * Patients who died
	 * @return cohort definition
	 */
	public CohortDefinition ptbSmearNotDoneResults2MonthsDied() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("ptbSmearNotDoneResultsAt2Months", ReportUtils.map(totalEnrolledPtbSmearNotDoneResultsAtMonths(12,8), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("ptbSmearNotDoneResultsAt2Months AND died");
		return cd;
	}

	/**
	 * Patient who absconded the treatment
	 * @return cohort definition
	 */
	public CohortDefinition ptbSmearNotDoneResults2MonthsAbsconded() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("ptbSmearNotDoneResultsAt2Months", ReportUtils.map(totalEnrolledPtbSmearNotDoneResultsAtMonths(12, 8), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("defaulted", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DEFAULTED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("ptbSmearNotDoneResultsAt2Months AND defaulted");
		return cd;
	}

	/**
	 * Patients who transferred out
	 * @return cohort definition
	 */
	public CohortDefinition ptbSmearNotDoneResults2MonthsTransferredOut() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("ptbSmearNotDoneResultsAt2Months", ReportUtils.map(totalEnrolledPtbSmearNotDoneResultsAtMonths(12, 8), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("ptbSmearNotDoneResultsAt2Months AND transferredOut");
		return cd;
	}

	/**
	 * Total Patients evaluated
	 * @return cohort Definition
	 */
	public CohortDefinition ptbSmearNotDoneResults2MonthsTotalEvaluated() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("ptbSmearNotDoneResults2MonthsFinalizedInitialtreatment", ReportUtils.map(ptbSmearNotDoneResults2MonthsFinalizedInitialtreatment(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("ptbSmearNotDoneResults2MonthsDied", ReportUtils.map(ptbSmearNotDoneResults2MonthsDied(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("ptbSmearNotDoneResults2MonthsAbsconded", ReportUtils.map(ptbSmearNotDoneResults2MonthsAbsconded(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("ptbSmearNotDoneResults2MonthsTransferredOut", ReportUtils.map(ptbSmearNotDoneResults2MonthsTransferredOut(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("ptbSmearNotDoneResults2MonthsFinalizedInitialtreatment OR ptbSmearNotDoneResults2MonthsDied OR ptbSmearNotDoneResults2MonthsAbsconded OR ptbSmearNotDoneResults2MonthsTransferredOut");
		return  cd;
	}

	/**
	 * Total enrolled 8-12 months earlier and have results a 8 months
	 * @return cohort definition
	 */
	public CohortDefinition totalEnrolled8Months(int highMonths, int leastMonths ) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("ptbSmearNotDoneResultsAtMonths", ReportUtils.map(ptbSmearNotDoneResultsAtMonths(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("enrolledHMonthsAgo", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrBefore-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("enrolledLMonthsAgo", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrBefore-"+ leastMonths +"m},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("resultsAt8Months", ReportUtils.map(startedTbTreatmentResultsAtMonths(8), "onDate=${onOrBefore}"));
		cd.setCompositionString("ptbSmearNotDoneResultsAtMonths AND enrolledHMonthsAgo AND enrolledLMonthsAgo AND resultsAt8Months");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV positive
	 * @return cohort definition
	 */
	public CohortDefinition totalEnrolled8MonthsHivPositive() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		Concept hivPositive = Dictionary.getConcept(Dictionary.POSITIVE);
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8Months", ReportUtils.map(totalEnrolled8Months(12, 8), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("hivPositive", ReportUtils.map(hivCohortLibrary.testedHivStatus(hivPositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8Months AND hivPositive");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV negative
	 * @return cohort definition
	 */
	public CohortDefinition totalEnrolled8MonthsHivNegative() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		Concept hivNegative = Dictionary.getConcept(Dictionary.NEGATIVE);
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8Months", ReportUtils.map(totalEnrolled8Months(12, 8), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("hivNegative", ReportUtils.map(hivCohortLibrary.testedHivStatus(hivNegative), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8Months AND hivNegative");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV test not done
	 * @return cohort definition
	 */
	public CohortDefinition totalEnrolled8MonthsHivTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		Concept hivNotDone = Dictionary.getConcept(Dictionary.NOT_DONE);
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8Months", ReportUtils.map(totalEnrolled8Months(12, 8), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("hivTestNotDone", ReportUtils.map(hivCohortLibrary.testedHivStatus(hivNotDone), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8Months AND hivTestNotDone");
		return cd;
	}

	/**
	 * Total number of patients registered 8-12 months earlier
	 * Patients should be hiv+, hiv-, hiv Test not done
	 * results at 8 months
	 */
	public CohortDefinition totalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("hivPositive", ReportUtils.map(totalEnrolled8MonthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("hivNegative", ReportUtils.map(totalEnrolled8MonthsHivNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("hivNotDone", ReportUtils.map(totalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("hivPositive OR hivNegative OR hivNotDone");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on cpt
	 * @return cohort definition
	 */
	public CohortDefinition totalEnrolled8MonthsHivPositiveOnCpt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositive", ReportUtils.map(totalEnrolled8MonthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("onCpt", ReportUtils.map(inTbAndHivProgramsAndOnCtxProphylaxis(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositive AND onCpt");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on art
	 * @return cohort definition
	 */
	public CohortDefinition totalEnrolled8MonthsHivPositiveOnArt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositive", ReportUtils.map(totalEnrolled8MonthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("onART", ReportUtils.map(artCohortLibrary.onArt(), "onDate=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositive AND onART");
		return cd;
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * completed the treatment
	 * @return cohort definition
	 */
	public CohortDefinition finalizedInitialTreatmentResults8monthsHivPositive() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositive", ReportUtils.map(totalEnrolled8MonthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(completedInitialTreatment(), "onDate=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositive AND finalizedInitialTreatment");
		return cd;
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * died
	 * @return cohort definition
	 */
	public CohortDefinition diedResults8monthsHivPositive() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositive", ReportUtils.map(totalEnrolled8MonthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositive AND died");
		return cd;
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * absconded
	 * @return cohort definition
	 */
	public CohortDefinition abscondedResults8monthsHivPositive() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositive", ReportUtils.map(totalEnrolled8MonthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("absconded", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DEFAULTED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositive AND absconded");
		return cd;
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * Transferred out
	 * @return cohort definition
	 */
	public CohortDefinition transferredOutResults8monthsHivPositive() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositive", ReportUtils.map(totalEnrolled8MonthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositive AND transferredOut");
		return cd;
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * Transferred out, absconded, died, finished initial treatment
	 * @return cohort definition
	 */
	public CohortDefinition finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositive() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("finalizedInitialTreatmentResults8monthsHivPositive", ReportUtils.map(finalizedInitialTreatmentResults8monthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("diedResults8monthsHivPositive", ReportUtils.map(diedResults8monthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("abscondedResults8monthsHivPositive", ReportUtils.map(abscondedResults8monthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOutResults8monthsHivPositive", ReportUtils.map(transferredOutResults8monthsHivPositive(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("finalizedInitialTreatmentResults8monthsHivPositive OR diedResults8monthsHivPositive OR abscondedResults8monthsHivPositive OR transferredOutResults8monthsHivPositive");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV negative
	 * finalized treatment
	 * @return cohort definition
	 */
	public CohortDefinition finalizedInitialTreatmentTotalEnrolled8MonthsHivNegative() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivNegative", ReportUtils.map(totalEnrolled8MonthsHivNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(completedInitialTreatment(), "onDate=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivNegative AND finalizedInitialTreatment");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV negative
	 * died
	 * @return cohort definition
	 */
	public CohortDefinition diedTotalEnrolled8MonthsHivNegative() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivNegative", ReportUtils.map(totalEnrolled8MonthsHivNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivNegative AND died");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV negative
	 * absconded
	 * @return cohort definition
	 */
	public CohortDefinition abscondedTotalEnrolled8MonthsHivNegative() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivNegative", ReportUtils.map(totalEnrolled8MonthsHivNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("absconded", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DEFAULTED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivNegative AND absconded");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV negative
	 * transferred out
	 * @return cohort definition
	 */
	public CohortDefinition transferredOutTotalEnrolled8MonthsHivNegative() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivNegative", ReportUtils.map(totalEnrolled8MonthsHivNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivNegative AND transferredOut");
		return cd;
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV negative
	 * Transferred out, absconded, died, finished initial treatment
	 * @return cohort definition
	 */
	public CohortDefinition finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivNegative() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("finalizedInitialTreatmentResults8monthsHivNegative", ReportUtils.map(finalizedInitialTreatmentTotalEnrolled8MonthsHivNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("diedResults8monthsHivNegative", ReportUtils.map(diedTotalEnrolled8MonthsHivNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("abscondedResults8monthsHivNegative", ReportUtils.map(abscondedTotalEnrolled8MonthsHivNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOutResults8monthsHivNegative", ReportUtils.map(transferredOutTotalEnrolled8MonthsHivNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("finalizedInitialTreatmentResults8monthsHivNegative OR diedResults8monthsHivNegative OR abscondedResults8monthsHivNegative OR transferredOutResults8monthsHivNegative");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV test not done
	 * finalized  initial treatment
	 * @return cohort definition
	 */
	public CohortDefinition finalizedInitialTreatmentTotalEnrolled8MonthsHivTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivTestNotDone", ReportUtils.map(totalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(completedInitialTreatment(), "onDate=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivTestNotDone AND finalizedInitialTreatment");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV test not done
	 * died
	 * @return cohort definition
	 */
	public CohortDefinition diedTotalEnrolled8MonthsHivTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivTestNotDone", ReportUtils.map(totalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivTestNotDone AND died");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV test not done
	 * absconded
	 * @return cohort definition
	 */
	public CohortDefinition abscondedTotalEnrolled8MonthsHivTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivTestNotDone", ReportUtils.map(totalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("absconded", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DEFAULTED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivTestNotDone AND absconded");
		return cd;
	}

	/**
	 *Total enrolled 8-12 months earlier and have results a 8 months and are HIV test not done
	 * Transferred out
	 * @return cohort definition
	 */
	public CohortDefinition transferredOutTotalEnrolled8MonthsHivTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivTestNotDone", ReportUtils.map(totalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivTestNotDone AND transferredOut");
		return cd;
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV test not done
	 * Transferred out, absconded, died, finished initial treatment
	 * @return cohort definition
	 */
	public CohortDefinition finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("finalizedInitialTreatmentResults8monthsHivTestNotDone", ReportUtils.map(finalizedInitialTreatmentTotalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("diedResults8monthsHivTestNotDone", ReportUtils.map(diedTotalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("abscondedResults8monthsHivTestNotDone", ReportUtils.map(abscondedTotalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOutResults8monthsHivTestNotDone", ReportUtils.map(transferredOutTotalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("finalizedInitialTreatmentResults8monthsHivTestNotDone OR diedResults8monthsHivTestNotDone OR abscondedResults8monthsHivTestNotDone OR transferredOutResults8monthsHivTestNotDone");
		return cd;
	}

	/**
	 * Total number of patients registered 8-12 months earlier
	 * Patients should be hiv+, hiv-, hiv Test not done
	 * Finalized initial treatment
	 * results at 8 months
	 */
	public CohortDefinition finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveNegativeTestNotDone", ReportUtils.map(totalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(completedInitialTreatment(), "onDate=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveNegativeTestNotDone AND finalizedInitialTreatment");
		return cd;
	}

	/**
	 * Total number of patients registered 8-12 months earlier
	 * Patients should be hiv+, hiv-, hiv Test not done
	 * died
	 * results at 8 months
	 */
	public CohortDefinition diedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveNegativeTestNotDone", ReportUtils.map(totalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveNegativeTestNotDone AND died");
		return cd;
	}

	/**
	 * Total number of patients registered 8-12 months earlier
	 * Patients should be hiv+, hiv-, hiv Test not done
	 * absconded
	 * results at 8 months
	 */
	public CohortDefinition abscondedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveNegativeTestNotDone", ReportUtils.map(totalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("absconded", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DEFAULTED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveNegativeTestNotDone AND absconded");
		return cd;
	}

	/**
	 * Total number of patients registered 8-12 months earlier
	 * Patients should be hiv+, hiv-, hiv Test not done
	 * absconded
	 * results at 8 months
	 */
	public CohortDefinition transferredOutTotalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveNegativeTestNotDone", ReportUtils.map(totalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveNegativeTestNotDone AND transferredOut");
		return cd;
	}

	/**
	 * Total number of patients registered 8-12 months earlier
	 * Patients should be hiv+, hiv-, hiv Test not done
	 * total evaluated
	 * results at 8 months
	 */
	public CohortDefinition finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositiveNegativeTestNotDone() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveNegativeTestNotDone", ReportUtils.map(finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("diedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone", ReportUtils.map(diedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("abscondedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone", ReportUtils.map(abscondedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOutTotalEnrolled8MonthsHivPositiveNegativeTestNotDone", ReportUtils.map(transferredOutTotalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveNegativeTestNotDone OR diedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone OR abscondedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone OR transferredOutTotalEnrolled8MonthsHivPositiveNegativeTestNotDone");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on cpt
	 * finalized initial treatment
	 * @return cohort definition
	 */
	public CohortDefinition finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnCpt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveOnCpt", ReportUtils.map(totalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(completedInitialTreatment(), "onDate=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveOnCpt AND finalizedInitialTreatment");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on cpt
	 * died
	 * @return cohort definition
	 */
	public CohortDefinition diedTotalEnrolled8MonthsHivPositiveOnCpt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveOnCpt", ReportUtils.map(totalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveOnCpt AND died");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on cpt
	 * absconded
	 * @return cohort definition
	 */
	public CohortDefinition abscondedTotalEnrolled8MonthsHivPositiveOnCpt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveOnCpt", ReportUtils.map(totalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("absconded", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DEFAULTED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveOnCpt AND absconded");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on cpt
	 * transferred out
	 * @return cohort definition
	 */
	public CohortDefinition transferredOutTotalEnrolled8MonthsHivPositiveOnCpt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveOnCpt", ReportUtils.map(totalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveOnCpt AND transferredOut");
		return cd;
	}

	/**
	 * Total number of patients registered 8-12 months earlier
	 * Patients should be hiv+, hiv-, hiv Test not done
	 * total evaluated
	 * results at 8 months
	 */
	public CohortDefinition finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositiveOnCpt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnCpt", ReportUtils.map(finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("diedTotalEnrolled8MonthsHivPositiveOnCpt", ReportUtils.map(diedTotalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("abscondedTotalEnrolled8MonthsHivPositiveOnCpt", ReportUtils.map(abscondedTotalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOutTotalEnrolled8MonthsHivPositiveOnCpt", ReportUtils.map(transferredOutTotalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnCpt OR diedTotalEnrolled8MonthsHivPositiveOnCpt OR abscondedTotalEnrolled8MonthsHivPositiveOnCpt OR transferredOutTotalEnrolled8MonthsHivPositiveOnCpt");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on art
	 * finalized initial treatment
	 * @return cohort definition
	 */
	public CohortDefinition finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnArt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveOnArt", ReportUtils.map(totalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(completedInitialTreatment(), "onDate=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveOnArt AND finalizedInitialTreatment");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on art
	 * died
	 * @return cohort definition
	 */
	public CohortDefinition diedTotalEnrolled8MonthsHivPositiveOnArt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveOnArt", ReportUtils.map(totalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveOnArt AND died");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on art
	 * absconded
	 * @return cohort definition
	 */
	public CohortDefinition abscondedTotalEnrolled8MonthsHivPositiveOnArt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveOnArt", ReportUtils.map(totalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("absconded", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DEFAULTED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveOnArt AND absconded");
		return cd;
	}

	/**
	 * Total patients enrolled 8-12 months earlier
	 * hiv +
	 * results at 8 months
	 * on art
	 * transfer out
	 * @return cohort definition
	 */
	public CohortDefinition transferOutTotalEnrolled8MonthsHivPositiveOnArt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("totalEnrolled8MonthsHivPositiveOnArt", ReportUtils.map(totalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("totalEnrolled8MonthsHivPositiveOnArt AND transferOut");
		return cd;
	}

	/**
	 * Total number of patients registered 8-12 months earlier
	 * Patients should be hiv+, hiv-, hiv Test not done
	 * on art
	 * total evaluated
	 * results at 8 months
	 */
	public CohortDefinition finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositiveOnArt() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnArt", ReportUtils.map(finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("diedTotalEnrolled8MonthsHivPositiveOnArt", ReportUtils.map(diedTotalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("abscondedTotalEnrolled8MonthsHivPositiveOnArt", ReportUtils.map(abscondedTotalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferOutTotalEnrolled8MonthsHivPositiveOnArt", ReportUtils.map(transferOutTotalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnArt OR diedTotalEnrolled8MonthsHivPositiveOnArt OR abscondedTotalEnrolled8MonthsHivPositiveOnArt OR transferOutTotalEnrolled8MonthsHivPositiveOnArt");
		return cd;
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 2 months
	 * @return cohort definition
	 */
	public CohortDefinition newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(int highMonths, int leastMonths) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("pulmoaryTbSmearNegative", ReportUtils.map(pulmonaryTbSmearNegative(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("enrolled15MonthsAgo", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrBefore-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore"));
		cd.addSearch("enrolled12MonthsAgo", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrBefore-"+ leastMonths +"m},enrolledOnOrBefore=${onOrBefore"));
		cd.addSearch("resultsAt2Months", ReportUtils.map(startedTbTreatmentResultsAtMonths(2), "onDate=${onOrBefore}"));
		cd.setCompositionString("pulmoaryTbSmearNegative AND enrolled15MonthsAgo AND AND enrolled12MonthsAgo AND resultsAt2Months");
		return cd;
	}

	/**
	 * newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months
	 * finalized initial treatment
	 */
	public CohortDefinition finalizedInitialTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months", ReportUtils.map(newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(15, 12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(completedInitialTreatment(), "onDate=${onOrBefore}"));
		cd.setCompositionString("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months AND finalizedInitialTreatment");
		return cd;
	}

	/**
	 * newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months
	 * died
	 */
	public CohortDefinition diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months", ReportUtils.map(newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(15, 12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(died(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months AND died");
		return cd;
	}

	/**
	 * newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months
	 * absconded
	 */
	public CohortDefinition abscondedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months", ReportUtils.map(newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(15, 12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("absconded", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DEFAULTED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months AND absconded");
		return cd;
	}

	/**
	 * newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months
	 * transfer out
	 */
	public CohortDefinition transferOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months", ReportUtils.map(newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(15, 12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months AND transferOut");
		return cd;
	}

	/**
	 * newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months
	 * transfer out,absconded,died,finalized initial treatment
	 */
	public CohortDefinition transferOutAbscondedDiedFinalizedInitialTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("finalizedInitialTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months", ReportUtils.map(finalizedInitialTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months", ReportUtils.map(diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("abscondedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months", ReportUtils.map(abscondedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months", ReportUtils.map(transferOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("finalizedInitialTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months OR diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months OR abscondedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months OR transferOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months");
		return cd;
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * @return cohort definition
	 */
	public CohortDefinition newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(int highMonths, int leastMonths) {
	
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("pulmoaryTbSmearNegative", ReportUtils.map(pulmonaryTbSmearNegative(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("resultsAt2Months", ReportUtils.map(startedTbTreatmentResultsAtMonths(8), "onDate=${onOrBefore}"));
		cd.setCompositionString("enrolled ");
		return cd;
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * treatment completed
	 * @return cohort definition
	 */
	public CohortDefinition treatmentCompletedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months", ReportUtils.map(newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("treatmentCompleted", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TREATMENT_COMPLETE)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months AND treatmentCompleted");
		return cd;
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * died
	 * @return cohort definition
	 */
	public CohortDefinition diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months", ReportUtils.map(newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months AND died");
		return cd;
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * Out of control
	 * @return cohort definition
	 */
	public CohortDefinition outOfControlNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months", ReportUtils.map(newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("outOfControl", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TYPE_OF_TB_PATIENT), Dictionary.getConcept(Dictionary.RETREATMENT_AFTER_DEFAULT_TUBERCULOSIS)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months AND outOfControl");
		return cd;
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * transfer out
	 * @return cohort definition
	 */
	public CohortDefinition transferOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months", ReportUtils.map(newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months AND transferOut");
		return cd;
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * became smear positive
	 * @return cohort definition
	 */
	public CohortDefinition becameSmearPositiveNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months", ReportUtils.map(newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("becameSmearPositive", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_FAILURE)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months AND becameSmearPositive");
		return cd;
	}

	/**
	 * NewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months
	 * transfer out,out of control,became smear positive,died,completed treatment
	 */
	public CohortDefinition transferOutOutOfControlDiedCompletedTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("treatmentCompleted", ReportUtils.map(treatmentCompletedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("outOfControl", ReportUtils.map(outOfControlNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferOut", ReportUtils.map(transferOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("becameSmearPositive", ReportUtils.map(becameSmearPositiveNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("treatmentCompleted OR died OR outOfControl OR transferOut OR becameSmearPositive");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 2 months
	 *  @return cohort definition
	 */
	public CohortDefinition extraPulmonaryTbResultsAt2Months(int highMonths, int leastMonths) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbPatients", ReportUtils.map(extraPulmonaryTbPatients(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrAfter-"+ leastMonths + "m}"));
		cd.addSearch("resultsAt2Months", ReportUtils.map(startedTbTreatmentResultsAtMonths(2), "onDate=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbPatients AND enrolled AND resultsAt2Months");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 2 months
	 *  finalizedInitialTreatment
	 *  @return cohort definition
	 */
	public CohortDefinition finalizedInitialTreatmentExtraPulmonaryTbResultsAt2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbResultsAt2Months", ReportUtils.map(extraPulmonaryTbResultsAt2Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TREATMENT_COMPLETE)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbResultsAt2Months AND finalizedInitialTreatment");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 2 months
	 *  died
	 *  @return cohort definition
	 */
	public CohortDefinition diedExtraPulmonaryTbResultsAt2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbResultsAt2Months", ReportUtils.map(extraPulmonaryTbResultsAt2Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbResultsAt2Months AND died");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 2 months
	 *  absconded
	 *  @return cohort definition
	 */
	public CohortDefinition abscondedExtraPulmonaryTbResultsAt2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbResultsAt2Months", ReportUtils.map(extraPulmonaryTbResultsAt2Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("absconded", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DEFAULTED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbResultsAt2Months AND absconded");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 2 months
	 *  transferredOut
	 *  @return cohort definition
	 */
	public CohortDefinition transferredOutExtraPulmonaryTbResultsAt2Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbResultsAt2Months", ReportUtils.map(extraPulmonaryTbResultsAt2Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbResultsAt2Months AND transferredOut");
		return cd;
	}

	/**
	 * ExtraPulmonaryTbResultsAt2Months
	 * transfer out,absconded,died,completed treatment
	 */
	public CohortDefinition transferOutAbscondedDiedCompletedTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("finalizedInitialTreatment", ReportUtils.map(finalizedInitialTreatmentExtraPulmonaryTbResultsAt2Months(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(diedExtraPulmonaryTbResultsAt2Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("absconded", ReportUtils.map(abscondedExtraPulmonaryTbResultsAt2Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(transferredOutExtraPulmonaryTbResultsAt2Months(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("finalizedInitialTreatment OR died OR absconded OR transferredOut");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 8 months
	 *  @return cohort definition
	 */
	public CohortDefinition extraPulmonaryTbResultsAt8Months(int highMonths, int leastMonths) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbPatients", ReportUtils.map(extraPulmonaryTbPatients(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrAfter-"+ leastMonths + "m}"));
		cd.addSearch("resultsAt2Months", ReportUtils.map(startedTbTreatmentResultsAtMonths(8), "onDate=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbPatients AND enrolled AND resultsAt2Months");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 8 months
	 *  treatment completed
	 *  @return cohort definition
	 */
	public CohortDefinition treatmentCompleteExtraPulmonaryTbResultsAt8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbResultsAt8Months", ReportUtils.map(extraPulmonaryTbResultsAt8Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("treatmentCompleted", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TREATMENT_COMPLETE)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbResultsAt8Months AND treatmentCompleted");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 8 months
	 *  died
	 *  @return cohort definition
	 */
	public CohortDefinition diedExtraPulmonaryTbResultsAt8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbResultsAt8Months", ReportUtils.map(extraPulmonaryTbResultsAt8Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.DIED)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbResultsAt8Months AND died");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 8 months
	 *  out of control
	 *  @return cohort definition
	 */
	public CohortDefinition outOfControlExtraPulmonaryTbResultsAt8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbResultsAt8Months", ReportUtils.map(extraPulmonaryTbResultsAt8Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("outOfControl", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TYPE_OF_TB_PATIENT), Dictionary.getConcept(Dictionary.RETREATMENT_AFTER_DEFAULT_TUBERCULOSIS)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbResultsAt8Months AND outOfControl");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 8 months
	 *  transfer out
	 *  @return cohort definition
	 */
	public CohortDefinition transferredOutExtraPulmonaryTbResultsAt8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("extraPulmonaryTbResultsAt8Months", ReportUtils.map(extraPulmonaryTbResultsAt8Months(15, 12), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME), Dictionary.getConcept(Dictionary.TRANSFERRED_OUT)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("extraPulmonaryTbResultsAt8Months AND transferredOut");
		return cd;
	}

	/**
	 *patients registered 12 to 15 months earlier
	 *  results at 8 months
	 *  transfer out, out of control, died, treatment completed
	 *  @return cohort definition
	 */
	public CohortDefinition transferOutOutOfControlDiedCompletedTreatmentExtraPulmonaryTbResultsAt8Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("treatmentCompleted", ReportUtils.map(treatmentCompleteExtraPulmonaryTbResultsAt8Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("died", ReportUtils.map(diedExtraPulmonaryTbResultsAt8Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("outOfControl", ReportUtils.map(outOfControlExtraPulmonaryTbResultsAt8Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferredOut", ReportUtils.map(transferredOutExtraPulmonaryTbResultsAt8Months(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("treatmentCompleted OR died OR outOfControl OR transferredOut");
		return cd;
	}       

	public CohortDefinition totalEnrolledResultswithsmearnegativeculturenegativeAt6Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearCulturenegativeCalculation());
		cp.setName("Patients who are on smear negative and culture negative");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingCultureAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_DiedAt6Mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_DefaultedAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_TransferedoutAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
	
		cd.setCompositionString("usingCultureAgo AND usingMonthsAgo AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearnegativeculturenegativeAtMonths(int highMonths, int leastMonths) {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept labresult=Dictionary.getConcept(Dictionary.AFB_NOT_SEEN)	;	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("labresult", ReportUtils.map(commonCohorts.hasObs(labtest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtest", ReportUtils.map(commonCohorts.hasObs( culturetest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestOne", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_Defaulted(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_Transferedout(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND labresult  AND (labtest OR labtestOne) AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearnegativeculturepositiveAt6Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearnegativeCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingCultureAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_DiedAt6Mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_DefaultedAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_TransferedoutAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
	
		cd.setCompositionString("usingCultureAgo AND usingMonthsAgo AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearnegativeculturepositiveAtMonths(int highMonths, int leastMonths) {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept labresult=Dictionary.getConcept(Dictionary.AFB_NOT_SEEN)	;	
		Concept cultureresult=Dictionary.getConcept(Dictionary.POSITIVE)	;	
		Concept singlePositive=Dictionary.getConcept(Dictionary.SINGLE_POSITIVE);
		Concept doublePositive=Dictionary.getConcept(Dictionary.DOUBLE_POSITIVE);
		Concept triplePositive=Dictionary.getConcept(Dictionary.TRIPLE_POSITIVE);
		Concept colonies=Dictionary.getConcept(Dictionary.COLONIES);
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("labresult", ReportUtils.map(commonCohorts.hasObs(labtest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsingle", ReportUtils.map(commonCohorts.hasObs( culturetest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestdouble", ReportUtils.map(commonCohorts.hasObs( culturetest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtesttriple", ReportUtils.map(commonCohorts.hasObs( culturetest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestcolonies", ReportUtils.map(commonCohorts.hasObs( culturetest,colonies), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestOne", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,cultureresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_Defaulted(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_Transferedout(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND labresult  AND (labtestsingle OR labtestdouble OR labtesttriple OR labtestcolonies OR labtestOne) AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearnegativeculturepositiveAtMonths() {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept labresult=Dictionary.getConcept(Dictionary.NEGATIVE)	;	
		Concept cultureresult=Dictionary.getConcept(Dictionary.POSITIVE)	;	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("labresult", ReportUtils.map(commonCohorts.hasObs(labtest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labsolidtest", ReportUtils.map(commonCohorts.hasObs( culturetest,cultureresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labliquidtest", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,cultureresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND labresult  AND labsolidtest OR labliquidtest");
		return cd;
	}
    
	public CohortDefinition totalEnrolledResultswithsmearnegativecultureunknownAt6Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearnegativecultureunknownCalculation());
		cp.setName("Patients who are on smear negative and culture unknown");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingCultureAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_DiedAt6Mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_DefaultedAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_TransferedoutAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("usingCultureAgo AND usingMonthsAgo  AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearnegativecultureunknownAtMonths(int highMonths, int leastMonths) {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept labresult=Dictionary.getConcept(Dictionary.NEGATIVE)	;	
		Concept afb=Dictionary.getConcept(Dictionary.AFB_NOT_SEEN)	;
		Concept singlePositive=Dictionary.getConcept(Dictionary.SINGLE_POSITIVE);
		Concept doublePositive=Dictionary.getConcept(Dictionary.DOUBLE_POSITIVE);
		Concept triplePositive=Dictionary.getConcept(Dictionary.TRIPLE_POSITIVE);
		Concept colonies=Dictionary.getConcept(Dictionary.COLONIES);
		Concept cultureresult=Dictionary.getConcept(Dictionary.POSITIVE)	;	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("labresult", ReportUtils.map(commonCohorts.hasObs(labtest,afb), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtest", ReportUtils.map(commonCohorts.hasObs( culturetest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestOne", ReportUtils.map(commonCohorts.hasObs(cultureliquidtest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));

		cd.addSearch("labtestsolidsingle", ReportUtils.map(commonCohorts.hasObs( culturetest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsoliddouble", ReportUtils.map(commonCohorts.hasObs( culturetest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsolidtriple", ReportUtils.map(commonCohorts.hasObs( culturetest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsolidcolonies", ReportUtils.map(commonCohorts.hasObs( culturetest,colonies), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestliquid", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,cultureresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_Defaulted(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_Transferedout(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND labresult AND NOT (labtest OR labtestOne) AND NOT (labtestsolidsingle OR labtestsoliddouble OR labtestsolidtriple OR labtestsolidcolonies OR labtestliquid)AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearpositiveculturenegativeAtMonths(int highMonths, int leastMonths) {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept labresult=Dictionary.getConcept(Dictionary.NEGATIVE)	;	
		Concept trace=Dictionary.getConcept(Dictionary.TRACE);
		Concept singlePositive=Dictionary.getConcept(Dictionary.SINGLE_POSITIVE);
		Concept doublePositive=Dictionary.getConcept(Dictionary.DOUBLE_POSITIVE);
		Concept triplePositive=Dictionary.getConcept(Dictionary.TRIPLE_POSITIVE);	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("labresulttrace", ReportUtils.map(commonCohorts.hasObs(labtest,trace), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labresultsingle", ReportUtils.map(commonCohorts.hasObs(labtest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labresultdouble", ReportUtils.map(commonCohorts.hasObs(labtest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labresulttriple", ReportUtils.map(commonCohorts.hasObs(labtest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtest", ReportUtils.map(commonCohorts.hasObs( culturetest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestOne", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_Defaulted(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_Transferedout(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND (labresulttrace OR labresultsingle OR labresultdouble OR labresulttriple) AND (labtest OR labtestOne) AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearpositiveculturenegativeAt6Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientswithsmearpositiveculturenegativeCalculation());
		cp.setName("Patients who are on smear positive and culture negative");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingCultureAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_DiedAt6Mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_DefaultedAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_TransferedoutAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("usingCultureAgo AND usingMonthsAgo  AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearpositiveculturepositiveAtMonths(int highMonths, int leastMonths) {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept trace=Dictionary.getConcept(Dictionary.TRACE);
		Concept singlePositive=Dictionary.getConcept(Dictionary.SINGLE_POSITIVE);
		Concept doublePositive=Dictionary.getConcept(Dictionary.DOUBLE_POSITIVE);
		Concept triplePositive=Dictionary.getConcept(Dictionary.TRIPLE_POSITIVE);
		Concept colonies=Dictionary.getConcept(Dictionary.COLONIES);
		Concept cultureresult=Dictionary.getConcept(Dictionary.POSITIVE)	;	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled"  );
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("labresulttrace", ReportUtils.map(commonCohorts.hasObs(labtest,trace), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labresultsingle", ReportUtils.map(commonCohorts.hasObs(labtest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labresultdouble", ReportUtils.map(commonCohorts.hasObs(labtest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labresulttriple", ReportUtils.map(commonCohorts.hasObs(labtest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsingle", ReportUtils.map(commonCohorts.hasObs( culturetest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestdouble", ReportUtils.map(commonCohorts.hasObs( culturetest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtesttriple", ReportUtils.map(commonCohorts.hasObs( culturetest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestcolonies", ReportUtils.map(commonCohorts.hasObs( culturetest,colonies), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestOne", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,cultureresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestTwo", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_Defaulted(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_Transferedout(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND (labresulttrace OR labresultsingle OR labresultdouble OR labresulttriple) AND (labtestsingle OR labtestdouble OR labtesttriple OR labtestcolonies OR labtestOne) AND NOT labtestTwo AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree) ");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearpositiveculturepositiveAt6Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithwithsmearpositiveculturepositiveCalculation());
		cp.setName("Patients who are on smear positive and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingCultureAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_DiedAt6Mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_DefaultedAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_TransferedoutAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("usingCultureAgo AND usingMonthsAgo AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearpositivecultureunknownAtMonths(int highMonths, int leastMonths) {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept labresult=Dictionary.getConcept(Dictionary.NEGATIVE)	;	
		Concept trace=Dictionary.getConcept(Dictionary.TRACE);
		Concept singlePositive=Dictionary.getConcept(Dictionary.SINGLE_POSITIVE);
		Concept doublePositive=Dictionary.getConcept(Dictionary.DOUBLE_POSITIVE);
		Concept triplePositive=Dictionary.getConcept(Dictionary.TRIPLE_POSITIVE);
		Concept colonies=Dictionary.getConcept(Dictionary.COLONIES);
		Concept cultureresult=Dictionary.getConcept(Dictionary.POSITIVE)	;	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("labresulttrace", ReportUtils.map(commonCohorts.hasObs(labtest,trace), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labresultsingle", ReportUtils.map(commonCohorts.hasObs(labtest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labresultdouble", ReportUtils.map(commonCohorts.hasObs(labtest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labresulttriple", ReportUtils.map(commonCohorts.hasObs(labtest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtest", ReportUtils.map(commonCohorts.hasObs( culturetest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestOne", ReportUtils.map(commonCohorts.hasObs(cultureliquidtest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsolidsingle", ReportUtils.map(commonCohorts.hasObs( culturetest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsoliddouble", ReportUtils.map(commonCohorts.hasObs( culturetest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsolidtriple", ReportUtils.map(commonCohorts.hasObs( culturetest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsolidcolonies", ReportUtils.map(commonCohorts.hasObs( culturetest,colonies), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestliquid", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,cultureresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_Defaulted(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_Transferedout(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND (labresulttrace OR labresultsingle OR labresultdouble OR labresulttriple) AND NOT(labtest OR labtestOne) AND NOT(labtestsolidsingle OR labtestsoliddouble OR labtestsolidtriple OR labtestsolidcolonies OR labtestliquid) AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearpositivecultureunknownAt6Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearpositiveCultureunknownCalculation());
		cp.setName("Patients who are on smear negative and culture unknown");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingCultureAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_DiedAt6Mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_DefaultedAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_TransferedoutAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
	
		cd.setCompositionString("usingCultureAgo AND usingMonthsAgo  AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearunknownculturenegativeAtMonths(int highMonths, int leastMonths) {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept singlePositive=Dictionary.getConcept(Dictionary.SINGLE_POSITIVE);
		Concept doublePositive=Dictionary.getConcept(Dictionary.DOUBLE_POSITIVE);
		Concept triplePositive=Dictionary.getConcept(Dictionary.TRIPLE_POSITIVE);
		Concept trace=Dictionary.getConcept(Dictionary.TRACE);
		Concept colonies=Dictionary.getConcept(Dictionary.COLONIES);
		Concept afb=Dictionary.getConcept(Dictionary.AFB_NOT_SEEN);
		Concept labresult=Dictionary.getConcept(Dictionary.NEGATIVE)	;	
		Concept cultureresult=Dictionary.getConcept(Dictionary.POSITIVE)	;	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("labtestNeg", ReportUtils.map(commonCohorts.hasObs( labtest,afb), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestPostrace", ReportUtils.map(commonCohorts.hasObs( labtest,trace), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestPossingle", ReportUtils.map(commonCohorts.hasObs( labtest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestPosdouble", ReportUtils.map(commonCohorts.hasObs( labtest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestPostriple", ReportUtils.map(commonCohorts.hasObs( labtest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtest", ReportUtils.map(commonCohorts.hasObs( culturetest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestOne", ReportUtils.map(commonCohorts.hasObs(cultureliquidtest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsolidcolonies", ReportUtils.map(commonCohorts.hasObs( culturetest,colonies), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsolidsingle", ReportUtils.map(commonCohorts.hasObs( culturetest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsoliddouble", ReportUtils.map(commonCohorts.hasObs( culturetest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsolidtriple", ReportUtils.map(commonCohorts.hasObs( culturetest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestliquid", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,cultureresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_Defaulted(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_Transferedout(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled  AND (labtest OR labtestOne) AND  NOT (labtestNeg OR labtestPostrace OR labtestPossingle OR labtestPosdouble OR labtestPostriple) AND NOT (labtestsolidcolonies OR labtestsolidsingle OR labtestsoliddouble OR labtestsolidtriple OR labtestliquid)  NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearunknownculturenegativeAt6Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearunknownCulturenegativeCalculation());
		cp.setName("Patients who are on smear unknown and culture negative");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingCultureAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_DiedAt6Mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_DefaultedAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_TransferedoutAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("usingCultureAgo AND usingMonthsAgo  AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearunknownculturepositiveAtMonths(int highMonths, int leastMonths) {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept labresult=Dictionary.getConcept(Dictionary.NEGATIVE)	;	
		Concept afb=Dictionary.getConcept(Dictionary.AFB_NOT_SEEN)	;	
		Concept cultureresult=Dictionary.getConcept(Dictionary.POSITIVE);	
		Concept trace=Dictionary.getConcept(Dictionary.TRACE);
		Concept singlePositive=Dictionary.getConcept(Dictionary.SINGLE_POSITIVE);
		Concept doublePositive=Dictionary.getConcept(Dictionary.DOUBLE_POSITIVE);
		Concept triplePositive=Dictionary.getConcept(Dictionary.TRIPLE_POSITIVE);
		Concept colonies=Dictionary.getConcept(Dictionary.COLONIES);
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("labtestNeg", ReportUtils.map(commonCohorts.hasObs( labtest,afb), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestPostrace", ReportUtils.map(commonCohorts.hasObs( labtest,trace), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestPossingle", ReportUtils.map(commonCohorts.hasObs( labtest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestPosdouble", ReportUtils.map(commonCohorts.hasObs( labtest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestPostriple", ReportUtils.map(commonCohorts.hasObs( labtest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		
		
		cd.addSearch("labtestcolonies", ReportUtils.map(commonCohorts.hasObs( culturetest,colonies), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsinglePositive", ReportUtils.map(commonCohorts.hasObs( culturetest,singlePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestdoublePositive", ReportUtils.map(commonCohorts.hasObs( culturetest,doublePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtesttriplePositive", ReportUtils.map(commonCohorts.hasObs( culturetest,triplePositive), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestOne", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,cultureresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestsolid", ReportUtils.map(commonCohorts.hasObs( culturetest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestliquid", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest,labresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_Defaulted(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_Transferedout(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND NOT (labtestNeg OR labtestPostrace OR labtestPossingle OR labtestPosdouble OR labtestPostriple ) AND NOT (labtestsolid OR labtestliquid) AND (labtestcolonies OR labtestsinglePositive OR labtestdoublePositive OR labtesttriplePositive OR labtestOne) AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)  ");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearunknownculturepositiveAt6Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearunknownCulturepositiveCalculation());
		cp.setName("Patients who are on smear unknown and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingCultureAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_DiedAt6Mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_DefaultedAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_TransferedoutAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("usingCultureAgo AND usingMonthsAgo AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearunknowncultureunknownAtMonths(int highMonths, int leastMonths) {
		Concept labtest=Dictionary.getConcept(Dictionary.SPUTUM_SMEAR_TEST);
		Concept labresult=Dictionary.getConcept(Dictionary.NEGATIVE)	;	
		Concept cultureresult=Dictionary.getConcept(Dictionary.POSITIVE)	;	
		Concept culturetest=Dictionary.getConcept(Dictionary.CULTURE_SOLID);
		Concept cultureliquidtest=Dictionary.getConcept(Dictionary.CULTURE_LIQUID);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("labresult", ReportUtils.map(commonCohorts.hasObs(labtest), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtest", ReportUtils.map(commonCohorts.hasObs( culturetest), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("labtestOne", ReportUtils.map(commonCohorts.hasObs( cultureliquidtest), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeOne", ReportUtils.map(treatmentOutcome_Died(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeTwo", ReportUtils.map(treatmentOutcome_Defaulted(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("OutcomeThree", ReportUtils.map(treatmentOutcome_Transferedout(15,12),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND NOT labresult AND NOT ( labtest OR labtestOne ) AND NOT (OutcomeOne OR OutcomeTwo OR OutcomeThree)");
		return cd;
	}
	public CohortDefinition totalEnrolledResultswithsmearunknowncultureunknownAt6Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearunknownCultureunknownCalculation());
		cp.setName("Patients who are on smear unknown and culture unkown");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingCultureAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingtreatmentdied", ReportUtils.map(treatmentOutcome_DiedAt6Mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("usingtreatmentloss", ReportUtils.map(treatmentOutcome_DefaultedAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("usingtreatmenteval", ReportUtils.map(treatmentOutcome_TransferedoutAt6mnth(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("usingCultureAgo AND usingMonthsAgo AND NOT (usingtreatmentdied OR usingtreatmentloss OR usingtreatmenteval)");
		return cd;
	}
	public CohortDefinition totalenrolledResultstbpatientwithdiedoutcome() {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		
		Concept outcomresult=Dictionary.getConcept(Dictionary.DIED);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("outcomeresult", ReportUtils.map(commonCohorts.hasObs(tboutcome,outcomresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("enrolled AND outcomeresult");
		return cd;
	}
	public CohortDefinition totalenrolledResultstbpatientwithlosstofollowupoutcome() {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		
		Concept outcomresult=Dictionary.getConcept(Dictionary.LOSS_TO_FOLLOW_UP);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("outcomeresult", ReportUtils.map(commonCohorts.hasObs(tboutcome,outcomresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("enrolled AND outcomeresult");
		return cd;
	}
	public CohortDefinition totalenrolledResultstbpatientwithnotevaluatedoutcome() {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		
		Concept outcomresult=Dictionary.getConcept(Dictionary.NOT_EVALUATED);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("outcomeresult", ReportUtils.map(commonCohorts.hasObs(tboutcome,outcomresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("enrolled AND outcomeresult");
		return cd;
	}
	public CohortDefinition totalenrolledResultstbpatientwithcureoutcome() {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		
		Concept outcomresult=Dictionary.getConcept(Dictionary.CURE_OUTCOME);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with cure outcome");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrAfter}"));
		cd.addSearch("outcomeresult", ReportUtils.map(commonCohorts.hasObs(tboutcome,outcomresult), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("enrolled AND outcomeresult");
		return cd;
	}
	public CohortDefinition totalpatientRegisteredInTb() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new TotalPatientRegisteredCalculation());
		cd.setName("registration");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		return cd;
	}

	public CohortDefinition totalpatientOnMedicationInTb() {
		
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new TotalPatientOnMedicationCalculation());
		cd.setName("medication");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedtb() {
		
		Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		Concept relapseIR=Dictionary.getConcept(Dictionary.RELAPSE_IR);
		Concept relapseRR=Dictionary.getConcept(Dictionary.RELAPSE_RR);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	
		cd.addSearch("reggroupOne", ReportUtils.map(commonCohorts.hasObs(registration_group,relapseIR), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("reggroupTwo", ReportUtils.map(commonCohorts.hasObs(registration_group,relapseRR), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
        cd.setCompositionString("reggroupOne OR reggroupTwo ");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedtbwithpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with Relapse ( IR, RR)");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithRelapseCategoryCalculation());
		rel.setName("Patients who are on relapse category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		
        cd.setCompositionString(" usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	
	public CohortDefinition totalpatientOnConfirmedtbwithpasAt36Mnth() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with Relapse ( IR, RR)");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithRelapseCategoryCalculation());
		rel.setName("Patients who are on relapse category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		
        cd.setCompositionString(" usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedtbwithoutpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));

		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithRelapseCategoryCalculation());
		rel.setName("Patients who are on relapse category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		
        cd.setCompositionString(" usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
	}
	
	public CohortDefinition totalpatientOnConfirmedtbwithoutpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));

		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithRelapseCategoryCalculation());
		rel.setName("Patients who are on relapse category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		
        cd.setCompositionString(" usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedtbwitheitherpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithRelapseCategoryCalculation());
		rel.setName("Patients who are on relapse category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		
        cd.setCompositionString(" usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedtbwitheitherpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithRelapseCategoryCalculation());
		rel.setName("Patients who are on relapse category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		
        cd.setCompositionString(" usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatItb() {
	
		Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		Concept catIR=Dictionary.getConcept(Dictionary.NONCONVERTER_IR);
		Concept catRR=Dictionary.getConcept(Dictionary.NONCONVERTER_RR);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("reggroupOne", ReportUtils.map(commonCohorts.hasObs(registration_group,catIR), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("reggroupTwo", ReportUtils.map(commonCohorts.hasObs(registration_group,catRR), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("reggroupOne OR reggroupTwo");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatItbwithpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with Non-Converter (IR , RR)");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithNonConverterCalculation());
		rel.setName("Patients who are on Non coverter category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatItbwithpasAt36Mnth() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with Non-Converter (IR , RR)");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithNonConverterCalculation());
		rel.setName("Patients who are on Non coverter category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatItbwithoutpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithNonConverterCalculation());
		rel.setName("Patients who are on Non coverter category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	
	public CohortDefinition totalpatientOnConfirmedCatItbwithoutpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithNonConverterCalculation());
		rel.setName("Patients who are on Non coverter category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatItbwitheitherpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithNonConverterCalculation());
		rel.setName("Patients who are on Non coverter category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatItbwitheitherpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithNonConverterCalculation());
		rel.setName("Patients who are on Non coverter category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatIItb() {
		
		Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		Concept catIIIR=Dictionary.getConcept(Dictionary.TREATMENT_AFTER_FAILURE_TREATMENT_IR);
		Concept catIIRR=Dictionary.getConcept(Dictionary.TREATMENT_AFTER_FAILURE_TREATMENT_RR);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("reggroupOne", ReportUtils.map(commonCohorts.hasObs(registration_group,catIIRR), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("reggroupTwo", ReportUtils.map(commonCohorts.hasObs(registration_group,catIIIR), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
        cd.setCompositionString("reggroupOne OR reggroupTwo ");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatIItbwithpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithFailuretreatmentCalculation());
		rel.setName("Patients who are on Failure category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatIItbwithpasAt36Months() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithFailuretreatmentCalculation());
		rel.setName("Patients who are on Failure category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatIItbwithoutpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithFailuretreatmentCalculation());
		rel.setName("Patients who are on Failure category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
    
	public CohortDefinition totalpatientOnConfirmedCatIItbwithoutpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithFailuretreatmentCalculation());
		rel.setName("Patients who are on Failure category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatIItbwitheitherpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithFailuretreatmentCalculation());
		rel.setName("Patients who are on Failure category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedCatIItbwitheitherpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithFailuretreatmentCalculation());
		rel.setName("Patients who are on Failure category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedDefaulttb() {
		
		Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		Concept losstofolloupIR=Dictionary.getConcept(Dictionary.TREATMENT_AFTER_LOSS_FOLLOW_UP_IR);
		Concept losstofolloupRR=Dictionary.getConcept(Dictionary.TREATMENT_AFTER_LOSS_FOLLOW_UP_RR);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("reggroupOne", ReportUtils.map(commonCohorts.hasObs(registration_group,losstofolloupIR), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("reggroupTwo", ReportUtils.map(commonCohorts.hasObs(registration_group,losstofolloupRR), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("reggroupOne OR reggroupTwo");
	       
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedDefaulttbwithpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with Treatment after lost to follow up (IR , RR)");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 OR 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithlosstoFollowupCalculation());
		rel.setName("Patients who are on lossto follow up category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedDefaulttbwithpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with Treatment after lost to follow up (IR , RR)");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered  36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithlosstoFollowupCalculation());
		rel.setName("Patients who are on lossto follow up category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedDefaulttbwithoutpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 OR 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithlosstoFollowupCalculation());
		rel.setName("Patients who are on lossto follow up category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
       
		
	}
   
	public CohortDefinition totalpatientOnConfirmedDefaulttbwithoutpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered  36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithlosstoFollowupCalculation());
		rel.setName("Patients who are on lossto follow up category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
       
		
	}
	public CohortDefinition totalpatientOnConfirmedDefaulttbwitheitherpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 OR 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithlosstoFollowupCalculation());
		rel.setName("Patients who are on lossto follow up category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedDefaulttbwitheitherpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered  36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithlosstoFollowupCalculation());
		rel.setName("Patients who are on lossto follow up category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedStandardtb() {
		
		Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		Concept stndard=Dictionary.getConcept(Dictionary.FAILURE_MDR_TB_REGIMEN);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("reggroup", ReportUtils.map(commonCohorts.hasObs(registration_group,stndard), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
        cd.setCompositionString("reggroup");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedStandardtbwithpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 OR 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithStandardMDRcasesCalculation());
		rel.setName("Patients who are on Standard category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedStandardtbwithpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered  36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithStandardMDRcasesCalculation());
		rel.setName("Patients who are on Standard category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedStandardtbwithoutpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 OR 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithStandardMDRcasesCalculation());
		rel.setName("Patients who are on Standard category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedStandardtbwithoutpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered  36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithStandardMDRcasesCalculation());
		rel.setName("Patients who are on Standard category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedStandardtbwitheitherpas() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 OR 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithStandardMDRcasesCalculation());
		rel.setName("Patients who are on Standard category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOnConfirmedStandardtbwitheitherpasAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered  36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithStandardMDRcasesCalculation());
		rel.setName("Patients who are on Standard category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithpasmearpositive() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithSmearCulturepositiveCalculation());
		cp.setName("Patients who are on smear positive and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		rel.setName("Patients who are registered 24 months ago");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");

		return comp;
	}
	public CohortDefinition totalpatientOutcomewithpasmearpositiveAt36Months() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithSmearCulturepositiveCalculation());
		cp.setName("Patients who are on smear positive and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		rel.setName("Patients who are registered 36 months ago");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");

		return comp;
	}

	public CohortDefinition totalpatientOutcomewithpaswithsmearnegative() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearnegativeCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		rel.setName("Patients who are registered 24 OR 36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");

		return comp;
	}
	public CohortDefinition totalpatientOutcomewithpaswithsmearnegativeAt36Months() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearnegativeCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		rel.setName("Patients who are registered 24 OR 36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");

		return comp;
	}
	public CohortDefinition totalpatientOutcomewithoutpasmearpositive() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithSmearCulturepositiveCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		rel.setName("Patients who are registered 24 OR 36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");

		return comp;
	}
	public CohortDefinition totalpatientOutcomewithoutpasmearpositiveAt36Month() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithSmearCulturepositiveCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		rel.setName("Patients who are registered  36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");

		return comp;
	}
	public CohortDefinition totalpatientOutcomewitheitherpasmearpositive() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithSmearCulturepositiveCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		rel.setName("Patients who are registered 24 OR 36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");

		return comp;
	}
	public CohortDefinition totalpatientOutcomewitheitherpasmearpositiveAt36Month() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithSmearCulturepositiveCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		rel.setName("Patients who are registered 36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");

		return comp;
	}
	public CohortDefinition totalpatientOutcomewithoutpaswithsmearnegative() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearnegativeCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		rel.setName("Patients who are registered 24 OR 36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");


		return comp;
	}
	public CohortDefinition totalpatientOutcomewithoutpaswithsmearnegativeAt36Month() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearnegativeCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		rel.setName("Patients who are registered  36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");


		return comp;
	}
	public CohortDefinition totalpatientOutcomewithpaswitheithersmearnegative() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));

		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearnegativeCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		rel.setName("Patients who are registered 24 OR 36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");


		return comp;
	}
	public CohortDefinition totalpatientOutcomewithpaswitheithersmearnegativeAt36Month() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		cd.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));

		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithsmearnegativeCalculation());
		cp.setName("Patients who are on smear negative and culture positive");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		rel.setName("Patients who are registered 24 OR 36 months ago ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition comp = new CompositionCohortDefinition();
		comp.setName("Patients with smear positive and culture positive");
		comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		comp.addSearch("usingRegime", ReportUtils.map(cd, "onDate=${onOrBefore}"));
		comp.addSearch("usingLabresult", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		comp.addSearch("usingMonthsresult", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		comp.setCompositionString("usingRegime and usingLabresult AND usingMonthsresult");


		return comp;
	}
	public CohortDefinition totalpatientOutcomewithNewCategory() {
		
		Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		Concept newpat=Dictionary.getConcept(Dictionary.NEW_PATIENT);
		Concept newmdr=Dictionary.getConcept(Dictionary.NEW_MDR);
		Concept newplhiv=Dictionary.getConcept(Dictionary.NEW_PLHIV);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	
		cd.addSearch("reggroupOne", ReportUtils.map(commonCohorts.hasObs(registration_group,newpat), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("reggroupTwo", ReportUtils.map(commonCohorts.hasObs(registration_group,newmdr), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("reggroupThree", ReportUtils.map(commonCohorts.hasObs(registration_group,newplhiv), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("reggroupOne OR reggroupTwo OR reggroupThree");
       
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithpaswithNewCategory() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS ");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithNewCategoryCalculation());
		rel.setName("Patients who are on New category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithpaswithNewCategoryAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS ");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithNewCategoryCalculation());
		rel.setName("Patients who are on New category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithoutpaswithoutNewCategory() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithNewCategoryCalculation());
		rel.setName("Patients who are on New category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	
	public CohortDefinition totalpatientOutcomewithoutpaswithoutNewCategoryAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithNewCategoryCalculation());
		rel.setName("Patients who are on New category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithoutpaswitheitherNewCategory() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithNewCategoryCalculation());
		rel.setName("Patients who are on New category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithoutpaswitheitherNewCategoryAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientswithNewCategoryCalculation());
		rel.setName("Patients who are on New category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
public CohortDefinition totalpatientOutcomewithOtherCategory() {
		
	Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
	Concept previuoslytreated=Dictionary.getConcept(Dictionary.PREVIOUSLY_TREATED_EP);
	Concept unknownmdr=Dictionary.getConcept(Dictionary.UNKNOWN_OUTCOME_PREVIOUSLY_TREATED_EP);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	
		cd.addSearch("reggroupOne", ReportUtils.map(commonCohorts.hasObs(registration_group,previuoslytreated), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("reggroupTwo", ReportUtils.map(commonCohorts.hasObs(registration_group,unknownmdr), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("reggroupOne OR reggroupTwo ");
       
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithpaswithOtherCategory() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithOtherCategoryCalculation());
		rel.setName("Patients who are on Other category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
		
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithpaswithOtherCategoryAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithOtherCategoryCalculation());
		rel.setName("Patients who are on Other category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
		
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithoutpaswithoutOtherCategory() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithOtherCategoryCalculation());
		rel.setName("Patients who are on Other category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	
	public CohortDefinition totalpatientOutcomewithoutpaswithoutOtherCategoryAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithoutPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithOtherCategoryCalculation());
		rel.setName("Patients who are on Other category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithoutpaswitheitherOtherCategory() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients24monthsagoCalculation());
		cp.setName("Patients who are registered 24 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithOtherCategoryCalculation());
		rel.setName("Patients who are on Other category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition totalpatientOutcomewithoutpaswitheitherOtherCategoryAt36Month() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new PatientOnRegimeWithEitherPASCalculation());
		comp.setName("Patients who are on regime Amk/ Z/ Lfx/ Eto/ Cs/ PAS with smear negative");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new Tbpatients36monthsagoCalculation());
		cp.setName("Patients who are registered 36 months ago ");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition rel = new CalculationCohortDefinition(new TbpatientwithOtherCategoryCalculation());
		rel.setName("Patients who are on Other category ");
		rel.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingCategory", ReportUtils.map(rel, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingRegime AND usingMonthsAgo AND usingCategory");
       
		return cd;
	}
	public CohortDefinition treatmentOutcome_Died(int highMonths, int leastMonths) {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept outcomresult=Dictionary.getConcept(Dictionary.DIED);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment  outcome died");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("givenOutcomeDied", ReportUtils.map(commonCohorts.hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("enrolled AND givenOutcomeDied");
		return cd;
	}
	public CohortDefinition treatmentOutcome_DiedAt6Mnth() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithDiedOutcomeAt6mnthCalculation());
		cp.setName("Patients with died outcome");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingOutcomeAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingOutcomeAgo AND usingMonthsAgo");
		return cd;
		
	}
	public CohortDefinition treatmentOutcome_Died() {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept outcomresult=Dictionary.getConcept(Dictionary.DIED);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment  outcome died");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("givenOutcomeDied", ReportUtils.map(commonCohorts.hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("givenOutcomeDied");
		return cd;
	}
	public CohortDefinition treatmentOutcome_Defaulted(int highMonths, int leastMonths) {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept outcomresult=Dictionary.getConcept(Dictionary.LOSS_TO_FOLLOW_UP);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment loss to follow up outcome");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("givenlosstofollowupOutcome", ReportUtils.map(commonCohorts.hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("enrolled AND givenlosstofollowupOutcome");
		return cd;
	}
	public CohortDefinition treatmentOutcome_DefaultedAt6mnth() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithLossTofollowupOutcomeCalculation());
		cp.setName("Patients with loss to follow up outcome");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingOutcomeAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingOutcomeAgo AND usingMonthsAgo");
		return cd;
	}
	public CohortDefinition treatmentOutcome_Transferedout(int highMonths, int leastMonths) {
		Concept tboutcome=Dictionary.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME);
		Concept outcomresult=Dictionary.getConcept(Dictionary.TRANSFERRED_OUT);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment  outcome transferred");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("givenOutcometransferredout", ReportUtils.map(commonCohorts.hasObs(tboutcome,outcomresult), "onOrBefore=${onOrBefore}"));
		
		cd.setCompositionString("enrolled AND givenOutcometransferredout");
		return cd;
	}
   
	public CohortDefinition treatmentOutcome_TransferedoutAt6mnth() {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithTransferredOutOutComeCalculation());
		cp.setName("Patients with Transferred outcome");
		cp.addParameter(new Parameter("onDate", "On Date", Date.class));
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingOutcomeAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
		cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.setCompositionString("usingOutcomeAgo AND usingMonthsAgo");
		return cd;
	}
	public CohortDefinition totalEnrolledResultsAtMonths(int highMonths, int leastMonths) {
		CalculationCohortDefinition comp = new CalculationCohortDefinition(new TotalPatientOnMedicationCalculation());
		comp.setName("medication");
		comp.addParameter(new Parameter("onDate", "On Date", Date.class));
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.setCompositionString("usingRegime AND enrolled ");
		return cd;
	
	}
	
public CohortDefinition totalEnrolledResultsAtMonths() {
	CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
	comp.setName("medication");
	comp.addParameter(new Parameter("onDate", "On Date", Date.class));
	CalculationCohortDefinition cp = new CalculationCohortDefinition(new TotalPatientRegisteredCalculation());
	cp.setName("registration");
	cp.addParameter(new Parameter("onDate", "On Date", Date.class));
	CompositionCohortDefinition cd = new CompositionCohortDefinition();
	cd.setName("Total enrolled  ");
	cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
	cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
	cd.addSearch("enrolled", ReportUtils.map(cp, "onDate=${onOrBefore}"));
	cd.setCompositionString("usingRegime AND enrolled ");
	return cd;

}
	
public CohortDefinition totalEnrolledResults() {
	CalculationCohortDefinition cp = new CalculationCohortDefinition(new TotalPatientRegisteredCalculation());
	cp.setName("registration");
	cp.addParameter(new Parameter("onDate", "On Date", Date.class));
	CalculationCohortDefinition comp = new CalculationCohortDefinition(new TotalPatientOnMedicationCalculation());
	comp.setName("medication");
	comp.addParameter(new Parameter("onDate", "On Date", Date.class));
	
	CompositionCohortDefinition cd = new CompositionCohortDefinition();
	cd.setName("Total enrolled  ");
	cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
	cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	cd.addSearch("usingRegime", ReportUtils.map(comp, "onDate=${onOrBefore}"));
	cd.addSearch("enrolled", ReportUtils.map(cp, "onDate=${onOrBefore}"));
	cd.setCompositionString("usingRegime AND enrolled ");
	return cd;

}
public CohortDefinition totalEnrolledResultswithTransferIn() {
	Concept registration_group=Dictionary.getConcept(Dictionary.METHOD_OF_ENROLLMENT);
	Concept previuoslytreated=Dictionary.getConcept(Dictionary.TRANSFER_IN);
	
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total confirmed  ");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	
		cd.addSearch("reggroupOne", ReportUtils.map(commonCohorts.hasObs(registration_group,previuoslytreated), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
	
		
		cd.setCompositionString("reggroupOne");
       
		return cd;

}	
public CohortDefinition totalEnrolledResultswithTransferIn(int highMonths, int leastMonths) {
	Concept registration_group=Dictionary.getConcept(Dictionary.METHOD_OF_ENROLLMENT);
	Concept previuoslytreated=Dictionary.getConcept(Dictionary.TRANSFER_IN);
	
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Total enrolled with treatment  outcome transferred");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(), "enrolledOnOrAfter=${onOrAfter-"+ highMonths +"m},enrolledOnOrBefore=${onOrBefore-"+ leastMonths + "m}"));
		cd.addSearch("reggroupOne", ReportUtils.map(commonCohorts.hasObs(registration_group,previuoslytreated), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("reggroupOne");
		
		cd.setCompositionString("enrolled AND reggroupOne");
		return cd;
		
}
public CohortDefinition totalEnrolledResultswithTransferInAtMonths() {
	CalculationCohortDefinition comp = new CalculationCohortDefinition(new TbpatientonMedication6mnthsagoCalculation());
	comp.setName("medication");
	comp.addParameter(new Parameter("onDate", "On Date", Date.class));
	CalculationCohortDefinition cp = new CalculationCohortDefinition(new TbpatientwithTransferredInCalculation());
	cp.setName("Patients with Transferred outcome");
	cp.addParameter(new Parameter("onDate", "On Date", Date.class));
	CompositionCohortDefinition cd = new CompositionCohortDefinition();
	cd.setName("Total enrolled ");
	cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
	cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	cd.addSearch("usingOutcomeAgo", ReportUtils.map(cp, "onDate=${onOrBefore}"));
	cd.addSearch("usingMonthsAgo", ReportUtils.map(comp, "onDate=${onOrBefore}"));
	cd.setCompositionString("usingOutcomeAgo AND usingMonthsAgo");
	return cd;
}
	}
	
   

