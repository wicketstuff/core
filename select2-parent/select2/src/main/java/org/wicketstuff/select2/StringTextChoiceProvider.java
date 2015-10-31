package org.wicketstuff.select2;

import java.util.Collection;

/**
 * A simple {@code TextChoiceProvider} for Strings.
 *
 * @author Tom Götz (tom@decoded.de)
 */
public abstract class StringTextChoiceProvider extends ChoiceProvider<String>
{
	private static final long serialVersionUID = 1L;

	@Override
	public String getDisplayValue(String choice)
	{
		return choice;
	}

	@Override
	public String getIdValue(String choice)
	{
		return choice;
	}

	@Override
	public Collection<String> toChoices(Collection<String> ids)
	{
		return ids;
	}

}
