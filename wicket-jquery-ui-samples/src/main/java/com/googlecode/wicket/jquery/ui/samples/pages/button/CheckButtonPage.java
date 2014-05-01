package com.googlecode.wicket.jquery.ui.samples.pages.button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.CheckChoice;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class CheckButtonPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;

	public CheckButtonPage()
	{
		this.initialize();
	}

	private void initialize()
	{
		final IModel<ArrayList<String>> checkModel = new Model<ArrayList<String>>();
		final List<String> checkList = Arrays.asList("my check 1", "my check 2", "my check 3");

		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Radio Button //
		form.add(new CheckChoice<String>("checks", checkModel, checkList));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				CheckButtonPage.this.info(this, checkModel);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				CheckButtonPage.this.info(this, checkModel);
				target.add(form);
			}
		});
	}

	private void info(Component component, IModel<ArrayList<String>> model)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + model.getObject());
	}
}
