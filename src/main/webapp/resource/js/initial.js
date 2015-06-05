
//常量
var WEBROOT = "/oj";
//var WEBROOT = "";
var CUSTOM_SET = 0;
//var MD_REDIRECT = "http%3A%2F%2Flocalhost:8080%2Foj%2F%23%2F";
//var MD_REDIRECT = "http://127.0.0.1:8080/oj/#/";
var MD_REDIRECT = "http%3A%2F%2Ffindfool.com%2F%23%2F" 
	
var u = navigator.userAgent, app = navigator.appVersion;          

if(navigator.userAgent.indexOf("MSIE")>0){   
  if(navigator.userAgent.indexOf("MSIE 6.0")>0){   
    alert("不支持IE8及以下版本，请更换其它浏览器");    
  }   
  if(navigator.userAgent.indexOf("MSIE 7.0")>0){  
    alert("不支持IE8及以下版本，请更换其它浏览器");   
  }   
  if(navigator.userAgent.indexOf("MSIE 8.0")>0 && !window.innerWidth){//这里是重点，你懂的
    alert("不支持IE8及以下版本，请更换其它浏览器");  
  }   
} 

/*
if(u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1){
	console.log("I am firefox");
}//火狐内核   
*/
//mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端    
/*ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端              
android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器   
iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器    
iPad: u.indexOf('iPad') > -1, //是否iPad              
webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部        */ 

