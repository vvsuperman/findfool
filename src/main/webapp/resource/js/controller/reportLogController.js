/**
 * 
 */

OJApp.controller('reportLogController',['$scope','$http','Data',function ($scope,$http,Data) {
	$scope.showReport =4;
	$scope.listNav=0;
	$scope.logNav=1;
	$scope.detailNav=0;
    $scope.ContentUs = 'page/contentUs.html';
    $scope.template = 'page/testreport.html';
    $scope.leftBar = 'page/leftBar.html';
    
    $http({
        url: WEBROOT+"/report/log",
        method: 'POST',
        headers: {
            "Authorization": Data.token()
        },
        data: {"inviteid":Data.inviteid()}
    }).success(function (data) {
    	$scope.logs = data.message;
    }).error(function(){
   	 console.log("get data failed");
    })
    
	
}])