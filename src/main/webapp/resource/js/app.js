'use strict';
/**
 * Created by liuzheng on 6/19/14.
 */
var OJApp = angular.module('OJApp', [
    'ui.bootstrap',
    'chart.js',
    'ngRoute',
    'ngSanitize',
//    'evgenyneu.markdown-preview',
    'webStorageModule',
//    'ngCkeditor',
    'lr.upload',
    'angucomplete-alt'
]);

OJApp.config(['$routeProvider' , '$locationProvider',
    function ($routeProvider,$locationProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'page/main.html',
                controller: 'mainController'
            }).
            when('/challenge/:signedid', {  //客户端主页
                templateUrl: 'challenge/page/preparechallenge.html',
                controller: 'ctestingController'
            }).
            when('/cad/comlist', {  //客户端主页
                templateUrl: 'cad/page/pagenav.html',
                controller: 'comListController'
            }).
            when('/cad/challengelist', {  //客户端主页
                templateUrl: 'cad/page/pagenav.html',
                controller: 'challengeListController'
            }).
            when('/cad/eachcom/:comid', {  //客户公司详情
                templateUrl: 'cad/page/pagenav.html',
                controller: 'eachComController'
            }).
            when('/cad/frcom', {  //客户端主页
                templateUrl: 'cad/page/pagenav.html',
                controller: 'frComController'
            }). 
            when('/cad/rank', {  //客户端主页
                templateUrl: 'cad/page/pagenav.html',
                controller: 'cadRankController'
            }).   
            when('/cad/personal', {  //客户端主页
                templateUrl: 'cad/page/pagenav.html',
                controller: 'cdPersonalController'
            }).
          
         
            when('/dp/register', {  //点评主页
                templateUrl: 'dp/page/dpregister.html',
                controller: 'cadLoginController'
            }).
            when('/dp/register2', {  //点评主页
                templateUrl: 'dp/page/dpregister2.html',
                controller: 'cadLoginController'
            }).
            when('/dp/', {  //点评主页
                templateUrl: 'dp/page/dplogin.html',
                controller: 'cadLoginController'
            }).
            when('/dp/login', {  //点评主页
                templateUrl: 'dp/page/dplogin.html',
                controller: 'cadLoginController'
            }).
            when('/dp/testmain', {  //点评主页
                templateUrl: 'dp/page/testmain.html',
                controller: 'testMainController'
            }).
            when('/dp/testdetail', {  //点评主页
                templateUrl: 'dp/page/testdetail.html',
                controller: 'testDetailController'
            }).
            when('/dp/cadtesting/', {  //点评主页
                templateUrl: 'dp/page/cadtesting.html',
                controller: 'cadtestingController'
            }).
            when('/dp/profile/', {  //个人信息
                templateUrl: 'dp/page/profile.html',
                controller: 'profileController'
            }).
            when('/dp/findpwd',{
            	templateUrl: 'dp/page/findPwd.html',
            	controller: 'findCadPwdCtrl'
            }).
            
            when('/user', {
                templateUrl: 'page/page.html',
                controller: 'testshow'
            }).
            when('/rock&roll/:rrid', {
                templateUrl: 'page/page.html',
                controller: 'RockRoll'
            }). 	
            when('/loginok', {
                templateUrl: 'page/page.html',
                controller: 'LoginOk'
            }).
            when('/test', {
                templateUrl: 'page/page.html',
                controller: 'TestPage'
            }).
//            内置测试
            when('/innertest', {
            	templateUrl: 'page/page.html',
            	controller: 'InnerTest'
            }).
            when('/test/:tid', {
                templateUrl: 'page/page.html',
                controller: 'TestPageTid'
            }).
            when('/testConfig', {
                templateUrl: 'page/page.html',
                controller: 'testConfig'
            }).
            when('/upgrade', {
                templateUrl: 'page/page.html',
                controller: 'Upgrade'
            }).
            when('/bank', {
                templateUrl: 'page/page.html',
                controller: 'TestBank'
            }).
            when('/invite', {
                templateUrl: 'page/page.html',
                controller: 'invite'
            }).
            when('/mybank', {
                templateUrl: 'page/page.html',
                controller: 'mytestbank'
            }).
            when('/profile', {
                templateUrl: 'page/page.html',
                controller: 'pInfo'
            }).
            when('/editor', {
                templateUrl: 'page/page.html',
                controller: 'aceEditor'
            }).
            when('/mchoice', {
                templateUrl: 'page/page.html',
                controller: 'mchoice'
            }).
            when('/mchoice/:r', {
                templateUrl: 'page/page.html',
                controller: 'mchoice'
            }).
            when('/adminhome', {
                templateUrl: 'page/admin/adminhome.html',
                controller: 'adminController'
            }).
            when('/testing/:url', {
                templateUrl: 'page/preparetest.html',
                controller: 'testingController'
            }).
            when('/report/show/:state', {
                templateUrl: 'page/page.html',
                controller: 'reportController'
            }).
            when('/report/list', {
                templateUrl: 'page/page.html',
                controller: 'reportListController'
            }).
            when('/publicReport/list/:url', {
                templateUrl: 'page/testreport.html',
                controller: 'publicReportListController'
            }).
            when('/report/detail', {
                templateUrl: 'page/page.html',
                controller: 'reportDetailController'
            }).
            when('/report/log', {
                templateUrl: 'page/page.html',
                controller: 'reportLogController'
            }).
            when('/oauthor', {
                templateUrl: 'page/oauthor.html',
                controller: 'oauthorController'
            }).
            when('/login',{
            	templateUrl: 'page/login.html',
            	controller: 'loginCtrl'
            }).
            when('/signup',{
            	templateUrl: 'page/signup.html',
            	controller: 'signupCtrl'
            }).
            when('/findPwd',{
            	templateUrl: 'page/findPwd.html',
            	controller: 'findPwdCtrl'
            }).
            when('/publictest/:url',{
            	templateUrl: 'page/page.html',
            	controller: 'publictestCtrl'
            }).
            when('/reset/:auth',{
            	templateUrl: 'page/findPwd.html',
            	controller: 'findPwdCtrl'
            }).
            when('/dp/reset/:auth',{
            	templateUrl: 'dp/page/findPwd.html',
            	controller: 'findCadPwdCtrl'
            }). 
            when('/cad/signup',{
            	templateUrl: 'cad/page/signup.html',
            	controller: 'cadSignupCtrl'
            }). 
            when('/cad/login',{
            	templateUrl: 'cad/page/login.html',
            	controller: 'cadLoginCtrl'
            }). 
            when('/company/companyset',{
            	templateUrl: 'company/page/companyIndex.html',
            	controller: 'companySet'
            }). when('/company/create',{
            	templateUrl: 'company/page/createCompany.html',
            	controller: 'companySet'
            }). 
            when('/company/show/:companyId',{
            	templateUrl: 'company/page/showCompany.html',
            	controller: 'companySet'
            }). 
            when('/company/modify/:companyId',{
            	templateUrl: 'company/page/modifyCompany.html',
            	controller: 'companySet'
            }).    
            when('/company/updateimage',{
            	templateUrl: 'company/page/ updateimage.html',
            	controller: 'companySet'
            }).
            when('/company/addUser/:companyId',{
            	templateUrl: 'company/page/addUser.html',
            	controller: 'companySet'
            }). 
            otherwise({
                redirectTo: '/'
            });
        
//        $locationProvider.html5Mode(true);
    }]);


OJApp.factory('Data', ['webStorage',function (webStorage) {
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
        this.removeToken = function(){
        	webStorage.remove('token');
        	this._token = '';
        };
        //明道的token
        this._mdToken = "";
        this.mdToken = function () {
            if (this._mdToken == "" || this._mdToken == null) {
                this._mdToken = webStorage.get("mdToken");
            }
            return this._mdToken;
        };
        
        this.setMdToken = function (to) {
            webStorage.remove('mdToken');
            webStorage.add('mdToken', to);
            this._mdToken = to;
        };
        this.removeMdToken = function(){
        	webStorage.remove('mdToken');
        	this._mdToken = '';
        };
        
        //明道的uid
        this._mdUid = "";
        this.mdUid = function () {
            if (this._mdUid == "" || this._mdUid == null) {
                this._mdUid = webStorage.get("mdUid");
            }
            return this._mdUid;
        };
        
        this.setMdUid = function (to) {
            webStorage.remove('mdUid');
            webStorage.add('mdUid', to);
            this._mdUid = to;
        };
        this.removeMdUid = function(){
        	webStorage.remove('mdUid');
        	this._mdUid = '';
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
        
        this._lastActive = "";
        this.lastActive = function () {
            if (this._lastActive == "" || this._lastActive == null) {
                this._lastActive = webStorage.get("lastActive");
            }
            return this._lastActive;
        };
        this.setLastActive = function (to) {
            webStorage.remove('lastActive');
            webStorage.add('lastActive', to);
            this._lastActive = to;
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
        
        this._inviteid="";
        this.inviteid = function () {
            if (this._inviteid == "" || this._inviteid == null) {
                this._inviteid = webStorage.get("inviteid");
            }
            return this._inviteid;
        };
        this.setInviteid = function (to) {
            webStorage.remove('inviteid');
            webStorage.add('inviteid', to);
            this._inviteid = to;
        };
        
        
        this._tuid="";
        this.tuid = function () {
            if (this._tuid == "" || this._tuid == null) {
                this._tuid = webStorage.get("tuid");
            }
            return this._tuid;
        };
        this.setTuid = function (to) {
            webStorage.remove('tuid');
            webStorage.add('tuid', to);
            this._tuid = to;
        };
        
        this.clear = function(){
        	webStorage.clear();
        	for (var p in this){
        		if(typeof(this[p])=="function"){
        		}
        		else{
        			this[p]="";
        		}
        	}
        };
    }
    return new _data();
}]);


//用于客户端的服务
OJApp.factory('CadData', ['webStorage',function (webStorage) {
    function _data() {
    	  this._token = "";
          this.getToken = function () {
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
    	
    	this._email = "";
        this.getEmail = function () {
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
        
        
        this._testid = "";
        this.getTestid = function () {
            if (this._testid == "" || this._testid == null) {
                this._testid = webStorage.get("testid");
            }
            return this._testid;
        };
        this.setTestid = function (to) {
            webStorage.remove('testid');
            webStorage.add('testid', to);
            this._testid = to;
        };
        
        
        this._testname = "";
        this.getTestname = function () {
            if (this._testname == "" || this._testname == null) {
                this._testname = webStorage.get("testname");
            }
            return this._testname;
        };
        this.setTestname = function (to) {
            webStorage.remove('testname');
            webStorage.add('testname', to);
            this._testname = to;
        };
        
        
        this._rembme = "";
        this.getRembme = function () {
            if (this._rembme == "" || this._rembme == null) {
                this._rembme = webStorage.get("rembme");
            }
            return this._rembme;
        };
        this.setRembme = function (to) {
            webStorage.remove('rembme');
            webStorage.add('rembme', to);
            this._rembme = to;
        };
        
        
        this.clear = function(){
        	webStorage.clear();
        	for (var p in this){
        		if(typeof(this[p])=="function"){
        		}
        		else{
        			this[p]="";
        		}
        	}
        };
       
    }  
     
    return new _data();
}]);

