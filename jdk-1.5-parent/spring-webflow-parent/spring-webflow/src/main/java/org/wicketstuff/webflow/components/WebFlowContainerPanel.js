/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributed by United Services Automotive Association (USAA)
 */

/**
 * @fileOverview UsaaFlowContainerPanel.js
 * @author C. Checketts
 * @version 1.0.0
**/

/**
 * @description History manager for Spring Web Flow Wicket panels
 * @requires history
**/
YUI.add('WebFlowContainerPanel_js', function(Y) {
	Y.on("history:change", function (e) 
	{
	    var changed = e.changed;
	    	
	    if(changed.flowExecutionKey){
			goBackTo(changed.flowExecutionKey.newVal);
	    }
	}); 

	
	//Having to wait for DOM ready so that IE7 can find its iFrame, see http://yuilibrary.com/forum/viewtopic.php?f=18&t=5476
	Y.on('domready', function() {
	
		var history = new Y.History();
		var currentState = history.get("flowExecutionKey");
		currentState = currentState || "initialState";
		
		//We add the value instead of calling updateMyModule directly because it passes an event target instead of a string
		history.replaceValue("flowExecutionKey",currentState);
	});
	
}, '1.0' ,{requires:['history']});

