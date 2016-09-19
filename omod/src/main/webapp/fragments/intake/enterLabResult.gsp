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
<th >Unit</th>
<th ><b>Reference Range</b></th>
<th ><b>Comment</b></th>
</tr>
</thead>
<% listTests.eachWithIndex { test , count -> %>
	<input type="hidden" id="${test.conceptId}_isRadiology" name="${test.conceptId}_isRadiology" value="${test.isRadioloy}"/>
	<input type="hidden" id="${test.conceptId}_hasAnswers" name="${test.conceptId}_hasAnswers" value="${test.hasAnswers}"/>
	
	<tr class="ke-stack-item ke-navigable" border="1"> 
	<td>${count+1}</td>
	<td>
		<table style="text-align:right"><input style='width: 190px;height: 30px;' type="text" id="${test.obs.obsId}"  size="40" value="${test.name}" alt="${test.name}" disabled>
		<input type="hidden" name="conceptIds" value="${test.conceptId}"/></table>
	</td> 
	<% if (test.isRadioloy) { %>
		<td >
		<table style="text-align:right"><tr>
			<td>Finding <input type="text" id="${test.conceptId}_valueFinding" name="${test.conceptId}_valueFinding" size="15" value="${test.resultFinding}"  onblur="calculateComment(${test.conceptId})"></td></tr>
		<tr><td>Impression <input type="text" id="${test.conceptId}_valueImpression" name="${test.conceptId}_valueImpression" size="15" value="${test.resultImpression}"  onblur="calculateComment(${test.conceptId})"></td></tr>
		</table>
		</td>
	<% } else if (test.hasAnswers){ %> 
		<td>
		<table style="text-align:right">
			<select style='width: 190px;height: 20px;' id="${test.conceptId}_valueDropdown" size="1" name="${test.conceptId}_valueDropdown" value="${test.valueDropdown}">
				<% if(test.conceptId==163561) { %>
				<option value=""></option>
				<option ${ (test.valueDropdown=='1874') ? 'selected="true"' : '' } value="1874">Trace</option>
				<option ${ (test.valueDropdown=='162973') ? 'selected="true"' : '' } value="162973">AFB not seen</option>
				<option ${ (test.valueDropdown=='151884') ? 'selected="true"' : '' } value="151884">+++</option>
				<option ${ (test.valueDropdown=='5254') ? 'selected="true"' : '' } value="5254">++</option>
				<option ${ (test.valueDropdown=='143129') ? 'selected="true"' : '' } value="143129">+</option>
				<% }
				else if(test.conceptId==163567)
				{ %>
				<option value=""></option>
				<option ${ (test.valueDropdown=='1594') ? 'selected="true"' : '' } value="1594">1-10 colonies</option>
				<option ${ (test.valueDropdown=='664') ? 'selected="true"' : '' } value="664">Negative</option>
				<option ${ (test.valueDropdown=='151884') ? 'selected="true"' : '' } value="151884">+++</option>
				<option ${ (test.valueDropdown=='5254') ? 'selected="true"' : '' } value="5254">++</option>
				<option ${ (test.valueDropdown=='143129') ? 'selected="true"' : '' } value="143129">+</option>
			    <option ${ (test.valueDropdown=='160008') ? 'selected="true"' : '' } value="160008">Contaminated</option>
				<% } 
				else if(test.conceptId==163566)
				{ %>
				<option value=""></option>
				<option ${ (test.valueDropdown=='703') ? 'selected="true"' : '' } value="703">Positive</option>
				<option ${ (test.valueDropdown=='664') ? 'selected="true"' : '' } value="664">Negative</option>
				<option ${ (test.valueDropdown=='160008') ? 'selected="true"' : '' } value="160008">Contaminated</option>
				<% } 
				else if(test.conceptId==163571)
				{ %>
				<option value=""></option>
				<option ${ (test.valueDropdown=='1586') ? 'selected="true"' : '' } value="1586">MTBC(No)</option>
				<option ${ (test.valueDropdown=='6095') ? 'selected="true"' : '' } value="6095">MTBC(Yes)</option>
				<% } 
				else 
				{ %>
				<option value=""></option>
				<option ${ (test.valueDropdown=='703') ? 'selected="true"' : '' } value="703">Positive</option>
				<option ${ (test.valueDropdown=='664') ? 'selected="true"' : '' } value="664">Negative</option>
				<% } %>
			</select>
			</table>
		</td>
	<% } else if (test.conceptId==12){ %> 	
		
		<td colspan="4" >
			<table  border="1">
				<tr>
					<td></td><td>Findings</td><td>Impressions</td>
				</tr>
				<tr>
					<td>Lungs & Pleura</td>
					<td>
						<table border="1">
							<tr>
								<td></td><td>Right</td><td>Left</td>									
							</tr>
							<tr>
								<td>Upper zone</td>
								<td>
									<select style='width: 190px;height: 20px;' id="163586_valueDropdown" value="" name="163586_valueDropdown" size="1">
										<option value=""></option>
										<option ${ (vd163586=='163525') ? 'selected="true"' : '' } value="163525">NAD</option>
										<option ${ (vd163586=='163526') ? 'selected="true"' : '' } value="163526">Cavity(ies)</option>
										<option ${ (vd163586=='6049') ? 'selected="true"' : '' } value="6049">Infiltrate</option>
										<option ${ (vd163586=='163527') ? 'selected="true"' : '' } value="163527">Consolidation</option>
										<option ${ (vd163586=='148187') ? 'selected="true"' : '' } value="148187">Fibrosis</option>
										<option ${ (vd163586=='163528') ? 'selected="true"' : '' } value="163528">Hyper-inflated lung</option>
										<option ${ (vd163586=='163529') ? 'selected="true"' : '' } value="163529">Collapse lung/atelactasis</option>
										<option ${ (vd163586=='126464') ? 'selected="true"' : '' } value="126464">Alveolitis Calcification</option>
										<option ${ (vd163586=='116197') ? 'selected="true"' : '' } value="116197">Mass/nodule</option>
									</select>
								</td>
								<td>
									<select style='width: 190px;height: 20px;' id="163589_valueDropdown" value="" name="163589_valueDropdown" size="1">
										<option value=""></option>
										<option ${ (vd163589=='163525') ? 'selected="true"' : '' } value="163525">NAD</option>
										<option ${ (vd163589=='163526') ? 'selected="true"' : '' } value="163526">Cavity(ies)</option>
										<option ${ (vd163589=='6049') ? 'selected="true"' : '' } value="6049">Infiltrate</option>
										<option ${ (vd163589=='163527') ? 'selected="true"' : '' } value="163527">Consolidation</option>
										<option ${ (vd163589=='148187') ? 'selected="true"' : '' } value="148187">Fibrosis</option>
										<option ${ (vd163589=='163528') ? 'selected="true"' : '' } value="163528">Hyper-inflated lung</option>
										<option ${ (vd163589=='163529') ? 'selected="true"' : '' } value="163529">Collapse lung/atelactasis</option>
										<option ${ (vd163589=='126464') ? 'selected="true"' : '' } value="126464">Alveolitis Calcification</option>
										<option ${ (vd163589=='116197') ? 'selected="true"' : '' } value="116197">Mass/nodule</option>
									</select>
								</td>							
							</tr>
							<tr>
								<td>Middle zone</td>
								<td>
									<select style='width: 190px;height: 20px;' id="163587_valueDropdown" value="" name="163587_valueDropdown" size="1">
										<option value=""></option>
										<option ${ (vd163587=='163525') ? 'selected="true"' : '' } value="163525">NAD</option>
										<option ${ (vd163587=='163526') ? 'selected="true"' : '' } value="163526">Cavity(ies)</option>
										<option ${ (vd163587=='6049') ? 'selected="true"' : '' } value="6049">Infiltrate</option>
										<option ${ (vd163587=='163527') ? 'selected="true"' : '' } value="163527">Consolidation</option>
										<option ${ (vd163587=='148187') ? 'selected="true"' : '' } value="148187">Fibrosis</option>
										<option ${ (vd163587=='163528') ? 'selected="true"' : '' } value="163528">Hyper-inflated lung</option>
										<option ${ (vd163587=='163529') ? 'selected="true"' : '' } value="163529">Collapse lung/atelactasis</option>
										<option ${ (vd163587=='126464') ? 'selected="true"' : '' } value="126464">Alveolitis Calcification</option>
										<option ${ (vd163587=='116197') ? 'selected="true"' : '' } value="116197">Mass/nodule</option>
									</select>
								</td>
								<td>
									<select style='width: 190px;height: 20px;' id="163590_valueDropdown" value="" name="163590_valueDropdown" size="1">
										<option value=""></option>
										<option ${ (vd163590=='163525') ? 'selected="true"' : '' } value="163525">NAD</option>
										<option ${ (vd163590=='163526') ? 'selected="true"' : '' } value="163526">Cavity(ies)</option>
										<option ${ (vd163590=='6049') ? 'selected="true"' : '' } value="6049">Infiltrate</option>
										<option ${ (vd163590=='163527') ? 'selected="true"' : '' } value="163527">Consolidation</option>
										<option ${ (vd163590=='148187') ? 'selected="true"' : '' } value="148187">Fibrosis</option>
										<option ${ (vd163590=='163528') ? 'selected="true"' : '' } value="163528">Hyper-inflated lung</option>
										<option ${ (vd163590=='163529') ? 'selected="true"' : '' } value="163529">Collapse lung/atelactasis</option>
										<option ${ (vd163590=='126464') ? 'selected="true"' : '' } value="126464">Alveolitis Calcification</option>
										<option ${ (vd163590=='116197') ? 'selected="true"' : '' } value="116197">Mass/nodule</option>
									</select>
								</td>											
							</tr>
							<tr>
								<td>Lower zone</td>
								<td>
									<select style='width: 190px;height: 20px;' id="163588_valueDropdown" value="" name="163588_valueDropdown" size="1">
										<option value=""></option>
										<option ${ (vd163588=='163525') ? 'selected="true"' : '' } value="163525">NAD</option>
										<option ${ (vd163588=='163526') ? 'selected="true"' : '' } value="163526">Cavity(ies)</option>
										<option ${ (vd163588=='6049') ? 'selected="true"' : '' } value="6049">Infiltrate</option>
										<option ${ (vd163588=='163527') ? 'selected="true"' : '' } value="163527">Consolidation</option>
										<option ${ (vd163588=='148187') ? 'selected="true"' : '' } value="148187">Fibrosis</option>
										<option ${ (vd163588=='163528') ? 'selected="true"' : '' } value="163528">Hyper-inflated lung</option>
										<option ${ (vd163588=='163529') ? 'selected="true"' : '' } value="163529">Collapse lung/atelactasis</option>
										<option ${ (vd163588=='126464') ? 'selected="true"' : '' } value="126464">Alveolitis Calcification</option>
										<option ${ (vd163588=='116197') ? 'selected="true"' : '' } value="116197">Mass/nodule</option>
									</select>
								</td>
								<td>
									<select style='width: 190px;height: 20px;' id="163591_valueDropdown" value="" name="163591_valueDropdown" size="1">
										<option value=""></option>
										<option ${ (vd163591=='163525') ? 'selected="true"' : '' } value="163525">NAD</option>
										<option ${ (vd163591=='163526') ? 'selected="true"' : '' } value="163526">Cavity(ies)</option>
										<option ${ (vd163591=='6049') ? 'selected="true"' : '' } value="6049">Infiltrate</option>
										<option ${ (vd163591=='163527') ? 'selected="true"' : '' } value="163527">Consolidation</option>
										<option ${ (vd163591=='148187') ? 'selected="true"' : '' } value="148187">Fibrosis</option>
										<option ${ (vd163591=='163528') ? 'selected="true"' : '' } value="163528">Hyper-inflated lung</option>
										<option ${ (vd163591=='163529') ? 'selected="true"' : '' } value="163529">Collapse lung/atelactasis</option>
										<option ${ (vd163591=='126464') ? 'selected="true"' : '' } value="126464">Alveolitis Calcification</option>
										<option ${ (vd163591=='116197') ? 'selected="true"' : '' } value="116197">Mass/nodule</option>
									</select>
								</td>
							</tr>
						</table>
					</td>
					<td>
						<select style='width: 190px;height: 20px;' id="139060_valueDropdown" value="" name="139060_valueDropdown" size="1">
							<option value=""></option>
							<option ${ (vd139060=='163550') ? 'selected="true"' : '' } value="163550">COPD</option>
							<option ${ (vd139060=='130002') ? 'selected="true"' : '' } value="130002">Pneumonia/ pneumonitis</option>
							<option ${ (vd139060=='42') ? 'selected="true"' : '' } value="42">Pulmonary TB</option>
							<option ${ (vd139060=='163070') ? 'selected="true"' : '' } value="163070">Miliary TB</option>
							<option ${ (vd139060=='150586') ? 'selected="true"' : '' } value="150586">Lung abscess</option>
							<option ${ (vd139060=='162948') ? 'selected="true"' : '' } value="162948">Malignancy</option>
							<option ${ (vd139060=='128134') ? 'selected="true"' : '' } value="128134">Emphysema</option>
							<option ${ (vd139060=='152744') ? 'selected="true"' : '' } value="152744">Pleural thickening</option>
							<option ${ (vd139060=='150547') ? 'selected="true"' : '' } value="150547">Pyothorax</option>
							<option ${ (vd139060=='114108') ? 'selected="true"' : '' } value="114108">Pleural effusion</option>
							<option ${ (vd139060=='122657') ? 'selected="true"' : '' } value="122657">Pneumothorax</option>
							<option ${ (vd139060=='138361') ? 'selected="true"' : '' } value="138361">Hydropneumothorax</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Heart</td>
					<td>
						<select style='width: 190px;height: 20px;' id="149710_valueDropdown" value="" name="149710_valueDropdown" size="1">
							<option value=""></option>
							<option ${ (vd149710=='163525') ? 'selected="true"' : '' } value="163525">NAD</option>
							<option ${ (vd149710=='5158') ? 'selected="true"' : '' } value="5158">Cardiomegaly</option>
							<option ${ (vd149710=='130562') ? 'selected="true"' : '' } value="130562">Pericardial effusion</option>
							<option ${ (vd149710=='130562') ? 'selected="true"' : '' } value="130562">Tubular heart</option>
							<option ${ (vd149710=='142491') ? 'selected="true"' : '' } value="142491">Dextrocardia</option>
						</select>
					</td><td></td>
				</tr>
				<tr>
					<td>Bone</td>
					<td>
						<select style='width: 190px;height: 20px;' id="122765_valueDropdown" value="" name="122765_valueDropdown" size="1">
							<option value=""></option>
							<option ${ (vd122765=='163525') ? 'selected="true"' : '' } value="163525">NAD</option>
							<option ${ (vd122765=='163531') ? 'selected="true"' : '' } value="163531">Pott's spine (TB spine)</option>
							<option ${ (vd122765=='118055') ? 'selected="true"' : '' } value="118055">Fracture</option>
						</select>
					</td><td></td>
				</tr>
				<tr>
					<td>Mediastinum</td>
					<td>
						<select style='width: 190px;height: 20px;' id="134486_valueDropdown" value="" name="134486_valueDropdown" size="1">
							<option value=""></option>
							<option ${ (vd134486=='163525') ? 'selected="true"' : '' } value="163525">NAD</option>
							<option ${ (vd134486=='111873') ? 'selected="true"' : '' } value="111873">Lymphadenopathy</option>
							<option ${ (vd134486=='163530') ? 'selected="true"' : '' } value="163530">Mediastinum shift</option>
						</select>
					</td><td></td>
				</tr>
			</table>
		</td>
		
<% } else{ %> 

		<td><table style="text-align:right"><input style='width: 190px;height: 30px;' type="text" id="${test.conceptId}_value" name="${test.conceptId}_value" size="15" value="${test.result}"  onblur="calculateComment(${test.conceptId})"></table></td>
		<td ><input disabled type="text" size="10" value="${test.units}"/></td>
		<td><input disabled id="${test.conceptId}_range" size="10" type="text" value="${test.range}"/></td>
		<td><input disabled id="${test.conceptId}_comment" class="comment" size="15" type="text" value=""/></td>
		<% if(test.conceptId==790) {%>
			<td> Creatinine clearance  rate :  
				<input disabled id="${test.conceptId}_rate" class="comment" size="15" type="text" value=""/>
			</td>
	
	<% } %>
	</tr>
<% } %>
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
		jq("#enterLabResultForm select").each(function(){jq(this).attr("disabled","disabled");});
		
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
