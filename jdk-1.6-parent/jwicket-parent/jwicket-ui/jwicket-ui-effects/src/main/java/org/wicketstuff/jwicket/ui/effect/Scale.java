package org.wicketstuff.jwicket.ui.effect;


import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;


public class Scale extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryUiEffectsScaleJs
		= JQuery.isDebug()
		? new JQueryResourceReference(Blind.class, "jquery.effects.scale.js")
		: new JQueryResourceReference(Blind.class, "jquery.effects.scale.min.js");

	public Scale() {
		super(jQueryUiEffectsScaleJs);
	}


	
	@Override
	String getEffectName() {
		return "scale";
	}



	private String direction = null;
	
	/**	Set the direction
	 * 
	 *	@param value the direction
	 *	@return this object
	 */
	public Scale setDirection(final ScaleDirection value) {
		if (value == null)
			direction = null;
		else
			direction = value.getDirection();
		return this;
	}


	private String from = null;
	
	/**	Set the start size
	 * 
	 *	@param height the start height or a number < 0 for default value
	 *	@param width the start width or a number < 0 for default value
	 *	@return this object
	 */
	public Scale setFrom(final int height, final int width) {
		if (height < 0 || width < 0)
			from = null;
		else {
			from = "{height:" + height + ",width:" + width + "}";
		}
		return this;
	}



	private String percent = null;
	
	/**	Set the percentage to scale to
	 * 
	 *	@param value is the percentage
	 *	@return this object
	 */
	public Scale setPercen(final int value) {
		if (value <= 0)
			percent = null;
		else
			percent = String.valueOf(value);
		return this;
	}



	private String scale = null;
	
	/**	Set the element to scale
	 * 
	 *	@param value the element
	 *	@return this object
	 */
	public Scale setElement(final ScaleElement value) {
		if (value == null)
			scale = null;
		else
			scale = value.getElement();
		return this;
	}



	@Override
	void appendOptions(final StringBuilder jsString) {
		if (direction != null || from != null || percent != null || scale != null) {
			boolean firstOption = true;
			jsString.append(",{");
			if (direction != null) {
				jsString.append("direction:'");
				jsString.append(direction);
				jsString.append("'");
				firstOption = false;
			}
			if (from != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("from:");
				jsString.append(from);
				firstOption = false;
			}
			if (percent != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("percent:");
				jsString.append(percent);
				firstOption = false;
			}
			if (scale != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("scale:'");
				jsString.append(scale);
				jsString.append("'");
			}
			jsString.append("}");
		}
	}




	public enum ScaleDirection {
		
		DEFAULT(null),
		BOTH("both"),
		VERTICAL("vertical"),
		HORIZONTAL("horizontal");

		private final String direction;

		private ScaleDirection(final String direction) {
			this.direction = direction;
		}

		public String getDirection() {
			return direction;
		}

		public String toString() {
			return direction==null?"default":direction;
		}
	}


	public enum ScaleElement {
		
		DEFAULT(null),
		BOTH("both"),
		BOX("box"),
		CONTENT("content");

		private final String direction;

		private ScaleElement(final String direction) {
			this.direction = direction;
		}

		public String getElement() {
			return direction;
		}

		public String toString() {
			return direction==null?"default":direction;
		}
	}
}
