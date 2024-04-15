package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * Defines the 'week' scheduler's view
 *
 * @author Patrick Davids - Patrick1701
 *
 */
public class WeekView extends SchedulerView
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a new instance of {@code WeekView}
	 *
	 * @return a new {@code WeekView}
	 */
	public static WeekView newInstance()
	{
		return new WeekView();
	}

	/**
	 * Constructor
	 */
	private WeekView()
	{
		super(SchedulerViewType.week);
	}

	@Override
	public SchedulerView setDateHeaderTemplatePattern(String pattern)
	{
		return super.setDateHeaderTemplatePattern(pattern);
	}
}
