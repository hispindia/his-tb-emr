

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
@Builds({"kenyaemr.tb.report.patient.mdrtb.without.pas.36"})
public class MDRTBwithoutPAS36MnthReportBuilder extends AbstractReportBuilder{
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
				ReportUtils.map(createTbsmearpositiveculturepositivewithoutpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearnegativeculturpositiveewithoutpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbNewCategorywithoutpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIIFwithoutpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbRelapseCategoryIIwithoutpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIITADwithoutpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIFwithoutpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbMDRTBCASESwithoutpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbOtherCategorywithoutpaspatientwithXpertORconventionalDstAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearpositiveculturepositivewithoutpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbsmearnegativeculturpositiveewithoutpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbNewCategorywithoutpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIIFwithoutpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbRelapseCategoryIIwithoutpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIITADwithoutpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIFwithoutpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbMDRTBCASESwithoutpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbOtherCategorywithoutpaspatientwithTBEnrollNumberAt36Month(), "startDate=${startDate},endDate=${endDate}")
				);
	}

	/**
	 * Create the data set
	 * @return data set
	 */
	private DataSetDefinition createTbsmearpositiveculturepositivewithoutpaspatientwithXpertORconventionalDstAt36Month() {
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

		EmrReportingUtils.addRow(dsd, "A1", "No. of detected cases with smear (+)ve and Culture (+) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "B1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "C1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "D1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "E1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "F1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "G1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbsmearnegativeculturpositiveewithoutpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("B");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with sputum negative and culture positive at iniatiation withXpertORconventionalDst");
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

		EmrReportingUtils.addRow(dsd, "BA1", "No. of detected cases with smear (-)ve and Culture (+) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "BB1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "BC1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "BD1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "BE1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "BF1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "BG1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
    
	
	private DataSetDefinition createTbCategoryIIFwithoutpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("C");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen with PAS  and with Treatment after failure of treatment (IR , RR) withXpertORconventionalDst");
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

		EmrReportingUtils.addRow(dsd, "CA1", "No. of detected cases with Treatment After Failure(IR or RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "CB1", "No. of detected cases with Treatment After Failure(IR or RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "CC1", "No. of detected cases with Treatment After Failure(IR or RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "CD1", "No. of detected cases with Treatment After Failure(IR or RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "CE1", "No. of detected cases with Treatment After Failure(IR or RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "CF1", "No. of detected cases with Treatment After Failure(IR or RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "CG1", "No. of detected cases with Treatment After Failure(IR or RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
   
	private DataSetDefinition createTbCategoryIITADwithoutpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("D");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Treatment after lost to follow up (IR , RR) withXpertORconventionalDst");
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

		EmrReportingUtils.addRow(dsd, "DA1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "DB1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "DC1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "DD1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "DE1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "DF1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "DG1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbRelapseCategoryIIwithoutpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("E");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Relapse ( IR, RR) withXpertORconventionalDst");
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

		EmrReportingUtils.addRow(dsd, "EA1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "EB1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "EC1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "ED1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "EE1", "No. of detected cases with Relapse ( IR, RR) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "EF1", "No. of detected cases with Relapse ( IR, RR) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "EG1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbCategoryIFwithoutpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("F");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Non-Converter (IR , RR) withXpertORconventionalDst");
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

		EmrReportingUtils.addRow(dsd, "FA1", "No. of detected cases with Non-Converter (IR , RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "FB1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "FC1", "No. of detected cases with Non-Converter (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "FD1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "FE1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "FF1", "No. of detected cases with Non-Converter (IR , RR) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "FG1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbNewCategorywithoutpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("H");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with New (New, MDR-TB contact, PLHIV) category cases withXpertORconventionalDst");
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

		EmrReportingUtils.addRow(dsd, "HA1", "No. of detected cases with NEW patient tb cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "HB1", "No. of detected cases with NEW patient tb cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "HC1", "No. of detected cases with NEW patient  tb cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "HD1", "No. of detected cases with NEW patient tb cases with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "HE1", "No. of detected cases with NEW patient tb cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "HF1", "No. of detected cases with NEW patient tb cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "HG1", "No. of detected cases with NEW patient tb cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbMDRTBCASESwithoutpaspatientwithXpertORconventionalDstAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("G");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with MDR tb cases withXpertORconventionalDst");
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

		EmrReportingUtils.addRow(dsd, "GA1", "No. of detected cases with MDR tb cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "GB1", "No. of detected cases with MDR tb cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "GC1", "No. of detected cases with MDR tb cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "GD1", "No. of detected cases with MDR tb cases with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "GE1", "No. of detected cases with MDR tb cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "GF1", "No. of detected cases with MDR tb cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "GG1", "No. of detected cases with MDR tb cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
       
	
	private DataSetDefinition createTbOtherCategorywithoutpaspatientwithXpertORconventionalDstAt36Month() {
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

		EmrReportingUtils.addRow(dsd, "IA1", "No. of detected cases with Other Category cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "IB1", "No. of detected cases with Other Category cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "IC1", "No. of detected cases with Other Category cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "ID1", "No. of detected cases with Other Category with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "IE1", "No. of detected cases with Other Category cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "IF1", "No. of detected cases with Other Category cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "IG1", "No. of detected cases with Other Category cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
		private DataSetDefinition createTbsmearpositiveculturepositivewithoutpaspatientwithTBEnrollNumberAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("J");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with smear positive and Solid OR Liquid culture  positive at iniatiation withTBEnrollNumber from Enrollment Form");
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

		EmrReportingUtils.addRow(dsd, "JA1", "No. of detected cases with smear (+)ve and Culture (+) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "JB1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "JC1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "JD1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "JE1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "JF1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "JG1", "No. of detected cases new with smear (+)ve and Culture (+) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.TbsmearculturewithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbsmearnegativeculturpositiveewithoutpaspatientwithTBEnrollNumberAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("K");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with smear negative and Solid OR Liquid culture  positive at iniatiation withTBEnrollNumber from Enrollment Form");
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

		EmrReportingUtils.addRow(dsd, "KA1", "No. of detected cases with smear (-)ve and Culture (+) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "KB1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "KC1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "KD1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "KE1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "KF1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "KG1", "No. of detected cases new with smear (-)ve and Culture (+) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.TbsmearnegativeculturewithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbNewCategorywithoutpaspatientwithTBEnrollNumberAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("L");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with New (New, MDR-TB contact, PLHIV) category withTBEnrollNumber from Enrollment Form");
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

		EmrReportingUtils.addRow(dsd, "LA1", "No. of detected cases with New category with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "LB1", "No. of detected cases with New category with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "LC1", "No. of detected cases with New category  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "LD1", "No. of detected cases with New category with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "LE1", "No. of detected cases with New category  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "LF1", "No. of detected cases with New category  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "LG1", "No. of detected cases with New category  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbCategoryIIFwithoutpaspatientwithTBEnrollNumberAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("M");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS   and with Treatment after failure of treatment (IR , RR) withTBEnrollNumber from Enrollment Form");
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

		EmrReportingUtils.addRow(dsd, "MA1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "MB1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "MC1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "MD1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "ME1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "MF1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "MG1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
   
	private DataSetDefinition createTbCategoryIITADwithoutpaspatientwithTBEnrollNumberAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("N");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Treatment after lost to follow up (IR , RR) withTBEnrollNumber from Enrollment Form");
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

		EmrReportingUtils.addRow(dsd, "NA1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "NB1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "NC1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "ND1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "NE1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "NF1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "NG1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultwithoutpasTBAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition  createTbRelapseCategoryIIwithoutpaspatientwithTBEnrollNumberAt36Month(){
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("O");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Relapse ( IR, RR) withTBEnrollNumber from Enrollment Form");
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

		EmrReportingUtils.addRow(dsd, "OA1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "OB1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "OC1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "OD1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "OE1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "OF1", "No. of detected cases with Relapse ( IR, RR)  with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "OG1", "No. of detected cases with Relapse ( IR, RR) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbCategoryIFwithoutpaspatientwithTBEnrollNumberAt36Month() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("P");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen without PAS  and with Non-Converter (IR , RR) withTBEnrollNumber from Enrollment Form");
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

		EmrReportingUtils.addRow(dsd, "PA1", "No. of detected cases with Non-Converter (IR , RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "PB1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "PC1", "No. of detected cases with Non-Converter (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "PD1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "PE1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "PF1", "No. of detected cases with Non-Converter (IR , RR) with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "PG1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbMDRTBCASESwithoutpaspatientwithTBEnrollNumberAt36Month() {
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

		EmrReportingUtils.addRow(dsd, "QA1", "No. of detected cases with MDR tb cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "QB1", "No. of detected cases with MDR tb cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "QC1", "No. of detected cases with MDR tb cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "QD1", "No. of detected cases with MDR tb cases with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "QE1", "No. of detected cases with MDR tb cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "QF1", "No. of detected cases with MDR tb cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "QG1", "No. of detected cases with MDR tb cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbOtherCategorywithoutpaspatientwithTBEnrollNumberAt36Month() {
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

		EmrReportingUtils.addRow(dsd, "RA1", "No. of detected cases with Other Category cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "RB1", "No. of detected cases with Other Category cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "RC1", "No. of detected cases with Other Category cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "RD1", "No. of detected cases with Other Category with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "RE1", "No. of detected cases with Other Category cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "RF1", "No. of detected cases with Other Category cases with outcome (TransferedOut)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "RG1", "No. of detected cases with Other Category cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwithoutpasAt36mnth(), indParams), columnsG);
		return  dsd;
	}
}

