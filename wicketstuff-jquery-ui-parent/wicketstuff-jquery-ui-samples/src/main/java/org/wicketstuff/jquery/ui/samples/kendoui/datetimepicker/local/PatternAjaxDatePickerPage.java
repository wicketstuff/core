package org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.local;

import java.time.LocalDate;
import java.util.Locale;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.core.utils.LocaleUtils;
import org.wicketstuff.kendo.ui.form.button.Button;
import org.wicketstuff.kendo.ui.form.datetime.local.AjaxDatePicker;
import org.wicketstuff.kendo.ui.form.datetime.local.DatePicker;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;

public class PatternAjaxDatePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public PatternAjaxDatePickerPage()
	{
		final Form<?> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// DatePicker //
		final String pattern = LocaleUtils.getLocaleDatePattern(Locale.FRENCH); // gives the french date pattern, ie: dd/MM/yyyy

		final DatePicker datepicker = new AjaxDatePicker("datepicker", Model.of(LocalDate.now()), pattern) {

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
				this.info("Submitted: " + datepicker.getModelObject());
			}
		});
	}
}
