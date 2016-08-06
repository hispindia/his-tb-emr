<%
	ui.decorateWith("kenyaui", "panel", [ heading: ui.format(program), frameOnly: true ])
%>
<% if (enrollments) { %>
<div class="ke-panel-content">
	<% enrollments.reverse().each { enrollment -> %>
		<% if (enrollment.dateCompleted) { %>
		<div class="ke-stack-item">
			${ ui.includeFragment("kenyaemr", "program/programCompletion", [ patientProgram: enrollment, showClinicalData: config.showClinicalData ]) }
		</div>
		<% } else if (patientForms) { %>
		<div class="ke-stack-item">
			<% patientForms.each { form -> %>
				${ ui.includeFragment("kenyaui", "widget/button", [
						iconProvider: form.iconProvider,
						icon: form.icon,
						label: form.name,
						extra: "Edit form",
						href: ui.pageLink("kenyaemr", "editProgramForm", [
								appId: currentApp.id,
								patientProgramId: enrollment.id,
								formUuid: form.formUuid,
								returnUrl: ui.thisUrl()
						])
				]) }
			<% } %>
		</div>
		<% } %>
	
		<% if (defaultEnrollmentForm.targetUuid == 'e4b506c1-7379-42b6-a374-284469cba8da') { %>
			<div class="ke-stack-item">	
				${ ui.includeFragment("kenyaemr", "program/programEnrollment", [ patientProgram: enrollment, showClinicalData: config.showClinicalData ]) }
			</div>	
		<% } else{ %>			
			<div class="ke-stack-item">
				${ ui.includeFragment("kenyaemr", "program/programEnrollment", [ patientProgram: enrollment, showClinicalData: config.showClinicalData ]) }
			</div>
		<% } %>
	<% } %>
</div>
<% } %>

<% if (currentEnrollment || patientIsEligible) { %>
<div class="ke-panel-footer">
	<% if (currentEnrollment) { %>
		<% if(defaultEnrollmentForm.targetUuid == '89994550-9939-40f3-afa6-173bce445c79') { %>	
			<button type="button" onclick="ui.navigate('${ ui.pageLink("kenyaemr", "enterForm", [ patientId: patient.id, formUuid: defaultCompletionForm.targetUuid, appId: currentApp.id, returnUrl: ui.thisUrl() ]) }')">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/discontinue.png") }" /> Enter outcome
			</button>	
		<% } else{ %>
			<button type="button" onclick="ui.navigate('${ ui.pageLink("kenyaemr", "enterForm", [ patientId: patient.id, formUuid: defaultCompletionForm.targetUuid, appId: currentApp.id, returnUrl: ui.thisUrl() ]) }')">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/discontinue.png") }" /> Discontinue
			</button>
		<% } %>
				
	<% } else if (patientIsEligible) { %>

		<% if (defaultEnrollmentForm.targetUuid == '89994550-9939-40f3-afa6-173bce445c79' && artEncounter=="TB Discontinuation") { %>	
			<button type="button" onclick="ui.navigate('${ ui.pageLink("kenyaemr", "enterForm", [ patientId: patient.id, formUuid: defaultEnrollmentForm.targetUuid, appId: currentApp.id, returnUrl: ui.thisUrl() ]) }')">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/enroll.png") }" /> ReEnroll
			</button>
		<% } else{ %>
			<button type="button" onclick="ui.navigate('${ ui.pageLink("kenyaemr", "enterForm", [ patientId: patient.id, formUuid: defaultEnrollmentForm.targetUuid, appId: currentApp.id, returnUrl: ui.thisUrl() ]) }')">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/enroll.png") }" /> Enroll
			</button>
		<% } %>
	<% } %>
</div>
<% } %>
