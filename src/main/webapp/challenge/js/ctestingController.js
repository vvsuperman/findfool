'use strict';

OJApp.controller('ctestingController',['$scope','$http','Data','$routeParams','$timeout','$sce','$compile','$interval',function ($scope,$http,Data,$routeParams,$timeout,$sce,$compile,$interval) {
	 var signedkey = $routeParams.signedkey;
	 
	 //通过signedkey获得testid
	 $http({
         url: WEBROOT+"/challenge/gettest",
         method: 'POST',
         data: sendData
     }).success(function (data) {
    	 if(data.state!=0){
    		 console.log(data.message);
    	 }else{
    		 
    	 }
     })
	
	 
	 $scope.genExtraInfo = function(data){
		//生成用户的完成题目
	    	$scope.answerCount.total = $scope.tProblems.length;
	    	for(var i=0;i<$scope.tProblems.length;i++){
	    		if(typeof($scope.tProblems[i].useranswer)!=null && $scope.tProblems[i].useranswer!="" 
	    			&& $scope.tProblems[i].useranswer!="0000" && $scope.tProblems[i].useranswer!="00000"){
	    			$scope.answerCount.sum++;
	    		}
	    	}
	    	//生成用户的剩余时间
	    	var beginTime =data.message.invite.begintime;	
	    	beginTime = (new Date(beginTime.replace(/\-/g,"/"))).getTime();
	    	var duration =data.message.invite.duration*60*1000;
	    	$scope.time.remain = beginTime+duration;
	    	$scope.$broadcast("countdown",$scope.time.remain);
	    	//定时器提醒
	    	var nowTime = (new Date()).getTime();
	    	var remain = $scope.time.remain - nowTime;
	    	//随机拍照
	         $scope.randomTakePicture(remain/60/1000);
//	    	 $scope.intervalTakePicture();
	         //结束测试
	    	$timeout($scope.endTest,remain);
	 }
	 
	 
	 $scope.stop = function(){
		     flashTip("测试已结束");
			 $scope.show = 5;
		 }
		
	 
	 $scope.startTest = function(){
    	 var sendData ={"email":$scope.email,"testid":$scope.tid}
		 $http({
	         url: WEBROOT+"/testing/starttest",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
	    	
	    	 if(data.state == 0){
	    		 $scope.show = 5;
	    		 return false;
	    	 }
	    	if(data.state!=0){
	    		$scope.tProblems = data.message.problems;
		    	$scope.submitAndFetch($scope.tProblems[0],1);
		    	$scope.genExtraInfo(data);
		    	$scope.show =4;
		    	//铺助数组，存储已完成的试题，用来判断已完成题数是否需要加一
		    	 $scope.sumArry=[];
		         for(var i=0;i<$scope.tProblems.length;i++){
		        	 if($scope.tProblems[i].useranswer!="" && $scope.tProblems[i].useranswer!="0000"){
		        		 $scope.sumArry[i] =1;
		        	 }else{
		        		 $scope.sumArry[i] =0;
		        	 }
		        	
		         }
		         var duration = data.message.invite.duration*60*1000;
	    	}
	    	else{
	    		if(data.message ==1){
	    			$scope.show = 5;
	    		}else{
	    			alert(data.message);
	    		}
	    		
	    	}
	    	
	     }).error(function(){
	    	 console.log("login failed");
	     })
	  
	 };
	 
	
	 
	 //规律的周期性拍摄，每30秒拍一次
	 $scope.intervalTakePicture=function(){
		 $interval(function(){
				$scope.$broadcast("takePicture");
				$scope.$broadcast("updatePicture","0");
		 },30*1000);
	 }
	 
	 
	
	 
     /*
      *获取试题信息
      *自动提交上一道试题答案
     */
     $scope.submitAndFetch= function(problem,index){
    	 //判断是否是第一道题，提交目前的试题
    	 var useranswer ="";
    	 if($scope.question.type ==1){
    		 if(typeof($scope.question.answer)!="undefined"){
            	 for(var i in $scope.question.answer){
            		 if($scope.question.answer[i].isright ==true){
            			 useranswer+=1;
            		 }else{
            			 useranswer+=0;
            		 }
            	 }
        	 }
    	 }else if($scope.question.type == 3){
    		 useranswer = $scope.question.useranswer;
    	 }
    	
    	 
    	 
    	 var sendData = {"testid":$scope.tid,"email":$scope.email,"nowProblemId":problem.problemid,"problemid":$scope.question.qid,
    			 		 "useranswer":useranswer,"index":index};
    	 
    	 $http({
	         url: WEBROOT+"/testing/submit",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
	    	 if(data.state == 0){
	    			$scope.show = 5
	    			 return false;
	    	 }
	    	 if(data.message.type ==1){
	    		 $scope.questionType =1;
	    	 }else if(data.message.type ==2){
	    		 $scope.questionType =2;
	    	 }else if(data.message.type ==3){
	    		 $scope.questionType =3;
	    	 }
	    	$scope.question = data.message;
	     }).error(function(){
	    	 console.log("login failed");
	     })
    	 
     }
     
     
     
     /*
      *获取一道试题信息
     */
     $scope.fetchProblem= function(problem,index){
    	 //判断是否是第一道题，提交目前的试题
    	 console.log("index.............",index);
    	 var sendData = {"testid":$scope.tid,"email":$scope.email,"problemId":problem.problemid,"index":index+1};
    	 $http({
	         url: WEBROOT+"/testing/fetchProblem",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
	    	 if(data.state == 0){
	    		 $scope.show = 5;
	    		 return false;
	    	 }
	    	 if(data.message.type ==1){
	    		 $scope.questionType =1;
	    	 }else if(data.message.type ==2){
	    		 $scope.questionType =2;
	    	 }else if(data.message.type ==3){
	    		 $scope.questionType =3;
	    	 }
	    	$scope.question = data.message;
	     }).error(function(){
	    	 console.log("login failed");
	     })
    	 
     }
     
     /*
      * 用户提交并获取下一道试题
      * */
    
     
     $scope.getNext = function(question){
    	 var index=0;
    	 for( var i=0;i< $scope.tProblems.length;i++){
    		 if($scope.tProblems[i].problemid == question.qid){
    			index = i+1;
    			
    			if($scope.sumArry[i]==0){
    				$scope.sumArry[i]=1;
    				$scope.answerCount.sum++;
    				$scope.$broadcast("adddot",index);
    			}
    			break;
    		 }
    	 }
    	 if(index<$scope.tProblems.length){
    		 $scope.submitAndFetch($scope.tProblems[index],index+1)
    	 }else{
    		 $scope.submitAndFetch($scope.tProblems[index-1],index+1);
    		 flashTip("以至最后一题，请仔细检查");
    	 }
    	 
     }
     
     /* 
      * 编程题运行,而不提交，此时problem_id为0，若不为0会执行测试用例
      * */
    $scope.run = function (data) {
       var solution = {};
       if(data=="test"){
    	   solution.submit =0;
       }else if(data == "submit"){
    	  
    	   solution.submit =1;
       }
       solution.problem_id = $scope.question.qid;
       solution.language = $scope.lg.context.lan;
       $scope.queryNum =5;
       //solution.solution = $scope.proSolution;
       solution.email =$scope.email
       solution.testid = $scope.tid;
       solution.solution = $scope.programCode.context;
       $http({
           url: WEBROOT+'/solution/run',
           method: "POST",
           data: solution
       }).success(function (data) {
    	   if($scope.state == 0){
    		   $scope.show = 5;
    		   return false;
    	   }
    	   
           $scope.state = 'success';
           $scope.solution_id = data.message.msg;         
          $timeout($scope.query,2000);
           $scope.RESULT = $sce.trustAsHtml($scope.result);

       }).error(function () {
           $scope.state = 'Try again';
           $scope.result = 'fail'
       });

   };
   
  
   $scope.query = function () {
       var solution = new Object();
       solution.solution_id = $scope.solution_id;
       --$scope.queryNum;
       
       $http({
           url: WEBROOT+"/solution/query",
           method: "POST",
           data: solution
       }).success(function (data) {
       	   var end;
           if (data.message.length == 0 && $scope.queryNum>0 ) {
           	   end = $timeout($scope.query,2000);
           }else{
          	   $timeout.cancel(end);
          	   $scope.result={}
          	   $scope.result.content = "";
          	   var resultInfos = data.message;
          	   //编译错误
          	   if(resultInfos.length ==1){
          		   $scope.result.content = resultInfos[0].test_case_result;
          	   }
          	   //运行了测试用例，循环输出结果和测试用例
               for (var i = 0; i < resultInfos.length; i++) {
            	   $scope.result.content += "测试用例"+i+":期望输出:"+resultInfos[i].testCaseExpected+
            	   ";实际输出:"+resultInfos[i].test_case_result+"\n";
               }
               $scope.result.display = $sce.trustAsHtml($scope.result.content)
           }
        
       }).error(function () {
    	   $scope.result.content += '<br>Try again'
       })
   };
     
    /* 
     * 完成所有测试
     * */
     $scope.finishTest = function(){
    	 smoke.confirm("你确定完成测试吗？",function(e){
    		 if(e){
    			 $scope.run('submit');
    	    	 $scope.endTest();
    		 }else{}
    	 },{
    		 ok:"是",
    		 cancel:"否",
    		 reverseButtons: true
    	 })
     };
     
     $scope.endTest = function(){
    	 var sendData ={"email":$scope.email,"testid":$scope.tid};
    	 $http({
	         url: WEBROOT+"/testing/finishtest",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
	    	if(data.state==0){
	    		//跳转到完成页面
	    		$scope.show = 5;
	    	}else{
	    		alert(data.message);
	    	}
	     }).error(function(){
	     })
     }
     
     $scope.lgs = [
                   {name: 'C', demo: 'resource/static/c.c', CodeType: 'c_cpp', lan: 0},
                   {name: 'C++', demo: 'resource/static/cpp.cpp', CodeType: 'c_cpp', lan: 1},
                   {name: 'Java', demo: 'resource/static/java.java', CodeType: 'java', lan: 3},
                   {name: 'php', demo: 'resource/static/php.php', CodeType: 'php', lan: 7},
                   
//                   {name: 'python', demo: 'resource/static/python.py', CodeType: 'python', lan: 6},
//                   {name: 'C#', demo: 'resource/static/csharp.cs', CodeType: 'csharp', lan: 9}
               ];
     
     $scope.lg ={};
     $scope.lg.context =$scope.lgs[0];
     
     var codeTemplete ={"C":"#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}",
				"C++":"using namespace std; \r\n #include <iostream>\r\n int main(){\r\n 	return 0;\r\n}",
				"Java":"public class Solution {\r\n    public static void main(String[] args) {\r\n    }\r\n}",
				"php":"<?php \r\n  \r\n  \r\n  \r\n  \r\n  \r\n  \r\n  \r\n   ?>",
				'C#':'public void Solution() {\r\n    Console.WriteLine("Hello World");\r\n}'
     }
     
    $scope.programCode.context=codeTemplete["C"];

 	$scope.$watch("lg.context",function(){
 		 //重新加载内容
    	 $scope.programCode.context = codeTemplete[$scope.lg.context.name];
     });
     
     
   
}]);

OJApp.controller("pagCtrl",['$scope',function($scope){
	
	$scope.totalItems = 41;
}])




