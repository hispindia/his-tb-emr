<%
	ui.decorateWith("kenyaui", "panel", [ heading: "TB Care" ])

	def dataPoints = []


if(calculations.tbPatientStatus){
		dataPoints << [ label: "TB Status", value: calculations.tbPatientStatus ]
}
else{
	dataPoints << [ label: "TB Status", value:  "None" ];
}

if(calculations.tbDiseaseSite){
		dataPoints << [ label: "Site", value: calculations.tbDiseaseSite ]
}
else{
	dataPoints << [ label: "Site", value:  "None" ];
}

if(calculations.tbTreatmentDrugStartDate){
		dataPoints << [ label: "TB treatment start date", value: calculations.tbTreatmentDrugStartDate ]
		dataPoints << [ label: "TB regimen", value: calculations.tbTreatmentDrugRegimen ]
}
else{
	dataPoints << [ label: "TB regimen", value: "None" ]
}

if(calculations.tbTreatmentDrugSensitivity){
		dataPoints << [ label: "Drug sensitivity", value: calculations.tbTreatmentDrugSensitivity ]
}
else{
	dataPoints << [ label: "Drug sensitivity", value: "None" ]
}


if(calculations.tbTreatmentOutcome){
		dataPoints << [ label: "TB treatment outcome", value: calculations.tbTreatmentOutcome ]
		dataPoints << [ label: "TB treatment outcome date", value: calculations.tbTreatmentOutcomeDate ]
		
}
else{
	dataPoints << [ label: "TB treatment outcome", value:  "None" ];
}
	
%>

<div class="ke-stack-item">
	<% dataPoints.each { print ui.includeFragment("kenyaui", "widget/dataPoint", it) } %>
</div>
<!--
<div class="ke-stack-item">
	<% if (activeVisit) { %>
	<button type="button" class="ke-compact" onclick="ui.navigate('${ ui.pageLink("kenyaemr", "regimenEditor", [ patientId: currentPatient.id, category: "TB", appId: currentApp.id, returnUrl: ui.thisUrl() ]) }')">
		<img src="${ ui.resourceLink("kenyaui", "images/glyphs/edit.png") }" />
	</button>
	<% } %>

	<%
		if (regimenHistory.lastChange) {
			def lastChange = regimenHistory.lastChangeBeforeNow
			def regimen = lastChange.started ? kenyaEmrUi.formatRegimenLong(lastChange.started, ui) : ui.message("general.none")
			def dateLabel = lastChange.started ? "Started" : "Stopped"
	%>
	${ ui.includeFragment("kenyaui", "widget/dataPoint", [ label: "Regimen", value: regimen ]) }
	${ ui.includeFragment("kenyaui", "widget/dataPoint", [ label: dateLabel, value: lastChange.date, showDateInterval: true ]) }
	<% } else { %>
	${ ui.includeFragment("kenyaui", "widget/dataPoint", [ label: "Regimen", value: ui.message("kenyaemr.neverOnTbRegimen") ]) }
	<% } %>
</div>
-->