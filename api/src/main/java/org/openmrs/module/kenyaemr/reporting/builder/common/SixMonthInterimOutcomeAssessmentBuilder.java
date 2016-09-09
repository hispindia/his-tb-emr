package org.openmrs.module.kenyaemr.reporting.builder.common;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
@Builds({"kenyaemr.tb.report.patient.registered.6"})
public class SixMonthInterimOutcomeAssessmentBuilder extends AbstractReportBuilder {
	
	
	

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
				ReportUtils.map(createTotalTbPatients(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearnegativeculturenegative(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearnegativeculturepositive(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearnegativecultureunknown(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearpositiveculturenegative(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearpositiveculturepositive(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearpositivecultureunknown(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearunknownculturenegative(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearunknownculturepositive(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearunknowncultureunknown(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTboutcomeDied(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTboutcomeLossToFollowUp(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTboutcomeNotEvaluated(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbDataSetwithTransferInpatient(), "startDate=${startDate},endDate=${endDate}")
			);
	}

	/**
	 * Create the data set
	 * @return data set
	 */
	
	
	private DataSetDefinition createTotalTbPatients() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("A");
		dsd.setDescription("Total Number of patient started with medication 6 month ago");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "A1", "No. of detected cases started with medication 6 month ago ", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientSixMonths(), indParams), columns);
		
	   return  dsd;
	}
	private DataSetDefinition createTbsmearnegativeculturenegative() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("B");
		dsd.setDescription("Number of patient started with medication 6 month ago with smear negative culture negative ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "B1", "No. of detected cases started with medication 6 month ago with smear negative culture negative", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithsmearnegativeculturenegative6mnth(), indParams), columns);
		
	   return  dsd;
	}

	/**
	 * Create the data set
	 * @return data set
	 */
	private DataSetDefinition createTbsmearnegativeculturepositive() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("C");
		dsd.setDescription("Number of patient started with medication 6 month ago with smear negative culture positive ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "C1", "No. of detected cases started with medication 6 month ago with smear negative culture positive", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithsmearnegativeculturepositive6mnth(), indParams), columns);
		
	   return  dsd;
	}
    
	private DataSetDefinition createTbsmearnegativecultureunknown() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("D");
		dsd.setDescription("Number of patient started with medication 6 month ago with smear negative culture unknown");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "D1", "No. of detected cases started with medication 6 month ago with smear negative culture unknown ", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithsmearnegativecultureunknown6mnth(), indParams), columns);
		
	   return  dsd;
	}
   
	private DataSetDefinition createTbsmearpositiveculturenegative() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("E");
		dsd.setDescription("Number of patient started with medication 6 month ago with smear positive culture negative");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "E1", "No. of detected cases started with medication 6 month ago with smear positive culture negative", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithsmearpositiveculturenegative6mnth(), indParams), columns);
		
	   return  dsd;
	}

	/**
	 * Create the data set
	 * @return data set
	 */
	private DataSetDefinition createTbsmearpositiveculturepositive() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("F");
		dsd.setDescription("Number of patient started with medication 6 month ago with smear positive culture positive");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "F1", "No. of detected cases started with medication 6 month ago with smear positive culture positive", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithsmearpositiveculturepositive6mnth(), indParams), columns);
		
	   return  dsd;
	}
    
	private DataSetDefinition createTbsmearpositivecultureunknown() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("G");
		dsd.setDescription("Number of patient started with medication 6 month ago with smear positive culture unknown");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "G1", "No. of detected cases started with medication 6 month ago with smear positive culture unknown ", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithsmearpositivecultureunknown6mnth(), indParams), columns);
		
	   return  dsd;
	}
	private DataSetDefinition createTbsmearunknownculturenegative() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("H");
		dsd.setDescription("Number of patient started with medication 6 month ago with smear unknown culture negative");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "H1", "No. of detected cases started with medication 6 month ago with smear unknown culture negative ", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithsmearunknownculturenegative6mnth(), indParams), columns);
		
	   return  dsd;
	}

	/**
	 * Create the data set
	 * @return data set
	 */
	private DataSetDefinition createTbsmearunknownculturepositive() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("I");
		dsd.setDescription("Number of patient started with medication 6 month ago with smear unknown culture positive");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "I1", "No. of detected cases started with medication 6 month ago with smear unknown culture positive ", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithsmearunknownculturepositive6mnth(), indParams), columns);
		
	   return  dsd;
	}
    
	private DataSetDefinition createTbsmearunknowncultureunknown() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("J");
		dsd.setDescription("Number of patient started with medication 6 month ago with smear unknown culture unknown");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		
		columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "J1", "No. of detected cases started with medication 6 month ago with smear unknown culture unknown ", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithsmearunknowncultureunknown6mnth(), indParams), columns);
		
	   return  dsd;
	}
    
		private DataSetDefinition createTboutcomeDied() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("K");
			dsd.setDescription("Number of patient started with medication 6 month ago with outcome died");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
			
			List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
			
			columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
			String indParams = "startDate=${startDate},endDate=${endDate}";

			EmrReportingUtils.addRow(dsd, "K1", "No. of detected cases started with medication 6 month ago with outcome died", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithdiedoutcome6mnth(), indParams), columns);
			
		   return  dsd;
		}
		private DataSetDefinition createTboutcomeLossToFollowUp() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("L");
			dsd.setDescription("Number of patient started with medication 6 month ago with outcome loss to follow up");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
			
			List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
			
			columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
			String indParams = "startDate=${startDate},endDate=${endDate}";

			EmrReportingUtils.addRow(dsd, "L1", "No. of detected cases started with medication 6 month ago with outcome loss to follow up", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithlosstofollowupoutcome6mnth(), indParams), columns);
			
		   return  dsd;
		}
		private DataSetDefinition createTboutcomeNotEvaluated() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("M");
			dsd.setDescription("Number of patient started with medication 6 month ago with outcome not Evaluated");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
			
			List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
			
			columns.add(new ColumnParameters("T", "total", "patienttype=enrollmentTbRegnumber"));
			String indParams = "startDate=${startDate},endDate=${endDate}";

			EmrReportingUtils.addRow(dsd, "M1", "No. of detected cases started with medication 6 month ago with outcome not Evaluated ", ReportUtils.map(tbIndicatorLibrary.totalenrolledtbpatientwithnotevaluatedoutcome6mnth(), indParams), columns);
			
		   return  dsd;
		}
	
		private DataSetDefinition createTbDataSetwithTransferInpatient() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("N");
			dsd.setDescription(" Patient in MDR-TB registered with transfer In entry point" );
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
			
			columns.add(new ColumnParameters("T", "total", ""));
			String indParams = "startDate=${startDate},endDate=${endDate}";

			EmrReportingUtils.addRow(dsd, "N1", "No. of detected cases with entry point TransferIn ", ReportUtils.map(tbIndicatorLibrary.patientwithTransferIn6mnth(), indParams), columns);
			return dsd;
		}
}
