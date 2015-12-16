/**
 * Created by liuzheng on 6/7/14.
 */
//angular.module('OJApp', [
//    'ngRoute',
//    'evgenyneu.markdown-preview'
//]);



OJApp.controller('mainController',['$scope','$http','$routeParams','$location','Data','$modal',function($scope, $http,$routeParams,$location, Data,$modal) {	
   // $scope.url = '#';
     var code = $routeParams.code;
     $scope.video={};
     $scope.video.show = 0;
     
     
     if(typeof(code)!="undefined"){
    	 $http({
             url: WEBROOT+"/user/oauthorlogin",
             method: 'POST',
     		headers : {
				"Authorization" : Data.token()
			}, 
             data:{"source":"mingdao","code":code}
         }).success(function (data) {
              console.log("data................",data);
              //用户已注册，登陆
              
              if(data.state == 1){
            	  $scope.Lemail = data.message.user.email;
            	  $scope.Lpwd = data.message.user.pwd;
            	  Data.setMdToken(data.message.token);
            	  $scope.confirm(2);
              }
              //用户还未注册，注册，转到注册页面
              else if(data.state == 2){
            	  Data.setMdToken(data.message.token);
            	  Data.setEmail(data.message.user.email);
            	  Data.setCompany(data.message.user.company);
            	  Data.setMdUid(data.message.user.mdUid);
            	  var url = MD_REDIRECT+'oauthor?title=请创建一个新账号来与明道账号绑定';
            	  window.location.href=url;
              }else {
            	  
            	  flashTip("非常抱歉，访问明道api超时，请稍后重试",function(e){
             		 if(e){
             			window.location.href='http://findfool.com/#/';
             		 }else{
             			window.location.href='http://findfool.com/#/';
             		 }
             	 },{
             		 ok:"确定",
             		 reverseButtons: true
             	 })
            	  
            	  
              }
              
         }).error(function () {
              console.log("error.......");
         });
    	 
//    	 $modal.open({
//		      templateUrl: 'page/waitModal.html',
//		      controller: 'waitModalInstance',
////		      size: "lg",
//		      resolve: {}
//		 });
    	 
    	 
     }
     
     //用户已登陆
     if(typeof(Data.lastActive())!="undefined"&&typeof(Data.email())!="undefined"){
    	 var nowTime = new Date().getTime();
    	 var lastActiveTime = Data.lastActive();
    	 //半小时之内
    	 if(nowTime - lastActiveTime < 30*60*1000){
    		 window.location.href= "#/loginok"
    	 }
     }
     
     $scope.confirm = function (data) {
         if ($scope.Lemail && $scope.Lpwd) {
         	if(data==1){
         		var pwd = md5($scope.Lpwd);
         	}else{
         		var pwd = $scope.Lpwd;
         	}
         	
         	
             $http({
                 url: WEBROOT+"/user/confirm",
                 method: 'POST',
                 headers: {
                     "Authorization": Data.token()
                 },
                 data: {"email": $scope.Lemail, "pwd": pwd, "name": $scope.Lname}
             }).success(function (data) {
             	console.log("success");
                 $scope.state = data["state"];//1 true or 0 false
                 Data.clear();//清空缓存
                 var name = $scope.Lemail;
                 $scope.message = data["message"];
                 if (data["message"].handler_url != null && data["message"].handler_url !== "") {
                     name = data["message"].handler_url;
                 }
                 Data.setName(name);
                 Data.setEmail($scope.Lemail);
                 

                 if ($scope.state) {
                     Data.setToken(data["token"]);
                     Data.setUid($scope.message.uid);
                     Data.setPrivi($scope.message.privilege);
                     Data.setTel($scope.message.tel);
                     Data.setCompany($scope.message.company);
                     Data.setInvitedleft($scope.message.invited_left);
                     Data.setLastActive((new Date()).getTime());
                     $scope.invitedleft = $scope.message.invited_left;
                    
                     window.location.href = '#/loginok';
//                     $scope.name = $scope.message.handler_url;
                 } else {
                     
//                     window.location.href = '#/loginok';
                 	$scope.errmsg = data.message.msg;
                 }
             }).error(function () {
             	console.log("err");
//                     alert("网络错误");
//                     window.location.reload(true);
                 }
             )
         }
     };
     
     
     $scope.register = function (email, company) {
 		 var modalInstance = $modal.open({
 		      templateUrl: 'page/registerModal.html',
 		      controller: 'registerInstanceCtrl',
 		      size: "lg",
 		      resolve: {
 		    	 params:function(){
		        	  var obj ={};
		        	  obj.email = email;
		        	  obj.company = company;
		        	  return obj;
		          }
 		      }
 		 });
 	 };
 	 
     $scope.login = function () {
 		 var modalInstance = $modal.open({
 		      templateUrl: 'page/loginModal.html',
 		      controller: 'loginInstanceCtrl',
 		      size: "lg",
 		      resolve: {}
 		 });
 	 };
 	 
 	 
 	 $scope.showVideo = function () {
// 		 $scope.video.show =1; 
 		 //展示视频后直接播放
 		
 		 var modalInstance = $modal.open({
		      templateUrl: 'page/videoModal.html',
		      controller: 'videoInstanceCtrl',
		      size: "lg",
		      resolve: {}
		 });
 		 

 		
 	 };
 	 
 	 //carouse
 	 $scope.myInterval = 2000;
 	 var slideText=[
 	                "自从用了FoolRank，腰不酸了，脖子不疼了，再也不用熬夜刷简历了",
 	                "FoolRank大法好，退其它招聘平台保平安",
 	                "程序猿再也不用每个平台填简历了"
 	                ]
 	  var slides = $scope.slides = [];
 	  $scope.addSlide = function(i) {
 	    slides.push({
 	      image: 'resource/static/carousel.png',
 	      text: slideText[i-1]
 	    });
 	  };
 	  for (var i=1; i<4; i++) {
 	    $scope.addSlide(i);
 	  }
    
	
	 $scope.labels =["html5", "css3", "javascript", "angularjs", "node.js", "object-c", "java"];

	  $scope.data = [
	    [65, 59, 90, 81, 56, 55, 100],
	    [58, 48, 40, 19, 96, 27, 78]
	  ];
	  
	  $scope.labelsScore1 = ["得分", "总分"];
	  $scope.dataScore1 = [80, 100];
	  
	  $scope.labelsScore2 = ["我的排名", ""];
	  $scope.dataScore2 = [90, 10];
	  
	 
    $scope.contactus = function () {
//        Data.email=$scope.Cemail;
        if (Data.email()) {
            $http({
                url: WEBROOT+'/contactus',
                method: 'POST',
                headers: {
                    "Authorization": Data.token()
                },
                data: {"email": Data.email(), "name": Data.name(), "msg": $scope.msg}
            }).success(function (data) {
                $scope.state = data["state"];//1 true or 0 false
                $scope.message = data["message"];

                if ($scope.state) {
                    $scope.thx = "感谢您的提交";
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
                    url: WEBROOT+'/contactus',
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
    
    $scope.addadmin = function () {
        $http({
            url: WEBROOT+"/user/add/admin",
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
    /*$scope.createCode = function () {
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
    $scope.createCode();*/
  

    $scope.show = 1;
    $scope.btn = function () {
        $scope.show = -($scope.show - 1);
    };
    $scope.enter = function ($event) {
        if ($event.keyCode == 13) {
            $scope.confirm(1)
        }
    };
}]);


OJApp.controller('nav', ['$scope','Data',function($scope, Data) {
	$scope.invitedleft = Data.invitedleft();
	$scope.name = Data.email();
	
	var hash=window.location.hash;
	if(hash=="#/test"||hash=="#/innertest"){
		$scope.isTestActive=true;
	} else if(hash=="#/bank"||hash=="#/mybank"){
		$scope.isLibActive=true;
	}
	// console.log(Data.name);
	$scope.navTest = function() {
		$scope.template = 'testshow.html';
		$scope.ContentUs = 'contentUs.html';
		$scope.leftBar = '';
	};
	$scope.navTestBank = function() {
		$scope.template = 'page/testBank.html';
		/* need update */
		$scope.ContentUs = 'contentUs.html';
		$scope.leftBar = 'leftBar1.html';
	};
	$scope.navmyTestBank = function() {
		$scope.template = 'page/mytestBank.html';
		/* need update */
		$scope.ContentUs = 'contentUs.html';
		$scope.leftBar = 'leftBar1.html';
	};
	// $scope.navUpgrade = function () {
	// $scope.template = 'upgrade.html';
	// $scope.ContentUs = 'contentUs.html';
	// $scope.leftBar = '';
	// };

	$scope.navPersonal = function() {
		$scope.template = 'user.html';
		$scope.ContentUs = 'contentUs.html';
		$scope.leftBar = '';
	};

	$scope.logout = function() {
		
		if(Data.getUserSource() == 'jd'){
			Data.clear();
			window.location.href = '#/jd/main';
		}else{
			Data.clear();
			window.location.href = '#/';
		}
		
		
	}
	
	

}]);

OJApp.controller('Upgrade', ['$scope',function($scope) {
	$scope.url = '#/upgrade';
	$scope.template = 'page/upgrade.html';
	$scope.ContentUs = 'page/contentUs.html';
	$scope.leftBar = '';
}]);

OJApp.controller('RockRoll', function($scope, $routeParams) {
	$scope.url = '#/upgrade';
	$scope.template = 'rrtest.html';
	$scope.ContentUs = 'contentUs.html';
	$scope.leftBar = '';
	$scope.rrid = $routeParams.rrid;
});

// function TestBank($scope) {
// $scope.active = 1;
// $scope.template = $scope.Qtype[0];
// $scope.GoPage = function (target) {
// $scope.show = 1;
// $scope.active = target.getAttribute('data');
// $scope.question = $scope.questionss[target.getAttribute('data') - 1];
// };
// }

OJApp.controller('addQuestion', function($scope) {
	$scope.Qactive = 1;
	$scope.Tactive = 1;
	$scope.Qtype = [ {
		name : '选择题',
		data : '1'
	}, {
		name : '编程题',
		data : '2'
	}, {
		name : '问答题',
		data : '3'
	} ];
	$scope.TYPE = [ {
		name : '网站题库',
		data : '1'
	}, {
		name : '自定义试题',
		data : '2'
	} ];
	$scope.goT = function(target) {
		$scope.Tactive = target.getAttribute('data');
	};
	$scope.goQ = function(target) {
		$scope.Qactive = target.getAttribute('data');
	}
});

OJApp.controller('waitModalInstance', function($scope) {

})
