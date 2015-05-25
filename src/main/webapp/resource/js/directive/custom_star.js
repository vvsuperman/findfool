

//辅助指令，用于处理列表的显示数据
OJApp.directive('star', function() {
	return {
		restrict: 'AE',
		scope:{},
		link: function(scope, elem, attrs) {
			var starNum =0
			if(attrs.num > 0 && attrs.num <= 0.2){
				starNum =1;
			}else if(attrs.num > 0.2 && attrs.num <= 0.4){
				starNum =2;
			}else if(attrs.num > 0.4 && attrs.num <= 0.6){
				starNum =3;
			}else if(attrs.num > 0.6 && attrs.num <= 0.8){
				starNum =4;
			}else if(attrs.num > 0.8 ){
				starNum =5;
			}
			
			scope.context="resource/static/"+ starNum +"star.png";
			scope.size ="col-md-"+attrs.size;
		},
		replace: true,
		template:"<img class='{{size}}' ng-src={{context}}  style='margin-top: -5px;'>"
	};

});