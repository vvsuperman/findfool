OJApp.controller('loginCtrl',['$scope','$http','Data',function ($scope,$http,Data) {
	
	//手机的错误信息
	$scope.errorMsg={};
	$scope.verifyQtn ={};
	
	$scope.mdLogin = function(){
		 var url = 'https://api.mingdao.com/oauth2/authorize?app_key=EB56F580B240&redirect_uri='+MD_REDIRECT
		 window.location.href= url;
	 } 
	$scope.confirm = function (data) {
        if ($scope.Lemail && $scope.Lpwd) {
        	if(data==1){
        		var pwd = md5($scope.Lpwd);
        	}else{
        		var pwd = $scope.Lpwd;
        	}
        	
        	
            $http({
                url: WEBROOT+"/user/confirm",
                method: 'POST',
                headers: {
                    "Authorization": Data.token()
                },
                data: {"email": $scope.Lemail, "pwd": pwd, "name": $scope.Lname}
            }).success(function (data) {
            	console.log("success");
                $scope.state = data["state"];//1 true or 0 false
//                Data.clear();//清空缓存
                var name = $scope.Lemail;
                $scope.message = data["message"];
                if (data["message"].handler_url != null && data["message"].handler_url !== "") {
                    name = data["message"].handler_url;
                }
                Data.setName(name);
                Data.setEmail($scope.Lemail);
                

                if ($scope.state) {
                	//蓝拓人力过来的，把标题换下
                	if($scope.state == 2){
                		Data.setUserSource("lantuo");
                	}
                    Data.setToken(data["token"]);
                    Data.setUid($scope.message.uid);
                    Data.setPrivi($scope.message.privilege);
                    Data.setTel($scope.message.tel);
                    Data.setCompany($scope.message.company);
                    Data.setInvitedleft($scope.message.invited_left);
                    Data.setLastActive((new Date()).getTime());
                    $scope.invitedleft = $scope.message.invited_left;
                   
                    window.location.href = ROOT+'/loginok';
//                    $scope.name = $scope.message.handler_url;
                } else {
                    
//                    window.location.href = '#/loginok';
                	$scope.errmsg = data.message.msg;
                }
            }).error(function () {
            	console.log("err");
//                    alert("网络错误");
//                    window.location.reload(true);
                }
            )
        }
    };
}]);