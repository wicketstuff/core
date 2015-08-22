function fn_initSlot${id}_${javaScriptId}(){
	slots${id} = new YAHOO.util.DDTarget(${id}, ${dragableSlot});
}
YAHOO.util.Event.addListener(window, "load", fn_initSlot${id}_${javaScriptId});