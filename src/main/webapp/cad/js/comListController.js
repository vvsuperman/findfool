/**
 * 客户端首页控制器
 */
OJApp.controller('comListController',['$scope','$http','CadData','Data',function ($scope,$http,CadData,Data) {
	

	$scope.nav = 'cad/page/cadnav.html'
	$scope.template = 'cad/page/comlist.html';
	
	$scope.comListByType=function(){
		$http({
			url : WEBROOT + "/company/comListByType",
			method : 'POST',
			data : {
			
			}
		}).success(function(data) {
//			$scope.companyTail = data["message"];
			$scope.companyList=data["message"];
//			$scope.username=Data.name();
//			$scope.email=Data.email();
//			          console.log(Data.name());         
		}).error(function() {
			console.log("get data failed");
		})
		
	}

	$scope.comListByType();

	
	$scope.showCompanyTest=function(company){
		var url = "#/cad/eachcom/"+company.id;
		 window.location.href=url;
	}
	
	
	
	
	$scope.renderList = function(status, challenges) {
		$http({
			url: WEBROOT + '/challenge/getListByStatus',
			method: 'POST',
			data: {
				'status': status
			}
		}).success(function(data) {
			if (data.state == 0) {
				console.log('1111');
				//challenges = data.message;
				$scope.unrunChallenges = data.message;
			}
		}).error(function() {
			alert("网络错误");
		});
	}
	
	$scope.renderList(1, null);
//	$scope.isLogin=function(param){
//		if(param==1){
//		if($scope.email==null){
//			return true;
//		}else{
//			return false;
//		}
//		}else{
//			
//			if($scope.email==null){
//				return false;
//			}else{
//				return true;
//			}		
//		}
//
//		
//	}
//	
//	$scope.loginOut=function(){
//		Data.clear();
//		
//		 window.location.reload();		
//	}
	
	
}])
