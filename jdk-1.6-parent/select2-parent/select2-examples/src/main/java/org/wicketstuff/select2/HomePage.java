/*
 * Copyright 2012 Igor Vaynberg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.select2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * Example page.
 * 
 * @author igor
 * 
 */
public class HomePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private static final int PAGE_SIZE = 10;
	@SuppressWarnings("unused")
	private Country country = Country.US;
	@SuppressWarnings("unused")
	private List<Country> countries = new ArrayList<Country>(Arrays.asList(new Country[] {
			Country.US, Country.CA }));

	public HomePage()
	{

		// single-select example

		add(new Label("country", new PropertyModel<Object>(this, "country")));

		Form<?> form = new Form<Void>("single");
		add(form);

		Select2Choice<Country> country = new Select2Choice<Country>("country",
			new PropertyModel<Country>(this, "country"), new CountriesProvider());
		country.getSettings().setMinimumInputLength(1);
		form.add(country);

		// multi-select example

		add(new Label("countries", new PropertyModel<Object>(this, "countries")));

		Form<?> multi = new Form<Void>("multi");
		add(multi);

		Select2MultiChoice<Country> countries = new Select2MultiChoice<Country>("countries",
			new PropertyModel<Collection<Country>>(this, "countries"), new CountriesProvider());
		countries.getSettings().setMinimumInputLength(1);
		countries.add(new DragAndDropBehavior());
		multi.add(countries);

		WebMarkupContainer clientSideSelect2ChoiceWrapper = new WebMarkupContainer("clientSideSelect2ChoiceWrapper",
				new Model<Country>());
		clientSideSelect2ChoiceWrapper.add(new Label("country", clientSideSelect2ChoiceWrapper.getDefaultModel()));
		Form<Void> clientSideSelect2Form = new Form<Void>("single");
		clientSideSelect2ChoiceWrapper.add(clientSideSelect2Form);
		Select2Choice<Country> clientSideSelect2Choice = new Select2Choice<Country>("country",
				(IModel<Country>) clientSideSelect2ChoiceWrapper.getDefaultModel(), Arrays.asList(Country.values()),
				new CountryChoiceRenderer());
		clientSideSelect2Choice.getSettings().setAllowClear(true);
		clientSideSelect2Choice.getSettings().setPlaceholder("Select a country from the list...");
		clientSideSelect2Form.add(clientSideSelect2Choice);
		add(clientSideSelect2ChoiceWrapper);
	}

	/**
	 * Queries {@code pageSize} worth of countries from the {@link Country} enum, starting with
	 * {@code page * pageSize} offset. Countries are matched on their {@code displayName} containing
	 * {@code term}
	 * 
	 * @param term
	 *            search term
	 * @param page
	 *            starting page
	 * @param pageSize
	 *            items per page
	 * @return list of matches
	 */
	private static List<Country> queryMatches(String term, int page, int pageSize)
	{

		List<Country> result = new ArrayList<Country>();

		term = term.toUpperCase();

		final int offset = page * pageSize;

		int matched = 0;
		for (Country country : Country.values())
		{
			if (result.size() == pageSize)
			{
				break;
			}

			if (country.getDisplayName().toUpperCase().contains(term))
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

	/**
	 * {@link Country} based choice provider for Select2 components. Demonstrates integration
	 * between Select2 components and a domain object (in this case an enum).
	 * 
	 * @author igor
	 * 
	 */
	public class CountriesProvider extends TextChoiceProvider<Country>
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected String getDisplayText(Country choice)
		{
			return choice.getDisplayName();
		}

		@Override
		protected Object getId(Country choice)
		{
			return choice.name();
		}

		@Override
		public void query(String term, int page, Response<Country> response)
		{
			response.addAll(queryMatches(term, page, PAGE_SIZE));
			response.setHasMore(response.size() == PAGE_SIZE);
		}

		@Override
		public Collection<Country> toChoices(Collection<String> ids)
		{
			ArrayList<Country> countries = new ArrayList<Country>();
			for (String id : ids)
			{
				countries.add(Country.valueOf(id));
			}
			return countries;
		}

	}

	/**
	 * @author lexx
	 */
	public static class CountryChoiceRenderer implements IChoiceRenderer<Country>
	{
		@Override
		public Object getDisplayValue(Country object)
		{
			return object == null ? "" : object.getDisplayName();
		}

		@Override
		public String getIdValue(Country object, int index)
		{
			return object == null ? "" : Integer.toString(index);
		}
	}
}
