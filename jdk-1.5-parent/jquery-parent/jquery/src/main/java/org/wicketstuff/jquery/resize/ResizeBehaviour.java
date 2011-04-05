package org.wicketstuff.jquery.resize;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
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
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        response.renderJavaScriptReference(JQueryBehavior.JQUERY_UI_JS);
    }

    @Override protected void respond(AjaxRequestTarget target) {
        Request req = RequestCycle.get().getRequest();
    	onResizeStop(target, 
    			req.getQueryParameters().getParameterValue("height").toInt(), 
    			req.getQueryParameters().getParameterValue("width").toInt());
    }
    
    public void onResizeStop(AjaxRequestTarget target, int height, int width) {
    }
}
