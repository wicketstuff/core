package org.wicketstuff.jwicket.ui;


import org.wicketstuff.jwicket.JQueryAjaxBehavior;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;


public abstract class AbstractJqueryUiBehavior extends JQueryAjaxBehavior {

	private static final long serialVersionUID = 1L;

	public AbstractJqueryUiBehavior(final JQueryJavascriptResourceReference... requiredLibraries) {
		super(new JQueryJavascriptResourceReference(AbstractJqueryUiBehavior.class, "jquery-ui-core-1.7.2.min.js"), requiredLibraries);
	}

}
