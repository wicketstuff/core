package com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.local;

import java.time.LocalDate;
import java.util.Locale;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.datetime.local.AjaxDatePicker;
import com.googlecode.wicket.kendo.ui.form.datetime.local.DatePicker;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.resource.KendoCultureResourceReference;

public class LocaleAjaxDatePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public LocaleAjaxDatePickerPage()
	{
		final Form<?> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// DatePicker //
		final DatePicker datepicker = new AjaxDatePicker("datepicker", Model.of(LocalDate.now()), Locale.FRANCE, "EEE dd MMM yyyy") {

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

		form.add(datepicker);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				this.info("Submitted: " + datepicker.getModelObject()); // warning, model object can be null
			}
		});
	}

	/**
	 * renderHead can be overridden directly in DatePicker if using wicket6+ (javascript dependencies priority)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(JavaScriptHeaderItem.forReference(new KendoCultureResourceReference(Locale.FRANCE)));
	}
}
