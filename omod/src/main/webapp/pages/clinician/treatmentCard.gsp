<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient, layout: "sidebar" ])
	
%>

<div class="ke-page-content">
	${ ui.includeFragment("kenyaemr", "program/treatmentCardRegister", [ patient: currentPatient, complete: true, activeOnly: false ]) }
</div>