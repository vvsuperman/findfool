OJApp.controller("oauthorController",function($scope, $http, Data,$routeParams) {

	$scope.template = 'page/oauthor.html';
    $scope.ContentUs = '';
    $scope.leftBar = '';
    $scope.errorMsg ={};
    $scope.verifyQtn ={};
    
    console.log("title", $routeParams.title);
    
    $scope.title = $routeParams.title;
    $scope.name = Data.name();
    
    $scope.Remail = Data.email();
    $scope.company = Data.company();
    $scope.mdUid = Data.mdUid();
    
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
    
    $scope.addhr = function () {
        if ($scope.Rpwd  && $scope.verifyAns && $scope.mobile) {
		    	if ($scope.verifyAns == $scope.verifyQtn.answer) {
		    		   
		                $http({
		                    url: WEBROOT+"/user/add/hr",
		                    method: 'POST',
		                    headers: {
		                        "Authorization": Data.token()
		                    },
		                    data: {"email": $scope.Remail, "pwd": md5($scope.Rpwd), "name": $scope.name,"company":$scope.company,"tel":$scope.mobile,"mdUid":$scope.mdUid}
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
		                        Data.setName($scope.message.email);
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
		    	}else{
		    		$scope.errorMsg.general = "验证问题答案错误";
		    	}   
            } else{
            	$scope.errorMsg.general = "选项均不可为空";
            }
       
    };
    
    $scope.cancel = function(){
    	 window.location.href = 'http://foolrank.com';
    }

    

});