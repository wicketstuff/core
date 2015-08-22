function fnCallback_${javaScriptId}(e) {
	for(i=0; i<boxes_${javaScriptId}.length; i++)
	{
		tempBox= boxes_${javaScriptId}[i];
		tempid= tempBox.charAt(this.id.length-1, this.id.length);
		
		selectedBox= this.id;
		selectedid= selectedBox.charAt(this.id.length-1, this.id.length);
		
		if(boxes_${javaScriptId}[i] == this.id)
		{
			attributes_${javaScriptId}=
			{
				${attributeOn}
			};
			anim = new YAHOO.util.ColorAnim(this.id, attributes_${javaScriptId}, duration_${javaScriptId}, easing_${javaScriptId});
			anim.animate();	
			counts_${javaScriptId}[selectedid]=1;
		}
		else if(counts_${javaScriptId}[tempid]==1)
		{
			attributes_${javaScriptId} =
			{
				${attributeOff}
			};
			anim = new YAHOO.util.ColorAnim(boxes_${javaScriptId}[i], attributes_${javaScriptId}, duration_${javaScriptId},easing_${javaScriptId} );
			anim.animate();	
			counts_${javaScriptId}[tempid]=0;
		}
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