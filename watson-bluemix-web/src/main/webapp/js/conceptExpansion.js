var app = angular.module('conceptExpansionModule', []);

app.controller('conceptExpansionCtrl', function($scope, $http) {
	
	$scope.loading = false;
	
	$scope.seeds = 'banana\norange\nstrawberry';
	
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
    };
});