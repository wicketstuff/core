package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker;

import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.kendo.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.kendo.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.datetime.DatePicker;
import com.googlecode.wicket.jquery.ui.kendo.datetime.DateTimePicker;
import com.googlecode.wicket.jquery.ui.kendo.datetime.TimePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class PatternDateTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public PatternDateTimePickerPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// TimePicker //
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 05, 27, 02, 00, 00);

		IModel<Date> model = new Model<Date>(calendar.getTime());

		final DateTimePicker datetimepicker = new DateTimePicker("datetimepicker", model, "dd MMM yyyy", "HH:mm:ss") {

			private static final long serialVersionUID = 1L;

			/**
			 * First way to specify the kendo-ui format
			 */
			@Override
			protected DatePicker newDatePicker(String id, IModel<Date> model, String datePattern)
			{
				//Specified datePattern is not the default kendo-ui date pattern, but is compatible with both java & kendo-ui
				//It can therefore be transmitted as-is to the DatePicker widget
				return new DatePicker(id, model, datePattern, new Options("format", Options.asString(datePattern)));
			}

			/**
			 * Second way to specify the kendo-ui format
			 */
			@Override
			protected TimePicker newTimePicker(String id, IModel<Date> model, final String timePattern)
			{
				return new TimePicker(id, model, timePattern) {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onConfigure(JQueryBehavior behavior)
					{
						super.onConfigure(behavior);

						//Specified timePattern is not the default kendo-ui date pattern, but is compatible with both java & kendo-ui
						//It can therefore be transmitted as-is to the TimePicker widget
						behavior.setOption("format", Options.asString(timePattern));
					}
				};
			}
		};

		form.add(datetimepicker);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				this.info("Date & Time: " + datetimepicker.getModelObjectAsString());
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.info("Date & Time: " + datetimepicker.getModelObjectAsString());
				target.add(feedback);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(feedback);
			}
		});
	}
}
