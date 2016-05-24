<%
	ui.decorateWith("kenyaui", "panel", [ heading: "View Chart Graphs", frameOnly: true ])
%>
<div class="ke-panel-content" style="text-align: center;">
	<div class="ke-panel-item">
	<button type="button" class="ke-compact" onclick="ui.navigate('${ ui.pageLink("kenyaemr", "chart/chartViewPatient", [ patientId: patient.id, returnUrl: ui.thisUrl() ]) }')">
			<img src="${ ui.resourceLink("kenyaui", "images/reports/indicator.png") }" /><b>View Details</b>
		</button> 
		
	</div>	
</div>


