package com.googlecode.wicket.jquery.ui.samples.jqueryui.datepicker;

import java.util.Date;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
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
		this.initialize();
	}

	private void initialize()
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
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				info("Selected date: " + this.getModelObject());
				handler.add(feedback);
			}
		});
	}
}
