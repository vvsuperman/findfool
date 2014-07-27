/**
 * Created by liuzheng on 2014/7/26.
 */
function TestPageTid($scope, $routeParams, $http, Data) {
    $scope.url = '#/test';
    $scope.ContentUs = 'contentUs.html';
    $scope.template = 'testlist.html';
    $scope.leftBar = 'leftBar.html';
    console.log('testDetail');
    $scope.tid = $routeParams.tid;
    Data.setTid($scope.tid);
    $scope.testManage = function () {
        //add by zpl
        var sendData = new Object();
        sendData.user = new Object();
        sendData.user.uid = Data.uid();
        sendData.quizid = Data.tid();
        $http({
            url: "/test/manage",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: sendData
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            $scope.message = data["message"];
            if ($scope.state) {
                console.log($scope.message);
                $scope.qs = $scope.message.qs;
                $scope.testtime = $scope.message.testtime;
                $scope.extraInfo = $scope.message.extraInfo;
                $scope.emails = $scope.message.emails;
                $scope.name = $scope.message.name;
                Data.setTname($scope.name);
            } else {

            }
        }).error(function (data) {
            alert("现在使用的是展示数据");
            $scope.qs = [
                {qid: 6, name: '测试选择', type: 0, tag: [], context: "在下面的选项中选出正确的一项", answer: []},
                {qid: 9, name: '第二个测试', type: 0, tag: ["测试", "选择题"], context: "选择下面正确的一项", answer: [
                    {text: "1+1=2", isright: "1", score: 4},
                    {text: "1+1=3", isright: "0", score: 0},
                    {text: "1+1=4", isright: "0", score: 0}
                ]}
            ];
            $scope.testtime = 70;
            $scope.extraInfo = "1,2";
            $scope.emails = "ss@ss.com,sd@ss.com"
        });
        $scope.template = 'testlist.html';
        $scope.myu = 1;
    };
    $scope.testManage();
//    $scope.testPage = function (target) {
//        console.log('testPage');
//        $scope.template = 'testlist.html';
//        $scope.leftBar = 'leftBar.html';
//        $scope.testManage()
//    };
    $scope.CommonSetting = function (target) {
        $scope.template = 'commonsetting.html';
        $scope.ContentUs = 'contentUs.html';
//        $scope.active = target.getAttribute('data');
//        $scope.tid = $scope.active;
//
//        $scope.leftBar = 'leftBar.html';
//        $scope.name = $scope.tests[$scope.active].name;
    };
    $scope.comSave = function () {
        var qid = [];
        console.log($scope.qs);
        for (q in $scope.qs) {
            qid.push($scope.qs[q].qid)
        }
        qid.push($scope.reciveData.choosedQ.qid);
        var uqid = [];
        $.each(qid, function (i, el) {
            if ($.inArray(el, uqid) === -1) uqid.push(el);
        });
        $http({
            url: "/test/manage/submite",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"user": {"uid": Data.uid()}, "quizid": $scope.tid, "qids": uqid}
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

    $scope.Invite = function (target) {
        console.log('Invite');
        window.location.href = '#/invite';
    };
    $scope.MultInvite = function (target) {
        console.log('MultInvite');
        window.location.href = '#/invite';
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active].name;
        $scope.tid = $scope.tests[$scope.active].quizid;
        Data.setTid($scope.tid);
        Data.setTname($scope.name);
//        $scope.active = target.getAttribute('data');
//        $scope.tid = target.getAttribute('data');
//
//        $scope.active = target.getAttribute('data');
//        $scope.name = $scope.tests[$scope.active].name;
//        $scope.template = 'MutleInvite.html';
//        $scope.leftBar = 'leftBar.html';
    };
    $scope.Report = function (target) {
        console.log('Report');
        $scope.leftBar = 'leftBar.html';
        $scope.template = 'report.html';
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active].name;
        $scope.tid = $scope.tests[$scope.active].quizid;
        Data.setTid($scope.tid);
        Data.setTname($scope.name);
//        $scope.testManage();
    };

    $scope.tjkst = function () {
        $scope.myu = 2;
    };
    $scope.tjzdy = function () {
        $scope.myu = 3;
    };
}