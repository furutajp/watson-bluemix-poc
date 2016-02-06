var app = angular.module('conceptExpansionModule', []);

app.controller('conceptExpansionCtrl', function($scope, $http) {
	
	$scope.submit = function() {
		$http.post("/evaluate", $scope.seeds)
	     	 .then(function(response) {
	     		 		$scope.concepts = response.data;
	     	 	   });
    };
});