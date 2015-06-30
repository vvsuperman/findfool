OJApp.controller('testConfig',function($scope, $http, Data,$modal) {
	
	$scope.url = '#/testConfig';
    $scope.active = 1;
    $scope.template = 'page/testconfig.html';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.leftBar = 'page/testlistleftbar.html';

    $scope.defaultTags=[];
    $scope.customTags=[];
    
    $scope.tid=Data.tid();
    $scope.labels=[];
    $scope.emails=[];
    $scope.angmodel=[];
    $scope.angmodel.labeladded="";
    $scope.angmodel.emailadded="";
    
    $scope.name = Data.tname();

    $scope.updateTestLabels= function (){
		$http({
	        url: WEBROOT+"/label/getlabels",
	        method: 'POST',
		    data: {"testid": $scope.tid}
	    }).success(function (data) {
	    	$scope.labels=data["message"];
	        //console.log("labels:")
	        //console.log($scope.labels);
	    }).error(function(){
	   	 console.log("get data failed");
	    });
    }
    
    $scope.updateQuizEmails= function (){
		$http({
	        url: WEBROOT+"/quizemail/getemails",
	        method: 'POST',
		    data: {"testid": $scope.tid}
	    }).success(function (data) {
	    	$scope.emails=data["message"];
	        //console.log("labels:")
	        //console.log($scope.labels);
	    }).error(function(){
	   	 console.log("get data failed");
	    });
    }
    
    $scope.updateTestLabels();
    $scope.updateQuizEmails();
    
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

            $scope.updateTestLabels();
            $scope.angmodel.labeladded="";
        }).error(function(){
       	 console.log("get data failed");
        });
    }
    $scope.addEmail = function (){
    	$http({
            url: WEBROOT+"/quizemail/addemail",
            method: 'POST',
    	    data: {"testid": $scope.tid,"email":$scope.angmodel.emailadded}
        }).success(function (data) {
        	if(data.state==1){
	            flashTip("添加成功");
	            $scope.updateQuizEmails();
	            $scope.angmodel.emailadded="";
        	}
        	else{
	            $scope.angmodel.emailadded="";
        		flashTip(data.message);
        	}
        }).error(function(){
       	 console.log("get data failed");
        });
    }
})