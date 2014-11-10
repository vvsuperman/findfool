/*文件上传指令，暂时不用 https://github.com/leon/angular-upload 这个看起来不错*/

OJApp.directive('uploader', [function() {
	return {
		restrict: 'E',
		scope: {
			action: '@'
		},

		controller: ['$scope', function ($scope) {

			$scope.progress = 0;
			$scope.avatar = '';

		   $scope.sendFile = function(el) {

		       var $form = $(el).parents('form');

		       if ($(el).val() == '') {
			     return false;
		       }

		      $form.attr('action', $scope.action);

	     		  $scope.$apply(function() {
				  	$scope.progress = 0;
	  	     	  });				

			 $form.ajaxSubmit({
				type: 'POST',
				uploadProgress: function(event, position, total, percentComplete) { 
					$scope.$apply(function() {
						// upload the progress bar during the upload
						$scope.progress = percentComplete;
					});

				},

				error: function(event, statusText, responseText, form) { 

					// remove the action attribute from the form
					$form.removeAttr('action');

				},

				success: function(responseText, statusText, xhr, form) { 

					var ar = $(el).val().split('\\'), 
					filename =  ar[ar.length-1];

					// remove the action attribute from the form
					$form.removeAttr('action');

					$scope.$apply(function() {
					$scope.avatar = filename;
				});

				},
			});
		  }
		}],

		link: function(scope, elem, attrs, ctrl) {
			
			elem.find('.fake-uploader').click(function() {
			elem.find('input[type="file"]').click();
		});
		
		},
		replace: false,    
		templateUrl: 'page/admin/uploader.html'
	};

}]);