
//常量
var WEBROOT = "/oj";
//var WEBROOT = "";
var CUSTOM_SET = 0;
//var MD_REDIRECT = "http%3A%2F%2Flocalhost:8080%2Foj%2F%23%2F";
//var MD_REDIRECT = "http://127.0.0.1:8080/oj/#/";



var initialdata =["了解数据结构与算法，知道数据结构的基础知识，能进行数据结构的基本操作，基础知识掌握较差",
           "11掌握数据结构与算法的基础概念，熟练运用数据结构知识，具有一定的算法基础",
           "精通数据结构与算法，能在项目中熟练运用数据结构的相关知识，具有较大的发展潜力"];

var initialhtml =["初步了解html/css的基础知识，能够进行初级的页面绘制",
           "会使用html/css的进行网页绘制，能解决一般的界面问题，对html/css熟练掌握",
           "精通html/css，能有针对性的做出特效，能解决绝大部分界面问题"];

var initialjavascript=["初步掌握javascript语言，能进行基本操作,能进行初级的javascript开发",
                "熟练掌握javascript语言，能熟练进行页面逻辑开发，能完成大部分的页面逻辑任务",
                "精通javascript,对ECMA规范有深入的认识，对前端的逻辑开发非常擅长"
                ];


var initialjquery=["了解jQuery，能进行初级操作",
            "掌握jQuery，能熟练的操纵网页元素",
            "精通jQuery，能非常熟练的操纵网页元素" ];


var initialangularjs=["了解angularjs，了解双向数据绑定、控制器等基本理论知识",
            "掌握angularjs,能够非常熟练进行网页开发，开发经验丰富",
            "精通angularjs，对双向数据绑定等理论知识" ];


var initialbootstrap=["了解bootstrap等基本理论知识",
            "掌握bootstrap,能够非常熟练进行样式处理和布局，开发经验丰富",
            "精通bootstrap，精通bootstrap开发，有很强的工作经验" ];

var initialphp=["初步掌握php基本操作，了解php基本语法",
            "掌握php，熟悉php理论知识，可以进行简单的php开发",
            "精通php，能熟练操作php，理论知识扎实，具有实际操作能力" ];

var initialjava=["初步了解java基本知识，了解java常用操作",
            "掌握java，掌握java操作，能参与java相关开发",
            "精通java，熟练掌握java各种操作，能非常熟练进行java开发!" ];
var initialcjia=["初步了解C++基本知识，了解C++常用操作",
                 "掌握C++，掌握C++操作，能参与C++相关开发",
                 "精通C++，熟练掌握指针等功能，能非常熟练进行C++开发!" ];


var initialsql=["初步掌握数据库，能进行增删查改基本操作,能编写简单的sql语句",
                "熟练掌握各种数据库，能熟练编写sql，熟练掌握数据各种方法",
                "精通数据库操作,对数据库有深入的认识，时候数据库专业开发"
                ];

var initialandroid=["初步掌握安卓开发，了解安卓基本理论知识,了解安卓开发基本流程",
                "熟练掌握安卓开发，能熟练进行安卓开发，对理论知识有深入的理解",
                "精通安卓开发,拥有相当丰富的安卓开发经验，用于非常帮的开发能力"
                ];



var initiallinux=["初步掌握linux的基本操作，了解linux常用的基本指令，",
                "熟练掌握linux操作，能熟练在linux进行相关编程，对linux有深入的理解",
                "精通linux，非常熟练运用linux基本命令，非常适合linux编程"
                ];


var initialObjectc=["初步掌握IOS，掌握object c基本理论知识",
                "熟练掌握object c，能熟练进行IOS开发，拥有很好的编程能力",
                "精通object c ,能非常熟练进行IOS开发，拥有相当强的开发经验"
                ];


//利用数组取值
var initialstr={'数据结构与算法(阿里)':initialdata,'数据结构与算法(腾讯)':initialdata,
		'数据结构与算法(谷歌)':initialdata,'数据结构与算法(微软)':initialdata,
		'html/css':initialhtml,
		'javascript':initialjavascript,'jquery':initialjquery,'bootstrap':initialbootstrap,'angularjs':initialangularjs,
        'java':initialjava,'php':initialphp,'C/C++':initialcjia,'C++':initialcjia,'Object_c':initialObjectc,
        'Android':initialandroid,'Linux/Shell':initiallinux,'数据库':initialsql
};



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

