/**
 * Created by liuzheng on 2014/7/11.
 */
/*
 * object for page
 * created by zpl on 2014/7/18
 */

function MyTestBank($scope) {
    $scope.url = '#/mybank';
    $scope.template = 'mytestBank.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = 'leftBar1.html';
    $scope.active = 1;
    $scope.show = 1;
}
function mytestbank($scope, $http, Data,$sce) {
    $scope.Qtype = [
        { name: '选择题', data: '1'},
        { name: '编程题', data: '2'},
        { name: '问答题', data: '3'}
    ];
    $scope.page = 1;
    $scope.keyword = "";
    $scope.tag = "";
    $scope.context = "";
    //document.getElementById("context").
    //add by zpl
    $scope.reciveData = new Object();
    $scope.reciveData.selectedSets = null;
    $scope.reciveData.totalPage = 1;
    $scope.reciveData.pageNum = 10;//默认一页10个
    $scope.reciveData.type = 1;
    $scope.reciveData.keyword = '';
    $scope.reciveData.pagelist = new Array();
    $scope.reciveData.frontPage = false;
    $scope.reciveData.rearPage = false;
    $scope.reciveData.currentPage = 1;
    $scope.reciveData.index = 1;
    $scope.reciveData.choosedQ = null;
    $scope.progrma = new Object();
    $scope.progrma.show = false;
    $scope.newQuestion = new QuestionMeta();




    //add by zpl
    $scope.computePage = function () {
        var index = Math.ceil($scope.reciveData.totalPage / $scope.reciveData.pageNum);
        index = index == 0 ? 1 : index;
        $scope.reciveData.index = index;
        if (index <= 1) {
            $scope.reciveData.frontPage = false;
            $scope.reciveData.rearPage = false;
        }
        if ($scope.reciveData.currentPage >= index) {
            $scope.reciveData.currentPage = index;
            $scope.reciveData.rearPage = false;
            $scope.reciveData.frontPage = true;
        } else if ($scope.reciveData.currentPage <= 1) {
            $scope.reciveData.currentPage = 1;
            $scope.reciveData.rearPage = true;
            $scope.reciveData.frontPage = false;
        } else {
            $scope.reciveData.rearPage = true;
            $scope.reciveData.frontPage = true;
        }
        $scope.reciveData.pagelist = new Array();
        for (i = 1; i <= index; i++) {
            var obj = new Object();
            obj.current = false;
            obj.index = i;
            $scope.reciveData.pagelist.push(obj);
        }
        $scope.reciveData.pagelist[$scope.reciveData.currentPage - 1].current = true;
    }

    $scope.getQuestions = function (sendData) {
        $http({
            url: "/search/my",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: sendData
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            $scope.message = data["message"];
            if ($scope.state) {
                //仅需要对message中的数据做处理
                //total pageNum
                $scope.reciveData.totalPage = $scope.message.totalPage;
                $scope.reciveData.pageNum = $scope.message.pageNum;
                $scope.reciveData.questions = $scope.message.questions;
                $scope.computePage();
            } else {
                $scope.reciveData.totalPage = 1;
                $scope.reciveData.pageNum = 0;
                $scope.reciveData.questions = null;
            }
        }).error(function (data) {
            //error
        });
    }
    
    $scope.gettrustContext = function(q){
    	var trust = "<span>内容:";
    	trust += q.context +"</span><br/>";
    	trust +="<span>&nbsp;&nbsp;&nbsp;标签:";
    	trust += q.tag+"</span><br/> <span>";
    	for(var i=0;i<q.answer.length;i++){
    		trust += "<p>"+i+":";
    		trust +=q.answer[i].text;
    		trust +="</p>";
    	}
    	trust +="</span>";
    	return $sce.trustAsHtml(trust);
    }
    
    $scope.queryQuestions = function (index) {
        $scope.reciveData.currentPage = index;
        for (i = 0; i < $scope.reciveData.pagelist.length; i++) {
            $scope.reciveData.pagelist[i].current = false;
        }
        $scope.reciveData.pagelist[index - 1].current = true;
        var sendData = new Object();
        sendData.user = new Object();
        sendData.user.uid = Data.uid();
        sendData.type = $scope.reciveData.type;
        sendData.keyword = $scope.reciveData.keyword;
        sendData.page = index;
        sendData.pageNum = $scope.reciveData.pageNum;
        $scope.getQuestions(sendData);
    }
    $scope.computePage();
    $scope.queryQuestions(1);

//    $scope.template = $scope.Qtype[0];
    $scope.GoPage = function (target) {
//        console.log($scope.context);
        $scope.show = 1;
        $scope.keyword = "";
        $scope.tag = "";
        $scope.context = "";
        $scope.active = target.getAttribute('data');
        $scope.reciveData.type = $scope.active;
//        $scope.newQuestion.tag = "";
        $scope.newQuestion = new QuestionMeta();
        $scope.queryQuestions(1);
//        $scope.template = $scope.templates[$scope.active - 1];
    };
    $scope.AddPage = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.show = 0;
//        $scope.newQuestion = new Object();
//        $scope.newQuestion.context = "";
//        $scope.newQuestion.answer = new Array();
//        $scope.newQuestion.tag = "";
//        
//        var ans = new Object();
//        ans.text="";
//        ans.score=0;
//        if($scope.active == "1")
//        	ans.isright=false;
//        else
//        	ans.isright = "";
//        $scope.newQuestion.answer.push(ans);
//        var tags = "";
//        $scope.newQuestion.tag = tags;
        console.log($scope.newQuestion);
    };
    $scope.deleteQuestion = function () {
        var res = confirm("确定删除吗？删除之后不可恢复");
        if (res == true) {
            $http({
                url: "/question/delete",
                method: 'POST',
                headers: {
                    "Authorization": Data.token()
                },
                data: {"user": {"uid": Data.uid()}, "qid": $scope.reciveData.choosedQ.qid}
            }).success(function (data) {
                $scope.state = data["state"];//1 true or 0 false
                if (data["token"] != "" && data["token"] != null)
                    Data.setToken(data["token"]);
                $scope.message = data["message"];
                if ($scope.state) {
                    alert('删除成功');
                    $scope.show = "1";
                    $scope.queryQuestions(1);
                    $scope.newQuestion = new QuestionMeta();
                } else {
                    alert('添加失败');
                }
            }).error(function (data) {

            });
        }
    }
    $scope.modifyQuestion = function () {
        $scope.show = "0";
        console.log($scope.reciveData.choosedQ);
        $scope.newQuestion.qid = $scope.reciveData.choosedQ.qid;
        $scope.newQuestion.type = $scope.active;
        $scope.newQuestion.name = $scope.reciveData.choosedQ.name;
        $scope.newQuestion.context = $scope.reciveData.choosedQ.context;
        $scope.context=$scope.newQuestion.context;
        var ans = $scope.reciveData.choosedQ.answer;
        if ($scope.newQuestion.type == 1) {
            for (a in ans) {
                a.isright = (a.isright == "0" ? false : true);
            }
        }
        $scope.newQuestion.answer = ans;
        var tags = "";
        for (var i = 0; i < $scope.reciveData.choosedQ.tag.length; i++) {
            if (i == 0) {
                tags += $scope.reciveData.choosedQ.tag[i];
            } else {
                tags += ",";
                tags += $scope.reciveData.choosedQ.tag[i];
            }
        }
        $scope.newQuestion.tag = tags;
        console.log($scope.newQuestion);
    };
    $scope.isNum = function (q) {
        if (q == null || q == "")
            return;
        var r = /^\+?[0-9][0-9]*$/;
        if (!r.test(q)) {
            alert("必须是数字！");
            q = 0;
        }
    };
    $scope.pushQuestion = function (sendData) {
        $http({
            url: "/question/add",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: sendData
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            if (data["token"] != "" && data["token"] != null)
                Data.setToken(data["token"]);
            $scope.message = data["message"];
            if ($scope.state) {
                alert('添加成功');
                $scope.show = "1";
                $scope.queryQuestions(1);
                $scope.newQuestion = new QuestionMeta();
            } else {
                alert('添加失败');
            }
        }).error(function (data) {

        });
    };
    /*   $scope.$watch('context', function () {
     var context = $scope.context;
     $scope.newQuestion.context =context;
     console.log(context)
     })*/

//    $scope.$apply();
    $scope.addQuestion = function () {
        $scope.newQuestion.type = parseInt($scope.active);
        var ans = $scope.newQuestion.answer;
        if ($scope.active == "1") {
            for (a in ans) {
                a.isright = (a.isright == false ? "0" : "1");
            }
        }
//console.log($scope.newQuestion.tag)
        var tags = $scope.tag.split(",");
        $scope.newQuestion.tag = tags;

        $scope.newQuestion.answer = ans;
        //var context = document.getElementById("context").innerText;
        var context = $scope.context;
        $scope.newQuestion.context = context;

        console.log($scope.newQuestion);
        sendData = {"user": {"uid": Data.uid()}, "question": $scope.newQuestion};
        $scope.pushQuestion(sendData);
    };
    $scope.cancel = function () {
        $scope.show = "1";
    };
    $scope.resetQuestion = function () {
        $scope.newQuestion.context = "";
        $scope.newQuestion.answer = null;
        //       $scope.newQuestion.answer = new Array();
        $scope.newQuestion.tag = "";
//        var ans = new Object();
//        ans.text="";
//        ans.score=0;
//        if($scope.active == "1")
//        	ans.isright=false;
//        else
//        	ans.isright = "";
//        $scope.newQuestion.answer.push(ans);
        var tags = "";
        $scope.newQuestion.tag = tags;
    };
    $scope.searchmy = function (keyword) {

    };
//    $scope.searchmy();
    $scope.addOne = function () {
        var ans = new Answers();
        if ($scope.active == "1")
            ans.isright = false;
        else
            ans.isright = "";
        $scope.newQuestion.addAnswer(ans);
    };

    $scope.removeOne = function (v) {
        $scope.newQuestion.removeAnswer(v);
    };

    $scope.InsertQuestion = function () {
//        $scope.show = "0";
//        console.log($scope.reciveData.choosedQ);
//        $scope.newQuestion.qid = $scope.reciveData.choosedQ.qid;
        var qid=[];
        console.log($scope.qs);
        for (q in $scope.qs){
            qid.push($scope.qs[q].qid)
        }
        qid.push($scope.reciveData.choosedQ.qid);
        var uqid = [];
        $.each(qid, function(i, el){
            if($.inArray(el, uqid) === -1) uqid.push(el);
        });
        $http({
            url: "/test/manage/submite",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"user":{"uid": Data.uid()},"quizid":$scope.tid,"qids": uqid}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
//仅需要对message中的数据做处理
//                alert($scope.message.msg);
                $scope.tid=$scope.message.quizid;
                window.location.href = '#/test/'+$scope.tid;
            } else {

            }
        }).error(function (data) {

        });
    };

}