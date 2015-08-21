/**
 * 做题控制器
 */
OJApp.controller('cadRankController',['$scope','$http','CadData',function ($scope,$http,CadData) {
	

	$scope.nav = 'cad/page/cadnav.html';
	$scope.template= 'cad/page/rank.html';
	
	
	
	var compreRank =["了解数据结构与算法，知道数据结构的基础知识，能进行数据结构的基本操作，基础知识掌握较差",
	                  "11掌握数据结构与算法的基础概念，熟练运用数据结构知识，具有一定的算法基础",
	                  "精通数据结构与算法，能在项目中熟练运用数据结构的相关知识，具有较大的发展潜力"];
    
	
//	var rank=
//	{"compreRank":[
//	               {"name":"霍明亮","score":"982","rank":"1"},
//	               {"name":"董思华","score":"895","rank":"2"},
//	               {"name":"陈军","score":"823","rank":"3"},
//	               {"name":"陈蒙蒙","score":"800","rank":"4"},
//	               {"name":"李林军","score":"798","rank":"5"},
//	               {"name":"刘建新","score":"768","rank":"6"},
//	               {"name":"刘可","score":"738","rank":"7"},
//	               {"name":"张建乐","score":"717","rank":"8"},
//	               {"name":"陈艿熙","score":"703","rank":"9"},
//	               {"name":"王梦路","score":"690","rank":"10"},
//	               {"name":"李可瑶","score":"679","rank":"11"},
//	               {"name":"王鑫","score":"668","rank":"12"},
//	               {"name":"孙威","score":"634","rank":"13"},
//	               {"name":"方思民","score":"630","rank":"14"},
//	               {"name":"曹杨","score":"602","rank":"15"},
//	               {"name":"朱广亮","score":"597","rank":"16"},
//	               {"name":"龚振伽","score":"592","rank":"17"},
//	               {"name":"徐佳雪","score":"573","rank":"18"},
//	               {"name":"侯元辉","score":"560","rank":"19"}
//	              
//	               ]
//			
//			
//			
//	}
	
	
	var compreRank=
	[
	               {"name":"霍明亮","score":"982","rank":"1"},
	               {"name":"董思华","score":"895","rank":"2"},
	               {"name":"陈军","score":"823","rank":"3"},
	               {"name":"陈蒙蒙","score":"800","rank":"4"},
	               {"name":"李林军","score":"798","rank":"5"},
	               {"name":"刘建新","score":"768","rank":"6"},
	               {"name":"刘可","score":"738","rank":"7"},
	               {"name":"张建乐","score":"717","rank":"8"},
	               {"name":"陈艿熙","score":"703","rank":"9"},
	               {"name":"王梦路","score":"690","rank":"10"},
	               {"name":"李可瑶","score":"679","rank":"11"},
	               {"name":"王鑫","score":"668","rank":"12"},
	               {"name":"孙威","score":"634","rank":"13"},
	               {"name":"方思民","score":"630","rank":"14"},
	               {"name":"曹杨","score":"602","rank":"15"},
	               {"name":"朱广亮","score":"597","rank":"16"},
	               {"name":"龚振伽","score":"592","rank":"17"},
	               {"name":"徐佳雪","score":"573","rank":"18"},
	               {"name":"侯元辉","score":"560","rank":"19"}
	              
	               ];
			
	
	
	var roleRank=
		[
		               {"name":"陈康杰","score":"574","rank":"1"},
		               {"name":"刘任东","score":"563","rank":"2"},
		               {"name":"张雅洁","score":"515","rank":"3"},
		               {"name":"陈余磊","score":"502","rank":"4"},
		               {"name":"李林军","score":"498","rank":"5"},
		               {"name":"佟林丽","score":"487","rank":"6"},
		               {"name":"张芳芳","score":"461","rank":"7"},
		               {"name":"王鑫","score":"457","rank":"8"},
		               {"name":"李思惠","score":"436","rank":"9"},
		               {"name":"韩于庆","score":"390","rank":"10"},
		               {"name":"李可瑶","score":"389","rank":"11"},
		               {"name":"何雪蕾","score":"374","rank":"12"},
		               {"name":"张建乐","score":"371","rank":"13"},
		               {"name":"刘科栋","score":"357","rank":"14"},
		               {"name":"徐佳雪","score":"343","rank":"15"},
		               {"name":"王亮亮","score":"331","rank":"16"},
		               {"name":"秦佳仁","score":"310","rank":"17"},
		               {"name":"贾彪","score":"309","rank":"18"},
		               {"name":"曹杨","score":"282","rank":"19"}
		              
		               ];
				
	var techRank=
		[
		               {"name":"霍明亮","score":"783","rank":"1"},
		               {"name":"陈康杰","score":"767","rank":"2"},
		               {"name":"李林军","score":"758","rank":"3"},
		               {"name":"何雪蕾","score":"749","rank":"4"},
		               {"name":"李林军","score":"717","rank":"5"},
		               {"name":"王鑫","score":"702","rank":"6"},
		               {"name":"刘可","score":"639","rank":"7"},
		               {"name":"刘科栋","score":"608","rank":"8"},
		               {"name":"徐佳雪","score":"593","rank":"9"},
		               {"name":"王梦路","score":"587","rank":"10"},
		               {"name":"司倩倩","score":"581","rank":"11"},
		               {"name":"孙威","score":"568","rank":"12"},
		               {"name":"孙易飞","score":"547","rank":"13"},
		               {"name":"方思民","score":"520","rank":"14"},
		               {"name":"梁玉成","score":"512","rank":"15"},
		               {"name":"余山中","score":"438","rank":"16"},
		               {"name":"龚振伽","score":"429","rank":"17"},
		               {"name":"徐佳雪","score":"417","rank":"18"},
		               {"name":"侯元辉","score":"360","rank":"19"}
		              
		               ];
				
			
			$scope.compreRank=compreRank;
			$scope.roleRank=roleRank;
			$scope.techRank=techRank;

	
//	console.log(compreRank[0]['name']);
	
	
	//rank.compreRank[0].name;
	
	
	
	
	
	
	
}])