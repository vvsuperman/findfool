/**
 * 客户端首页控制器
 */
OJApp.controller('cdPersonalController',['$scope','$http','CadData','Data',function ($scope,$http,CadData,Data) {
	

	$scope.nav = 'cad/page/cadnav.html'
	$scope.email=Data.email();
	
	$scope.testid="347";
	$scope.username="韩亚全";
	$scope.email="aaa";
	$scope.school="bbb";
$scope.company="ccc";
$scope.blog="ddd";
$scope.age="24";
	$scope.tel="121121";
	$scope.degree="fff";
	$scope.gratime="ggg";
	$scope.city="hhh";
	$scope.gender="jjj";
$scope.rollnumber="222";
$scope.gpa="kkk";
$scope.discipline="lll";
	
	console.log("js控制器已经执行");
	
	
	
	
	
	
	
	
	$scope.cdPersonalList=function(){
		$http({
			url : WEBROOT + "/personal/findAllList",
			method : 'POST',
			data : {
				'email':$scope.email
			}
		}).success(function(data) {
			$scope.map=data["message"];
			$scope.testUser=$scope.map.testUser;
			$scope.inviteList=$scope.map.inviteList;
       
		}).error(function() {
			console.log("get data failed");
		})
		
	}

//	$scope.cdPersonalList();

	
	$scope.showCompanyTest=function(company){
		var url = "#/cad/eachcom/"+company.id;
		 window.location.href=url;
	}
	
	
	//修改个人资料方法
	$scope.cdPersonalModify=function(){
		$http({
			url : WEBROOT + "/personal/modify",
			method : 'POST',
			data : {
				'testid':$scope.testid,
				'username':$scope.username,'email':$scope.email,'school':$scope.school,'company':$scope.company,
				'blog':$scope.blog,'age':$scope.age,'tel':$scope.tel,
				'degree':$scope.degree,'gratime':$scope.gratime,'city':$scope.city,'gender':$scope.gender,
				'rollnumber':$scope.rollnumber,'gpa':$scope.gpa,'discipline':$scope.discipline
			}
		}).success(function(data) {
			$scope.map=data["message"];
			$scope.testUser=$scope.map.testUser;
			$scope.inviteList=$scope.map.inviteList;
       
		}).error(function() {
			console.log("get data failed");
		})
		
	}
	
	
	$scope.cdPersonalModify();


	
}])
