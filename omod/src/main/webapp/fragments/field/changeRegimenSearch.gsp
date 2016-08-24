<%
	ui.includeJavascript("kenyaemr", "controllers/drugRegimenController.js")
	
	def strengths = ["150/75/400/275 mg","150/75/275 mg","60/30/150 mg","500/125 mg","150/75 mg","60/60 mg","600 mg","500 mg","400 mg","300 mg","250 mg",
	                 "100 mg","50 mg","100 g jar","4 g sachets","500/500 mg vial","1 g vial"]
	                 
	def types = ["tab","ml","vial","sachet","scoop"]
	
	def frequencys = ["Once daily","Twice daily","Three times daily","Four times daily","Early morning","Night time","3 times per week","6 times per week","prn","stat"]
	
	def strengthOptions = strengths.collect( { """<option value="${ it }">${ it }</option>""" } ).join()
	
	def typeOptions = types.collect( { """<option value="${ it }">${ it }</option>""" } ).join()
	
	def frequencyOptions = frequencys.collect( { """<option value="${ it }">${ it }</option>""" } ).join()

%>

<div id="changeRegimenSearch" ng-controller="DrugCtrl" data-ng-init="init()">

<table>
<tbody>
<tr>
<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="guide" name="guide" value="Guide" onClick="guideForSubstRegimen();" /></td>
<!-- <td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="artRegimen" name="artRegimen" value="TB Regimen" onClick="artForSubstRegimen();" /></td> -->
</tr>

<tr>
<td colspan="3"><input type="radio" id="regimen1" name="regimen" value="regimen1" onClick="regimenSelectionForChange(this.value);" />6(Amk Z Lfx Eto Cs)/14(Lfx Eto Cs Z)</td>
</tr>
<tr>
<td colspan="3"><input type="radio" id="regimen2" name="regimen" value="regimen2" onClick="regimenSelectionForChange(this.value);" />6(Amk Z Lfx Eto Cs PAS)/14(Lfx Eto Cs Z PAS)</td>
</tr>

<tr>
<td class="colA" style="text-align:center">Drug</td>
<td class="colB" style="text-align:center">Strength per unit</td>
<td class="colC" style="text-align:center">Quantity of Unit</td>
<td class="colD" style="text-align:center">Accounting unit</td>
<td class="colE" style="text-align:center">Frequency</td>
<td class="colF" style="text-align:center">Route</td>
<td class="colG" style="text-align:center">Duration(in days)</td>
<td class="colH" style="text-align:center"></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"></td>
<td class="colK" style="text-align:center"></td>
</tr>
</tbody>
</table>

<table>
<tbody>
<% drugOrderProcesseds.each { drugOrderProcessed -> %>
<tr>
<td class="colA" style="text-align:center"><input type="text" ng-model="drugKey${count}" id="drugKey${count}" name="drugKey${count}" value="${drugOrderProcessed.drugOrder.concept.name}" uib-typeahead="drug as drug.drugName for drug in myDrug | filter : drugKey${count}" typeahead-on-select="drugSearchForRegimenChange(drugKey${count},${count});"></td>
<td class="colB" style="text-align:center"><select style='width: 155px;height: 30px;' id="strength${count}"  name="strength${count}"><option value="${drugOrderProcessed.dose}" > ${drugOrderProcessed.dose}</option>${ strengthOptions }</select></td>
<td class="colC" style="text-align:center"><input type="text" id="noOfTablet${count}" name="noOfTablet${count}" value="${drugOrderProcessed.noOfTablet}"></td>
<td class="colD" style="text-align:center"><select style='width: 155px;height: 30px;' id="type${count}"  name="type${count}"><option value="${drugOrderProcessed.drugOrder.units}" > ${drugOrderProcessed.drugOrder.units}</option>${typeOptions}</select></td>
<td class="colE" style="text-align:center"><select style='width: 155px;height: 30px;' id="frequncy${count}"  name="frequncy${count}"><option value="${drugOrderProcessed.drugOrder.frequency}" > ${drugOrderProcessed.drugOrder.frequency}</option>${frequencyOptions}</select></td>
<td class="colF" style="text-align:center">
<select id="route${count}" name="route${count}" style='width: 155px;height: 30px;'>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
<option value="${drugOrderProcessed.route.name}" > ${drugOrderProcessed.route.name}</option>
</select>
</td>
<td class="colG" style="text-align:center"><input type="text" id="duration${count}" name="duration${count}"></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForContinueRegimenSearch('${drugOrderProcessed.drugOrder.concept.name}');" /></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"></td>
<td class="colK" style="text-align:center"><input type="hidden" id="srNumber${count}" name="srNo" value="${count}"></td>
<td class="colL" style="text-align:center"><input type="hidden" id="drugConcept${count}" name="drugConcept${count++}" value="${drugOrderProcessed.regimenConcept.conceptId}"></td>
</tr>
<% } %>
</tbody>
</table>

<fieldset  data-ng-repeat="choice in choices">
<table>
<tbody>
<tr>
<td class="colA" style="text-align:center"><input type="text" ng-model="drugKey" id={{choice.drugKey}} name={{choice.drugKey}} placeholder="search box" uib-typeahead="drug as drug.drugName for drug in myDrug | filter : drugKey" typeahead-on-select="drugSearch(drugKey,choice);" value={{drugKey}}></td>
<td class="colB" style="text-align:center"><select style='width: 155px;height: 30px;' id={{choice.strength}}  name={{choice.strength}}><option value="" />${ strengthOptions }</select></td>
<td class="colC" style="text-align:center"><input type="text" ng-model="noOfTablet" id={{choice.noOfTablet}} name={{choice.noOfTablet}}></td>
<td class="colD" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="type" id={{choice.type}} name={{choice.type}}>${typeOptions}</select></td>
<td class="colE" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="frequncy" id={{choice.frequncy}} name={{choice.frequncy}} >${ frequencyOptions }</select></td>
<td class="colF" style="text-align:center">
<select ng-model="route" id={{choice.route}} name={{choice.route}} style='width: 155px;height: 30px;'>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
</select>
</td>
<td class="colG" style="text-align:center"><input type="text" ng-model="duration" id={{choice.duration}} name={{choice.duration}}></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForRegimenSearch(drugKey);" /></td>
<td class="colI" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="add" name="add" value="Add" ng-click="addNewChoice()"/></td>
<td class="colJ" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" style="color:red" id="remove" name="remove" value="[X]" ng-click="removeChoice(choice)" /></td>
<td class="colK" style="text-align:center"><input type="hidden" id={{choice.srNumber}} name="srNo" value={{choice.srNo}}></td>
<td class="colL" style="text-align:center"><input type="hidden" id={{choice.drugConcept}} name={{choice.drugConcept}} value={{choice.drugConcept}}></td>
</tr>
</tbody>
</table>
</fieldset>

<table>
<tbody>
<tr>
<td class="colA" style="text-align:center"><input type="hidden" id="regimenNo" name="regimenNo"></td>
</tr>
</tbody>
</table>

</div>

<script type="text/javascript">
var patientId=${patient.patientId};

function guideForSubstRegimen(){
jQuery('#guideDiv').empty();
var age=${patient.age};
if(age>14){
var htmlText =  "<img src='${ ui.resourceLink('kenyaui', 'images/glyphs/flow_chart_adult.jpg') }' />"
var newElement = document.createElement('div');
newElement.setAttribute("id", "guideDivAdult"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('guideDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=500&width=820&inlineId=guideDivAdult";
tb_show("Guide",url,false);
}
else{
var htmlText =  "<img src='${ ui.resourceLink('kenyaui', 'images/glyphs/flow_chart_child.jpg') }' />"
var newElement = document.createElement('div');
newElement.setAttribute("id", "guideDivChild"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('guideDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=500&width=840&inlineId=guideDivChild";
tb_show("Guide",url,false);
}
}

function artForSubstRegimen(){
jQuery('#artRegimenDiv').empty();
var age=${patient.age};
var htmlText =  "<img src='${ ui.resourceLink('kenyaui', 'images/glyphs/tbRegimen.jpg') }' />"
var newElement = document.createElement('div');
newElement.setAttribute("id", "artRegimenDivAdult"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('artRegimenDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=500&width=1110&inlineId=artRegimenDivAdult";
tb_show("Art Regimen",url,false);
}

function regimenSelectionForChange(value){
jQuery('#continueRegimenSearch').empty();

if(value=='regimen1'){
jQuery('#drugKey1').val('${regimenDetails1.drugName}');
jQuery('#strength1').val('${regimenDetails1.strength}');
jQuery('#type1').val('${regimenDetails1.formulation}');
jQuery('#frequncy1').val('${regimenDetails1.frequency}');
jQuery('#route1').val('${regimenDetails1.route}');
jQuery('#drugConcept1').val('${regimenDetails1.drugConcept}');

jQuery('#drugKey2').val('${regimenDetails2.drugName}');
jQuery('#strength2').val('${regimenDetails2.strength}');
jQuery('#type2').val('${regimenDetails2.formulation}');
jQuery('#frequncy2').val('${regimenDetails2.frequency}');
jQuery('#route2').val('${regimenDetails2.route}');
jQuery('#drugConcept2').val('${regimenDetails2.drugConcept}');

jQuery('#drugKey3').val('${regimenDetails3.drugName}');
jQuery('#strength3').val('250 mg');
jQuery('#type3').val('${regimenDetails3.formulation}');
jQuery('#frequncy3').val('${regimenDetails3.frequency}');
jQuery('#route3').val('${regimenDetails3.route}');
jQuery('#drugConcept3').val('${regimenDetails3.drugConcept}');

jQuery('#drugKey4').val('${regimenDetails4.drugName}');
jQuery('#strength4').val('${regimenDetails4.strength}');
jQuery('#type4').val('${regimenDetails4.formulation}');
jQuery('#frequncy4').val('${regimenDetails4.frequency}');
jQuery('#route4').val('${regimenDetails4.route}');
jQuery('#drugConcept4').val('${regimenDetails4.drugConcept}');

jQuery('#drugKey5').val('${regimenDetails5.drugName}');
jQuery('#strength5').val('${regimenDetails5.strength}');
jQuery('#type5').val('${regimenDetails5.formulation}');
jQuery('#frequncy5').val('${regimenDetails5.frequency}');
jQuery('#route5').val('${regimenDetails5.route}');
jQuery('#drugConcept5').val('${regimenDetails5.drugConcept}');

jQuery('#drugKey6').val("");
jQuery('#strength6').val("");
jQuery('#noOfTablet6').val("");
jQuery('#type6').val("");
jQuery('#frequncy6').val("");
jQuery('#route6').val("");
jQuery('#drugConcept6').val("");

jQuery('#regimenNo').val("Standard Regimen 1");
}
else{
jQuery('#drugKey1').val('${regimenDetails1.drugName}');
jQuery('#strength1').val('${regimenDetails1.strength}');
jQuery('#type1').val('${regimenDetails1.formulation}');
jQuery('#frequncy1').val('${regimenDetails1.frequency}');
jQuery('#route1').val('${regimenDetails1.route}');
jQuery('#drugConcept1').val('${regimenDetails1.drugConcept}');

jQuery('#drugKey2').val('${regimenDetails2.drugName}');
jQuery('#strength2').val('${regimenDetails2.strength}');
jQuery('#type2').val('${regimenDetails2.formulation}');
jQuery('#frequncy2').val('${regimenDetails2.frequency}');
jQuery('#route2').val('${regimenDetails2.route}');
jQuery('#drugConcept2').val('${regimenDetails2.drugConcept}');

jQuery('#drugKey3').val('${regimenDetails3.drugName}');
jQuery('#strength3').val('500 mg');
jQuery('#type3').val('${regimenDetails3.formulation}');
jQuery('#frequncy3').val('${regimenDetails3.frequency}');
jQuery('#route3').val('${regimenDetails3.route}');
jQuery('#drugConcept3').val('${regimenDetails3.drugConcept}');

jQuery('#drugKey4').val('${regimenDetails4.drugName}');
jQuery('#strength4').val('${regimenDetails4.strength}');
jQuery('#type4').val('${regimenDetails4.formulation}');
jQuery('#frequncy4').val('${regimenDetails4.frequency}');
jQuery('#route4').val('${regimenDetails4.route}');
jQuery('#drugConcept4').val('${regimenDetails4.drugConcept}');

jQuery('#drugKey5').val('${regimenDetails5.drugName}');
jQuery('#strength5').val('${regimenDetails5.strength}');
jQuery('#type5').val('${regimenDetails5.formulation}');
jQuery('#frequncy5').val('${regimenDetails5.frequency}');
jQuery('#route5').val('${regimenDetails5.route}');
jQuery('#drugConcept5').val('${regimenDetails5.drugConcept}');

jQuery('#drugKey6').val('${regimenDetails6.drugName}');
jQuery('#strength6').val('${regimenDetails6.strength}');
jQuery('#type6').val('${regimenDetails6.formulation}');
jQuery('#frequncy6').val('${regimenDetails6.frequency}');
jQuery('#route6').val('${regimenDetails6.route}');
jQuery('#drugConcept6').val('${regimenDetails6.drugConcept}');

jQuery('#regimenNo').val("Standard Regimen 2");
}

}

jQuery(document).ready(function(){
jQuery('#regimenNo').val("${regimenNo}");
});
</script>

<style type="text/css">
  table { width: 100%; }
  td.colA { width: 10%; }
  td.colB { width: 10%; }
  td.colC { width: 10%; }
  td.colD { width: 10%; }
  td.colE { width: 10%; }
  td.colF { width: 10%; }
  td.colG { width: 5%; }
  td.colH { width: 5%; }
  td.colI { width: 5%; }
  td.colJ { width: 5%; }
  td.colK { width: 5%; }
</style>