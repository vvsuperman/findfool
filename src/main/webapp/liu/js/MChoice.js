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
    }

    $scope.agree = function () {
        $scope.main = 0;
        $scope.chapter = 1;
        window.location.href = '#/MChoice/1';
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
            data: {"user": {"uid": Data.uid()}, "quizid": 394}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
//仅需要对message中的数据做处理
                $scope.allMC = $scope.message.qs;
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
                if ($scope.chapter==1) {
                    $scope.context = $sce.trustAsHtml($scope.xzt[$scope.section].context);

//        console.log($scope.section);
//        console.log($scope.allMC[$scope.section].context);
//        console.log($scope.context);
                    $scope.answer = $scope.xzt[$scope.section].answer;
                }else if ($scope.chapter==2){
                    $scope.context = $sce.trustAsHtml($scope.bct[$scope.section].context);
                    $scope.answer = $scope.bct[$scope.section].answer;
                }
            } else if ($scope.chapter==3){
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
    }

    if ($scope.section) {
        $scope.getALL();
    }


}