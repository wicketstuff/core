/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.local;

import java.time.LocalDateTime;
import java.util.Locale;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.form.button.Button;
import org.wicketstuff.kendo.ui.form.datetime.local.DateTimePicker;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;
import org.wicketstuff.kendo.ui.resource.KendoCultureResourceReference;

public class LocaleDateTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public LocaleDateTimePickerPage()
	{
		Form<?> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// DateTimePicker //
		final String datePattern = "EEE dd MMM yyyy";
		final String timePattern = "HH:mm";

		final DateTimePicker datetimepicker = new DateTimePicker("datetimepicker", Model.of(LocalDateTime.now()), Locale.FRANCE, datePattern, timePattern);
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

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				this.info("Submitted: " + datetimepicker.getModelObject()); // warning, model object can be null
				target.add(feedback);
			}

			@Override
			protected void onError(AjaxRequestTarget target)
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

		response.render(JavaScriptHeaderItem.forReference(new KendoCultureResourceReference(Locale.FRANCE)));
	}
}
