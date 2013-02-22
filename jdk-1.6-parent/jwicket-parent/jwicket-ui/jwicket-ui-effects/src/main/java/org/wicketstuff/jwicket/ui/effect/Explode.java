package org.wicketstuff.jwicket.ui.effect;


import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;


public class Explode extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryUiEffectsExplodeJs
		= JQuery.isDebug()
		? new JQueryResourceReference(Blind.class, "jquery.effects.explode.js")
		: new JQueryResourceReference(Blind.class, "jquery.effects.explode.min.js");

	public Explode() {
		super(jQueryUiEffectsExplodeJs);
	}


	@Override
	String getEffectName() {
		return "explode";
	}



	private String pieces = null;
	
	/**	Set the number of pieces for explosion
	 * 
	 *	@param value is the number of pieces. The effective number of pieces fits the nearest square number.
	 *	@return this object
	 */
	public Explode setPieces(final int value) {
		if (value <= 0)
			pieces = null;
		else
			pieces = String.valueOf(value);
		return this;
	}


	@Override
	void appendOptions(final StringBuilder jsString) {
		if (pieces != null) {
			jsString.append(",{pieces:");
			jsString.append(pieces);
			jsString.append("}");
		}
	}
}
