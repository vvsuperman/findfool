OJApp.controller('proModalInstanceCtrl',function ($scope,$http,$modalInstance,Data,params) {
	//绑定变量到服务
	
	
	$scope.question = params.question;
	$scope.operation = params.operation;
	$scope.title = params.title;
	$scope.report = params.report;
	
	console.log("question......",$scope.question);
	
	
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	
});