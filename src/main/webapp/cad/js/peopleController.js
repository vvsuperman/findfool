/**
 * 客户端首页控制器
 */
OJApp.controller('peopleController',['$scope','$http','CadData','Data','CadData',function ($scope,$http,CadData,Data,CadData) {
	
	$scope.nav = 'cad/page/cadnav.html';

	$scope.template= 'cad/page/cadPeopleTail.html';

		
    var peopleTail=[
                    
                    {"challengeName":"java特工挑战赛","companyName":"上海腾飞科技有限公司","challengeTime":"2015-8-10 13:20","score":"135","rank":"5"},
                    {"challengeName":"C#极限挑战","companyName":"上海大众点评网络科技有限公司","challengeTime":"2015-9-10 13:20","score":"222","rank":"12"},
                    {"challengeName":"上海安卓设计挑战","companyName":"上海一棵树科技有限公司","challengeTime":"2015-7-05 13:20","score":"235","rank":"5"},
                    {"challengeName":"程序设计挑战赛","companyName":"上海东梦科技有限公司","challengeTime":"2015-7-01 13:20","score":"124","rank":"31"},
                    {"challengeName":"c语言设计挑战大赛","companyName":"上海腾飞科技有限公司","challengeTime":"2015-6-10 13:20","score":"135","rank":"13"},
                    {"challengeName":"asp.net挑战赛","companyName":"上海创飞有限公司","challengeTime":"2015-6-10 13:20","score":"135","rank":"41"},
                    {"challengeName":"梦飞java极客挑战","companyName":"上海一点同科技有限公司","challengeTime":"2015-6-10 13:20","score":"317","rank":"52"},
                    {"challengeName":"第五届C++杯挑战","companyName":"上海一科技术有限公司","challengeTime":"2015-6-10 13:20","score":"130","rank":"43"},
                    {"challengeName":"算法设计招聘挑战","companyName":"上海IT桔子有限公司","challengeTime":"2015-6-10 13:20","score":"214","rank":"53"}
                    
                                 
                    ];
		
   $scope.peopleTail=peopleTail;
	
	
	
	
	
	


	
}])
