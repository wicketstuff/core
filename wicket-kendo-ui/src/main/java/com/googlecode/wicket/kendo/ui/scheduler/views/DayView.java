package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public final class DayView extends SchedulerView
{

	private static final long serialVersionUID = 1L;

	private DayView()
	{
		super(SchedulerViewType.day);
	}

	public static DayView newInstance()
	{
		return new DayView();
	}
}