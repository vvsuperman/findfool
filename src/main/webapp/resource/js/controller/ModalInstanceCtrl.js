OJApp.controller('ModalInstanceCtrl',function ($scope,$http,$modalInstance,Data,params) {
	//绑定变量到服务
	
	console.log("disabled1...",params.disabled);
	$scope.question = params.question;
	$scope.read = params.read;
	$scope.operation = params.operation;
	$scope.title = params.title;
	$scope.fuck = "fuck";
	
	$scope.saveQustion = function () {
			 var sendData={"quizid":$scope.tid,"user":{"uid": Data.uid()},"question":question};
			 console.log("sendData",sendData);
			 $http({
		            url: WEBROOT+"/question/add",
		            method: 'POST',
		            headers: {
		                "Authorization": Data.token()
		            },
		            data: sendData
		        }).success(function (data) {
		        	alert("试题修改成功");
		        }).error(function (data) {
		           console.log("获取数据错误");
		        });
		
		
		$modalInstance.dismiss('cancel');
	};
	
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	
});