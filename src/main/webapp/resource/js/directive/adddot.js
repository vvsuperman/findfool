

//辅助指令，用于处理列表的显示数据
OJApp.directive('adddot', function() {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
			if(typeof(scope.problem.useranswer)!="undefined" && scope.problem.useranswer!=""&& scope.problem.useranswer!="0000" ){
				elem.addClass("dot");
			}
			
			scope.$on("adddot",function(event,index){
				if((index-1)==attrs.index){
					elem.addClass("dot");
				}
			});
		}
	};

});