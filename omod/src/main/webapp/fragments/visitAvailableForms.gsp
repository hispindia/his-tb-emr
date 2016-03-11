<%
	ui.decorateWith("kenyaui", "panel", [ heading: "<html><div id=\"availab\">Available Visit Forms</div></html>" ])
	config.require("visit")
	def onFormClick = { form ->
		def visitId = currentVisit ? currentVisit.id : activeVisit.id
		def opts = [ appId: currentApp.id, visitId: visitId, patientId: currentPatient.id, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
    	"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
	}
%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
\$(document).ready(function(){
    \$("#availab").click(function(){ 
        \$("#avail").slideToggle();
    });
});
</script>
<html><h5 id="avail">${ ui.includeFragment("kenyaui", "widget/formStack", [ forms: availableForms, onFormClick: onFormClick ]) }</h5></html>
