package com.googlecode.wicket.jquery.ui.samples.component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.calendar.CalendarEvent;
import com.googlecode.wicket.jquery.ui.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.kendo.datetime.DateTimePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public abstract class CalendarDialog<T extends CalendarEvent> extends AbstractFormDialog<T>
{
	private static final long serialVersionUID = 1L;
	protected static final String BTN_SUBMIT = "Save";
	
	static IModel<CalendarEvent> emptyModel()
	{
		return new Model<CalendarEvent>(new CalendarEvent(0, "", new Date()));
	}

	private Form<?> form;
	private FeedbackPanel feedback;

	@SuppressWarnings("unchecked")
	public CalendarDialog(String id, String title)
	{
		super(id, title, (IModel<T>) emptyModel(), true);
		
		this.init();
	}
	
	private void init()
	{
		this.form = new Form<T>("form", new CompoundPropertyModel<T>(this.getModel()));
		this.add(this.form);

		this.form.add(new RequiredTextField<String>("title"));
		
		CheckBox checkAllDay = new CheckBox("allDay");
		this.form.add(checkAllDay.setOutputMarkupId(true));
		this.form.add(new Label("label", "all day?").add(AttributeModifier.append("for", checkAllDay.getMarkupId())));

//		Options options = new Options();
//		options.set("dateFormat", Options.asString("dd MM yy"));
		
		this.form.add(new DateTimePicker("start").setRequired(true));
		this.form.add(new DateTimePicker("end"));

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
		this.setDefaultModelObject(event);
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
