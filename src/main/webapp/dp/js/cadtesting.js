/**
 * 做题控制器
 */
OJApp.controller('cadtestingController',function ($scope,$http,CadData) {
	$scope.time={};
	
	 //判断用户是否登陆
	$scope.email = CadData.getEmail();
	if($scope.email =="" || typeof($scope.email)=="undefined" || $scope.email ==null){
		window.location.href='#/dp';
	}
	
	$http({
        url: WEBROOT+"/cadquiz/starttest",
        method: 'POST',
        data: {"testid":CadData.getTestid(),"email":CadData.getEmail()}
    }).success(function (data) {
    	if(data.state == 101){
        	smoke.alert(data.message);
        	window.location.href='#/dp/testmain';
        }
    	else if(data.state!=0){//试题已完成·
   		 smoke.alert(data.message);
   		 window.location.href='#/dp/testdetail';
   		 
   	 }else{
   		$scope.question = data.message.question;
   		$scope.getQuestionStar($scope.question);
   		$scope.cadInfo = data.message.cadInfo;
   		$scope.countdown($scope.question.limittime);
   	 }
   	
    }).error(function(){
   	 console.log("get data failed");
    })
    
    
    $scope.countdown = function(limittime){
		var beginTime = (new Date()).getTime();
    	var remain = beginTime+limittime*1000;;
    	console.log("limittime....",limittime);
   		$scope.$broadcast("countdown",remain);
	}
    
    
    $scope.$on("cdfinished",function(){
    	$scope.submit();
    })
    
    $scope.submit=function(){
		
		//将用户答案按照id排排序，并生成正确的useranswer
		var options = $scope.question.answer;
		var length = options.length
		for(var i=0;i<length;i++){
			for(var j=i;j<length-i;j++){
				if(options[j]>options[j+1]){
					var option = options[j];
					options[j] = options[j+1];
					options[j+1] = option;
				}
			}
		}
		
		var useranswer ="";
		
		for(var i=0;i<length;i++){
			if(options[i].isright){
				useranswer +="1";
			}else{
				useranswer +="0";
			}
		}
		//用户未提交
		if(useranswer=="0000"){
			smoke.alert("你还没有答题哦，怎么就提交了？如果这题不会，可以选择跳过～");
			return false;
		}
		
		$http({
	        url: WEBROOT+"/cadquiz/answerQuestion",
	        method: 'POST',
	        data: {"testid":CadData.getTestid(),problemid:$scope.question.qid,"email":CadData.getEmail(),useranswer:useranswer}
	    }).success(function (data) {
	    	if(data.state == 101){
	        	smoke.alert(data.message);
	        	window.location.href='#/dp/testmain';
	        }
	    	else if(data.state==1){
	   		 smoke.alert(data.message)
	   		 window.location.href='#/dp/testdetail';
	   	 }else{
	   		$scope.question = data.message.question;
	   		
	   		$scope.getQuestionStar($scope.question);
	   		
	   		$scope.cadInfo = data.message.cadInfo;
	   		$scope.countdown($scope.question.limittime);
	   	 }
	   	
	    }).error(function(){
	   	 console.log("get data failed");
	    })
	}
	
    
    $scope.getQuestionStar = function(question){
    	var star ="";
   		if(question.level ==1){
   		    star ="1star";
   		}else if(question.level == 2){
   			star ="2star";
   		}else if(question.level == 3){
   			star ="3star"
   		}
   		$scope.questionStar ="resource/static/"+star+".png"
   	
    }
    
	$scope.getNext = function(){
		$http({
	        url: WEBROOT+"/cadquiz/getquestion",
	        method: 'POST',
	        data: {"testid":CadData.getTestid(),"email":CadData.getEmail()}
	    }).success(function (data) {
	    	if(data.state == 101){
	        	smoke.alert(data.message);
	        	window.location.href='#/dp/testmain';
	        }
	    	else if(data.state!=0){
	   		 $scope.errmsg = data.message;
	   	 }else{
	   		$scope.question = data.message;
	   		$scope.getQuestionStar($scope.question);
	   		$scope.countdown($scope.question.limittime);
	   	 }
	   	
	    }).error(function(){
	   	 console.log("get data failed");
	    })
	}
	
})