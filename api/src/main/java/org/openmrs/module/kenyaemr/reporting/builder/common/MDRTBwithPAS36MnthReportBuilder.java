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
				ReportUtils.map(createTbsmearpositiveculturepositivewithpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearnegativeculturpositiveewithpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIIFwithpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbRelapseCategoryIIwithpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIITADwithpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIFwithpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbMDRTBCASESwithpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbNewCategorywithpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbOtherCategorywithpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearpositiveculturepositivewithpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearnegativeculturpositiveewithpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
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
	private DataSetDefinition createTbsmearpositiveculturepositivewithpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("A");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with smear positive and Solid OR Liquidculture positive at iniatiation withXpertORconventionalDst");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=conventionalDST")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=conventionalDST"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=conventionalDST"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "A1", "No. of detected cases with smear (+)ve and Culture (+) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "B1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "C1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "D1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "E1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "F1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "G1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbsmearnegativeculturpositiveewithpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("B");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with sputum negative and Solid or Liquid culture positive at iniatiation withXpertORconventionalDst");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure |patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=conventionalDST")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=conventionalDST"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=conventionalDST"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "BA1", "No. of detected cases with smear (-)ve and Culture (+) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "BB1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "BC1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "BD1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "BE1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "BF1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "BG1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
    
	
	private DataSetDefinition createTbCategoryIIFwithpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("C");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Treatment after failure of treatment (IR , RR) withXpertORconventionalDst ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=conventionalDST")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=conventionalDST"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=conventionalDST"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "CA1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "CB1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "CC1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "CD1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "CE1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "CF1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "CG1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
   
	private DataSetDefinition createTbCategoryIITADwithpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("D");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Treatment after lost to follow up (IR , RR) withXpertORconventionalDst");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=conventionalDST")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=conventionalDST"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=conventionalDST"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "DA1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "DB1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "DC1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "DD1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "DE1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "DF1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "DG1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbRelapseCategoryIIwithpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("E");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Relapse ( IR, RR) withXpertORconventionalDst");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=conventionalDST")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=conventionalDST"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=conventionalDST"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "EA1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "EB1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "EC1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "ED1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "EE1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "EF1", "No. of detected cases with Relapse ( IR, RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "EG1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbCategoryIFwithpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("F");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Non-Converter (IR , RR) withXpertORconventionalDst");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=conventionalDST")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=conventionalDST"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=conventionalDST"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "FA1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "FB1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "FC1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "FD1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "FE1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "FF1", "No. of detected cases with Non-Converter (IR , RR) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "FG1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbMDRTBCASESwithpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("G");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with MDR tb cases withXpertORconventionalDst");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=conventionalDST")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=conventionalDST"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=conventionalDST"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "GA1", "No. of detected cases with MDR tb cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "GB1", "No. of detected cases with MDR tb cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "GC1", "No. of detected cases with MDR tb cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "GD1", "No. of detected cases with MDR tb cases with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "GE1", "No. of detected cases with MDR tb cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "GF1", "No. of detected cases with MDR tb cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "GG1", "No. of detected cases with MDR tb cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbNewCategorywithpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("H");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with New (New, MDR-TB contact, PLHIV) category cases withXpertORconventionalDst");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=conventionalDST")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=conventionalDST"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=conventionalDST"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "HA1", "No. of detected cases with New Category cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "HB1", "No. of detected cases with New Category cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "HC1", "No. of detected cases with New Category cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "HD1", "No. of detected cases with New Category with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "HE1", "No. of detected cases with New Category cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "HF1", "No. of detected cases with New Category cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "HG1", "No. of detected cases with New Category cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbOtherCategorywithpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("I");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) category cases withXpertORconventionalDst");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=conventionalDST"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=conventionalDST")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=conventionalDST"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=conventionalDST"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=conventionalDST"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "IA1", "No. of detected cases with Other Category cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "IB1", "No. of detected cases with Other Category cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "IC1", "No. of detected cases with Other Category cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "ID1", "No. of detected cases with Other Category with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "IE1", "No. of detected cases with Other Category cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "IF1", "No. of detected cases with Other Category cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "IG1", "No. of detected cases with Other Category cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbsmearpositiveculturepositivewithpaspatientwithTBEnrollNumberAt36Month() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("J");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with sputum positive and Solid OR Liquid culture positive positive at iniatiation withTBEnrollNumber from Enrollment Form");
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
	columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "JA1", "No. of detected cases with smear (+)ve and Solid OR Liquid Culture (+) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "JB1", "No. of detected cases new with smear (+)ve and Solid OR Liquid Culture (+) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "JC1", "No. of detected cases new with smear (+)ve and Solid OR Liquid Culture (+) with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "JD1", "No. of detected cases new with smear (+)ve and Solid OR Liquid Culture (+) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "JE1", "No. of detected cases new with smear (+)ve and Solid OR Liquid Culture (+) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "JF1", "No. of detected cases new with smear (+)ve and Solid OR Liquid Culture (+) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "JG1", "No. of detected cases new with smear (+)ve and Solid OR Liquid Culture (+) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}
private DataSetDefinition createTbsmearnegativeculturpositiveewithpaspatientwithTBEnrollNumberAt36Month() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("K");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with sputum negative and Solid OR Liquid culture  positive at iniatiation withTBEnrollNumber from Enrollment Form");
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
	columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "KA1", "No. of detected cases with smear (-)ve and Solid OR Liquid Culture (+) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "KB1", "No. of detected cases new with smear (-)ve and Solid OR Liquid Culture (+) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "KC1", "No. of detected cases new with smear (-)ve and Solid OR Liquid Culture (+) with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "KD1", "No. of detected cases new with smear (-)ve and Solid OR Liquid Culture (+) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "KE1", "No. of detected cases new with smear (-)ve and Solid OR Liquid Culture (+) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "KF1", "No. of detected cases new with smear (-)ve and Solid OR Liquid Culture (+) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "KG1", "No. of detected cases new with smear (-)ve and Solid OR Liquid Culture (+) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}
private DataSetDefinition createTbNewCategorywithpaspatientwithTBEnrollNumberAt36Month() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("L");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and  with New (New, MDR-TB contact, PLHIV) category withTBEnrollNumber from Enrollment Form");
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
	columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "LA1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "LB1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "LC1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "LD1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "LE1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "LF1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "LG1", "No. of detected cases with New (New, MDR-TB contact, PLHIV) category  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbCategoryIIFwithpaspatientwithTBEnrollNumberAt36Month() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("M");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Treatment after failure of treatment (IR , RR) withTBEnrollNumber from Enrollment Form");
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
	columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "MA1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "MB1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "MC1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "MD1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "ME1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "MF1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "MG1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbCategoryIITADwithpaspatientwithTBEnrollNumberAt36Month() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("N");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Treatment after lost to follow up (IR , RR) withTBEnrollNumber from Enrollment Form");
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
	columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "NA1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "NB1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "NC1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "ND1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "NE1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "NF1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "NG1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithpasTBAt36mnth(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbRelapseCategoryIIwithpaspatientwithTBEnrollNumberAt36Month() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("O");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Relapse ( IR, RR) withTBEnrollNumber from Enrollment Form");
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
	columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "OA1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "OB1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "OC1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "OD1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "OE1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "OF1", "No. of detected cases with Relapse ( IR, RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "OG1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}

private DataSetDefinition createTbCategoryIFwithpaspatientwithTBEnrollNumberAt36Month() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("P");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Non-Converter (IR , RR)e withTBEnrollNumber from Enrollment Form");
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
	columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "PA1", "No. of detected cases with Non-Converter (IR , RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "PB1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "PC1", "No. of detected cases with Non-Converter (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "PD1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "PE1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "PF1", "No. of detected cases with Non-Converter (IR , RR) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "PG1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}
private DataSetDefinition createTbMDRTBCASESwithpaspatientwithTBEnrollNumberAt36Month() {
	CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	dsd.setName("Q");
	dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with MDR tb cases withTBEnrollNumber from Enrollment Form");
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
	columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "QA1", "No. of detected cases with MDR tb cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "QB1", "No. of detected cases with MDR tb cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "QC1", "No. of detected cases with MDR tb cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "QD1", "No. of detected cases with MDR tb cases with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "QE1", "No. of detected cases with MDR tb cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "QF1", "No. of detected cases with MDR tb cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "QG1", "No. of detected cases with MDR tb cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithpasAt36mnth(), indParams), columnsG);
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
	columnsF.add(new ColumnParameters("T5", "TransferedOut", "parameter=TransferedOut|patienttype=enrollmentTbRegnumber"));
    
	List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
	columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
	
	String indParams = "startDate=${startDate},endDate=${endDate}";

	EmrReportingUtils.addRow(dsd, "RA1", "No. of detected cases with Other Category cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsA);
	EmrReportingUtils.addRow(dsd, "RB1", "No. of detected cases with Other Category cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsB);
	EmrReportingUtils.addRow(dsd, "RC1", "No. of detected cases with Other Category cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsC);
	EmrReportingUtils.addRow(dsd, "RD1", "No. of detected cases with Other Category with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsD);
	EmrReportingUtils.addRow(dsd, "RE1", "No. of detected cases with Other Category cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsE);
	EmrReportingUtils.addRow(dsd, "RF1", "No. of detected cases with Other Category cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsF);
	EmrReportingUtils.addRow(dsd, "RG1", "No. of detected cases with Other Category cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithpasAt36mnth(), indParams), columnsG);
	return  dsd;
}
}
