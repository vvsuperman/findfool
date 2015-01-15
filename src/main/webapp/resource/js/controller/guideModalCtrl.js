OJApp.controller('guideModalCtrl',function ($scope,$http,$modalInstance,Data,params) {
	//绑定变量到服务	
	$scope.title = params.title;
	$scope.content = params.content;
	$scope.data = params.data;
	
	
	
	$scope.genQuiz = function(){
		
	}
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	
});