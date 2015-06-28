/**
 * 做题控制器
 */
OJApp.controller('cadtestingController',function ($scope,$http,CadData) {
	$scope.time={};
	
	$http({
        url: WEBROOT+"/cadquiz/starttest",
        method: 'POST',
        data: {"testid":CadData.getTestid(),"email":CadData.getEmail()}
    }).success(function (data) {
   	 if(data.state!=0){//试题已完成·
   		 flashTip(data.message);
   		 window.location.href='#/dp/testdetail';
   		 
   	 }else{
   		$scope.question = data.message.question;
   		$scope.cadInfo = data.message.cadInfo;
   		$scope.countdown($scope.question.limittime);
   	 }
   	
    }).error(function(){
   	 console.log("get data failed");
    })
    
    
    $scope.countdown = function(limittime){
		var beginTime = (new Date()).getTime();
    	var remain = beginTime+limittime*1000;;
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
		
		$http({
	        url: WEBROOT+"/cadquiz/answerQuestion",
	        method: 'POST',
	        data: {"testid":CadData.getTestid(),problemid:$scope.question.qid,"email":CadData.getEmail(),useranswer:useranswer}
	    }).success(function (data) {
	   	 if(data.state==1){
	   		 flashTip(data.message)
	   		 window.location.href='#/dp/testdetail';
	   	 }else{
	   		$scope.question = data.message.question;
	   		
	   		$scope.cadInfo = data.message.cadInfo;
	   		$scope.countdown($scope.question.limittime);
	   	 }
	   	
	    }).error(function(){
	   	 console.log("get data failed");
	    })
	}
	
	$scope.getNext = function(){
		$http({
	        url: WEBROOT+"/cadquiz/getquestion",
	        method: 'POST',
	        data: {"testid":CadData.getTestid(),"email":CadData.getEmail()}
	    }).success(function (data) {
	   	 if(data.state!=0){
	   		 $scope.errmsg = data.message;
	   	 }else{
	   		$scope.question = data.message;
	   		$scope.countdown($scope.question.limittime);
	   	 }
	   	
	    }).error(function(){
	   	 console.log("get data failed");
	    })
	}
	
})