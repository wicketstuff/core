package org.wicketstuff.jwicket.ui.effect;


import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;


public class Shake extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryUiEffectsShakeJs
		= JQuery.isDebug()
		? new JQueryResourceReference(Blind.class, "jquery.effects.shake.js")
		: new JQueryResourceReference(Blind.class, "jquery.effects.shake.min.js");

	public Shake() {
		super(jQueryUiEffectsShakeJs);
	}


	
	@Override
	String getEffectName() {
		return "shake";
	}



	private String direction = null;
	
	/**	Set the direction
	 * 
	 *	@param value the direction
	 *	@return this object
	 */
	public Shake setDirection(final EffectDirection value) {
		if (value == null)
			direction = null;
		else
			direction = value.getDirection();
		return this;
	}


	private String distance = null;
	
	/**	Set the distance
	 * 
	 *	@param value is the distance
	 *	@return this object
	 */
	public Shake setDistance(final int value) {
		if (value <= 0)
			distance = null;
		else
			distance = String.valueOf(value);
		return this;
	}



	private String times = null;
	
	/**	Set the number of times to shake
	 * 
	 *	@param value is the number of times
	 *	@return this object
	 */
	public Shake setTimes(final int value) {
		if (value <= 0)
			times = null;
		else
			times = String.valueOf(value);
		return this;
	}



	@Override
	void appendOptions(final StringBuilder jsString) {
		if (direction != null || times != null || distance != null) {
			boolean firstOption = true;
			jsString.append(",{");
			if (direction != null) {
				jsString.append("direction:'");
				jsString.append(direction);
				jsString.append("'");
				firstOption = false;
			}
			if (times != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("times:");
				jsString.append(times);
				firstOption = false;
			}
			if (distance != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("distance:");
				jsString.append(distance);
			}
			jsString.append("}");
		}
	}

}
