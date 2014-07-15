/**
 * Created by liuzheng on 2014/7/11.
 */
function MyTestBank($scope, $http, Data) {
    $scope.url = '#/mybank';
    $scope.template = 'mytestBank.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = 'leftBar1.html';
    $scope.active = 1;
    $scope.show = 1;
    $scope.page = 1;
    $scope.keyword = "";
    $scope.tag = "";
  //add by zpl
    $scope.reciveData = new Object();
    $scope.reciveData.selectedSets = null;
    $scope.reciveData.totalPage = 1;
	$scope.reciveData.pageNum = 1;//默认一页10个
	$scope.reciveData.type = 1;
	$scope.reciveData.keyword = '';
	$scope.reciveData.pagelist = new Array();
	$scope.reciveData.frontPage = false;
	$scope.reciveData.rearPage = false;
	$scope.reciveData.currentPage = 1;
	$scope.reciveData.index = 1;
    $scope.Qtype = [
        { name: '选择题', data: '1'},
        { name: '编程题', data: '2'},
        { name: '问答题', data: '3'}
    ];
    $scope.newQuestion = {};
    $scope.newQuestion.answer = [
        {text: "", isright: 0, score: 0}
    ];
    
    //add by zpl
    $scope.computePage = function(){
    	var index = Math.ceil($scope.reciveData.totalPage/$scope.reciveData.pageNum);
    	index = index == 0?1:index;
    	$scope.reciveData.index = index;
    	if(index <=1){
    		$scope.reciveData.frontPage = false;
    		$scope.reciveData.rearPage = false;
    	}
    	if($scope.reciveData.currentPage >= index){
    		$scope.reciveData.currentPage = index;
    		$scope.reciveData.rearPage = false;
    		$scope.reciveData.frontPage = true;
    	}else if($scope.reciveData.currentPage <= 1){
    		$scope.reciveData.currentPage = 1;
    		$scope.reciveData.rearPage = true;
    		$scope.reciveData.frontPage = false;
    	}else{
    		$scope.reciveData.rearPage = true;
    		$scope.reciveData.frontPage = true;
    	}
    	$scope.reciveData.pagelist = new Array();
    	for(i=1;i<=index;i++){
    		var obj = new Object();
    		obj.current=false;
    		obj.index = i;
    		$scope.reciveData.pagelist.push(obj);
    	}
    	$scope.reciveData.pagelist[$scope.reciveData.currentPage-1].current=true;
    }
   
    $scope.getQuestions = function(sendData){
    	$http({
    	    url: "/search/my",
    	    method: 'POST',
    	    headers: {
    	        "Authorization": Data.token
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
    $scope.queryQuestions = function(index){
    	$scope.reciveData.currentPage = index;
    	for(i=0;i<$scope.reciveData.pagelist.length;i++){
    		$scope.reciveData.pagelist[i].current = false;
    	}
    	$scope.reciveData.pagelist[index-1].current=true;
    	var sendData = new Object();
    	sendData.user = new Object();
    	sendData.user.uid = Data.uid;
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
        $scope.show = 1;
        $scope.keyword = "";
        $scope.tag = "";
        $scope.newQuestion = {};
        $scope.newQuestion.answer = [
            {text: "", isright: 0, score: 0}
        ];
        $scope.active = target.getAttribute('data');
        $scope.reciveData.type = $scope.active;
        $scope.queryQuestions(1);
//        $scope.template = $scope.templates[$scope.active - 1];
    };
    $scope.AddPage = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.show = 0;
    };
    $scope.addQuestion = function () {
//        $scope.newQuestion.type = $scope.active;
//        $scope.newQuestion.name = "null";
////        var tag = $scope.tag;
////        console.log(tag);
//        $scope.newQuestion.tag = document.getElementById("tag").value.split(",");
////        $scope.newQuestion.tag = tag.split(",");
////        console.log($scope.newQuestion.tag)
//        $http({
//            url: "/question/add",
//            method: 'POST',
//            headers: {
//                "Authorization": Data.token
//            },
//            data: {"user": {"uid": Data.uid}, "question": $scope.newQuestion}
//        }).success(function (data) {
//            $scope.state = data["state"];//1 true or 0 false
//            Data.token = data["token"];
//            $scope.message = data["message"];
//            if ($scope.state) {
//
//            } else {
//
//            }
//        }).error(function (data) {
//
//        });
    };
    $scope.searchmy = function (keyword) {
//        console.log({"user": {"uid": Data.uid}, "type": $scope.active, "page": $scope.page, "pageNum": 10, "keyword": $scope.keyword});
//        try{$scope.keyword = document.getElementById("keyword").value}catch(err){};
//        $scope.ke=$scope.keyword;
//        $scope.$apply();
//        console.log(ke);
//        console.log($scope.keyword);
//        $http({
//            url: "/search/my",
//            method: 'POST',
//            headers: {
//                "Authorization": Data.token
//            },
//            data: {"user": {"uid": Data.uid}, "type": $scope.active, "page": $scope.page, "pageNum": 10, "keyword": keyword}
//        }).success(function (data) {
//            $scope.state = data["state"];//1 true or 0 false
//            Data.token = data["token"];
//            $scope.message = data["message"];
//            if ($scope.state) {
////仅需要对message中的数据做处理
//                $scope.curPage = $scope.message.curPage;
//                $scope.pageNum = $scope.message.pageNum;
//                $scope.totalPage = $scope.message.totalPage;
//                $scope.qs = $scope.message.questions;
//
//            } else {
//
//            }
//        }).error(function (data) {
//            $scope.qs = [
//                {qid: 1, name: '第一个测试', type: 0, tag: ["测试", "选择题"], context: "选择下面正确的一项", answer: [
//                    {text: "1+1=2", isright: 1, score: 4},
//                    {text: "1+1=3", isright: 0, score: 0},
//                    {text: "1+1=1", isright: 0, score: 0}
//                ]},
//                {qid: 2, name: 'hdh2', type: 0, tag: ["测试", "选择题"], context: "选择下面正确的一项", answer: [
//                    {text: "1+1=2", isright: 1, score: 4},
//                    {text: "1+1=3", isright: 0, score: 0},
//                    {text: "1+1=1", isright: 0, score: 0}
//                ]},
//                {qid: 3, name: 'hdh3', type: 0, tag: ["测试", "选择题"], context: "选择下面正确的一项", answer: [
//                    {text: "1+1=2", isright: 1, score: 4},
//                    {text: "1+1=3", isright: 0, score: 0},
//                    {text: "1+1=1", isright: 0, score: 0}
//                ]},
//                {qid: 4, name: 'hdh4', type: 0, tag: ["测试", "选择题"], context: "选择下面正确的一项", answer: [
//                    {text: "1+1=2", isright: 1, score: 4},
//                    {text: "1+1=3", isright: 0, score: 0},
//                    {text: "1+1=1", isright: 0, score: 0}
//                ]}
//            ];
//        });
//        $scope.keyword =""
    };
//    $scope.searchmy();

    $scope.addOne = function () {
        $scope.newQuestion.answer.push({text: "", isright: 0, score: 0});
    };

    $scope.removeOne = function (v) {
//        var tmp = $scope.xlsusers;
//        console.log(v);
//        var i = $scope.newQuestion.answer.indexOf(v);
//        console.log(i);
        if (v > 0) {
            $scope.newQuestion.answer.splice(v, 1);
        }
//        $scope.xlsusers = tmp;
//        Data.xlsusers=$scope.xlsusers;
    };
}