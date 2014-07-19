/**
 * Created by liuzheng on 2014/7/11.
 */

function invite($scope, $http, Data) {
    $scope.url = '#/invite';
    $scope.template = 'invite.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    $scope.text="Hello [FIRST_NAME] [LAST_NAME],<br/>In order to assess your programming skills we've prepared a programming challenge that we would like you to complete. <br/>    The following link takes you to your test:<br/> [PRIVATE_TEST_LINK] <br/>  After clicking the link you will be able to choose to start the test, practice with a demo test or come back later.  <br/>  Best of luck!  <br/> Regards,<br/>  [COMPANY_NAME]";
    $scope.clean = function () {
        $scope.text = "";
        document.getElementById("wmd-output").innerHTML = "<pre><code></code></pre>";
        document.getElementById("wmd-preview").innerHTML = "";
        document.getElementById("wmd-input").value="";
        $scope.html = "";
    }

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
