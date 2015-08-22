var ${javaScriptId};
function init${javaScriptId}(startValue) 
{
	${javaScriptId} = YAHOO.widget.Slider.getHorizSlider("${backGroundElementId}", "${imageElementId}", ${leftUp}, ${rightDown}, ${tick});
   	${javaScriptId}.onChange = function(offsetFromStart) 
   	{
		document.getElementById("${formElementId}").value = Math.round(offsetFromStart / ${divisor});
	}
	
	if (startValue == null) 
		document.getElementById("${formElementId}").value = "";
	else
		${javaScriptId}.setValue(Math.round(startValue * ${divisor}));
}