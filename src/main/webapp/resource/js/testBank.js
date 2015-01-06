/**
 * Created by liuzheng on 2014/7/11.
 */
function Answers() {
    this.text = "";
    this.isright = "";
    this.score = 4;
}
function QuestionMeta() {
    this.context = "";
    this.answer = null;
    this.tag = "";
    this.name = "";
    this.type = "";
    this.qid = "";
    this.setid = "";
    this.addAnswer = function (ans) {
        if (this.answer == null)
            this.answer = new Array();
        this.answer.push(ans);
    };
    this.removeAnswer = function (v) {
        this.answer.splice(v, 1);
    };
    this.reset = function(){
        this.context = "";
        this.answer = null;
        this.tag = "";
        this.name = "";
        this.type = "";
        this.qid = "";
        this.setid = "";   	
    };
    this.copyObj = function(obj){
        this.context = obj.context;
        this.answer = obj.answer;
        this.tag = obj.tag;
        this.name = obj.name;
        this.type = obj.type;
        this.qid = obj.qid;
        this.setid = obj.setid;    	
    }
}

OJApp.controller('TestBank',function($scope, $http,Data,$sce,$modal) {
    $scope.url = '#/bank';
    $scope.template = 'page/testBank.html';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.leftBar = 'page/libleftBar.html';
    $scope.active = 1;
    $scope.show = 1;
    $scope.privi = Data.privi();
    $scope.reciveData = {};
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
	$scope.reciveData.choosedQlist = new Array();
	$scope.reciveData.setObj = null;
    $scope.progrma = new Object();
    $scope.progrma.show = false;
    $scope.newQuestion = new QuestionMeta();
    $scope.sendQuestion = new QuestionMeta();
    $scope.tag = "";
	$scope.myu = 1;
    $scope.Qtype = [
        { name: '选择题', data: '1'},
        { name: '编程题', data: '2'},
    ];
    
    $scope.domainType =1;
    
    
	//查看和修改试题的通用方法
	$scope.modifyQuestionInTest = function (size,q,params) {
	   	var question = jQuery.extend(true, {}, q);
		 var modalInstance = $modal.open({
		      templateUrl: 'page/myModalContent.html',
		      controller: 'ModalInstanceCtrl',
		      size: size,
		      resolve: {
		          params:function(){
		        	  var obj ={};
		        	  obj.operation = params.operation;
		        	  obj.title=params.title;
		        	  obj.question = question;
		        	  return obj;
		          }
		      }
		 });
	 };
    
    
    $scope.editorOptions =
	{
	    language: 'ru',
	    uiColor: '#000000'
	};
    
    $scope.editorOptions1 =
	{
	    language: 'ru',
	    uiColor: '#000000'
	};
    
    $scope.editorOptions2 =
	{
	    language: 'ru',
	    uiColor: '#000000'
	};
    
//    $scope.qs = [
//        {id: '1', name: 'hdh1', type: 'xzt', score: 4, detail: 'i dont know'},
//        {id: '2', name: 'hdh2', type: 'xzt', score: 4, detail: 'i dont know'},
//        {id: '3', name: 'hdh3', type: 'xzt', score: 4, detail: 'i dont know'},
//        {id: '4', name: 'hdh4', type: 'xzt', score: 4, detail: 'i dont know'}
//    ];
    //$scope.template = $scope.Qtype[0];
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
    $scope.getSets = function(){
    	
    	var user = new Object();
    	user.uid = Data.uid();
    	$http({
    	    url: WEBROOT+"/search/sets",
    	    method: 'POST',
    	    headers: {
    	        "Authorization": Data.token()
    	    },
    	    data: user
    	}).success(function (data) {
    	    $scope.state = data["state"];//1 true or 0 false
    	    $scope.message = data["message"];
    	    if ($scope.state) {
    	    	//仅需要对message中的数据做处理
    	    	//total pageNum
    	    	$scope.reciveData.sets = $scope.message;
    	    	$scope.problemSet =[];
    	    	for(var i=0;i<$scope.reciveData.sets.length;i++){
    	    		var sets = $scope.reciveData.sets[i].problemSets;
    	    		for(var j=0;j<sets.length;j++){
    	    			$scope.problemSet.push(sets[j]);
    	    		}
    	    	}
    	    } else {

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
    $scope.getQuestions = function(sendData){
    	
    	$http({
    	    url: WEBROOT+"/search/site",
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
//    	    	$scope.reciveData.pageNum = $scope.message.pageNum;
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
    
    /*
     * 点击试题库后显示试题
     * */
    $scope.showQuestions = function(set){
    	$scope.reciveData.selectedSets = set; //一个problemset
    	$scope.queryQuestions(1);
    	$scope.set.show=0;
    }
    
    
    $scope.queryQuestions = function(index){
    	$scope.reciveData.currentPage = index;
    	for(i=0;i<$scope.reciveData.pagelist.length;i++){
    		$scope.reciveData.pagelist[i].current = false;
    	}
    	$scope.reciveData.pagelist[index-1].current=true;
    	var sendData = new Object();
    	sendData.user = new Object();
    	sendData.user.uid = Data.uid();
    	sendData.type = $scope.reciveData.type;
    	if($scope.reciveData.type != "2" && $scope.reciveData.selectedSets != null){
    		sendData.setid=$scope.reciveData.selectedSets.problemSetId;
    	}
    	sendData.keyword = $scope.reciveData.keyword;
    	sendData.page = index;
    	sendData.pageNum = $scope.reciveData.pageNum;
    	$scope.getQuestions(sendData);
    	$scope.showSet=0;
    }
    
    //载入页面时候向服务器获取试题集
    $scope.getSets();
    $scope.computePage();
    $scope.set={};
    $scope.set.show =1;
  //  $scope.queryQuestions(1);
    
    
    //end by zpl
   
    $scope.goPage = function (data) {
    	console.log("data.............",data);
        $scope.show = 1;
        $scope.context = "";
        $scope.keyword = "";
        $scope.active = data;
        $scope.reciveData.type = $scope.active;
        $scope.tag = "";
        $scope.newQuestion.reset();
        $scope.set.show=1;
        $scope.reciveData.selectedSets ={};
        $scope.reciveData.setObj = null;
    };

    $scope.AddPage = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.show = 0;
        $scope.newQuestion.reset();
        $scope.reciveData.keyword = '';
    };
    
    
    $scope.isNum = function (q) {
        if (q == null || q == "")
            return;
        var r = /^\+?[0-9][0-9]*$/;
        if (!r.test(q)) {
            alert("必须是数字！")
            q = 0;
        }
    }
    
    
    $scope.pushQuestion = function (sendData) {
        $http({
            url: WEBROOT+"/question/add",
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
                $scope.newQuestion.reset();
                $scope.reciveData.setObj = null;
            } else {
                alert('添加失败');
            }
        }).error(function (data) {

        });
    }
     
   
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
            url: WEBROOT+"/test/manage/submite",
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
	$scope.addOnelist = function(q){
		var idx = $scope.reciveData.choosedQlist.indexOf(q);

		// is currently selected
		if (idx > -1) {
		  $scope.reciveData.choosedQlist.splice(idx, 1);
		}
		// is newly selected
		else {
			if($scope.myu == 1){
				$scope.reciveData.choosedQ = q;
				$scope.reciveData.choosedQlist = new Array();
			}
			  $scope.reciveData.choosedQlist.push(q);		
		}		
	}
});
