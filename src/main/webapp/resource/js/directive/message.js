function flashTip(string){
	var content=document.createElement('span');
	content.setAttribute('class','tipContent');
	content.innerHTML=string;
	var hint = document.createElement('div');
	hint.setAttribute('id','hint');
	hint.setAttribute('class','tipBox');
	hint.appendChild(content);
	document.body.appendChild(hint);		
	setTimeout("remove()",2000);
}

function remove(){
	document.body.removeChild(document.body.lastChild);
}

