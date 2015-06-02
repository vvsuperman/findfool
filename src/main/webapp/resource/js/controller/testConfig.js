OJApp.controller('testConfig',function($scope, $http, Data,$modal) {
	$scope.url = '#/testConfig';
    $scope.active = 1;
    $scope.template = 'page/testConfig.html';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.leftBar = 'page/leftBar.html';

    $scope.defaultTags=[];
    $scope.customTags=[];
    

	$http({
        url: WEBROOT+"/label/getlabels",
        method: 'POST',
	     data: {"email":$scope.email, "testid": $scope.tid}
    }).success(function (data) {
     
        
    }).error(function(){
   	 console.log("get data failed");
    })
    
})