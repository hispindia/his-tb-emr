<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Patient Details", frameOnly: true ])
%>
<div class="ke-panel-content">
	<div class="ke-stack-item">
		<% if (recordedAsDeceased) { %>
		<div class="ke-warning" style="margin-bottom: 5px">
			Patient has been recorded as deceased in a program form. Please update the registration form.
		</div>
		<% } %>
		
		Financial Support :${fin}  <br/>
		Nutrition Support :${nut}  <br/> 

		<button type="button" class="ke-compact" onclick="ui.navigate('${ ui.pageLink("kenyaemr", "registration/editPatient", [ patientId: patient.id, returnUrl: ui.thisUrl() ]) }')">
			<img src="${ ui.resourceLink("kenyaui", "images/glyphs/edit.png") }" />
		</button>

		
<!--
		<% patient.activeAttributes.each { %>
		${ ui.includeFragment("kenyaui", "widget/dataPoint", [ label: ui.format(it.attributeType), value: it ]) }
		<% } %>
-->		
		<% for ( d in dotMembers ) { %>
		<% def values = d.value.split(",")	%>
			DOT Provider Name : <strong> <% println  values[0] %> </strong> </br>
			DOT Provider Contact No. : <strong><% println  values[1] %></strong>
		<% } %>
		</br> 
		<% if (personWrap.fatherName) { %>
		Father's name : <strong>${personWrap.fatherName}</strong>
		<% } %>
		<% if (personWrap.telephoneContact) { %>
			<br/>Patient contact number : <strong> ${personWrap.telephoneContact}</strong>
		<% } %>
		<% if (comorbitidyList) { %>
			<br/>Co-morbidities : <strong>${comorbitidyList}</strong>
		<% } %>
		<% if (pregStatusVal) { %>
		<%  if(deliveredStatusVal!="140791" && deliveredStatusVal!="140242" && deliveredStatusVal!="151843")
		{ %>	<br/>Pregnancy : <strong>${pregStatusVal}</strong>
		<% } }%>
		<% if (drugAllergiesVal) { %>
			<% if (drugAllergiesVal=="Yes") { %>
			<br/>Drug Allergy & name : <strong>${drugAllergiesVal} 
			(${drugAllergiesName}  ${otherReasonVal})
			<% } else {%>
				<br/>Drug Allergy  : <strong>${drugAllergiesVal} 
			</strong>
		<%  } } %>
		
		
		
		
	</div>
</div>

<% if (forms) { %>
<div class="ke-panel-footer">
	<% forms.each { form -> %>
		${ ui.includeFragment("kenyaui", "widget/button", [
				iconProvider: form.iconProvider,
				icon: form.icon,
				label: form.name,
				extra: "Edit form",
				href: ui.pageLink("kenyaemr", "editPatientForm", [
					appId: currentApp.id,
					patientId: patient.id,
					formUuid: form.formUuid,
					returnUrl: ui.thisUrl()
				])
		]) }
	<% } %>
</div>

<% } %>
