OJApp.controller('companySet',['$scope','$http','$routeParams','Data',function ($scope,$http,$routeParams,Data) {
	    var cid = $routeParams.companyId;
	    console.log(cid);

	$scope.findAll = function () {			
                    $http({
                        url: WEBROOT+"/company/findAll",
                        method: 'POST',
                        data: {}
                    }).success(function (data) {
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
	
if($scope.companyname){
    $http({
        url: WEBROOT+"/company/create",
        method: 'POST',
        data: {"name": $scope.companyname,"address": $scope.address,"mobile": $scope.mobile,"website": $scope.website,"description":$scope.description}

    }).success(function (data) {
        $scope.companyList = data["message"];
    })}

	else{
		$scope.errorMsg.general = "公司名字不能为空";}
};

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




$scope.modify = function (cid) {	
	
    $http({
        url: WEBROOT+"/company/modify",
        method: 'POST',
        data: {"cid":cid }
    }).success(function (data) {
        $scope.company = data["message"];

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