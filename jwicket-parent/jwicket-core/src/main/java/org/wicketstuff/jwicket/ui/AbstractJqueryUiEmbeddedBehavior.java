package org.wicketstuff.jwicket.ui;


import org.wicketstuff.jwicket.JQueryCssResourceReference;
import org.wicketstuff.jwicket.JQueryDurableAjaxBehavior;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;
import org.wicketstuff.jwicket.JQueryResourceReference;


public abstract class AbstractJqueryUiEmbeddedBehavior extends JQueryDurableAjaxBehavior {

	private static final long serialVersionUID = 1L;
	
	// Javascript
	private static final JQueryJavascriptResourceReference jQueryUiCore   = new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.core-1.8.min.js");
	public  static final JQueryJavascriptResourceReference jQueryUiWidget = new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.widget-1.8.min.js");
	public  static final JQueryJavascriptResourceReference jQueryUiMouse  = new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.mouse-1.8.min.js");
	public  static final JQueryJavascriptResourceReference jQueryUiI18n   = new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.i18n-1.8.js");

	// CSS
	public  static final JQueryCssResourceReference jQueryUiBaseCss       = new JQueryCssResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "css/jquery.ui.base.css");
	public  static final JQueryCssResourceReference jQueryUiThemeCss      = new JQueryCssResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "css/jquery.ui.theme.css");

	public AbstractJqueryUiEmbeddedBehavior(final JQueryResourceReference... requiredLibraries) {
		super(jQueryUiCore, requiredLibraries);
	}

}
