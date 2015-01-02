/**
 * Created by liuzheng on 2014/7/26.
 */
OJApp.controller('TestPageTid',function($scope, $routeParams, $http,$modal, Data) {
    $scope.url = '#/test';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.template = 'page/testlist.html';
    $scope.leftBar = 'page/leftBar.html';
    console.log('testDetail');
    $scope.tid = $routeParams.tid;
    
    $scope.contain ={};
    $scope.contain.show =1;
    
    Data.setTid($scope.tid);
    
    $scope.showDefault = false;
    $scope.showCustom = false;
    $scope.showButton = true;
    
    $scope.test ={};
    $scope.test.addAction = true;
    
    $scope.getTypeName = function(typeName){
    	if(typeName == 1){
    		return "选择题";
    	}else if(typeName ==2){
    		return "编程题";
    	}else{
    		return "no";
    	}
    }
    
    
    $scope.testManage = function () {
        //add by zpl
        var sendData = new Object();
        sendData.user = new Object();
        sendData.user.uid = Data.uid();
        sendData.quizid = Data.tid();
        $http({
            url: WEBROOT+"/test/manage",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: sendData
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            $scope.message = data["message"];
            if ($scope.state) {
                console.log($scope.message);
                $scope.qs = $scope.message.qs;
                $scope.isDisplay = new Array($scope.qs.length);
                for(i in $scope.isDisplay)
                	$scope.isDisplay[i]=false;
                $scope.testtime = $scope.message.testtime;
                $scope.extraInfo = $scope.message.extraInfo;
                $scope.emails = $scope.message.emails;
                $scope.name = $scope.message.name;
                Data.setTname($scope.name);
            } else {

            }
        }).error(function (data) {
            smoke.alert("获取数据错误");
        });
        $scope.template = 'page/testlist.html';
        $scope.myu = 1;
    };
    $scope.testManage();
//    $scope.testPage = function (target) {
//        console.log('testPage');
//        $scope.template = 'testlist.html';
//        $scope.leftBar = 'leftBar.html';
//        $scope.testManage()
//    };
    $scope.CommonSetting = function (target) {
        $scope.template = 'page/commonsetting.html';
        $scope.ContentUs = 'page/contentUs.html';
//        $scope.active = target.getAttribute('data');
//        $scope.tid = $scope.active;
//
//        $scope.leftBar = 'leftBar.html';
//        $scope.name = $scope.tests[$scope.active].name;
    };


    $scope.Invite = function (target) {
        console.log('Invite');
        window.location.href = '#/invite';
    };
    $scope.MultInvite = function (target) {
        console.log('MultInvite');
        window.location.href = '#/invite';
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active].name;
        $scope.tid = $scope.tests[$scope.active].quizid;
        Data.setTid($scope.tid);
        Data.setTname($scope.name);
//        $scope.active = target.getAttribute('data');
//        $scope.tid = target.getAttribute('data');
//
//        $scope.active = target.getAttribute('data');
//        $scope.name = $scope.tests[$scope.active].name;
//        $scope.template = 'MutleInvite.html';
//        $scope.leftBar = 'leftBar.html';
    };
    $scope.Report = function (target) {
        console.log('Report');
        $scope.leftBar = 'page/leftBar.html';
        $scope.template = 'page/report.html';
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active].name;
        $scope.tid = $scope.tests[$scope.active].quizid;
        Data.setTid($scope.tid);
        Data.setTname($scope.name);
//        $scope.testManage();
    };
    
    $scope.panel={};
    $scope.panel.show="";
    
    
    $scope.showDefaultPanel = function(){
    	console.log("show default panel");
    	$scope.panel.show = "default";
    }
    
    
   $scope.showCustomPanel = function(){
    	console.log("show custom panel");
    	$scope.panel.show = "custom";
    }
   
   
   //将试题添加到测试
   $scope.addQuestionToTest = function(q,$event){
		 var sendData={"quizid":$scope.tid,"problemid":q.qid};
		 $http({
	            url: WEBROOT+"/test/addquestion",
	            method: 'POST',
	            headers: {
	                "Authorization": Data.token()
	            },
	            data: sendData
	        }).success(function (data) {
	        	if(data.message == "success"){
	        		$scope.qs.push(q);
	        		smoke.alert("添加试题成功");
	        	}else{
	        		smoke.alert(data.message);
	        	}
	        }).error(function (data) {
	           console.log("获取数据错误");
	        });
		//阻止事件传播
		// $event.stopPropagation();
	}
    
    //by fw 修改混乱逻辑，将方法从testpage移入
	//查看和修改试题的通用方法
	$scope.modifyQuestionInTest = function (size,q,params) {
	   	var question = jQuery.extend(true, {}, q);
		 var modalInstance = $modal.open({
		      templateUrl: 'page/myModalContent.html',
		      controller: 'ModalInstanceCtrl',
		      size: size,
		      resolve: {
		          params:function(){
		        	  var obj ={};
		        	  obj.operation = params.operation;
		        	  obj.title=params.title;
		        	  obj.question = question;
		        	  return obj;
		          }
		      }
		 });
	 };
	 
	 $scope.deleteQuestionFromTest = function(question){
		 var sendData = {"quizid":$scope.tid,"problemid":question.qid};
		 console.log("sendData..........",question);
		 $http({
	            url: WEBROOT+"/test/delquestion",
	            method: 'POST',
	            headers: {
	                "Authorization": Data.token()
	            },
	            data: sendData
	        }).success(function (data) {
	            smoke.alert("试题已从测试中删除");	            
	            location.reload();
	        }).error(function (data) {
	            smoke.alert("获取数据错误");
	        });
	 }

	 $scope.displayQuestionDetails = function(index){
		 for(i in $scope.qs){
			 $scope.isDisplay[i]=false;
		 }
		 $scope.isDisplay[index]=true;
	 }

	 $scope.hideQuestionDetails = function(index){
		 $scope.isDisplay[index]=false;
	 }
	 
		 
    $scope.isAdded = function (qid){
    	for(i in $scope.qs){
    		if($scope.qs[i].qid == qid) return true;
    	}
    	return false;
    }
    
    $scope.moveUp =  function(index){
    	if(index == 0){
    		smoke.alert("已是第一题，不可前移");
    	}else{
    		var q = $scope.qs[index];
        	$scope.qs[index] = $scope.qs[index-1];
        	$scope.qs[index-1] = q;
    	}
    	
    }
    
    $scope.moveDown =  function(index){
    	if(index >= $scope.qs.length-1){
    		smoke.alert("已是最后一题，不可后移");
    	}else{
    		var q = $scope.qs[index];
        	$scope.qs[index] = $scope.qs[index+1];
        	$scope.qs[index+1] = q;
    	}
    	
    }
 
});
