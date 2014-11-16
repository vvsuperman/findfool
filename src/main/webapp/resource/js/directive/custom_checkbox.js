/*文件上传指令，暂时不用 https://github.com/leon/angular-upload 这个看起来不错*/

OJApp.directive('customcheckbox', function() {
	return {
		restrict: 'A',
		scope: true,
		link: function(scope, elem, attrs, ctrl) {
			console.log("initial directive");
			elem.prepend("<span class='icon'></span><span class='icon-to-fade'></span>");
		    elem.click(function(){
			    var checkBoxChecked = "checked";
			    var checkBoxDisabled = "disabled";

			    // Radio
			    var radioOn = "checked";
			    var radioDisabled = "disabled";	     
			    
			    if(elem.children("input").prop("checked") == false){
			    	elem.children("input").prop("checked",true);
			    	elem.addClass(checkBoxChecked);
			    }else{
			    	elem.children("input").prop("checked",false);
			    	elem.removeClass(checkBoxChecked)
			    }
			  });

		},
		replace: false   
	};

});