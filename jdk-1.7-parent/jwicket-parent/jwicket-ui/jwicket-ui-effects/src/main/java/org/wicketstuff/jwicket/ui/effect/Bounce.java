package org.wicketstuff.jwicket.ui.effect;


import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;


public class Bounce extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryUiEffectsBounceJs
		= JQuery.isDebug()
		? new JQueryResourceReference(Blind.class, "jquery.effects.bounce.js")
		: new JQueryResourceReference(Blind.class, "jquery.effects.bounce.min.js");

	public Bounce() {
		super(jQueryUiEffectsBounceJs);
	}



	@Override
	String getEffectName() {
		return "bounce";
	}



	private String mode = null;
	
	/**	Set the blind mode
	 * 
	 *	@param value the mode
	 *	@return this object
	 */
	public Bounce setMode(final BounceMode value) {
		if (value == null)
			mode = null;
		else
			mode = value.getMode();
		return this;
	}



	private String direction = null;
	
	/**	Set the blind direction
	 * 
	 *	@param value the direction
	 *	@return this object
	 */
	public Bounce setDirection(final EffectDirection value) {
		if (value == null)
			direction = null;
		else
			direction = value.getDirection();
		return this;
	}



	private String distance = null;
	
	/**	Set the distance to bounce
	 * 
	 *	@param value is the distance
	 *	@return this object
	 */
	public Bounce setDistance(final int value) {
		if (value <= 0)
			distance = null;
		else
			distance = String.valueOf(value);
		return this;
	}



	private String times = null;
	
	/**	Set the number of times to bounce
	 * 
	 *	@param value is the number of times
	 *	@return this object
	 */
	public Bounce setTimes(final int value) {
		if (value <= 0)
			times = null;
		else
			times = String.valueOf(value);
		return this;
	}



	@Override
	void appendOptions(final StringBuilder jsString) {
		if (mode != null || direction != null || distance != null || times != null) {
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
				firstOption = false;
			}
			if (distance != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("distance:");
				jsString.append(distance);
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



	public enum BounceMode {
		
		DEFAULT(null),
		SHOW("show"),
		HIDE("hide"),
		EFFECT("effect");

		private final String mode;

		private BounceMode(final String mode) {
			this.mode = mode;
		}

		public String getMode() {
			return mode;
		}

		public String toString() {
			return mode==null?"default":mode;
		}
	}

}
