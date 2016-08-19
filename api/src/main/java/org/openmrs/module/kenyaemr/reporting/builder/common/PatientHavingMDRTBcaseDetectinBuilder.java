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
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		return Arrays.asList(
				ReportUtils.map(createTbDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbDataSetonMedication(), "startDate=${startDate},endDate=${endDate}"),
		        ReportUtils.map( createTbDataSetConfirmedMDRTB(),"startDate=${startDate},endDate=${endDate}")  ,  
				   ReportUtils.map( createTbDataSetConfirmedCatIMDRTB() ,"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map(   createTbDataSetConfirmedCatIIMDRTB() ,"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map(     createTbDataSetConfirmedDefaultMDRTB(),"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map(   createTbDataSetMDRTB(),"startDate=${startDate},endDate=${endDate}"),
				   ReportUtils.map(   createTbDataSetNewpatient(),"startDate=${startDate},endDate=${endDate}")
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
             
		EmrReportingUtils.addRow(dsd, "P1", "No. of detected cases ", ReportUtils.map(tbIndicators.registeredTB(), indParams), columns);
		return dsd;
	}

	
	private DataSetDefinition createTbDataSetonMedication() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("Q");
		dsd.setDescription("Started on MDR-TB treatment during quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", ""));

		String indParams = "startDate=${startDate},endDate=${endDate}";
             
		EmrReportingUtils.addRow(dsd, "Q1", "No. of detected cases ", ReportUtils.map(tbIndicators.startedTB(), indParams), columns);
		return dsd;
	}


	private DataSetDefinition createTbDataSetConfirmedMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("R");
		dsd.setDescription("Confirmed MDR-TB registered during the quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", ""));

		String indParams = "startDate=${startDate},endDate=${endDate}";
             
		EmrReportingUtils.addRow(dsd, "R1", "No. of detected cases ", ReportUtils.map(tbIndicators.confirmedTB(), indParams), columns);
		return dsd;
	}
	
	private DataSetDefinition createTbDataSetConfirmedCatIMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("S");
		dsd.setDescription("Confirmed MDR-TB Cat I registered during the quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", ""));

		String indParams = "startDate=${startDate},endDate=${endDate}";
             
		EmrReportingUtils.addRow(dsd, "S1", "No. of detected cases ", ReportUtils.map(tbIndicators.confirmedCatITB(), indParams), columns);
		return dsd;
	}
	private DataSetDefinition createTbDataSetConfirmedCatIIMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("T");
		dsd.setDescription("Confirmed MDR-TB Cat II registered during the quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", ""));

		String indParams = "startDate=${startDate},endDate=${endDate}";
             
		EmrReportingUtils.addRow(dsd, "T1", "No. of detected cases ", ReportUtils.map(tbIndicators.confirmedCatIITB(), indParams), columns);
		return dsd;
	}
	private DataSetDefinition createTbDataSetConfirmedDefaultMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("U");
		dsd.setDescription("Confirmed MDR-TB Cat Default registered during the quarter");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", ""));

		String indParams = "startDate=${startDate},endDate=${endDate}";
             
		EmrReportingUtils.addRow(dsd, "U1", "No. of detected cases ", ReportUtils.map(tbIndicators.confirmedDefaultTB(), indParams), columns);
		return dsd;
	}
	private DataSetDefinition createTbDataSetMDRTB() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("V");
		dsd.setDescription("Confirmed MDR-TB registered during" );
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", ""));

		String indParams = "startDate=${startDate},endDate=${endDate}";
             
		EmrReportingUtils.addRow(dsd, "V1", "No. of detected cases ", ReportUtils.map(tbIndicators.confirmedstandardMDRTB(), indParams), columns);
		return dsd;
	}
	private DataSetDefinition createTbDataSetNewpatient() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("W");
		dsd.setDescription("New patient in MDR-TB registered during" );
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", ""));

		String indParams = "startDate=${startDate},endDate=${endDate}";
             
		EmrReportingUtils.addRow(dsd, "W1", "No. of detected cases ", ReportUtils.map(tbIndicators.confirmednewCategoryMDRTB(), indParams), columns);
		return dsd;
	}
}
