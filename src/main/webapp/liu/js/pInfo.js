/**
 * Created by liuzheng on 2014/7/11.
 */
function pInfo($scope, $http, Data) {
    $scope.url = '#/profile';
    $scope.template = 'profile.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    $scope.company = "";
    $scope.name = "";
    $scope.tel = "";
    $scope.renewpasswd = 0;
    $scope.renewPWD = function () {
        $scope.renewpasswd = 1 -$scope.renewpasswd;
    }

    $scope.getInfo = function () {
        $http({
            url: "/user/setting/query",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"uid": Data.uid()}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
                $scope.email = $scope.message.email;
                $scope.company = $scope.message.company;
                $scope.name = $scope.message.name;
                $scope.tel = $scope.message.tel;
                Data.setCompany($scope.company);
                Data.setName($scope.name);
                Data.setTel($scope.tel);
				Data.setEmail($scope.email);
            } else {
            }
        }).error(function (data) {
            alert("测试数据");
            $scope.email = "liuzheng@ss.ss";
            $scope.company = "";
            $scope.name = "";
            $scope.tel = "";

        });
    };
    $scope.getInfo();

    $scope.sendInfo = function (name,company,tel,pwd,newpwd) {
        $scope.name = name;
        $scope.company = company;
        $scope.tel = tel;
//        console.log(pwd);
        if (!pwd){alert("请输入密码用于验证");return}
        $scope.pwd = pwd;
        $scope.newpwd = newpwd;
        if (!$scope.renewpasswd) {
            $scope.newPWD = md5($scope.pwd)
        } else {
            $scope.newPWD = md5($scope.newpwd)
        }
        $scope.PWD = md5($scope.pwd);
        $http({
            url: "/user/setting/set",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"user": {"uid": Data.uid()}, "name": $scope.name, "company": $scope.company, "tel": $scope.tel, "pwd": $scope.PWD, "newPWD": $scope.newPWD }

        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
//                console.log($scope.name);
                alert($scope.message.msg);
                Data.setName($scope.name);
//                console.log(Data.name);
                window.location.reload(true);
            } else {

            }
        }).error(function (data) {

        });
    };
}