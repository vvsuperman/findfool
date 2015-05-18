OJApp.controller('findPwdCtrl',function ($scope,$http,Data) {
	
	$scope.requestStage=1;
	$scope.submitStage=0;
	
	$scope.request=function(){
		if($scope.email){
			$http({
				url: WEBROOT + "/user/setting/resetpwdapply",
				method: 'POST',
				data: {'email': $scope.email}
			}).success(function(data){
				alert('已向您邮箱发送重置密码信息');
				$scope.requestStage=0;
				$scope.submitStage=1;
			});
		}
	};
	
	$scope.submit=function(){
		if($scope.newPwd==$scope.confirmPwd){
			var pwd=md5($scope.configmPwd);
			$http({
				url: WEBROOT+"/user/setting/resetpwd",
				method: 'POST',
				headers: {
                  "Authorization": Data.token()
				},
				data: {'email': $scope.emai,'password': pwd,'confimPassword': pwd}
			}).success(function(){
				alert('重置密码成功')
			}).error(function(){
				alert('重置密码失败')
			});
		}
		else{
			alert("密码不一致")
		}
	}
	
//	//手机的错误信息
//	$scope.errorMsg={};
//	$scope.verifyQtn ={};
//	
//	$scope.mdLogin = function(){
//		 var url = 'https://api.mingdao.com/oauth2/authorize?app_key=EB56F580B240&redirect_uri='+MD_REDIRECT
//		 window.location.href= url;
//	 } 
//	$scope.confirm = function (data) {
//        if ($scope.Lemail && $scope.Lpwd) {
//        	if(data==1){
//        		var pwd = md5($scope.Lpwd);
//        	}else{
//        		var pwd = $scope.Lpwd;
//        	}
//        	
//        	
//            $http({
//                url: WEBROOT+"/user/confirm",
//                method: 'POST',
//                headers: {
//                    "Authorization": Data.token()
//                },
//                data: {"email": $scope.Lemail, "pwd": pwd, "name": $scope.Lname}
//            }).success(function (data) {
//            	console.log("success");
//                $scope.state = data["state"];//1 true or 0 false
//                Data.clear();//清空缓存
//                var name = $scope.Lemail;
//                $scope.message = data["message"];
//                if (data["message"].handler_url != null && data["message"].handler_url !== "") {
//                    name = data["message"].handler_url;
//                }
//                Data.setName(name);
//                Data.setEmail($scope.Lemail);
//                
//
//                if ($scope.state) {
//                    Data.setToken(data["token"]);
//                    Data.setUid($scope.message.uid);
//                    Data.setPrivi($scope.message.privilege);
//                    Data.setTel($scope.message.tel);
//                    Data.setCompany($scope.message.company);
//                    Data.setInvitedleft($scope.message.invited_left);
//                    Data.setLastActive((new Date()).getTime());
//                    $scope.invitedleft = $scope.message.invited_left;
//                   
//                    window.location.href = '#/loginok';
////                    $scope.name = $scope.message.handler_url;
//                } else {
//                    
////                    window.location.href = '#/loginok';
//                	$scope.errmsg = data.message.msg;
//                }
//            }).error(function () {
//            	console.log("err");
////                    alert("网络错误");
////                    window.location.reload(true);
//                }
//            )
//        }
//    };
});