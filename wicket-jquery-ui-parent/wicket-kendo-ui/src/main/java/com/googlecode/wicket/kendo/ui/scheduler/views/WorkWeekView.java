package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * Defines the 'workWeek' scheduler's view
 *
 * @author Patrick Davids - Patrick1701
 *
 */
public class WorkWeekView extends SchedulerView
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a new instance of {@code WorkWeekView}
	 *
	 * @return a new {@code WorkWeekView}
	 */
	public static WorkWeekView newInstance()
	{
		return new WorkWeekView();
	}

	/**
	 * Constructor
	 */
	private WorkWeekView()
	{
		super(SchedulerViewType.workWeek);
	}

	@Override
	public SchedulerView setDateHeaderTemplatePattern(String pattern)
	{
		return super.setDateHeaderTemplatePattern(pattern);
	}
}
