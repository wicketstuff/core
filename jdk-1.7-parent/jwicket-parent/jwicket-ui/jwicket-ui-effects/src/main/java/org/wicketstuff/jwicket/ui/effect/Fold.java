package org.wicketstuff.jwicket.ui.effect;


import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryResourceReference;


public class Fold extends AbstractJqueryUiEffect {

	private static final long serialVersionUID = 1L;

	public static final JQueryResourceReference jQueryUiEffectsFoldJs
		= JQuery.isDebug()
		? new JQueryResourceReference(Blind.class, "jquery.effects.fold.js")
		: new JQueryResourceReference(Blind.class, "jquery.effects.fold.min.js");

	public Fold() {
		super(jQueryUiEffectsFoldJs);
	}



	@Override
	String getEffectName() {
		return "fold";
	}



	private String mode = null;
	
	/**	Set the fold mode
	 * 
	 *	@param value the mode
	 *	@return this object
	 */
	public Fold setMode(final EffectMode value) {
		if (value == null)
			mode = null;
		else
			mode = value.getMode();
		return this;
	}



	private String horizFirst = null;

	/**	Whether to fold horizontally first or not.
	 * 
	 *	@param value the behavior
	 *	@return this object
	 */
	public Fold setHorizFirst(final boolean value) {
		horizFirst = String.valueOf(value);
		return this;
	}

	/**	Whether to fold horizontally first or not.
	 * 
	 * @param value the behavior or {@code null} for default behavior
	 * @return
	 */
	public Fold setHorizFirst(final Boolean value) {
		if (value == null)
			horizFirst = null;
		else
			horizFirst = String.valueOf(value);
		return this;
	}



	private String size = null;
	
	/**	Set the size to be folded to.
	 * 
	 *	@param value is the size to be folded to.
	 *	@return this object
	 */
	public Fold setSize(final int value) {
		if (value <= 0)
			size = null;
		else
			size = String.valueOf(value);
		return this;
	}


	@Override
	void appendOptions(final StringBuilder jsString) {
		if (mode != null || horizFirst != null || size != null) {
			boolean firstOption = true;
			jsString.append(",{");
			if (mode != null) {
				jsString.append("mode:'");
				jsString.append(mode);
				jsString.append("'");
				firstOption = false;
			}
			if (horizFirst != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("horizFirst:");
				jsString.append(horizFirst);
				firstOption = false;
			}
			if (size != null) {
				if (!firstOption)
					jsString.append(",");
				jsString.append("size:");
				jsString.append(size);
			}
			jsString.append("}");
		}
	}

}
