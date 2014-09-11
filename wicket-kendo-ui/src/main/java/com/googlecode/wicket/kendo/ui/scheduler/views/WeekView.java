package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public final class WeekView extends SchedulerView
{

	private static final long serialVersionUID = 1L;

	public WeekView()
	{
		super(SchedulerViewType.week);
	}
	
	public static WeekView newInstance()
	{
		return new WeekView();
	}
}
