OJApp.controller('ModalInstanceCtrl',function ($scope,$http,$modalInstance,Data,params) {
	//绑定变量到服务
	
	$scope.type = params.type;
	$scope.question = params.question;
	$scope.operation = params.operation;
	$scope.title = params.title;
	$scope.report = params.report;
	
	$scope.saveQustion = function () {
			
			 var answers = $scope.question.answer;
			 var rightanswer =""
			 for(var i =0;i<answers.length;i++){
				  	if(answers[i].isright == true  || answers[i].isright =="true"){
				  		rightanswer+=1;
				  	}else{
				  		rightanswer+=0;
				  	}
			 }
			 $scope.question.rightanswer = rightanswer;
			 var sendData={"quizid":$scope.tid,"user":{"uid": Data.uid()},"question":$scope.question};
			 
			 console.log("question..........",$scope.question);
			 $http({
		            url: WEBROOT+"/question/add",
		            method: 'POST',
		            headers: {
		                "Authorization": Data.token()
		            },
		            data: sendData
		        }).success(function (data) {
//		        	location.reload(); 
		        	console.log("更新试题.....");
		        }).error(function (data) {
		           console.log("获取数据错误");
		        });
		
		
		$modalInstance.dismiss('cancel');
	};
	
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	
});