package org.wicketstuff.jwicket.ui;


import org.wicketstuff.jwicket.*;


public abstract class AbstractJqueryUiEmbeddedBehavior extends JQueryDurableAjaxBehavior {

	private static final long serialVersionUID = 1L;


	// Javascript
	public static final JQueryJavaScriptResourceReference jQueryUiCoreJs
		= JQuery.isDebug()
		? new JQueryJavaScriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.core.js")
		: new JQueryJavaScriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.core.min.js");
	public static final JQueryJavaScriptResourceReference jQueryUiWidgetJs
		= JQuery.isDebug()
		? new JQueryJavaScriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.widget.js")
		: new JQueryJavaScriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.widget.min.js");
	public static final JQueryJavaScriptResourceReference jQueryUiMouseJs
		= JQuery.isDebug()
		? new JQueryJavaScriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.mouse.js")
		: new JQueryJavaScriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.mouse.min.js");
	public static final JQueryJavaScriptResourceReference jQueryUiPositionJs
		= JQuery.isDebug()
		? new JQueryJavaScriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.position.js")
		: new JQueryJavaScriptResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.position.min.js");


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
