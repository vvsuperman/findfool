OJApp.controller('leftBarController',function($scope,Data){
	$scope.$watch("Data.tname()",function(){
		console.log("name change");
		$scope.tname = Data.tname();
		
	});
	$scope.tid = Data.tid();
	
	
});