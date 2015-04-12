package org.wicketstuff.select2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONWriter;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

/**
 * @author lexx
 */
public class Select2ChoicePage extends WebPage
{

	final TextField<String> city;

	final Select2Choice<Country> country;

	final Form<Void> form;

	public Select2ChoicePage()
	{
		super();

		this.city = new TextField<String>("city", new Model<String>());
		this.city.setRequired(true);

		this.country = new Select2Choice<Country>("country", new Model<Country>(),
			new CountryChoiceProvider());
		this.country.setRequired(true);

		this.form = new Form<Void>("form");
		this.form.add(this.country);
		this.form.add(this.city);
		add(this.form);
	}

	private static final class CountryChoiceProvider extends ChoiceProvider<Country>
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
			ArrayList<Country> countries = new ArrayList<Country>(PAGE_SIZE);
			for (String id : ids)
			{
				countries.add(Country.valueOf(id));
			}
			return countries;
		}

		private static List<Country> countryMatches(String term, int page, int pageSize)
		{
			List<Country> result = new ArrayList<Country>(PAGE_SIZE);
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

	}

}
