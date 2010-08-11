package org.wicketstuff.jquery.codepress;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

import org.wicketstuff.jquery.JQueryBehavior;
import org.wicketstuff.misc.behaviors.SimpleAttributeAppender;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 *
 */
public class CodepressBehaviour extends JQueryBehavior {
    public static final CompressedResourceReference CODEPRESS_JS = new CompressedResourceReference(CodepressBehaviour.class, "jquery.codepress.js");

    private CodepressOptions options_;
    
    public CodepressBehaviour() {
    	this(new CodepressOptions());
	}
    
    public CodepressBehaviour(CodepressOptions options) {
    	super();
    	options_ = options != null ? options : new CodepressOptions();
    }
    
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavascriptReference(CODEPRESS_JS);
    }

    @Override protected CharSequence getOnReadyScript() {
    	StringBuilder onReady = new StringBuilder("$.codepress.config = ");
    	onReady.append(options_.toString(false));
    	onReady.append("$('" + getComponent().getMarkupId() + "').codepress();"); 
    	
    	return onReady;
    }
    
    @Override protected void onBind() {
    	super.onBind();
    	StringBuilder classes = new StringBuilder("codepress");
    	if(options_.getFileType() != null)
    		classes.append(" " + options_.getFileType());
    	
        classes.append(" autocomplete-" + (options_.isAutoComplete() ? "on" : "off"));

    	if(!options_.isLineNumbers())
   		classes.append(" linenumbers-off");
    	
    	getComponent().add(new SimpleAttributeAppender("class", classes.toString(), " "));
    }
    
}
