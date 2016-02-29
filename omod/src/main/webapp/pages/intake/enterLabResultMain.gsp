<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
	
%>

<div class="ke-page-content">
		${ ui.includeFragment("kenyaemr", "intake/enterLabResult", [visit : visit, encounter : encounter ]) }
</div>