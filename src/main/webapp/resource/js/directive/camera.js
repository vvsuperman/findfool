

//摄像头指令
OJApp.directive('camera', function() {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
			var width = 320, height = 240;
			var context = elem.get(0).getContext("2d");               
			var video = document.getElementById("video");  
			
			scope.$on("takeVideo",function(){
				isSupportH5Video();
				try {                  
					//动态创建一个canvas元 ，并获取他2Dcontext。如果出现异常则表示不支持                
					document.createElement("canvas").getContext("2d");        
			        console.log( "浏览器支持HTML5 CANVAS");         
			    }         
			    catch (e) {           
			        console.log("浏览器不支持HTML5 CANVAS");      
			    }   
			    
				    
				setupCamera(video,scope);
			});
			
			//拍照
			scope.$on("takePicture",function(event){
				context.drawImage(video, 0, 0, width, height);
			})
		    
			//使用照片
		    scope.$on("updatePicture",function(event){
		    	//get BitmapData object
		    	var imageData = context.getImageData(0, 0, width, height);
		    	var imgsrc = canvas.toDataURL('image/jpeg', 0.7).substr(22);
		    			    	
		    	$.ajax({
			    		url :WEBROOT+"/upload/img",
			    		type : "POST",
			    		contentType : "application/json",
			    		data:  JSON.stringify({ "imgData": imgsrc,"email":scope.email,"invitedid":scope.invitedid}),
			    		method:"POST",
			    		dataType : "json",
			    		success : function(result) {
			    			flashTip("照片已上传成功");
		    		}
		    	});
		    	
		    	
		    });
			
		}
	};

});


var setupCamera = function(video,scope)
{
	var errorCallback = function (error) {           
	      console.log("Video capture error: ", error.code);  
	      if (error.name == "PermissionDeniedError"
				|| error.code == "PermissionDeniedError") {
	    	  console.log("cameraerr open failed");
	    	  flashTip("不小心点拒绝了吧。请清除缓冲或重启浏览器来重新开启摄像头。");
	    	  scope.$emit("cameraErr");
	      }
	     
	};               
	    //navigator.getUserMedia这个写法在Opera中是navigator.getUserMedianow      
	// Normalizes window.URL
	
	var  successsCallback = function(stream) {
//		 scope.camera.video =true;
//		 scope.camera.show = false;
	      video.src = (window.URL && window.URL.createObjectURL) ? window.URL.createObjectURL(stream) : stream;
	      video.play();
	      console.log("cameraerr open success");
	      scope.$emit("cameraOK");
	}
	
	
	
	window.URL = window.URL || window.webkitURL || window.msURL || window.oURL;

	 // Normalizes navigator.getUserMedia
	navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia
	                        || navigator.mozGetUserMedia || navigator.msGetUserMedia;

	
	try {
     // Tries it with spec syntax
		console.dir(navigator.getUserMedia);
        navigator.getUserMedia({ video: true }, successsCallback, errorCallback);
        
   } catch (err) {
     console.log(err);
     // Tries it with old spec of string syntax
     navigator.getUserMedia('video', successsCallback, errorCallback);
    
   }
}

var isSupportH5Video = function()
{	
	 // Normalizes navigator.getUserMedia
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia
                           || navigator.mozGetUserMedia || navigator.msGetUserMedia;

	if (isChromiumVersionLower()) {
		return false;
	} else if (!navigator.getUserMedia) {
	    return false;
	}
	else if(navigator.getUserMedia.length == '0')
	{
		return false;
	}
	else {
	    return true;
	}

}


var isChromiumVersionLower = function() {
	var ua = navigator.userAgent;
	var testChromium = ua.match(/AppleWebKit\/.* Chrome\/([\d.]+).* Safari\//);
	if (!testChromium) return false;
	
	var rltArray = testChromium[1].split('.');
	return ((parseInt(rltArray[0]) < 18) || ((parseInt(rltArray[0]) == 18) && (parseInt(rltArray[1]) == 0) && (parseInt(rltArray[2]) < 966)));
}