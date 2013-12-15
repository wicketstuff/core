package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker;

import java.util.Calendar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.kendo.ui.form.datetime.AjaxDateTimePicker;
import com.googlecode.wicket.kendo.ui.form.datetime.DateTimePicker;

public class AjaxDateTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public AjaxDateTimePickerPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// TimePicker //
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 05, 27, 02, 00);

		final DateTimePicker datetimepicker = new AjaxDateTimePicker("datetimepicker", Model.of(calendar.getTime())) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(AjaxRequestTarget target)
			{
				this.info(this.getModelObjectAsString());

				target.add(feedback);
			}

		};

		form.add(datetimepicker);
	}
}
