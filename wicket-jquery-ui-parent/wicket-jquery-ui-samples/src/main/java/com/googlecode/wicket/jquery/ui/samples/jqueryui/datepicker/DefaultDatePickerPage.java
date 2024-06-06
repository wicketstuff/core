/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package com.googlecode.wicket.jquery.ui.samples.jqueryui.datepicker;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultDatePickerPage extends AbstractDatePickerPage
{
	private static final long serialVersionUID = 1L;

	public DefaultDatePickerPage()
	{
		final Form<Date> form = new Form<Date>("form", new Model<Date>());
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Date Picker //
		form.add(new DatePicker("datepicker", form.getModel()).setRequired(true));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				DefaultDatePickerPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				DefaultDatePickerPage.this.info(this, form);
				target.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(form); //refresh feedback panel
			}
		});
	}

	private void info(Component component, Form<?> form)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + form.getModelObject());
	}
}
