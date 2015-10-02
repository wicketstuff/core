package org.wicketstuff.selectize;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;

public class SelectizeJavaScriptResourceReference extends JavaScriptResourceReference {

    private static final long serialVersionUID = 1L;

    /**
     * Singleton instance of this reference
     */
    private static final SelectizeJavaScriptResourceReference INSTANCE = new SelectizeJavaScriptResourceReference();

    /**
     * @return the single instance of the resource reference
     */
    public static SelectizeJavaScriptResourceReference instance() {
	return INSTANCE;
    }

    /**
     * Private constructor.
     */
    private SelectizeJavaScriptResourceReference() {
	super(SelectizeJavaScriptResourceReference.class,"res/selectize/js/standalone/selectize.js");
    }
    
    @Override
    public List<HeaderItem> getDependencies() {
	List<HeaderItem> dependencies = new ArrayList<HeaderItem>();
	dependencies.add(JavaScriptHeaderItem.forReference(JQueryResourceReference.get()));
        return dependencies;
    }
}
