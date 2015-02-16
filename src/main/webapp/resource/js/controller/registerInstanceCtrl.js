OJApp.controller('registerInstanceCtrl',function ($scope,$http,$modalInstance,Data,params) {
	
	$scope.Remail = params.email;
	$scope.company = params.company;
	//绑定变量到服务
	$scope.addhr = function () {
        if ($scope.Remail && $scope.Rpwd && $scope.Rrepwd && $scope.verifyAns) {
            if ($scope.Rpwd == $scope.Rrepwd) {
                if ($scope.verifyAns == $scope.verifyQtn.answer) {
                	//必须为公司邮箱才可注册
                	var re = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;	
 					
 				    if(!re.test($scope.Remail)){
 						$("#emailInfo").text("请输入正确的email格式");
 						return false;
 					}
 					else{
 						var company_part=regemail.split("@")[1];
 						var common_email=["126.com","163.com","sina.com","21cn.com","sohu.com","yahoo.com.cn","yahoo.com","tom.com","qq.com","etang.com","eyou.com","56.com","x.cn","chinaren.com","sogou.com","citiz.com","hotmail.com","msn.com","gmail.com","aim.com","aol.com","mail.com","walla.com","inbox.com","live.com","googlemail.com","ask.com","163.net","263.net","3721.net","yeah.net"];
 						//数组中不包含被查元素则返回-1，否则返回元素位置坐标
 						if($.inArray(company_part, common_email)>-1){
 							$("#emailInfo").text("请使用公司邮箱注册！");
 							return false;
 						}
 						alert("前端处理完成，转到后端处理");
 					}
                	
                	
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
        }else{
        	
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