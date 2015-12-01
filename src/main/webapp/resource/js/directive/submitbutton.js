

//倒计时按钮
OJApp.directive('submitbutton', function() {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
	
			var wait = 6;
			elem.click(function(){
				console.log("submit solution");
				subtime(elem,wait,scope,scope.mobile);
			})
		}
	};
});

function subtime(o,wait,scope,mobile) {
    if (wait == 0) {
        o.removeAttr("disabled");          
        o.html("运行/提交");
        wait = 120;
    } else {
    	if(wait == 10){
    		//设定头信息,获得手机验证码
    		 scope.$emit("submitsolution");
    		 
    	}
        o.attr("disabled", true);
        o.html("运行/提交(" + wait + ")");
        wait--;
        setTimeout(function() {
            time(o,wait,scope,mobile)
        },
        1000)
    }
}