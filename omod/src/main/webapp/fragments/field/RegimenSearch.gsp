<%
	ui.includeJavascript("kenyaemr", "controllers/drugRegimenController.js")
	
	def strengths = ["150/75/400/275 mg","150/75/275 mg","60/30/150 mg","500/125 mg","150/75 mg","60/60 mg","600 mg","500 mg","400 mg","300 mg","250 mg",
	                 "100 mg","50 mg","100 g jar","4 g sachets","500/500 mg Vial","1 g Vial"]
	                 
	def types = ["tab","ml","vial","sachet"]
	
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
<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="artRegimen" name="artRegimen" value="TB Regimen" onClick="artRegimenn();" /></td>
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

