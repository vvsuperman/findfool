OJApp.controller('challengeInfoController', ['$scope','$http','Data','$routeParams','$window',function($scope,$http,Data,$routeParams,$window) {
	$scope.getCompanyInfo = function() {
		console.log('$scope.getCompanyInfo');
		console.log($routeParams.signedkey);
	}
	
	$scope.getCompanyInfo();
}]);