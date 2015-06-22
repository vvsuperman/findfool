//辅助指令，倒计时
OJApp.directive('timer',['$timeout', function($timeout) {
	return {
		restrict: 'AE',
		scope:{},
		link: function(scope, elem, attrs) {
				
		scope.$on("countdown",function(pevent,remain){
			elem.countdown(remain, function(event) {
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
		            	scope.$emit("cdfinished");
		                break;
		        }
		    });
			
			
		})		
	    	
				
		}
	
	
	};

}]);