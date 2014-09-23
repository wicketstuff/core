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
var wbClipArtList = '';
var wbDocList='';
var wbCurrentDoc='';
var wbCurrentDocComponentList='';
var wbCurrentDocPage='';
var wbElementCollection='';

function addWBElement(m) {
	wbElementCollection.acceptJsonStr(m);
}

function parseWB(m) {
	wbElementCollection.parseJson(m);
}

function wbDeleteElement(m) {
	wbElementCollection.acceptDeletion(m);
}

function wbUndo(m) {
    if (JSON.stringify(m.deleteList) != '\"\"') {
        var deleteList = JSON.stringify(m.deleteList).substring(1, JSON.stringify(m.deleteList).length - 1).split(",");
        for (var i = 0; i < deleteList.length; i++) {
        	wbDeleteElement(deleteList[i])
        }
    }

    var changeList = m.changeList;
    for (var i = 0; i < changeList.length; i++) {
        addWBElement(JSON.stringify(changeList[i]));
    }
}

function wbMessage(msg) {
    var message = jQuery.parseJSON(msg)
    if (message && message.type == "addElement") {
        addWBElement(JSON.stringify(message.json));
    } else if (message && message.type == "parseWB") {
        parseWB(message.json);
    } else if (message && message.type == "undoList") {
    	wbUndo(message);
    }
    else if (message && message.type == "clipArtList") {
    	wbClipArtList = message.json;
    }
    else if (message && message.type == "documentList") {
    	wbDocList = message.json;
    }
    else if (message && message.type == "documentComponentList") {
    	wbCurrentDocComponentList = message.json;
    }
    else if (message && message.type == "addBackground") {
        if( wbCurrentDocPage!=message.json.url) {
            whiteboard.acceptBackground(JSON.stringify(message.json));
            var b=message.json.url;
            if(b != '') {
            	wbCurrentDoc = b.substring(b.lastIndexOf("/") + 1, b.lastIndexOf("."));
            	wbCurrentDocPage = b;
            	wbCurrentDocComponentList = "";
            } else {
            	wbCurrentDoc = '';
            	wbCurrentDocPage = '';
            	wbCurrentDocComponentList = '';
            }
        }
    } else if (message && message.type == "eraseElements") {
        whiteboard.collections.main.clear();
        whiteboard.collections.tracer.clear();
        whiteboard.redrawAll()
    }
}

function initWB(cbUrl, markupId, elems, bg) {
	wbCallbackUrl = cbUrl;
	whiteboard = bay.whiteboard.Create();
	wbElementCollection = whiteboard.getMainCollection();
	whiteboard.getMainCollection().onChange = function(element) {
		changedElement=this.getJson(element);
		Wicket.Ajax.get({u: wbCallbackUrl, ep: {editedElement: changedElement}});
	};
	whiteboard.render(document.getElementById(markupId));
	whiteboard.setBoundaries(0, 0, 0, 0);
	window.onload = function() {
		Wicket.Ajax.get({u: wbCallbackUrl, ep: {clipArt: "clipArt"}});
		Wicket.Ajax.get({u: wbCallbackUrl, ep: {docList: "docList"}});
	};
	whiteboard.onBackground = function(e) {
		Wicket.Ajax.get({u: wbCallbackUrl, ep: {background: whiteboard.backgroundJson()}});
	};
	if (elems) {
		wbElementCollection.rebuild(elems);
	}
	if (bg) {
		whiteboard.backgroundJson(bg);
		var b = bg.url;
		wbCurrentDoc = b.substring(b.lastIndexOf("/") + 1, b.lastIndexOf("."));
		wbCurrentDocPage = b;
		wbCurrentDocComponentList = "";
	}
}
