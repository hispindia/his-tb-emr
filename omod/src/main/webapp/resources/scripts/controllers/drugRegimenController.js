/*
kenyaemrApp.controller('DrugCtrl', ['$scope', function($scope) {

$scope.drugSearch = function(drugKey){
	if(drugKey.length>2){
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/drugConcept.action',{ patientId: patientId,drugKey: drugKey})
	    .done(function(data) {
	    	$scope.$apply(function(){ 
	    		$scope.myDrug = data.drugConceptName;
		    	
			});
	    	
	    });
	 }
  }

}]);
*/

kenyaemrApp.controller('DrugCtrl', ['$scope', function($scope) {
	
	$scope.choices = [{srNo:'11',srNumber:'srNumber11',id:'choice11',drugKey:'drugKey11',drugConcept:'drugConcept11',strength:'strength11',noOfTablet:'noOfTablet11',route:'route11',type: 'type11',frequncy: 'frequncy11',duration:'duration11'}];
	$scope.addNewChoice = function() {
		var newItemNo = $scope.choices.length+1;
		$scope.choices.push({srNo:newItemNo,srNumber:'srNumber'+newItemNo,id:'choice'+newItemNo,drugKey:'drugKey'+newItemNo,drugConcept:'drugConcept'+newItemNo,strength:'strength'+newItemNo,noOfTablet:'noOfTablet'+newItemNo,route:'route'+newItemNo,type: 'type'+newItemNo,frequncy:'frequncy'+newItemNo,duration:'duration'+newItemNo});
	}
	
	$scope.removeChoice = function(index) {
	    $scope.choices.splice(index,1);
	    /*
	    var newItemNo = $scope.choices.length;
	    $scope.choices=[];
	    for(var i=1;i<=newItemNo;i++){
	    	$scope.choices.push({srNo:i,srNumber:'srNumber'+i,id:'choice'+i,drugKey:'drugKey'+i,strength:'strength'+i,noOfTablet:'noOfTablet'+i,type: 'type'+i,frequncy:'frequncy'+i,duration:'duration'+i});
	    }*/
	}
	
	$scope.drugSearch = function(drugKey,choice){
	//var drugKey="drugKey"+count.toString();
	//$scope.strength = $scope[drugKey].strength;
	//$scope.strength = $scope.drugKey.strength;
	var srNo=choice.srNo;
	jQuery('#continueRegimenSearch').empty();
	$('#strength'+srNo).val(drugKey.strength);
	$('#noOfTablet'+srNo).val(drugKey.noOfTablet);
	$('#type'+srNo).val(drugKey.type);
	$('#frequncy'+srNo).val(drugKey.frequency);
	$('#route'+srNo).val(drugKey.route);
	$('#drugConcept'+srNo).val(drugKey.drugConcept);
	}
	
	$scope.artDrugInfoForRegimenSearch=function(drugKey){
		var drugName=drugKey.drugName;
		jQuery('#drugInfoDiv').empty();
		jQuery.ajax(ui.fragmentActionLink("kenyaemr", "field/drugInfo", "drugDetails"), { data: { drugNames: drugName }, dataType: 'json'
		}).done(function(data) {
        var htmlText =  "<table style='width: 100%'>"
        +"<tr>"
        +"<th>"
        +"Drug Code&nbsp;"
        +"</th>"
        +"<th>"
        +'Adverse Effect&nbsp;'
        +"</th>"
        +"</tr>"

        $.each(data, function(i, item){
            $.each(this,function(j) {
        
            	htmlText=htmlText
            	 +"<tr>"
            	 +"<td>"
                 +this.drugCode
                 +"</td>"
                 +"<td>"
                 +this.adverseEffect
                 +"</td>"
                 +"</tr>"
            });
        });
		htmlText=htmlText
		 +"</table>"
       var newElement = document.createElement('div');
      newElement.setAttribute("id", "drugDiv"); 
      newElement.innerHTML = htmlText;
      var fieldsArea = document.getElementById('drugInfoDiv');
      fieldsArea.appendChild(newElement);
      var url = "#TB_inline?height=500&width=750&inlineId=drugDiv";
      tb_show("Drug Info",url,false);
      });
	}
	
	$scope.artDrugInfoForContinueRegimenSearch=function(drugName){
		jQuery('#drugInfoDiv').empty();
		jQuery.ajax(ui.fragmentActionLink("kenyaemr", "field/drugInfo", "drugDetails"), { data: { drugNames: drugName }, dataType: 'json'
		}).done(function(data) {
        var htmlText =  "<table style='width: 100%'>"
        +"<tr>"
        +"<th>"
        +"Drug Code&nbsp;"
        +"</th>"
        +"<th>"
        +'Adverse Effect&nbsp;'
        +"</th>"
        +"</tr>"

        $.each(data, function(i, item){
            $.each(this,function(j) {
        
            	htmlText=htmlText
            	 +"<tr>"
            	 +"<td>"
                 +this.drugCode
                 +"</td>"
                 +"<td>"
                 +this.adverseEffect
                 +"</td>"
                 +"</tr>"
            });
        });
		htmlText=htmlText
		 +"</table>"
       var newElement = document.createElement('div');
      newElement.setAttribute("id", "drugDiv"); 
      newElement.innerHTML = htmlText;
      var fieldsArea = document.getElementById('drugInfoDiv');
      fieldsArea.appendChild(newElement);
      var url = "#TB_inline?height=500&width=750&inlineId=drugDiv";
      tb_show("Drug Info",url,false);
      });
	}
	
	$scope.init = function(){
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/drugConcept.action',{ patientId: patientId})
	    .done(function(data) {
	    	$scope.$apply(function(){ 
	    		$scope.myDrug = data.drugConceptName;
	    		$scope.drugKey1 = data.drugConceptName1;
	    		$scope.drugKey2 = data.drugConceptName2;
	    		$scope.drugKey3 = data.drugConceptName3;
	    		$scope.drugKey4 = data.drugConceptName4;
	    		$scope.drugKey5 = data.drugConceptName5;
	    		$scope.drugKey6 = data.drugConceptName6;
			});
	    	
	     });
	 }

}]);