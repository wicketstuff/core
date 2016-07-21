/*
 * YuiEditor.java
 *
 * Created on 7. September 2007, 11:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wicketstuff.yui.markup.html.editor;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;
import org.wicketstuff.yui.markup.html.contributor.YuiLoaderHeaderContributor;

/**
 *
 * @author korbinianbachl
 */
public class YuiEditor extends Panel {
    
    private static final long serialVersionUID = 1L;

    /** Ref to CSS file 
     * 
     * due to yuiLoader probably not needed
     */
    //private static final ResourceReference CSS = new ResourceReference(YuiHeaderContributor.class, "inc/2.4.1/assets/skins/sam/skin.css");
    
    /** Creates a new instance of YuiEditor 
     *
     *  Attention: currently in Alpha state!
     *  @param String id
     *  @param IModel model
     */
    public YuiEditor(String id, IModel model) {
        super(id);
       
        String jsInit = getInitJs();
        
        add(YuiLoaderHeaderContributor.forModule("editor", jsInit));
        //add(YuiHeaderContributor.forModule("editor", new String[]{"utilities","container","menu","button"}));
        //add(HeaderContributor.forCss(CSS));
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
	        "myEditor = new YAHOO.widget.Editor('yuiEditor', { \n" +
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
    
    
//    /**
//     * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
//     */
//    public void renderHead(IHeaderResponse response)
//    {
//            response.renderOnLoadJavascript("\n var myEditor = new YAHOO.widget.Editor('yuiEditor', { \n" +
//             	   " height: '300px', \n" +
//              	   " width: '522px', \n" +
//              	   " dompath: true, //Turns on the bar at the bottom \n" +
//              	   " animate: true //Animates the opening, closing and moving of Editor windows \n" +
//              	 " }); \n" +
//             " myEditor.render(); \n");
//            
//            
//             
//            
//    }
    
}






