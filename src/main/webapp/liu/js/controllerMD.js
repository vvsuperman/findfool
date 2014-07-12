'use strict';

/* Controllers */

var OJAppControllers = angular.module('OJAppControllers', ['ngRoute'] );

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

