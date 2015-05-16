OJApp.controller('videoInstanceCtrl',function ($scope,$modalInstance) {
	
    $scope.videoplay =0;
    
//	$scope.$broadcast("videoplay");
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	
	
});