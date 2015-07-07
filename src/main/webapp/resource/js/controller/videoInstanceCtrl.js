OJApp.controller('videoInstanceCtrl',['$scope','modalInstance',function ($scope,$modalInstance) {
	
    $scope.videoplay =0;
    
//	$scope.$broadcast("videoplay");
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	
	
}]);