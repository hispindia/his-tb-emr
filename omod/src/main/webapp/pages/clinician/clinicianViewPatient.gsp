<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-content">

		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
			<td width="20%" valign="top" style="padding-left: 5px">
			<% if (activeVisit) { %>
			${ ui.includeFragment("kenyaemr", "visitAvailableForms", [ visit: activeVisit ]) }
			<% } %>
			${ ui.includeFragment("kenyaemr", "program/programHistories", [ patient: currentPatient, showClinicalData: true ]) }
			</td>
				<td width="60%" valign="top">
					${ ui.includeFragment("kenyaemr", "patient/patientSummary", [ patient: currentPatient ]) }
					${ ui.includeFragment("kenyaemr", "patient/patientChart", [ patient: currentPatient ]) }
					${ ui.includeFragment("kenyaemr", "program/hiv/hivCarePanel", [ patient: currentPatient, complete: false, activeOnly: false ]) }	
					${ ui.includeFragment("kenyaemr", "program/tb/tbCarePanel", [ patient: currentPatient, complete: false, activeOnly: false ]) }	
				</td>
				<td width="20%" valign="top" style="padding-left: 5px">
					
${ ui.includeFragment("kenyaemr", "visitMenu", [ patient: currentPatient, visit: activeVisit ]) }
					<% if (activeVisit) { %>
					
					${ ui.includeFragment("kenyaemr", "visitCompletedForms", [ visit: activeVisit ]) }
					<% } %>
					${ ui.includeFragment("kenyaemr", "patient/patientWhiteCard", [ patient: currentPatient ]) }
				</td>
			</tr>
		</table>
</div>
