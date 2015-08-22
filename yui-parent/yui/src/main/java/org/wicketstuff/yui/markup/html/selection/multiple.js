function fnCallback_${javaScriptId}(e) { 
	tempid= this.id.charAt(this.id.length-1, this.id.length);
	maxCount=0;
	
	for(i=0; i<counts_${javaScriptId}.length; i++)
	{
		if(counts_${javaScriptId}[i]== 1){
			maxCount++;
		}	
	}
	
	if(maxCount >= maxSelection_${javaScriptId} && counts_${javaScriptId}[tempid]== 0){
		alert(${message});
	}
	else{
		if(counts_${javaScriptId}[tempid] == 1)
		{		
			attributes_${javaScriptId} = 
			{
				${attributeOff}
			};
			counts_${javaScriptId}[tempid]=0;
		}
		else if(counts_${javaScriptId}[tempid] == 0)
		{	
			attributes_${javaScriptId} =
			{
				${attributeOn}
			};
			counts_${javaScriptId}[tempid]=1;
		}
	
		anim = new YAHOO.util.ColorAnim(this.id, attributes_${javaScriptId}, duration_${javaScriptId}, easing_${javaScriptId});
		anim.animate();	
	}
}

YAHOO.util.Event.addListener(boxes_${javaScriptId}, event_${javaScriptId}, fnCallback_${javaScriptId});

function onWindowLoad_${javaScriptId}(p_oEvent) 
{
	for(i=0; i<boxes_${javaScriptId}.length; i++){
		attributes_${javaScriptId} =
		{
			${attributeOff}
		};
		anim = new YAHOO.util.ColorAnim(boxes_${javaScriptId}[i], attributes_${javaScriptId}, 0.1, easing_${javaScriptId});
		anim.animate();	
	}    
}

YAHOO.util.Event.addListener(window, "load", onWindowLoad_${javaScriptId});