package com.googlecode.wicket.jquery.ui.samples.pages.spinner;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.spinner.Spinner;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultSpinnerPage extends AbstractSpinnerPage
{
	private static final long serialVersionUID = 1L;
	private FeedbackPanel feedback;

	public DefaultSpinnerPage()
	{
		this.init();
	}

	private void init()
	{
		final Form<Integer> form = new Form<Integer>("form", new Model<Integer>(0));
		this.add(form);

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		form.add(this.feedback.setOutputMarkupId(true));

		// Spinner //
		final Spinner<Integer> spinner = new Spinner<Integer>("spinner", form.getModel(), Integer.class);
		form.add(spinner.setRequired(false));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				DefaultSpinnerPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(DefaultSpinnerPage.this.feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused)
			{
				DefaultSpinnerPage.this.info(this, form);
				target.add(form);
			}
		});

		form.add(new AjaxButton("toggle") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(DefaultSpinnerPage.this.feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused)
			{
				spinner.setEnabled(!spinner.isEnabled());
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
