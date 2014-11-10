function importController($scope){
	$scope.showImportForm = function(){
		$scope.templete = "page/admin/batchimport.html";
	}	
	
	$scope.uploadFile1 = function(){
		console.log("import......."+ $scope.myFile);
		console.log("import....."+$scope.texttext);
	}
	
	 
}