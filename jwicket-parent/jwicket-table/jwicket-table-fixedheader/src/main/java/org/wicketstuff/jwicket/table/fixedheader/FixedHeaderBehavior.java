package org.wicketstuff.jwicket.table.fixedheader;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.JQueryEmbeddedAjaxBehavior;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;


/**
 * You can add an instance of this class to a Wicket {@link Component} to make it
 * draggable. An instance of this class can be added to one and only one
 * {@link Component}. Another {@link Component} that should have exactly the
 * same draggable behavior needs it's own instance.
 */
public class FixedHeaderBehavior extends JQueryEmbeddedAjaxBehavior {

	private static final long serialVersionUID = 1L;
	private static final JQueryJavascriptResourceReference uiDraggable = new JQueryJavascriptResourceReference(FixedHeaderBehavior.class, "jquery.fixedheadertable.1.0.js");

//	private JsMap options = new JsMap();


	public FixedHeaderBehavior() {
		super(uiDraggable, null);
	}





	/**
	 * Disable the dragging
	 *
	 * @param target An AjaxRequestTarget
	 */
	public void disable(final AjaxRequestTarget target) {
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('disable');");
	}


	/**
	 * Enable the dragging
	 *
	 * @param target An AjaxRequestTarget
	 */
	public void enable(final AjaxRequestTarget target) {
		target.appendJavascript("jQuery('#" + getComponent().getMarkupId() + "').draggable('enable');");
	}


	@Override
	/**
	 * For internal use only.
	 */
	protected JsBuilder getJsBuilder() {
		JsBuilder builder = new JsBuilder();


		builder.append("jQuery(document).ready(function(){");

		builder.append("jQuery('#" + getComponent().getMarkupId() + "').fixedHeaderTable(");
//		builder.append("{");
//		builder.append(options.toString(rawOptions));
//		builder.append("}");
		builder.append(");");

		builder.append("});");

		return builder;
	}
}
