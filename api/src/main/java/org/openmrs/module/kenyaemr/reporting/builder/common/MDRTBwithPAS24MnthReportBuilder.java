package org.openmrs.module.kenyaemr.reporting.builder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.tb.TbIndicatorLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Builds({"kenyaemr.tb.report.patient.mdrtb.pas.24"})
public class MDRTBwithPAS24MnthReportBuilder extends AbstractReportBuilder{
	@Autowired
	TbIndicatorLibrary tbIndicatorLibrary;
	@Autowired
	private CommonDimensionLibrary commonDimensions;
	/**
	 * @see org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder#getParameters(org.openmrs.module.kenyacore.report.ReportDescriptor)
	 */

	@Override
	protected List<Parameter> getParameters(ReportDescriptor descriptor) {
		return Arrays.asList(
				new Parameter("startDate", "Start Date", Date.class),
				new Parameter("endDate", "End Date", Date.class)
		);
	}

	/**
	 * @see org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder#buildDataSets(org.openmrs.module.kenyacore.report.ReportDescriptor, org.openmrs.module.reporting.report.definition.ReportDefinition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		return Arrays.asList(
				ReportUtils.map(createTbNewCategorywithpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIIFwithpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbRelapseCategoryIIwithpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIITADwithpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIFwithpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbMDRTBCASESwithpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbOtherCategorywithpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}")
			);
	}

	/**
	 * Create the data set
	 * @return data set
	 */
	
private DataSetDefinition createTbNewCategorywithpaspatientwithTBEnrollNumber() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("L");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and  with New (New, MDR-TB contact, PLHIV) category");
	dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
	dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
	dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));

	List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
	columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
	columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
	columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
	columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
    
	List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
	columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
	columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "LA1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpas(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "LB1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpas(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "LC1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpas(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "LD1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpas(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "LE1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpas(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "LF1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpas(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "LG1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpas(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbCategoryIIFwithpaspatientwithTBEnrollNumber() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("M");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Treatment after failure of treatment (IR , RR)");
	dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
	dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
	dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));

	List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
	columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
	columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
	columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
	columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
    
	List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
	columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
	columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "MA1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpas(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "MB1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpas(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "MC1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpas(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "MD1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpas(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "ME1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpas(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "MF1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpas(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "MG1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpas(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbCategoryIITADwithpaspatientwithTBEnrollNumber() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("N");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Treatment after lost to follow up (IR , RR)");
	dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
	dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
	dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));

	List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
	columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
	columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
	columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
	columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
    
	List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
	columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
	columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "NA1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTB(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "NB1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTB(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "NC1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTB(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "ND1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTB(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "NE1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTB(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "NF1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTB(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "NG1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTB(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbRelapseCategoryIIwithpaspatientwithTBEnrollNumber() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("O");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Relapse ( IR, RR)");
	dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
	dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
	dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));

	List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
	columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
	columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
	columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
	columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
    
	List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
	columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
	columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "OA1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpas(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "OB1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpas(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "OC1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpas(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "OD1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpas(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "OE1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpas(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "OF1", "No. of detected cases with Relapse ( IR, RR)  with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpas(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "OG1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpas(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbCategoryIFwithpaspatientwithTBEnrollNumber() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("P");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Non-Converter (IR , RR)e");
	dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
	dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
	dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));

	List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
	columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
	columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
	columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
	columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
    
	List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
	columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
	columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "PA1", "No. of detected cases with Non-Converter (IR , RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpas(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "PB1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpas(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "PC1", "No. of detected cases with Non-Converter (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpas(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "PD1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpas(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "PE1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpas(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "PF1", "No. of detected cases with Non-Converter (IR , RR) with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpas(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "PG1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpas(), indParams), columnsG);
	return  dsd;
}
private DataSetDefinition createTbMDRTBCASESwithpaspatientwithTBEnrollNumber() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("Q");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with MDR tb cases");
	dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
	dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
	dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));

	List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
	columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
	columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
	columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
	columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
    
	List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
	columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
	columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "QA1", "No. of detected cases with MDR tb cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpas(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "QB1", "No. of detected cases with MDR tb cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpas(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "QC1", "No. of detected cases with MDR tb cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpas(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "QD1", "No. of detected cases with MDR tb cases with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpas(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "QE1", "No. of detected cases with MDR tb cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpas(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "QF1", "No. of detected cases with MDR tb cases with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpas(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "QG1", "No. of detected cases with MDR tb cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpas(), indParams), columnsG);
	return  dsd;
}
private DataSetDefinition createTbOtherCategorywithpaspatientwithTBEnrollNumber() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("R");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) category cases withXpertORconventionalDst");
	dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
	dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
	dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));

	List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
	columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
	columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
	columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
	
    List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
	columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
    
	List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
	columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
	
	List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
	columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "RA1", "No. of detected cases with Other Category cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpas(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "RB1", "No. of detected cases with Other Category cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpas(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "RC1", "No. of detected cases with Other Category cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpas(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "RD1", "No. of detected cases with Other Category with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpas(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "RE1", "No. of detected cases with Other Category cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpas(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "RF1", "No. of detected cases with Other Category cases with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpas(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "RG1", "No. of detected cases with Other Category cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpas(), indParams), columnsG);
	return  dsd;
}
}
