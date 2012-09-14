package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker;

import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.kendo.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.kendo.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.datetime.TimePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public DefaultTimePickerPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// TimePicker //
		Calendar calendar = Calendar.getInstance();
		calendar.set(0, 0, 0, 14, 0); //2:00 PM

		final TimePicker timepicker = new TimePicker("timepicker", new Model<Date>(calendar.getTime()));
		form.add(timepicker);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				this.info(timepicker.getModelObjectAsString());
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.info(timepicker.getModelObjectAsString());
				target.add(feedbackPanel);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(feedbackPanel);
			}
		});
	}
}
