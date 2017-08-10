package com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker;

import java.util.Date;
import java.util.Locale;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.datetime.AjaxDateTimePicker;
import com.googlecode.wicket.kendo.ui.form.datetime.DateTimePicker;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.resource.KendoCultureResourceReference;

public class LocaleAjaxDateTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public LocaleAjaxDateTimePickerPage()
	{
		Form<?> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// DateTimePicker //
		final String datePattern = "EEE dd MMM yyyy";
		final String timePattern = "HH:mm";

		final DateTimePicker datetimepicker = new AjaxDateTimePicker("datetimepicker", Model.of(new Date()), Locale.FRANCE, datePattern, timePattern) {

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
				this.info("Submitted: " + datetimepicker.getModelObject()); // warning, model object can be null
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

		response.render(JavaScriptHeaderItem.forReference(new KendoCultureResourceReference(Locale.FRANCE)));
	}
}
