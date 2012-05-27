package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.datetime.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class KendoDatePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;
	
	public KendoDatePickerPage()
	{
		final Form<Date> form = new Form<Date>("form", new Model<Date>());
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Date Picker //
		form.add(new DatePicker("datepicker", form.getModel()));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				KendoDatePickerPage.this.info(this, form);
			}			
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				KendoDatePickerPage.this.info(this, form);
				target.add(form);
			}
		});
	}

	private void info(Component component, Form<?> form)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + form.getModelObject());
	}
}
