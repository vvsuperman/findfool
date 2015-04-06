OJApp.controller('signupCtrl',function ($scope,$http,Data) {
	
	$scope.Remail;
	$scope.company;
	//手机的错误信息
	$scope.errorMsg={};
	$scope.verifyQtn ={};
	
	$scope.$on("mobileError",function(event,data){
		$scope.$apply(function(){
			$scope.errorMsg.mobile = data;
		});
	});
	
	$scope.$on("vertifycode",function(event,data){
		$scope.$apply(function(){
			$scope.verifyQtn.answer = data;
		});
	})
	//明道登陆
	$scope.mdLogin = function(){
		 var url = 'https://api.mingdao.com/oauth2/authorize?app_key=EB56F580B240&redirect_uri='+MD_REDIRECT
		 window.location.href= url;
	 } 
	
	//绑定变量到服务
	$scope.addhr = function () {
        if ($scope.Remail && $scope.Rpwd && $scope.Rrepwd && $scope.verifyAns) {
            if ($scope.Rpwd == $scope.Rrepwd) {
                if ($scope.verifyAns == $scope.verifyQtn.answer) {
                	//必须为公司邮箱才可注册
                	var re = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;	
 					
 				    if(!re.test($scope.Remail)){
 						$scope.errorEmail = "请输入正确的email格式";
 						return false;
 					}
 					 	
					var company_part=$scope.Remail.split("@")[1];
					var common_email=["126.com","163.com","sina.com","21cn.com","sohu.com","yahoo.com.cn","yahoo.com","tom.com","qq.com","etang.com","eyou.com",
					                  "56.com","x.cn","chinaren.com","sogou.com","citiz.com","hotmail.com","msn.com","gmail.com","aim.com","aol.com","mail.com",
					                  "walla.com","inbox.com","live.com","googlemail.com","ask.com","163.net","263.net","3721.net","yeah.net","aliyun.com"];
					//数组中不包含被查元素则返回-1，否则返回元素位置坐标
					if($.inArray(company_part, common_email)>-1){
						$scope.errorEmail = "请使用公司邮箱注册！";
						return false;
					}
                	
                    $http({
                        url: WEBROOT+"/user/add/hr",
                        method: 'POST',
                        headers: {
                            "Authorization": Data.token()
                        },
                        data: {"email": $scope.Remail, "pwd": md5($scope.Rpwd), "name": $scope.name,"company":$scope.company,"tel":$scope.mobile}
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
                            /*$scope.cancel();*/
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
                	$scope.errorMsg.general = "验证问题答案错误";
                    //$scope.createCode();
                }
            } else {
            	$scope.errorMsg.general ="密码不相同";
            }
        }else{
        	$scope.errorMsg.general = "所有选项均不可为空";
        }
    };
});