package org.wicketstuff.select2;

import java.util.Arrays;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.util.CollectionModel;

/**
 * @author lexx
 */
public class Select2MultiChoiceRendererPage extends WebPage
{

	final Select2MultiChoice<Country> countries;

	final Form<Void> form;

	public Select2MultiChoiceRendererPage()
	{
		super();
		this.countries = new Select2MultiChoice<Country>("countries", new CollectionModel<Country>(),
				Arrays.asList(Country.values()), new CountryChoiceRenderer());
		this.form = new Form<Void>("form");
		this.form.add(this.countries);
		add(this.form);
	}
}
