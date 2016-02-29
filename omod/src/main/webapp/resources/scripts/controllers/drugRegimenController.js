

/**
kenyaemrApp.controller('DrugCtrl', ['$scope', function($scope) {
	$scope.drugSelection = function(myDrug) {
		alert(myDrug);
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/drugConcept.action',{listOfDrug:myDrug})
		.done(function(data) {
			$scope.$apply(function(){ 
				$scope.myDrug = data.drugConceptName;
				
			});
	    });
	};
	
	$scope.init = function(){
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/drugRegimen.action')
	    .done(function(data) {
	    	$scope.$apply(function(){ 
	    		$scope.drugs = data.drugName;
			});
	    	
	    });
	}
}]);

*/