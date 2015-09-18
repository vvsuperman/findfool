OJApp.controller("testshow",['$scope','$http','$Data',function($scope, $http, Data) {
//    $scope.local = false;
    $scope.template = 'testshow.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    $scope.name = Data.name();
    


    if ($scope.local == true) {
        $http({
            url: WEBROOT+'./data/TestManage.json',
            method: 'GET',
            headers: {
                "Authorization": Data.token()
            },
            data: {'testID': $scope.tests[$scope.active - 1].id}
        }).success(function (data) {
            $scope.questions = data.qs;
//            console.log("testManage");
//            console.log(data);
        })
    } else {
        $http({
            url: WEBROOT+'./data/TestManage.json',
            method: 'GET',
            headers: {
                "Authorization": Data.token()
            },
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
        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active - 1].id;
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active - 1].name;
        $scope.template = 'invite.html';
        $scope.leftBar = 'leftBar.html';
    };
    $scope.MultInvite = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active - 1].id;
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active - 1].name;
        $scope.template = 'MutleInvite.html';
        $scope.leftBar = 'leftBar.html';
    };
    $scope.Report = function (target) {
        $scope.active = target.getAttribute('data');
        $scope.tid = $scope.tests[$scope.active - 1].id;
        $scope.active = target.getAttribute('data');
        $scope.name = $scope.tests[$scope.active - 1].name;
        $scope.leftBar = 'leftBar.html';
        $scope.template = 'ReportPage';
    };
    $scope.addKUtest = function (target) {
//        添加库试题
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

    
    
  //利用数组取值s
	$scope.javaset={'id':15,'level':1,'num':3
	       
	};

	$scope.phpset={'id':16,'level':1,'num':3
		       
	};   
	 $scope.Remail = Data.email();
	    $scope.company = Data.company();
	    $scope.mdUid = Data.mdUid();
	$scope.userid= Data.uid();
	console.log($scope.userid);
	$scope.findAll = function() {
		$http({
			url : WEBROOT + "/test/findSet",
			method : 'POST',
			data : {"userid":$scope.userid}
		}).success(function(data) {
			$scope.setList = data["message"];

			console.log("执行了查询全部");

		})
	};
	$scope.addRandomQuiz = function() {
		$http({
			url : WEBROOT + "/test/addRandomQuiz",
			method : 'POST',
			data : {"userid":$scope.userid}
		}).success(function(data) {
			$scope.setList = data["message"];

			console.log("执行了插入");

		})
	};
	//$scope.findAll();
	$scope.addRandomQuiz();
	
    
    
    
}]);