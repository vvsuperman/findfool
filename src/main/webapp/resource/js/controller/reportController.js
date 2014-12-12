OJApp.controller('reportController',function ($scope,$http,Data,$routeParams) {
	$scope.showReport =1;
    $scope.ContentUs = 'page/contentUs.html';
    $scope.template = 'page/testreport.html';
    $scope.leftBar = 'page/leftBar.html';
    
    var testid = Data.tid();
	 
	$http({
         url: WEBROOT+"/report/list",
         method: 'POST',
         data: {"testid":testid}
     }).success(function (data) {
    	$scope.invites = data;
     }).error(function(){
    	 console.log("get data failed");
     })
     
     $scope.getScore = function(invite){
		 return invite.score+"/"+invite.totalscore
	 }
	 
	 $scope.getState = function(invite){
		 if(invite.state==0){
			 return "未完成";
		 }else if(invite.state ==1){
			 return "已完成";
		 }
	 }
	 
	 $scope.viewReport = function(invite){
		Data.setTid(invite.testid);
        Data.setInviteid(invite.iid);
        Data.setTuid(invite.uid);
        window.location.href="#/report/list";
	 }
});

OJApp.controller('reportListController',function ($scope,$http,Data,$routeParams) {
	$scope.showReport =2;
    $scope.ContentUs = 'page/contentUs.html';
    $scope.template = 'page/testreport.html';
    $scope.leftBar = 'page/leftBar.html';
    
    var testid = Data.tid();
    var inviteid = Data.inviteid();
    var tuid = Data.tuid();
	 
    $http({
        url: WEBROOT+"/report/overall",
        method: 'POST',
        data: {"testid":testid,"iid":inviteid,"uid":tuid}
    }).success(function (data) {
    	$scope.score = data.score;
    	$scope.rank = data.rank;
    	$scope.user = data.user;
    	$scope.labels = data.dimension.name;
    	$scope.data = data.dimension.val;
    	$scope.series = ['总分', '用户成绩'];
     	
    }).error(function(){
   	 console.log("get data failed");
    })
});

OJApp.controller('reportDetailController',function ($scope,$http,Data,$routeParams) {
	$scope.showReport =3;
    $scope.ContentUs = 'page/contentUs.html';
    $scope.template = 'page/testreport.html';
    $scope.leftBar = 'page/leftBar.html';
    
    var testid = Data.tid();
    var inviteid = Data.inviteid();
    var tuid = Data.tuid();
	 
    $http({
        url: WEBROOT+"/report/detail",
        method: 'POST',
        data: {"testid":testid,"iid":inviteid,"uid":tuid}
    }).success(function (data) {
    	$scope.questions = data;
    }).error(function(){
   	 console.log("get data failed");
    })
    
    
    $scope.judge = function(question){
    	if(question.useranswer == question.rightanswer){
    		return "正确";
    		$scope.color=1;
    	}else{
    		return "错误";
    		$scope.color=2;
    	}
    }
});