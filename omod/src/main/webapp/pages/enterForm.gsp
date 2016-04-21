
<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient, visit: activeVisit ])

	def defaultEncounterDate = activeVisit ? activeVisitDate : new Date()
%>
<div class="ke-page-content">
	${ ui.includeFragment("kenyaemr", "form/enterHtmlForm", [
			patient: currentPatient,
			formUuid: formUuid,
			visit: currentVisit,
			defaultEncounterDate: defaultEncounterDate,
			returnUrl: returnUrl
	]) }
</div>