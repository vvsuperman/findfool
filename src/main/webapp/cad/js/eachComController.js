/**
 * 公司详情控制器
 */
OJApp.controller('eachComController',['$scope','$http','CadData','$routeParams','Data',function ($scope,$http,CadData,$routeParams,Data) {

	
	$scope.nav = 'cad/page/cadnav.html';
	$scope.template= 'cad/page/eachcom.html';
	
	
	var comid = $routeParams.comid;
console.log(comid);
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
			console.log($scope.quizList[0].extraInfo);
			
			                      
	   console.log($scope.companyTail.name);
		}).error(function() {
			console.log("get data failed");
		})
		
	}

	$scope.getcomTail();

	//开始挑战前进行登录判断，如果没有登录，跳转到登录界面
	
	
	$scope.isLogin=function(){
		
		console.log("正在验证是否登录");
		$scope.Lemail=Data.email();
		console.log($scope.Lemail);

		if($scope.Lemail==null){

			var url = "#/cad/login";
			 window.location.href=url;
			
		}else{
			var url2 = "#//testing";
			console.log("用户已经登录");
		
		}
		
		
		
	}
	
	
	
	
	
	
	
	
}])