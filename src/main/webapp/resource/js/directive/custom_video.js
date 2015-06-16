

//辅助指令，用于视频的播放、停止
OJApp.directive('customvideo', function() {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
			
			scope.$watch("videoplay",function(){
				if(typeof(scope.videoplay)!="undefined"){
					elem[0].play();
				}
				
			});
		    
			//broadcast没用，原因未知
			scope.$on("videoplay",function(){
				console.log("video play............");
				elem[0].play();
			});
			
			scope.$on("videostop",function(){
				elem[0].end();
			})
			
		},
		

	};

});