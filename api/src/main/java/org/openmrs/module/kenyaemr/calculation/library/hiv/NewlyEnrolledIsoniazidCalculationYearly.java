/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.kenyaemr.calculation.library.hiv;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Program;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;


public class NewlyEnrolledIsoniazidCalculationYearly extends AbstractPatientCalculation implements PatientFlagCalculation {

    @Override
    public String getFlagMessage() {
        return "Newly Enrolled Isoniazid Patients";
    }

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
        Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
        Set<Integer> alive = Filters.alive(cohort, context);
        Set<Integer> inHIV = Filters.inProgram(hivProgram, alive, context);
        Set<Integer> ltfu = CalculationUtils.patientsThatPass(calculate(new LostToFollowUpCalculation(), cohort, context));

		Calendar calendar = Calendar.getInstance();
		
		//endDate value have to be set from UI
        Date endDate = new Date(); 
        
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, -360); //12 months
        Date startDate = calendar.getTime();
        
        CalculationResultMap finalList = new CalculationResultMap();
        ObsService obs = Context.getObsService();
        
        for (Integer pID : cohort) {
            if (inHIV.contains(pID)) {
                if (alive.contains(pID)) {

                    boolean trigger = false;
                    Person p = new Patient(pID);
                    List<Obs> obsListForPatient = obs.getObservationsByPerson(p);
                    Date obsDate = null;

                    for (Obs patient : obsListForPatient) {
                        if (patient.getValueCoded() == Dictionary.getConcept(Dictionary.ISONIAZID)) {
                            obsDate = patient.getObsDatetime();
                            break;
                        }
                    }
                    if (obsDate != null && obsDate.after(startDate) && obsDate.before(endDate)) {
                        trigger = true;
                    }

                    if (ltfu.contains(pID)) {
                        trigger = false;
                    }

                    finalList.put(pID, new BooleanResult(trigger, this, context));
                }
            }
        }
        return finalList;
    }

}
