OJApp.controller('testConfig', function($scope, $http, Data, $modal) {

	$scope.url = '#/testConfig';
	$scope.active = 1;
	$scope.template = 'page/testconfig.html';
	$scope.ContentUs = 'page/contentUs.html';
	$scope.leftBar = 'page/testlistleftbar.html';

	$scope.defaultTags = [];
	$scope.customTags = [];

	$scope.tid = Data.tid();
	$scope.labels = [];
	$scope.emails = [];
	$scope.angmodel = [];
	$scope.angmodel.labeladded = "";
	$scope.angmodel.emailadded = "";

	$scope.name = Data.tname();

	$scope.updateTestLabels = function() {
		$http({
			url : WEBROOT + "/label/getlabels",
			method : 'POST',
			data : {
				"testid" : $scope.tid
			}
		}).success(function(data) {
			$scope.labels = data["message"];
			// console.log("labels:")
			// console.log($scope.labels);
		}).error(function() {
			console.log("get data failed");
		});
	}

	// $scope.getSystemLabels= function (){
	// $http({
	// url: WEBROOT+"/label/getsystemlabels",
	// method: 'POST',
	// data: {"testid": $scope.tid}
	// }).success(function (data) {
	// $scope.systemlabels=data["message"];
	// //console.log("labels:")
	// //console.log($scope.labels);
	// }).error(function(){
	// console.log("get data failed");
	// });
	// }

	$scope.orseclected = function(value) {

		if (value == true)
			return true;
		else
			return false;

	};

	$scope.updateQuizEmails = function() {
		$http({
			url : WEBROOT + "/quizemail/getemails",
			method : 'POST',
			data : {
				"testid" : $scope.tid
			}
		}).success(function(data) {
			$scope.emails = data["message"];
			// console.log("labels:")
			// console.log($scope.labels);
		}).error(function() {
			console.log("get data failed");
		});
	}

	$scope.updateTestLabels();
	$scope.updateQuizEmails();
	// $scope.getSystemLabels();

	$scope.saveConfig = function() {
		$http({
			url : WEBROOT + "/label/saveconfig",
			method : 'POST',
			data : {
				"testid" : $scope.tid,
				"labels" : $scope.labels
			}
		}).success(function(data) {
			flashTip("保存成功");
		}).error(function() {
			console.log("get data failed");
		});
	}

	$scope.findLableType = function(value) {
		$http({
			url : WEBROOT + "/label/findlabletype",
			method : 'POST',
			data : {
				"labelname" : value.labelname
			}
		}).success(function(data) {
			$scope.labeltype = data["message"];
		}).error(function() {
			console.log("get data failed");
		});
	}
	
	$scope.istype();
	$scope.istype=function(value){
		console.log("$scope.findLableType(value)");
		if($scope.findLableType(value)==0){return false;}else{return true;}
		
		
	}
	$scope.deleteLable = function(value) {
		$http({
			url : WEBROOT + "/label/deletelable",
			method : 'POST',
			data : {
				"testid" : $scope.tid,
				"labelname" : value.labelname
			}
		}).success(function(data) {
		}).error(function() {
			console.log("get data failed");
		});
	}

	$scope.saveConfigOne = function(value, value2) {

		if (value2 == 1) {
			value.isSelected = true;
			$scope.saveConfig();

		} else {

			value.isSelected = false;
			$scope.deleteLable(value);
			$scope.saveConfig();

		}
	}

	// $scope.findSystemLabel=function(value){
	// $scope.findLableType(value);
	// if($scope.labeltype==0)
	// {return true;}else{
	// return false;
	// }
	//    	
	//
	// }

	$scope.addLabel = function() {
		if ($scope.angmodel.labeladded == "") {
			flashTip("标签不能为空");
		} else {
			$http({
				url : WEBROOT + "/label/addlabel",
				method : 'POST',
				data : {
					"testid" : $scope.tid,
					"label" : $scope.angmodel.labeladded
				}
			}).success(function(data) {
				flashTip("添加成功");

				$scope.updateTestLabels();
				$scope.angmodel.labeladded = "";
			}).error(function() {
				console.log("get data failed");
			});
		}
	}
	$scope.addEmail = function() {
		$http({
			url : WEBROOT + "/quizemail/addemail",
			method : 'POST',
			data : {
				"testid" : $scope.tid,
				"email" : $scope.angmodel.emailadded
			}
		}).success(function(data) {
			if (data.state == 1) {
				flashTip("添加成功");
				$scope.updateQuizEmails();
				$scope.angmodel.emailadded = "";
			} else {
				$scope.angmodel.emailadded = "";
				flashTip(data.message);
			}
		}).error(function() {
			console.log("get data failed");
		});
	}
})