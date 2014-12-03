OJApp.controller('testingController',function ($scope,$http,Data,$routeParams) {
	//根据头信息解析出测试id和用户id，检查有没有开始做测试
	
	/*
	 var param = strDec($routeParams.url, "1", "2", "3").split("|");
	 console.log("param...........",param);
	
	 $scope.email = param[0];
	 $scope.tid = param[1];
	 */
	 $scope.tuser = {};
	 $scope.question = {};
	 
	 
	 //测试数据
	 $scope.email ="693605668@qq.com";
	 $scope.tid = "1";
	
	 
	 
	 //检查该url是否合法
	 $http({
         url: WEBROOT+"/testing/checkurl",
         method: 'POST',
         data: {"email":$scope.email, "testid": $scope.tid}
     }).success(function (data) {
         //测试未开始
    	 if( data.state == 0){
    		 //该url无效
    		 alert(data.message);
    	 }else{
    		 $scope.show = 1;
    	 }	 
         
     }).error(function(){
    	 console.log("get data failed");
     })
     
     //登陆
	 $scope.login = function(){
		 $http({
	         url: WEBROOT+"/testing/login",
	         method: 'POST',
	         data: {"email":$scope.loginEmail, "pwd": $scope.loginPwd,"testid":$scope.tid}
	     }).success(function (data) {
	    	 if( data.state == 0){
	    		 //用户名或密码不匹配
	    		 $scope.errMsg = "用户名或密码不匹配";
	    	 }else if(data.state == 2){
	    		 //填写用户信息
	    		 $scope.show = 2;
	    		 $scope.tuser.tuid = data.message;
	    	 }else{
	    		 //用户已开始做题了，跳转到做题页面
	    	 }	 
	         
	     }).error(function(){
	    	 console.log("login failed");
	     })
	 }
	 
	
	 
	 //提交用户信息
	 $scope.submitUserInfo = function(){
		 var sendData ={"email":$scope.email,"testid":$scope.tid,"tuser":$scope.tuser}
		 $http({
	         url: WEBROOT+"/testing/submituserinfo",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
	    	 if( data.state == 0){
	    		 //用户名或密码不匹配
	    		 $scope.errMsg = "错误";
	    	 }else{
	    		 $scope.show = 3;
	    		 $scope.testInfo = data.message;
	    	 }	 
	         
	     }).error(function(){
	    	 console.log("login failed");
	     })
	 }
	
     
     //开始测试
     $scope.startTest = function(){
    	 var sendData ={"email":$scope.email,"testid":$scope.tid}
		 $scope.show=4;
		 $http({
	         url: WEBROOT+"/testing/starttest",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
	    	if(data.state!=0){
	    		$scope.tProblems = data.message;
		    	$scope.question = $scope.tProblems[1];
		    	$scope.submitAndFetch($scope.question);
	    	}else{
	    		alert(data.message);
	    	}
	    	
	     }).error(function(){
	    	 console.log("login failed");
	     })
		 
	 }
     
     $scope.startTest();
     
     /*
      *获取试题信息
      *自动提交上一道试题答案
     */
     $scope.submitAndFetch= function(problem){
    	 //判断是否是第一道题，提交目前的试题
    	 if(typeof($scope.question.answer)!="undefined"){
    		 var useranswer ="";
        	 for(var i in $scope.question.answer){
        		 if($scope.question.answer.istrue =="true"){
        			 useranswer+=1;
        		 }else{
        			 useranswer+=0;
        		 }
        	 }
    	 }
    	 var sendData = {"testid":$scope.tid,"email":$scope.email,"problemid":$scope.question.qid,
    			 		 "useranswer":useranswer,"nowProblemId":problem.problemid};
    	 
    	 $http({
	         url: WEBROOT+"/testing/submit",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
	    	$scope.question = data.message;
	     }).error(function(){
	    	 console.log("login failed");
	     })
    	 
     }
     
     /*
      * 用户直接点击获取下一道试题
      * */
     
     $scope.getNext = function(question){
    	 var index=0;
    	 for( var i in $scope.tProblems){
    		 if($scope.tProblems[i].problemid == question.qid){
    			index = ++i;
    			break;
    		 }
    	 }
    	 if(index<$scope.tProblems.length){
    		 $scope.submitAndFetch($scope.tProblems[index])
    	 }else{
    		 alert("已到最后一题，请仔细检查");
    	 }
    	 
     }
     
     $scope.finishTest = function(){
    	 var sendData ={"email":$scope.email,"testid":$scope.tid};
    	 $http({
	         url: WEBROOT+"/testing/finishtest",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
	    	if(data.state==1){
	    		alert("恭喜，已完成测试");
	    		//跳转到完成页面
	    	}else{
	    		alert(data.message);
	    	}
	     }).error(function(){
	     })
     }
     
     
	 
	 /*
	  * 辅助类，用于处理空字符串
	  * */
     $scope.dealNull = function(data){
		 if(data==""){
			 return 0;
		 }
	 }
	 
});