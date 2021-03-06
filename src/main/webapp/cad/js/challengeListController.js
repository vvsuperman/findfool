/**
 * 做题控制器
 */
OJApp.controller('challengeListController', [ '$scope', '$http', 'CadData',
		function($scope, $http, CadData) {

			$scope.nav = 'cad/page/cadnav.html'
			$scope.template = 'cad/page/comlist.html';

			$scope.renderList = function(status) {
				$http({
					url : WEBROOT + '/challenge/getListByStatus',
					method : 'POST',
					headers : {
						"Authorization" : CadData.getTestToken()
					},
					data : {
						'status' : status
					}
				}).success(function(data) {
					if (data.state == 0) {
						// console.log('1111');
						// challenges = data.message;
						switch (status) {
						case 1:
							$scope.unrunChallenges = data.message;
							break;
						case 2:
							$scope.runningChallenges = data.message;
							break;
						default:
							break;
						}

					}
				}).error(function() {
					alert("网络错误");
				});
			}

			$scope.renderList(1, $scope.unrunChallenges);
			$scope.renderList(2, $scope.runningChallenges);

			$scope.myInterval = 5000;
			$scope.noWrapSlides = false;
			var slides = $scope.slides = [];

			slides.push({
				image : 'resource/static/comlistbg.jpg'
			});
			slides.push({
				image : 'resource/static/dpbg.jpg'
			});

		} ])