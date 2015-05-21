'use strict';

OJApp.controller('testingController',function ($scope,$http,Data,$routeParams,$timeout,$sce,$compile,$interval) {
	//根据头信息解析出测试id和用户id，检查有没有开始做测试
	 var param = strDec($routeParams.url, "1", "2", "3").split("|");
	 $scope.email = param[0];
	 $scope.tid = param[1];
	 //takePicture广播轮训的promise变量
	 var pictureTimer;
	
//测试数据	

//	 $scope.email ="693605668@qq.com";
//	 $scope.testid ="11";
//	 $scope.tid = "11";

	
//测试数据	 
	 $scope.tuser = {};
	 $scope.loginUser={};
	 //$scope.loginUser.email="apachee@qq.com";

     $scope.schools = [];
	 $scope.question = {};
	 $scope.programCode = {};
	 $scope.answerCount ={total:0,sum:0};
	 $scope.time={};
	 $scope.time.remain = 1;
	 
	 $scope.monitor =1;
	 

	 
	 $scope.btnShow =1;

	 $scope.btn ={};
	 $scope.btn.Show =1;
	 $scope.btnZone =0;
	 
	 $scope.showCamera = 1;
	 
	 $scope.showCZone = 1;//控制是否显示视频区域
	 
	 $scope.pictureOK = 0;//用户是否上传好照片
	 

	 
	 $scope.isCameraOk={};
	 $scope.isCameraOk.ok=0;	 
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
    			 flashTip(data.message);
    		 }
    	 }
    	 else{
    		 //$scope.show = 1;
    		//测试数据
    	     $scope.show = 2;
    	 }	 
         
     }).error(function(){
    	 console.log("get data failed");
     })
     
     
     //拍照
     $scope.takePicture = function(){
		 $scope.$broadcast("takePicture");
		 $scope.btn.Show =2;
		 $scope.monitor = 2;
		 console.log("广播takePicture");
	 }
	 
	 $scope.updatePicture = function(){
		 smoke.confirm("你确定要提交该照片？提交后不可修改！", function(e){
				if (e){
					$scope.$apply(
							function(){
							  $scope.btn.Show=3
							 });
					 $scope.$broadcast("updatePicture");
					 $scope.pictureOK =1;
				}
			}, {
				ok: "是",
				cancel: "否",
				classname: "custom-class",
				reverseButtons: true
			});
	 }
	 
	 $scope.cancelPicture = function(){
		 $scope.btn.Show =1;
		 $scope.monitor = 1;
	 }
	 
	 
	 //尝试开启摄像头
	 $scope.showVideo = function(){
		 $scope.showCZone =2;
		 $scope.$broadcast("takeVideo");

	 }
	
	 
     
	 //开启摄像头失败
	 $scope.$on("cameraErr",function(){
		 console.log("show camera");
	 });
	 
	 //定义控件是否显示变量
	 $scope.btnZone={};
	 $scope.btnZone.hide=0;
	 $scope.cameraZone={};
	 $scope.cameraZone.show=0;
	 //开启摄像头成功，开始捕捉视频
	 $scope.$on("cameraOK",function(){
		 $scope.$apply(function(){
			 $scope.isCameraOk.ok=1;
			 $scope.btnZone.hide = 1;
			 $scope.cameraZone.show=1;
		 });
		 console.log("cameraOk");
	 });
	 
	 //自动启动摄像头
	 $scope.$on("video",function(){
		 $scope.showVideo();
		 console.log("捕获video,调用showVideo()");
	 })
     
     //检测测试密码
	 $scope.login = function(){
		 $http({
	         url: WEBROOT+"/testing/checkpwd",
	         method: 'POST',
	         //data: {"email":($scope.loginUser.email).replace(/(^\s*)|(\s*$)/g,''), "pwd": $scope.loginUser.pwd,"testid":$scope.tid}
         	 data: {"email":$scope.email,"pwd": $scope.loginUser.pwd,"testid":$scope.tid}
	     }).success(function (data) {
	    	 if( data.state == 0){
	    		 //用户名或密码不匹配
	    		 if(data.message ==1){
	    			 $scope.show = 5;
	    		 }else if(data.message ==2){
	    			 flashTip("用户名、密码不匹配");
	    		     return false;
	    		 }else{
	    			 flashTip("用户不存在");
	    			 return false;
	    		 }
	    		 
	    	 }else if(data.state == 2){
	    		 $scope.invitedid=data.message["invitedid"];
	    		 $scope.errMsg = "";
	    		 //填写用户信息
	    		 $scope.show = 2;
	    		 //$scope.tuser.tuid = data.message;
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
		 if($scope.pictureOK!=1){
			 smoke.alert("请先拍照");
			 return false;
		 }
		 
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
	    	//定时器提醒
	    	var nowTime = (new Date()).getTime();
	    	var remain = $scope.time.remain - nowTime;
	    	//随机拍照
	         $scope.randomTakePicture(remain/60/1000);
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
		    	$scope.submitAndFetch($scope.tProblems[0]);
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
	 
	 //周期性随机拍照
	 $scope.randomTakePicture=function(duration){
		 //拍照间隔周期 (分钟)
		 var takePictureDuration=10;
		 var IntervalTime=takePictureDuration*60; //秒
		 console.log("intervalTime="+IntervalTime);
		 $interval(function(){
			 var randomTime=Math.floor(Math.random()*IntervalTime);	//秒
			 console.log("randomTime="+randomTime);	
			 $timeout(function(){
				$scope.$broadcast("takePicture");
				$scope.$broadcast("updatePicture");
			 },randomTime*1000);
		 },IntervalTime*1000);
	 }
	
	 
     /*
      *获取试题信息
      *自动提交上一道试题答案
     */
     $scope.submitAndFetch= function(problem){
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
    			 		 "useranswer":useranswer};
    	 
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
     $scope.fetchProblem= function(problem){
    	 //判断是否是第一道题，提交目前的试题
    	 var sendData = {"testid":$scope.tid,"email":$scope.email,"problemId":problem.problemid};
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
    		 $scope.submitAndFetch($scope.tProblems[index])
    	 }else{
    		 $scope.submitAndFetch($scope.tProblems[index-1]);
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
     
     
   
});

OJApp.controller("pagCtrl",function($scope){
	
	$scope.totalItems = 41;
})




