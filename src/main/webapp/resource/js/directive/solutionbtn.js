

//辅助指令，用于处理列表的显示数据
OJApp.directive('solutionbtn', function() {
	return {
		restrict: 'AE',
		scope:{},
		link: function(scope, elem, attrs) {
			elem.click(function(){
				
			var logohead = $(".logohead");
			logohead.animate({
					height:'-=600'
			});
			elem.parent().animate({
				height:'-=600'
			})
			elem.parent().hide();
			
				
				
				
				
				
			})
			
		}
		
	};

});