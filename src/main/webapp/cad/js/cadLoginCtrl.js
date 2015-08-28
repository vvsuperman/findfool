
OJApp.controller('cadLoginCtrl', [ '$scope', '$http', 'CadData','Data',function($scope, $http,CadData,Data) {
			// 手机的错误信息
			$scope.errorMsg = {};
			$scope.verifyQtn = {};
			$scope.Lemail;
			$scope.Lpwd;
		
	$scope.confirm = function () {
        if ($scope.Lemail && $scope.Lpwd) {
				 //	console.log("1");
					var pwd = md5($scope.Lpwd);
					$http({
						url : WEBROOT + "/tuser/confirm",
						method : 'POST',
//					headers : {
//						"Authorization" : CadData.getTestToken()
//					},
						data : {
							"email" : $scope.Lemail,
							"pwd" : pwd
						}
					}).success(function(data) {
						$scope.state = data["state"];// 1 true or 0 false
						CadData.clear();// 清空缓存
//						Data.clear();
						$scope.message = data["message"];
						console.log("执行了登录");
						// 修改
						if ($scope.state == 0) {
					//		Data.setTestEmail($scope.message.email);							
							CadData.setTestEmail($scope.message.email);
							 CadData.setCadTestname($scope.message.username);
							var url = "#/cad/comlist";
							window.location.href = url;
						
							
						} else  {
						
							$scope.errmsg = data.message.msg;
						}
						
					}).error(function() {
						console.log("err");
						alert("网络错误");
						// window.location.reload(true);
					})
				}
			};
		} ]);
