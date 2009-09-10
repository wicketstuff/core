/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
function fixPanel(outerPanelId, innerPanelId, expandId) {
	var outerPanel = document.getElementById(outerPanelId);
	var innerPanel = document.getElementById(innerPanelId);
	var expand = document.getElementById(expandId);
	var originalHeight = document.body.getAttribute(outerPanelId);
	if (originalHeight == null) {
		if (outerPanel.parentNode.style.height != "") {
			originalHeight = outerPanel.parentNode.style.height;
		} else {
			var ot = document.createElement(outerPanel.tagName);
			ot.innerHTML = '...';
			document.body.appendChild(ot);
			if(ot.clientHeight > 0 ){
				originalHeight = ot.clientHeight + 'px';
			}else{
				originalHeight = ot.offsetHeight + 'px';
			}
			ot.style.display = 'none';
		}
		document.body.setAttribute(outerPanelId, originalHeight);
	}
	outerPanel.style.height = originalHeight;
	if (outerPanel.parentNode.style.width != "") {
		outerPanel.style.width = outerPanel.parentNode.style.width;
	}
	function updateIndication() {
		expand.style.display = outerPanel.clientHeight < innerPanel.offsetHeight ? 'block'
				: 'none';
	}
	updateIndication();
	outerPanel.onmouseover = function() {
		outerPanel.style.overflow = 'visible';
		innerPanel.style.zIndex = 999;
		innerPanel.style.backgroundColor= "white";
		updateIndication();
	};
	outerPanel.onmouseout = function() {
		outerPanel.style.overflow = 'hidden';
		innerPanel.style.zIndex = 0;
		innerPanel.style.backgroundColor= "";
		updateIndication();
	};
}