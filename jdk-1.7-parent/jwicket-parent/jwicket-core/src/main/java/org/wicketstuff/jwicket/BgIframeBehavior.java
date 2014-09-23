package org.wicketstuff.jwicket;


/**
 * Sometimes a DOM element should be shown in front of other DOM elements but the
 * z-index CSS property does not work in IE6:
 * e.g. IE6 shows <select><option> elements alwas on top.
 * For those situations you can add a BgIframeBehavior to the DOM element that
 * should be shown in front.
 * 
 * The BgIframeBehavior decides automatically if the content is viewed with IE6.
 * There will be no effect in other browsers. 
 */
public class BgIframeBehavior extends JQueryDurableAjaxBehavior {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryBgiframeJs = new JQueryResourceReference(JQuery.class, "bgiframe-2.1.3pre.js");

	private JsMap options = new JsMap();


	public BgIframeBehavior() {
		super(jQueryBgiframeJs);
		
		setRestoreAfterRedraw(true);
	}



	/**	
	 * Sets the 'top' property for the BgIframeBehavior. Please consult the
	 * bgiframe documentation at http://github.com/brandonaaron/bgiframe
	 * for a detailed description of this property.
	 * @param value the top property
	 * @return this object
	 */
	public BgIframeBehavior setTop(final String value) {
		if (value == null || value.trim().length() == 0)
			options.remove("top");
		else
			options.put("top", value);
		return this;
	}
	public BgIframeBehavior setTop(final int value) {
		if (value < 0)
			options.remove("top");
		else
			options.put("top", value);
		return this;
	}


	/**	
	 * Sets the 'left' property for the BgIframeBehavior. Please consult the
	 * bgiframe documentation at http://github.com/brandonaaron/bgiframe
	 * for a detailed description of this property.
	 * @param value the top property
	 * @return this object
	 */
	public BgIframeBehavior setLeft(final String value) {
		if (value == null || value.trim().length() == 0)
			options.remove("left");
		else
			options.put("left", value);
		return this;
	}
	public BgIframeBehavior setLeft(final int value) {
		if (value < 0)
			options.remove("left");
		else
			options.put("left", value);
		return this;
	}


	/**	
	 * Sets the 'width' property for the BgIframeBehavior. Please consult the
	 * bgiframe documentation at http://github.com/brandonaaron/bgiframe
	 * for a detailed description of this property.
	 * @param value the top property
	 * @return this object
	 */
	public BgIframeBehavior setWidth(final String value) {
		if (value == null || value.trim().length() == 0)
			options.remove("width");
		else
			options.put("width", value);
		return this;
	}
	public BgIframeBehavior setWidth(final int value) {
		if (value < 0)
			options.remove("width");
		else
			options.put("width", value);
		return this;
	}


	/**	
	 * Sets the 'height' property for the BgIframeBehavior. Please consult the
	 * bgiframe documentation at http://github.com/brandonaaron/bgiframe
	 * for a detailed description of this property.
	 * @param value the top property
	 * @return this object
	 */
	public BgIframeBehavior setHeight(final String value) {
		if (value == null || value.trim().length() == 0)
			options.remove("height");
		else
			options.put("height", value);
		return this;
	}
	public BgIframeBehavior setHeight(final int value) {
		if (value < 0)
			options.remove("height");
		else
			options.put("height", value);
		return this;
	}



	@Override
	protected JsBuilder getJsBuilder() {
		JsBuilder builder = new JsBuilder();

		/* Normal processing */
		builder.append("jQuery('#" + getComponent().getMarkupId() + "').bgiframe(");
		builder.append("{");
		builder.append(options.toString(rawOptions));
		builder.append("}");
		builder.append(")");

		return builder;
	}


}
