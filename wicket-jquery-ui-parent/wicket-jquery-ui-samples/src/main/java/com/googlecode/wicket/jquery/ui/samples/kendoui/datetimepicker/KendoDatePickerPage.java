package com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.datetime.DatePicker;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class KendoDatePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;

	public KendoDatePickerPage()
	{
		final Form<?> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// Date Picker //
		final DatePicker datepicker = new DatePicker("datepicker", Model.of(new Date()));
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
}
