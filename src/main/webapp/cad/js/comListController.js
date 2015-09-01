/**
 * 做题控制器
 */
OJApp.controller('comListController',['$scope','$http','CadData',function ($scope,$http,CadData) {

    	$scope.nav = 'cad/page/cadnav.html'
		$scope.template = 'cad/page/comlist.html';
		
		$scope.comListByType=function(){
				$http({
					url : WEBROOT + "/company/comListByType",
					method : 'POST',
					headers : {
						"Authorization" : CadData.getTestToken()
					},

					data : {

					}
				}).success(function(data) {
					$scope.companyList = data["message"];
				}).error(function() {
					console.log("get data failed");
				})

			}
		
		
		$scope.comListAll=function(){
				$http({
					url : WEBROOT + "/company/findAll",
					method : 'POST',
					headers : {
						"Authorization" : CadData.getTestToken()
					},
					data : {

					}
				}).success(function(data) {
					$scope.companyList = data["message"];
				}).error(function() {
					console.log("get data failed");
				})

			}

		$scope.comListByType();
		//$scope.comListAll();

		
		$scope.showCompanyTest=function(company){
			var url = "#/cad/eachcom/"+company.id;
			 window.location.href=url;
		}
		
		
		
	
	
	$scope.myInterval = 5000;
	$scope.noWrapSlides = false;
    var slides = $scope.slides = [];
    
    slides.push({image:'resource/static/comlistbg.jpg'});
    slides.push({image:'resource/static/dpbg.jpg'});
    
    
    $scope.isTextShow=0;
	$scope.ismouseover=function(x){
		
	    $scope.isTextShow=x;
		
	}
    
$scope.ismouseleave=function(x){
		
	    $scope.isTextShow=0;
		
	}
    
	
    
    
    
    
    var professionRank=
    	[
    	               {"name":"霍明亮","company":"上海腾飞科技有限公司","profession":"java开发"},
    	               {"name":"董思华","company":"上海IT桔子空间","profession":"asp.net开发"},
    	               {"name":"陈军","company":"上海腾飞科技有限公司","profession":"java开发"},
    	               {"name":"陈蒙蒙","company":"上海IT桔子空间","profession":"asp.net开发"},
//    	               {"name":"李林军","company":"上海IT桔子空间","profession":"asp.net开发"},
    	               {"name":"刘建新","company":"上海IT桔子空间","profession":"asp.net开发"},
    	               {"name":"刘可","company":"上海余智传媒科技","profession":"安卓开发"},
//    	               {"name":"张建乐","company":"上海腾飞科技有限公司","profession":"java开发"},
    	               {"name":"陈艿熙","company":"上海余智传媒科技","profession":"安卓开发"},
//    	               {"name":"王梦路","company":"上海余智传媒科技","profession":"安卓开发"},
//    	               {"name":"李可瑶","company":"上海腾飞科技有限公司","profession":"java开发"},
    	               {"name":"王鑫","company":"上海余智传媒科技","profession":"安卓开发"},
    	               {"name":"孙威","company":"上海萌动科技有限公司","profession":"C++开发"},
    	               {"name":"方思民","company":"上海腾飞科技有限公司","profession":"java开发"},
    	               {"name":"曹杨","company":"上海萌动科技有限公司","profession":"C++开发"},
    	               {"name":"朱广亮","company":"上海萌动科技有限公司","profession":"C++开发"},
    	               {"name":"龚振伽","company":"上海腾飞科技有限公司","profession":"java开发"},
    	               {"name":"徐佳雪","company":"上海萌动科技有限公司","profession":"C++开发"},
    	               {"name":"侯元辉","company":"上海萌动科技有限公司","profession":"C++开发"}
    	              
    	               ];
    
    
    var prizeRank=
    	    	[
    	    	               {"name":"java校园挑战杯","company":"上海腾飞科技有限公司","prize":"Iphone6一部"},
    	    	               {"name":"asp.net挑战赛","company":"上海IT桔子空间","prize":"1000元现金"},
    	    	              
    	    	               {"name":"app挑战赛","company":"上海余智传媒科技","prize":"小米4一部"},
    	    	              
    	    	               {"name":"C++开发","company":"上海萌动科技有限公司","prize":"2000元现金"}
    	    	               
    	    	              
    	    	               ];
    
    $scope.professionRank=professionRank;
	$scope.prizeRank=prizeRank;
	

	
}])
