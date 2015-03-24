

//摄像头指令
OJApp.directive('camera', function() {
	return {
		restrict: 'AE',
		scope:{},
		link: function(scope, elem, attrs) {
			isSupportH5Video();
			try {                  
				//动态创建一个canvas元 ，并获取他2Dcontext。如果出现异常则表示不支持                
				document.createElement("canvas").getContext("2d");        
		        console.log( "浏览器支持HTML5 CANVAS");         
		    }         
		    catch (e) {           
		        console.log("浏览器不支持HTML5 CANVAS");      
		    }   
		    
		    var canvas = document.getElementById("canvas");
			var context = canvas.getContext("2d");               
			var video = document.getElementById("video");         
			setupCamera(video);
			
			var width = 320, height = 240;
			
			//拍照
			scope.$on("takePicture",function(event){
				elem.show();
				$("#video").hide();
				context.drawImage(video, 0, 0, width, height);
				
		    	
			})
		    
		    scope.$on("updatePicture",function(event){
		    	//get BitmapData object
		    	var imageData = context.getImageData(0, 0, width, height);
		    	var imgsrc = canvas.toDataURL('image/jpeg', 0.7).substr(22);
		    			    	
		    	$.ajax({
			    		url :WEBROOT+"/upload/img",
			    		type : "POST",
			    		contentType : "application/json",
			    		data:  JSON.stringify({ "imgData": imgsrc,"email":"693605668@qq.com","imgName":"0001img"}),
			    		method:"POST",
			    		dataType : "json",
			    		success : function(result) {
		    		}
		    	});
		    	
		    	
		    });
			
			scope.$on("cancelPicture",function(event){
				elem.hide();
				$("#video").show();
			});
			
		}
	};

});


var setupCamera = function(video)
{
	var errorCallback = function (error) {           
	       console.log("Video capture error: ", error.code);   
	};               
	    //navigator.getUserMedia这个写法在Opera中是navigator.getUserMedianow      
	// Normalizes window.URL
	
	var  successsCallback = function(stream) {
		  console.log("video success");
	      video.src = (window.URL && window.URL.createObjectURL) ? window.URL.createObjectURL(stream) : stream;
	      video.play();
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