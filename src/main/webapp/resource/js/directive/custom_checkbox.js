
OJApp.directive('customcheckbox', function() {
	return {
		restrict: 'A',
		scope:false,
//		scope: {
//			option:'=option'
//		},
		link: function(scope, elem, attrs, ctrl) {
			
			//elem.prepend("<span class='icon'></span><span class='icon-to-fade'></span>");
			 var checkBoxChecked = "checked";
			 var checkBoxDisabled = "disabled";
			//Radio
			 var radioOn = "checked";
			 var radioDisabled = "disabled";	
			
			//判断选项是否正确
		/*	if(scope.option.isright == "true"){
				elem.addClass(checkBoxChecked);
				elem.css("border-color","#2fe2bf");	
			}*/
			
			var index = attrs.index;
			//若存在用户的选择
			if(attrs.useranswer!=""&&typeof(attrs.useranswer)!="undefined"){
				//判断用户是否选择
				if(attrs.useranswer.charAt(index)==1){
					elem.addClass(checkBoxChecked);
					elem.css("border-color","#2fe2bf");	
				}
				//判断选项是否正确
				if(typeof(attrs.rightanswer)!="undefined"&&attrs.rightanswer.charAt(index)==1){
					//elem.append("<span class='icon righticon' >");
					elem.append("<p>正确选项</p>")
				}
				
			}else{
			//若用户的选择不存在，则为查看元数据，根据rightanswer来判断选项的正确
				if(typeof(attrs.rightanswer)!="undefined"&&attrs.rightanswer.charAt(index)==1){
					elem.addClass(checkBoxChecked);
					elem.css("border-color","#2fe2bf");	
				}
			}
			
			
			
			
			elem.children("textarea").click(function(event){
				event.stopPropagation();
			})
			
			 
			//若当前是编辑，则可以点击。否则不可编辑
			 if(attrs.operation == "edit"){
				 elem.click(function(event){	    	
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