<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Enter Lab Results" ])
%>

<% if (listTests == null) { %>
No record found.
<% } else { %>
<form id="enterLabResultForm" action="${ ui.actionLink("kenyaemr", "intake/enterLabResult", "submit") }" method="post">
<input type="hidden" id="confirm" name="confirmed" value="${confirmed}"/>
		<input type="hidden" name="visitId" value="${visit.visitId}"/>
		<% if (resultEncounter != null) { %>
			<input type="hidden" name="encounterId" value="${resultEncounter.encounterId}"/>
		<% } %>
<table style="text-align:left">
<thead>
<tr>
<th><b>No.</b></th>
<th><b>Lab Test</b></th>
<th><b>Test Result</b></th>
<th>Unit</th>
<th><b>Reference Range</b></th>
<th><b>Comment</b></th>
</tr>
</thead>
<% listTests.eachWithIndex { test , count -> %>
	<input type="hidden" id="${test.conceptId}_isRadiology" name="${test.conceptId}_isRadiology" value="${test.isRadioloy}"/>
	
	<tr class="ke-stack-item ke-navigable">
	<td>${count+1}</td>
	<td>
		<input type="text" id="${test.obs.obsId}"  size="40" value="${test.name}" alt="${test.name}" disabled>
		<input type="hidden" name="conceptIds" value="${test.conceptId}"/>
	</td> 
	<% if (test.isRadioloy) { %>
		<td >
		<table style="text-align:right"><tr>
			<td>Finding <input type="text" id="${test.conceptId}_valueFinding" name="${test.conceptId}_valueFinding" size="15" value="${test.resultFinding}"  onblur="calculateComment(${test.conceptId})"></td></tr>
		<tr><td>Impression <input type="text" id="${test.conceptId}_valueImpression" name="${test.conceptId}_valueImpression" size="15" value="${test.resultImpression}"  onblur="calculateComment(${test.conceptId})"></td></tr>
		</table>
		</td>
	<% } else { %> 
		<td style="text-align:right"><input type="text" id="${test.conceptId}_value" name="${test.conceptId}_value" size="15" value="${test.result}"  onblur="calculateComment(${test.conceptId})"></td>
		<td ><input disabled type="text" size="10" value="${test.units}"/></td>
		<td><input disabled id="${test.conceptId}_range" size="10" type="text" value="${test.range}"/></td>
		<td><input disabled id="${test.conceptId}_comment" class="comment" size="15" type="text" value=""/></td>
		<% if(test.conceptId==790) {%>
			<td> Creatinine clearance  rate :  
				<input disabled id="${test.conceptId}_rate" class="comment" size="15" type="text" value=""/>
			</td>
		<% } %>
	<% } %>
	
	</tr>
<% } %>
</table> 
</br>
<% if (!confirmed) { %>
<input type="button" value="Confirm" onclick="confirmResult()"/>
<input type="submit" value="Save"/>
<% } %>
<input type="button" value="Cancel" onclick="ui.navigate('${returnUrl}')"/>
</form>
<% } %>

<script type="text/javascript">
jq(function() {
	jq('#enterLabResultForm .cancel-button').click(function() {
		ui.navigate('${ returnUrl }');
	});

	kenyaui.setupAjaxPost('enterLabResultForm', {
		onSuccess: function(data) {
			ui.navigate('${ returnUrl }');
		}
	});
	
	jq(".comment").each(function () {
		var inputId = jq(this).attr("id");
		var conceptId = inputId.split("_")[0];
		calculateComment(conceptId);
	});
	
	var confirmed = jq("#confirm").val();
	if (confirmed == "true") {
		jq("#enterLabResultForm input[type=text]").each(function(){jq(this).attr("disabled","disabled");});
	}
	
});

function calculateComment(conceptId) {
	if ("true" == jq("#"+conceptId+"_isRadiology").val()) {
		return ;
	} 
	var value =  parseInt(jq("#"+conceptId+"_value").val());
	var range = jq("#"+conceptId+"_range").val();
	var low = parseInt(range.split("-")[0]);
	var high = parseInt(range.split("-")[1]);
	var comment = "";
	var commentColor = "";
	if (!isNaN(value) && !isNaN(low) && !isNaN(high)) {
		if (value < low) {
			comment = "Low";
			commentColor = "red"; 
		} else if (value > high) {
			comment = "High";
			commentColor = "red";
		} else {
			comment = "Normal";
			commentColor = "green";
		}
	}
	jq("#"+conceptId+"_comment").val(comment);
	jq("#"+conceptId+"_comment").css("color",commentColor);
	
	var total;
	var age = '${patientAge}';
	if(conceptId==790 && '${patientGender}'=='F'){
		total = 175*  Math.pow(value, -1.154) * Math.pow(age,-0.203) * 0.742;
	}
	else if(conceptId==790 && '${patientGender}'=='M'){
		total = 175*  Math.pow(value, -1.154) * Math.pow(age,-0.203) ;
	}
	jq("#"+conceptId+"_rate").val(Math.round(total*100)/100 + ' ml/min');
}

function confirmResult() {
	if (confirm("You can't edit the Lab Result once it is confirmed. Are you sure ?")) {
		jq("#confirm").val("true");
		jq("#enterLabResultForm").submit();
	} 
}

</script>