/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.spinner;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.wicketstuff.jquery.core.JQueryBehavior;
import org.wicketstuff.jquery.core.resource.JQueryGlobalizeHeaderItem;
import org.wicketstuff.jquery.ui.form.button.AjaxButton;
import org.wicketstuff.jquery.ui.form.button.Button;
import org.wicketstuff.jquery.ui.form.spinner.TimeSpinner;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.samples.SampleApplication;

public class TimeSpinnerPage extends AbstractSpinnerPage // NOSONAR
{
	private static final long serialVersionUID = 1L;
	private FeedbackPanel feedback;

	public TimeSpinnerPage()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(0, 0, 0, 14, 30); // 2:30 PM

		final Form<Date> form = new Form<Date>("form", Model.of(calendar.getTime()));
		this.add(form);

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		form.add(this.feedback.setOutputMarkupId(true));

		// Spinner //
		final TimeSpinner spinner = new TimeSpinner("spinner", form.getModel(), Locale.FRENCH) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(JQueryBehavior behavior)
			{
				super.onConfigure(behavior);

				behavior.add(new JavaScriptResourceReference(SampleApplication.class, "globalize.culture.fr-FR.js"));
			}
		};

		form.add(spinner);

		// Buttons //
		form.add(new Button("submit") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				TimeSpinnerPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(TimeSpinnerPage.this.feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				TimeSpinnerPage.this.info(this, form);
				target.add(form);
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		// important! //
		response.render(new PriorityHeaderItem(new JQueryGlobalizeHeaderItem()));
	}

	private void info(Component component, Form<Date> form)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + form.getModelObject());
	}
}
