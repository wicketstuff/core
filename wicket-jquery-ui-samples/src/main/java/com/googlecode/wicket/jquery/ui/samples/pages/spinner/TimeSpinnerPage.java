package com.googlecode.wicket.jquery.ui.samples.pages.spinner;

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

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.resource.JQueryGlobalizeHeaderItem;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.spinner.TimeSpinner;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.SampleApplication;

public class TimeSpinnerPage extends AbstractSpinnerPage
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
		final TimeSpinner spinner = new TimeSpinner("spinner", form.getModel(), Locale.FRENCH) {

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
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				TimeSpinnerPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(TimeSpinnerPage.this.feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused)
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
