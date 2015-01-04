OJApp.controller( "adminController",function($scope,$http,upload){
	$scope.showImport = false;
	
	$scope.showImportForm = function(){
		$scope.showImport = true;
	}	
	
	$scope.onSuccess = function(data){
		if(data.state == 0){
			smoke.alert(data.msg)
		}else{
			smoke.alert("恭喜你，导入成功");
		}
	}

});
