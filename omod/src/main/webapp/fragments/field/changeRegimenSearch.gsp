<%
	ui.includeJavascript("kenyaemr", "controllers/drugRegimenController.js")
	
	def strengths = ["","150/75/400/275 mg","150/75/275 mg","60/30/150 mg","500/125 mg","150/75 mg","60/60 mg","600 mg","500 mg","400 mg","300 mg","250 mg",
	                 "100 mg","50 mg","100 g jar","4 g sachets","500/500 mg vial","1 g vial"]
	                 
	def types = ["","tab","ml","vial","sachet","scoop"]
	
	def frequencys = ["","Once daily","Twice daily","Three times daily","Four times daily","Early morning","Night time","3 times per week","6 times per week","prn","stat"]
	
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
<tr id="row${count}">
<td class="colA" style="text-align:center"><input type="text" id="drugKey${count}" name="drugKey${count}" value="${drugOrderProcessed.drugOrder.concept.name}"></td>
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
<td class="colG" style="text-align:center"><input type="text" id="durationn${count}" name="durationn${count}"></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForContinueRegimenSearch('${drugOrderProcessed.drugOrder.concept.name}');" /></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" style="color:red" id="remove" name="remove" value="[X]" onClick="removee(${count});" /></td>
<td class="colK" style="text-align:center"><input type="hidden" id="srNumber${count}" name="srNo" value="${count}"></td>
<td class="colL" style="text-align:center"><input type="hidden" id="drugConcept${count}" name="drugConcept${count++}" value="${drugOrderProcessed.regimenConcept.conceptId}"></td>
</tr>
<% } %>

<% if(drugOrderProcessedd!=null) { %>
<% drugOrderProcessedd.each { drugOrderProcessed -> %>
<tr id="row${count}">
<td class="colA" style="text-align:center"><input type="text" id="drugKey${count}" name="drugKey${count}"></td>
<td class="colB" style="text-align:center"><select style='width: 155px;height: 30px;' id="strength${count}"  name="strength${count}">${ strengthOptions }</select></td>
<td class="colC" style="text-align:center"><input type="text" id="noOfTablet${count}" name="noOfTablet${count}"></td>
<td class="colD" style="text-align:center"><select style='width: 155px;height: 30px;' id="type${count}"  name="type${count}">${typeOptions}</select></td>
<td class="colE" style="text-align:center"><select style='width: 155px;height: 30px;' id="frequncy${count}"  name="frequncy${count}">${frequencyOptions}</select></td>
<td class="colF" style="text-align:center">
<select id="route${count}" name="route${count}" style='width: 155px;height: 30px;'>
<option value="" > </option>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
</select>
</td>
<td class="colG" style="text-align:center"><input type="text" id="durationn${count}" name="durationn${count}"></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" onClick="drugInfo('${count}');" /></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" style="color:red" id="remove" name="remove" value="[X]" onClick="removee(${count});" /></td>
<td class="colK" style="text-align:center"><input type="hidden" id="srNumber${count}" name="srNo" value="${count}"></td>
<td class="colL" style="text-align:center"><input type="hidden" id="drugConcept${count}" name="drugConcept${count++}"></td>
</tr>
<% } %>
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
<td class="colG" style="text-align:center"><input type="text" ng-model="duration" id={{choice.durationn}} name={{choice.durationn}}></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForRegimenSearchh(drugKey);" /></td>
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

jQuery('#drugKey7').val("");
jQuery('#strength7').val("");
jQuery('#noOfTablet7').val("");
jQuery('#type7').val("");
jQuery('#frequncy7').val("");
jQuery('#route7').val("");
jQuery('#drugConcept7').val("");

jQuery('#drugKey8').val("");
jQuery('#strength8').val("");
jQuery('#noOfTablet8').val("");
jQuery('#type8').val("");
jQuery('#frequncy8').val("");
jQuery('#route8').val("");
jQuery('#drugConcept8').val("");

jQuery('#drugKey9').val("");
jQuery('#strength9').val("");
jQuery('#noOfTablet9').val("");
jQuery('#type9').val("");
jQuery('#frequncy9').val("");
jQuery('#route9').val("");
jQuery('#drugConcept9').val("");

jQuery('#drugKey10').val("");
jQuery('#strength10').val("");
jQuery('#noOfTablet10').val("");
jQuery('#type10').val("");
jQuery('#frequncy10').val("");
jQuery('#route10').val("");
jQuery('#drugConcept10').val("");

jQuery('#regimenNo').val("Standard Regimen 1");

var indx = selectedSerialNoArr.indexOf(1);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }

var indx = selectedSerialNoArr.indexOf(2);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
var indx = selectedSerialNoArr.indexOf(3);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
var indx = selectedSerialNoArr.indexOf(4);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
var indx = selectedSerialNoArr.indexOf(5);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
var indx = selectedSerialNoArr.indexOf(6);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
selectedSerialNoArr.push(1);
selectedSerialNoArr.push(2);
selectedSerialNoArr.push(3);
selectedSerialNoArr.push(4);
selectedSerialNoArr.push(5);
}
else if(value=='regimen2'){
jQuery('#drugKey1').val('${regimenDetails1.drugName}');
jQuery('#strength1').val('${regimenDetails1.strength}');
jQuery('#type1').val('${regimenDetails1.formulation}');
jQuery('#frequncy1').val('${regimenDetails1.frequency}');
jQuery('#route1').val(${regimenDetails1.route});
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

jQuery('#drugKey7').val("");
jQuery('#strength7').val("");
jQuery('#noOfTablet7').val("");
jQuery('#type7').val("");
jQuery('#frequncy7').val("");
jQuery('#route7').val("");
jQuery('#drugConcept7').val("");

jQuery('#drugKey8').val("");
jQuery('#strength8').val("");
jQuery('#noOfTablet8').val("");
jQuery('#type8').val("");
jQuery('#frequncy8').val("");
jQuery('#route8').val("");
jQuery('#drugConcept8').val("");

jQuery('#drugKey9').val("");
jQuery('#strength9').val("");
jQuery('#noOfTablet9').val("");
jQuery('#type9').val("");
jQuery('#frequncy9').val("");
jQuery('#route9').val("");
jQuery('#drugConcept9').val("");

jQuery('#drugKey10').val("");
jQuery('#strength10').val("");
jQuery('#noOfTablet10').val("");
jQuery('#type10').val("");
jQuery('#frequncy10').val("");
jQuery('#route10').val("");
jQuery('#drugConcept10').val("");

jQuery('#regimenNo').val("Standard Regimen 2");

var indx = selectedSerialNoArr.indexOf(1);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }

var indx = selectedSerialNoArr.indexOf(2);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
var indx = selectedSerialNoArr.indexOf(3);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
var indx = selectedSerialNoArr.indexOf(4);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
var indx = selectedSerialNoArr.indexOf(5);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
selectedSerialNoArr.push(1);
selectedSerialNoArr.push(2);
selectedSerialNoArr.push(3);
selectedSerialNoArr.push(4);
selectedSerialNoArr.push(5);
selectedSerialNoArr.push(6);
}
}

jQuery(document).ready(function(){
//jQuery('#continueRegimenSearch').remove();
jQuery('#regimenNo').val("${regimenNo}");
for (var i = 1; i <= ${drugOrderProcesseds.size}; i++){
selectedSerialNoArr.push(i);
}
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