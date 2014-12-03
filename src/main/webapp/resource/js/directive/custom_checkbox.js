/*文件上传指令，暂时不用 https://github.com/leon/angular-upload 这个看起来不错*/

OJApp.directive('customcheckbox', function() {
	return {
		restrict: 'A',
		scope:false,
//		scope: {
//			option:'=option'
//		},
		link: function(scope, elem, attrs, ctrl) {
			
			elem.prepend("<span class='icon'></span><span class='icon-to-fade'></span>");
			 var checkBoxChecked = "checked";
			 var checkBoxDisabled = "disabled";
			//Radio
			 var radioOn = "checked";
			 var radioDisabled = "disabled";	
			
			//判断选项是否正确
			if(scope.option.isright == "true"){
				elem.addClass(checkBoxChecked);
				elem.css("border-color","#2fe2bf");	
			}
			
			elem.children("textarea").click(function(event){
				console.log("textarea stop propagation......");
				event.stopPropagation();
			})
			
			 
			//若当前是编辑，则可以点击。否则不可编辑
			console.log("operation.......",attrs.operation);
			 if(attrs.operation == "edit"){
				 elem.click(function(event){	    	
					 console.log("operation",attrs.operation);
						 elem.toggleClass(checkBoxChecked);
						    if(elem.hasClass(checkBoxChecked)){
					        	elem.css("border-color","#2fe2bf");	
					        	scope.option.isright = true;
					        }else{
					        	elem.css("border-color","#f2f2f2");
					        	scope.option.isright = false;
					        }
					    event.stopPropagation();
				 });
			} else{
				elem.children("textarea").attr("disabled","disabled");
			}
		},
		replace: false   
	};

});