<% if (encounter) { %>
	<button id="editHivForm" type="button" class="ke-compact" onclick="ui.navigate('${ ui.pageLink("kenyaemr", "editForm", [ encounterId: encounter.id, appId: currentApp.id, returnUrl: ui.thisUrl() ]) }')">
		<img src="${ ui.resourceLink("kenyaui", "images/forms/generic.png") }" />
	</button>
<% } %>

${ ui.includeFragment(summaryFragment.provider, summaryFragment.path, [ patientProgram: enrollment, encounter: encounter, showClinicalData: showClinicalData ] )}