package com.googlecode.wicket.jquery.ui.samples.pages.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.renderer.TextRenderer;
import com.googlecode.wicket.jquery.ui.samples.TemplatePage;

public class TestPage extends TemplatePage
{
	private static final long serialVersionUID = 1L;
	
	public TestPage()
	{
		this.init();
	}
	
	private void init()
	{
		// Model //
		final IModel<Country> model = new Model<Country>(COUNTRIES.get(0));

		// Form //
		final Form<String> form = new Form<String>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// Auto-complete //
		form.add(new AutoCompleteTextField<Country>("autocomplete", model, new TextRenderer<Country>("name")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Country> getChoices(String input)
			{
				List<Country> choices = new ArrayList<Country>();

				int count = 0;
				for (Country genre: COUNTRIES)
				{
					if (genre.getName().toLowerCase().contains(input.toLowerCase()))
					{
						choices.add(genre);
						
						if (++count == 20) { break; } //limits the number of results
					}
				}

				return choices;
			}

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				//Country genre = this.getModelObject(); //can be used

				info(String.format("Selected country: %s (+%d)", model.getObject().getName(), model.getObject().getPrefix()));
				target.add(feedbackPanel);
			}
		});
	}
	
	// List of Countries //
	static final List<Country> COUNTRIES = Arrays.asList(
			new Country(33, "France"),
			new Country(49, "Allemagne")); 

	// Bean //
	static class Country implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		private final int prefix;
		private final String name;
		
		public Country(final int prefix, final String name)
		{
			this.prefix = prefix;
			this.name = name;
		}
		
		public int getPrefix()
		{
			return this.prefix;
		}
		
		public String getName()
		{
			return this.name;
		}
	}
}
