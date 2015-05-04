package org.wicketstuff.select2;

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

		this.city = new TextField<>("city", new Model<String>());
		this.city.setRequired(true);

		this.country = new Select2Choice<>("country", new Model<Country>(),
			new CountryChoiceProvider());
		this.country.setRequired(true);

		this.form = new Form<>("form");
		this.form.add(this.country);
		this.form.add(this.city);
		add(this.form);
	}

}
