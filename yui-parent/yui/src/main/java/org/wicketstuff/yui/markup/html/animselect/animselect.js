var boxes${boxId}_${javaScriptId} = ['DefaultImg${boxId}_${javaScriptId}', 'DefaultImgOver${boxId}_${javaScriptId}', 'SelectedImg${boxId}_${javaScriptId}', 'SelectedImgOver${boxId}_${javaScriptId}'];
var count${boxId}_${javaScriptId} = 0;
var message_${javaScriptId} = '${message}';

function fnMouseOver${boxId}_${javaScriptId}(){
	if(count${boxId}_${javaScriptId}%2 == 0){
		fnShow${boxId}_${javaScriptId}('DefaultImgOver${boxId}_${javaScriptId}');
	}
	else{
		fnShow${boxId}_${javaScriptId}('SelectedImg${boxId}_${javaScriptId}');
	}
}

function fnMouseOut${boxId}_${javaScriptId}(){
	if(count${boxId}_${javaScriptId}%2 == 0){
		fnShow${boxId}_${javaScriptId}('DefaultImg${boxId}_${javaScriptId}');
	}
	else{
		fnShow${boxId}_${javaScriptId}('SelectedImgOver${boxId}_${javaScriptId}');
	}
}

function fnClick${boxId}_${javaScriptId}(){
	maxCount=0;
	if(maxSelection_${javaScriptId} == 1 ){ 
		for(m=0; m< noOfBoxes_${javaScriptId}; m++){ 
			if(eval("boxes"+m+"_${javaScriptId}[0]") == this.id){	
				eval("fnShow"+m+"_${javaScriptId}('SelectedImg"+m+"_${javaScriptId}')");
				eval("count"+m+"_${javaScriptId}++");
				document.getElementById("selectedValue_${javaScriptId}").value= selectedValues_${javaScriptId}[m];
			}
			else{
				eval("fnShow"+m+"_${javaScriptId}('DefaultImg"+m+"_${javaScriptId}')");
				eval("count"+m+"_${javaScriptId}=0");
			}
		}
	}
	else{
		for(i=0; i< noOfBoxes_${javaScriptId}; i++){
			if((eval("count"+i+"_${javaScriptId}"))%2 == 1){
				maxCount++;	
			}
		}
		if(maxCount>= maxSelection_${javaScriptId} && count${boxId}_${javaScriptId}%2==0){
			alert(message_${javaScriptId});
		}
		else{
			if(count${boxId}_${javaScriptId}%2 == 0) {
				fnShow${boxId}_${javaScriptId}('SelectedImg${boxId}_${javaScriptId}');
				count${boxId}_${javaScriptId}++;
				fn_addSelection_${javaScriptId}(selectedValues_${javaScriptId}[${boxId}]);
			}
			else {	
				fnShow${boxId}_${javaScriptId}('DefaultImgOver${boxId}_${javaScriptId}');
				count${boxId}_${javaScriptId}++;
				fn_removeSelection_${javaScriptId}(selectedValues_${javaScriptId}[${boxId}]);
			}
		}
	}
}

function fn_addSelection_${javaScriptId}(selectedValue){
	newValue="";
	currentValue= document.getElementById("selectedValue_${javaScriptId}").value;
	if(currentValue == ""){
		newValue=selectedValue;
	}
	else{
		newValue=currentValue+","+selectedValue;
	}
	document.getElementById("selectedValue_${javaScriptId}").value = newValue;
}

function fn_removeSelection_${javaScriptId}(unselectedValue){
	newValue="";
	currentValue= ","+document.getElementById("selectedValue_${javaScriptId}").value;
	newValue= currentValue.replace(","+unselectedValue, "");
	document.getElementById("selectedValue_${javaScriptId}").value = newValue.substring(1, newValue.length);
}

function fnShow${boxId}_${javaScriptId}(elementId){
	for(i=0; i<boxes${boxId}_${javaScriptId}.length; i++){
		if(elementId == boxes${boxId}_${javaScriptId}[i]){
			anim = new YAHOO.util.Anim(boxes${boxId}_${javaScriptId}[i], visible_${javaScriptId} , duration_${javaScriptId} , easing_${javaScriptId});
			anim.animate();	
		}
		else{
			anim = new YAHOO.util.Anim(boxes${boxId}_${javaScriptId}[i], invisible_${javaScriptId}, duration_${javaScriptId} , easing_${javaScriptId});
			anim.animate();
		}
	}	
}

YAHOO.util.Event.addListener(boxes${boxId}_${javaScriptId}, "mouseover", fnMouseOver${boxId}_${javaScriptId});
YAHOO.util.Event.addListener(boxes${boxId}_${javaScriptId}, "mouseout", fnMouseOut${boxId}_${javaScriptId});
YAHOO.util.Event.addListener(boxes${boxId}_${javaScriptId}, "click", fnClick${boxId}_${javaScriptId});