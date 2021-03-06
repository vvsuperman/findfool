/**
 * 
 */

OJApp.controller('cadLoginController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	$scope.tuser={};
	
	//获取用户是否点击remberme
	$scope.rembme = CadData.getCadRembme();
	if($scope.rembme){
		var email = CadData.getTestEmail();
		if(email !=null){
			 window.location.href='#/dp/testmain';
		}
	}
	
	//如果用户点击记住我,直接跳转到主界面
	$scope.$watch("rembme",function(){
		if($scope.rembme){
			CadData.setCadRembme(true)
		}else{
			CadData.setCadRembme(false);
			
		}
	})
	
	
	
		
	
	$scope.$on("vertifycode",function(event,code){
		$scope.$apply(function(){
			$scope.verifyCode = code;
		})
		
	})
	
	
	$scope.$on("mobileError",function(event,data){
		$scope.$apply(function(){
			$scope.errmsg = data;
		});
	});
	
	$scope.preRegister = function(){
	
		if(!$scope.tuser.email || !$scope.tuser.pwd || !$scope.mobile || !$scope.verifyAns ){
			$scope.errmsg = "输入皆不可为空";
			return false;
		}
//		if($scope.verifyCode != $scope.verifyAns){
//			$scope.errmsg = "验证码错误";
//			return false;
//		}
		
		$scope.tuser.tel = $scope.mobile;
		
	  	var tmpUser = jQuery.extend(true, {},  $scope.tuser);
		tmpUser.pwd =  md5($scope.tuser.pwd);
		
		$http({
	         url: WEBROOT+"/cad/preregister",
	         method: 'POST',
	         data: tmpUser
	     }).success(function (data) {
	    	 if(data.state!=0){
	    		 $scope.errmsg = data.message;
	    	 }else{
	    		 CadData.setTestEmail(data.message);
	    		 window.location.href='#/dp/register2';
	    	 }
	    	
	     }).error(function(){
	    	 console.log("get data failed");
	     })
		
	}
	
	
	
	$scope.degrees=[{id:0,name:"大专"},{id:1,name:"本科"},{id:2,name:"硕士研究生"},{id:3,name:"博士研究生"}];
	$scope.gratimes=[{id:0,time:"2014"},{id:1,time:"2015"},{id:2,time:"2016"},{id:3,time:"2017"}];
	
	$scope.tuser.degree="本科";
	$scope.tuser.gratime="2016";
	
	
	$scope.register = function(){
		
		if( !$scope.tuser.username || !$scope.tuser.school
				|| !$scope.tuser.discipline || !$scope.tuser.degree || !$scope.tuser.gratime){
			$scope.errmsg = "输入皆不可为空";
			return false;
		}
		
		$scope.tuser.email = CadData.getTestEmail();
		$http({
	         url: WEBROOT+"/cad/register",
	         method: 'POST',
	         headers: {
	                "Authorization": CadData.getTestToken()
	         },
	         data: $scope.tuser
	     }).success(function (data) {
	    	 if(data.state!=0){
	    		 $scope.errmsg = data.message;
	    	 }else{
	    		 CadData.setTestEmail(data.message);
	    		 window.location.href='#/dp/testmain';
	    	 }
	     }).error(function(){
	    	 console.log("get data failed");
	     })
	}
	
	$scope.login = function(){
		if(typeof($scope.cad)=="undefined"|| typeof($scope.cad.email)=="undefined"||typeof($scope.cad.pwd)=="undefined"||!$scope.cad.email || !$scope.cad.pwd){
			$scope.errmsg = "输入皆不可为空";
			return false;
		}
		
		$scope.cad.pwd = md5($scope.cad.pwd);
		
		$http({
	         url: WEBROOT+"/cad/login",
	         method: 'POST',
	         headers: {
	                "Authorization": CadData.getTestToken()
	         },
	         data: $scope.cad
	     }).success(function (data) {
	    	 if(data.state!=0){
	    		 $scope.errmsg = data.message;
	    	 }else if(data.state ==4){
	    	 //用户未完成第二步注册
	    		 CadData.setTestEmail(data.message);
	    		 window.location.href='#/dp/register2';
	    	 }
	    	 
	    	 else{
	    		 CadData.setTestToken(data.message.token);
	    		 CadData.setTestEmail(data.message.email);
	    		 window.location.href='#/dp/testmain';
	    	 }
	     }).error(function(){
	    	 console.log("get data failed");
	     })
	}
	
}]);