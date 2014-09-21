package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * Defines the 'month' scheduler's view
 *
 * @author Patrick Davids - Patrick1701
 *
 */
public class MonthView extends SchedulerView
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a new instance of <tt>MonthView</tt>
	 *
	 * @return a new {@link MonthView}
	 */
	public static MonthView newInstance()
	{
		return new MonthView();
	}

	/**
	 * Constructor
	 */
	private MonthView()
	{
		super(SchedulerViewType.month);
	}

	/**
	 * Sets the template pattern used to render the day slots in month view.
	 *
	 * @param pattern the date pattern
	 * @return <tt>this</tt>, for chaining
	 */
	public SchedulerView setDayTemplatePattern(String pattern)
	{
		this.set("dayTemplate", String.format("kendo.template(\"<strong>#=kendo.toString(date, '%s')#</strong>\")", pattern));

		return this;
	}
}
