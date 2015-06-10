/**
 * 
 */

OJApp.controller('dpMainController',function ($scope,$http,Data) {
	$scope.tuser={};
	
	$scope.register = function(){
		window.location.href='#/dp/register';
	}
	
	$scope.login = function(){
		window.location.href='#/dp/login';
	}
	
	
	$scope.$on("vertifycode",function(code){
		$scope.$apply(function(){
			$scope.verifyCode = code;
		})
		
	})
	
	$scope.preRegister = function(){
		if(user.email&&user.pwd&&user.tel&&$scope.verifyAns ==false){
			$scope.errmsg = "输入皆不可为空";
			return false;
		}
		if($scope.verifyCode != scope.verifyAns){
			$scope.errmsg = "验证码错误";
		}
		window.location.href='#/dp/register2';
		
	}
	 
});