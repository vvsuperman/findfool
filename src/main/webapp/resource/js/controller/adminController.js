OJApp.controller( "adminController",['$scope','$http','upload',function($scope,$http,upload){
	$scope.showImport = false;
	
	$scope.showImportForm = function(){
		$scope.showImport = true;
	}	
	
	$scope.onSuccess = function(data){
		if(data.state == 0){
			flashTip(data.msg)
		}else{
			flashTip("恭喜你，导入成功");
		}
	}

}]);
