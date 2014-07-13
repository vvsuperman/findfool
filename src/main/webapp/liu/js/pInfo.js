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
    $scope.getInfo = function () {
        $http({
            url: "/user/setting/query",
            method: 'POST',
            headers: {
                "Authorization": Data.token
            },
            data: {"uid": Data.uid}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
                $scope.email = $scope.message.email;
                $scope.company = $scope.message.company;
                $scope.name = $scope.message.name;
                $scope.tel = $scope.message.tel;
                Data.email = $scope.email;
                Data.company = $scope.company;
                Data.name = $scope.name;
                Data.tel = $scope.tel;
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
        $scope.pwd = pwd;
        $scope.newpwd = newpwd;
        if ($scope.newpwd=="") {
            $scope.newPWD = md5($scope.pwd)
        } else {
            $scope.newPWD = md5($scope.newpwd)
        }
        $scope.PWD = md5($scope.pwd);
        $http({
            url: "/user/setting/query",
            method: 'POST',
            headers: {
                "Authorization": Data.token
            },
            data: {"user": {"uid": Data.uid}, "name": $scope.name, "company": $scope.company, "tel": $scope.tel, "pwd": $scope.PWD, "newPWD": $scope.newPWD }

        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
                alert($scope.message.msg)
            } else {

            }
        }).error(function (data) {

        });
    };
}