OJApp.controller('leftBarController',function($scope,Data){
	$scope.$watch("Data.tname()",function(){
		$scope.tname = Data.tname();
		
	});
	$scope.tid = Data.tid();
	
	var hash=window.location.hash;
	if(hash=="#/test"){
		$scope.isTestActive=true;
	} else if(hash=="#/innertest"){
		$scope.isInnertestActive=true;
	} else if(hash=="#/mybank"){
		$scope.isMybankActive=true;
	} else if(hash="#bank") {
		$scope.isBankActive=true;
	}
	
	
});