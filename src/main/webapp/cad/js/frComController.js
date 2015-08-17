/**
 * 做题控制器
 */
OJApp.controller('frComController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	
	$scope.nav = 'cad/page/cadnav.html'
		$scope.template = 'cad/page/frcom.html';
	
	
	$scope.getFrChallage=function(){
		$http({
			url : WEBROOT + "/challenge/getFrChallage",
			method : 'POST',
			data : {
			
			}
		}).success(function(data) {				
			$scope.map=data["message"];
			$scope.frQuizNaver=$scope.map.frQuizNaver;
			$scope.frQuizBegin=$scope.map.frQuizBegin;
			$scope.frQuizOver=$scope.map.frQuizOver;
		}).error(function() {
			console.log("get data failed");
		})
		
	}

	$scope.getFrChallage();

	$scope.startTest=function(quizid){
		var email=CadData.getEmail();
		if(email==null){
		    window.location.href="#/cad/login";
		}else{
			window.location.href="#/pubtesting/"+quizid;
		}
	}

	
	
	
	
	
}])