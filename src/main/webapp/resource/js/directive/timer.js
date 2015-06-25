//辅助指令，倒计时
OJApp.directive('timer',['$timeout', function($timeout) {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
			
		if(typeof(scope.time.remain)!="undefined"){
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
		            	scope.$emit("cdfinished");
		                break;
		        }
		    });	
		}
			
			
		scope.$on("countdown",function(pevent,remain){
			console.log("countdown..............");
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
//		            	scope.$emit("cdfinished");
		                break;
		        }
		    });
		})	
		

		
	    	
				
		}
	
	
	};

}]);