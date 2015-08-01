/**
 * 做题控制器
 */
OJApp.controller('challengeListController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	$scope.renderList = function(status, challenges) {
		$http({
			url: WEBROOT + '/challenge/getListByStatus',
			method: 'POST',
			data: {
				'status': status
			}
		}).success(function(data) {
			if (data.state == 0) {
				console.log('1111');
				challenges = data.message;
				//$scope.unrunChallenges = data.message;
			}
		}).error(function() {
			alert("网络错误");
		});
	}
	
	$scope.renderList(1, $scope.unrunChallenges);
	$scope.renderList(2, $scope.runningChallenges);
}])