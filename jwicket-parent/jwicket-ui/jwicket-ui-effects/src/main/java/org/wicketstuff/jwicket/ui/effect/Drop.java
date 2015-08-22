package org.wicketstuff.jwicket.ui.effect;


import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;


public class Drop extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryUiEffectsDropJs
		= JQuery.isDebug()
		? new JQueryResourceReference(Blind.class, "jquery.effects.drop.js")
		: new JQueryResourceReference(Blind.class, "jquery.effects.drop.min.js");

	public Drop() {
		super(jQueryUiEffectsDropJs);
	}



	@Override
	String getEffectName() {
		return "drop";
	}



	private String mode = null;
	
	/**	Set the clip mode
	 * 
	 *	@param value the mode
	 *	@return this object
	 */
	public Drop setMode(final EffectMode value) {
		if (value == null)
			mode = null;
		else
			mode = value.getMode();
		return this;
	}



	private String direction = null;
	
	/**	Set the clip direction
	 * 
	 *	@param value the direction
	 *	@return this object
	 */
	public Drop setDirection(final EffectDirection value) {
		if (value == null)
			direction = null;
		else
			direction = value.getDirection();
		return this;
	}


	@Override
	void appendOptions(final StringBuilder jsString) {
		if (mode != null || direction != null) {
			boolean firstOption = true;
			jsString.append(",{");
			if (mode != null) {
				jsString.append("mode:'");
				jsString.append(mode);
				jsString.append("'");
				firstOption = false;
			}
			if (direction != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("direction:'");
				jsString.append(direction);
				jsString.append("'");
			}
			jsString.append("}");
		}
	}

}
