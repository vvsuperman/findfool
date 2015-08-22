/**
 * 做题控制器
 */
OJApp.controller('programController',['$scope','$http','CadData','$routeParams',function ($scope,$http,CadData,$routeParams) {
	
	$scope.nav = 'cad/page/cadnav.html'
    $scope.template = 'cad/page/program.html';
	
	$scope.programs =[{name:"算法"},{name:"数据结构"},{name:"数学"},{name:"人工智能"},{name:"C++"},{name:"Java"},{name:"Python"},{name:"Ruby"}]
	
    $scope.startTest = function(){
		smoke.alert("即将开放,敬请期待");
	}
	
}])
