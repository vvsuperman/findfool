/**
 * Created by liuzheng on 2014/7/22.
 */
function MChoice($scope) {
    $scope.url = '#/MChoice';
    $scope.template = 'MChoice.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    $scope.context = "HTML 4.01 与 HTML 5的属性之间有差异的，下面不是HTML5中新增加的属性是（）。";
    $scope.answer = [
        {text: "AAAA", isright: false, score: 4},
        {text: "BBB", isright: false, score: 4},
        {text: "CCC", isright: true, score: 4}
    ];


    $scope.chose = function (an) {
        console.log($scope.answer[an]);
    }
}