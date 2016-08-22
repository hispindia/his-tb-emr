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

	<% } %>

	<br />
           <tr>
  				<td width="50%" colspan="6" valign="top">
	                <table width="100%" border="1">
	                	<tr bgcolor="#778899">
							<td colspan="3">
								<center><strong>DST Result</strong></center>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<table border="1" width="100%">
									<tr >
										<th colspan="1"><strong>Drug</strong></th>
										<th colspan="1"><strong>R</strong></th>
										<th colspan="1"><strong>H</strong></th>
										<th colspan="1"><strong>E</strong></th>
										<th colspan="1"><strong>S</strong></th>
										<th colspan="1"><strong>Km</strong></th>
										<th colspan="1"><strong>Cm</strong></th>
										<th colspan="1"><strong>Fq</strong></th>
										<th colspan="1"><strong>Pto/Eto</strong></th>
										<th colspan="1"><strong>Other</strong></th>
										<th colspan="1"><strong>Date of DST</strong></th>
										<th colspan="1"><strong>Culture Number</strong></th>
									</tr>
								<% for ( d in cultureDstList ) { %>
								<% def values = d.value.split(",")	%>
									<tr>
										<td colspan="1"><% println  d.key+1  %></td>
										<td colspan="1"><% println  values[3] %> </td>
										<td colspan="1"><% println  values[2] %> </td>
										<td colspan="1"><% println  values[4] %> </td>
										<td colspan="1"><% println  values[1] %> </td>
										<td colspan="1"><% println  values[7] %> </td>
										<td colspan="1"><% println  values[6] %> </td>
										<td colspan="1"><% println  values[10] %> </td>
										<td colspan="1"><% println  values[5] %> </td>
										<td colspan="1"><% println  values[8] %> </td>
										<td colspan="1"><% println  values[0] %> </td>
										<td colspan="1"><% println  values[9] %> </td>
									</tr>
									<% } %>
								</table>
							</td>
			             </tr>
			          </table>
				</td> 
			</tr>


</div>