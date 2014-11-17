/*文件上传指令，暂时不用 https://github.com/leon/angular-upload 这个看起来不错*/

OJApp.directive('customcheckbox', function() {
	return {
		restrict: 'A',
		scope: true,
		link: function(scope, elem, attrs, ctrl) {
			elem.prepend("<span class='icon'></span><span class='icon-to-fade'></span>");
			 var checkBoxChecked = "checked";
			 var checkBoxDisabled = "disabled";
			//Radio
			 var radioOn = "checked";
			 var radioDisabled = "disabled";	
			
			//判断选项是否正确
			if(attrs.isright == "true"){
				elem.addClass(checkBoxChecked);
				elem.css("border-color","#2fe2bf");	
			}
			 
			//若当前是编辑，则可以点击。否则点击无效
			 if(attrs.operation == "true"){
				 elem.click(function(){	    	
					    elem.toggleClass(checkBoxChecked);
					    if(elem.hasClass(checkBoxChecked)){
				        	elem.css("border-color","#2fe2bf");	
				        }else{
				        	elem.css("border-color","#f2f2f2");
				        }
					  });
				
			} 
			 
		
			
		   

		},
		replace: false   
	};

});