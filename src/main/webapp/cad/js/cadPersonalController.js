/**
 * 客户端首页控制器
 */
OJApp.controller('cadPersonalController',['$scope','$http','CadData','Data','CadData',function ($scope,$http,CadData,Data,CadData) {
	

	$scope.nav = 'cad/page/cadnav.html';
	$scope.template= 'cad/page/cadPersonal.html';
console.log("执行了第1步");
	
		
	$scope.email=CadData.getEmail();
	
	$scope.cadPersonalList=function(){
		$http({
			url : WEBROOT + "/personal/findAllList",
			method : 'POST',
			data : {
				'email':$scope.email
			}
		}).success(function(data) {
			$scope.map=data["message"];
			$scope.testuser=$scope.map.testuser;
			$scope.myChallenge=$scope.map.myChallenge;
       
		}).error(function() {
			console.log("get data failed");
		})
		
	}

	$scope.cadPersonalList();

	
	$scope.showCompanyTest=function(company){
		var url = "#/cad/eachcom/"+company.id;
		 window.location.href=url;
	}
	
	
	//修改个人资料方法
	$scope.cdPersonalModify=function(){
		$http({
			url : WEBROOT + "/personal/modify",
			method : 'POST',
			data :$scope.testuser,
		
		}).success(function(data) {

			 $scope.ifShow();
		}).error(function() {
			console.log("get data failed");
		})
		
	};
	
	
	
	//判断是否显示元素
	$scope.show=true;
	
	$scope.ifShow=function(param){	
		$scope.show==true?$scope.show=false:$scope.show=true;
	}
	

	

		
	
	
	
	
	
	


	
}])
