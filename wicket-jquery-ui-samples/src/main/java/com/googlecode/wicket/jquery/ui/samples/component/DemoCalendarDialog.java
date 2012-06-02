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

import com.googlecode.wicket.jquery.ui.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.kendo.datetime.DateTimePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarEvent;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarEvent.Category;

public abstract class DemoCalendarDialog extends AbstractFormDialog<DemoCalendarEvent>
{
	private static final long serialVersionUID = 1L;
	protected static final String BTN_SUBMIT = "Save";
	
	static IModel<DemoCalendarEvent> emptyModel()
	{
		return new Model<DemoCalendarEvent>(new DemoCalendarEvent(0, "", Category.PUBLIC, new Date()));
	}

	private Form<?> form;
	private FeedbackPanel feedback;

	public DemoCalendarDialog(String id, String title)
	{
		super(id, title, emptyModel(), true);
		
		this.init();
	}
	
	private void init()
	{
		this.form = new Form<DemoCalendarEvent>("form", new CompoundPropertyModel<DemoCalendarEvent>(this.getModel()));
		this.add(this.form);

		this.form.add(new RequiredTextField<String>("title"));
		this.form.add(new RadioChoice<Category>("category", Arrays.asList(Category.values())));

		CheckBox checkAllDay = new CheckBox("allDay");
		this.form.add(checkAllDay.setOutputMarkupId(true));

		this.form.add(new Label("label", "All day?").add(AttributeModifier.append("for", checkAllDay.getMarkupId())));
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

	public void setModelObject(DemoCalendarEvent event)
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
