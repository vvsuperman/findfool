

//辅助指令，用于处理列表的显示数据
OJApp.directive('abstract', function() {
	return {
		restrict: 'AE',
		scope:{},
		link: function(scope, elem, attrs) {
			if(typeof(attrs.context)!="undefined"){
				var context = attrs.context;
				//截取首个<p></p>标签中的内容
				scope.tmpContext = context.substring(context.indexOf("<p>")+3,context.indexOf("</p>"));
				
				scope.tmpContext= scope.tmpContext .replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/<br>/,'');
			}else{
				scope.tmpContext ="";
			}
			
		},
		replace: true,
		template:"<td>{{tmpContext}}</td>"
	};

});