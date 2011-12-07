var ${javaScriptId}
function init${javaScriptId}(){
	document.getElementById("${domId}").style.display="block";
	${javaScriptId} = new YAHOO.widget.Panel("${domId}", ${settings});
	${javaScriptId}.render();
}