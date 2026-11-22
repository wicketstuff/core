package org.wicketstuff.hugerte.ajax;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;

/**
 * Modifier to modify the onclick method so that the HugeRTE data will be updated to the model
 * <p>
 * For more details see:
 * {@code hugerte.triggerSave()} <BR>
 *
 * @author Sander van Faassen
 */
public class HugeRteAjaxSubmitModifier extends AttributeModifier
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Trigger that has to be called
	 */
	public static final String SAVE_TRIGGER = "hugerte.triggerSave();";

	/**
	 * Constructor
	 */
	public HugeRteAjaxSubmitModifier()
	{
		super("onclick", Model.of(SAVE_TRIGGER));
	}

	/**
	 * @see org.apache.wicket.AttributeModifier#newValue(java.lang.String, java.lang.String)
	 */
	@Override
	protected String newValue(final String currentValue, final String replacementValue)
	{
		// Call the trigger, before submitting the form
		String result = replacementValue;

		if (currentValue != null)
		{
			result = result + currentValue;
		}
		return result;
	}
}
