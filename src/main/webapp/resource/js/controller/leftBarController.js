OJApp.controller('leftBarController',function($scope,Data){
	$scope.$watch("Data.tname()",function(){
		$scope.tname = Data.tname();
		
	});
	$scope.tid = Data.tid();
	
	
});