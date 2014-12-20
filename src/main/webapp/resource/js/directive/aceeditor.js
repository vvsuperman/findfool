OJApp.directive('aceeditor', ['$timeout', function ($timeout) {

    var resizeEditor = function (editor, elem) {
        var lineHeight = editor.renderer.lineHeight;
        var rows = editor.getSession().getLength();

        $(elem).height(rows * lineHeight);
        editor.resize();
    };

    return {
        restrict: 'A',
        require: '?ngModel',
        scope: true,
        link: function (scope, elem, attrs, ngModel) {
            var node = elem[0];
            var editor = ace.edit(node);
            editor.setTheme('ace/theme/twilight');
            /*
            scope.$watch("lg.context", function(){
            	editor.getSession().setMode("ace/mode/"+scope.lg.context.CodeType);
            });
            */
            
            editor.getSession().setMode("ace/mode/"+attrs.language);
            // set editor options
            editor.setShowPrintMargin(false);
            // data binding to ngModel
            ngModel.$render = function () {
                editor.setValue(ngModel.$viewValue);
                resizeEditor(editor, elem);
            };
            

            editor.on('change', function () {
                $timeout(function () {
                    scope.$apply(function () {
                        var value = editor.getValue();
                        ngModel.$setViewValue(value);
                    });
                });

                resizeEditor(editor, elem);
            });
        }
    };
}]);