OJApp.controller('testing',function ($scope,$http,Data,$routeParams) {
	//根据头信息解析出测试id和用户id，检查有没有开始做测试
	 var param = strDec($routeParams.r, 1, 2, 3).split("**");
	 $scope.email = param[0];
	 $scope.tid = param[1];
	 
	 $http({
         url: WEBROOT+"/testing/validate",
         method: 'POST',
         data: {"email": email, "testId": $scope.tid}
     }).success(function (data) {
         //测试未开始
    	 if( data["message"]=="notstart"){
        	 
         //测试已开始	 
         }else if(data["message"] =="alreadystart"){
        	 $http({
                 url: WEBROOT+"/testing/getanswer",
                 method: 'POST',
                 data: {"email": email, "testId": $scope.tid}
             }).success(function (data) {
            	 //取出用户的测试和以做过题的记录
            	 
            	 
             }).error(function(){
            	 console.log("get quizproblem failed");
             })
         };
     }).error(function(){
    	 console.log("get data failed");
     })
     
     //开始测试
     $scope.startTest = function(){
		 
	 }
	 
	 
	 //做题
	 $scope.submit = function(question){
		 
	 }
	 
	 
});