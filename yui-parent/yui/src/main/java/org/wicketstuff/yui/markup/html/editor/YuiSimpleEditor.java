/*
 *  Copyright 2008 korbinianbachl.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.wicketstuff.yui.markup.html.editor;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.yui.markup.html.contributor.YuiLoaderHeaderContributor;

/**
 *
 * @author korbinianbachl
 */
public class YuiSimpleEditor extends Panel {

    /**
     * Alpha of Yui SimpleEditor - note that it uses the YuiLoader and 
     * that these 2 components (Loader + Editor) are BETA status from YUI!
     *  
     * @param id
     * @param model
     */
    public YuiSimpleEditor(String id, IModel model) {
        super(id);
       
        String jsInit = getInitJs();
        
        add(YuiLoaderHeaderContributor.forModule("simpleeditor", jsInit));
        TextArea ta = new TextArea("editorArea", model);
        ta.setEscapeModelStrings(false);    
        add(ta);
    }
    
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		IRequestTarget target = ((WebRequestCycle)RequestCycle.get()).getRequestTarget();
		if(target instanceof AjaxRequestTarget){
			//if refreshed by ajax render it again
			((AjaxRequestTarget)target).appendJavascript(getInitJs());
		}
	}
	
	private String getInitJs(){
		String jsInit = "" +
	        "var Dom = YAHOO.util.Dom, \n" +
	        "    myEditor = null, \n" +
	        "    timer = null; \n" +
	        "myEditor = new YAHOO.widget.SimpleEditor('yuiSimpleEditor', { \n" +
	     	"   height: '300px', \n" +
	      	"   width: '522px', \n" +
	      	"   dompath: true, \n" +
	      	"   animate: true \n" +
	      	"   }); \n" +
	        "var update = function(ev) { " +
	        "   if(timer) { \n" +
	        "       clearTimeout(timer); \n" +
	        "       } \n" +
	        "   timer = setTimeout(function() { \n " +
	        "       myEditor.saveHTML(); \n" +
	        "       }, 200);  \n" +
	        "   } \n" +
	        "myEditor.on('editorKeyDown', update); \n" +
	        "myEditor.on('afterNodeChange', update); \n" +
	        "myEditor.render(); \n";
		return jsInit;
	}
    
    
}
