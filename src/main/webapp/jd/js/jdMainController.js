OJApp.controller('jdMainController',['$scope','$http','Data',function ($scope,$http,Data) {
		
	Data.setUserSource("jd");
	
	$scope.jdLogin = function(){
		window.location.href = ROOT+'/login';
	}
	
	$scope.jdSignup = function(){
		window.location.href = ROOT+'/signup';
	}
	
}])