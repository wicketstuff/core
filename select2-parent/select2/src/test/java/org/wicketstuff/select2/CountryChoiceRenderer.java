package org.wicketstuff.select2;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * @author lexx
 */
public final class CountryChoiceRenderer implements IChoiceRenderer<Country>
{

	@Override
	public Object getDisplayValue(Country object)
	{
		return object == null ? "" : object.getDisplayName();
	}

	@Override
	public String getIdValue(Country object, int index)
	{
		return object == null ? "" : object.name();
	}

}
