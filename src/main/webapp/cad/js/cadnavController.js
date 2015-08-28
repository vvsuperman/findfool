/**
 * 客户端首页上部分控制器
 */
OJApp.controller('cadNavController',['$scope','$http','CadData','Data',function ($scope,$http,CadData,Data) {

	$scope.username=CadData.getCadTestname();
	$scope.testemail=CadData.getTestEmail();
//	$scope.testemail=Data.getTestEmail();

	$scope.isLogin = function() {
			if ($scope.testemail == null) {
				return false;
			} else {
				return true;
			}
		}


$scope.isNotLogin = function() {
	if ($scope.testemail == null) {
		return true;
	} else {
		return false;
	}
}
	
	$scope.isLogin();
	$scope.isNotLogin();
	$scope.loginOut=function(){
		CadData.clear();
	    window.location.href="#/cad/comlist";		
	    window.location.reload(true);
	}
	
}])
