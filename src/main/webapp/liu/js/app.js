'use strict';
/**
 * Created by liuzheng on 6/19/14.
 */
var OJApp = angular.module('OJApp', [
    'ngRoute',
    'ngSanitize',
    'evgenyneu.markdown-preview',
	'webStorageModule'
]);
//OJApp.run(['$rootScope', function ($rootScope) {
//    $rootScope.content = '';
//}]);
OJApp.directive('cleditor', function () {
    return {
        require: '?ngModel',
        link   : function (scope, elm, attr, ngModel) {
            if (!ngModel) return;
            ngModel.$render = function () {
                elm.val(ngModel.$viewValue).blur();
            };
            elm.cleditor().change(function(){
                var value = elm.val();
                if (!scope.$$phase) {
                    scope.$apply(function () {
                        ngModel.$setViewValue(value);
                    });
                }
            });
        }
    }
});
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
            when('/rock&roll/:rrid', {
                templateUrl: 'page.html',
                controller: 'RockRoll'
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
            when('/mybank', {
                templateUrl: 'page.html',
                controller: 'MyTestBank'
            }).
            when('/profile', {
                templateUrl: 'page.html',
                controller: 'pInfo'
            }).
            when('/editor', {
                templateUrl: 'page.html',
                controller: 'aceEditor'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);
OJApp.factory('Data', function (webStorage) {
	function _data(){
			this._token="";
			this.token=function(){
				if(this._token==""||this._token==null){
					return  webStorage.get("token");
				}else{
					return this._token;
				}
			};
			this.setToken =function(to){
					webStorage.remove('token');

					webStorage.add('token',to);
					this._token = to;

			};
			
			this._uid="";
			this.uid=function(){
				if(this._uid==""||this._uid==null){
					return  webStorage.get("uid");
				}else{
					return this._uid;
				}
			};
			this.setUid =function(to){

					webStorage.remove('uid');

					webStorage.add('uid',to);
					this._uid = to;
			};
			
			this._name="";
			this.name=function(){
				if(this._name==""||this._name==null){
					return  webStorage.get("name");
				}else{
					return this._name;
				}
			};
			this.setName =function(to){

					webStorage.remove('name');

					webStorage.add('name',to);
					this._name=to;
			};
			
			this._email="";
			this.email=function(){
				if(this._email==""||this._email==null){
					return  webStorage.get("email");
				}else{
					return this._email;
				}
			};
			this.setEmail =function(to){

					webStorage.remove('email');

					webStorage.add('email',to);
					this._email=to;

			};
			
			this._tid="";
			this.tid=function(){
				if(this._tid==""||this._tid==null){
					return  webStorage.get("tid");
				}else{
					return this._tid;
				}
			};
			this.setTid =function(to){

					webStorage.remove('tid');

					webStorage.add('tid',to);
					this._tid = to;

			};
			
			this._tname="";
			this.tname=function(){
				if(this._tname==""||this._tname==null){
					return  webStorage.get("tname");
				}else{
					return this._tname;
				}
			};
			this.setTname =function(to){

					webStorage.remove('tname');

					webStorage.add('tname',to);
					this._tname = to;
			};

			this._company="";
			this.company=function(){
				if(this._company==""||this._company==null){
					return  webStorage.get("company");
				}else{
					return this._company;
				}
			};
			this.setCompany =function(to){

					webStorage.remove('company');

					webStorage.add('company',to);
					this._company = to;
			};
			this._tel="";
			this.tel=function(){
				if(this._tel==""||this._tel==null){
					return  webStorage.get("tel");
				}else{
					return this._tel;
				}
			};
			this.setTel =function(to){

					webStorage.remove('tel');

					webStorage.add('tel',to);
					this._tel = to;
			}

		}
    return new _data();
});