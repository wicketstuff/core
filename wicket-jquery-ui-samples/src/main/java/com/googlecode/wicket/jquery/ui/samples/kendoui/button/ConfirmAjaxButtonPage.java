package com.googlecode.wicket.jquery.ui.samples.kendoui.button;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.form.button.ConfirmAjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class ConfirmAjaxButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;

	public ConfirmAjaxButtonPage()
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
		form.add(new ConfirmAjaxButton("button", "Submit", "Please confirm", "Do you confirm?") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				this.error("Validation failed!");

				target.add(feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				this.info("Model object: " + textField.getModelObject());

				target.add(feedback);
			}

			@Override
			protected void onCancel(AjaxRequestTarget target)
			{
				this.warn("Canceled...");

				target.add(feedback);
			}
		});
	}
}
