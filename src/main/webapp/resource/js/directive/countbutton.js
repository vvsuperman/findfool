

//倒计时按钮
OJApp.directive('countbutton', function() {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
			var wait = 120;
			elem.click(function(){
				if(typeof(scope.mobile) == "undefined" || scope.mobile ==""){
					scope.$emit("mobileError", "手机号不得为空");
					return false;
				}else if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(scope.mobile))){
					scope.$emit("mobileError", "手机号格式不正确");
					return false
				}
				time(elem,wait,scope,scope.mobile);
			})
		}
	};
});

function time(o,wait,scope,mobile) {
    if (wait == 0) {
        o.removeAttr("disabled");          
        o.val("免费获取验证码");
        wait = 120;
    } else {
    	if(wait == 120){
    		//设定头信息,获得手机验证码
    		 $.ajaxSetup({  
    		    contentType : 'application/json'
    		 });  
    		 
    		$.post(
    		    WEBROOT+"/user/getvertifycode",
    		    JSON.stringify({"mobile":mobile}),
    			function(data){
    		      scope.$emit("vertifycode",data.message);
    		});
    	}
        o.attr("disabled", true);
        o.val("重新发送(" + wait + ")");
        wait--;
        setTimeout(function() {
            time(o,wait,scope,mobile)
        },
        1000)
    }
}