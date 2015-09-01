OJApp.controller('companySet',['$scope','$http','$routeParams','Data',function ($scope,$http,$routeParams,Data) {
    var cid = $routeParams.companyId;

	$scope.company = {};
	$scope.show = {};
	$scope.show.step1 = true;
	$scope.showPage = 1;
	
	
	
	$scope.findAll = function() {
		$http({
			url : WEBROOT + "/company/findAll",
			method : 'POST',
			data : {}
		}).success(function(data) {
			$scope.companyList = data["message"];

			console.log("执行了查询全部");

		})
	};
    
	$scope.cDelete = function (id) {	
        $http({
        	
            url: WEBROOT+"/company/delete",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            data: {"id":id}
        }).success(function (data) {
            $scope.companyList = data["message"];
       
            $scope.findAll();
       console.log("shanchu");
  
        })
};
//将公司关联到用户  通过手动输入邮箱来关联
	$scope.addUser = function (companyId) {	
	    $http({
	        url: WEBROOT+"/company/addUser",
	        method: 'POST',
	        headers: {
	            "Authorization": Data.token()
	        },
	        data: {"companyId":$scope.company.id,"item":$scope.items}
	    }).success(function (data) {
	        $scope.msg = data["message"];
	   
	        $scope.findAll();
	   console.log("关联用户");
	
	    })
	};
	
	//插入url
	$scope.operateurl = function (x) {	
	    $http({
	        url: WEBROOT+"/operate/operateurl",
	        method: 'POST',
	        headers: {
	        },
	        data: {"arg":x}
	    }).success(function (data) {
	    	  $scope.msg = data["message"];
	
	    })
	};
	


	$scope.items=[];    //初始化数组，以便为每一个ng-model分配一个对象
	var i=0;
	
	
	$scope.Fn= {
	    add: function () {         //每次添加都要给items数组的长度加一
	        $scope.items[i] = null;
	        i++;
	    },
	    del: function (key) {      //每次删除一个输入框都后要让i自减，否则重新添加时会出bug
	        console.log(key);
	        $scope.items.splice(key, 1);
	        i--;
	    }
	}

    $scope.Fn.add();
 


	$scope.addUserUI=function(){
		  window.open ("#/company/companySet", "newwindow", "height=400, width=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no,top=200,left=300");
	}
    
    $scope.findAllByName = function () {	
    	
    	if($scope.x==2){
    	console.log($scope.Lcname);
    
        $http({
            url: WEBROOT+"/company/findAllByName",
            method: 'POST',
            data: {"cname": $scope.Lcname}

        }).success(function (data) {
            $scope.companyList = data["message"];
       
            
       console.log("按名字查询");
  
        })}
    	else{  $scope.findAll();}
};





$scope.create = function () {	
	$scope.errorMsg={};
	
	console.log($scope.company.companyname,$scope.company.address, $scope.company.mobile,$scope.company.website);
	
    if($scope.company.companyname && $scope.company.address && $scope.company.mobile && $scope.company.website){
	   $scope.companyModel={};
	   
       $http({
         url: WEBROOT+"/company/create",
         method: 'POST',
         data: {"name": $scope.company.companyname,"address": $scope.company.address,"mobile": $scope.company.mobile,
        	   "website": $scope.company.website,"description":$scope.company.description}

    }).success(function (data) {
    	$scope.state = data["state"];// 1 true or 0 false
		
		if ($scope.state == 0) {
        $scope.companyModel = data["message"];
        $scope.showPage =2; }
		else {
		$scope.errorMsg.general = data["message"];
			
		}
 
    })}
	else{
		$scope.errorMsg.general = "所有项均不能为空";
	}
};




$scope.updateImageCompany=function(companyModel){
	var url = "#/company/update/"+companyModel.id;
	 window.location.href=url;
}

$scope.show = function (cid) {	
    $http({
        url: WEBROOT+"/company/getById",
        method: 'POST',
        data: {"cid":cid }
    }).success(function (data) {
        $scope.company = data["message"];

    })


};
$scope.modifyCompany = function (company) {	
	var url = "#/company/modify/"+company.id;
	 window.location.href=url;

};


$scope.addUserFn = function (company) {	
	var url = "#/company/addUser/"+company.id;
	 window.location.href=url;

};

$scope.modify = function () {	
    $http({
        url: WEBROOT+"/company/modify",
        method: 'POST',
        data: {"cid":$scope.company.id,"name": $scope.company.name,"address": $scope.company.address,"tel": $scope.company.tel,"website": $scope.company.website,"description":$scope.company.description }
    }).success(function (data) {
        $scope.company = data["message"];
        var url = "#/company/companyset";
        window.location.href=url;
 
    })
};
$scope.show(cid);

$scope.showCompany=function(company){
	var url = "#/company/show/"+company.id;
	 window.location.href=url;
}

   $scope.findAll();
    
    
    $scope.onChange=function(x){
    	if(x==1){
    		$scope.findAll();}
    	if(x==2)
    
 {
 }   	
    }
  
   
    
}]);

