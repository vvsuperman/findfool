OJApp.controller('leftBarController',['$scope','Data',function($scope,Data){
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
	
	$scope.inviteNum ={};
	$scope.inviteNum.invited = Data.getInvitedNum();
	$scope.inviteNum.process = Data.getProcessNum();
	$scope.inviteNum.finished = Data.getFinishedNum();
	
	$scope.$on("numChange",function(){
		$scope.inviteNum.invited = Data.getInvitedNum();
		$scope.inviteNum.process = Data.getProcessNum();
		$scope.inviteNum.finished = Data.getFinishedNum();
	});
	
	
	
	
	$scope.showQuestions = function(){
		$scope.question =true;
		window.location.href = '#/test/'+Data.tid()+","+Data.getIsRandom();
	}
	
	$scope.commonSet = function(){
		$scope.set =true;
		window.location.href = '#/testConfig';
	}
	
	$scope.	publicSet = function(){
		$scope.publicset =true;
		window.location.href = '#/testPublicConfig';
	}
	$scope.invited = function(state){
		if(state==1){
			$scope.lBar.state1= true;	
		}else if(state==2){
			$scope.lBar.state2 = true;
		}else if(state==3){
			$scope.lBar.state3 = true;
		}
		
		window.location.href = '#/report/show/'+state;
		
	}
	
}]);