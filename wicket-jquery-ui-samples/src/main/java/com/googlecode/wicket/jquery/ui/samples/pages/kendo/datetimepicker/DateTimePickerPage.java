package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker;

import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.kendo.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.kendo.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.datetime.DateTimePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DateTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public DateTimePickerPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// TimePicker //
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 05, 27, 02, 00);

		final DateTimePicker datetimepicker = new DateTimePicker("datetimepicker", new Model<Date>(calendar.getTime()));
		form.add(datetimepicker);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				this.info("Date & Time: " + datetimepicker.getModelObject()); //warning, model object can be null
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.info("Date & Time: " + datetimepicker.getModelObject()); //warning, model object can be null
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
