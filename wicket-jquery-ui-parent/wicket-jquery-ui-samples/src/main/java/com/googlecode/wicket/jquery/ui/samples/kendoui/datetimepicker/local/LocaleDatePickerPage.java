package com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.local;

import java.time.LocalDate;
import java.util.Locale;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.datetime.local.DatePicker;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.resource.KendoCultureResourceReference;

public class LocaleDatePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public LocaleDatePickerPage()
	{
		final Form<?> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// DatePicker //
		final DatePicker datepicker = new DatePicker("datepicker", Model.of(LocalDate.now()), Locale.FRANCE, "EEE dd MMM yyyy");
		form.add(datepicker);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				this.info("Submitted: " + datepicker.getModelObject());
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				this.info("Submitted: " + datepicker.getModelObject());
				target.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(feedback);
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
