/*
 * flex.js
 * 提供flex相关功能，包括1.设备检查；2.拍照；3.监控；4.视频；5.音频
 * 为了让flex可调用，所有的函数需要是全局的
 * @author zengjie 下午4:48:15
*/
var params = {
	testId : 0,
	questionId : 0,
	loadTimeout:30000,
	onMicrophoneMissing : "flash_onMicrophoneMissing",
	onCameraMissing : "flash_onCameraMissing",
	onMuted : "flash_onMuted",
	onReady : "flash_onReady",
	onStop : "flash_onStop",
	onProgress : "flash_onProgress",
	onConnectError : "flash_onConnectError"
};
var flex_pub = {
	gevent_flash_onReady : "gevent_flash_onReady",//当条件准备好
	gevent_flash_onStop : "gevent_flash_onStop",
	gevent_flash_onMuted : "gevent_flash_onMuted",//拒绝
	gevent_flash_onCreationComplete : "gevent_flash_onCreationComplete",//FlashPlayer创建完毕时
	gevent_flash_onUploadComplete: "gevent_flash_onUploadComplete",//FlashPlayer创建完毕时
	gevent_flash_onUploadError :  "gevent_flash_onUploadError",//FlashPlayer上传失败时
	gevent_flash_onConnectError :  "gevent_flash_onConnectError"//FlashPlayer连接失败时
};

function flash_debug(msg) {
	console.log(msg);
}
function flash_showSettings() {
	var player = document.getElementById("FlashPlayer");
	player.showSettings();
	console.log("flash_showSettings");
	return false;
}

function flash_reset() {
	var player = document.getElementById("FlashPlayer");
	player.reset();
	console.log("flash_reset");
	return false;
}

function flash_setPhotoMode() {
	var player = document.getElementById("FlashPlayer");
	player.setPhotoMode(params);
	console.log("flash_setPhotoMode");
	return false;
}

function flash_setMonitorMode(testId) {
	if (!testId){
		return false;
	}
	var player = document.getElementById("FlashPlayer");
	params.testId =testId;
	player.setMonitorMode(params);
	console.log("flash_setMonitorMode");
	return false;
}

function flash_isMuted() {
	var player = document.getElementById("FlashPlayer");
	if(!player){
	    return null;
	}
	var result = player.isMuted();
	console.log("flash_isMuted: " + result);
	return result;
}

function flash_setAudioMode(testId,questionId) {
	if(!testId || !questionId){
		return false;
	}
	var player = document.getElementById("FlashPlayer");
	params.testId =testId;
	params.questionId =questionId;
	player.setAudioMode(params);
	console.log("flash_setAudioMode:"+testId+'-'+questionId);
	return true;
}

function flash_setVideoMode(testId,questionId) {
	if(!testId || !questionId){
		return false;
	}
	var player = document.getElementById("FlashPlayer");
	params.testId =testId;
	params.questionId =questionId;
	player.setVideoMode(params);
	console.log("flash_setVideoMode:"+testId+'-'+questionId);
	return true;
}

function flash_takePhoto() {
	var player = document.getElementById("FlashPlayer");
	player.takePhoto();
	console.log("flash_takePhoto");
	return false;
}

function flash_uploadPhoto() {
	var player = document.getElementById("FlashPlayer");
	player.uploadPhoto("flash_onUploadComplete", "flash_onUploadError");
	console.log("flash_uploadPhoto");
	return false;
}

function flash_hasMicrophone() {
	var player = document.getElementById("FlashPlayer");
	var hasMicrophone = player.hasMicrophone();
	console.log("flash_hasMicrophone: " + hasMicrophone);
	return hasMicrophone;
}

function flash_hasCamera() {
	var player = document.getElementById("FlashPlayer");
	var hasCamera = player.hasCamera();
	console.log("flash_hasCamera: " + hasCamera);
	return hasCamera;
}

function flash_onUploadComplete(data) {
	console.log("upload complete: data=" + data);
	$.gevent.publish(flex_pub.gevent_flash_onUploadComplete,data);
}

function flash_onUploadError(data) {
	console.log("uploadPhoto fault: data=" + data);
	$.gevent.publish(flex_pub.gevent_flash_onUploadError,data);
}

function flash_record() {
	var player = document.getElementById("FlashPlayer");
	player.record();
	console.log("flash_record");
	return false;
}

function flash_play(url) {
	var player = document.getElementById("FlashPlayer");
	player.play(url);
	console.log("flash_play");
	return false;
}

function flash_pause() {
	var player = document.getElementById("FlashPlayer");
	player.pause();
	return false;
}

function flash_resume() {
	var player = document.getElementById("FlashPlayer");
	player.resume();
	return false;
}

function flash_stop() {
	var player = document.getElementById("FlashPlayer");
	player.stop();
	console.log("flash_stop");
	return false;
}

function flash_uploadMedia() {
	var player = document.getElementById("FlashPlayer");
	player.uploadMedia("flash_onUploadComplete", "flash_onUploadError");
	console.log("flash_uploadMedia");
	return false;
}

function flash_onCreationComplete() {
	$.gevent.publish(flex_pub.gevent_flash_onCreationComplete);
}

function flash_onMicrophoneMissing() {
	console.log("onMicrophoneMissing");
}

function flash_onCameraMissing() {
	console.log("onCameraMissing");
}

function flash_onMuted() {
	console.log("flex_onMuted");
}

function flash_onReady() {
	$.gevent.publish(flex_pub.gevent_flash_onReady);
	console.log("flash_onReady");
}

function flash_onStop() {
	$.gevent.publish(flex_pub.gevent_flash_onStop);
	console.log("flex_onStop");
}

function flash_onProgress(duration, current) {
	console.log("flex_onProgress: duration=" + duration + ", current=" + current);
}

function flash_onConnectError(obj) {
	$.gevent.publish(flex_pub.gevent_flash_onConnectError);
	console.log("onConnectError: obj=" + obj);
}
//判断页面是否安装flash player
function flashChecker() {
	var hasFlash = false; // 是否安装了flash
	var swf = null;
	if (document.all) {
		try {
			swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
			if (swf) {
				hasFlash = true;
			}
		} catch (e) {
		}
	} else {
		if (navigator.plugins && navigator.plugins.length > 0) {
			swf = navigator.plugins["Shockwave Flash"];
			if (swf) {
				hasFlash = true;
			}
		}
	}

	swf = null;
	return hasFlash;
}
function createSwfObject() {
	var swfVersionStr = "10.0.0";
	var xiSwfUrlStr = "app/flex/playerProductInstall.swf";
	var flashvars = {};
	var params = {};
	params.quality = "high";
	params.bgcolor = "#2a4462";
	params.allowscriptaccess = "always";
	params.allowfullscreen = "true";
	params.loop = "true";
	params.menu = "false";
	params.wmode = "window";//opaque，window
	var attributes = {};
	attributes.id = "FlashPlayer";
	attributes.name = "FlashPlayer";
	attributes.align = "middle";
	swfobject.embedSWF("app/flex/FlashPlayer.swf?__v=" + window.sets_version , "flashContent", "100%", "100%", swfVersionStr, xiSwfUrlStr, flashvars,
			params, attributes);
	swfobject.createCSS("#flashContent", "display:block;text-align:left;");
}
function flash_setProperty(option){
	params.red5Url = option.red5Url;
	params.baseUrl =option.baseUrl;
	params.monitorUrl =option.monitorUrl;
	params.checkFreq = option.checkFreq;
}
