/**
 * 做题控制器
 */
OJApp.controller('eachComController',['$scope','$http','CadData','$routeParams',function ($scope,$http,CadData,$routeParams) {

	var comid = $routeParams.comid;

	$scope.findAllTest=function(){
		$http({
			url : WEBROOT + "/company/findAllTest",
			method : 'POST',
			data : {
				"comid" :comid
			}
		}).success(function(data) {

			$scope.invites = data;
			console.log(data);
		}).error(function() {
			console.log("get data failed");
		})
		
	}
	
	
	
	
	
	
	
	
	
}])