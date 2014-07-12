'use strict';

var OJApp = angular.module('OJApp', [
    'ngRoute',
    'OJAppControllers'
]);

OJApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/login', {
                templateUrl: 'login.html',
                controller: 'OJAppLogin'
            }).
            when('/home', {
                templateUrl: 'home.html',
                controller: 'OJAppHome'
            }).
            when('/editor', {
                templateUrl: 'editor1.html',
                controller: 'OJAppEditor'
            }).
//        when('/edit/:title', {
//            controller: EditCtrl,
//            templateUrl: 'partials/create.html'
//        }).
            otherwise({
                redirectTo: '/editor'
            });
    }]);