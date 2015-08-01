OJApp.controller('cadSignupCtrl',['$scope','$http','Data',function ($scope,$http,Data) {
	
	$scope.Remail;
//	$scope.company;
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
                            "Authorization": Data.token()
                        },
                        data: {"email": $scope.Remail, "pwd": md5(($scope.Rpwd).toLowerCase()), "username": $scope.name,"tel":$scope.mobile}
                    }).success(function (data) {
                        $scope.state = data["state"];//1 true or 0 false
                        $scope.message = data["message"];
                        if ($scope.state==0) {
 
                            Data.setToken(data["token"]);
                            //end bu zpl
                            Data.setName($scope.message.email);
                            Data.setEmail($scope.message.email);
                          
                        } else {
                            alert($scope.message);
//                            window.location.reload(true);
                        }
                    }).error(function () {
                            alert("网络错误q");
//                            window.location.reload(true);
                        }
                    );
                } else {
                	$scope.errorMsg.general = "验证问题答案错误";
                    //$scope.createCode();
                }
            } else {
            	$scope.errorMsg.general ="密码为空，请重新输入";
            }
        }else{
        	$scope.errorMsg.general = "所有选项均不可为空";
        }
    };
}]);