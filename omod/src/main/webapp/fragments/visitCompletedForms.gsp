<%
	ui.decorateWith("kenyaui", "panel", [ heading: "<html><div >Completed Visit Forms</div></html>" ])

	def onEncounterClick = { encounter ->
		"""kenyaemr.openEncounterDialog('${ currentApp.id }', ${ encounter.id });"""
	}
%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
\$(document).ready(function(){
    \$("div").click(function(){
          \$("h5").toggle();
       });
     
});
</script>

<html><h5>${ ui.includeFragment("kenyaemr", "widget/encounterStack", [ encounters: encounters, onEncounterClick: onEncounterClick ]) }</h5></html>