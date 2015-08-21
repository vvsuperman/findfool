/**
 * 做题控制器
 */
OJApp.controller('cadRankController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	

	$scope.nav = 'cad/page/cadnav.html';
	$scope.template= 'cad/page/rank.html';
	
	
	
	var compreRank =["了解数据结构与算法，知道数据结构的基础知识，能进行数据结构的基本操作，基础知识掌握较差",
	                  "11掌握数据结构与算法的基础概念，熟练运用数据结构知识，具有一定的算法基础",
	                  "精通数据结构与算法，能在项目中熟练运用数据结构的相关知识，具有较大的发展潜力"];
    
	
	var rank=
	{"compreRank":[
	               {"name":"","score":"982","rank":"1"}
	               
	          
	               ]
			
			
			
	}
	
	//rank.compreRank[0].name;
	
	
	
	
	
	
	
}])