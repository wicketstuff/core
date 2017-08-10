package com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker;

import java.util.Date;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.datetime.AjaxDateTimePicker;
import com.googlecode.wicket.kendo.ui.form.datetime.DateTimePicker;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class PatternAjaxDateTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public PatternAjaxDateTimePickerPage()
	{
		Form<?> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// DateTimePicker //
		final DateTimePicker datetimepicker = new AjaxDateTimePicker("datetimepicker", Model.of(new Date()), "EEE dd MMM yyyy", "HH:mm:ss") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				this.info("Value Changed: " + this.getModelObject());

				handler.add(feedback);
			}

			@Override
			protected void onError(IPartialPageRequestHandler handler)
			{
				handler.add(feedback);
			}
		};

		form.add(datetimepicker);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				this.info("Submitted: " + datetimepicker.getModelObject());
			}
		});
	}
}
