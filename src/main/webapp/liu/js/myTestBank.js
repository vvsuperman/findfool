/**
 * Created by liuzheng on 2014/7/11.
 */
function MyTestBank($scope) {
    $scope.url = '#/mybank';
    $scope.template = 'mytestBank.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = 'leftBar1.html';
    $scope.active = 1;
    $scope.show = 1;
    $scope.page = 1;
    $scope.keyword="";
    $scope.Qtype = [
        { name: '选择题', data: '1'},
        { name: '编程题', data: '2'},
        { name: '问答题', data: '3'}
    ];
    $scope.qs = [
        {qid: 1, name: 'hdh1', type: 0, tag:["测试","选择题"],context: "选择下面正确的一项",answer:[{text:"1+1=2",isright:1,score:4}]},
        {qid: 2, name: 'hdh2', type: 'xzt', score: 4, detail: 'i dont know'},
        {qid: 3, name: 'hdh3', type: 'xzt', score: 4, detail: 'i dont know'},
        {qid: 4, name: 'hdh4', type: 'xzt', score: 4, detail: 'i dont know'}
    ];
//    $scope.template = $scope.Qtype[0];
    $scope.GoPage = function (target) {
        $scope.show = 1;
        $scope.active = target.getAttribute('data');
        $scope.question = $scope.qs[$scope.active - 1];
//        $scope.template = $scope.templates[$scope.active - 1];
    };
    $scope.AddPage = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.show = 0;
    };
    $scope.addQuestion = function () {
        $http({
            url: "/question/add",
            method: 'POST',
            headers: {
                "Authorization": Data.token
            },
            data: {"user": {"uid": Data.uid}, "question":$scope.newQuestion}
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
  $scope.searchmy= function () {
      $http({
          url: "/search/my",
          method: 'POST',
          headers: {
              "Authorization": Data.token
          },
          data: {"user":{"uid": Data.uid},"type":$scope.active,"page":$scope.page,"pageNum":10,"keyword":$scope.keyword}
      }).success(function (data) {
          $scope.state = data["state"];//1 true or 0 false
          Data.token = data["token"];
          $scope.message = data["message"];
          if ($scope.state) {
//仅需要对message中的数据做处理
              $scope.curPage=$scope.message.curPage;
              $scope.pageNum=$scope.message.pageNum;
              $scope.totalPage=$scope.message.totalPage;
              $scope.qs=$scope.message.questions;

          } else {

          }
      }).error(function (data) {

      });
  }
}