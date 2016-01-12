function fn_initPlayer${id}_${javaScriptId}(){
	player${id} = new YAHOO.example.DDPlayer(${id}, ${dragableSlot});
	player${id}.addToGroup(${targetSlot});

	player${id}.onDragOver = function (e, id){
		document.getElementById("drag").value = this.getEl().id;
		document.getElementById("target").value = YAHOO.util.DDM.getBestMatch(id).getEl().id;	
	}
	player${id}.startDrag = function(x,y){};
	player${id}.endDrag = function (e){
		isDragging= false;
		//temporary hard-code
		//this need to be created as hidden field for the system to trace the drag and target
		var dragElement = document.getElementById("drag").value;
		var targetSlot = document.getElementById("target").value;
		fn_processOrder_${javaScriptId}(dragElement, targetSlot);	
	}
}
YAHOO.util.Event.addListener(window, "load", fn_initPlayer${id}_${javaScriptId});