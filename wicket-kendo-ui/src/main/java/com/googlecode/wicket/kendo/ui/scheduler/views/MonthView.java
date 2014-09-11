package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public final class MonthView extends SchedulerView
{

	private static final long serialVersionUID = 1L;

	private MonthView()
	{
		super(SchedulerViewType.month);
	}

	public static MonthView newInstance()
	{
		return new MonthView();
	}
}