<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient, layout: "sidebar" ])

	def menuItems = [
			[
					label: "Overview",
					href: ui.pageLink("kenyaemr", "chart/chartViewPatient", [ patientId: currentPatient.id, section: "overview" ]),
					active: (selection == "section-overview"),
					iconProvider: "kenyaui",
					icon: "buttons/patient_overview.png"
			]
	];

	def backButton = [
			[
					label: "Back",
					href: ui.pageLink("kenyaemr", "clinician/clinicianViewPatient", [ patientId: currentPatient.id]),
					iconProvider: "kenyaui",
					icon: "buttons/back.png"
			]
	];
	
%>
<div class="ke-page-sidebar">
	<div class="ke-panel-frame">
		<% backButton.each { item -> print ui.includeFragment("kenyaui", "widget/panelMenuItem", item) } %>
		<% menuItems.each { item -> print ui.includeFragment("kenyaui", "widget/panelMenuItem", item) } %>
	</div>	
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
						label: ui.format(visit.visitType),
						href: ui.pageLink("kenyaemr", "chart/chartViewPatient", [ patientId: currentPatient.id, visitId: visit.id ]),
						extra: kenyaui.formatVisitDates(visit),
						active: (selection == "visit-" + visit.id)
				])
			}
		} %>
	</div>

</div>

<div class="ke-page-content">

	<% if (visit) { %>

		${ ui.includeFragment("kenyaemr", "visitSummary", [ visit: visit ]) }
		<% if (!visit.voided) { %>
			${ ui.includeFragment("kenyaemr", "visitCompletedForms", [ visit: visit ]) }
			${ ui.includeFragment("kenyaemr", "visitAvailableForms", [ visit: visit ]) }
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

	<% } else if (program) { %>

		${ ui.includeFragment("kenyaemr", "program/programHistory", [ patient: currentPatient, program: program, showClinicalData: true ]) }

	<% } else if (section == "overview") { %>

		${ ui.includeFragment("kenyaemr", "program/programCarePanels", [ patient: currentPatient, complete: true, activeOnly: false ]) }

	<% } else if (section == "moh257") { %>

		${ ui.includeFragment("kenyaemr", "moh257", [ patient: currentPatient ]) }

	<% } %>

</div>