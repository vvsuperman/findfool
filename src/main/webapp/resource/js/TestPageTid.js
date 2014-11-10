/**
 * Created by liuzheng on 2014/7/26.
 */
function TestPageTid($scope, $routeParams, $http, Data) {
    $scope.url = '#/test';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.template = 'page/testlist.html';
    $scope.leftBar = 'page/leftBar.html';
    console.log('testDetail');
    $scope.tid = $routeParams.tid;
    Data.setTid($scope.tid);
    $scope.showDefault = false;
    $scope.showCustom = false;
    
    $scope.testManage = function () {
        //add by zpl
        var sendData = new Object();
        sendData.user = new Object();
        sendData.user.uid = Data.uid();
        sendData.quizid = Data.tid();
        $http({
            url: WEBROOT+"/test/manage",
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
            	console.log("user not authorition");
            }
        }).error(function (data) {
            console.log("get test info error");
        });
        $scope.template = 'page/testlist.html';
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
        $scope.template = 'page/commonsetting.html';
        $scope.ContentUs = 'page/contentUs.html';
//        $scope.active = target.getAttribute('data');
//        $scope.tid = $scope.active;
//
//        $scope.leftBar = 'leftBar.html';
//        $scope.name = $scope.tests[$scope.active].name;
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
        $scope.leftBar = 'page/leftBar.html';
        $scope.template = 'page/report.html';
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active].name;
        $scope.tid = $scope.tests[$scope.active].quizid;
        Data.setTid($scope.tid);
        Data.setTname($scope.name);
//        $scope.testManage();
    };
    
    $scope.addDefault = function () {
//        $scope.myu = 2;
    	console.log("addDefault");
	    $scope.showDefault = true;
    };
    $scope.addCustom = function () {
//        $scope.myu = 3;        
        console.log("add custom");
		$scope.showCustom = true;
    };
}
