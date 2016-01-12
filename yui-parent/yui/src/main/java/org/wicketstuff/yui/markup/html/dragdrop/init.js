var empty_${javaScriptId}= "empty";

var targetSlot_${javaScriptId} = [${targetSlot}];
var targetSlotId_${javaScriptId} = [${targetSlotId}];

var dragSlot_${javaScriptId} = [${dragSlot}];
var dragSlotId_${javaScriptId} = [${dragSlotId}];

var DDM_${javaScriptId} = YAHOO.util.DDM;
DDM_${javaScriptId}.mode = "Intersect";

function fn_init_${javaScriptId}(){
	//temporary hard-code the textfields' name
	//these fields are to be named by the user
	document.getElementById("targetOrder").value= fn_getTargetOrderSequence_${javaScriptId}();
	document.getElementById("dragOrder").value= fn_getDragOrderSequence_${javaScriptId}();
}

YAHOO.util.Event.addListener(window, "load", fn_init_${javaScriptId});

function fn_isDragElementToTarget_${javaScriptId}(targetSlot){
	isDragElementToTarget= false;
	for(i=0; i<targetSlotId_${javaScriptId}.length; i++){
		if(targetSlotId_${javaScriptId}[i] == targetSlot_${javaScriptId}){
			isDragElementToTarget= true;
		}
	}
	return isDragElementToTarget;
}

function fn_isDragElementFromTarget_${javaScriptId}(dragElement){
	isDragElementFromTarget= false;
	for(i=0; i<targetSlot_${javaScriptId}.length; i++){
		if(targetSlot_${javaScriptId}[i] == dragElement_${javaScriptId}){
			isDragElementFromTarget= true;
		}
	}
	return isDragElementFromTarget;
}

function fn_isTargetElementInTarget_${javaScriptId}(targetElement){
	isTargetElementInTarget= false;
	for(i=0; i<targetSlot_${javaScriptId}.length; i++){
		if(targetSlot_${javaScriptId}[i] == targetElement_${javaScriptId}){
			isTargetElementIntarget= true;
		}
	}
	return isTargetElementInTarget;
		
}

function fn_checkTargetOrDrag_${javaScriptId}(targetSlot){
	isTarget= false;
	for(i=0; i<targetSlotId_${javaScriptId}.length; i++){
		if(targetSlotId_${javaScriptId}[i] == targetSlot_${javaScriptId}){
			isTarget= true;
		}
	}
	return isTarget;
}

function fn_getDragIndex_Target_${javaScriptId}(dragElement, dragIndex){
	tempIndex= dragIndex;
	for(i=0; i< targetSlot_${javaScriptId}.length; i++){
		if(dragElement == targetSlot_${javaScriptId}[i]){
			tempIndex=i;
		}
	}
	return tempIndex;
}

function fn_getDragIndex_Dragable_${javaScriptId}(dragElement, dragIndex){
	tempIndex= dragIndex;
	for(i=0; i<dragSlot_${javaScriptId}.length; i++){
		if(dragElement == dragSlot_${javaScriptId}[i]){
			tempIndex= i;
		}
	}
	return tempIndex;
}

function fn_getTargetIndex_Target_${javaScriptId}(targetSlot, targetIndex){
	tempIndex= targetIndex;
	for(i=0; i< targetSlotId_${javaScriptId}.length; i++){
		if(targetSlot == targetSlotId_${javaScriptId}[i]){
			tempIndex=i;
		}
	}
	return tempIndex;
}

function fn_getTargetIndex_Dragable_${javaScriptId}(targetSlot, targetIndex){
	tempIndex= targetIndex;
	for(i=0; i<dragSlotId_${javaScriptId}.length; i++){
		if(targetSlot == dragSlotId_${javaScriptId}[i]){
			tempIndex=i;
		}
	}
	return tempIndex;
}


function fn_getTargetElement_Target_${javaScriptId}(targetSlot, targetElement){
	tempTargetElement= targetElement;
	
	for(i=0; i<targetSlotId_${javaScriptId}.length; i++){
		if(targetSlotId_${javaScriptId}[i] == targetSlot){
			//temporary hard-code the textfields' name
			//this field is to be named by the user
			var ordering = document.getElementById("targetOrder").value;
			array = ordering.split(",");
			tempTargetElement = array[i];
		}
	}
	return tempTargetElement;
}

function fn_getTargetElement_Dragable_${javaScriptId}(targetSlot, targetElement){
	tempTargetElement= targetElement;
	
	for(i=0; i<dragSlotId_${javaScriptId}.length; i++){
		if(dragSlotId_${javaScriptId}[i] == targetSlot){
			//temporary hard-code the textfields' name
			//this field is to be named by the user
			var ordering = document.getElementById("dragOrder").value;
			array = ordering.split(",");
			tempTargetElement = array[i];
		}
	}
	return tempTargetElement;
}

function fn_getTargetOrderSequence_${javaScriptId}(){
	order = "";
	for(i=0; i<targetSlot_${javaScriptId}.length; i++){
		if(i == 0){
			order = targetSlot_${javaScriptId}[i];
		}
		else{
			order = order +","+targetSlot_${javaScriptId}[i];
		}
	}
	return order;
}

function fn_getDragOrderSequence_${javaScriptId}(){
	order = "";
	for(i=0; i<dragSlot_${javaScriptId}.length; i++){
		if(i == 0){
			order = dragSlot_${javaScriptId}[i];
		}
		else{
			order = order +","+dragSlot_${javaScriptId}[i];
		}
	}
	return order;
}

function fn_processOrder_${javaScriptId}(dragElement, targetSlot){
	
	dragIndex= -1;
	dragIndex= fn_getDragIndex_Target_${javaScriptId}(dragElement, dragIndex);
	dragIndex= fn_getDragIndex_Dragable_${javaScriptId}(dragElement, dragIndex);		

	targetElement= empty;
	targetElement= fn_getTargetElement_Target_${javaScriptId}(targetSlot, targetElement);
	targetElement= fn_getTargetElement_Dragable_${javaScriptId}(targetSlot, targetElement);	

	targetIndex= -1;
	targetIndex= fn_getTargetIndex_Target_${javaScriptId}(targetSlot, targetIndex);
	targetIndex= fn_getTargetIndex_Dragable_${javaScriptId}(targetSlot, targetIndex);

	isDragElementToTarget= fn_isDragElementToTarget_${javaScriptId}(targetSlot);
	isDragElementFromTarget= fn_isDragElementFromTarget_${javaScriptId}(dragElement);

	fn_updateSlot_${javaScriptId}(targetIndex, dragElement, dragIndex, targetElement, isDragElementToTarget, isDragElementFromTarget);

	//temporary hard-code the textfields' name
	//these fields are to be named by the user
	document.getElementById("targetOrder").value= fn_getTargetOrderSequence_${javaScriptId}();
	document.getElementById("dragOrder").value= fn_getDragOrderSequence_${javaScriptId}();
}

function fn_updateSlot_${javaScriptId}(targetIndex, dragElement, dragIndex, targetElement, isDragElementToTarget, isDragElementFromTarget){

	if(isDragElementToTarget == true){	
		targetSlot_${javaScriptId}[targetIndex]= dragElement;							
	}	
	else if(isDragElementToTarget== false){	
		dragSlot_${javaScriptId}[targetIndex]= dragElement;
	}

	if(isDragElementFromTarget == true){
		targetSlot_${javaScriptId}[dragIndex]= targetElement;							
	}
	else if(isDragElementFromTarget== false){	
		dragSlot_${javaScriptId}[dragIndex]= targetElement;	
	}
}