'use strict';
angular.module('ui.autocomplete', [])
.directive('uiAutocomplete',['',function(){
	return{
		restrict: 'A',
        require: '?ngModel',
        scope: true,
        link: function (scope, elem, attr, ctrl) {
        	
        }
	};
}]);