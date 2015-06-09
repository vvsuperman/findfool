/**
 * 
 */

OJApp.controller('dpMainController',function ($scope,$http,Data) {
	$scope.register = function(){
		window.location.href='#/dp/register';
	}
	
	$scope.login = function(){
		window.location.href='#/dp/login';
	}
	 
});