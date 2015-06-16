/**
 * 
 */

OJApp.controller('cadLoginController',function ($scope,$http,CadData) {
	$scope.tuser={};
	
	
	if(CadData.getEmail()!=""){
	   	
	}
	
	$scope.register = function(){
		window.location.href='#/dp/register';
	}
	
	$scope.login = function(){
		window.location.href='#/dp/login';
	}
	
	
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
		if($scope.verifyCode != $scope.verifyAns){
			$scope.errmsg = "验证码错误";
			return false;
		}
		
		$scope.tuser.tel = $scope.mobile;
		
		$scope.tuser.pwd = md5($scope.tuser.pwd);
		
		$http({
	         url: WEBROOT+"/cad/preregister",
	         method: 'POST',
	         data: $scope.tuser
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
		
		
		
		if( !$scope.cad.email || !$scope.cad.pwd){
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