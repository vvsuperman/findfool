OJApp.controller('companySet',['$scope','$http','$routeParams','Data',function ($scope,$http,$routeParams,Data) {
	    var cid = $routeParams.companyId;
	    console.log(cid);
	    

//	   $scope.imgifo="4,1";
	   console.log($scope.imgifo);
	$scope.company ={};
    $scope.show={};
	$scope.show.step1 =true; 
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
console.log($scope.items);



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
	console.log($scope.show);
if($scope.companyname&&$scope.address&&$scope.mobile&&$scope.website,$scope.description){
	   $scope.companyModel={};
	   console.log($scope.show);
    $http({
        url: WEBROOT+"/company/create",
        method: 'POST',
        data: {"name": $scope.companyname,"address": $scope.address,"mobile": $scope.mobile,"website": $scope.website,"description":$scope.description}

    }).success(function (data) {
        $scope.companyModel = data["message"];
        $scope.show =2; 
        location.reload() 
 
    })}
	else{
		$scope.errorMsg.general = "所有项均不能为空";}

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
        $scope.findAll();

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

OJApp.controller('upload1',['upload','$http','$scope',function(upload,$http,$scope){
	console.log("initial upload");
	$scope.doUpload=function(){
		
		console.log("doupload......................."+$scope.myFile);
//		upload({
//		      url: '/upload',
//		      method: 'POST',
//		      data: {
//		        company: 1111111,
//		        aFile: $scope.myFile, // a jqLite type="file" element, upload() will extract all the files from the input and put them into the FormData object before sending.
//		      }
//		    }).then(
//		      function (response) {
//		        console.log(response.data); // will output whatever you choose to return from the server on a successful upload
//		      },
//		      function (response) {
//		          console.error(response); //  Will return if status code is above 200 and lower than 300, same as $http
//		      }
//		    );
		
		
		
		
		//加载图片到服务器
		//$scope.uploadimg=function(flag){
//			if(flag){
//				console.log("1");
//			  
//				console.log($scope.companyname);
//				
//				
		//
//		        $http({
//		            url: WEBROOT+"/company/uploadimg",
//		            method: 'POST',
//		            data: {"flag":flag,"name": $scope.ccompanyname,"img":$scope}
		//
//		        }).success(function (data) {
//		            $scope.companyList = data["message"];
//		       
//		            
//		       console.log("按名字查询");
		//  
//		        })
//				
//			}else{
//				console.log("2");
//				}
		//	
		//}

		//
//			  $scope.doUpload = function () {
//				  console.log($scope.companyname);
//			    upload({
//			      url: '/upload',
//			      method: 'POST',
//			      data: {
//			        anint: 123,
//			        aBlob: Blob([1,2,3]), // Only works in newer browsers
//			        aFile: $scope.myFile, // a jqLite type="file" element, upload() will extract all the files from the input and put them into the FormData object before sending.
//			      }
//			    }).then(
//			      function (response) {
//			        console.log(response.data); // will output whatever you choose to return from the server on a successful upload
//			      },
//			      function (response) {
//			          console.error(response); //  Will return if status code is above 200 and lower than 300, same as $http
//			      }
//			    );
//			  }


		
	}
	
	 
}]);