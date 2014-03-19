package org.wicketstuff.jwicket.ui.effect;

import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;


public class Puff extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryUiEffectsPussJs
		= JQuery.isDebug()
		? new JQueryResourceReference(Blind.class, "jquery.effects.scale.js")
		: new JQueryResourceReference(Blind.class, "jquery.effects.scale.min.js");

	public Puff() {
		super(jQueryUiEffectsPussJs);
	}



	@Override
	String getEffectName() {
		return "puff";
	}



	private String mode = null;

	/**	Set the fold mode
	 * 
	 *	@param value the mode
	 *	@return this object
	 */
	public Puff setMode(final EffectMode value) {
		if (value == null)
			mode = null;
		else
			mode = value.getMode();
		return this;
	}



	private String percent = null;

	/**	Set the percentage to scale to
	 * 
	 *	@param value is the percentage
	 *	@return this object
	 */
	public Puff setPercen(final int value) {
		if (value <= 0)
			percent = null;
		else
			percent = String.valueOf(value);
		return this;
	}



	@Override
	void appendOptions(final StringBuilder jsString) {
		if (mode != null || percent != null) {
			boolean firstOption = true;
			jsString.append(",{");
			if (mode != null) {
				jsString.append("mode:'");
				jsString.append(mode);
				jsString.append("'");
				firstOption = false;
			}
			if (percent != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("percent:");
				jsString.append(percent);
			}
			jsString.append("}");
		}
	}

}
