package org.wicketstuff.jquery.resize;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

import org.wicketstuff.jquery.FunctionString;
import org.wicketstuff.jquery.JQueryBehavior;
/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 *
 */
public class ResizeBehaviour extends JQueryBehavior {
    private ResizeOptions options;
    
    public ResizeBehaviour() {
    	this(new ResizeOptions());
	}
    
    public ResizeBehaviour(ResizeOptions options) {
    	super();
    	this.options = options != null ? options : new ResizeOptions();
    }

    @Override protected CharSequence getOnReadyScript() {
    	StringBuilder onReady = new StringBuilder("$('#" + getComponent().getMarkupId() + "').resizable(");
    	
    	// Add callback method to options
    	options.set("stop", new FunctionString("function() {\n " + getCallbackScript() + "}\n"));
    	
    	onReady.append(options.toString(false));
    	onReady.append(");");
    	
    	return onReady;
    }
    
    @Override
	protected CharSequence getCallbackScript() {
    	String mid = getComponent().getMarkupId();
		return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl() +
				"&height=' + $('#" + mid + "').height() + '" +
				"&width=' + $('#" + mid + "').width()");
	}

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavascriptReference(JQueryBehavior.JQUERY_UI_JS);
    }

    @Override protected void respond(AjaxRequestTarget target) {
        Request req = RequestCycle.get().getRequest();
    	onResizeStop(target, Integer.parseInt(req.getParameter("height")), Integer.parseInt(req.getParameter("width")));
    }
    
    public void onResizeStop(AjaxRequestTarget target, int height, int width) {
    }
}
