/**
 * 做题控制器
 */
OJApp.controller('cadtestingController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	$scope.time={};
	
	$scope.showtext = 1;
	
	 //判断用户是否登陆

	$scope.email = CadData.getTestEmail();

	if($scope.email =="" || typeof($scope.email)=="undefined" || $scope.email ==null){
		window.location.href='#/dp';
	}
	
	$http({
        url: WEBROOT+"/cadquiz/starttest",
        method: 'POST',
        data: {"testid":CadData.getCadTestid(),"email":CadData.getTestEmail()}
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
    
    //倒计时提交
    $scope.$on("cdfinished",function(){
    	$scope.firsttime =0;
    	$scope.submit();
    })
    
    //双段提交标志位。如果用户提交空,首先提示不可提交。而后再提交；如果是从倒计时进来，则直接提交
    $scope.firsttime =1;
    
    $scope.submit=function(){
		
		//将用户答案按照id排排序，并生成正确的useranswer
		var options = $scope.question.answer;
		var length = options.length
		
		
		for(var i=0;i<length;i++){
			for(var j=i+1;j<length;j++){
				if(options[j].caseId<options[i].caseId){
					var option = options[i];
					options[i] = options[j];
					options[j] = option;
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
		if(useranswer=="0000" && $scope.firsttime ==1){
			$scope.firsttime =0;
			smoke.alert("你还没有答题哦，怎么就提交了？如果这题不会，可以选择跳过～");
			return false;
		}
		
		$http({
	        url: WEBROOT+"/cadquiz/answerQuestion",
	        method: 'POST',
	        data: {"testid":CadData.getCadTestid(),problemid:$scope.question.qid,"email":CadData.getTestEmail(),useranswer:useranswer}
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
	    
	    $scope.firsttime =1;
	}
	
    
    $scope.getQuestionStar = function(question){
    	var star ="";
   		if(question.level ==1){
   		    star ="dp1star";
   		}else if(question.level == 2){
   			star ="dp2star";
   		}else if(question.level == 3){
   			star ="dp3star"
   		}
   		$scope.questionStar ="resource/static/"+star+".png"
   	
    }
    
	$scope.getNext = function(){
		$http({
	        url: WEBROOT+"/cadquiz/getquestion",
	        method: 'POST',
	        data: {"testid":CadData.getCadTestid(),"email":CadData.getTestEmail()}
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
	
	$scope.comment={};
	
	$scope.submitComment = function(){
		
		if(typeof($scope.comment.txt) == "undefined" || $scope.comment.txt.replace(/\s+/g,"")==""){
			flashTip("吐槽不可为空");
			return false;
		}
		
		$http({
	        url: WEBROOT+"/comment/add",
	        method: 'POST',
	        data: {"email":CadData.getTestEmail(),"pid":0,"content":$scope.comment.txt,"stype":2,"sid":$scope.question.qid}
	    }).success(function (data) {
	    	flashTip("吐槽成功，感谢您的吐槽!");
	    	$scope.showtext =0;
	    }).error(function(){
	   	 console.log("get data failed");
	    })
	}
	
}])
