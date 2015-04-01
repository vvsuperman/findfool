function flashTip(string){
	var content=document.createElement('span');
	content.setAttribute('class','tipContent');
	content.innerHTML=string;
	var hint = document.createElement('div');
	hint.setAttribute('id','hint');
	hint.setAttribute('class','tipBox');
	hint.appendChild(content);
	document.body.appendChild(hint);		
	setTimeout("removeTip()",2000);
}

function removeTip(){
	document.body.removeChild(document.body.lastChild);
}

function loadingTip(){
	var container=document.createElement('div');
	container.setAttribute('id','container');
	container.setAttribute('class','loadingTip-container');
	var content=document.createElement('div');
	content.setAttribute('class','loadingTip-content');
	var img=document.createElement('img');
	img.setAttribute('src','./resource/static/loadingTip.gif');
	var p=document.createElement('p');
	p.innerHTML="正在处理...";
	content.appendChild(img);
	content.appendChild(p);
	container.appendChild(content);
	document.body.appendChild(container);
}

