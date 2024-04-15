package com.googlecode.wicket.kendo.ui.scheduler.views;



/**
 * Defines the 'timeline' scheduler's view
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class TimelineWorkWeekView extends SchedulerView
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a new instance of {@code AgendaView}
	 *
	 * @return a new {@code TimelineWorkWeekView}
	 */
	public static TimelineWorkWeekView newInstance()
	{
		return new TimelineWorkWeekView();
	}

	/**
	 * Constructor
	 */
	private TimelineWorkWeekView()
	{
		super(SchedulerViewType.timelineWorkWeek);
	}
}
