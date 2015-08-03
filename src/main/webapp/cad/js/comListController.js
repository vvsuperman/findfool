/**
 * 做题控制器
 */
OJApp.controller('comListController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	
	$scope.nav = 'cad/page/cadnav.html'
	$scope.template = 'cad/page/comlist.html';
	
//	$scope.renderList = function(status, challenges) {
//		$http({
//			url: WEBROOT + '/challenge/getListByStatus',
//			method: 'POST',
//			data: {
//				'status': status
//			}
//		}).success(function(data) {
//			if (data.state == 0) {
//				console.log('1111');
//				//challenges = data.message;
//				$scope.unrunChallenges = data.message;
//			}
//		}).error(function() {
//			alert("网络错误");
//		});
//	}
	
	$http({
		url: WEBROOT + '/company/findAll',
		method: 'POST',
	}).success(function(data) {
			console.log(data);
	}).error(function() {
		alert("网络错误");
	});
	
//	$scope.renderList(1, null);
	
	
	$scope.myInterval = 5000;
	$scope.noWrapSlides = false;
    var slides = $scope.slides = [];
    
    slides.push({image:'resource/static/comlistbg.jpg'});
    slides.push({image:'resource/static/dpbg.jpg'});
}])