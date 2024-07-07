/* Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0 */
package org.wicketstuff.jquery.ui.samples.component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import org.wicketstuff.jquery.core.resource.StyleSheetPackageHeaderItem;
import org.wicketstuff.jquery.ui.form.RadioChoice;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.samples.data.DemoCalendar6Event;
import org.wicketstuff.jquery.ui.samples.data.DemoCalendar6Event.Category;
import org.wicketstuff.jquery.ui.widget.dialog.AbstractFormDialog;
import org.wicketstuff.jquery.ui.widget.dialog.DialogButton;
import org.wicketstuff.kendo.ui.form.datetime.local.DateTimePicker;

public abstract class DemoCalendar6Dialog extends AbstractFormDialog<DemoCalendar6Event> // NOSONAR
{
	private static final long serialVersionUID = 1L;
	protected final DialogButton btnSubmit = new DialogButton(SUBMIT, Model.of("Save"));
	protected final DialogButton btnCancel = new DialogButton(CANCEL, LBL_CANCEL);

	static IModel<DemoCalendar6Event> emptyModel()
	{
		return Model.of(new DemoCalendar6Event("", Category.PUBLIC, LocalDateTime.now()));
	}

	private Form<?> form;
	private FeedbackPanel feedback;
	// DateTimePickers //
	private final DateTimePicker startDateTimePicker = new DateTimePicker("start");
	private final DateTimePicker endDateTimePicker = new DateTimePicker("end");

	public DemoCalendar6Dialog(String id, String title)
	{
		super(id, title, emptyModel(), true);

		this.form = new Form<DemoCalendar6Event>("form", new CompoundPropertyModel<DemoCalendar6Event>(this.getModel()));
		this.add(this.form);

		this.form.add(new RequiredTextField<String>("title"));
		this.form.add(new RadioChoice<Category>("category", Arrays.asList(Category.values())));

		this.form.add(startDateTimePicker.setRequired(true));
		this.form.add(endDateTimePicker);

		// All-day checkbox //
		CheckBox checkAllDay = new AjaxCheckBox("allDay") { // NOSONAR

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

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(DemoCalendarDialog.class));
	}

	// AbstractFormDialog //

	@Override
	protected List<DialogButton> getButtons()
	{
		return Arrays.asList(this.btnSubmit, this.btnCancel);
	}

	@Override
	public DialogButton getSubmitButton()
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
	protected void onOpen(IPartialPageRequestHandler handler)
	{
		boolean timeEnabled = !getModelObject().isAllDay();
		startDateTimePicker.setTimePickerEnabled(handler, timeEnabled);
		endDateTimePicker.setTimePickerEnabled(handler, timeEnabled);
		handler.add(this.form);
	}

	@Override
	public void onError(AjaxRequestTarget target, DialogButton button)
	{
		target.add(this.feedback);
	}
}
