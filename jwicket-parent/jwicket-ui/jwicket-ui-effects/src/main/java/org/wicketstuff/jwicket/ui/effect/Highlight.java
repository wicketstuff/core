package org.wicketstuff.jwicket.ui.effect;


import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;


public class Highlight extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;


	public Highlight() {
		super(new JQueryJavascriptResourceReference(Highlight.class, "jquery.effects.highlight.min.js"));
	}


	
	
	
	
	@Override
	String getEffectName() {
		return "highlight";
	}



	private String mode = null;
	
	/**	Set the blind mode
	 * 
	 *	@param value the mode
	 *	@return this object
	 */
	public Highlight setMode(final EffectMode value) {
		if (value == null)
			mode = null;
		else
			mode = value.getMode();
		return this;
	}



	private String color = null;
	
	/**	Set the highlight color
	 * 
	 *	@param value the color in CSS style e.g. {@code "#223344}
	 *	@return this object
	 */
	public Highlight setColor(final String value) {
		this.color = value;
		return this;
	}



	@Override
	void appendOptions(final StringBuilder jsString) {
		if (mode != null || color != null) {
			boolean firstOption = true;
			jsString.append(",{");
			if (mode != null) {
				jsString.append("mode:'");
				jsString.append(mode);
				jsString.append("'");
				firstOption = false;
			}
			if (color != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("color:'");
				jsString.append(color);
				jsString.append("'");
				firstOption = false;
			}
			jsString.append("}");
		}
	}

}
