/**
 * Created by liuzheng on 2014/7/13.
 */
$http({
    url: "/user/setting/query",
    method: 'POST',
    headers: {
        "Authorization": Data.token
    },
    data: {"uid": Data.uid}
}).success(function (data) {
    $scope.state = data["state"];//1 true or 0 false
    //Data.token = data["token"];
    $scope.message = data["message"];
    if ($scope.state) {
//仅需要对message中的数据做处理
    } else {

    }
}).error(function (data) {

});