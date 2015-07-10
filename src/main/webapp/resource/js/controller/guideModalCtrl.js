OJApp.controller('guideModalCtrl',['$scope','$http','modalInstance','Data','params',function ($scope,$http,$modalInstance,Data,params) {
	//绑定变量到服务	
	$scope.title = params.title;
	$scope.content = params.content;
	$scope.data = params.data;
	
	
	
	$scope.genQuiz = function(){
		$http({
            url: WEBROOT+"/test/genquiz",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {quizName:$scope.data}
        }).success(function (data) {
        	flashTip("创建测试成功");
        	location.reload();
        }).error(function (data) {
            //error
        	console.log("genQuizFailed");
        });
		
	}
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	
}]);