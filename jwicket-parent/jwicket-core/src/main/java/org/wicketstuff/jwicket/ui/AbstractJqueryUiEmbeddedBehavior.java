package org.wicketstuff.jwicket.ui;


import org.wicketstuff.jwicket.JQueryEmbeddedAjaxBehavior;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;


public abstract class AbstractJqueryUiEmbeddedBehavior extends JQueryEmbeddedAjaxBehavior {

	private static final long serialVersionUID = 1L;
	
	private static final JQueryJavascriptResourceReference jqueryCore = new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery-ui-core-1.7.2.min.js");

	public AbstractJqueryUiEmbeddedBehavior(final JQueryJavascriptResourceReference... requiredLibraries) {
		super(jqueryCore, requiredLibraries);
	}

}
