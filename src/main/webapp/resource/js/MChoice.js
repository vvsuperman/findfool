/**
 * Created by liuzheng on 2014/7/22.
 */
function MChoice($scope, $sce, $http, Data, $routeParams) {
    $scope.url = '#/MChoice';
    $scope.template = 'MChoice.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    Data.setKey1("1");
    Data.setKey2("2");
    Data.setKey3("3");
    route = strDec($routeParams.r, Data.key1(), Data.key2(), Data.key3()).split(",");
    $scope.tid = route[0];
//    $scope.context = "HTML 4.01 与 HTML 5的属性之间有差异的，下面不是HTML5中新增加的属性是（）。";
//    $scope.answer = [
//        {text: "AAAA", isright: false, score: 4},
//        {text: "BBB", isright: false, score: 4},
//        {text: "CCC", isright: true, score: 4}
//    ];
    $scope.main = 1;
    $scope.chapter = 0;
    if (!$routeParams.r) {
        //$scope.main = 1;
        //$scope.chapter = 0;
        alert("错误链接，跳转到主页");
        window.location.href = '#/';
    } else {

        //var a = $routeParams.qqid.split(",");
        //$scope.main = 0;
        $scope.chapter = route[1];
        $scope.section = route[2];
        if (!$scope.section) {
            $scope.section = -2;
        }
    }
//    if ($scope.context) {
    if ($scope.chapter > 0 && $scope.section > -1) {
        if (Data.qs()) {
            $scope.allMC = Data.qs();
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
                if ($scope.xzt[$scope.section]) {
                    $scope.context = $sce.trustAsHtml($scope.xzt[$scope.section].context);
                    $scope.answer = $scope.xzt[$scope.section].answer;
                }
            } else if ($scope.chapter == 2) {
                if ($scope.bct[$scope.section]) {
                    $scope.context = $sce.trustAsHtml($scope.bct[$scope.section].context);
                    $scope.answer = $scope.bct[$scope.section].answer;
                }
            } else if ($scope.chapter == 3) {
                if ($scope.wdt[$scope.section]) {
                    $scope.context = $sce.trustAsHtml($scope.wdt[$scope.section].context);
                    $scope.answer = $scope.wdt[$scope.section].answer;
                }
            }
            if (!$scope.context) {
                $scope.context = "木有题目"
            }
            if (!$scope.answer) {
                $scope.answer = "木有题目"
            }
        }
//        } else {
//            $scope.context = "";
//            $scope.answer = "";
//            $scope.xzt = [];
//            $scope.bct = [];
//            $scope.wdt = [];
//        }

        if (!$scope.context) {
            $scope.context = "木有题目"
        }
        if (!$scope.answer) {
            $scope.answer = "木有题目"
        }
    }
    $scope.agree = function () {
        $scope.main = 0;
        $scope.chapter = 1;
        $scope.section = 0;
        $scope.getALL();

    };
    $scope.selected = function () {
        $scope.ans = Data.ans();
        console.log(Data.ans());
        console.log($scope.answer);
        $scope.chosen = {};
        for (i in $scope.ans[$scope.section]) {
            for (j in $scope.answer) {
                if ($scope.answer[j].caseId == $scope.ans[$scope.section][i]) {
                    $scope.chosen[j] = j;
                    break;
                }
            }
        }
        console.log($scope.chosen)
    };
    $scope.selected();
    $scope.chose = function (an) {
        $scope.ans = Data.ans();
        if ($.inArray($scope.answer[an].caseId, $scope.ans[$scope.section]) == -1) {
            if (!$scope.ans[$scope.section]) {
                $scope.ans[$scope.section] = []
            }
            $scope.ans[$scope.section].push($scope.answer[an].caseId);
            $scope.chosen[an]=an;
        } else {
            $scope.ans[$scope.section].splice($.inArray($scope.answer[an].caseId, $scope.ans[$scope.section]), 1);
            $scope.chosen[an]=-1;
        }
        Data.setAns($scope.ans);
        console.log(Data.ans());
    };
    $scope.getALL = function () {
        $http({
            url: WEBROOT+"/test/manage",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"user": {"uid": Data.uid()}, "quizid": $scope.tid}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
//仅需要对message中的数据做处理
                $scope.allMC = $scope.message.qs;
                Data.setQs($scope.allMC);
//                $scope.quiz = $scope.message;
                window.location.href = $scope.url + '/' + strEnc($scope.tid + ",1,0", Data.key1(), Data.key2(), Data.key3());
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
    $scope.getUrl = function (target) {
        window.location.href = $scope.url + '/' + strEnc($scope.tid + "," + $scope.chapter + "," + target, Data.key1(), Data.key2(), Data.key3());
    }
    $scope.getChar = function (target) {
        console.log(target)
        window.location.href = $scope.url + '/' + strEnc($scope.tid + "," + target + ",0", Data.key1(), Data.key2(), Data.key3());
    }

    $scope.start = function () {
        if ($scope.MCemail && $scope.MCpwd) {
            $http({
                url: WEBROOT+"/user/confirm",
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
                    Data.setUid($scope.message.uid);
                    Data.setPrivi($scope.message.privilege);
                    Data.setTel($scope.message.tel);
                    Data.setCompany($scope.message.company);
                    Data.setInvitedleft($scope.message.invited_left);
                    $scope.invitedleft = $scope.message.invited_left;
                    Data.setName($scope.message.handler_url);
                    Data.setQs($scope.message.qs);
                    $scope.name = Data.name();
                    window.location.href = $scope.url + '/' + strEnc($scope.tid + ",1,", Data.key1(), Data.key2(), Data.key3());
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
