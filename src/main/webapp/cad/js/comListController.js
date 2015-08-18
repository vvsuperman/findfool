/**
 * 做题控制器
 */
OJApp.controller('comListController',['$scope','$http','CadData','Data',function ($scope,$http,CadData,Data) {

    	$scope.nav = 'cad/page/cadnav.html'
		$scope.template = 'cad/page/comlist.html';
		
		$scope.comListByType=function(){
			$http({
				url : WEBROOT + "/company/comListByType",
				method : 'POST',
				data : {
				
				}
			}).success(function(data) {
				$scope.companyList=data["message"];
			}).error(function() {
				console.log("get data failed");
			})
			
		}
		
		
		$scope.comListAll=function(){
			$http({
				url : WEBROOT + "/company/findAll",
				method : 'POST',
				data : {
				
				}
			}).success(function(data) {
				$scope.companyList=data["message"];
			}).error(function() {
				console.log("get data failed");
			})
			
		}

		$scope.comListByType();
		//$scope.comListAll();

		
		$scope.showCompanyTest=function(company){
			var url = "#/cad/eachcom/"+company.id;
			 window.location.href=url;
		}
		
		
		
		
//		$scope.renderList = function(status, challenges) {
//			$http({
//				url: WEBROOT + '/challenge/getListByStatus',
//				method: 'POST',
//				data: {
//					'status': status
//				}
//			}).success(function(data) {
//				if (data.state == 0) {
//					console.log('1111');
//					//challenges = data.message;
//					$scope.unrunChallenges = data.message;
//				}
//			}).error(function() {
//				alert("网络错误");
//			});
//		}
//		
//		$scope.renderList(1, null);
	
	
	$scope.myInterval = 5000;
	$scope.noWrapSlides = false;
    var slides = $scope.slides = [];
    
    slides.push({image:'resource/static/comlistbg.jpg'});
    slides.push({image:'resource/static/dpbg.jpg'});

	

	
}])
