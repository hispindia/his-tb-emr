package org.openmrs.module.kenyaemr.fragment.controller.field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Patient;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.regimen.RegimenChange;
import org.openmrs.module.kenyaemr.regimen.RegimenChangeHistory;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.regimen.RegimenOrder;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.springframework.web.bind.annotation.RequestParam;

public class StopDrugRegimenFragmentController {
	public void stopRegimen(@RequestParam("patient") Patient patient,
			@RequestParam("reason") Concept reason,
			@RequestParam(value="otherReason",required = false) String otherReason,
			@SpringBean RegimenManager regimenManager) {
		OrderService os = Context.getOrderService();
		String category="ARV";
		Concept masterSet = regimenManager.getMasterSetConcept(category);
		RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, masterSet);
		RegimenChange lastChange = history.getLastChange();
		RegimenOrder baseline = lastChange != null ? lastChange.getStarted() : null;
		List<DrugOrder> toStop = new ArrayList<DrugOrder>(baseline.getDrugOrders());
		for (DrugOrder o : toStop) {
			o.setDiscontinued(true);
			o.setDiscontinuedDate(new Date());
			o.setDiscontinuedBy(Context.getAuthenticatedUser());
			o.setDiscontinuedReason(reason);
			o.setDiscontinuedReasonNonCoded(otherReason);
			os.saveOrder(o);
		}
	}
}
