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
@Builds({"kenyaemr.tb.report.patient.mdrtb.pas.36"})
public class MDRTBwithPAS36MnthReportBuilder extends AbstractReportBuilder{
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
				ReportUtils.map(createTbNewCategorywithpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIIFwithpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbRelapseCategoryIIwithpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIITADwithpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIFwithpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbMDRTBCASESwithpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbOtherCategorywithpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}")
			);
	}

	/**
	 * Create the data set
	 * @return data set
	 */

private DataSetDefinition createTbNewCategorywithpaspatientwithTBEnrollNumberAt36Month() {
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

	EmrReportingUtils.addRow(dsd, "LA1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "LB1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "LC1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "LD1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "LE1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "LF1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "LG1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbCategoryIIFwithpaspatientwithTBEnrollNumberAt36Month() {
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

	EmrReportingUtils.addRow(dsd, "MA1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "MB1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "MC1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "MD1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "ME1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "MF1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "MG1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbCategoryIITADwithpaspatientwithTBEnrollNumberAt36Month() {
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

	EmrReportingUtils.addRow(dsd, "NA1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "NB1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "NC1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "ND1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "NE1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "NF1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "NG1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbRelapseCategoryIIwithpaspatientwithTBEnrollNumberAt36Month() {
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

	EmrReportingUtils.addRow(dsd, "OA1", "No. of detected cases with Relapse ( IR, RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "OB1", "No. of detected cases with Relapse ( IR, RR)  with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "OC1", "No. of detected cases with Relapse ( IR, RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "OD1", "No. of detected cases with Relapse ( IR, RR)  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "OE1", "No. of detected cases with Relapse ( IR, RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "OF1", "No. of detected cases with Relapse ( IR, RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "OG1", "No. of detected cases with Relapse ( IR, RR)  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbCategoryIFwithpaspatientwithTBEnrollNumberAt36Month() {
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

	EmrReportingUtils.addRow(dsd, "PA1", "No. of detected cases with Non-Converter (IR , RR)  with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "PB1", "No. of detected cases with Non-Converter (IR , RR) with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "PC1", "No. of detected cases with Non-Converter (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "PD1", "No. of detected cases with Non-Converter (IR , RR) with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "PE1", "No. of detected cases with Non-Converter (IR , RR) with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "PF1", "No. of detected cases with Non-Converter (IR , RR) with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "PG1", "No. of detected cases with Non-Converter (IR , RR) with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}
private DataSetDefinition createTbMDRTBCASESwithpaspatientwithTBEnrollNumberAt36Month() {
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

	EmrReportingUtils.addRow(dsd, "QA1", "No. of detected cases with MDR tb cases  with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "QB1", "No. of detected cases with MDR tb cases with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "QC1", "No. of detected cases with MDR tb cases  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "QD1", "No. of detected cases with MDR tb cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "QE1", "No. of detected cases with MDR tb cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "QF1", "No. of detected cases with MDR tb cases with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "QG1", "No. of detected cases with MDR tb cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}
private DataSetDefinition createTbOtherCategorywithpaspatientwithTBEnrollNumberAt36Month() {
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

	EmrReportingUtils.addRow(dsd, "RA1", "No. of detected cases with Other Category cases  with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "RB1", "No. of detected cases with Other Category cases with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "RC1", "No. of detected cases with Other Category cases  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "RD1", "No. of detected cases with Other Category cases  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "RE1", "No. of detected cases with Other Category cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "RF1", "No. of detected cases with Other Category cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "RG1", "No. of detected cases with Other Category cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}
}
