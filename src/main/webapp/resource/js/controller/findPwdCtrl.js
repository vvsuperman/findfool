OJApp.controller('findPwdCtrl',function ($scope,$http,Data,$routeParams,$window) {
	
	$scope.request=function(){
		if($scope.email){
			$http({
				url: WEBROOT + "/user/setting/resetpwdapply",
				method: 'POST',
				data: {'email': $scope.email}
			}).success(function(data){
				flashTip('已向您邮箱发送重置密码信息');
				$scope.isMessage=1;
			});
		}
	};
	
	$scope.submit=function(){
		if($scope.newPwd==$scope.confirmPwd){
			var pwd=md5($scope.confirmPwd);
			$http({
				url: WEBROOT+"/user/setting/resetpwd",
				method: 'POST',
				data: {'email': $scope.email,'password': pwd,'confirmPassword': pwd}
			}).success(function(){
				flashTip('重置密码成功');
				$scope.successStage=1;
				$scope.submitStage=0;
			}).error(function(){
				flashTip('重置密码失败');
			});
		}
		else{
			flashTip("密码不一致")
		}
	}
	
	$scope.checkUrl=function(){
		$http({
			url: WEBROOT+'/user/setting/checkurl',
			method: 'POST',
			data: {'url': $routeParams.auth}
		}).success(function(data){
			$scope.email=data.message;
			$scope.requestStage=0;
			$scope.submitStage=1;
		}).error(function(err){
			$scope.requestStage=0;
			$scope.submitStage=1;
			flashTip('链接地址无效');
		});
	}
	
	$scope.isMessage=0;
	$scope.successStage==0;
	if($routeParams.auth){
		$scope.checkUrl();
	}
	else{
		$scope.requestStage=1;
		$scope.submitStage=0;
	}
});