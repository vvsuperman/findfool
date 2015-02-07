OJApp.controller('registerInstanceCtrl',function ($scope,$http,$modalInstance,Data,params) {
	
	$scope.Remail = params.email;
	$scope.company = params.company;
	//绑定变量到服务
	$scope.addhr = function () {
        if ($scope.Remail && $scope.Rpwd && $scope.Rrepwd && $scope.verifyAns) {
            if ($scope.Rpwd == $scope.Rrepwd) {
                if ($scope.verifyAns == $scope.verifyQtn.answer) {
                    $http({
                        url: WEBROOT+"/user/add/hr",
                        method: 'POST',
                        headers: {
                            "Authorization": Data.token()
                        },
                        data: {"email": $scope.Remail, "pwd": md5($scope.Rpwd), "name": $scope.name,"company":$scope.company}
                    }).success(function (data) {
                        $scope.state = data["state"];//1 true or 0 false
                        $scope.message = data["message"];
                        if ($scope.state) {
                            Data.setUid($scope.message.uid);
                            Data.setToken(data["token"]);
                            Data.setPrivi($scope.message.privilege);
                            Data.setTel($scope.message.tel);
                            Data.setCompany($scope.message.company);
                            Data.setInvitedleft($scope.message.invited_left);
                            //end bu zpl
                            Data.setName($scope.message.email);
                            $scope.cancel();
                            //除去modal层的遮罩
//                            var child = document.getElementsByClassName("modal-backdrop fade in");
//                            child[0].parentNode.removeChild(child[0]);
                            window.location.href = '#/test';
                        } else {
                            alert($scope.message.msg);
                            window.location.reload(true);
                        }
                    }).error(function () {
                            alert("网络错误");
                            window.location.reload(true);
                        }
                    );
                } else {
                    alert("验证问题答案错误");
                    //$scope.createCode();
                }
            } else {
                alert("密码不相同")
            }
        }
    };
	
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	  $scope.changeQuestion = function(){
	    	$http({
	            url: WEBROOT+"/user/getVerifyQtn",
	            method: 'POST'
	        }).success(function (data) {
	            if (data.state == 200) {
	            	$scope.verifyQtn=data.message;
	            } else {
	            	console.log("获取问题出错哦！");
	            }
	        }).error(function () {
	                console.log("网络错误");
	                //window.location.reload(true);
	            }
	        );
	    }
	    $scope.changeQuestion();
	
	
});