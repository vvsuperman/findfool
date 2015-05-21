

//摄像头指令
OJApp.directive('camera', function() {
	return {
		restrict: 'AE',
		scope:true,
		link: function(scope, elem, attrs) {
			var width = 320, height = 240;
			var context = elem.get(0).getContext("2d");               
			var video = document.getElementById("video"); 
			var firstFaceId=null;
			//开启摄像头
			scope.$on("takeVideo",function(){
				console.log("执行takeVideo()");
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
				console.log("执行takePicture");
			})
		    
			//使用照片
		    scope.$on("updatePicture",function(event){
		    	//get BitmapData object
		    	var imageData = context.getImageData(0, 0, width, height);
		    	var imgsrc1 = canvas.toDataURL('image/jpeg', 0.7);
		    	var imgsrc = canvas.toDataURL('image/jpeg', 0.7).substr(23);
		    	
		    	var b=new Base64();
		    	var imgsrc3 =utf16to8((canvas.toDataURL('image/png'))).substr(23);
		    	
		    	var imgDecode=b.decode(imgsrc);
		    	var img3 = utf16to8(imgDecode);
		    	$.ajax({
			    		url :WEBROOT+"/upload/img",
			    		type : "POST",
			    		contentType : "application/json",
			    		data:  JSON.stringify({ "imgData": imgsrc,"email":scope.email,"invitedid":scope.invitedid}),
			    		method:"POST",
			    		dataType : "json",
			    		success : function(result) {
		    		}
		    	});
		    	//face++
		    	var api = new FacePP('0ef14fa726ce34d820c5a44e57fef470',
		                '4Y9YXOMSDvqu1Ompn9NSpNwWQFHs1hYD', {
		                    apiURL: 'http://apicn.faceplusplus.com/v2'
		                });
//		    	console.log(imgsrc1);
		        api.request('detection/detect', {
//		            url: 'http://photocdn.sohu.com/20111026/Img323493088.jpg'
		            img: imgsrc3
		        }, function(err, result) {
		            if (err) {
		                alert('载入失败');
		                return;
		            }
		            //获取face_id
		            var faceId=result.po;io9face[0].face_id;
		          //存储第一张照片路径到firstImageData
		            if(firstFaceId==null){
		            	firstFaceId=faceId;
		            	firstFaceId='eec562ced7e8f508ad504b701c8f5b60';
		            	api.request('/recognition/compare',{
		            		face_id1: faceId,
		            		face_id2: firstFaceId
		            	}, function(err,result){
		            		if(err) {
		            			alert("对比照片失败");
		            			return;
		            		}
		            		console.log(JSON.stringify(result));
		            	});
		            }
		            //与第一张照片进行比较，输出相似度
		            else{
		            	api.request('/recognition/compare',{
		            		face_id1: faceId,
		            		face_id2: firstFaceId
		            	}, function(err,result){
		            		if(err) {
		            			alert("对比照片失败");
		            			return;
		            		}
		            		console.log(JSON.stringify(result));
		            	})
		            }
		            
		        });		    
		    });
		}
	};

});


var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";  
var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);  
/** 
 * base64编码 
 * @param {Object} str 
 */  
function base64encode(str){  
    var out, i, len;  
    var c1, c2, c3;  
    len = str.length;  
    i = 0;  
    out = "";  
    while (i < len) {  
        c1 = str.charCodeAt(i++) & 0xff;  
        if (i == len) {  
            out += base64EncodeChars.charAt(c1 >> 2);  
            out += base64EncodeChars.charAt((c1 & 0x3) << 4);  
            out += "==";  
            break;  
        }  
        c2 = str.charCodeAt(i++);  
        if (i == len) {  
            out += base64EncodeChars.charAt(c1 >> 2);  
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));  
            out += base64EncodeChars.charAt((c2 & 0xF) << 2);  
            out += "=";  
            break;  
        }  
        c3 = str.charCodeAt(i++);  
        out += base64EncodeChars.charAt(c1 >> 2);  
        out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));  
        out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));  
        out += base64EncodeChars.charAt(c3 & 0x3F);  
    }  
    return out;  
}  
/** 
 * base64解码 
 * @param {Object} str 
 */  
function base64decode(str){  
    var c1, c2, c3, c4;  
    var i, len, out;  
    len = str.length;  
    i = 0;  
    out = "";  
    while (i < len) {  
        /* c1 */  
        do {  
            c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
        }  
        while (i < len && c1 == -1);  
        if (c1 == -1)   
            break;  
        /* c2 */  
        do {  
            c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
        }  
        while (i < len && c2 == -1);  
        if (c2 == -1)   
            break;  
        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));  
        /* c3 */  
        do {  
            c3 = str.charCodeAt(i++) & 0xff;  
            if (c3 == 61)   
                return out;  
            c3 = base64DecodeChars[c3];  
        }  
        while (i < len && c3 == -1);  
        if (c3 == -1)   
            break;  
        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));  
        /* c4 */  
        do {  
            c4 = str.charCodeAt(i++) & 0xff;  
            if (c4 == 61)   
                return out;  
            c4 = base64DecodeChars[c4];  
        }  
        while (i < len && c4 == -1);  
        if (c4 == -1)   
            break;  
        out += String.fromCharCode(((c3 & 0x03) << 6) | c4);  
    }  
    return out;  
}  
/** 
 * utf16转utf8 
 * @param {Object} str 
 */  
function utf16to8(str){  
    var out, i, len, c;  
    out = "";  
    len = str.length;  
    for (i = 0; i < len; i++) {  
        c = str.charCodeAt(i);  
        if ((c >= 0x0001) && (c <= 0x007F)) {  
            out += str.charAt(i);  
        }  
        else   
            if (c > 0x07FF) {  
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));  
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));  
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));  
            }  
            else {  
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));  
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));  
            }  
    }  
    return out;  
}  
/** 
 * utf8转utf16 
 * @param {Object} str 
 */  
function utf8to16(str){  
    var out, i, len, c;  
    var char2, char3;  
    out = "";  
    len = str.length;  
    i = 0;  
    while (i < len) {  
        c = str.charCodeAt(i++);  
        switch (c >> 4) {  
            case 0:  
            case 1:  
            case 2:  
            case 3:  
            case 4:  
            case 5:  
            case 6:  
            case 7:  
                // 0xxxxxxx  
                out += str.charAt(i - 1);  
                break;  
            case 12:  
            case 13:  
                // 110x xxxx 10xx xxxx  
                char2 = str.charCodeAt(i++);  
                out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));  
                break;  
            case 14:  
                // 1110 xxxx10xx xxxx10xx xxxx  
                char2 = str.charCodeAt(i++);  
                char3 = str.charCodeAt(i++);  
                out += String.fromCharCode(((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));  
                break;  
        }  
    }  
    return out;  
}  
//demo  
//function doit(){  
//    var f = document.f;  
//    f.output.value = base64encode(utf16to8(f.source.value));  
//    f.decode.value = utf8to16(base64decode(f.output.value));  
//}  

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