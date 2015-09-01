/**
 * 公司详情控制器
 */
OJApp.controller('eachComController',['$scope','$http','CadData','$routeParams','CadData',function ($scope,$http,CadData,$routeParams,CadData) {

	
	$scope.nav = 'cad/page/cadnav.html';
	$scope.template= 'cad/page/eachcom.html';
	var comid = $routeParams.comid;
	$scope.getcomTail=function(){
		$http({
			url : WEBROOT + "/company/getcomTail",
			method : 'POST',
			data : {
				"comid" :comid
			}
		}).success(function(data) {
//			$scope.companyTail = data["message"];
			$scope.map=data["message"];
			$scope.companyTail=$scope.map.company;
			$scope.quizList=$scope.map.quizList;
			
			                      
		}).error(function() {
			console.log("get data failed");
		})
		
	}

	$scope.getcomTail();

	//开始挑战前进行登录判断，如果没有登录，跳转到登录界面
	
	
	$scope.startTest=function(quiz){
		var testemail=CadData.getTestEmail();
		if(testemail==null){
		    window.location.href="#/cad/login";
		}else{
			 $http({
		         url: WEBROOT+"/testing/checkstate",
		         method: 'POST',
			     data: {"email":CadData.getTestEmail(), "testid":quiz.quizid}
		     }).success(function (data) {
		         //测试未开始
		    	 if( data.state == 0){	
		    		 console.log("执行了第1步，");

			    	smoke.alert("您已经参加过本次挑战，不能重复挑战！");
		    	 }else if(data.state==2){ //试题尚未开始
		    		 console.log("执行了第2步，");

		    			smoke.alert("挑战还未开始，请您耐心等待！");
		    	 }else if(data.state==3){//试题已截至

		    		 smoke.alert("挑战赛已经结束，下次早点来哟！");
		    	 }
		    	 else{
		 			window.location.href="#/pubtesting/"+quiz.signedKey;

		    	 }	 
		         
		     }).error(function(){
		    	 console.log("get data failed");
		     })
		}
	}
	
	
	
	
	
	
	
	
}])