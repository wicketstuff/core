package org.wicketstuff.select2;

import java.util.Arrays;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

/**
 * @author lexx
 */
public class Select2ChoiceRendererPage extends WebPage
{

	final Select2Choice<Country> country;

	final Form<Void> form;

	public Select2ChoiceRendererPage()
	{
		super();
		this.country = new Select2Choice<Country>("country", new Model<Country>(), Arrays.asList(Country.values()),
				new CountryChoiceRenderer());
		this.form = new Form<Void>("form");
		this.form.add(this.country);
		add(this.form);
	}

}
