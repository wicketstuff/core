package org.wicketstuff.jwicket.ui;


import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.wicketstuff.jwicket.JQueryAjaxBehavior;



public abstract class AbstractJqueryUiBehavior extends JQueryAjaxBehavior {

	private static final long serialVersionUID = 1L;

	public AbstractJqueryUiBehavior(final JavascriptResourceReference... requiredLibraries) {
		super(new JavascriptResourceReference(AbstractJqueryUiBehavior.class, "jquery-ui-core-1.7.2.min.js"), requiredLibraries);
	}

}
