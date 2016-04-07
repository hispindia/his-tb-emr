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
		dataPoints << [ label: "TB treatment outcome ", value: calculations.tbTreatmentOutcome ]
		dataPoints << [ label: "TB treatment outcome date", value: calculations.tbTreatmentOutcomeDate ]
		
}
else{
	dataPoints << [ label: "TB treatment outcome", value:  "None" ];
}
	
%>

<div class="ke-stack-item">
	<% dataPoints.each { print ui.includeFragment("kenyaui", "widget/dataPoint", it) } %>
</div>

	<table style="background-color: #e8efdc" border="1" align="center" width="100%">
		<tr>
			<th><center>Month</center></th>
			<th><center>1</center></th>
			<th><center>2</center></th>
			<th><center>3</center></th>
			<th><center>4</center></th>
			<th><center>5</center></th>
			<th><center>6</center></th>
			<th><center>7</center></th>
			<th><center>8</center></th>
			<th><center>9</center></th>
			<th><center>10</center></th>
			<th><center>11</center></th>
			<th><center>12</center></th>
			<th><center>13</center></th>
			<th><center>14</center></th>
			<th><center>15</center></th>
			<th><center>16</center></th>
			<th><center>17</center></th>
			<th><center>18</center></th>
			<th><center>19</center></th>
			<th><center>20</center></th>
			<th><center>21</center></th>
			<th><center>22</center></th>
			<th><center>23</center></th>
			<th><center>24</center></th>
		</tr>
		<tr>
			<td><center>Smear</center></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
		</tr>
		<tr>
			<td><center>Culture</center></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
		</tr>	
	</table>
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