package com.googlecode.wicket.jquery.ui.samples.pages.spinner;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.spinner.AjaxSpinner;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxSpinnerPage extends AbstractSpinnerPage
{
	private static final long serialVersionUID = 1L;
	private FeedbackPanel feedback;

	public AjaxSpinnerPage()
	{
		final Form<Integer> form = new Form<Integer>("form", Model.of(0));
		this.add(form);

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		form.add(this.feedback.setOutputMarkupId(true));

		// Spinner //
		final AjaxSpinner<Integer> spinner = new AjaxSpinner<Integer>("spinner", form.getModel(), Integer.class) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSpin(AjaxRequestTarget target, Integer value)
			{
				this.info("Spinned to : " + value);
				target.add(feedback);
			}
		};

		form.add(spinner);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				AjaxSpinnerPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(AjaxSpinnerPage.this.feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused)
			{
				AjaxSpinnerPage.this.info(this, form);
				target.add(form);
			}
		});
	}

	private void info(Component component, Form<Integer> form)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + form.getModelObject());
	}
}
