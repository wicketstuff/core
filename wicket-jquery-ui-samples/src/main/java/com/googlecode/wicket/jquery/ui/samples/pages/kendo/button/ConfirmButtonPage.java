package com.googlecode.wicket.jquery.ui.samples.pages.kendo.button;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.form.button.ConfirmButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class ConfirmButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;

	public ConfirmButtonPage()
	{
		final Form<Void> form = new Form<Void>("form") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit()
			{
				info("Form submitted");
			}
		};

		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// TextField //
		final TextField<String> textField = new TextField<String>("text", new Model<String>());
		form.add(textField.setRequired(true));

		// Buttons //
		form.add(new ConfirmButton("button", "Submit", "Please confirm", "Do you confirm?") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				this.info("Model object: " + textField.getModelObject());
			}

			@Override
			public void onError()
			{
				this.error("Validation failed!");
			}
		});
	}
}
