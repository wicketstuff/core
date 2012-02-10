package org.wicketstuff.jwicket.ui;


import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryCssResourceReference;
import org.wicketstuff.jwicket.JQueryDurableAjaxBehavior;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;
import org.wicketstuff.jwicket.JQueryResourceReference;


public abstract class AbstractJqueryUiEmbeddedBehavior extends JQueryDurableAjaxBehavior {

	private static final long serialVersionUID = 1L;


	// Javascript
	public static final JQueryJavascriptResourceReference jQueryUiCoreJs
		= JQuery.isDebug()
		? new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.core.js")
		: new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.core.min.js");
	public static final JQueryJavascriptResourceReference jQueryUiWidgetJs
		= JQuery.isDebug()
		? new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.widget.js")
		: new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.widget.min.js");
	public static final JQueryJavascriptResourceReference jQueryUiMouseJs
		= JQuery.isDebug()
		? new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.mouse.js")
		: new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.mouse.min.js");
	public static final JQueryJavascriptResourceReference jQueryUiPositionJs
		= JQuery.isDebug()
		? new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.position.js")
		: new JQueryJavascriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.position.min.js");


	// CSS
	public  static final JQueryCssResourceReference jQueryUiCss              = new JQueryCssResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "css/jquery-ui.css");
	public  static final JQueryCssResourceReference jQueryUiBaseCss          = new JQueryCssResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "css/jquery.ui.base.css");
	public  static final JQueryCssResourceReference jQueryUiThemeCss         = new JQueryCssResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "css/jquery.ui.theme.css");
	public  static final JQueryCssResourceReference jQueryUiAccordionCss     = new JQueryCssResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "css/jquery.ui.accordion.css");
	public  static final JQueryCssResourceReference jQueryUiCustomCss        = new JQueryCssResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "css/jquery-ui.custom.css");


	public AbstractJqueryUiEmbeddedBehavior(final JQueryResourceReference... requiredLibraries) {
		super(jQueryUiCoreJs, requiredLibraries);
	}

}
