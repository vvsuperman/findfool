OJApp.controller('viewtempCtrl',function ($scope,$http,$modalInstance,Data,params) {
	//绑定变量到服务	
	$scope.quizName = params.quizName;
	//获取quizid
	$http({
        url: WEBROOT+"/test/gettemp",
        method: 'POST',
        headers: {
            "Authorization": Data.token()
        },
        data: {"quizName":$scope.quizName}
    }).success(function (data) {
        if(data.state!=0){
        	smoke.alert(data.message);
        	return false;
        }else{
        	$scope.testManage(data.message);
        }
       
    })
    
    
    $scope.getTypeName = function(typeName){
    	if(typeName == 1){
    		return "选择题";
    	}else if(typeName ==2){
    		return "编程题";
    	}else if(typeName ==3){
    		return "简答题";
    	}else{
    		return "no";
    	}
    }
	
    $scope.displayQuestionDetails = function(index){
		 for(i in $scope.qs){
			 $scope.isDisplay[i]=false;
		 }
		 $scope.isDisplay[index]=true;
	 }

	 $scope.hideQuestionDetails = function(index){
		 $scope.isDisplay[index]=false;
	 }
    
    
    
	//加载该测试题tid的所有题目
    $scope.testManage = function (quizid) {
        //add by zpl
		if(quizid == 0){
			smoke.alert("quiz不得为空");
			return false;
		}
		
        var sendData = new Object();
        sendData.user = new Object();
        sendData.user.uid = Data.uid();
        sendData.quizid = quizid;
        $http({
            url: WEBROOT+"/test/manage",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: sendData
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            $scope.message = data["message"];
            if ($scope.state) {
//                console.log($scope.message);
                $scope.qs = $scope.message.qs;	//$scope.qs即测试题的所有题目
                $scope.isDisplay = new Array($scope.qs.length);
                for(i in $scope.isDisplay)
                	$scope.isDisplay[i]=false;
                $scope.testtime = $scope.message.testtime;
                $scope.extraInfo = $scope.message.extraInfo;
                $scope.emails = $scope.message.emails;
                $scope.name = $scope.message.name;
            } else {

            }
        }).error(function (data) {
        	flashTip("获取数据错误");
        });
        $scope.template = 'page/testlist.html';
        $scope.myu = 1;
    };
    
	
	
	$scope.cancel = function(){
		$modalInstance.dismiss('cancel');
	}
	
	
});