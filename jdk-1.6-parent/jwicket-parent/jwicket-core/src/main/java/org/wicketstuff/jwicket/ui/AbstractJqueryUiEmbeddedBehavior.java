package org.wicketstuff.jwicket.ui;


import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryCssResourceReference;
import org.wicketstuff.jwicket.JQueryDurableAjaxBehavior;
import org.wicketstuff.jwicket.JQueryResourceReference;


public abstract class AbstractJqueryUiEmbeddedBehavior extends JQueryDurableAjaxBehavior {

	private static final long serialVersionUID = 1L;


	// Javascript
	public static final JQueryResourceReference jQueryUiCoreJs
		= JQuery.isDebug()
		? new JQueryResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.core.js")
		: new JQueryResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.core.min.js");
	public static final JQueryResourceReference jQueryUiWidgetJs
		= JQuery.isDebug()
		? new JQueryResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.widget.js")
		: new JQueryResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.widget.min.js");
	public static final JQueryResourceReference jQueryUiMouseJs
		= JQuery.isDebug()
		? new JQueryResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.mouse.js")
		: new JQueryResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.mouse.min.js");
	public static final JQueryResourceReference jQueryUiPositionJs
		= JQuery.isDebug()
		? new JQueryResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.position.js")
		: new JQueryResourceReference(AbstractJqueryUiEmbeddedBehavior.class, "jquery.ui.position.min.js");


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
