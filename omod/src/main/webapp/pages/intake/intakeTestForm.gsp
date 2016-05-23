<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-sidebar">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td>
					<div class="ke-panel-frame">
						<div class="ke-panel-heading">Visits(${ visitsCount })</div>
						</div>
				
						<div class="ke-panel-frame">
						<% if (!visits) {
							print ui.includeFragment("kenyaui", "widget/panelMenuItem", [
								label: ui.message("general.none"),
							])
						}
						else {
							visits.each { visit ->
								print ui.includeFragment("kenyaui", "widget/panelMenuItem", [
										label: "Visit Details",
										href: ui.pageLink("kenyaemr", "intake/intakeTestForm", [ patientId: currentPatient.id, visitId: visit.id ]),
										extra: kenyaui.formatVisitDates(visit),
										active: (selection == "visit-" + visit.id)
								])
							}
						} %>
					</div>
				</td>
			</tr>
		</table>
</div>

<div class="ke-page-content">

	${ ui.includeFragment("kenyaemr", "visitMenu", [ patient: currentPatient, visit: activeVisit ]) }
	<% if (visit) { %>
		<% if (!visit.voided) { %>
			${ ui.includeFragment("kenyaemr", "visitAvailableForms", [ visit: visit ]) }
			${ ui.includeFragment("kenyaemr", "visitCompletedForms", [ visit: visit ]) }
		<% } %>

	<% } else if (form) { %>

		<div class="ke-panel-frame">
			<div class="ke-panel-heading">${ ui.format(form) }</div>
			<div class="ke-panel-content">

				<% if (encounter) { %>
					${ ui.includeFragment("kenyaemr", "form/viewHtmlForm", [ encounter: encounter ]) }
				<% } else { %>
					<em>Not filled out</em>
				<% } %>

			</div>
		</div>
	<% } %>

</div>
