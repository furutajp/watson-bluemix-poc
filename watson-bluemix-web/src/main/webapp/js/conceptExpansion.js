var app = angular.module('conceptExpansionModule', []);

app.controller('conceptExpansionCtrl', function($scope, $http) {
	
	$scope.init = function() {
		$scope.loading = false;
		$scope.seeds = 'banana\norange\nstrawberry';
		$scope.listAll();
	};
	
	$scope.listAll = function() {
		$http.get("/listAll")
			 .then(function(response) {
				 		$scope.latestSeeds = response.data;
	    });
	};
	
	$scope.submit = function() {
		
		if (!$scope.conceptExpansionForm.$valid) {
			return;
		}
		
		$scope.loading = true;
		
		$http.post("/evaluate", $scope.seeds)
	     	 .then(function(response) {
	     		 		$scope.concepts = response.data;
	     		 		$scope.loading = false;
 	    });
		
		$scope.listAll();
    };

    $scope.init();
    
});