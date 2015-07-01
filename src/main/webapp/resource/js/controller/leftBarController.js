OJApp.controller('leftbarController',function($scope,Data){
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
	
	$scope.lBar={};
	
//	$scope.question =1;
	
	$scope.showQuestions = function(){
		$scope.question =true;
		console.log("tid............",Data.tid());
		window.location.href = '#/test/'+Data.tid();
	}
	
	$scope.commonSet = function(){
		$scope.set =true;
		window.location.href = '#/testConfig';
	}
	
	$scope.invited = function(state){
		if(state==1){
			$scope.lBar.state1= true;	
		}
		
		window.location.href = '#/report/show/'+state;
		
	}
	
});