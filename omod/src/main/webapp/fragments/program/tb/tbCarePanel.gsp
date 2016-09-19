<%
	ui.decorateWith("kenyaui", "panel", [ heading: "TB Care" ])

	def dataPoints = []

/*
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
		dataPoints << [ label: "TB treatment outcome ", value: calculations.tbTreatmentOutcome ]
		dataPoints << [ label: "TB treatment outcome date", value: calculations.tbTreatmentOutcomeDate ]
		
}
else{
	dataPoints << [ label: "TB treatment outcome", value:  "None" ];
}
	*/
%>


<div class="ke-stack-item">
	<% if (activeVisit && currentApp.id != "kenyaemr.medicalChart") { %>
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


<div class="ke-stack-item">
	<% dataPoints.each { print ui.includeFragment("kenyaui", "widget/dataPoint", it) } %>
</div>
<% if (currentApp.id != "kenyaemr.medicalChart") { %>

	<table style="background-color: #e8efdc" border="1" align="center" width="100%">
		<tr>
			<th><center>Month</center></th>
			<% for ( d in smearCultureIndexList ) { %>
				<td style="font-size: 15px;" colspan="1"><center><b>
				<% println d.key %>
				</b></center></td>	
			<% }%>
		</tr>
		<tr>
			<td><center>Smear</center></td>
			<% for ( d in smearCultureIndexList ) { %>
				<td style="font-size: 15px;" colspan="1"><center><b>
				<% def values = d.value.split(",")	%>
					<% if (values[1].contains('AFB not seen')) { %>  AFB not seen
					<% } else if (values[1].contains('Trace')) { %> Trace
					<% } else if (values[1].contains('+++')) { %> +++
					<% } else if (values[1].contains('++')) { %> ++
					<% } else if (values[1].contains('+')) { %> +
				<% }  %>
				</b></center></td>	
			<% }%>
		</tr>
		<tr>
			<td><center>Culture</center></td>
			<% for ( d in smearCultureIndexList ) { %>
				<td style="font-size: 15px;" colspan="1"><center><b>
				<% def values = d.value.split(",")	%>
					<% if (values[2].contains('1-10 colonies')) { %> 1-10 colonies
					<% } else if (values[2].contains('+++')) { %> +++
					<% } else if (values[2].contains('++')) { %> ++
					<% } else if (values[2].contains('+')) { %> +
					<% } else if (values[2].contains('Negative')) { %> -
					<% } else if (values[2].contains('Contaminated')) { %> Contaminated
					<% } else if (values[2].contains('Positive')) { %> +
					
					
				<% }  %>
				</b></center></td>	
			<% }%>
		</tr>	
	</table>
<% } %>
