OJApp.directive('simditor', ['$timeout', function ($timeout) {
    return {
        restrict: 'A',
        require: '?ngModel',
        scope: true,
        link: function (scope, elem, attrs, ngModel) {
        	var option ={
            	  textarea: elem,
            	  toolbar:[
            	           'color'   ,
            	           'ol'      ,     
            	           'ul'       ,//      # 无序列表
            	           'blockquote' ,//    # 引用
            	           'code'      ,//     # 代码
            	           'table'     ,//     # 表格
            	           'link'      ,//     # 插入链接
            	           'image'     ,//     # 插入图片
            	           'hr'         ,//    # 分割线
            	           'indent'     ,//    # 向右缩进
            	           'outdent'    ,//    # 向左缩进
            	         ],
            	 toolbarFloatOffset:0
            };
        	if(attrs.operation=="view"){
        		option.toolbarHidden =true;
        	}
        	
            var editor = new Simditor(option);
            
        	if(typeof(attrs.minheight)!="undefined"){
        		elem.prev().css("min-height",attrs.minheight);
        	}
        	
        	if(attrs.operation=="view"){
        		elem.prev().attr("contenteditable",false);
        	}
            
            ngModel.$render = function () {
                editor.setValue(ngModel.$viewValue);
            };
            
            elem.click(function(event){
            	 event.stopPropagation();
            })
            
            editor.on('valuechanged', function () {
               if(attrs.operation=="edit"){  //为何会被替换掉，在view时强制改为不可替换
            	   $timeout(function () {
                       scope.$apply(function () {
                           var value = editor.getValue();
                           ngModel.$setViewValue(value);
                       });
                   }); 
            	   
               }
                
            });
        }
    };
}]);