OJApp.controller("oauthorController",function($scope, $http, Data,$routeParams) {

	$scope.template = 'page/oauthor.html';
    $scope.ContentUs = '';
    $scope.leftBar = '';
    
    console.log("title", $routeParams.title);
    
    $scope.title = $routeParams.title;
    $scope.name = Data.name();
    
    $scope.Remail = Data.email();
    $scope.company = Data.company();
    
    $scope.addhr = function () {
        if ($scope.Rpwd) {
            
               
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
                    
                    
                    
                } 
       
    };
    
    $scope.cancel = function(){
    	 window.location.href = 'http://findfool.com';
    }

    

});