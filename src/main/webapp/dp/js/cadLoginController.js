/**
 * 
 */

OJApp.controller('cadLoginController',function ($scope,$http,CadData) {
	$scope.tuser={};
	
	//获取用户是否点击remberme
	$scope.rembme = CadData.getRembme();
	if($scope.rembme){
		var email = CadData.getEmail();
		if(email !=null){
			 window.location.href='#/dp/testmain';
		}
	}
	
	//如果用户点击记住我,直接跳转到主界面
	$scope.$watch("rembme",function(){
		if($scope.rembme){
			CadData.setRembme(true)
		}else{
			CadData.setRembme(false);
			
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
	    		 CadData.setEmail(data.message);
	    		 window.location.href='#/dp/register2';
	    	 }
	    	
	     }).error(function(){
	    	 console.log("get data failed");
	     })
		
	}
	
	
	
	$scope.degrees=[{id:0,name:"大专"},{id:1,name:"本科"},{id:2,name:"研究生"},{id:2,name:"博士"}];
	$scope.tuser.degree="本科";
	
	$scope.register = function(){
		
		if( !$scope.tuser.username || !$scope.tuser.school
				|| !$scope.tuser.discipline || !$scope.tuser.degree || !$scope.tuser.gratime){
			$scope.errmsg = "输入皆不可为空";
			return false;
		}
		
		$scope.tuser.email = CadData.getEmail();
		$http({
	         url: WEBROOT+"/cad/register",
	         method: 'POST',
	         headers: {
	                "Authorization": CadData.getToken()
	         },
	         data: $scope.tuser
	     }).success(function (data) {
	    	 if(data.state!=0){
	    		 $scope.errmsg = data.message;
	    	 }else{
	    		 CadData.setEmail(data.message);
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
	                "Authorization": CadData.getToken()
	         },
	         data: $scope.cad
	     }).success(function (data) {
	    	 if(data.state!=0){
	    		 $scope.errmsg = data.message;
	    	 }else{
	    		 CadData.setToken(data.message.token);
	    		 CadData.setEmail(data.message.email);
	    		 window.location.href='#/dp/testmain';
	    	 }
	     }).error(function(){
	    	 console.log("get data failed");
	     })
	}
	
});