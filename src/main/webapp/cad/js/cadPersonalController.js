/**
 * 客户端首页控制器
 */
OJApp.controller('cadPersonalController',['$scope','$http','CadData','Data','CadData',function ($scope,$http,CadData,Data,CadData) {
	

	$scope.nav = 'cad/page/cadnav.html';
	$scope.template= 'cad/page/cadPersonal.html';
console.log("执行了第1步");
	
		
	$scope.email=CadData.getEmail();
	console.log($scope.email);
	console.log("执行了第2步");
	
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
//				'tuid':$scope.testid,
//				'username':$scope.username,'email':$scope.email,'school':$scope.school,'company':$scope.company,
//			'blog':$scope.blog,'age':$scope.age,'tel':$scope.tel,
//				'degree':$scope.degree,'gratime':$scope.gratime,'city':$scope.city,'gender':$scope.gender,
//				'rollnumber':$scope.rollnumber,'gpa':$scope.gpa,'discipline':$scope.discipline
			
		}).success(function(data) {
//			$scope.map=data["message"];
//			$scope.testuser=$scope.map.testuser;
//			$scope.inviteList=$scope.map.inviteList;
			var url = "#/cad/personal";
			 window.location.href=url;
			 $scope.ifShow();
		}).error(function() {
			console.log("get data failed");
		})
		
	};
	
	
	//$scope.cdPersonalModify();

	//判断是否显示元素
	$scope.show=true;
	
	$scope.ifShow=function(param){
//		if(param==1){
//		$scope.show=false;
//		}else if(param==2){
//			$scope.show=true;
//			
//		}
		
		$scope.show==true?$scope.show=false:$scope.show=true;
	}
	

	

		
	
	
	
	
	
	


	
}])
