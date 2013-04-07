package com.googlecode.wicket.jquery.ui.samples.pages.datepicker;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.datepicker.AjaxDatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxDatePickerPage extends AbstractDatePickerPage
{
	private static final long serialVersionUID = 1L;

	public AjaxDatePickerPage()
	{
		this.init();
	}

	private void init()
	{
		final Form<Date> form = new Form<Date>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Date Picker //
		form.add(new AjaxDatePicker("datepicker", new Model<Date>()) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(AjaxRequestTarget target)
			{
				info("Selected date: " + this.getModelObject());
				target.add(feedback);
			}
		});
	}
}
