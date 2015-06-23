/**
 * 
 */

OJApp.controller('testMainController',function ($scope,$http,CadData) {
	 
    $scope.showtext=0;	
	
	$scope.email = CadData.getEmail();
	
	
	$scope.cadInfo ={};
	
	$scope.openTest = function(testid,testname){
		CadData.setTestid(testid);
		CadData.setTestname(testname);
		window.location.href='#/dp/testdetail';
	 
	}
	
	$scope.logout = function(){
		CadData.clear();
		window.location.href='#/dp/login';
	}
	

	
	
	
});

OJApp.controller('testDetailController',function ($scope,$http,CadData) {
	var testname = CadData.getTestname();
	$scope.testsrc ="resource/static/"+ testname+".png";	
	
	
	$http({
        url: WEBROOT+"/cadquiz/preparetest",
        method: 'POST',
        data: {"testid":CadData.getTestid(),"email":CadData.getEmail()}
    }).success(function (data) {
   	 if(data.state!=0){
   		 $scope.errmsg = data.message;
   	 }else{
   		 var percent = data.message.percent;
   		 $scope.cads = data.message.cads;
   		 $scope.state = data.message.state;
   		 if(percent>=90){
   			 $scope.slogan = "我伙呆！您打败了全国"+percent+"%的挑战者，你还是人类吗？";
   		 }else if(percent>=70 && percent<90){
   			 $scope.slogan = "您打败了全国"+percent+"％的挑战者，你这么NB，你的小伙伴们造吗！";
   		 }else if(percent>=50 && percent<69){
   			 $scope.slogan = "您打败了全国"+percent+"%的挑战者，何弃疗！药不能停！再接再厉！";
   		 }else{
   			 $scope.slogan = "您打败了全国"+percent+"％的挑战者，蛋白质肯定不是你，快让我们见识你的实力！";
   		 }
   		
   	 }
   	
    }).error(function(){
   	 console.log("get data failed");
    })
	
    
	$scope.startTest = function(){
		
		if($scope.state == 1){
			flashTip("太牛＊了，题库已被你虐残了！换个题库玩玩吧！");
			return false;
		}
		 window.location.href='#/dp/cadtesting';
	}
	
});


OJApp.controller('profileController',function ($scope,$http,CadData) {
	
	$scope.edit={};
	$scope.edit.profile=0;
	$scope.edit.pwd = false;
	$scope.pwd={};
	
	//获取用户信息
	$http({
        url: WEBROOT+"/cad/getcadinfo",
        method: 'POST',
        data: {"email":CadData.getEmail()}
    }).success(function (data) {
   	 if(data.state!=0){
   		 $scope.errmsg = data.message;
   	 }else{
   		
   		 $scope.cad = data.message;
   		
   	 }
    });
	
	
	$scope.modifyCad = function(){
		$scope.edit.profile=1;
		$scope.tempCad = $scope.cad;
	}
	
	$scope.saveCad= function(){
		
		
		
		//修改用户信息
		$http({
	        url: WEBROOT+"/cad/modifycadinfo",
	        method: 'POST',
	        data: $scope.tempCad
	    }).success(function (data) {
	   	 if(data.state!=0){
	   		 $scope.errmsg = data.message;
	   	 }else{
	   		 $scope.cad = $scope.tempCad;
	   		 $scope.errmsg = "修改成功";
	   		 $scope.edit.profile=0;
	   	 }
	    });
	}
	
	$scope.savePwd = function(){
		
		
		
		//修改密码
		$http({
	        url: WEBROOT+"/cad/modifypwd",
	        method: 'POST',
	        data: {"email":CadData.getEmail(),"oldpwd":md5($scope.pwd.old),"newpwd":md5($scope.pwd.newpd)}
	    }).success(function (data) {
	   	 if(data.state!=0){
	   		 $scope.errmsg = data.message;
	   	 }else{
	   		 $scope.errmsg = "修改成功";
	   		 $scope.edit.pwd = false;
	   	 }
	    });
		
	}
	
	
	
	
});
