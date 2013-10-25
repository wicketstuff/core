package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.kendo.datetime.AjaxDatePicker;
import com.googlecode.wicket.jquery.ui.kendo.datetime.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxDatePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public AjaxDatePickerPage()
	{
		final Form<Date> form = new Form<Date>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Date Picker //
		final DatePicker datepicker = new AjaxDatePicker("datepicker", Model.of(new Date())) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(AjaxRequestTarget target)
			{
				this.info(this.getModelObjectAsString());

				target.add(feedback);
			}
		};

		form.add(datepicker);
	}
}
