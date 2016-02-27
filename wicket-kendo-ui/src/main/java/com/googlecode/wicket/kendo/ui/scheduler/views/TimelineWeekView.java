package com.googlecode.wicket.kendo.ui.scheduler.views;



/**
 * Defines the 'timeline' scheduler's view
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class TimelineWeekView extends SchedulerView
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a new instance of {@code AgendaView}
	 *
	 * @return a new {@code TimelineWeekView}
	 */
	public static TimelineWeekView newInstance()
	{
		return new TimelineWeekView();
	}

	/**
	 * Constructor
	 */
	private TimelineWeekView()
	{
		super(SchedulerViewType.timelineWeek);
	}
}
