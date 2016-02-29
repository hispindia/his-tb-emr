package org.openmrs.module.kenyaemr.fragment.controller.patient;


import org.openmrs.Patient;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

public class PatientChartFragmentController {
	public void controller(@FragmentParam("patient") Patient patient,
			   @SpringBean KenyaUiUtils kenyaUi,
			   PageRequest pageRequest,
			   UiUtils ui,
			   FragmentModel model) {

model.addAttribute("patient", patient);
}

}
