

//5秒后按钮再生效
OJApp.directive('delaybutton', function() {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
			elem.click(function(){
				elem.attr("disabled","disabled");
				setTimeout(function(){enableButton(elem)},1000);
			})
		}
	};
});

function enableButton(elem){
	elem.removeAttr("disabled");
}