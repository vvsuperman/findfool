/**
 * 客户端首页上部分控制器
 */
OJApp.controller('cadNavController',['$scope','$http','CadData','Data',function ($scope,$http,CadData,Data) {

	$scope.username=CadData.getTestname();
	$scope.email=CadData.getEmail();

	$scope.isLogin = function(param) {
		if (param == 1) {
			if ($scope.email == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if ($scope.email == null) {
				return false;
			} else {
				return true;
			}
		}

	}
	
	$scope.loginOut=function(){
		CadData.clear();
	    window.location.href="#/cad/comlist";		
	}
	
}])
