package org.wicketstuff.selectize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.selectize.SelectizeCssResourceReference.Theme;

public class TestPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public TestPage()
	{
		Form<Void> form = new Form<>("form");

		// Select with text input
		Selectize selectize1 = new Selectize("selectize1", Model.of("test"));
		selectize1.setTheme(Theme.BOOTSTRAP3);
		selectize1.setCreateAvailable(true);
		form.add(selectize1);

		List<SelectizeOption> options = new ArrayList<>();
		options.add(new SelectizeOption("1", "Brian Reavis", "1"));
//		options.add(new SelectizeOption("2", "Nikola Tesla", "1"));
//		options.add(new SelectizeOption("3", "Albert Einstein", "2"));

		// Simple Select
		Selectize selectize2 = new Selectize("selectize2", Model.of(options));
		selectize2.setTheme(Theme.BOOTSTRAP3);
		form.add(selectize2);

		// Select with Groups
		List<SelectizeOptionGroup> optionGroups = new ArrayList<>();
		optionGroups.add(new SelectizeOptionGroup("1", "1", "English"));
		optionGroups.add(new SelectizeOptionGroup("2", "2", "German"));
		Selectize selectize3 = new Selectize("selectize3", Model.of(optionGroups),
			Model.of(options));
		selectize3.setPlaceholder("Select a person!");
		selectize3.setTheme(Theme.BOOTSTRAP3);
		form.add(selectize3);

		final Label label = new Label("value","Select a value");
		label.setOutputMarkupId(true);
		form.add(label);
		// Select with ajax
		Selectize selectize4 = new Selectize("selectize4")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected SelectizeResponse response(String search)
			{
				if ("test".equals(search))
				{
					SelectizeOption option = new SelectizeOption("1", "Test");
					SelectizeOption option2 = new SelectizeOption("2", "Test2");
					return new SelectizeResponse(Arrays.asList(option, option2));
				}
				return super.response(search);
			}

			@Override
			protected Component responseTemplate()
			{
				return new TestPanel(Selectize.SELECTIZE_COMPONENT_ID);
			}
			
			@Override
			protected void onChange(AjaxRequestTarget target, String value)
			{
				label.modelChanging();
				label.setDefaultModelObject("The selected value is: "+value);
				label.modelChanged();
				target.add(label);
			}
		};
		selectize4.enableAjaxHandling();
		selectize4.setTheme(Theme.BOOTSTRAP3);
		form.add(selectize4);
		add(form);
	}
}
