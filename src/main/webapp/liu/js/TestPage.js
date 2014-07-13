/**
 * Created by liuzheng on 2014/7/11.
 */
function TestPage($scope, $http, Data) {
    $scope.url = '#/test';
    $scope.active = 1;
    $scope.template = 'testshow.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    $scope.tshow = function () {
        $http({
            url: "/test/show",
            method: 'POST',
            headers: {
                "Authorization": Data.token
            },
            data: {"uid": Data.uid}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            $scope.message = data["message"];
            if ($scope.state) {
                $scope.tests = $scope.message.tests;
                for (var i = 0; i < $scope.tests.length; i++) {
                    $scope.tests[i].data = i;
                }
                console.log($scope.tests);
            } else {

            }
        }).error(function (data) {
            $scope.tests = [
                {quizid: 5, name: '现在网络错误', detail: 'i dont know', date: 1404284942000, emails: "524510356@qq.com", extraInfo: "1,2,3,4", owner: 29, time: 70, uuid: 3},
                {quizid: 5, name: '所以这只是一个', detail: 'i dont know', date: 1404284942000, emails: "524510356@qq.com", extraInfo: "1,2,3,4", owner: 29, time: 70, uuid: 3},
                {quizid: 5, name: '样例展示', detail: 'i dont know', date: 1404284942000, emails: "524510356@qq.com", extraInfo: "1,2,3,4", owner: 29, time: 70, uuid: 3}
            ];
            for (var i = 0; i < $scope.tests.length; i++) {
                $scope.tests[i].data = i;
            }
        });
    };
    $scope.tshow();

    $scope.createNewTest = function () {
        $scope.template = 'addtest.html';
        $scope.ContentUs = 'contentUs.html';
        $scope.leftBar = '';
    };
    $scope.Invite = function (target) {
        console.log('Invite');
        window.location.href = '#/invite';
    };
    $scope.MultInvite = function (target) {
        console.log('MultInvite');
        window.location.href = '#/invite';
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
    };
    $scope.testDetail = function (target) {
        console.log('testDetail');
        $scope.active = target.getAttribute('data');
        $scope.template = 'testlist.html';
        $scope.leftBar = 'leftBar.html';
        $scope.name = $scope.tests[$scope.active].name;
        $scope.tid = $scope.tests[$scope.active].quizid;
        Data.tid = $scope.tid;
        Data.tname = $scope.name;
        $scope.testManage();

    };

    $scope.createNewTest = function () {
        $scope.template = 'addtest.html';
        $scope.ContentUs = 'contentUs.html';
        $scope.leftBar = '';
        //author zpl
        //这个是处理增加一个测试的对象
        $scope.addtest = new Object();
        //增加测试的名字
        $scope.addtest.name = '';
        //是否自定义时间
        $scope.addtest.selfTime = false;
        //测试所需时间，默认为70
        $scope.addtest.time = 70;
        //额外信息
        $scope.addtest.school = false;
        $scope.addtest.prof = false;
        $scope.addtest.project = false;
        $scope.addtest.link = false;
        //是否使用默认email
        $scope.addtest.defaultEmail = true;
        //邮件列表
        $scope.addtest.eamils = '';
        
        //推向服务器
        $scope.pushTest = function(){
        	//检查名字是否为空
        	if($scope.addtest.name == ''){
        		alert("名字不允许为空");
        		return;
        	}
        	var senddata = new Object();
        	senddata.user = new Object();
        	senddata.user.uid=Data.uid;
        	senddata.name = $scope.addtest.name;
        	senddata.testtime = $scope.addtest.time;
        	senddata.extrainfo = ''+($scope.addtest.school == true?'1':'')+
        	($scope.addtest.prof == true?',2':'')+
        	($scope.addtest.project == true?',3':'')+
        	($scope.addtest.link == true?',4':'');
        	senddata.emails = $scope.addtest.eamils+
        				($scope.addtest.defaultEmail==true? Data.email:'');
        	
        	//发送
        	$http({
                url: "/test/add",
                method: 'POST',
                headers: {
                    "Authorization": Data.token
                },
                data: senddata
            }).success(function (data) {
                $scope.state = data["state"];//1 true or 0 false
                $scope.message = data["message"];
                if ($scope.state) {
                	alert('添加成功');
                	//跳转到testdetail
                    console.log('testDetail');
                    $scope.template = 'testlist.html';
                    $scope.leftBar = 'leftBar.html';
                    $scope.name = senddata.name;
                    $scope.tid = $scope.message.msg;
                    Data.tid = $scope.tid;
                    Data.tname = $scope.name;
                    $scope.testManage();
                } else {
                	alert('error:'+$scope.message.msg);
                }
            }).error(function (data) {
            	//relogin
            });
        	
        };
    };
    $scope.CommonSetting = function () {
        $scope.template = 'commonsetting.html';
        $scope.ContentUs = 'contentUs.html';
//        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active].uuid;

        $scope.leftBar = 'leftBar.html';
        $scope.name = $scope.tests[$scope.active].name;
    };


    $scope.testPage = function (target) {
        console.log('testPage');
        $scope.template = 'testlist.html';
        $scope.leftBar = 'leftBar.html';
        $scope.testManage()
    };
    $scope.testManage = function () {
    	//add by zpl
    	var sendData = new Object();
    	sendData.user = new Object();
    	sendData.user.uid = Data.uid;
    	sendData.quizid = Data.tid;
        $http({
            url: "/test/manage",
            method: 'POST',
            headers: {
                "Authorization": Data.token
            },
            data:sendData
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            $scope.message = data["message"];
            if ($scope.state) {
                $scope.qs = $scope.message.qs;
                $scope.testtime = $scope.message.testtime;
                $scope.extraInfo = $scope.message.extraInfo;
                $scope.emails = $scope.message.emails
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
    }
}