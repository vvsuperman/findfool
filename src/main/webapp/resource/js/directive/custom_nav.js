
OJApp.directive('customnav', function() {
	return {
		restrict: 'AE',
		scope:false,
		link: function(scope, elem, attrs, ctrl) {
			console.log("custom nav..........");
			elem.click(function(){
				console.log("click nav");
				
				var parent = elem.parent();
				parent.addClass("active");
				window.location.href=attrs.url;
			})
		},
		replace: false   
	};

});