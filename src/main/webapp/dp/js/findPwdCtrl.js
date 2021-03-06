OJApp.controller('findCadPwdCtrl',['$scope','$http','CadData','$routeParams','$window',function ($scope,$http,CadData,$routeParams,$window) {
	
	 //判断用户是否登陆

	
	//获取验证码
	$scope.refreshQuestion = function(){
		$http({
			url: WEBROOT + "/user/getVerifyQtn",
			method: 'POST',
		}).success(function(data){
			$scope.content = data.message;
		});
	}
	$scope.refreshQuestion();
	
	$scope.request=function(){
		if($scope.email && $scope.answer){
			$http({
				url: WEBROOT + "/cad/setting/resetpwdapply",
				method: 'POST',
				data: {'email': $scope.email,'content':$scope.content,'answer':$scope.answer}
			}).success(function(data){
				if(data.state !=0){
					$scope.errmsg=data.message;
					return false;
				}else{
					$scope.errmsg='已向您邮箱发送重置密码信息';
					$scope.refreshQuestion();
					$scope.isMessage=1;
				}
				
			});
		}else{
			$scope.errmsg= "输入均不得为空";
		}
	};
	
	$scope.submit=function(){
		
		if(typeof($scope.pwd)=="undefined" ||typeof($scope.confirmpwd)=="undefined"||  $scope.pwd=="" ||$scope.confirmpwd==""){
			$scope.errmsg="输入不得为空";
			$scope.errmsg="输入不得为空";
			return false;
		}
		
		if($scope.pwd != $scope.confirmpwd){
			$scope.errmsg="两次密码不同";
			return false;
		}
		
		var pwd=md5($scope.pwd);
		var confirmpwd=md5($scope.confirmpwd);
		$http({
			url: WEBROOT+"/cad/setting/resetpwd",
			method: 'POST',
			data: {'email': $scope.email,'pwd': pwd,'confirmpwd': confirmpwd}
		}).success(function(data){
			if(data.state==0){
				$scope.errmsg='重置密码成功';
				$scope.successStage=1;
				$scope.submitStage=0;
			}else{
				$scope.errmsg=data.message;
			}
			
		}).error(function(){
			$scope.errmsg='重置密码失败';
		});
		
	}
	
	$scope.checkUrl=function(){
		$http({
			url: WEBROOT+'/cad/setting/checkurl',
			method: 'POST',
			data: {'url': $routeParams.auth}
		}).success(function(data){
			$scope.email=data.message;
			$scope.requestStage=0;
			$scope.submitStage=1;
		}).error(function(err){
			$scope.requestStage=0;
			$scope.submitStage=1;
			$scope.errmsg='链接地址无效';
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
}]);