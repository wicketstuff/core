package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker;

import java.util.Calendar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.kendo.datetime.AjaxTimePicker;
import com.googlecode.wicket.jquery.ui.kendo.datetime.TimePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public AjaxTimePickerPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// TimePicker //
		Calendar calendar = Calendar.getInstance();
		calendar.set(0, 0, 0, 14, 0); //2:00 PM

		final TimePicker timepicker = new AjaxTimePicker("timepicker", Model.of(calendar.getTime())) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(AjaxRequestTarget target)
			{
				this.info(this.getModelObjectAsString());

				target.add(feedback);
			}
		};
		
		form.add(timepicker);
	}
}
