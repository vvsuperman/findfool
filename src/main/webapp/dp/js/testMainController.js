/**
 * 
 */

OJApp.controller('testMainController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	 
    $scope.showtext=0;	
  
    //判断用户是否登陆
	$scope.email = CadData.getEmail();
	
	if($scope.email =="" || typeof($scope.email)=="undefined" || $scope.email ==null){
		window.location.href='#/dp';
	}

	$http({
        url: WEBROOT+"/cadquiz/getlevel",
        method: 'POST',
        data: {"email":CadData.getEmail()}
    }).success(function (data) {
   	    $scope.level = data.message;
    });
	

	
	$scope.cadInfo ={};
	
	$scope.openTest = function(testname,level){
		
		if( $scope.level<level ){
			if(level ==2){
				smoke.alert("想挑战我？请积分超过300分后再来哦～");
			}else if(level ==3){
				smoke.alert("想跟我玩？继续玩小红小白，凑够500分再来");
			}
			
			return false;
		}
		$http({
	        url: WEBROOT+"/cadquiz/gettest",
	        method: 'POST',
	        data: {"testname":testname}
	    }).success(function (data) {
	   	    if(data.state!=0){
	   	    	smoke.alert(data.message);
	   	    	return false;
	   	    }else{
	   	    	console.log("settestid..............");
	   	    	CadData.setTestid(data.message);
	   	    	CadData.setTestname(testname);
	   			window.location.href='#/dp/testdetail';
	   		 
	   	    }
	    });
		
		
	}
	
	$scope.logout = function(){
		CadData.clear();
		window.location.href='#/dp';
	}
	

	
	
	
}]);

OJApp.controller('testDetailController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	
	
	 //判断用户是否登陆
	$scope.email = CadData.getEmail();
	if($scope.email =="" || typeof($scope.email)=="undefined" || $scope.email ==null){
		window.location.href='#/dp';
	}
	
	
	
	$scope.testname = CadData.getTestname();
	$scope.testsrc ="resource/static/"+ $scope.testname+".png";	
	$scope.testdes ="在"+$scope.testname+"中"
	
	
	$http({
        url: WEBROOT+"/cadquiz/preparetest",
        method: 'POST',
        data: {"testid":CadData.getTestid(),"email":CadData.getEmail()}
    }).success(function (data) {
    if(data.state == 101){
    	smoke.alert(data.message);
    	window.location.href='#/dp/testmain';
    }
    else if(data.state!=0){
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
   		 }else if(percent==0){
   			$scope.slogan = "您还未开始做题，来！证明你自己！";
   		 }else{
   			 $scope.slogan = "您打败了全国"+percent+"％的挑战者，蛋白质肯定不是你，快让我们见识你的实力！";
   		 }
   	 }
   	
    }).error(function(){
   	 console.log("get data failed");
    })
	
    
	$scope.startTest = function(){
		
		if($scope.state == 1){
			smoke.alert("太牛＊了，题库已被你虐残了！换个题库玩玩吧！");
			return false;
		}
		 window.location.href='#/dp/cadtesting';
	}
	
	

	
}]);


OJApp.controller('profileController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	
	 //判断用户是否登陆
	$scope.email = CadData.getEmail();
	if($scope.email =="" || typeof($scope.email)=="undefined" || $scope.email ==null){
		window.location.href='#/dp';
	}
	
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
	
	
	
	
}]);
