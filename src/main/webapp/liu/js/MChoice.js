/**
 * Created by liuzheng on 2014/7/22.
 */
function MChoice($scope, $sce, $http, Data, $routeParams) {
    $scope.url = '#/MChoice';
    $scope.template = 'MChoice.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';

//    $scope.context = "HTML 4.01 与 HTML 5的属性之间有差异的，下面不是HTML5中新增加的属性是（）。";
//    $scope.answer = [
//        {text: "AAAA", isright: false, score: 4},
//        {text: "BBB", isright: false, score: 4},
//        {text: "CCC", isright: true, score: 4}
//    ];
    $scope.main = 1;
    $scope.chapter = 0;
    if (!$routeParams.qqid) {
        $scope.main = 1;
        $scope.chapter = 0;
    } else {

        var a = $routeParams.qqid.split(",");
        $scope.main = 0;
        $scope.chapter = a[0];
        $scope.section = a[1];
        if (!$scope.section) {
            $scope.section = -2;
        }
    }
if($scope.chapter>0 && $scope.section > -1){
if(Data.qs()){
	$scope.allMC=Data.qs();
	$scope.xzt = [];
	$scope.bct = [];
	$scope.wdt = [];
	for (i in $scope.allMC) {
	if ($scope.allMC[i].type == 1) {
	$scope.xzt.push($scope.allMC[i])
                    } else if ($scope.allMC[i].type == 2) {
                        $scope.bct.push($scope.allMC[i])
                    } else if ($scope.allMC[i].type == 3) {
                        $scope.wdt.push($scope.allMC[i])
                    }
                }
                if ($scope.chapter == 1) {
                    $scope.context=$sce.trustAsHtml($scope.xzt[$scope.section].context);
                    $scope.answer=$scope.xzt[$scope.section].answer;
                } else if ($scope.chapter == 2) {
                    $scope.context=$sce.trustAsHtml($scope.bct[$scope.section].context);
                    $scope.answer=$scope.bct[$scope.section].answer;
                }else if ($scope.chapter == 3) {
                    $scope.context=$sce.trustAsHtml($scope.wdt[$scope.section].context);
                    $scope.answer=$scope.wdt[$scope.section].answer;
                }
	}
}else{
$scope.context = "";
$scope.answer="";
	$scope.xzt = [];
	$scope.bct = [];
	$scope.wdt = [];
}
    $scope.agree = function () {
        $scope.main = 0;
        $scope.chapter = 1;
        $scope.section = 0;
        $scope.getALL();

    };
    $scope.chose = function (an) {
        console.log($scope.answer[an]);
    };
    $scope.getALL = function () {
        $http({
            url: "/test/manage",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"user": {"uid": Data.uid()}, "quizid": 406}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
//仅需要对message中的数据做处理
                $scope.allMC = $scope.message.qs;
                $scope.quiz = $scope.message;
                
                window.location.href = $scope.url + '/1,0';
            } else if ($scope.chapter == 3) {
                $scope.context = $sce.trustAsHtml($scope.wdt[$scope.section].context);
                $scope.answer = $scope.wdt[$scope.section].answer;
            }
        }).error(function (data) {
            alert("测试数据");
            $scope.allMC = [
                {name: "选择题1", context: "选择题1<br />选择题1选择题1选择题1选择题1选择题1选择题1", answer: [
                    {text: "AAAA", isright: false, score: 4},
                    {text: "BBB", isright: false, score: 4},
                    {text: "CCC", isright: true, score: 4}
                ]},
                {name: "选择题2", context: "选择题2选择题2选择题2选择题2选择题2选择题2选择题2", answer: [
                    {text: "AAAA", isright: false, score: 4},
                    {text: "BBB", isright: false, score: 4},
                    {text: "CCC", isright: true, score: 4}
                ]}
            ];
        });
    };
    $scope.enter = function ($event) {
        if ($event.keyCode == 13) {
            $scope.start()
        }
    };
    $scope.start = function () {
        console.log($scope.MCemail);
        console.log($scope.MCpwd);
        if ($scope.MCemail && $scope.MCpwd) {
            $http({
                url: "/user/confirm",
                method: 'POST',
                data: {"email": $scope.MCemail, "pwd": md5($scope.MCpwd), "name": $scope.MCname}
            }).success(function (data) {
                $scope.state = data["state"];//1 true or 0 false

                var name = $scope.MCemail;
                $scope.message = data["message"];
                if (data["message"].handler_url != null && data["message"].handler_url !== "") {
                    name = data["message"].handler_url;
                }
                Data.setName(name);
                Data.setEmail($scope.MCemail);

                if ($scope.state) {
                    Data.setToken(data["token"]);
                    console.log(Data.token());
                    console.log(data["token"]);
                    Data.setUid($scope.message.uid);
                    Data.setPrivi($scope.message.privilege);
                    Data.setTel($scope.message.tel);
                    Data.setCompany($scope.message.company);
                    Data.setInvitedleft($scope.message.invited_left);
                    $scope.invitedleft = $scope.message.invited_left;
                    Data.setName($scope.message.handler_url);
                    Data.setQs($scope.message.qs);
                    $scope.name = Data.name();
                    window.location.href = $scope.url + '/1';
                } else {

                }

            }).error(function () {
                    alert("网络错误");
                    window.location.reload(true);
                }
            )
        }

    };
//    if ($scope.section>-1) {
//        $scope.getALL();
//    }

}