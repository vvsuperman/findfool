OJApp.controller('testConfig',function($scope, $http, Data,$modal) {
	
	$scope.url = '#/testConfig';
    $scope.active = 1;
    $scope.template = 'page/testconfig.html';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.leftBar = 'page/leftBar.html';

    $scope.defaultTags=[];
    $scope.customTags=[];
    
    $scope.tid=Data.tid();
    $scope.labels=[];
    $scope.angmodel=[];
    $scope.angmodel.labeladded="";
    
    $scope.updateTestLabels= function (){
		$http({
	        url: WEBROOT+"/label/getlabels",
	        method: 'POST',
		    data: {"testid": $scope.tid}
	    }).success(function (data) {
	    	$scope.labels=data["message"];
	        
	    }).error(function(){
	   	 console.log("get data failed");
	    });
    }
    
    $scope.updateTestLabels();
    
    $scope.saveConfig = function(){
    	$http({
            url: WEBROOT+"/label/saveconfig",
            method: 'POST',
    	    data: {"testid": $scope.tid,"labels":$scope.labels}
        }).success(function (data) {
            flashTip("保存成功");            
        }).error(function(){
       	 console.log("get data failed");
        });
	}
    
    $scope.addLabel = function (){
    	$http({
            url: WEBROOT+"/label/addlabel",
            method: 'POST',
    	    data: {"testid": $scope.tid,"label":$scope.angmodel.labeladded}
        }).success(function (data) {
            flashTip("添加成功");
        }).error(function(){
       	 console.log("get data failed");
        });
    }
})