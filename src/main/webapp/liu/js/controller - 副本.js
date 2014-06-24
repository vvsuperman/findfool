/**
 * Created by liuzheng on 2014/6/2.
 */
/*var OJAppControllers = angular.module('OJAppControllers', ['ngRoute'] );

OJAppControllers.controller('OJAppHome', ['$scope',
    function($scope) {
        $scope.page = 'home';
    }]);

OJAppControllers.controller('OJAppLogin', ['$scope',
    function($scope) {
        $scope.page = 'login';
    }]);


OJAppControllers.controller('OJAppEditor', ['$scope', '$routeParams',
    function($scope, $routeParams) {
        $scope.page = 'editor';
        $scope.phoneId = $routeParams.phoneId;
    }]);

OJAppControllers.controller('PhoneListCtrl', ['$scope',
    function ($scope) {
        $scope.phones = [
            {"name": "Nexus S",
                "snippet": "Fast just got faster with Nexus S."},
            {"name": "Motorola XOOM™ with Wi-Fi",
                "snippet": "The Next, Next Generation tablet."},
            {"name": "MOTOROLA XOOM™",
                "snippet": "The Next, Next Generation tablet."}
        ];
    }]);*/

function PhoneListCtrl($scope) {
    $scope.phones = [
        {"name": "Nexus S",
            "snippet": "Fast just got faster with Nexus S."},
        {"name": "Motorola XOOM™ with Wi-Fi",
            "snippet": "The Next, Next Generation tablet."},
        {"name": "MOTOROLA XOOM™",
            "snippet": "The Next, Next Generation tablet."}
    ];
}
