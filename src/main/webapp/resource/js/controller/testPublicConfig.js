
OJApp.controller('testPublicConfig',['$scope','$http','Data','$modal',function($scope, $http, Data,$modal) {
	
	$scope.url = '#/testConfig';
	$scope.active = 1;
	$scope.template = 'page/testpublic.html';
	$scope.ContentUs = 'page/contentUs.html';
	$scope.leftBar = 'page/testlistleftbar.html';
	$scope.defaultTags = [];
	$scope.customTags = [];
	$scope.tid = Data.tid();
	$scope.test = [];
	$scope.test.tail= "";
	$scope.time={};
	$scope.name = Data.tname();
	$scope.getconfig = function() {
		$http({
			url : WEBROOT + "/test/getconfig",
			method : 'POST',
			data : {
				"testid" : $scope.tid
			}
		}).success(function(data) {
			$scope.quiz = data["message"];
			$scope.time.startTime=$scope.quiz.startTime;
			$scope.time.endTime=$scope.quiz.endTime;
			$scope.time.duration=$scope.quiz.time;
			$scope.test.tail=$scope.quiz.description;		
		}).error(function() {
			console.log("get data failed");
		});
	}
	$scope.getconfig();
	
    
    $scope.publictest={};
    $scope.publictest.flag=0;
    
    //将挑战赛设为公有
    $scope.setPublic = function(){
    	if(!$scope.test.tail || !$scope.time.startTime || !$scope.time.endTime ){
    		flashTip("所有输入皆不可为空");
    		
    	}else{
    		$http({
                url: WEBROOT+"/test/setpub",
                method: 'POST',
        	    data: {"testid": $scope.tid,"publicFlag":$scope.publictest.flag}
            }).success(function (data) {
            	if(data.state ==0){
            		if(data.message!=""){
            			$scope.publictest.url = "http://foolrank.com/challenge/"+data.message;	
            			$scope.publictest.flag =1;
            		}else{
            			$scope.publictest.flag =0;
            		}
            	}else{
            		console.log(data.message);
            	}
            	
            }).error(function(){
           	 console.log("get data failed");
            });
    	}
    	
    }
    
    
  //保存公开挑战赛的信息
    $scope.setPublicConfig = function(){
    	if(!$scope.test.tail || !$scope.time.startTime || !$scope.time.endTime ){
    		flashTip("所有输入皆不可为空");
    		
    	}else{
    		$http({
                url: WEBROOT+"/test/setPublicConfig",
                method: 'POST',
        	    data: {"testid": $scope.tid,"testTail":$scope.test.tail,"starttime":$scope.time.startTime,"deadtime":$scope.time.endTime}
            }).success(function (data) {
                    flashTip("保存成功！");
                	$scope.getconfig();
                    
            	
            }).error(function(){
           	 console.log("get data failed");
            });
    	}
    	
    }
    
    
    
    $scope.upload = function (tname, userlist) {
        $http({
            url: WEBROOT+"/test/manage/invite",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            
//          data: {"user": {"uid": Data.uid()}, "subject": $scope.subject,"duration":$scope.duration, "replyTo": $scope.replyTo, "quizid": $scope.tnamelist[tname], "invite": userlist, "context": $scope.content}
            data: {"user": {"uid": Data.uid()}, "subject": $scope.subject,"duration":$scope.duration, "replyTo": $scope.replyTo, "quizid":Data.tid(), "invite": userlist, "context": $scope.content, "starttime": $scope.startTime, "deadtime": $scope.endTime}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
            	removeTip();
            	flashTip("邀请成功")
            } else {
            	flashTip($scope.message.msg)
            }
        }).error(function (data) {
        });	
    };
    
    //查看挑战赛是否是公有,若为空则给出
    $scope.checkPublic = function(){
    	$http({
            url: WEBROOT+"/test/checkpub",
            method: 'POST',
    	    data: {"testid": $scope.tid}
        }).success(function (data) {
        	if(data.state ==0){
        		if(typeof(data.message) !="undefined" && data.message!=""){
        			$scope.publictest.url = "http://foolrank.com/challenge/#/"+data.message;	
        			$scope.publictest.flag =1;
        		}else{
        			$scope.publictest.flag =0;
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
