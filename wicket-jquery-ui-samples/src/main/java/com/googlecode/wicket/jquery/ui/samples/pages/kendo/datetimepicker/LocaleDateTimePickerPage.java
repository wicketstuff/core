package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.KendoCultureHeaderItem;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.datetime.DatePicker;
import com.googlecode.wicket.kendo.ui.form.datetime.DateTimePicker;
import com.googlecode.wicket.kendo.ui.form.datetime.TimePicker;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class LocaleDateTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public LocaleDateTimePickerPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// TimePicker //
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 5, 27, 2, 0);

		final DateTimePicker datetimepicker = new DateTimePicker("datetimepicker", Model.of(calendar.getTime())) {

			private static final long serialVersionUID = 1L;

			@Override
			protected DatePicker newDatePicker(String id, IModel<Date> model, String datePattern)
			{
				return new DatePicker(id, model, Locale.FRENCH);
			}

			@Override
			protected TimePicker newTimePicker(String id, IModel<Date> model, String timePattern)
			{
				return new TimePicker(id, model, Locale.FRENCH);
			}
		};

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
				target.add(feedback);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(feedback);
			}
		});
	}

	/**
	 * renderHead could be overridden directly in DatePicker if using wicket6+ (javascript dependencies priority)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		// response.render(new KendoCultureHeaderItem("fr")); // or fr-FR
		// response.render(new KendoCultureHeaderItem(KendoCulture.FR)); // or KendoCulture.FR_FR
		response.render(new KendoCultureHeaderItem(Locale.FRENCH));
	}
}
