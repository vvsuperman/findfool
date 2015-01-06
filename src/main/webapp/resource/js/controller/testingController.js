'use strict';
OJApp.controller('testingController',function ($scope,$http,Data,$routeParams,$timeout,$sce,$compile) {
	//根据头信息解析出测试id和用户id，检查有没有开始做测试
	
	 var param = strDec($routeParams.url, "1", "2", "3").split("|");
	 $scope.email = param[0];
	 $scope.tid = param[1];
	
	
	/*
	 $scope.email ="693605668@qq.com";
	 $scope.testid ="1";
	 $scope.tid = "12";
	 $scope.show = 2;
	 */
	 $scope.tuser = {};
	 $scope.loginUser={};
	 //$scope.loginUser.email="apachee@qq.com";

     $scope.schools = [];
	 $scope.question = {};
	 $scope.programCode = {};
	 $scope.answerCount ={total:0,sum:0};
	 $scope.timeremain = 0;
	  $scope.queryNum =5;
	 //检查该url是否合法
	 $http({
         url: WEBROOT+"/testing/checkurl",
         method: 'POST',
         data: {"email":$scope.email, "testid": $scope.tid}
     }).success(function (data) {
         //测试未开始
    	 if( data.state == 0){
    		 //试题已截至
    		 if(data.message ==1){
    			 $scope.show =5;
    		 }else{
    			 smoke.alert(data.message);
    		 }
    	 }
    	 else{
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
	         data: {"email":($scope.loginUser.email).replace(/(^\s*)|(\s*$)/g,''), "pwd": $scope.loginUser.pwd,"testid":$scope.tid}
	     }).success(function (data) {
	    	 if( data.state == 0){
	    		 //用户名或密码不匹配
	    		 if(data.message ==1){
	    			 $scope.show = 5;
	    		 }else if(data.message ==2){
	    		     smoke.alert("用户名、密码不匹配");
	    		     return false;
	    		 }else{
	    			 smoke.alert("用户不存在");
	    			 return false;
	    		 }
	    		 
	    	 }else if(data.state == 2){
	    		 $scope.errMsg = "";
	    		 //填写用户信息
	    		 $scope.show = 2;
	    		 $scope.tuser.tuid = data.message;
	    		 /*$http({
	    	         url: WEBROOT+"/testing/getSchools",
	    	         method: 'GET',
	    	         data: {"name":'ss',"email":$scope.loginUser.email, "pwd": $scope.loginUser.pwd,"testid":$scope.tid}
	    	     }).success(function (data) {
	    	    	 if(data.state==200)
	    	    		 $scope.schools=data.message;
	    	    	 else
	    	    		 alert(data.message);
	    	     });*/
	    	 }else{
	    		 //用户已开始做题了，跳转到做题页面
	    		 $scope.startTest();
	    	 }	 
	     }).error(function(){
	    	 console.log("login failed");
	     })
	 };

	 
	 //学校字段的自动补全功能
	 $scope.changeClass = function (options) {
         var widget = options.methods.widget();
         // remove default class, use bootstrap style
         widget.removeClass('ui-menu ui-corner-all ui-widget-content').addClass('dropdown-menu');
     };

		$scope.schoolOption = {
		    options: {
		        html: true,
		        minLength: 1,
		        onlySelect: true,
		        outHeight: 20,
		        source: function (request, response) {
		            //data = $scope.myOption.methods.filter(data, request.term);
		            if (!$scope.schools.length) {
		            	$scope.schools.push({
		                    label: '未发现',
		                    value: null
		                });
		            }		            
		            response($scope.schools);
		        }
		    }
		};
	 
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
	 };
	 
	 $scope.genExtraInfo = function(data){
		//生成用户的得分情况
	    	$scope.answerCount.total = $scope.tProblems.length;
	    	for(var i=0;i<$scope.tProblems.length;i++){
	    		if(typeof($scope.tProblems[i].useranswer)!=null && $scope.tProblems[i].useranswer!=""){
	    			$scope.answerCount.sum++;
	    		}
	    	}
	    	//生成用户的剩余时间
	    	var beginTime =data.message.invite.begintime;
	    	beginTime = (new Date(beginTime.replace(/\-/g,"/"))).getTime();
	    	var duration =data.message.invite.duration*60*1000;
	    	$scope.timeremain = beginTime+duration;
	 }
	 
	 $scope.startTest = function(){
    	 var sendData ={"email":$scope.email,"testid":$scope.tid}
		 $http({
	         url: WEBROOT+"/testing/starttest",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
	    	if(data.state!=0){
	    		$scope.show =4;
	    		$scope.tProblems = data.message.problems;
		    	$scope.submitAndFetch($scope.tProblems[0]);
		    	
		    	$scope.genExtraInfo(data);
		    	
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
	
     
//	 $scope.$startTest();
 	
     
    	 
    	 
     
     /*
      *获取试题信息
      *自动提交上一道试题答案
     */
     $scope.submitAndFetch= function(problem){
    	 //判断是否是第一道题，提交目前的试题
    	 if(typeof($scope.question.answer)!="undefined"){
    		 var useranswer ="";
    	
        	 for(var i in $scope.question.answer){
        		 if($scope.question.answer[i].isright ==true){
        			 useranswer+=1;
        		 }else{
        			 useranswer+=0;
        		 }
        	 }
    	 }
    	 
    	 
    	 var sendData = {"testid":$scope.tid,"email":$scope.email,"nowProblemId":problem.problemid,"problemid":$scope.question.qid,
    			 		 "useranswer":useranswer};
    	 
    	 $http({
	         url: WEBROOT+"/testing/submit",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
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
     $scope.fetchProblem= function(problem){
    	 //判断是否是第一道题，提交目前的试题
    	 var sendData = {"testid":$scope.tid,"email":$scope.email,"problemId":problem.problemid};
    	 $http({
	         url: WEBROOT+"/testing/fetchProblem",
	         method: 'POST',
	         data: sendData
	     }).success(function (data) {
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
    	 for( var i in $scope.tProblems){
    		 if($scope.tProblems[i].problemid == question.qid){
    			index = ++i;
    			break;
    		 }
    	 }
    	 $scope.answerCount.sum++;
    	 $scope.$broadcast("adddot",index);
    	 
    	 if(index<$scope.tProblems.length){
    		 $scope.submitAndFetch($scope.tProblems[index])
    	 }else{
    		 smoke.alert("已到最后一题，请仔细检查");
    	 }
    	 
     }
     
     /*
      * 编程题运行,而不提交，此时problem_id为0，若为0会执行测试用例
      * */
    $scope.run = function () {
       var solution = {};
       solution.problem_id = 0;
       solution.language = $scope.lg.context.lan;

       //solution.solution = $scope.proSolution;
       solution.user_id = $scope.tuser.tuid;
       solution.testid = $scope.tid;
       solution.solution = $scope.programCode.context;
       console.log("solution......",solution);
       $http({
           url: WEBROOT+'/solution/run',
           method: "POST",
           data: solution
       }).success(function (data) {
    	   console.log("data......",data);
           $scope.state = 'success';
           $scope.solution_id = data.message.msg;         
           $scope.query();
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
           		end = $timeout($scope.query,3000);
           }else{
          		$timeout.cancel(end);
               for (var i = 0; i < data.message.length; i++) {
                  $scope.result ="";
            	   // $scope.result += '<br>';
                   var res = data.message[i];
                  // $scope.result += "time:" + res.cost_time;
                  // $scope.result += " mem:" + res.cost_mem;
                   if (res.test_case == null) {
                       $scope.result += " error:" + res.test_case_result;
                   } else {
                      // $scope.result += " testCase:" + res.test_case;
                       $scope.result += res.test_case_result;
                   }
               }
           }
           $scope.RESULT = $sce.trustAsHtml($scope.result)
       }).error(function () {
           $scope.result += '<br>Try again'
       })
   };
     
    /* 
     * 完成所有测试
     * */
     $scope.finishTest = function(){
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
     };
     
	 
	
     
     $scope.lgs = [
                   {name: 'C', demo: 'resource/static/c.c', CodeType: 'c_cpp', lan: 0},
                   {name: 'C++', demo: 'resource/static/cpp.cpp', CodeType: 'c_cpp', lan: 1},
                   {name: 'Java', demo: 'resource/static/java.java', CodeType: 'java', lan: 3},
//                   {name: 'python', demo: 'resource/static/python.py', CodeType: 'python', lan: 6},
                   {name: 'C#', demo: 'resource/static/csharp.cs', CodeType: 'csharp', lan: 9}
               ];
     
     $scope.lg ={};
     $scope.lg.context =$scope.lgs[1];
     
     var codeTemplete ={"C":"#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}",
				"C++":"#include <stdio.h>\r\n int main(){\r\n 	return 0;\r\n}",
				"Java":"public class Solution {\r\n    public static void main(String[] args) {\r\n    }\r\n}",
				'C#':'public void Solution() {\r\n    Console.WriteLine("Hello World");\r\n}'
     }
     
    $scope.programCode.context=codeTemplete["C"];

 	$scope.$watch("lg.context",function(){
 		 //重新加载内容
    	 $scope.programCode.context = codeTemplete[$scope.lg.context.name];
     });
     
     
   
});




