package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public final class WorkWeekView extends SchedulerView
{

	private static final long serialVersionUID = 1L;

	public WorkWeekView()
	{
		super(SchedulerViewType.workWeek);
	}
	
	public static WorkWeekView newInstance()
	{
		return new WorkWeekView();
	}
}
