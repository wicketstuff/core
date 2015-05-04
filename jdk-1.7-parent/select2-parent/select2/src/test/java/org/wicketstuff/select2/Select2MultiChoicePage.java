package org.wicketstuff.select2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;

/**
 * @author lexx
 */
public class Select2MultiChoicePage extends WebPage
{

	final TextField<String> city;

	final Select2MultiChoice<Country> country;

	final Form<Void> form;

	public Select2MultiChoicePage()
	{
		super();

		this.city = new TextField<>("city", new Model<String>());
		this.city.setRequired(true);

		this.country = new Select2MultiChoice<>("country", new CollectionModel<Country>(),
			new CountryChoiceProvider());
		this.country.setRequired(true);

		this.form = new Form<>("form");
		this.form.add(this.country);
		this.form.add(this.city);
		add(this.form);
	}

}
