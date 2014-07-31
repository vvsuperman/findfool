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
/*
 OJApp.directive('cleditor', function () {
 return {
 require: '?ngModel',
 link: function (scope, elm, attr, ngModel) {
 if (!ngModel) return;
 ngModel.$render = function () {
 elm.val(ngModel.$viewValue).blur();
 };
 elm.cleditor().change(function () {
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
 */
OJApp.directive('ckEditor', [function () {
    return {
        require: '?ngModel',
        restrict: 'C',
        link: function (scope, elm, attr, model) {
            var isReady = false;
            var data = [];
            var ck = CKEDITOR.replace(elm[0]);
            function setData() {
                if (!data.length) {
                    return;
                }
                var d = data.splice(0, 1);
                ck.setData(d[0] || '<span></span>', function () {
                    setData();
                    isReady = true;
                });
            }
            ck.on('instanceReady', function (e) {
                if (model) {
                    setData();
                }
            });
            elm.bind('$destroy', function () {
                ck.destroy(false);
            });
            if (model) {
                ck.on('change', function () {
                    scope.$apply(function () {
                        var data = ck.getData();
                        if (data == '<span></span>') {
                            data = null;
                        }
                        model.$setViewValue(data);
                    });
                });
                model.$render = function (value) {
                    if (model.$viewValue === undefined) {
                        model.$setViewValue(null);
                        model.$viewValue = null;
                    }
                    data.push(model.$viewValue);
                    if (isReady) {
                        isReady = false;
                        setData();
                    }
                };
            }
        }
    };
}]);
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
            when('/loginok', {
                templateUrl: 'page.html',
                controller: 'LoginOk'
            }).
            when('/test', {
                templateUrl: 'page.html',
                controller: 'TestPage'
            }).
            when('/test/:tid', {
                templateUrl: 'page.html',
                controller: 'TestPageTid'
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
            when('/MChoice', {
                templateUrl: 'page.html',
                controller: 'MChoice'
            }).
            when('/MChoice/:r', {
                templateUrl: 'page.html',
                controller: 'MChoice'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);
OJApp.factory('Data', function (webStorage) {
    function _data() {
        this._flag = 0;
        this.flag = function () {
            if (this._flag == "" || this._flag == null) {
                this._flag = webStorage.get("flag");
                if (this._flag == "" || this._flag == null) {
                    this._flag = 0;
                    webStorage.add('flag', 0);
                }
            }
            return this._flag;
        };
        this.setFlag = function (to) {
            webStorage.remove('flag');
            webStorage.add('flag', to);
            this._flag = to;
        };
        this._key1 = "";
        this.key1 = function () {
            if (this._key1 == "" || this._key1 == null) {
                this._key1 = webStorage.get("key1");
            }
            return this._key1;
        };
        this.setKey1 = function (to) {
            webStorage.remove('key1');
            webStorage.add('key1', to);
            this._key1 = to;
        };
        this._key2 = "";
        this.key2 = function () {
            if (this._key2 == "" || this._key2 == null) {
                this._key1 = webStorage.get("key2");
            }
            return this._key2;
        };
        this.setKey2 = function (to) {
            webStorage.remove('key2');
            webStorage.add('key2', to);
            this._key2 = to;
        };
        this._key3 = "";
        this.key3 = function () {
            if (this._key3 == "" || this._key3 == null) {
                this._key3 = webStorage.get("key3");
            }
            return this._key3;
        };
        this.setKey3 = function (to) {
            webStorage.remove('key3');
            webStorage.add('key3', to);
            this._key3 = to;
        };
        this._token = "";
        this.token = function () {
            if (this._token == "" || this._token == null) {
                this._token = webStorage.get("token");
            }
            return this._token;
        };
        this.setToken = function (to) {
            webStorage.remove('token');
            webStorage.add('token', to);
            this._token = to;
        };
        this._invitedleft = "";
        this.invitedleft = function () {
            if (this._invitedleft == "" || this._invitedleft == null) {
                this._invitedleft = webStorage.get("invitedleft");
            }
            return this._invitedleft;
        };
        this.setInvitedleft = function (to) {
            webStorage.remove('invitedleft');
            webStorage.add('invitedleft', to);
            this._invitedleft = to;
        };
        this._uid = "";
        this.uid = function () {
            if (this._uid == "" || this._uid == null) {
                this._uid = webStorage.get("uid");
            }
            return this._uid;
        };
        this.setUid = function (to) {
            webStorage.remove('uid');
            webStorage.add('uid', to);
            this._uid = to;
        };
        this._ans = "";
        this.ans = function () {
            if (this._ans == "" || this._ans == null) {
                this._ans = webStorage.get("ans");
            }
            return this._ans;
        };
        this.setAns = function (to) {
            webStorage.remove('ans');
            webStorage.add('ans', to);
            this._ans = to;
        };
        this._name = "";
        this.name = function () {
            if (this._name == "" || this._name == null) {
                this._name = webStorage.get("name");
            }
            return this._name;
        };
        this.setName = function (to) {
            webStorage.remove('name');
            webStorage.add('name', to);
            this._name = to;
        };
        this._email = "";
        this.email = function () {
            if (this._email == "" || this._email == null) {
                this._email = webStorage.get("email");
            }
            return this._email;
        };
        this.setEmail = function (to) {
            webStorage.remove('email');
            webStorage.add('email', to);
            this._email = to;
        };
        this._tid = "";
        this.tid = function () {
            if (this._tid == "" || this._tid == null) {
                this._tid = webStorage.get("tid");
            }
            return this._tid;
        };
        this.setTid = function (to) {
            webStorage.remove('tid');
            webStorage.add('tid', to);
            this._tid = to;
        };
        this._tname = "";
        this.tname = function () {
            if (this._tname == "" || this._tname == null) {
                this._tname = webStorage.get("tname");
            }
            return this._tname;
        };
        this.setTname = function (to) {
            webStorage.remove('tname');
            webStorage.add('tname', to);
            this._tname = to;
        };
        this._context = "";
        this.context = function () {
            if (this._context == "" || this._context == null) {
                this._context = webStorage.get("context");
            }
            return this._context;
        };
        this.setContext = function (to) {
            webStorage.remove('context');
            webStorage.add('context', to);
            this._context = to;
        };
        this._answer = "";
        this.answer = function () {
            if (this._answer == "" || this._answer == null) {
                this._answer = webStorage.get("answer");
            }
            return this._answer;
        };
        this.setAnswer = function (to) {
            webStorage.remove('answer');
            webStorage.add('answer', to);
            this._answer = to;
        };
        this._qs = "";
        this.qs = function () {
            if (this._qs == "" || this._qs == null) {
                this._qs = webStorage.get("qs");
            }
            return this._qs;
        };
        this.setQs = function (to) {
            webStorage.remove('qs');
            webStorage.add('qs', to);
            this._qs = to;
        };
        this._company = "";
        this.company = function () {
            if (this._company == "" || this._company == null) {
                this._company = webStorage.get("company");
            }
            return this._company;
        };
        this.setCompany = function (to) {
            webStorage.remove('company');
            webStorage.add('company', to);
            this._company = to;
        };
        this._tel = "";
        this.tel = function () {
            if (this._tel == "" || this._tel == null) {
                this._tel = webStorage.get("tel");
            }
            return this._tel;
        };
        this.setTel = function (to) {
            webStorage.remove('tel');
            webStorage.add('tel', to);
            this._tel = to;
        };
        this._privi = "";
        this.privi = function () {
            if (this._privi == "" || this._privi == null) {
                this._privi = webStorage.get("privi");
            }
            return this._privi;
        };
        this.setPrivi = function (to) {
            webStorage.remove('privi');
            webStorage.add('privi', to);
            this._privi = to;
        };
    }
    return new _data();
});