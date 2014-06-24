'use strict';
/**
 * Created by liuzheng on 6/19/14.
 */
var OJApp = angular.module('phonecatApp', [
    'ngRoute',
    'evgenyneu.markdown-preview',
    'phonecatFilters',
    'phonecatServices'
]);
phonecatApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/phones', {
                templateUrl: 'partials/phone-list.html',
                controller: 'PhoneListCtrl'
            }).
            when('/phones/:phoneId', {
                templateUrl: 'partials/phone-detail.html',
                controller: 'PhoneDetailCtrl'
            }).
            otherwise({
                redirectTo: '/phones'
            });
    }]);