<%
		
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-patientheader">

</div>

<div class="ke-page-content">

	${ ui.includeFragment("kenyaemr", "dispensary/drugOrderList", [ patient: currentPatient ]) }

</div>


