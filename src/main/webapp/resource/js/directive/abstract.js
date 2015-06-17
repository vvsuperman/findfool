

//辅助指令，用于处理列表的显示数据
OJApp.directive('abstract', function() {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
			
			//独立作用域尝试不成功，可参考http://segmentfault.com/a/1190000002773689#articleHeader4
			scope.$watch("q.context",function(){
				var context = scope.q.context;
				//截取首个<p></p>标签中的内容
				scope.tmpContext = context.substring(context.indexOf("<p>")+3,context.indexOf("</p>"));
				//改用br，显示的格式更加紧凑
				
				//若无p标签，则是br
				if(context.indexOf("<p>")==-1){
					
					scope.tmpContext = context.substring(0,context.indexOf("<br>"));
				}
				
				if(scope.tmpContext == ""){
					scope.tmpContext = context;
				}
				
				scope.tmpContext= scope.tmpContext .replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/<br>/,'');
			})
			
			if(typeof(attrs.context)!="undefined"){
				var context = attrs.context;
				
				//截取首个<p></p>标签中的内容
				scope.tmpContext = context.substring(context.indexOf("<p>")+3,context.indexOf("</p>"));
				//改用br，显示的格式更加紧凑
				
				//若无p标签，则是br
				if(context.indexOf("<p>")==-1){
					
					scope.tmpContext = context.substring(0,context.indexOf("<br>"));
				}
				
				if(scope.tmpContext == ""){
					scope.tmpContext = context;
				}
				
				scope.tmpContext= scope.tmpContext .replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/<br>/,'');
			}else{
				scope.tmpContext ="";
			}
			
		},
		replace: true,
		template:"<td>{{tmpContext}}</td>"
	};

});