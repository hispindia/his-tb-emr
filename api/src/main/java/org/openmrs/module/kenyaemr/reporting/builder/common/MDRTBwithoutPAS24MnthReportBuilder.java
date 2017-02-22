

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
@Builds({"kenyaemr.tb.report.patient.mdrtb.without.pas.24"})
public class MDRTBwithoutPAS24MnthReportBuilder extends AbstractReportBuilder{
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
				ReportUtils.map(createTbNewCategorywithoutpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIIFwithoutpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbRelapseCategoryIIwithoutpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIITADwithoutpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIFwithoutpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbMDRTBCASESwithoutpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbOtherCategorywithoutpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}")
				);
	}

	/**
	 * Create the data set
	 * @return data set
	 */
	private DataSetDefinition createTbNewCategorywithoutpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("L");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS and  with New (New, MDR-TB contact, PLHIV) category");
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

		EmrReportingUtils.addRow(dsd, "LA1", "No. of detected cases with New category with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "LB1", "No. of detected cases with New category with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "LC1", "No. of detected cases with New category  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "LD1", "No. of detected cases with New category with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "LE1", "No. of detected cases with New category  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "LF1", "No. of detected cases with New category  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "LG1", "No. of detected cases with New category  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpas(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbCategoryIIFwithoutpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("M");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Treatment after failure of treatment (IR , RR)");
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

		EmrReportingUtils.addRow(dsd, "MA1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "MB1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "MC1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "MD1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "ME1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "MF1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "MG1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpas(), indParams), columnsG);
		return  dsd;
	}
   
	private DataSetDefinition createTbCategoryIITADwithoutpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("N");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS   and with Treatment after lost to follow up (IR , RR)");
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

		EmrReportingUtils.addRow(dsd, "NA1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTB(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "NB1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTB(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "NC1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTB(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "ND1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTB(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "NE1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTB(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "NF1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTB(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "NG1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTB(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition  createTbRelapseCategoryIIwithoutpaspatientwithTBEnrollNumber(){
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("O");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Relapse ( IR, RR)");
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

		EmrReportingUtils.addRow(dsd, "OA1", "No. of detected cases with Relapse ( IR, RR)  with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "OB1", "No. of detected cases with Relapse ( IR, RR)  with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "OC1", "No. of detected cases with Relapse ( IR, RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "OD1", "No. of detected cases with Relapse ( IR, RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "OE1", "No. of detected cases with Relapse ( IR, RR)  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "OF1", "No. of detected cases with Relapse ( IR, RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "OG1", "No. of detected cases with Relapse ( IR, RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpas(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbCategoryIFwithoutpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("P");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Non-Converter (IR , RR)");
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

		EmrReportingUtils.addRow(dsd, "PA1", "No. of detected cases with Non-Converter (IR , RR)  with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "PB1", "No. of detected cases with Non-Converter (IR , RR) with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "PC1", "No. of detected cases with Non-Converter (IR , RR)  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "PD1", "No. of detected cases with Non-Converter (IR , RR) with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "PE1", "No. of detected cases with Non-Converter (IR , RR) with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "PF1", "No. of detected cases with Non-Converter (IR , RR) with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "PG1", "No. of detected cases with Non-Converter (IR , RR) with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpas(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbMDRTBCASESwithoutpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("Q");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with MDR tb cases");
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

		EmrReportingUtils.addRow(dsd, "QA1", "No. of detected cases with MDR tb cases  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "QB1", "No. of detected cases with MDR tb cases with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "QC1", "No. of detected cases with MDR tb cases  with outcome", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "QD1", "No. of detected cases with MDR tb cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "QE1", "No. of detected cases with MDR tb cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "QF1", "No. of detected cases with MDR tb cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "QG1", "No. of detected cases with MDR tb cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpas(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbOtherCategorywithoutpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("R");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) category cases withXpertORconventionalDst");
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

		EmrReportingUtils.addRow(dsd, "RA1", "No. of detected cases with Other Category cases  with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "RB1", "No. of detected cases with Other Category cases with outcome  ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "RC1", "No. of detected cases with Other Category cases  with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "RD1", "No. of detected cases with Other Category with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "RE1", "No. of detected cases with Other Category cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "RF1", "No. of detected cases with Other Category cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "RG1", "No. of detected cases with Other Category cases with outcome ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpas(), indParams), columnsG);
		return  dsd;
	}
}

