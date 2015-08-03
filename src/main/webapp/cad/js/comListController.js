/**
 * 客户端首页控制器
 */
OJApp.controller('comListController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	

	$scope.comListByType=function(){
		$http({
			url : WEBROOT + "/company/comListByType",
			method : 'POST',
			data : {
			
			}
		}).success(function(data) {
//			$scope.companyTail = data["message"];
			$scope.companyList=data["message"];
			
			                      
		}).error(function() {
			console.log("get data failed");
		})
		
	}

	$scope.comListByType();

	
	$scope.showCompanyTest=function(company){
		var url = "#/cad/eachcom/"+company.id;
		 window.location.href=url;
	}
	
	
	
	
}])