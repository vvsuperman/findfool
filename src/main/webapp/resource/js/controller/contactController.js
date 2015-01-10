OJApp.controller('contactController',function($scope,$http,Data){
	$scope.tname = Data.tname();
	$scope.suggest ={};
	$scope.contact ={};
	$scope.contact.show =1;
	
	$scope.addSuggest = function(){
		$scope.suggest.uid = Data.uid();
		  $http({
	            url: WEBROOT+"/suggest/add",
	            method: 'POST',
	            headers: {
	                "Authorization": Data.token()
	            },
	            data: $scope.suggest
	        }).success(function (data) {
	        	smoke.alert("感谢您宝贵的建议!");
	        	$scope.contact.show =1;
	        
			}).error(function (data) {
		        smoke.alert("获取数据错误");
		    });
	};
});