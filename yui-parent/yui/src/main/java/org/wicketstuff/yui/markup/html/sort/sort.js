var dd${id}_${javaScriptId} = new YAHOO.example.DDSwap${isIntersect}(${classId} , ${groupId});
var dragValue_${javaScriptId}= "";
var targetValue_${javaScriptId}= "";
var dragId_${javaScriptId}= "";
var targetId_${javaScriptId}= "";
var dragElement_${javaScriptId}= "";
var targetElement_${javaScriptId}= "";

dd${id}_${javaScriptId}.onDragOver = function (e, id){
	
	dragId = "";
	targetId ="";
	if(mode_${javaScriptId} == "YAHOO.util.DDM.INTERSECT"){
		dragId = this.getEl().id;
		targetId = YAHOO.util.DDM.getBestMatch(id).getEl().id
	}
	else if(mode_${javaScriptId} == "YAHOO.util.DDM.POINT"){
		dragId = this.getEl().id;
		targetId = id;
	}		
	dragId_${javaScriptId} = dragId;
	targetId_${javaScriptId} = targetId;
		
	dragIndex = fn_getItemIndex_${javaScriptId}(dragId);
	targetIndex = fn_getItemIndex_${javaScriptId}(targetId);
		
	dragElement_${javaScriptId} = fn_getItemDesc_${javaScriptId}(dragIndex);
	targetElement_${javaScriptId} = fn_getItemDesc_${javaScriptId}(targetIndex);
	
	dragValue_${javaScriptId} = dragElement_${javaScriptId};
	targetValue_${javaScriptId} = targetElement_${javaScriptId};
}

dd${id}_${javaScriptId}.endDrag = function (e){
		fn_processOrder_${javaScriptId}(dragId_${javaScriptId}, dragElement_${javaScriptId}, targetId_${javaScriptId}, targetElement_${javaScriptId});
}

function fn_init(){
	fn_displayOrderValues_${javaScriptId}();
}

function fn_processOrder_${javaScriptId}(dragId, dragElement, targetId, targetElement){
		dragIndexA = -1;
		targetIndexA = -1;
		for(i=0; i<sortIds_${javaScriptId}.length; i++){
			if(sortIds_${javaScriptId}[i] == dragId){
				dragIndexA = i;
			}
			if(sortIds_${javaScriptId}[i]== targetId){
				targetIndexA = i;
			}
		}
		sortIds_${javaScriptId}[dragIndexA]= targetId;
		sortIds_${javaScriptId}[targetIndexA]= dragId;
		
		dragIndexB = -1;
		targetIndexB = -1;
		for(i=0; i<sortValues_${javaScriptId}.length; i++){
			if(sortValues_${javaScriptId}[i] == dragElement){
				dragIndexB = i;
			}
			if(sortValues_${javaScriptId}[i]== targetElement){
				targetIndexB = i;
			}
		}
		sortValues_${javaScriptId}[dragIndexB]= targetElement;
		sortValues_${javaScriptId}[targetIndexB]= dragElement;
		
		fn_displayOrderValues_${javaScriptId}();
}

function fn_displayOrderValues_${javaScriptId}(){
	values="";
	for(i=0; i<sortValues_${javaScriptId}.length; i++){
		if(values == ""){
			values = sortValues_${javaScriptId}[i];
		}
		else{
			values = values +","+ sortValues_${javaScriptId}[i];
		}
	}
	document.getElementById(valueId_${javaScriptId}).value= values;
}

function fn_displayOrderIds_${javaScriptId}(){
	values="";
	for(i=0; i<sortIds_${javaScriptId}.length; i++){
		if(values == ""){
			values = sortIds_${javaScriptId}[i];
		}
		else{
			values = values +","+ sortIds_${javaScriptId}[i];
		}
	}
	document.getElementById("orderIds").value= values;
}

function fn_getItemIndex_${javaScriptId}(item){
	for(i=0; i<sortIds_${javaScriptId}.length; i++){
		if(sortIds_${javaScriptId}[i] == item){
			return i;
		}
	}
	return -1;
}

function fn_getItemDesc_${javaScriptId}(index){
	return sortValues_${javaScriptId}[index];
}

YAHOO.util.Event.addListener(window, "load", fn_init);
YAHOO.util.Event.addListener("dd${id}_${javaScriptId}", "mouseover", fn_${javaScriptId});