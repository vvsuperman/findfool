
OJApp.controller('cadLoginCtrl', [ '$scope', '$http', 'CadData',function($scope, $http, CadData) {
			// 手机的错误信息
			$scope.errorMsg = {};
			$scope.verifyQtn = {};



	$scope.confirm = function () {
        if ($scope.Lemail && $scope.Lpwd) {
				 //	console.log("1");
					var pwd = md5($scope.Lpwd);
					$http({
						url : WEBROOT + "/tuser/confirm",
						method : 'POST',
//						headers : {
//							"Authorization" : CadData.token()
//						},
						data : {
							"email" : $scope.Lemail,
							"pwd" : pwd
						}
					}).success(function(data) {
						$scope.state = data["state"];// 1 true or 0 false
						CadData.clear();// 清空缓存
						var name = $scope.Lname;
						$scope.message = data["message"];

//						CadData.setName($scope.message.username);
						CadData.setEmail($scope.Lemail);

						// 修改
						if ($scope.state == 0) {
//							CadData.setToken(data["token"]);
//							CadData.setLastActive((new Date()).getTime());
						} else {
							// window.location.href = '#/loginok';
							$scope.errmsg = data.message.msg;
						}
						var url = "#/cad/comlist";
						window.location.href = url;
					}).error(function() {
						console.log("err");
						alert("网络错误");
						// window.location.reload(true);
					})
				}
			};
		} ]);
