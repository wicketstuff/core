package org.wicketstuff.jwicket.ui;


import org.wicketstuff.jwicket.JQueryDurableAjaxBehavior;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;


public abstract class AbstractJqueryUiEmbeddedBehavior extends JQueryDurableAjaxBehavior {

	private static final long serialVersionUID = 1L;
	
	private static final JQueryJavascriptResourceReference jQueryUiCore   = new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.core-1.8.min.js");
	public  static final JQueryJavascriptResourceReference jQueryUiWidget = new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.widget-1.8.min.js");
	public  static final JQueryJavascriptResourceReference jQueryUiMouse  = new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.mouse-1.8.min.js");

	public AbstractJqueryUiEmbeddedBehavior(final JQueryJavascriptResourceReference... requiredLibraries) {
		super(jQueryUiCore, requiredLibraries);
	}

}
