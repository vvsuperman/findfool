OJApp.controller( "adminController",function($scope,$http,upload){
	$scope.showImport = false;
	
	$scope.showImportForm = function(){
		$scope.showImport = true;
	}	

});