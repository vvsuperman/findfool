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
	
	
	$scope.startTest=function(quizid){
		var email=CadData.getEmail();
		if(email==null){
		    window.location.href="#/cad/login";
		}else{
			window.location.href="#/pubtesting/"+quizid;
		}
	}
	
	
	
	
	
	
	
	
}])