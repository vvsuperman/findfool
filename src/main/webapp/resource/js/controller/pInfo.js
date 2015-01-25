/**
 * Created by liuzheng on 2014/7/11.
 */
OJApp.controller("pInfo",function($scope, $http, Data) {
    $scope.url = '#/profile';
    $scope.template = 'page/profile.html';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.leftBar = 'page/leftBar.html';
    
    $scope.user ={};
    $scope.tempUser ={};
    $scope.user.company = "";
    $scope.user.name = "";
    $scope.user.tel = "";
    $scope.assist =0;
    $scope.pwd ={};
    $scope.pwd.show = 0;
    
    $scope.getInfo = function () {
        $http({
            url: WEBROOT+"/user/setting/query",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"uid": Data.uid()}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
                $scope.user = $scope.message
                Data.setCompany($scope.user.company);
                Data.setName($scope.user.name);
                Data.setTel($scope.user.tel);
				Data.setEmail($scope.user.email);
            } else {
            }
        }).error(function (data) {
           console.log("获取用户信息错误");
        });
    };
    $scope.getInfo();

    $scope.modifyProfile = function(){
    	$scope.assist =1;
    	$scope.tempUser.name = $scope.user.name;
    	$scope.tempUser.company = $scope.user.company;
    	$scope.tempUser.tel = $scope.user.tel;
    }
    
    //基本信息修改，不用密码
    $scope.saveProfile = function (name,company,tel,pwd,newpwd) {
        $scope.name = name;
        $scope.company = company;
        $scope.tel = tel;
       
        $http({
            url: WEBROOT+"/user/setting/set",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"uid": Data.uid(), "company": $scope.tempUser.company,"tel": $scope.tempUser.tel}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            if ($scope.state) {
               $scope.user = data["message"];
               $scope.assist = 0;
            } else {

            }
        }).error(function (data) {

        });
    };
    
    
    $scope.cancelProfile = function(){
    	$scope.assist =0;
    	$scope.tempUser = {};
    }
    
    //修改密码
    $scope.modifyPwd = function(){
    	console.log("oldpwd...........",$scope.pwd.old);
    	
    		 $http({
    	            url: WEBROOT+"/user/setting/setpwd",
    	            method: 'POST',
    	            headers: {
    	                "Authorization": Data.token()
    	            },
    	            data: {"uid": Data.uid(), "oldPwd": md5($scope.pwd.old),"newPwd": md5($scope.pwd.now)}
    	        }).success(function (data) {
    	            $scope.state = data["state"];//1 true or 0 false
    	            if ($scope.state) {
    	               $scope.pwd.show = 0;
    	               smoke.alert("修改成功");
    	               $scope.pwd.old ="";
    	               $scope.pwd.now ="";
    	            } else {
    	               smoke.alert("密码错误");
    	            }
    	        }).error(function (data) {

    	        });
    	
    	
         
         if (!$scope.renewpasswd) {
             $scope.newPWD = md5($scope.pwd);
         } else {
             $scope.newPWD = md5($scope.newpwd);
         }
         $scope.PWD = md5($scope.pwd);
    }
})
