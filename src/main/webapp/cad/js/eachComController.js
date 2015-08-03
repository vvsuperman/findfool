/**
 * 公司详情控制器
 */
OJApp.controller('eachComController',['$scope','$http','CadData','$routeParams','Data',function ($scope,$http,CadData,$routeParams,Data) {

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

	
	
	
	
	
}])