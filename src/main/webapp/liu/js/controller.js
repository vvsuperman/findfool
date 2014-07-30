/**
 * Created by liuzheng on 6/7/14.
 */
//angular.module('OJApp', [
//    'ngRoute',
//    'evgenyneu.markdown-preview'
//]);

function Indexx($scope, $http, Data) {
    $scope.url = '#';
//    $scope.customers = [
//        {name: "1公司", img: "./static/benefit-1.png", note: "待定"},
//        {name: "2公司", img: "./static/benefit-2.png", note: "待定"},
//        {name: "3公司", img: "./static/benefit-3.png", note: "待定"}
//    ];
    /*$http({
     url: './data/indexGET.json',
     method: 'GET'
     }).success(function (data) {
     $scope.customers = data.customers;
     });*/
    $scope.confirm = function () {
        if ($scope.Lemail && $scope.Lpwd) {
            $http({
                url: "/user/confirm",
                method: 'POST',
                headers: {
                    "Authorization": Data.token()
                },
                data: {"email": $scope.Lemail, "pwd": md5($scope.Lpwd), "name": $scope.Lname}
            }).success(function (data) {
                $scope.state = data["state"];//1 true or 0 false

                var name = $scope.Lemail;
                $scope.message = data["message"];
                if (data["message"].handler_url != null && data["message"].handler_url !== "") {
                    name = data["message"].handler_url;
                }
                Data.setName(name);
                Data.setEmail($scope.Lemail);

                if ($scope.state) {
                    Data.setToken(data["token"]);
                    console.log(Data.token());
                    console.log(data["token"]);
                    Data.setUid($scope.message.uid);
                    Data.setPrivi($scope.message.privilege);
                    Data.setTel($scope.message.tel);
                    Data.setCompany($scope.message.company);
                    Data.setInvitedleft($scope.message.invited_left);
                    $scope.invitedleft = $scope.message.invited_left;
                    var child = document.getElementsByClassName("modal-backdrop fade in");
                    child[0].parentNode.removeChild(child[0]);
                    window.location.href = '#/loginok';
                    $scope.name = $scope.message.handler_url;
                } else {
                    var child = document.getElementsByClassName("modal-backdrop fade in");
                    child[0].parentNode.removeChild(child[0]);
                    window.location.href = '#/loginok';
                    $scope.name = "测试用户";
                }

            }).error(function () {
                    alert("网络错误");
                    window.location.reload(true);
                }
            )
        }
    };
    $scope.contactus = function () {
//        Data.email=$scope.Cemail;
        if (Data.email()) {
            $http({
                url: '/contactus',
                method: 'POST',
                headers: {
                    "Authorization": Data.token()
                },
                data: {"email": Data.email(), "name": Data.name(), "msg": $scope.msg}
            }).success(function (data) {
                $scope.state = data["state"];//1 true or 0 false
                $scope.message = data["message"];

                if ($scope.state) {
                    $scope.thx = "感谢您的提交"
                } else {

                }
            }).error(function () {
                    alert("网络错误");
//                    window.location.reload(true);
                }
            );
        } else {
            if ($scope.Cemail) {
                $http({
                    url: '/contactus',
                    method: 'POST',
                    headers: {
                        "Authorization": Data.token()
                    },
                    data: {"email": $scope.Cemail, "name": $scope.name, "msg": $scope.msg}
                }).success(function (data) {
                    $scope.state = data["state"];//1 true or 0 false
                    $scope.message = data["message"];

                    if ($scope.state) {
                        $scope.thx = "感谢您的提交"
                    } else {

                    }
                }).error(function () {
                        alert("网络错误");
                        window.location.reload(true);
                    }
                );
            }
        }
    };
    $scope.addhr = function () {
        if ($scope.Remail && $scope.Rpwd && $scope.Rrepwd && $scope.RVerification) {
            if ($scope.Rpwd == $scope.Rrepwd) {
                if ($scope.RVerification = $scope.Verification) {
                    $http({
                        url: "/user/add/hr",
                        method: 'POST',
                        headers: {
                            "Authorization": Data.token()
                        },
                        data: {"email": $scope.Remail, "pwd": md5($scope.Rpwd), "name": $scope.name}
                    }).success(function (data) {
                        $scope.state = data["state"];//1 true or 0 false
                        $scope.message = data["message"];
                        if ($scope.state) {
                            Data.setUid($scope.message.uid);
                            Data.setToken(data["token"]);
                            console.log(Data.token());
                            console.log(data["token"]);
                            Data.setUid($scope.message.uid);
                            Data.setPrivi($scope.message.privilege);
                            Data.setTel($scope.message.tel);
                            Data.setCompany($scope.message.company);
                            //add by zpl
//                            Data.token = $scope.token;//ignore by lz,token 直接都在Data里的，不走$scope
                            Data.setEmail($scope.Remail);
                            //end bu zpl
                            var child = document.getElementsByClassName("modal-backdrop fade in");
                            $scope.name = $scope.name;
                            Data.setName($scope.name);
                            child[0].parentNode.removeChild(child[0]);
                            window.location.href = '#/test';
                        } else {
                            alert($scope.message.msg);
                            window.location.reload(true);
                        }
                    }).error(function () {
                            alert("网络错误");
                            window.location.reload(true);
                        }
                    );
                } else {
                    alert("验证码错误");
                    $scope.createCode();
                }
            } else {
                alert("密码不相同")
            }
        }
    };
    $scope.addadmin = function () {
        $http({
            url: "/user/add/admin",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"email": $scope.email, "pwd": $scope.pwd, "name": $scope.name}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            $scope.message = data["message"];
            if ($scope.state) {

            } else {

            }
        }).error(function () {
                alert("网络错误");
                window.location.reload(true);
            }
        );
    };
    $scope.createCode = function () {
        code = "";
        var codeLength = 4;//验证码的长度
        var checkCode = document.getElementById("code");
        var random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');//随机数
        for (var i = 0; i < codeLength; i++) {//循环操作
            var index = Math.floor(Math.random() * 36);//取得随机数的索引（0~35）
            code += random[index];//根据索引取得随机数加到code上
        }
        $scope.Verification = code;//把code值赋给验证码
    };
    $scope.createCode();
    $scope.show = 1;
    $scope.btn = function () {
        $scope.show = -($scope.show - 1);
    };
    $scope.enter = function ($event) {
        if ($event.keyCode == 13) {
            $scope.confirm()
        }
    };
}
function aceEditor($scope) {
    $scope.url = "#/editor";
    $scope.template = 'editor.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
}
function TestShow($scope, $http, Data) {
//    $scope.local = false;
    $scope.template = 'testshow.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    $scope.name = Data.name();


//    $scope.navTest = function () {
//        $scope.template = 'testshow.html';
//        $scope.ContentUs = 'contentUs.html';
//        $scope.leftBar = '';
//    };
//    $scope.navTestBank = function () {
//        $scope.template = 'testBank.html';
//        /*need update*/
//        $scope.ContentUs = 'contentUs.html';
//        $scope.leftBar = 'leftBar1.html';
//    };
//    $scope.navmyTestBank = function () {
//        $scope.template = 'mytestBank.html';
//        /*need update*/
//        $scope.ContentUs = 'contentUs.html';
//        $scope.leftBar = 'leftBar1.html';
//    };
//    $scope.navUpgrade = function () {
//        $scope.template = 'upgrade.html';
//        $scope.ContentUs = 'contentUs.html';
//        $scope.leftBar = '';
//    };

//    $scope.navPersonal = function () {
//        $scope.template = 'profile.html';
//        $scope.ContentUs = 'contentUs.html';
//        $scope.leftBar = '';
//    };


//        TestManage($scope, $scope.tests[$scope.active - 1].id);
    if ($scope.local == true) {
//            $scope.questions = [{
//                "id": "1",
//                "name": "hdh1",
//                "type": 1,
//                "score": 4,
//                "detail": "i dont know"
//            },
//                {
//                    "id": "2",
//                    "name": "hdh2",
//                    "type": "xzt",
//                    "score": 4,
//                    "detail": "i dont know"
//                },
//                {
//                    "id": "3",
//                    "name": "hdh3",
//                    "type": 1,
//                    "score": 4,
//                    "detail": "i dont know"
//                },
//                {
//                    "id": "4",
//                    "name": "hdh4",
//                    "type": 1,
//                    "score": 4,
//                    "detail": "i dont know"
//                }]
        $http({
            url: './data/TestManage.json',
            method: 'GET',
            data: {'testID': $scope.tests[$scope.active - 1].id}
        }).success(function (data) {
            $scope.questions = data.qs;
//            console.log("testManage");
//            console.log(data);
        })
    } else {
        $http({
            url: './data/TestManage.json',
            method: 'GET',
            data: {'testID': $scope.tests[$scope.active - 1].id}
        }).success(function (data) {
            $scope.questions = data.qs;
//            console.log("testManage");
//            console.log(data);
        })
    }
    /*.error(function (response, status) {
     //            console.log('dddd');
     //            console.log(status)
     })*/

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
function nav($scope, Data) {
    $scope.invitedleft = Data.invitedleft();
    $scope.name = Data.name();
//    console.log(Data.name);
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
//    $scope.navUpgrade = function () {
//        $scope.template = 'upgrade.html';
//        $scope.ContentUs = 'contentUs.html';
//        $scope.leftBar = '';
//    };

    $scope.navPersonal = function () {
        $scope.template = 'user.html';
        $scope.ContentUs = 'contentUs.html';
        $scope.leftBar = '';
    };
}


function Upgrade($scope) {
    $scope.url = '#/upgrade';
    $scope.template = 'upgrade.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
}
function RockRoll($scope, $routeParams) {
    $scope.url = '#/upgrade';
    $scope.template = 'rrtest.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    $scope.rrid = $routeParams.rrid;
}


//function TestBank($scope) {
//    $scope.active = 1;
//    $scope.template = $scope.Qtype[0];
//    $scope.GoPage = function (target) {
//        $scope.show = 1;
//        $scope.active = target.getAttribute('data');
//        $scope.question = $scope.questionss[target.getAttribute('data') - 1];
//    };
//}

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
    };
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
            if (data.length == 0) {
//                $scope.result = '';
                $timeout($scope.querry, 1000);
            } else {
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

//                if ($scope.flag > 0) {
//                $scope.result = '<br>Try again';
//                    $scope.flag -= 1;
//                    $timeout($scope.querry, 1000);
//                setTimeout($scope.querry(), 1000);
//                }
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

//        $scope.flag = 5;
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
function mdeditor($scope) {
    $scope.text = "Hello [FIRST_NAME] [LAST_NAME],<br/>In order to assess your programming skills we've prepared a programming challenge that we would like you to complete. <br/>    The following link takes you to your test:<br/> [PRIVATE_TEST_LINK] <br/>  After clicking the link you will be able to choose to start the test, practice with a demo test or come back later.  <br/>  Best of luck!  <br/> Regards,<br/>  [COMPANY_NAME]";
    $scope.copyHTML = function () {

        $scope.html = document.getElementById("wmd-output").innerText;
        console.log($scope.html);
    };
    $scope.clean = function () {
        $scope.text = "";
        document.getElementById("wmd-output").innerHTML = "<pre><code></code></pre>";
        document.getElementById("wmd-preview").innerHTML = "";
        document.getElementById("wmd-input").value = "";
        $scope.html = "";
    };
    function sign($scope) {
        $scope.active = 0;
        $scope.GoPage = function () {
            $scope.active = 1 - $scope.active;
        }
    }
}
function personal($scope, $http) {
    if ($scope.local == true) {
        $scope.personal = {
            "email": "cc@qq.com",
            "name": "myname",
            "company": "国际大企业",
            "tel": "2344",
            "old_pwd": "dsds23454df",
            "new_pwd": "dsdsdsdswew3"
        };
//        $http({
//            url: './data/personal.json',
//            method: 'GET'
//        }).success(function (data) {
//            $scope.personal = data;
//        });
    } else {
        $http({
            url: './data/indexGET.json',
            method: 'GET'
        }).success(function (data) {
            $scope.personal = data;
        });
    }

}


