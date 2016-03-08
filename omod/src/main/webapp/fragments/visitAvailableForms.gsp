<%
	ui.decorateWith("kenyaui", "panel", [ heading: "<html><span>Available Visit Forms</span></html>" ])

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
    \$("span").click(function(){ 
        \$("p").toggle();
       
    });
    
});
</script>

<html><p>${ ui.includeFragment("kenyaui", "widget/formStack", [ forms: availableForms, onFormClick: onFormClick ]) }</p></html>
