$(window).load(function() {
	window.setInterval(function(){
		 changeHeight();
	 },1000);
	 changeHeight();
	 window.onresize=function(){  
	     changeHeight();  
	}  
	function changeHeight(){
		var mainBlock2=document.getElementsByClassName("main-block2");
	 	var mainBlock3=document.getElementsByClassName("main-block3-img");
	 	for(var i=0;i<mainBlock2.length;i++){
	 		mainBlock2[i].style.height="auto";
//	 		mainBlock2[i].style.height=mainBlock3[i].offsetHeight+ "px";
	 		if(mainBlock2[i].offsetHeight>mainBlock3[i].offsetHeight){
	 			mainBlock3[i].style.height=mainBlock2[i].offsetHeight+ "px";
	 		}else{
	 			mainBlock2[i].style.height=mainBlock3[i].offsetHeight+ "px";
	 		}
	 	}
	}
});