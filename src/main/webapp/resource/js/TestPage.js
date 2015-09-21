/**
 * Created by liuzheng on 2014/7/11.
 */
OJApp.controller('TestPage',['$scope','$http','Data','$modal',function($scope, $http, Data,$modal) {
    $scope.url = '#/test';
    $scope.active = 1;
    $scope.template = 'page/testshow.html';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.leftBar = 'page/testsleftbar.html';
    $scope.addtest ={};
    $scope.addtest.user ={};
    $scope.addtest.testtime=70;
    $scope.panel={};
    $scope.panel.isShow=true;
    $scope.panel.isGuide=true;
    $scope.panel.body=0;
    $scope.panel.isBack=false;
    $scope.panel.trace=[];
    $scope.test={};
    $scope.test.isNewTest=false;
    $scope.guide={};
    $scope.guide.show=0; //测试
    
    
    $scope.genQuiz = function(data){
		$http({
            url: WEBROOT+"/test/genquiz",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {quizName:data}
        }).success(function (data) {
        	flashTip("创建测试成功");
        	location.reload();
        }).error(function (data) {
            //error
        	flashTip("genQuizFailed");
        });
		
	}
	
    
    $scope.viewGuideModal = function(data){
    	console.log("view guide modal.........",data);
    	 var modalInstance = $modal.open({
		      templateUrl: 'page/guideModal.html',
		      controller: 'guideModalCtrl',
		      resolve: {
		          params:function(){
		        	  var obj ={};
		        	  obj.title="技能介绍";
		        	  obj.content = $scope.guideArray[data];
		        	  obj.data =data;
		        	  return obj;
		          }
		      }
		     })
    }
    
    $scope.goNext = function (id) {	
    	$scope.panel.trace.push($scope.panel.body);	
    	$scope.panel.body=id;
    	$scope.panel.isBack=true;
    }
    
    $scope.goBack = function () {    	
		var id= $scope.panel.trace.pop();
		$scope.panel.body=id;
    	if($scope.panel.trace.length!=0)
    		$scope.panel.isBack=true;
    	else
    		$scope.panel.isBack=false;
    }
    //加载所有测试的信息
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
            } else {

            }
        }).error(function (data) {
            console.log("服务器错误");
        });
    };
    
    $scope.tshow();

   

/* 
 *  跳转到测试报告页面
 *   */
    $scope.report = function (test) {
        
        Data.setTid(test.quizid);
        Data.setTname(test.name);
        window.location.href="#/report/show/2" 
    };
    
/* 
 *  跳转到邀请页面
 *   */   
    $scope.inviteUser = function(test){
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
        
        $scope.addtest.user.uid = Data.uid();

        //发送
        $http({
            url: WEBROOT+"/test/add",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: $scope.addtest
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            $scope.message = data["message"];
            if ($scope.state) {
             
                //跳转到testdetail
                Data.setTid($scope.message.msg);
                Data.setTname($scope.addtest.name);
                $scope.testManage();
                window.location.href = '#/test/'+$scope.message.msg ;
//                $scope.template = 'testlist.html';
//                $scope.leftBar = 'leftBar.html';
            } else {
            	flashTip('error:'+$scope.message.msg);
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
}])

