package org.openmrs.module.kenyaemr.reporting.builder.common;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.HivIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.tb.TbIndicatorLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
@Builds({"kenyaemr.tb.report.patient.mdrtb.detection"})
public class PatientHavingMDRTBcaseDetectinBuilder extends AbstractReportBuilder{
    protected static final Log log = LogFactory.getLog(HIVPositiveTBReceivedARTBuilder.class);


     
    @Autowired
	private CommonDimensionLibrary commonDimensions;
    
	@Autowired
	private TbIndicatorLibrary tbIndicators;

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
				ReportUtils.map(createTbDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbDataSetonMedication(), "startDate=${startDate},endDate=${endDate}"),
		        ReportUtils.map( createTbDataSetConfirmedMDRTB(),"startDate=${startDate},endDate=${endDate}")  ,  
				   ReportUtils.map( createTbDataSetConfirmedCatIMDRTB() ,"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map( createTbDataSetConfirmedCatIIMDRTB() ,"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map( createTbDataSetConfirmedDefaultMDRTB(),"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map( createTbDataSetMDRTB(),"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map( createTbDataSetNewpatient(),"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map( createTbDataSetTotalTbpatient(),"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map( createTbDataSetOtherpatient(),"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map( createTbDataSetwithTransferInpatient(),"startDate=${startDate},endDate=${endDate}")
				);                      
	}

	/**
	 * Creates the ART data set
	 * @return the data set
	 */
	private DataSetDefinition createTbDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("P");
		dsd.setDescription("Registered in MDR-TB diagnostic group");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		
		columns.add(new ColumnParameters("T", "total", ""));
         
		String indParams = "startDate=${startDate},endDate=${endDate}";
             
		EmrReportingUtils.addRow(dsd, "P1", "No. of detected cases Registered in MDR-TB diagnostic group ", ReportUtils.map(tbIndicators.registeredTB(), indParams), columns);
		return dsd;
	}

	
	private DataSetDefinition createTbDataSetonMedication() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("Q");
		dsd.setDescription("Started on MDR-TB treatment during the quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		
		columns.add(new ColumnParameters("T", "total", ""));
		
		

		String indParams = "startDate=${startDate},endDate=${endDate}";
             
		EmrReportingUtils.addRow(dsd, "Q1", "No. of detected cases Started on MDR-TB treatment during the quarter", ReportUtils.map(tbIndicators.startedTB(), indParams), columns);
		return dsd;
	}


	private DataSetDefinition createTbDataSetConfirmedMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("R");
		dsd.setDescription("Confirmed MDR-TB registered with Relapse Category(IR or RR) during the quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		
		
		
		ColumnParameters female=new ColumnParameters("FP", "female", "gender=F|patienttype=enrollmentTbRegnumber");
		ColumnParameters male=new ColumnParameters("MP", "male", "gender=M|patienttype=enrollmentTbRegnumber");
		ColumnParameters total=new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber");


		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(female,male,total);
		List<String> indSuffixes = Arrays.asList("FM","MA", "TT");    
                
		  
		EmrReportingUtils.addRow(dsd, "R1", "No. of detected cases with Relapse category", ReportUtils.map(tbIndicators.confirmedTB(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	
	private DataSetDefinition createTbDataSetConfirmedCatIMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("S");
		dsd.setDescription("Confirmed MDR-TB  registered with Non-converter (IR or RR) during the quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		ColumnParameters female=new ColumnParameters("FP", "female", "gender=F|patienttype=enrollmentTbRegnumber");
		ColumnParameters male=new ColumnParameters("MP", "male", "gender=M|patienttype=enrollmentTbRegnumber");
		ColumnParameters total=new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber");


		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(female,male,total);
		List<String> indSuffixes = Arrays.asList("FM","MA", "TT");    
             
		EmrReportingUtils.addRow(dsd, "S1", "No. of detected cases with Non-converter (IR or RR) category", ReportUtils.map(tbIndicators.confirmedCatITB(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	private DataSetDefinition createTbDataSetConfirmedCatIIMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("T");
		dsd.setDescription("Confirmed MDR-TB  registered with Treatment after failure (IR or RR) during the quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		ColumnParameters female=new ColumnParameters("FP", "female", "gender=F|patienttype=enrollmentTbRegnumber");
		ColumnParameters male=new ColumnParameters("MP", "male", "gender=M|patienttype=enrollmentTbRegnumber");
		ColumnParameters total=new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber");


		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(female,male,total);
		List<String> indSuffixes = Arrays.asList("FM","MA", "TT");    
             
		EmrReportingUtils.addRow(dsd, "T1", "No. of detected cases with Treatment after failure (IR or RR) ctegory  ", ReportUtils.map(tbIndicators.confirmedCatIITB(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	private DataSetDefinition createTbDataSetConfirmedDefaultMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("U");
		dsd.setDescription("Confirmed MDR-TB registered with loss to follow up (IR or RR) during the quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		ColumnParameters female=new ColumnParameters("FP", "female", "gender=F|patienttype=enrollmentTbRegnumber");
		ColumnParameters male=new ColumnParameters("MP", "male", "gender=M|patienttype=enrollmentTbRegnumber");
		ColumnParameters total=new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber");


		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(female,male,total);
		List<String> indSuffixes = Arrays.asList("FM","MA", "TT");    
             
		EmrReportingUtils.addRow(dsd, "U1", "No. of detected cases with Loss to follow up (IR or RR) category", ReportUtils.map(tbIndicators.confirmedDefaultTB(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	private DataSetDefinition createTbDataSetMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("V");
		dsd.setDescription("Confirmed MDR-TB registered with Treatment after the Standard MDR-TB  category" );
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		ColumnParameters female=new ColumnParameters("FP", "female", "gender=F|patienttype=enrollmentTbRegnumber");
		ColumnParameters male=new ColumnParameters("MP", "male", "gender=M|patienttype=enrollmentTbRegnumber");
		ColumnParameters total=new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber");


		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(female,male,total);
		List<String> indSuffixes = Arrays.asList("FM","MA", "TT");    
             
		EmrReportingUtils.addRow(dsd, "V1", "No. of detected cases with Treatment after the Standard MDR-TB  Category", ReportUtils.map(tbIndicators.confirmedstandardMDRTB(), indParams),allColumns,indSuffixes);
		return dsd;
	}
	private DataSetDefinition createTbDataSetNewpatient() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("W");
		dsd.setDescription("New patient in MDR-TB registered " );
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		ColumnParameters female=new ColumnParameters("FP", "female", "gender=F|patienttype=enrollmentTbRegnumber");
		ColumnParameters male=new ColumnParameters("MP", "male", "gender=M|patienttype=enrollmentTbRegnumber");
		ColumnParameters total=new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber");


		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(female,male,total);
		List<String> indSuffixes = Arrays.asList("FM","MA", "TT");    
             
		EmrReportingUtils.addRow(dsd, "W1", "No. of detected cases with New MDR-TB case (new,MDR-TB contact, PLHIV)", ReportUtils.map(tbIndicators.confirmednewCategoryMDRTB(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	private DataSetDefinition createTbDataSetTotalTbpatient() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("X");
		dsd.setDescription("Total Number Of patients in Tb case (Registered and on medication)" );
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		
		ColumnParameters child=new ColumnParameters("CP", "child", "age=<15|patienttype=enrollmentTbRegnumber");
		ColumnParameters adult=new ColumnParameters("AP", "adult", "age=15+|patienttype=enrollmentTbRegnumber");
		ColumnParameters total=new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber");


		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(child,adult,total);
		List<String> indSuffixes = Arrays.asList("CP","AP", "TT");    
		
             
		EmrReportingUtils.addRow(dsd, "X1", "No. of detected cases ", ReportUtils.map(tbIndicators.totalNumberPatients(), indParams),allColumns,indSuffixes);
		return dsd;
	}
	private DataSetDefinition createTbDataSetOtherpatient() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("Y");
		dsd.setDescription(" Patient in MDR-TB registered with other category" );
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		ColumnParameters female=new ColumnParameters("FP", "female", "gender=F|patienttype=enrollmentTbRegnumber");
		ColumnParameters male=new ColumnParameters("MP", "male", "gender=M|patienttype=enrollmentTbRegnumber");
		ColumnParameters total=new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber");


		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(female,male,total);
		List<String> indSuffixes = Arrays.asList("FM","MA", "TT");    
             
		EmrReportingUtils.addRow(dsd, "Y1", "No. of detected cases with Other(Previously treated EP OR Unknown outcome of previously treated Pul:TB)", ReportUtils.map(tbIndicators.confirmedOtherCategoryMDRTB(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	
	private DataSetDefinition createTbDataSetwithTransferInpatient() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("Z");
		dsd.setDescription(" Patient in MDR-TB registered with transfer In entry point" );
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "Z1", "No. of detected cases with entry point TransferIn ", ReportUtils.map(tbIndicators.patientwithTransferIn(), indParams),columns);
		return dsd;
	}
}
