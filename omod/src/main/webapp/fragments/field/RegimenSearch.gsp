<%
	ui.includeJavascript("kenyaemr", "controllers/drugRegimenController.js")
	
	def strengths = ["150/75/400/275 mg","150/75/275 mg","60/30/150 mg","500/125 mg","150/75 mg","60/60 mg","600 mg","500 mg","400 mg","300 mg","250 mg",
	                 "100 mg","50 mg","100 g jar","4 g sachets","500/500 mg Vial","1 g vial"]
	                 
	def types = ["tab","ml","vial","sachet","scoop"]
	
	def frequencys = ["Once daily","Twice daily","Three times daily","Four times daily","Early morning","Night time","3 times per week","6 times per week","prn","stat"]
	
	def strengthOptions = strengths.collect( { """<option value="${ it }">${ it }</option>""" } ).join()
	
	def typeOptions = types.collect( { """<option value="${ it }">${ it }</option>""" } ).join()
	
	def frequencyOptions = frequencys.collect( { """<option value="${ it }">${ it }</option>""" } ).join()

%>

<div ng-controller="DrugCtrl" data-ng-init="init()">

<table>
<tbody>
<tr>
<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="guide" name="guide" value="Guide" onClick="guidee();" /></td>
<!-- <td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="artRegimen" name="artRegimen" value="TB Regimen" onClick="artRegimenn();" /></td> -->
</tr>

<tr>
<td colspan="3"><input type="radio" id="regimen1" name="regimen" value="regimen1" onClick="regimenSelection(this.value);" />6(Amk Z Lfx Eto Cs)/14(Lfx Eto Cs Z)</td>
</tr>
<tr>
<td colspan="3"><input type="radio" id="regimen2" name="regimen" value="regimen2" onClick="regimenSelection(this.value);" />6(Amk Z Lfx Eto Cs PAS)/14(Lfx Eto Cs Z PAS)</td>
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
<tr>
<td class="colA" style="text-align:center"><input type="text" id="drugKey1" name="drugKey1"></td>
<td class="colB" style="text-align:center"><select style='width: 155px;height: 30px;' id="strength1"  name="strength1"><option value="" />${ strengthOptions }</select></td>
<td class="colC" style="text-align:center"><input type="text" id="noOfTablet1" name="noOfTablet1"></td>
<td class="colD" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="type" id="type1" name="type1">${typeOptions}</select></td>
<td class="colE" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="frequncy" id="frequncy1" name="frequncy1" >${ frequencyOptions }</select></td>
<td class="colF" style="text-align:center">
<select ng-model="route" id="route1" name="route1" style='width: 155px;height: 30px;'>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
</select>
</td>
<td class="colG" style="text-align:center"><input type="text" id="duration1" name="duration1"></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForRegimenSearch(drugKey);" /></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"></td>
<td class="colK" style="text-align:center"><input type="hidden" id="srNumber1" name="srNo" value="1"></td>
<td class="colL" style="text-align:center"><input type="hidden" id="drugConcept1" name="drugConcept1"></td>
</tr>

<tr>
<td class="colA" style="text-align:center"><input type="text" id="drugKey2" name="drugKey2"></td>
<td class="colB" style="text-align:center"><select style='width: 155px;height: 30px;' id="strength2"  name="strength2"><option value="" />${ strengthOptions }</select></td>
<td class="colC" style="text-align:center"><input type="text" id="noOfTablet2" name="noOfTablet2"></td>
<td class="colD" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="type" id="type2" name="type2">${typeOptions}</select></td>
<td class="colE" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="frequncy" id="frequncy2" name="frequncy2" >${ frequencyOptions }</select></td>
<td class="colF" style="text-align:center">
<select ng-model="route" id="route2" name="route2" style='width: 155px;height: 30px;'>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
</select>
</td>
<td class="colG" style="text-align:center"><input type="text" id="duration2" name="duration2"></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForRegimenSearch(drugKey);" /></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"></td>
<td class="colK" style="text-align:center"><input type="hidden" id="srNumber2" name="srNo" value="2"></td>
<td class="colL" style="text-align:center"><input type="hidden" id="drugConcept2" name="drugConcept2"></td>
</tr>

<tr>
<td class="colA" style="text-align:center"><input type="text" id="drugKey3" name="drugKey3"></td>
<td class="colB" style="text-align:center"><select style='width: 155px;height: 30px;' id="strength3"  name="strength3"><option value="" />${ strengthOptions }</select></td>
<td class="colC" style="text-align:center"><input type="text" id="noOfTablet3" name="noOfTablet3"></td>
<td class="colD" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="type" id="type3" name="type3">${typeOptions}</select></td>
<td class="colE" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="frequncy" id="frequncy3" name="frequncy3" >${ frequencyOptions }</select></td>
<td class="colF" style="text-align:center">
<select ng-model="route" id="route3" name="route3" style='width: 155px;height: 30px;'>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
</select>
</td>
<td class="colG" style="text-align:center"><input type="text" id="duration3" name="duration3"></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForRegimenSearch(drugKey);" /></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"></td>
<td class="colK" style="text-align:center"><input type="hidden" id="srNumber3" name="srNo" value="3"></td>
<td class="colL" style="text-align:center"><input type="hidden" id="drugConcept3" name="drugConcept3"></td>
</tr>

<tr>
<td class="colA" style="text-align:center"><input type="text" id="drugKey4" name="drugKey4"></td>
<td class="colB" style="text-align:center"><select style='width: 155px;height: 30px;' id="strength4"  name="strength4"><option value="" />${ strengthOptions }</select></td>
<td class="colC" style="text-align:center"><input type="text" id="noOfTablet4" name="noOfTablet4"></td>
<td class="colD" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="type" id="type4" name="type4">${typeOptions}</select></td>
<td class="colE" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="frequncy" id="frequncy4" name="frequncy4" >${ frequencyOptions }</select></td>
<td class="colF" style="text-align:center">
<select ng-model="route" id="route4" name="route4" style='width: 155px;height: 30px;'>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
</select>
</td>
<td class="colG" style="text-align:center"><input type="text" id="duration4" name="duration4"></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForRegimenSearch(drugKey);" /></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"></td>
<td class="colK" style="text-align:center"><input type="hidden" id="srNumber4" name="srNo" value="4"></td>
<td class="colL" style="text-align:center"><input type="hidden" id="drugConcept4" name="drugConcept4"></td>
</tr>

<tr>
<td class="colA" style="text-align:center"><input type="text" id="drugKey5" name="drugKey5"></td>
<td class="colB" style="text-align:center"><select style='width: 155px;height: 30px;' id="strength5"  name="strength5"><option value="" />${ strengthOptions }</select></td>
<td class="colC" style="text-align:center"><input type="text" id="noOfTablet5" name="noOfTablet5"></td>
<td class="colD" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="type" id="type5" name="type5">${typeOptions}</select></td>
<td class="colE" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="frequncy" id="frequncy5" name="frequncy5" >${ frequencyOptions }</select></td>
<td class="colF" style="text-align:center">
<select ng-model="route" id="route5" name="route5" style='width: 155px;height: 30px;'>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
</select>
</td>
<td class="colG" style="text-align:center"><input type="text" id="duration5" name="duration5"></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForRegimenSearch(drugKey);" /></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"></td>
<td class="colK" style="text-align:center"><input type="hidden" id="srNumber4" name="srNo" value="5"></td>
<td class="colL" style="text-align:center"><input type="hidden" id="drugConcept5" name="drugConcept5"></td>
</tr>
</tbody>
</table>

<fieldset  data-ng-repeat="choice in choices">
<table>
<tbody>
<tr>
<td class="colA" style="text-align:center"><input type="text" ng-model="drugKey" id={{choice.drugKey}} name={{choice.drugKey}} placeholder="search box" uib-typeahead="drug as drug.drugName for drug in myDrug | filter : drugKey" typeahead-on-select="drugSearch(drugKey,choice);"></td>
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

</div>

<script type="text/javascript">
var patientId=${patient.patientId};

function guidee(){
jQuery('#guideDiv').empty();
var age=${patient.age};
if(age>14){
var htmlText =  "<img src='${ ui.resourceLink('kenyaui', 'images/glyphs/flow_chart_adult.jpg') }' />"
var newElement = document.createElement('div');
newElement.setAttribute("id", "guideDivAdult"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('guideDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=750&width=820&inlineId=guideDivAdult";
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

function artRegimenn(){
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

function regimenSelection(value){

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
jQuery('#strength3').val('${regimenDetails3.strength}');
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
jQuery('#type6').val("");
jQuery('#frequncy6').val("");
jQuery('#route6').val("");
jQuery('#drugConcept6').val("");
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
jQuery('#strength3').val('${regimenDetails3.strength}');
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
}

}
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

