package org.wicketstuff.jwicket.ui.effect;


import org.apache.wicket.Component;
import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;


public class Transfer extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryUiEffectsTransferJs
		= JQuery.isDebug()
		? new JQueryResourceReference(Blind.class, "jquery.effects.transfer.js")
		: new JQueryResourceReference(Blind.class, "jquery.effects.transfer.min.js");

	public Transfer() {
		super(jQueryUiEffectsTransferJs);
	}


	
	@Override
	String getEffectName() {
		return "transfer";
	}



	private String className = null;
	
	/**	Set the CSS class name
	 * 
	 *	@param value the CSS class name
	 *	@return this object
	 */
	public Transfer setClassName(final String value) {
		this.className = value;
		return this;
	}



	private String to = null;
	
	/**	Set the jQuery selector of the component to transform to
	 * 
	 *	@param value  the jQuery selector
	 *	@return this object
	 */
	public Transfer setTo(final String value) {
		this.to = "jQuery('#" + value + "')";
		return this;
	}

	public Transfer setTo(final Component component) throws Exception{
		if (!component.getOutputMarkupId())
			throw new IllegalArgumentException("You must not transform to a component that has not set output markup id: " + component);
		this.to = "jQuery('#" + component.getMarkupId() + "')";
		return this;
	}



	@Override
	void appendOptions(final StringBuilder jsString) {
		if (className != null || to != null) {
			boolean firstOption = true;
			jsString.append(",{");
			if (className != null) {
				jsString.append("className:'");
				jsString.append(className);
				jsString.append("'");
				firstOption = false;
			}
			if (to != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("to:");
				jsString.append(to);
			}
			jsString.append("}");
		}
	}

}
