

//5秒后按钮再生效
OJApp.directive('delaybutton', ['$timeout',function($timeout) {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
			elem.click(function(){
				elem.attr("disabled", true);
				$timeout(function(){enableButton(elem)},3000);
			})
		}
	};
}]);

function enableButton(elem){
	elem.removeAttr("disabled");
}