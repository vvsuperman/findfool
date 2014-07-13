/**
 * Created by liuzheng on 2014/7/11.
 */

function invite($scope, $http, Data) {
    $scope.url = '#/invite';
    $scope.template = 'invite.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
}
OJApp.filter('filterTest', function () {
    return function (items, v) {
        var r = [];
        if (v == "notSelect") {
            v = ""
        }
        angular.forEach(items, function (item) {
            if (item.test == v) {
                r.push(item);
            }
            /*else {
             if ((item.test == '') && (item.email == '')) {
             item.test = v;
             r.push(item)
             }
             }*/

        });
        return r;
    }
});
