OJApp.controller('ModalInstanceCtrl',function ($scope,$http,$modalInstance,Data,params) {
	//绑定变量到服务
	
	$scope.question = params.question;
	$scope.operation = params.operation;
	$scope.title = params.title;
	$scope.report = params.report;
	
	$scope.saveQustion = function () {
			 var sendData={"quizid":$scope.tid,"user":{"uid": Data.uid()},"question":$scope.question};
			
			 $http({
		            url: WEBROOT+"/question/add",
		            method: 'POST',
		            headers: {
		                "Authorization": Data.token()
		            },
		            data: sendData
		        }).success(function (data) {
		        	alert("试题修改成功");
		        	location.reload(); 
		        }).error(function (data) {
		           console.log("获取数据错误");
		        });
		
		
		$modalInstance.dismiss('cancel');
	};
	
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	
});