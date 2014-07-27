/**
 * Created by liuzheng on 2014/7/27.
 */
function commonsetting($scope,$http,Data) {
    $scope.zdy=false;
    $scope.comSave = function () {
//        var qid = [];
//        console.log($scope.qs);
//        for (q in $scope.qs) {
//            qid.push($scope.qs[q].qid)
//        }
//        qid.push($scope.reciveData.choosedQ.qid);
//        var uqid = [];
//        $.each(qid, function (i, el) {
//            if ($.inArray(el, uqid) === -1) uqid.push(el);
//        });
        $scope.extrainfo = [];
        if($scope.school){$scope.extrainfo.push($scope.school)};
        if($scope.spcial){$scope.extrainfo.push($scope.spcial)};
        if($scope.exp){$scope.extrainfo.push($scope.exp)};
        if($scope.link){$scope.extrainfo.push($scope.link)};
        if (!$scope.zdy) {
            $scope.testtime = 70
        }
        $http({
            url: "/test/manage/setting/set",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"user": {"uid": Data.uid()}, "quizid": Data.tid(), "testtime": $scope.testtime, "extrainfo": $scope.extrainfo, "emails": $scope.emails}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
//仅需要对message中的数据做处理
//                alert($scope.message.msg);
                $scope.tid = $scope.message.quizid;
                window.location.href = '#/test/' + $scope.tid;
            } else {

            }
        }).error(function (data) {

        });
    };
}