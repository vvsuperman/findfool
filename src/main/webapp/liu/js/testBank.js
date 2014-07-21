/**
 * Created by liuzheng on 2014/7/11.
 */
function TestBank($scope, $http,Data,$sce) {
    $scope.url = '#/bank';
    $scope.template = 'testBank.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = 'leftBar1.html';
    $scope.active = 1;
    $scope.show = 1;
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
    $scope.Qtype = [
        { name: '选择题', data: '1'},
        { name: '编程题', data: '2'},
        { name: '问答题', data: '3'}
    ];
    $scope.qs = [
        {id: '1', name: 'hdh1', type: 'xzt', score: 4, detail: 'i dont know'},
        {id: '2', name: 'hdh2', type: 'xzt', score: 4, detail: 'i dont know'},
        {id: '3', name: 'hdh3', type: 'xzt', score: 4, detail: 'i dont know'},
        {id: '4', name: 'hdh4', type: 'xzt', score: 4, detail: 'i dont know'}
    ];
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
    	    url: "/search/sets",
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
    	    } else {

    	    }
    	}).error(function (data) {
    		//error
    	});
    }
    $scope.gettrustContext = function(q){
    	var trust = "内容";
    	trust += q.context +"<br/>";
    	trust +="标签";
    	trust += q.tag+"<br/> <span>";
    	for(a in q.answer){
    		trust += "<p>";
    		trust +=a.text;
    		trust +="</p>";
    	}
    	trust +="</span>";
    	return $sce.trustAsHtml(trust);
    }
    $scope.getQuestions = function(sendData){
    	$http({
    	    url: "/search/site",
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
    $scope.queryQuestions = function(index){
    	if($scope.reciveData.type != "2" &&$scope.reciveData.selectedSets == null)
    		return;
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
    }
    //载入页面时候向服务器获取试题集
    $scope.getSets();
    $scope.computePage();
    $scope.queryQuestions(1);
    
    
    //end by zpl
   
    $scope.GoPage = function (target) {
        $scope.show = 1;
        $scope.active = target.getAttribute('data');
        $scope.reciveData.type = $scope.active;
        $scope.queryQuestions(1);
//        console.log($scope.active);
        $scope.question = $scope.qs[$scope.active - 1];
//        console.log($scope.question);
    };
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
    $scope.AddPage = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.show = 0;
    };
}