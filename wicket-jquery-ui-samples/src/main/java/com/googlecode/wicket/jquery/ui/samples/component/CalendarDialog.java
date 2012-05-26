package com.googlecode.wicket.jquery.ui.samples.component;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.calendar.CalendarEvent;
import com.googlecode.wicket.jquery.ui.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public abstract class CalendarDialog<T extends CalendarEvent> extends AbstractFormDialog<T>
{
	private static final long serialVersionUID = 1L;
	protected static final String BTN_SUBMIT = "Save";

	private Form<?> form;
	private FeedbackPanel feedback;

	public CalendarDialog(String id, String title)
	{
		super(id, title, true);

		Options options = new Options();
		options.set("dateFormat", Options.asString("dd MM yy"));

		this.form = new Form<Integer>("form");
		this.add(this.form);

		// Slider //
		this.form.add(new RequiredTextField<String>("title"));
		this.form.add(new DatePicker("start", "dd MMMM yyyy", options).setRequired(true));
		this.form.add(new DatePicker("end", "dd MMMM yyyy", options));

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.form.add(this.feedback);
	}

	// AbstractFormDialog //
	@Override
	protected List<String> getButtons()
	{
		return Arrays.asList(BTN_SUBMIT, BTN_CANCEL);
	}

	@Override
	protected String getSubmitButton()
	{
		return BTN_SUBMIT;
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
	}

	public void setModelObject(T event)
	{
		this.setDefaultModel(new CompoundPropertyModel<T>(event));
	}

	// Events //
	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		target.add(this.form);
	}

	@Override
	public void onError(AjaxRequestTarget target)
	{
		target.add(this.feedback);
	}
}
