package org.wicketstuff.jwicket.ui.effect;


import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;


public class Pulsate extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryUiEffectsPulsateJs
		= JQuery.isDebug()
		? new JQueryResourceReference(Blind.class, "jquery.effects.pulsate.js")
		: new JQueryResourceReference(Blind.class, "jquery.effects.pulsate.min.js");

	public Pulsate() {
		super(jQueryUiEffectsPulsateJs);
	}


	
	@Override
	String getEffectName() {
		return "pulsate";
	}



	private String mode = null;
	
	/**	Set the blind mode
	 * 
	 *	@param value the mode
	 *	@return this object
	 */
	public Pulsate setMode(final EffectMode value) {
		if (value == null)
			mode = null;
		else
			mode = value.getMode();
		return this;
	}



	private String times = null;
	
	/**	Set the number of times to bounce
	 * 
	 *	@param value is the number of times
	 *	@return this object
	 */
	public Pulsate setTimes(final int value) {
		if (value <= 0)
			times = null;
		else
			times = String.valueOf(value);
		return this;
	}



	@Override
	void appendOptions(final StringBuilder jsString) {
		if (mode != null || times != null) {
			boolean firstOption = true;
			jsString.append(",{");
			if (mode != null) {
				jsString.append("mode:'");
				jsString.append(mode);
				jsString.append("'");
				firstOption = false;
			}
			if (times != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("times:");
				jsString.append(times);
			}
			jsString.append("}");
		}
	}

}
