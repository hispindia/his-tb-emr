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

import org.openmrs.module.reporting.evaluation.parameter.Parameterizable;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Library of TB related indicator definitions. All indicators require parameters ${startDate} and ${endDate}
 */
@Component
public class TbIndicatorLibrary {

	@Autowired
	private TbCohortLibrary tbCohorts;

	/**
	 * Number of patients screened for TB
	 * @return the indicator
	 */
	public CohortIndicator screenedForTb() {
		return cohortIndicator("patients screened for TB",
				map(tbCohorts.screenedForTb(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Number of patients who died and started TB treatment 12 months ago
	 * @return the indicator
	 */
	public CohortIndicator diedAndStarted12MonthsAgo() {
		return cohortIndicator("patients who started TB treatment 12 months ago and died",
				map(tbCohorts.diedAndStarted12MonthsAgo(), "onOrBefore=${endDate}")
		);
	}

	/**
	 * Number of patients who completed Tb Treatment and are in Tb program
	 * @return the indicator
	 */
	public CohortIndicator completedTbTreatment() {
		return cohortIndicator("patients who completed TB treatment",
				map(tbCohorts.completedTreatment(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}


	/**
	 * Number of patients who defaulted
	 * @return the indicator
	 */
	public CohortIndicator defaulted() {
		return cohortIndicator("patients who defaulted", map(tbCohorts.defaulted(), "onDate=${endDate}"));
	}

	/**
	 * Number of patients in Tb and HIV programs who are taking CTX prophylaxis
	 * @return the indicator
	 */
	public CohortIndicator inTbAndHivProgramsAndOnCtxProphylaxis() {
		return cohortIndicator("in TB and HIV programs and on CTX prophylaxis",
				map(tbCohorts.inTbAndHivProgramsAndOnCtxProphylaxis(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Number of patients in Tb and are HIV tested
	 * @return the indicator
	 */
	public CohortIndicator inTbAndTestedForHiv() {
		return cohortIndicator("in TB program and tested for HIV",
				map(tbCohorts.testedForHivAndInTbProgram(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Number of patients in Tb and are HIV tested and their result is positive
	 * @return the indicator
	 */
	public CohortIndicator inTbAndTestedForHivPositive() {
		return cohortIndicator("in TB program and tested positive for HIV",
				map(tbCohorts.testedHivPositiveAndInTbProgram(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Number of patients with Tb retreatments
	 * @return the indicator
	 */
	public CohortIndicator tbRetreatmentsPatients() {
		return cohortIndicator("TB re-treatment patients",
				map(tbCohorts.tbRetreatments(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Number of patients with Extra pulmonary Tb
	 * @return the indicator
	 */
	public CohortIndicator extraPulmonaryTbPatients() {
		return cohortIndicator("patients with extra pulmonary TB",
				map(tbCohorts.extraPulmonaryTbPatients(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Number of pulmonary TB patients with smear negative results
	 * @return the indicator
	 */
	public CohortIndicator pulmonaryTbSmearNegative() {
		return cohortIndicator("patients with pulmonary TB smear negative results",
				map(tbCohorts.pulmonaryTbSmearNegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Number of pulmonary TB patients with smear positive results
	 * @return the indicator
	 */
	public CohortIndicator pulmonaryTbSmearPositive() {
		return cohortIndicator("patients with pulmonary TB smear positive results",
				map(tbCohorts.pulmonaryTbSmearPositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Patients with new Tb detected cases
	 * @return the indicator
	 */
	public CohortIndicator tbNewDetectedCases() {
		return cohortIndicator("new TB cases detected",
				map(tbCohorts.tbNewDetectedCases(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total enrolled patients into tb program and have ptb smear not done results at 2 months
	 * @return cohort indicator
	 */
	public CohortIndicator totalEnrolled() {
		return cohortIndicator("TB - Total Enrolled",
				map(tbCohorts.totalEnrolledPtbSmearNotDoneResultsAtMonths(12, 8), "onOrAfter=${startDate},onOrBefore=${endDate}")
				);
	}

	/**
	 * Total patients who finalized their treatment
	 * @return Indicator
	 */
	public  CohortIndicator finalizedInitialTreatment() {
		return cohortIndicator("TB - Finalized Initial Treatment",
				map(tbCohorts.ptbSmearNotDoneResults2MonthsFinalizedInitialtreatment(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total patients who died
	 * @return Indicator
	 */
	public  CohortIndicator died() {
		return cohortIndicator("Died",
				map(tbCohorts.ptbSmearNotDoneResults2MonthsDied(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total patients who died
	 * @return Indicator
	 */
	public  CohortIndicator absconded() {
		return cohortIndicator("Absconded",
				map(tbCohorts.ptbSmearNotDoneResults2MonthsAbsconded(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total patients who Transferred out
	 * @return Indicator
	 */
	public  CohortIndicator transferredOut() {
		return cohortIndicator("Transferred Out",
				map(tbCohorts.ptbSmearNotDoneResults2MonthsTransferredOut(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total patients evaluated
	 * @return Indicator
	 */
	public  CohortIndicator totalEvaluated() {
		return cohortIndicator("Total evaluated",
				map(tbCohorts.ptbSmearNotDoneResults2MonthsTotalEvaluated(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator totalEnrolled8MonthsHivPositive() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive",
				map(tbCohorts.totalEnrolled8MonthsHivPositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
				);
	}

	/**
	 * Total number of patients enrollment
	 * hiv negative
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator totalEnrolled8MonthsHivNegative() {
		return cohortIndicator("Total Enrolled 8 months HIV Negative",
				map(tbCohorts.totalEnrolled8MonthsHivNegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator totalEnrolled8MonthsHivTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test NOT done",
				map(tbCohorts.totalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done, hiv+, hiv-
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator totalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test NOT done hiv pos,neg",
				map(tbCohorts.totalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on cpt
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator totalEnrolled8MonthsHivPositiveOnCpt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on cpt",
				map(tbCohorts.totalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on art
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator totalEnrolled8MonthsHivPositiveOnArt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on art",
				map(tbCohorts.totalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * completed the treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentResults8monthsHivPositive() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and finalized initial treatment ",
				map(tbCohorts.finalizedInitialTreatmentResults8monthsHivPositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * died
	 * @return Indicator
	 */
	public CohortIndicator diedResults8monthsHivPositive() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and Died ",
				map(tbCohorts.diedResults8monthsHivPositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * absconded
	 * @return Indicator
	 */
	public CohortIndicator abscondedResults8monthsHivPositive() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and Absconded ",
				map(tbCohorts.abscondedResults8monthsHivPositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * Transferred out
	 * @return Indicator
	 */
	public CohortIndicator transferredOutResults8monthsHivPositive() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and Transferred Out ",
				map(tbCohorts.transferredOutResults8monthsHivPositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * Transferred out, absconded, died, finished initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositive() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and Transferred out, absconded, died, finished initial treatment ",
				map(tbCohorts.finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV negative
	 * finished initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentTotalEnrolled8MonthsHivNegative() {
		return cohortIndicator("Total Enrolled 8 months HIV Negative and finished initial treatment ",
				map(tbCohorts.finalizedInitialTreatmentTotalEnrolled8MonthsHivNegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV negative
	 * died
	 * @return Indicator
	 */
	public CohortIndicator diedTotalEnrolled8MonthsHivNegative() {
		return cohortIndicator("Total Enrolled 8 months HIV Negative and died ",
				map(tbCohorts.diedTotalEnrolled8MonthsHivNegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV negative
	 * absconded
	 * @return Indicator
	 */
	public CohortIndicator abscondedTotalEnrolled8MonthsHivNegative() {
		return cohortIndicator("Total Enrolled 8 months HIV Negative and absconded ",
				map(tbCohorts.abscondedTotalEnrolled8MonthsHivNegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV negative
	 * Transferred out
	 * @return Indicator
	 */
	public CohortIndicator transferredOutTotalEnrolled8MonthsHivNegative() {
		return cohortIndicator("Total Enrolled 8 months HIV Negative and transferred out ",
				map(tbCohorts.transferredOutTotalEnrolled8MonthsHivNegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV negative
	 * Transferred out, absconded, died, finished initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivNegative() {
		return cohortIndicator("Total Enrolled 8 months HIV Negative and Transferred out, absconded, died, finished initial treatment ",
				map(tbCohorts.finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivNegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done
	 * results 8 months
	 * 8-12 months earlier
	 * completed initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finishedInitialTreatmentTotalEnrolled8MonthsHivTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test NOT done and finished initial treatment",
				map(tbCohorts.finalizedInitialTreatmentTotalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done
	 * results 8 months
	 * 8-12 months earlier
	 * died
	 * @return Indicator
	 */
	public CohortIndicator diedTotalEnrolled8MonthsHivTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test NOT done and died",
				map(tbCohorts.diedTotalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done
	 * results 8 months
	 * 8-12 months earlier
	 * absconded
	 * @return Indicator
	 */
	public CohortIndicator abscondedTotalEnrolled8MonthsHivTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test NOT done and absconded",
				map(tbCohorts.abscondedTotalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done
	 * results 8 months
	 * 8-12 months earlier
	 * transferred out
	 * @return Indicator
	 */
	public CohortIndicator transferredOutTotalEnrolled8MonthsHivTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test NOT done and transferred out",
				map(tbCohorts.transferredOutTotalEnrolled8MonthsHivTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV test not done
	 * Transferred out, absconded, died, finished initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test not done and Transferred out, absconded, died, finished initial treatment ",
				map(tbCohorts.finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done, hiv+, hiv-
	 * completed initial treatment
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test total and hiv pos,neg completed treatment",
				map(tbCohorts.finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done, hiv+, hiv-
	 * died
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator diedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test total and hiv pos,neg and died",
				map(tbCohorts.diedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done, hiv+, hiv-
	 * absconded
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator abscondedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test total and hiv pos,neg and absconded",
				map(tbCohorts.abscondedTotalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv test not done, hiv+, hiv-
	 * transferred out
	 * results 8 months
	 * 8-12 months earlier
	 * @return Indicator
	 */
	public CohortIndicator transferredOutTotalEnrolled8MonthsHivPositiveNegativeTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test total and hiv pos,neg and transferred out",
				map(tbCohorts.transferredOutTotalEnrolled8MonthsHivPositiveNegativeTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV test totals
	 * Transferred out, absconded, died, finished initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositiveNegativeTestNotDone() {
		return cohortIndicator("Total Enrolled 8 months HIV test total and Transferred out, absconded, died, finished initial treatment ",
				map(tbCohorts.finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositiveNegativeTestNotDone(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on cpt
	 * results 8 months
	 * 8-12 months earlier
	 * finalized initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizeInitialTreatmentTotalEnrolled8MonthsHivPositiveOnCpt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on cpt and finalized initial treatment",
				map(tbCohorts.finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on cpt
	 * results 8 months
	 * 8-12 months earlier
	 * died
	 * @return Indicator
	 */
	public CohortIndicator diedTotalEnrolled8MonthsHivPositiveOnCpt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on cpt and died",
				map(tbCohorts.diedTotalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on cpt
	 * results 8 months
	 * 8-12 months earlier
	 * absconded
	 * @return Indicator
	 */
	public CohortIndicator abscondedTotalEnrolled8MonthsHivPositiveOnCpt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on cpt and absconded",
				map(tbCohorts.abscondedTotalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on cpt
	 * results 8 months
	 * 8-12 months earlier
	 * transferred out
	 * @return Indicator
	 */
	public CohortIndicator transferredOutTotalEnrolled8MonthsHivPositiveOnCpt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on cpt and transfer out",
				map(tbCohorts.transferredOutTotalEnrolled8MonthsHivPositiveOnCpt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * Transferred out, absconded, died, finished initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositiveOnCpt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on cpt and Transferred out, absconded, died, finished initial treatment ",
				map(tbCohorts.finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositiveOnCpt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on art
	 * results 8 months
	 * 8-12 months earlier
	 * finalized initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnArt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on art and finalized initial treatment",
				map(tbCohorts.finalizedInitialTreatmentTotalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on art
	 * results 8 months
	 * 8-12 months earlier
	 * died
	 * @return Indicator
	 */
	public CohortIndicator diedTotalEnrolled8MonthsHivPositiveOnArt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on art and died",
				map(tbCohorts.diedTotalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on art
	 * results 8 months
	 * 8-12 months earlier
	 * absconded
	 * @return Indicator
	 */
	public CohortIndicator abscondedTotalEnrolled8MonthsHivPositiveOnArt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on art and absconded",
				map(tbCohorts.abscondedTotalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Total number of patients enrollment
	 * hiv positive
	 * on art
	 * results 8 months
	 * 8-12 months earlier
	 * absconded
	 * @return Indicator
	 */
	public CohortIndicator transferredOutTotalEnrolled8MonthsHivPositiveOnArt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on art and transferred out",
				map(tbCohorts.transferOutTotalEnrolled8MonthsHivPositiveOnArt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Patients who finalized initial treatment
	 * Results at 8 months
	 * 8-12 months earlier
	 * HIV positive
	 * Transferred out, absconded, died, finished initial treatment
	 * on art
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositiveOnArt() {
		return cohortIndicator("Total Enrolled 8 months HIV Positive and on art and Transferred out, absconded, died, finished initial treatment ",
				map(tbCohorts.finalizedInitialTreatmentDiedAbscondedTransferredOutResults8monthsHivPositiveOnArt(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 2 months
	 * @return Indicator
	 */
	public CohortIndicator newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		return cohortIndicator("Total Enrolled 2 months results new smear negative PTB ",
				map(tbCohorts.newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(15, 12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 2 months
	 * finalized treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		return cohortIndicator("Total Enrolled 2 months results new smear negative PTB - Finalized Initial Treatment ",
				map(tbCohorts.finalizedInitialTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 2 months
	 * died
	 * @return Indicator
	 */
	public CohortIndicator diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		return cohortIndicator("Total Enrolled 2 months results new smear negative PTB - Died",
				map(tbCohorts.diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 2 months
	 * absconded
	 * @return Indicator
	 */
	public CohortIndicator abscondedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		return cohortIndicator("Total Enrolled 2 months results new smear negative PTB - Absconded",
				map(tbCohorts.abscondedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 2 months
	 * transferred out
	 * @return Indicator
	 */
	public CohortIndicator transferredOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		return cohortIndicator("Total Enrolled 2 months results new smear negative PTB - Transferred Out",
				map(tbCohorts.transferOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 2 months
	 * transferred out, absconded, died, finalized initial treatment
	 * @return Indicator
	 */
	public CohortIndicator transferOutAbscondedDiedFinalizedInitialTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months() {
		return cohortIndicator("Total Enrolled 2 months results new smear negative PTB - All Outcomes",
				map(tbCohorts.transferOutAbscondedDiedFinalizedInitialTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults2Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * @return Indicator
	 */
	public CohortIndicator newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		return cohortIndicator("Total Enrolled 8 months results new smear negative PTB ",
				map(tbCohorts.newSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(15, 12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * treatment complete
	 * @return Indicator
	 */
	public CohortIndicator treatmentCompletedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		return cohortIndicator("Total Enrolled 8 months results new smear negative PTB - Treatment Complete ",
				map(tbCohorts.treatmentCompletedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * died
	 * @return Indicator
	 */
	public CohortIndicator diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		return cohortIndicator("Total Enrolled 8 months results new smear negative PTB - Died ",
				map(tbCohorts.diedNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * out of control
	 * @return Indicator
	 */
	public CohortIndicator outOfControlNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		return cohortIndicator("Total Enrolled 8 months results new smear negative PTB - Out Of Control ",
				map(tbCohorts.outOfControlNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * transferred out
	 * @return Indicator
	 */
	public CohortIndicator transferOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		return cohortIndicator("Total Enrolled 8 months results new smear negative PTB - Transferred Out ",
				map(tbCohorts.transferOutNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * became smear positive
	 * @return Indicator
	 */
	public CohortIndicator becameSmearPositiveNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		return cohortIndicator("Total Enrolled 8 months results new smear negative PTB - Transferred Out ",
				map(tbCohorts.becameSmearPositiveNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * Treatment of new sputum smear negative pulmonary
	 * patients registered 12 to 15 months earlier
	 * pulmonary tb
	 * results at 8 months
	 * became smear positive,transferred out,out of control,treatment complete, died
	 * @return Indicator
	 */
	public CohortIndicator transferOutOutOfControlDiedCompletedTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		return cohortIndicator("Total Enrolled 8 months results new smear negative PTB - Outcomes all ",
				map(tbCohorts.transferOutOutOfControlDiedCompletedTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}


	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 2 months
	 * @return Indicator
	 */
	public CohortIndicator extraPulmonaryTbResultsAt2Months() {
		return cohortIndicator("Total Enrolled 2 months results Extra-Pulmonary TB ",
				map(tbCohorts.extraPulmonaryTbResultsAt2Months(15, 12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 2 months
	 * finalized initial treatment
	 * @return Indicator
	 */
	public CohortIndicator finalizedInitialTreatmentExtraPulmonaryTbResultsAt2Months() {
		return cohortIndicator("Total Enrolled 2 months results Extra-Pulmonary TB - Finalized Initial Treatment ",
				map(tbCohorts.finalizedInitialTreatmentExtraPulmonaryTbResultsAt2Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 2 months
	 * died
	 * @return Indicator
	 */
	public CohortIndicator diedExtraPulmonaryTbResultsAt2Months() {
		return cohortIndicator("Total Enrolled 2 months results Extra-Pulmonary TB - Died ",
				map(tbCohorts.diedExtraPulmonaryTbResultsAt2Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 2 months
	 * absconded
	 * @return Indicator
	 */
	public CohortIndicator abscondedExtraPulmonaryTbResultsAt2Months() {
		return cohortIndicator("Total Enrolled 2 months results Extra-Pulmonary TB - Absconded ",
				map(tbCohorts.abscondedExtraPulmonaryTbResultsAt2Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 2 months
	 * absconded
	 * @return Indicator
	 */
	public CohortIndicator transferredOutExtraPulmonaryTbResultsAt2Months() {
		return cohortIndicator("Total Enrolled 2 months results Extra-Pulmonary TB - Transferred Out ",
				map(tbCohorts.transferredOutExtraPulmonaryTbResultsAt2Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 2 months
	 * all outcomes
	 * @return Indicator
	 */
	public CohortIndicator transferOutAbscondedDiedCompletedTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months() {
		return cohortIndicator("Total Enrolled 2 months results Extra-Pulmonary TB - Outcomes all ",
				map(tbCohorts.transferOutAbscondedDiedCompletedTreatmentNewSputumSmearNegative12to15MonthsEarlierPulmonaryTbResults8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 8 months
	 * @return Indicator
	 */
	public CohortIndicator extraPulmonaryTbResultsAt8Months() {
		return cohortIndicator("Total Enrolled 8 months results Extra-Pulmonary TB ",
				map(tbCohorts.extraPulmonaryTbResultsAt8Months(15, 12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 8 months
	 * treatment completed
	 * @return Indicator
	 */
	public CohortIndicator treatmentCompletedExtraPulmonaryTbResultsAt8Months() {
		return cohortIndicator("Total Enrolled 8 months results Extra-Pulmonary TB - Treatment Complete ",
				map(tbCohorts.treatmentCompleteExtraPulmonaryTbResultsAt8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 8 months
	 * died
	 * @return Indicator
	 */
	public CohortIndicator diedExtraPulmonaryTbResultsAt8Months() {
		return cohortIndicator("Total Enrolled 8 months results Extra-Pulmonary TB - Died",
				map(tbCohorts.diedExtraPulmonaryTbResultsAt8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 8 months
	 * out of control
	 * @return Indicator
	 */
	public CohortIndicator outOfControlExtraPulmonaryTbResultsAt8Months() {
		return cohortIndicator("Total Enrolled 8 months results Extra-Pulmonary TB - Out of Control",
				map(tbCohorts.outOfControlExtraPulmonaryTbResultsAt8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 8 months
	 * transferred out
	 * @return Indicator
	 */
	public CohortIndicator transferredOutExtraPulmonaryTbResultsAt8Months() {
		return cohortIndicator("Total Enrolled 8 months results Extra-Pulmonary TB - Transferred Out",
				map(tbCohorts.transferredOutExtraPulmonaryTbResultsAt8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	/**
	 * patients registered 12 to 15 months earlier
	 * extra pulmonary TB
	 * results at 8 months
	 * outcomes all
	 * @return Indicator
	 */
	public CohortIndicator transferOutOutOfControlDiedCompletedTreatmentExtraPulmonaryTbResultsAt8Months() {
		return cohortIndicator("Total Enrolled 8 months results Extra-Pulmonary TB - Outcomes all",
				map(tbCohorts.transferOutOutOfControlDiedCompletedTreatmentExtraPulmonaryTbResultsAt8Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedTB() {
		return cohortIndicator("Total Patient confirmed on TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedtb(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}

	public CohortIndicator confirmedTBwithpas() {
		return cohortIndicator("Total Patient confirmed on Relapse ( IR, RR) ",
				map(tbCohorts.totalpatientOnConfirmedtbwithpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedTBwithpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Relapse ( IR, RR) ",
				map(tbCohorts.totalpatientOnConfirmedtbwithpasAt36Mnth(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedTBwithoutpas() {
		return cohortIndicator("Total Patient confirmed on TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedtbwithoutpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedTBwithoutpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on TB Treatment  At 36 Month",
				map(tbCohorts.totalpatientOnConfirmedtbwithoutpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedTBwitheitherpas() {
		return cohortIndicator("Total Patient confirmed on TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedtbwitheitherpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	
	public CohortIndicator confirmedTBwitheitherpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on TB Treatment At 36 mnth",
				map(tbCohorts.totalpatientOnConfirmedtbwitheitherpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatITB() {
		return cohortIndicator("Total Patient confirmed on Cat I TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedCatItb(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatITBwithpas() {
		return cohortIndicator("Total Patient confirmed on Non-Converter (IR , RR) Treatment ",
				map(tbCohorts.totalpatientOnConfirmedCatItbwithpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatITBwithpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Non-Converter (IR , RR) Treatment At 36 Months",
				map(tbCohorts.totalpatientOnConfirmedCatItbwithpasAt36Mnth(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatITBwithoutpas() {
		return cohortIndicator("Total Patient confirmed on Cat I TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedCatItbwithoutpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatITBwithoutpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Cat I TB Treatment At 36 Month",
				map(tbCohorts.totalpatientOnConfirmedCatItbwithoutpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatITBwitheitherpas() {
		return cohortIndicator("Total Patient confirmed on Cat I TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedCatItbwitheitherpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatITBwitheitherpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Cat I TB Treatment At 36 mnth ",
				map(tbCohorts.totalpatientOnConfirmedCatItbwitheitherpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatIITB() {
		return cohortIndicator("Total Patient confirmed on Cat II TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedCatIItb(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	
	public CohortIndicator confirmedCatIITBwithpas() {
		return cohortIndicator("Total Patient confirmed on Cat II TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedCatIItbwithpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatIITBwithpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Cat II TB Treatment At 36 Months ",
				map(tbCohorts.totalpatientOnConfirmedCatIItbwithpasAt36Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatIITBwithoutpas() {
		return cohortIndicator("Total Patient confirmed on Cat II TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedCatIItbwithoutpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatIITBwithoutpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Cat II TB Treatment without pas at 36 mnth",
				map(tbCohorts.totalpatientOnConfirmedCatIItbwithoutpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatIITBwitheitherpas() {
		return cohortIndicator("Total Patient confirmed on Cat II TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedCatIItbwitheitherpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedCatIITBwitheitherpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Cat II TB Treatment At 36 mnth",
				map(tbCohorts.totalpatientOnConfirmedCatIItbwitheitherpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedDefaultTB() {
		return cohortIndicator("Total Patient confirmed on Default TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedDefaulttb(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedDefaultwithpasTB() {
		return cohortIndicator("Total Patient confirmed on Treatment after lost to follow up (IR , RR)  ",
				map(tbCohorts.totalpatientOnConfirmedDefaulttbwithpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedDefaultwithpasTBAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Treatment after lost to follow up (IR , RR) At 36 Month ",
				map(tbCohorts.totalpatientOnConfirmedDefaulttbwithpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedDefaultwithoutpasTB() {
		return cohortIndicator("Total Patient confirmed on Default TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedDefaulttbwithoutpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	
	public CohortIndicator confirmedDefaultwithoutpasTBAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Default TB Treatment At 36 Month ",
				map(tbCohorts.totalpatientOnConfirmedDefaulttbwithoutpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedDefaultTBwitheitherpas() {
		return cohortIndicator("Total Patient confirmed on Default TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedDefaulttbwitheitherpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedDefaultTBwitheitherpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Default TB Treatment At 36 mnth ",
				map(tbCohorts.totalpatientOnConfirmedDefaulttbwitheitherpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedstandardMDRTB() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedStandardtb(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedstandardMDRTBwithpas() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedStandardtbwithpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedstandardMDRTBwithpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment At 36 Month ",
				map(tbCohorts.totalpatientOnConfirmedStandardtbwithpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedstandardMDRTBwithoutpas() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedStandardtbwithoutpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedstandardMDRTBwithoutpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment At 36 mnth",
				map(tbCohorts.totalpatientOnConfirmedStandardtbwithoutpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedstandardMDRTBwitheitherpas() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment ",
				map(tbCohorts.totalpatientOnConfirmedStandardtbwitheitherpas(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedstandardMDRTBwitheitherpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment At 36 mnth  ",
				map(tbCohorts.totalpatientOnConfirmedStandardtbwitheitherpasAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator Tbsmearculturewithpas() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (+)ve  culture (+)ve with pas",
				map(tbCohorts.totalpatientOutcomewithpasmearpositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator TbsmearculturewithpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (+)ve  culture (+)ve with pas At 36 Months",
				map(tbCohorts.totalpatientOutcomewithpasmearpositiveAt36Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator Tbsmearculturewithoutpas() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (+)ve  culture (+)ve with pas",
				map(tbCohorts.totalpatientOutcomewithoutpasmearpositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
    
	public CohortIndicator TbsmearculturewithoutpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (+)ve  culture (+)ve without pas At 36 mnth",
				map(tbCohorts.totalpatientOutcomewithoutpasmearpositiveAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator Tbsmearculturewitheitherpas() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (+)ve  culture (+)ve with pas",
				map(tbCohorts.totalpatientOutcomewitheitherpasmearpositive(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	
	public CohortIndicator TbsmearculturewitheitherpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (+)ve  culture (+)ve with either  pas At 36 mnth",
				map(tbCohorts.totalpatientOutcomewitheitherpasmearpositiveAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator Tbsmearnegativeculturewithpas() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (-)ve culture(+) ve with pas",
				map(tbCohorts.totalpatientOutcomewithpaswithsmearnegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator TbsmearnegativeculturewithpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (-)ve culture(+) ve with pas At 36 mnths",
				map(tbCohorts.totalpatientOutcomewithpaswithsmearnegativeAt36Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	
	public CohortIndicator Tbsmearnegativeculturewithoutpas() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (-)ve culture(+) ve without pas",
				map(tbCohorts.totalpatientOutcomewithoutpaswithsmearnegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator TbsmearnegativeculturewithoutpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (-)ve culture(+) ve without pas At 36 mnth ",
				map(tbCohorts.totalpatientOutcomewithoutpaswithsmearnegativeAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator Tbsmearnegativeculturewitheitherpas() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (-)ve culture(+) ve with pas",
				map(tbCohorts.totalpatientOutcomewithpaswitheithersmearnegative(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator TbsmearnegativeculturewitheitherpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Standard TB Treatment smear (-)ve culture(+) ve with pas",
				map(tbCohorts.totalpatientOutcomewithpaswitheithersmearnegativeAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmednewCategoryMDRTB() {
		return cohortIndicator("Total Patient confirmed on New category ",
				map(tbCohorts.totalpatientOutcomewithNewCategory(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmednewCategoryMDRTBwithpas() {
		return cohortIndicator("Total Patient confirmed on New (New, MDR-TB contact, PLHIV) with pas",
				map(tbCohorts.totalpatientOutcomewithpaswithNewCategory(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmednewCategoryMDRTBwithpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on New (New, MDR-TB contact, PLHIV) with pas At 36 Month",
				map(tbCohorts.totalpatientOutcomewithpaswithNewCategoryAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmednewCategoryMDRTBwithoutpas() {
		return cohortIndicator("Total Patient confirmed on New category with pas",
				map(tbCohorts.totalpatientOutcomewithoutpaswithoutNewCategory(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmednewCategoryMDRTBwithoutpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on New category with pas At 36 mnth",
				map(tbCohorts.totalpatientOutcomewithoutpaswithoutNewCategoryAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmednewCategoryMDRTBwitheitherpas() {
		return cohortIndicator("Total Patient confirmed on New category with pas",
				map(tbCohorts.totalpatientOutcomewithoutpaswitheitherNewCategory(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	
	public CohortIndicator confirmednewCategoryMDRTBwitheitherpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on New category with either pas At 36 mnth",
				map(tbCohorts.totalpatientOutcomewithoutpaswitheitherNewCategoryAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedOtherCategoryMDRTB() {
		return cohortIndicator("Total Patient confirmed on Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) with pas",
				map(tbCohorts.totalpatientOutcomewithOtherCategory(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedotherCategoryMDRTBwithpas() {
		return cohortIndicator("Total Patient confirmed on Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) with pas",
				map(tbCohorts.totalpatientOutcomewithpaswithOtherCategory(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedotherCategoryMDRTBwithpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) with pas At 36 Month",
				map(tbCohorts.totalpatientOutcomewithpaswithOtherCategoryAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedotherCategoryMDRTBwithoutpas() {
		return cohortIndicator("Total Patient confirmed on Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) without pas",
				map(tbCohorts.totalpatientOutcomewithoutpaswithoutOtherCategory(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedotherCategoryMDRTBwithoutpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) without pas At 36 mnth",
				map(tbCohorts.totalpatientOutcomewithoutpaswithoutOtherCategoryAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedotherCategoryMDRTBwitheitherpas() {
		return cohortIndicator("Total Patient confirmed on Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) with pas",
				map(tbCohorts.totalpatientOutcomewithoutpaswitheitherOtherCategory(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator confirmedotherCategoryMDRTBwitheitherpasAt36mnth() {
		return cohortIndicator("Total Patient confirmed on Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) with pas",
				map(tbCohorts.totalpatientOutcomewithoutpaswitheitherOtherCategoryAt36Month(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public  CohortIndicator registeredTB() {
		return cohortIndicator("Total Enrolled ",
				map(tbCohorts.totalpatientRegisteredInTb(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator startedTB() {
		return cohortIndicator("Total Patient started on TB Treatment ",
				map(tbCohorts.totalpatientOnMedicationInTb(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearnegativeculturenegative() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearnegativeculturenegativeAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearnegativeculturenegative6mnth() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearnegativeculturenegativeAt6Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearnegativeculturepositive6mnth() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearnegativeculturepositiveAt6Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearnegativeculturepositive() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearnegativeculturepositiveAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	
	
	public CohortIndicator totalenrolledtbpatientwithsmearpositiveculturenegative() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearpositiveculturenegativeAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearpositiveculturenegative6mnth() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearpositiveculturenegativeAt6Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearpositivecultureunknown() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearpositivecultureunknownAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearpositivecultureunknown6mnth() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearpositivecultureunknownAt6Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearunknownculturenegative() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearunknownculturenegativeAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearunknownculturenegative6mnth() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearunknownculturenegativeAt6Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearunknownculturepositive() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearunknownculturepositiveAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearunknownculturepositive6mnth() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearunknownculturepositiveAt6Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
    
	public CohortIndicator totalenrolledtbpatientwithsmearunknowncultureunknown() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearunknowncultureunknownAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	  
		public CohortIndicator totalenrolledtbpatientwithsmearunknowncultureunknown6mnth() {
			return cohortIndicator("Total enrolled patients",
					map(tbCohorts.totalEnrolledResultswithsmearunknowncultureunknownAt6Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
			);
		}
	public CohortIndicator totalenrolledtbpatientwithsmearnegativecultureunknown6mnth() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearnegativecultureunknownAt6Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearnegativecultureunknown() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearnegativecultureunknownAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	
	public CohortIndicator totalenrolledtbpatientwithsmearpositiveculturepositive() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearpositiveculturepositiveAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithsmearpositiveculturepositive6mnth() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultswithsmearpositiveculturepositiveAt6Months(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithdiedoutcome() {
		return cohortIndicator("Total patients  with died out come",
				map(tbCohorts.treatmentOutcome_Died(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithdiedoutcome6mnth() {
		return cohortIndicator("Total patients  with died out come",
				map(tbCohorts.treatmentOutcome_DiedAt6Mnth(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithlosstofollowupoutcome() {
		return cohortIndicator("Total patients  with loss to follow up to come",
				map(tbCohorts.treatmentOutcome_Defaulted(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
    
	public CohortIndicator totalenrolledtbpatientwithlosstofollowupoutcome6mnth() {
		return cohortIndicator("Total patients  with loss to follow up to come at 6 mnth",
				map(tbCohorts.treatmentOutcome_DefaultedAt6mnth(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatientwithnotevaluatedoutcome() {
		return cohortIndicator("Total patients  with transferred outcome",
				map(tbCohorts.treatmentOutcome_Transferedout(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
   
	public CohortIndicator totalenrolledtbpatientwithnotevaluatedoutcome6mnth() {
		return cohortIndicator("Total patients  with transferred outcome At 6 mnth",
				map(tbCohorts.treatmentOutcome_TransferedoutAt6mnth(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalenrolledtbpatient() {
		return cohortIndicator("Total enrolled patients",
				map(tbCohorts.totalEnrolledResultsAtMonths(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
  
	public CohortIndicator totalenrolledtbpatientSixMonths() {
		return cohortIndicator("Total enrolled patients in Six months",
				map(tbCohorts.totalEnrolledResultsAtMonths(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator totalNumberPatients() {
		return cohortIndicator("Total enrolled patients ",
				map(tbCohorts.totalEnrolledResults(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	
	public CohortIndicator patientwithTransferIn() {
		return cohortIndicator("Total enrolled patients with patient Transfer In",
				map(tbCohorts.totalEnrolledResultswithTransferIn(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator patientwithTransferInbefor1215() {
		return cohortIndicator("Total enrolled patients with patient Transfer In befor 12 to 15 mnths",
				map(tbCohorts.totalEnrolledResultswithTransferIn(15,12), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
	public CohortIndicator patientwithTransferIn6mnth() {
		return cohortIndicator("Total enrolled patients with patient Transfer In before 6 mnth",
				map(tbCohorts.totalEnrolledResultswithTransferInAtMonths(), "onOrAfter=${startDate},onOrBefore=${endDate}")
		);
	}
}