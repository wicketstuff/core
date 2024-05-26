package org.wicketstuff.jquery.ui.samples.jqueryui.spinner;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.ui.form.button.AjaxButton;
import org.wicketstuff.jquery.ui.form.button.Button;
import org.wicketstuff.jquery.ui.form.spinner.Spinner;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultSpinnerPage extends AbstractSpinnerPage // NOSONAR
{
	private static final long serialVersionUID = 1L;
	private FeedbackPanel feedback;

	public DefaultSpinnerPage()
	{
		final Form<Integer> form = new Form<Integer>("form", Model.of(0));
		this.add(form);

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		form.add(this.feedback.setOutputMarkupId(true));

		// Spinner //
		final Spinner<Integer> spinner = new Spinner<Integer>("spinner", form.getModel(), Integer.class);
		form.add(spinner.setRequired(false));

		// Buttons //
		form.add(new Button("submit") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				DefaultSpinnerPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(DefaultSpinnerPage.this.feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				DefaultSpinnerPage.this.info(this, form);
				target.add(form);
			}
		});

		form.add(new AjaxButton("toggle") { // NOSONAR
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(DefaultSpinnerPage.this.feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
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
