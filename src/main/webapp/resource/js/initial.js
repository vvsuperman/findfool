
//常量
var WEBROOT = "/oj";
//var WEBROOT = "";
//var CUSTOM_SET = 0;
//var MD_REDIRECT = "http%3A%2F%2Flocalhost:8080%2Foj%2F%23%2F";
//var MD_REDIRECT = "http://127.0.0.1:8080/oj/#/";

var data =["了解数据结构与算法，知道数据结构的基础知识，能进行数据结构的基本操作，基础知识掌握较差",
           "掌握数据结构与算法的基础概念，熟练运用数据结构知识，具有一定的算法基础",
           "精通数据结构与算法，能在项目中熟练运用数据结构的相关知识，具有较大的发展潜力"];

var html =["初步了解html/css的基础知识，能够进行初级的页面绘制",
           "会使用html/css的进行网页绘制，能解决一般的界面问题，对html/css熟练掌握",
           "精通html/css，能有针对性的做出特效，能解决绝大部分界面问题"];

var javascript=["初步掌握javascript语言，能进行基本操作,能进行初级的javascript开发",
                "熟练掌握javascript语言，能熟练进行页面逻辑开发，能完成大部分的页面逻辑任务",
                "精通javascript,对ECMA规范有深入的认识，对前端的逻辑开发非常擅长"
                ];


var jquery=["了解jQuery，能进行初级操作",
            "掌握jQuery，能熟练的操纵网页元素",
            "精通jQuery，能非常熟练的操纵网页元素" ];


var bootstrap =[""]


var PUBLIC_COMPANY=1;
var PUBLIC_SYSTEM=0;

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

