package org.wicketstuff.select2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONWriter;

/**
 * @author lexx
 */
public final class CountryChoiceProvider implements ChoiceProvider<Country>
{

	private static final int PAGE_SIZE = 5;

	@Override
	public void query(String term, int page, Response<Country> response)
	{
		response.addAll(countryMatches(term, page, PAGE_SIZE));
		response.setHasMore(response.size() == PAGE_SIZE);
	}

	@Override
	public void toJson(Country choice, JSONWriter writer) throws JSONException
	{
		writer.key("id").value(choice.name()).key("text").value(choice.getDisplayName());
	}

	@Override
	public Collection<Country> toChoices(Collection<String> ids)
	{
		ArrayList<Country> countries = new ArrayList<>(PAGE_SIZE);
		for (String id : ids)
		{
			countries.add(Country.valueOf(id));
		}
		return countries;
	}

	private static List<Country> countryMatches(String term, int page, int pageSize)
	{
		List<Country> result = new ArrayList<>(pageSize);
		int offset = page * pageSize;
		int matched = 0;
		for (Country country : Country.values())
		{
			if (result.size() == pageSize)
			{
				break;
			}
			if (country.getDisplayName().toUpperCase().contains(term.toUpperCase()))
			{
				matched++;
				if (matched > offset)
				{
					result.add(country);
				}
			}
		}
		return result;
	}

	@Override
	public void detach()
	{
		// nop
	}

}
