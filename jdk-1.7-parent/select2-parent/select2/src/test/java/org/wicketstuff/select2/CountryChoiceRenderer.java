package org.wicketstuff.select2;

import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

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

	@Override
	public Country getObject(String id, IModel<? extends List<? extends Country>> choices)
	{
		List<? extends Country> countries = choices.getObject();
		for (int index = 0; index < countries.size(); index++)
		{
			Country country = countries.get(index);
			if (getIdValue(country, index).equals(id))
			{
				return country;
			}
		}
		return null;
	}

}
