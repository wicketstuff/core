package com.googlecode.wicket.jquery.ui.samples.pages.kendo.timepicker;

import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.timepicker.TimePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class PatternTimePickerPage extends AbstractTimePickerPage
{
	private static final long serialVersionUID = 1L;
	
	public PatternTimePickerPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);
		
		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// TimePicker //
		Calendar calendar = Calendar.getInstance();
		calendar.set(0, 0, 0, 14, 0); //2:00 PM

		final TimePicker timepicker = new TimePicker("timepicker", new Model<Date>(calendar.getTime()), "HH:mm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure(JQueryBehavior behavior)
			{
				super.onConfigure(behavior);
				
				behavior.setOption("format", Options.asString(this.getTextFormat())); //the pattern - here - is the same between java & jQuery, so we can use #getTextFormat(). When it is not the case, you have to specify the jQuery time pattern, that matches the java time pattern, to the format option.
			}
		};
		form.add(timepicker);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				this.info(timepicker.getModelObjectAsString());
			}			
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.info(timepicker.getModelObjectAsString());
				target.add(feedbackPanel);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(feedbackPanel);
			}
		});
	}
}
