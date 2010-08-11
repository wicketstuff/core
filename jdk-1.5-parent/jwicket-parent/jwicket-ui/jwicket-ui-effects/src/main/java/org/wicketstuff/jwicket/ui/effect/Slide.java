package org.wicketstuff.jwicket.ui.effect;


import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;


public class Slide extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;


	public Slide() {
		super(new JQueryJavascriptResourceReference(Slide.class, "jquery.effects.slide.min.js"));
	}


	
	@Override
	String getEffectName() {
		return "slide";
	}



	private String direction = null;
	
	/**	Set the direction
	 * 
	 *	@param value the direction
	 *	@return this object
	 */
	public Slide setDirection(final EffectDirection value) {
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
	public Slide setDistance(final int value) {
		if (value <= 0)
			distance = null;
		else
			distance = String.valueOf(value);
		return this;
	}



	private String mode = null;
	
	/**	Set the blind mode
	 * 
	 *	@param value the mode
	 *	@return this object
	 */
	public Slide setMode(final EffectMode value) {
		if (value == null)
			mode = null;
		else
			mode = value.getMode();
		return this;
	}



	@Override
	void appendOptions(final StringBuilder jsString) {
		if (direction != null || mode != null || distance != null) {
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
			}
			jsString.append("}");
		}
	}

}
