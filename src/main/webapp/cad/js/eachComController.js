/**
 * 做题控制器
 */
OJApp.controller('eachComController', [ '$scope', '$http', 'Data',
		function($scope, $http, Data) {
			$scope.renderList = function(status) {
				$http({
					url: WEBROOT + "/challenge/getListByStatus",
					method: 'POST',
					headers: {
						"Authorization" : Data.token()
					},
					data : {
						"status" : status
					}
				}).success(function(data) {
					
				}).error(function() {
					alert("网络错误");
				});
			}
		} ])