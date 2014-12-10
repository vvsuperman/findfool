OJApp.controller('LoginOk',function($scope, $http,Data,$sce){
	$scope.url = '#/loginOk';
    $scope.template = 'loginOk.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = 'leftBar1.html';
//add by zpl
	var flag = Data.flag();
	if(flag == 0){
		Data.setFlag(1);
		window.location.reload();
	}else{
		Data.setFlag(0);		
	}
	window.location.href = '#/test';
})