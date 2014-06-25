/**
 * Created by liuzheng on 6/7/14.
 */
angular.module('OJApp', [
    'ngRoute',
    'evgenyneu.markdown-preview'
]);

function Indexx($scope,$http) {
//    $scope.customers = [
//        {name: "1公司", img: "./static/benefit-1.png", note: "待定"},
//        {name: "2公司", img: "./static/benefit-2.png", note: "待定"},
//        {name: "3公司", img: "./static/benefit-3.png", note: "待定"}
//    ];
    $http({
     url:'./data/indexGET.json',
     method:'GET'
     }).success(function(data){
        $scope.customers = data.customers;
     });
}
function TestShow($scope, $http) {
    $scope.local = false;
    $scope.template = 'testshow.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    $scope.tests = [
        {id: '1', name: 'hh1', detail: 'i dont know'},
        {id: '2', name: 'hh2', detail: 'i dont know'},
        {id: '3', name: 'hh3', detail: 'i dont know'},
        {id: '4', name: 'hh4', detail: 'i dont know'}
    ];

    $scope.navTest = function () {
        $scope.template = 'testshow.html';
        $scope.ContentUs = 'contentUs.html';
        $scope.leftBar = '';
    };
    $scope.navTestBank = function () {
        $scope.template = 'testBank.html';
        /*need update*/
        $scope.ContentUs = 'contentUs.html';
        $scope.leftBar = 'leftBar1.html';
    };
    $scope.navmyTestBank = function () {
        $scope.template = 'mytestBank.html';
        /*need update*/
        $scope.ContentUs = 'contentUs.html';
        $scope.leftBar = 'leftBar1.html';
    };
    $scope.navUpgrade = function () {
        $scope.template = 'upgrade.html';
        $scope.ContentUs = 'contentUs.html';
        $scope.leftBar = '';
    };

    $scope.createNewTest = function () {
        $scope.template = 'addtest.html';
        $scope.ContentUs = 'contentUs.html';
        $scope.leftBar = '';
    };
    $scope.CommonSetting = function () {
        $scope.template = 'commonsetting.html';
        $scope.ContentUs = 'contentUs.html';
//        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active - 1].id;

        $scope.leftBar = 'leftBar.html';
        $scope.name = $scope.tests[$scope.active - 1].name;
    };

    $scope.testPage = function (target) {
        console.log('testPage');
        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active - 1].id;
        $scope.template = 'testlist.html';
        $scope.leftBar = 'leftBar.html';
        $scope.name = $scope.tests[$scope.active - 1].name;
//        TestManage($scope, $scope.tests[$scope.active - 1].id);
        $http({
            url: './data/TestManage.json',
            method: 'GET',
            data: {'testID': $scope.tests[$scope.active - 1].id}
        }).success(function (data) {
            $scope.questions = data;
//            console.log("testManage");
//            console.log(data);
        })
        /*.error(function (response, status) {
         //            console.log('dddd');
         //            console.log(status)
         })*/
    };
    $scope.Invite = function (target) {
        console.log('Invite');
        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active - 1].id;
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active - 1].name;
        $scope.template = 'invite.html';
        $scope.leftBar = 'leftBar.html';
    };
    $scope.MultInvite = function (target) {
        console.log('MultInvite');
        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active - 1].id;
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active - 1].name;
        $scope.template = 'MutleInvite.html';
        $scope.leftBar = 'leftBar.html';
    };
    $scope.Report = function (target) {
        console.log('Report');
        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active - 1].id;
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active - 1].name;
        $scope.leftBar = 'leftBar.html';
        $scope.template = 'ReportPage';
    };
    $scope.addKUtest = function (target) {
//        添加库试题
        console.log('addKUtest 添加库试题');
        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active - 1].id;
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active - 1].name;
        $scope.leftBar = 'leftBar.html';
        $scope.template = 'addQuestion.html';
    };
    $scope.questionss = [
        [
            {id: '1', name: 'xzt1'},
            {id: '2', name: 'xzt2'},
            {id: '3', name: 'xzt3'},
            {id: '4', name: 'xzt4'}
        ],
        [
            {id: '1', name: 'bct1'},
            {id: '2', name: 'bct2'},
            {id: '3', name: 'bct3'},
            {id: '4', name: 'bct4'}
        ],
        [
            {id: '1', name: 'wdt1'},
            {id: '2', name: 'wdt2'},
            {id: '3', name: 'wdt3'},
            {id: '4', name: 'wdt4'}
        ]
    ];
    $scope.active = 1;
    $scope.show = 1;
    $scope.Qtype = [
        { name: '选择题', data: '1'},
        { name: '编程题', data: '2'},
        { name: '问答题', data: '3'}
    ];
    $scope.question = $scope.questionss[0];

}

/*function TestManage($scope, id) {
 $scope.n = id;
 console.log(id);
 $scope.questions = [
 {id: '1', name: 'hdh1', type: 'xzt', score: 4, detail: 'i dont know'},
 {id: '2', name: 'hdh2', type: 'xzt', score: 4, detail: 'i dont know'},
 {id: '3', name: 'hdh3', type: 'xzt', score: 4, detail: 'i dont know'},
 {id: '4', name: 'hdh4', type: 'xzt', score: 4, detail: 'i dont know'}
 ];

 }*/

function TestBank($scope) {
    $scope.questions = [
        {id: '1', name: 'hdh1', type: 'xzt', score: 4, detail: 'i dont know'},
        {id: '2', name: 'hdh2', type: 'xzt', score: 4, detail: 'i dont know'},
        {id: '3', name: 'hdh3', type: 'xzt', score: 4, detail: 'i dont know'},
        {id: '4', name: 'hdh4', type: 'xzt', score: 4, detail: 'i dont know'}
    ];

}

function TestBank($scope) {
    $scope.active = 1;
    $scope.template = $scope.Qtype[0];
    $scope.GoPage = function (target) {
        $scope.show = 1;
        $scope.active = target.getAttribute('data');
        $scope.question = $scope.questionss[target.getAttribute('data') - 1];
    };
}
function MyTestBank($scope) {
    $scope.active = 1;

    $scope.template = $scope.Qtype[0];
    $scope.GoPage = function (target) {
        $scope.show = 1;
        $scope.active = target.getAttribute('data');
        $scope.question = $scope.questionss[target.getAttribute('data') - 1];
//        $scope.template = $scope.templates[$scope.active - 1];
    };
    $scope.AddPage = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.show = 0;
    };
}
function addQuestion($scope) {
    $scope.Qactive = 1;
    $scope.Tactive = 1;
    $scope.Qtype = [
        { name: '选择题', data: '1'},
        { name: '编程题', data: '2'},
        { name: '问答题', data: '3'}
    ];
    $scope.TYPE = [
        {name: '网站题库', data: '1'},
        {name: '自定义试题', data: '2'}
    ];
    $scope.goT = function (target) {
        $scope.Tactive = target.getAttribute('data');
    }
    $scope.goQ = function (target) {
        $scope.Qactive = target.getAttribute('data');
    }
}


function editor($scope, $http, $sce, $timeout) {
    $scope.language = [
        {name: 'C', demo: './static/c.c', CodeType: 'c_cpp', lan: 0},
        {name: 'C++', demo: './static/cpp.cpp', CodeType: 'c_cpp', lan: 1},
        {name: 'Java', demo: './static/java.java', CodeType: 'java', lan: 3},
        {name: 'python', demo: './static/python.py', CodeType: 'python', lan: 6},
        {name: 'C#', demo: './static/csharp.cs', CodeType: 'csharp', lan: 9}
    ];
    $scope.selected = $scope.language[0];

    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/twilight");

    // add command to lazy-load keybinding_menu extension
    editor.commands.addCommand({
        name: "showKeyboardShortcuts",
        bindKey: {win: "Ctrl-Alt-h", mac: "Command-Alt-h"},
        exec: function (editor) {
            ace.config.loadModule("ace/ext/keybinding_menu", function (module) {
                module.init(editor);
            })
        }
    });
    editor.execCommand("showKeyboardShortcuts");

    if (typeof ace == "undefined" && typeof require == "undefined") {
        document.body.innerHTML = "<p style='padding: 20px 50px;'>couldn't find ace.js file, <br>"
            + "to build it run <code>node Makefile.dryice.js full<code>"
    } else if (typeof ace == "undefined" && typeof require != "undefined") {
        require(["ace/ace"], setValue)
    } else {
        require = ace.require;
    }
    require("ace/lib/net").get($scope.selected.demo, function (t) {
        var el = document.getElementById("editor");
        el.env.editor.setValue(t, 1);
        el.env.editor.getSession().setMode("ace/mode/" + $scope.selected.CodeType);

        $scope.eCode = el.env.editor.getValue();
    });
//    $scope.CodeURL = './static/java.java';
//    $scope.CodeType = 'java';
    $scope.refresh = function () {
        require("ace/lib/net").get($scope.selected.demo, function (t) {
            var el = document.getElementById("editor");
            el.env.editor.setValue(t, 1);
            el.env.editor.getSession().setMode("ace/mode/" + $scope.selected.CodeType);

            $scope.eCode = el.env.editor.getValue();
        });
    };
    $scope.querry = function () {
        var da = new Object();
        da.solution_id = $scope.solution_id;
        var url;
        if ($scope.local) {
            url = "./data/test.json";
        } else {
            url = "/solution/query";
        }

        $http({
            url: url,
            method: "POST",
            data: da
        }).success(function (data) {
//            var flag = 0;
            for (var i = 0; i < data.length; i++) {
//                flag = 1;
                $scope.result += '<br>';
                var res = data[i];
                $scope.result += "time:" + res.cost_time;
                $scope.result += " mem:" + res.cost_mem;
                if (res.test_case == null) {
//                    there has a bug need fix
                    $scope.result += " error:" + res.test_case_result;
                    //$scope.result += " error:"+res.test_case_result.strlen()>200?res.test_case_result.substr(0,200)+"[...]":res.test_case_result;
                } else {
                    $scope.result += " testCase:" + res.test_case;
                    $scope.result += " result:" + res.test_case_result;
                }
            }
//            if (data.length==0){
//                $scope.result = '';
//
//            }
            if ($scope.flag > 0) {
//                $scope.result = '<br>Try again';
                $scope.flag -= 1;
                $timeout($scope.querry, 1000);
//                setTimeout($scope.querry(), 1000);
            }
            $scope.RESULT = $sce.trustAsHtml($scope.result)
        }).error(function () {
            $scope.result += '<br>Try again'
        })
    };

//    测试用例函数
    $scope.cases = [
        {'value': ''}
    ];
    $scope.addTestCase = function () {
        $scope.cases.push({'value': ''});
    };
    $scope.showTestCase = function () {
        $scope.all = '';
        for (var ca = 0; ca < $scope.cases.length; ca++) {
            $scope.all += $scope.cases[ca].value;
        }
    };
    $scope.removeTestCase = function (v) {
        var tmp = $scope.cases;
        var i = tmp.indexOf(v);
        if (i > -1) {
            tmp.splice(i, 1);
        }
        $scope.cases = tmp;

    };
//    测试用例函数end

    $scope.Run = function () {

        $scope.flag = 5;
        var el = document.getElementById("editor");
        $scope.code = el.env.editor.getValue();
        $scope.pid = 0;
        if ($scope.local) {
            $scope.result = 'local!!! Judging...';
        } else {
            $scope.result = 'Judging...';
        }
        var da = new Object();
        da.problem_id = $scope.pid;
        da.language = $scope.selected.lan;
//        $scope.hehe = ["1,2", "3,4"];
//        da.user_test_cases = $scope.hehe;
//        console.log(da.user_test_cases);
        da.user_test_cases = $scope.cases;
        console.log(da.user_test_cases);
        da.solution = $scope.code;
        da.user_id = 110;
        var url;
        if ($scope.local) {
            url = ''
        } else {
            url = '/solution/run';
        }
        $http({
            url: url,
            method: "POST",
            data: da
        }).success(function (data) {
            $scope.zhuangtai = 'success';
            $scope.solution_id = data.solution_id;
//            setTimeout($scope.querry(), 2000);
            $timeout($scope.querry, 2000);

            $scope.RESULT = $sce.trustAsHtml($scope.result);
        }).error(function () {
            $scope.zhuangtai = 'Try again1';
            $scope.result = 'fail'
        });

    };


}

function sign($scope) {
    $scope.active = 0;
    $scope.GoPage = function () {
        $scope.active = 1 - $scope.active;
    }
}