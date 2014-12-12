/**
 * Created by liuzheng on 2014/7/11.
 */
OJApp.controller('TestPage',function($scope, $http, Data) {
    $scope.url = '#/test';
    $scope.active = 1;
    $scope.template = 'page/testshow.html';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.leftBar = '';
   
    
    $scope.tshow = function () {
        $http({
            url: WEBROOT+"/test/show",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"uid": Data.uid()}
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
            console.log("服务器错误");
        });
    };
    
    $scope.tshow();

    $scope.createNewTest = function () {
        $scope.template = 'addtest.html';
        $scope.ContentUs = 'contentUs.html';
        $scope.leftBar = '';
    };

/* 
 *  跳转到测试报告页面
 *   */
    $scope.report = function (test) {
        console.log("go to listreport",test);
        Data.setTid(test.quizid);
        Data.setTname(test.name);
        window.location.href="#/report" 
    };
    
/* 
 *  跳转到邀请页面
 *   */   
    $scope.inviteUser = function(test){
    	console.log("ivite user....",test);
    	  Data.setTid(test.quizid);
          Data.setTname(test.name);
          window.location.href="#/invite" 
    }

    $scope.pushTest = function(){
        //检查名字是否为空
        if($scope.addtest.name == ''){
            alert("名字不允许为空");
            return;
        }
        var senddata = new Object();
        senddata.user = new Object();
        senddata.user.uid=Data.uid();
        senddata.name = $scope.addtest.name;
        senddata.testtime = $scope.addtest.time;
        senddata.extrainfo = ''+($scope.addtest.school == true?'1':'')+
            ($scope.addtest.prof == true?',2':'')+
            ($scope.addtest.project == true?',3':'')+
            ($scope.addtest.link == true?',4':'');
        senddata.emails = $scope.addtest.eamils+
            ($scope.addtest.defaultEmail==true? Data.email():'');

        //发送
        $http({
            url: WEBROOT+"/test/add",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: senddata
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            $scope.message = data["message"];
            if ($scope.state) {
                alert('添加成功');
                //跳转到testdetail
                console.log('testDetail');
                $scope.name = senddata.name;
                $scope.tid = $scope.message.msg;
                Data.setTid($scope.tid);
                Data.setTname($scope.name);
                $scope.testManage();
                window.location.href = '#/test/'+$scope.tid ;
//                $scope.template = 'testlist.html';
//                $scope.leftBar = 'leftBar.html';
            } else {
                alert('error:'+$scope.message.msg);
            }
        }).error(function (data) {
            //relogin
        });

    };
   


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
    };

})

