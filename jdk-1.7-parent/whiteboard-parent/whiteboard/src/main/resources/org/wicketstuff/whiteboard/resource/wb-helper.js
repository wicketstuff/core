/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

function initWB(cbUrl, markupId, elems, bg) {
	callbackUrl = cbUrl;
	whiteboard = bay.whiteboard.Create();
	elementCollection = whiteboard.getMainCollection();
	whiteboard.getMainCollection().onChange = function(element) {
		changedElement=this.getJson(element);
		Wicket.Ajax.get({u: callbackUrl, ep: {editedElement: changedElement}});
	};
	whiteboard.render(document.getElementById(markupId));
	whiteboard.setBoundaries(0, 0, 0, 0);
	window.onload = function() {
		Wicket.Ajax.get({u: callbackUrl, ep: {clipArt: "clipArt"}});
		Wicket.Ajax.get({u: callbackUrl, ep: {docList: "docList"}});
	};
	whiteboard.onBackground = function(e) {
		Wicket.Ajax.get({u: callbackUrl, ep: {background: whiteboard.backgroundJson()}});
	};
	if (elems) {
		elementCollection.rebuild(elems);
	}
	if (bg) {
		whiteboard.backgroundJson(bg);
		var b = bg.url;
		currentDoc = b.substring(b.lastIndexOf("/") + 1, b.lastIndexOf("."));
		currentDocPage = b;
		currentDocComponentList = "";
	}
}