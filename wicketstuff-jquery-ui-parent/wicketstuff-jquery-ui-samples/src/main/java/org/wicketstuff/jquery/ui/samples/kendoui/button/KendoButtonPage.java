/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.button;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.kendo.ui.form.button.Button;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;


public class KendoButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;

	public KendoButtonPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		form.add(new KendoFeedbackPanel("feedback"));

		// Buttons //
		form.add(new Button("button1", Model.of("Submit")) { // the model here is used to retrieve the button's text afterward

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				KendoButtonPage.this.info(this);
			}
		});

		form.add(new Button("button2", Model.of("Submit, with client click")) { // idem as previous comment

			private static final long serialVersionUID = 1L;

			@Override
			protected String getOnClickScript()
			{
				return "alert('The button has been clicked!');";
			}

			@Override
			public void onSubmit()
			{
				KendoButtonPage.this.info(this);
			}
		});
	}

	private final void info(Component component)
	{
		this.info(String.format("'%s' has been clicked", component.getDefaultModelObjectAsString()));
	}
}
