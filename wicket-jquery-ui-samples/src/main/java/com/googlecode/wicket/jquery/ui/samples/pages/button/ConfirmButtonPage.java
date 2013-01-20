package com.googlecode.wicket.jquery.ui.samples.pages.button;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.ConfirmButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ConfirmButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;
	
	public ConfirmButtonPage()
	{
		this.init();
	}
	
	private void init()
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
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));
		
		// TextField //
		final RequiredTextField<String> textField = new RequiredTextField<String>("text", new Model<String>());
		form.add(textField);

		// Buttons //
		form.add(new ConfirmButton("button", "Submit", "Please confirm", "Do you confirm?") {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onError()
			{
				this.error("Validation failed!");
			}

			@Override
			public void onSubmit()
			{
				this.info("Model object: " + textField.getModelObject());
			}
		});
	}
}
