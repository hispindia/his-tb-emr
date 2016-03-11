<%
	ui.decorateWith("kenyaui", "panel", [ heading: "<html><div id=\"availdiv\">Completed Visit Forms</div></html>" ])
	def onEncounterClick = { encounter ->
		"""kenyaemr.openEncounterDialog('${ currentApp.id }', ${ encounter.id });"""
	}
%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
\$(document).ready(function(){
    \$("#availdiv").click(function(){
          \$("#compl").slideToggle();
       });
});
</script>
<html><h5 id="compl">${ ui.includeFragment("kenyaemr", "widget/encounterStack", [ encounters: encounters, onEncounterClick: onEncounterClick ]) }</h5></html>