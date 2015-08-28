OJApp.controller('cadSignupCtrl',['$scope','$http','CadData',function ($scope,$http,CadData) {
	
	$scope.Remail;
	$scope.name;
	//手机的错误信息
	$scope.errorMsg={};
	$scope.verifyQtn ={};
	console.log("dadada");
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

	
	//绑定变量到服务
	$scope.addhr = function () {
        if ($scope.Remail && $scope.Rpwd && $scope.verifyAns) {
            if ($scope.Rpwd !=null) {
            
                if ($scope.verifyAns == $scope.verifyQtn.answer) {
                	//邮箱才可注册
                	var re = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;	
 					
 				    if(!re.test($scope.Remail)){
 						$scope.errorEmail = "请输入正确的email格式";
 						return false;
 					} 	
				
                    $http({
                        url: WEBROOT+"/tuser/register",
                        method: 'POST',
                        headers: {
                            "Authorization": CadData.getTestToken
                        },
                        data: {"email": $scope.Remail, "pwd": md5(($scope.Rpwd).toLowerCase()), "username": $scope.name,"tel":$scope.mobile}
                    }).success(function (data) {
                        $scope.state = data["state"];//1 true or 0 false
                        $scope.message = data["message"];
                        if ($scope.state==0) {
 
                        	CadData.setTestToken(data["token"]);
                        	CadData.setCadTestname($scope.message.email);
                        	CadData.setTestEmail($scope.message.email);
                            var url = "#/cad/login";
    						window.location.href = url;
                          
                        } else {
                            alert($scope.message);
                        }
                    }).error(function () {
                            alert("网络错误q");
                        }
                    );
                } else {
                	$scope.errorMsg.general = "验证问题答案错误";
                }
            } else {
            	$scope.errorMsg.general ="密码为空，请重新输入";
            }
        }else{
        	$scope.errorMsg.general = "所有选项均不可为空";
        }
    };
}]);