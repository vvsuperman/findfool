/**
 * 做题控制器
 */
OJApp.controller('frComController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	
	
	$scope.getFrChallage=function(){
		$http({
			url : WEBROOT + "/challenge/getFrChallage",
			method : 'POST',
			data : {
			
			}
		}).success(function(data) {
//			$scope.companyTail = data["message"];
			$scope.frQuizList=data["message"];
			
			                      
		}).error(function() {
			console.log("get data failed");
		})
		
	}

	$scope.getFrChallage();

	

	
	
	
	
	
}])