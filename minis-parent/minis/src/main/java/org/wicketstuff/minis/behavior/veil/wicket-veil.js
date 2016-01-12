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
 
 /**
 	Implementation of a javacsript veil. A veil is a semi-transparent layer
 	that sits above markup and prevents any events such as clicks, etc
 	there by making the markup look disabled
 	
 	@author Igor Vaynberg (ivaynberg)
 */
if (typeof(Wicket) == "undefined")
	Wicket = { };
	
Wicket.Veil = {

	/**
		toggles a veil over the element with the specified id
		options map must at least contain className key
	*/
	toggle:function(targetId, options) {
		if (Wicket.Veil.hide(targetId)==false) {
		  Wicket.Veil.show(targetId, options);
		}
	},

	/**
		shows a veil over the element with the specified id
		options map must at least contain className key
	*/
	show:function(targetId, options) {
		var target=document.getElementById(targetId);
		var veil=document.createElement("div");
		veil.innerHTML="&nbsp;";
		veil.className=options.className;
		veil.style.position="absolute";
		veil.style.left=Wicket.Veil.left(target);
		veil.style.top=Wicket.Veil.top(target);
		veil.style.width=target.clientWidth;
		veil.style.height=target.clientHeight;
		veil.style.display="block";
		veil.style.cursor="not-allowed";
		veil.style.zIndex="5000";
		veil.id="wicket_veil_"+targetId;
		document.body.appendChild(veil);
	},

	/**
		hides veil currently shown over the element with the specified id
		@return true if veil was hidden, false if there was none
	*/
	hide:function(targetId) {
		var veil=document.getElementById("wicket_veil_"+targetId);
		if (veil!=null) {
			veil.style.display="none";
			document.body.removeChild(veil);
			return true;
		}
		return false;
	},

	/**
		finds left offset of the specified element in px
	*/
	left:function (e) { 
		if (e.offsetParent) {
			var c = 0;
			while (e.offsetParent) {
				c += e.offsetLeft
				e = e.offsetParent;
			}
			return c;
		} else if (e.x) {
			return e.x;
		} else {
			return 0;
		}	
	},

	/**
		finds top offset of the specified element in px
	*/
	top:function(e) {
		if (e.offsetParent) {
			var c = 0;
			while (e.offsetParent) {
				c += e.offsetTop
				e = e.offsetParent;
			}
			return c;
		} else if (e.y) {
			return e.y;
		} else {
			return 0;
		}				
	}
	
}