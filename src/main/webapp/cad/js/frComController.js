/**
 * 做题控制器
 */
OJApp.controller('frComController',['$scope','$http','CadData','$routeParams',function ($scope,$http,CadData,$routeParams) {
	
	$scope.nav = 'cad/page/cadnav.html'
		$scope.template = 'cad/page/frcom.html';
	
	
	$scope.getFrChallage=function(){
		$http({
					url : WEBROOT + "/challenge/getFrChallage",
			method : 'POST',
			data : {
			
			}
		}).success(function(data) {				
			$scope.map=data["message"];
			$scope.frQuizNaver=$scope.map.frQuizNaver;
			$scope.frQuizBegin=$scope.map.frQuizBegin;
			$scope.frQuizOver=$scope.map.frQuizOver;
		}).error(function() {
			console.log("get data failed");
		})
		
	}

	$scope.getFrChallage();

	$scope.startTest=function(quizid){
		var email=CadData.getEmail();
		if(email==null){
		    window.location.href="#/cad/login";
		}else{

			 $http({
		         url: WEBROOT+"/testing/checkstate",
		         method: 'POST',
			     data: {"email":CadData.getEmail(), "testid":quizid}
		     }).success(function (data) {
		         //测试未开始
		    	 if( data.state == 0){		
			    	smoke.alert("您已经参加过本次挑战，不能重复挑战！");
		    	 }else if(data.state==2){ //试题尚未开始
		    			smoke.alert("挑战还未开始，请您耐心等待！");
		    	 }else if(data.state==3){//试题已截至
		    		 smoke.alert("挑战赛已经结束，下次早点来哟！");
		    	 }
		    	 else{
		 			window.location.href="#/pubtesting/"+quizid;

		    	 }	 
		         
		     }).error(function(){
		    	 console.log("get data failed");
		     })
			

			
			
		}
	}

	
	
	
	
	
}])