OJApp.controller('cadLoginCtrl', [ '$scope', '$http', 'Data',
		function($scope, $http, Data) {
			// 手机的错误信息
			$scope.errorMsg = {};
			$scope.verifyQtn = {};

			$scope.confirm = function() {
				if ($scope.Lemail && $scope.Lpwd) {
					// console.log("1");
					var pwd = md5($scope.Lpwd);
					console.log(pwd);

					$http({
						url : WEBROOT + "/tuser/confirm",
						method : 'POST',
						headers : {
							"Authorization" : Data.token()
						},
						data : {
							"email" : $scope.Lemail,
							"pwd" : pwd
						}
					}).success(function(data) {
						console.log("success1");
						$scope.state = data["state"];// 1 true or 0 false
						Data.clear();// 清空缓存
						var name = $scope.Lname;
						$scope.message = data["message"];

						Data.setName($scope.message.username);
						Data.setEmail($scope.Lemail);

						// 修改
						if ($scope.state == 0) {
							Data.setToken(data["token"]);
							Data.setLastActive((new Date()).getTime());
							console.log("已经登录成功");

						} else {

							// window.location.href = '#/loginok';
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