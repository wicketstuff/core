package com.googlecode.wicket.jquery.ui.samples.component;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.threeten.bp.LocalDateTime;

import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarEvent;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarEvent.Category;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.kendo.ui.form.datetime.local.DateTimePicker;

public abstract class DemoCalendarDialog extends AbstractFormDialog<DemoCalendarEvent>
{
	private static final long serialVersionUID = 1L;
	protected final DialogButton btnSubmit = new DialogButton(SUBMIT, Model.of("Save"));
	protected final DialogButton btnCancel = new DialogButton(CANCEL, LBL_CANCEL);

	static IModel<DemoCalendarEvent> emptyModel()
	{
		return Model.of(new DemoCalendarEvent(0, "", Category.PUBLIC, LocalDateTime.now()));
	}

	private Form<?> form;
	private FeedbackPanel feedback;

	public DemoCalendarDialog(String id, String title)
	{
		super(id, title, emptyModel(), true);

		this.form = new Form<DemoCalendarEvent>("form", new CompoundPropertyModel<DemoCalendarEvent>(this.getModel()));
		this.add(this.form);

		this.form.add(new RequiredTextField<String>("title"));
		this.form.add(new RadioChoice<Category>("category", Arrays.asList(Category.values())));

		// DateTimePickers //
		final DateTimePicker startDateTimePicker = new DateTimePicker("start");
		final DateTimePicker endDateTimePicker = new DateTimePicker("end");

		this.form.add(startDateTimePicker.setRequired(true));
		this.form.add(endDateTimePicker);

		// All-day checkbox //
		CheckBox checkAllDay = new AjaxCheckBox("allDay") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				Boolean allday = this.getModelObject();
				startDateTimePicker.setTimePickerEnabled(!allday);
				endDateTimePicker.setTimePickerEnabled(!allday);
			}

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				Boolean allday = this.getModelObject();
				startDateTimePicker.setTimePickerEnabled(target, !allday);
				endDateTimePicker.setTimePickerEnabled(target, !allday);
			}
		};

		this.form.add(checkAllDay.setOutputMarkupId(true));
		this.form.add(new Label("label", "All day?").add(AttributeModifier.append("for", checkAllDay.getMarkupId())));

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.form.add(this.feedback);
	}

	// AbstractFormDialog //
	@Override
	protected List<DialogButton> getButtons()
	{
		return Arrays.asList(this.btnSubmit, this.btnCancel);
	}

	@Override
	protected DialogButton getSubmitButton()
	{
		return this.btnSubmit;
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
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
