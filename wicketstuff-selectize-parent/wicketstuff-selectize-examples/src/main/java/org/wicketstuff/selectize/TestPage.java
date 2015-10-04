package org.wicketstuff.selectize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.selectize.SelectizeCssResourceReference.Theme;

public class TestPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public TestPage()
	{
		Form<Void> form = new Form<Void>("form");

		// Select with text input
		Selectize selectize1 = new Selectize("selectize1", Model.of("test"));
		selectize1.setTheme(Theme.BOOTSTRAP3);
		form.add(selectize1);

		List<SelectizeOption> options = new ArrayList<SelectizeOption>();
		options.add(new SelectizeOption("1", "Brian Reavis", "1"));
		options.add(new SelectizeOption("2", "Nikola Tesla", "1"));
		options.add(new SelectizeOption("3", "Albert Einstein", "2"));

		List<SelectizeOptionGroup> optionGroups = new ArrayList<SelectizeOptionGroup>();
		optionGroups.add(new SelectizeOptionGroup("1", "1", "English"));
		optionGroups.add(new SelectizeOptionGroup("2", "2", "German"));

		// Simple Select
		Selectize selectize2 = new Selectize("selectize2", Model.of(options));
		selectize2.setTheme(Theme.BOOTSTRAP3);
		form.add(selectize2);

		// Select with Groups
		Selectize selectize3 = new Selectize("selectize3", Model.of(optionGroups),
			Model.of(options));
		selectize3.setPlaceholder("Select a person!");
		selectize3.setTheme(Theme.BOOTSTRAP3);
		form.add(selectize3);

		// Select with ajax
		Selectize selectize4 = new Selectize("selectize4")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected SelectizeResponse response(String search)
			{
				if (search != null && search.equals("test"))
				{
					SelectizeOption option = new SelectizeOption("1", "Test");
					return new SelectizeResponse(Arrays.asList(option),
						Collections.<SelectizeOptionGroup> emptyList());
				}
				return super.response(search);
			}
		};
		selectize4.setAjax();
		selectize4.setTheme(Theme.BOOTSTRAP3);
		form.add(selectize4);
		add(form);
	}
}
