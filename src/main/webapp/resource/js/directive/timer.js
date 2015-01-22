//辅助指令，用于处理列表的显示数据
OJApp.directive('timer', function() {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
				elem.countdown(scope.time.remain, function(event) {
			        var $this = $(this);
			        switch(event.type) {
			            case "seconds":
			            case "minutes":
			            case "hours":
			            case "days":
			            case "weeks":
			            case "daysLeft":
			                $this.find('span.'+event.type).html(event.value);
			                break;
			            case "finished":
			                $this.hide();
			                break;
			        }
			    });
			
		}
	};

});