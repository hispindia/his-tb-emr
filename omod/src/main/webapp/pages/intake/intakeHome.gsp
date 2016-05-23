<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "" ])
%>

<div class="ke-patientheader">
<div style="float: left; padding-right: 5px">
		<button class="ke-compact" title="IntakeTest" onclick="ke_enterTest()"><img src="${ ui.resourceLink("kenyaui", "images/apps/registration.png") }"/><b> Select Test</b></button>
</div>

<div style="float: right; padding-right: 5px">
		<button class="ke-compact" title="IntakeResult" onclick="ke_enterResult()"><img src="${ ui.resourceLink("kenyaui", "images/apps/registration.png") }"/><b> Enter/edit result</b></button>
</div>

</div>

<script type="text/javascript">

		function ke_enterTest() {
			ui.navigate('kenyaemr', 'intake/intakeEnterTest');
		}
		
		function ke_enterResult() {
			ui.navigate('kenyaemr', 'intake/intakeEnterResult');
		}

</script>










