/**
 * Created by liuzheng on 2014/7/11.
 */
function TestBank($scope, $http) {
    $scope.url = '#/bank';
    $scope.template = 'testBank.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = 'leftBar1.html';
    $scope.active = 1;
    $scope.show = 1;
    $scope.Qtype = [
        { name: '选择题', data: '1'},
        { name: '编程题', data: '2'},
        { name: '问答题', data: '3'}
    ];
    $scope.qs = [
        {id: '1', name: 'hdh1', type: 'xzt', score: 4, detail: 'i dont know'},
        {id: '2', name: 'hdh2', type: 'xzt', score: 4, detail: 'i dont know'},
        {id: '3', name: 'hdh3', type: 'xzt', score: 4, detail: 'i dont know'},
        {id: '4', name: 'hdh4', type: 'xzt', score: 4, detail: 'i dont know'}
    ];
    //$scope.template = $scope.Qtype[0];
    $scope.GoPage = function (target) {
        $scope.show = 1;
        $scope.active = target.getAttribute('data');
//        console.log($scope.active);
        $scope.question = $scope.qs[$scope.active - 1];
//        console.log($scope.question);
    };
//    $scope.navTestBank = function () {
//        $scope.template = 'testBank.html';
//        /*need update*/
//        $scope.ContentUs = 'contentUs.html';
//        $scope.leftBar = 'leftBar1.html';
//    };
//    $scope.navmyTestBank = function () {
//        $scope.template = 'mytestBank.html';
//        /*need update*/
//        $scope.ContentUs = 'contentUs.html';
//        $scope.leftBar = 'leftBar1.html';
//    };
    $scope.AddPage = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.show = 0;
    };
}