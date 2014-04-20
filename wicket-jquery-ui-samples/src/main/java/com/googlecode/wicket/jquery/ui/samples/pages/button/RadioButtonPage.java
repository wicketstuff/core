package com.googlecode.wicket.jquery.ui.samples.pages.button;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class RadioButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;

	public RadioButtonPage()
	{
		final IModel<String> radioModel = new Model<String>();
		final List<String> radioList = Arrays.asList("my radio 1", "my radio 2", "my radio 3");

		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Radio Button //
		form.add(new RadioChoice<String>("radios", radioModel, radioList));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				RadioButtonPage.this.info(this, radioModel);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				RadioButtonPage.this.info(this, radioModel);
				target.add(form);
			}
		});
	}

	private void info(Component component, IModel<String> model)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + model.getObject());
	}
}
