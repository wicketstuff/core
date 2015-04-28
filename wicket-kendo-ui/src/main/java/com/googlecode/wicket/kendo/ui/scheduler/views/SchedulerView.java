package com.googlecode.wicket.kendo.ui.scheduler.views;

import com.googlecode.wicket.jquery.core.Options;

/**
 * Defines the views displayed by the scheduler and their configuration.<br/>
 * By default the Kendo UI Scheduler widget displays "day" and "week" view.
 *
 * @author Patrick Davids - Patrick1701
 * @see <a href="http://docs.telerik.com/kendo-ui/api/javascript/ui/scheduler">http://docs.telerik.com/kendo-ui/api/javascript/ui/scheduler</a>
 */
public abstract class SchedulerView extends Options
{
	private static final long serialVersionUID = 1L;

	protected SchedulerView(SchedulerViewType type)
	{
		this.set("type", Options.asString(type.name()));
	}

	/**
	 * Sets the user-friendly title of the view displayed by the scheduler.
	 *
	 * @param title the title
	 * @return {@code this}, for chaining
	 */
	public SchedulerView setTitle(String title)
	{
		this.set("title", Options.asString(title));

		return this;
	}

	/**
	 * Indicates whether the view will be initially selected by the scheduler widget.
	 *
	 * @param selected true or false
	 * @return {@code this}, for chaining
	 */
	public SchedulerView setSelected(boolean selected)
	{
		this.set("selected", selected);

		return this;
	}

	/**
	 * Indicates whether the user would be able to create new scheduler events and modify or delete existing ones<br/>
	 * Overrides the editable option of the scheduler.
	 *
	 * @param editable true or false
	 * @return {@code this}, for chaining
	 */
	public SchedulerView setEditable(boolean editable)
	{
		this.set("editable", editable);

		return this;
	}

	/**
	 * Indicates whether the view will be initially shown in business hours mode.<br/>
	 * By default view is displayed in full day mode.
	 * @param show true or false
	 * @return {@code this}, for chaining
	 */
	public SchedulerView setShowWorkHours(boolean show)
	{
		this.set("showWorkHours", show);

		return this;
	}

	/**
	 * Sets the template pattern to be used to render the date header cells.<br/>
	 * {@link SchedulerView#setDateHeaderTemplatePattern(String)} is marked as protected by default because it is not available to all views.
	 *
	 * @param pattern the date pattern
	 * @return {@code this}, for chaining
	 */
	protected SchedulerView setDateHeaderTemplatePattern(String pattern)
	{
		this.set("dateHeaderTemplate", String.format("kendo.template(\"<strong>#=kendo.toString(date, '%s')#</strong>\")", pattern));

		return this;
	}
}
