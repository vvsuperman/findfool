'use strict';
/**
 * Created by liuzheng on 6/19/14.
 */
var OJApp = angular.module('OJApp', [
    'ngRoute',
    'evgenyneu.markdown-preview'
]);
OJApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'main.html',
                controller: 'Indexx'
            }).
            when('/user', {
                templateUrl: 'page.html',
                controller: 'TestShow'
            }).
            when('/test', {
                templateUrl: 'page.html',
                controller: 'TestPage'
            }).
            when('/upgrade', {
                templateUrl: 'page.html',
                controller: 'Upgrade'
            }).
            when('/bank', {
                templateUrl: 'page.html',
                controller: 'TestBank'
            }).
            when('/invite', {
                templateUrl: 'page.html',
                controller: 'invite'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);
OJApp.factory('Data', function () {
    return {
        token: "", uid: "", name: "", email: "", tid: ""
    }
});