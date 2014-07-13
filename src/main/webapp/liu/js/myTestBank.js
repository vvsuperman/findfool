/**
 * Created by liuzheng on 2014/7/11.
 */
function MyTestBank($scope, $http, Data) {
    $scope.url = '#/mybank';
    $scope.template = 'mytestBank.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = 'leftBar1.html';
    $scope.active = 1;
    $scope.show = 1;
    $scope.page = 1;
    $scope.keyword = "";
    $scope.tag = "";
    $scope.Qtype = [
        { name: '选择题', data: '1'},
        { name: '编程题', data: '2'},
        { name: '问答题', data: '3'}
    ];
    $scope.newQuestion = {};
    $scope.newQuestion.answer = [
        {text: "", isright: 0, score: 0}
    ];
//    $scope.template = $scope.Qtype[0];
    $scope.GoPage = function (target) {
        $scope.show = 1;
        $scope.keyword = "";
        $scope.tag = "";
        $scope.newQuestion = {};
        $scope.newQuestion.answer = [
            {text: "", isright: 0, score: 0}
        ];
        $scope.active = target.getAttribute('data');
        $scope.question = $scope.qs[$scope.active - 1];
        $scope.searchmy();
//        $scope.template = $scope.templates[$scope.active - 1];
    };
    $scope.AddPage = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.show = 0;
    };
    $scope.addQuestion = function () {
        $scope.newQuestion.type = $scope.active;
        $scope.newQuestion.name = "null";
//        var tag = $scope.tag;
//        console.log(tag);
        $scope.newQuestion.tag = document.getElementById("tag").value.split(",");
//        $scope.newQuestion.tag = tag.split(",");
//        console.log($scope.newQuestion.tag)
        $http({
            url: "/question/add",
            method: 'POST',
            headers: {
                "Authorization": Data.token
            },
            data: {"user": {"uid": Data.uid}, "question": $scope.newQuestion}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {

            } else {

            }
        }).error(function (data) {

        });
    };
    $scope.searchmy = function () {
//        console.log({"user": {"uid": Data.uid}, "type": $scope.active, "page": $scope.page, "pageNum": 10, "keyword": $scope.keyword});
//        try{$scope.keyword = document.getElementById("keyword").value}catch(err){};
        $http({
            url: "/search/my",
            method: 'POST',
            headers: {
                "Authorization": Data.token
            },
            data: {"user": {"uid": Data.uid}, "type": $scope.active, "page": $scope.page, "pageNum": 10, "keyword": $scope.keyword}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
//仅需要对message中的数据做处理
                $scope.curPage = $scope.message.curPage;
                $scope.pageNum = $scope.message.pageNum;
                $scope.totalPage = $scope.message.totalPage;
                $scope.qs = $scope.message.questions;

            } else {

            }
        }).error(function (data) {
            $scope.qs = [
                {qid: 1, name: '第一个测试', type: 0, tag: ["测试", "选择题"], context: "选择下面正确的一项", answer: [
                    {text: "1+1=2", isright: 1, score: 4},
                    {text: "1+1=3", isright: 0, score: 0},
                    {text: "1+1=1", isright: 0, score: 0}
                ]},
                {qid: 2, name: 'hdh2', type: 0, tag: ["测试", "选择题"], context: "选择下面正确的一项", answer: [
                    {text: "1+1=2", isright: 1, score: 4},
                    {text: "1+1=3", isright: 0, score: 0},
                    {text: "1+1=1", isright: 0, score: 0}
                ]},
                {qid: 3, name: 'hdh3', type: 0, tag: ["测试", "选择题"], context: "选择下面正确的一项", answer: [
                    {text: "1+1=2", isright: 1, score: 4},
                    {text: "1+1=3", isright: 0, score: 0},
                    {text: "1+1=1", isright: 0, score: 0}
                ]},
                {qid: 4, name: 'hdh4', type: 0, tag: ["测试", "选择题"], context: "选择下面正确的一项", answer: [
                    {text: "1+1=2", isright: 1, score: 4},
                    {text: "1+1=3", isright: 0, score: 0},
                    {text: "1+1=1", isright: 0, score: 0}
                ]}
            ];
        });
        $scope.keyword =""
    };
    $scope.searchmy();

    $scope.addOne = function () {
        $scope.newQuestion.answer.push({text: "", isright: 0, score: 0});
    };

    $scope.removeOne = function (v) {
//        var tmp = $scope.xlsusers;
//        console.log(v);
//        var i = $scope.newQuestion.answer.indexOf(v);
//        console.log(i);
        if (v > 0) {
            $scope.newQuestion.answer.splice(v, 1);
        }
//        $scope.xlsusers = tmp;
//        Data.xlsusers=$scope.xlsusers;
    };
}