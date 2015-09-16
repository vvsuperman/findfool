OJApp.controller('randomQuiz',['$scope','$http','$routeParams','Data',function ($scope,$http,$routeParams,Data) {
  
	 $scope.Remail = Data.email();
	    $scope.company = Data.company();
	    $scope.mdUid = Data.mdUid();
	$scope.userid= Data.uid();
	console.log($scope.userid);
	$scope.findAll = function() {
		$http({
			url : WEBROOT + "/test/findSet",
			method : 'POST',
			data : {"userid":$scope.userid}
		}).success(function(data) {
			$scope.setList = data["message"];

			console.log("执行了查询全部");

		})
	};
	$scope.findAll();
    
	
    
}]);

