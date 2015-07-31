
OJApp.controller('testConfig',['$scope','$http','Data','$modal',function($scope, $http, Data,$modal) {
	
	$scope.url = '#/testConfig';
	$scope.active = 1;
	$scope.template = 'page/testconfig.html';
	$scope.ContentUs = 'page/contentUs.html';
	$scope.leftBar = 'page/testlistleftbar.html';

	$scope.defaultTags = [];
	$scope.customTags = [];

	$scope.tid = Data.tid();
	$scope.labels = [];
	$scope.emails = [];
	$scope.angmodel = [];
	$scope.angmodel.labeladded = "";
	$scope.angmodel.emailadded = "";

	$scope.name = Data.tname();

	$scope.updateTestLabels = function() {
		$http({
			url : WEBROOT + "/label/getlabels",
			method : 'POST',
			data : {
				"testid" : $scope.tid
			}
		}).success(function(data) {
			$scope.labels = data["message"];
			// console.log("labels:")
			// console.log($scope.labels);
		}).error(function() {
			console.log("get data failed");
		});
	}



	$scope.orseclected = function(value) {

		if (value == true)
			return true;
		else
			return false;

	};

	
	//动态样式
	$scope.setStyle = function(args) 
	    {
		if($scope.orseclected(args) == false)
		  { 
	    	 return 'btn btn-info btn-xs dropdown-toggle'; }
	         else if($scope.orseclected(args) ==true){ 
	        	 return 'btn  btn-xs disabled ';
	         } 
		};
	
	
	
	$scope.updateQuizEmails = function() {
		$http({
			url : WEBROOT + "/quizemail/getemails",
			method : 'POST',
			data : {
				"testid" : $scope.tid
			}
		}).success(function(data) {
			$scope.emails = data["message"];
			// console.log("labels:")
			// console.log($scope.labels);
		}).error(function() {
			console.log("get data failed");
		});
	}

	$scope.updateTestLabels();
	$scope.updateQuizEmails();
	

	$scope.saveConfig = function() {
		$http({
			url : WEBROOT + "/label/saveconfig",
			method : 'POST',
			data : {
				"testid" : $scope.tid,
				"labels" : $scope.labels
			}
		}).success(function(data) {
			
		}).error(function() {
			console.log("get data failed");
		});
	}

	$scope.istype=function(value){
	
	if(value.labeltype==0)
	{return true;
	
	}else{
		return false;}
	
	
}

	$scope.deleteLable = function(value) {
		$http({
			url : WEBROOT + "/label/deletelable",
			method : 'POST',
			data : {
				"testid" : $scope.tid,
				"labelname" : value.labelname
			}
		}).success(function(data) {
		}).error(function() {
			console.log("get data failed");
		});
	}

	$scope.saveConfigOne = function(value, value2) {

		if (value2 == 1) {
			value.isSelected = true;
			$scope.saveConfig();

		} else {

			value.isSelected = false;
			$scope.deleteLable(value);
			$scope.saveConfig();

		}
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
    
    
    //将挑战赛设为公有
    $scope.setPublic = function(){
    		$http({
                url: WEBROOT+"/test/setpub",
                method: 'POST',
        	    data: {"testid": $scope.tid,"publicFlag":$scope.publicFlag}
            }).success(function (data) {
            	if(data.state ==0){
            		if(data.message!=""){
            			$scope.pubUrl = "http://foolrank.com/challenge/"+data.message;	
            			$scope.publicFlag =1;
            		}else{
            			$scope.publicFlag =0;
            		}
            	}else{
            		console.log(data.message);
            	}
            	
            }).error(function(){
           	 console.log("get data failed");
            });
    	
    }
    
    
    //查看挑战赛是否是公有,若为空则给出
    $scope.checkPublic = function(){
    	$http({
            url: WEBROOT+"/test/checkpub",
            method: 'POST',
    	    data: {"testid": $scope.tid}
        }).success(function (data) {
        	if(data.state ==0){
        		if(data.message!=""){
        			$scope.publicFlag = "http://foolrank.com/challenge/#/"+data.message;	
        			$scope.publicFlag =0;
        		}else{
        			$scope.publicFlag =1;
        		}
        	}else{
        		console.log(data.message);
        	}
        	
        }).error(function(){
       	 console.log("get data failed");
        });
    }
    
    $scope.checkPublic();
    
}])
