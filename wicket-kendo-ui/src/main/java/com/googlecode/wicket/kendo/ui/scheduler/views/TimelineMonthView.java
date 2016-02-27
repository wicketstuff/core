package com.googlecode.wicket.kendo.ui.scheduler.views;



/**
 * Defines the 'timeline' scheduler's view
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class TimelineMonthView extends SchedulerView
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a new instance of {@code AgendaView}
	 *
	 * @return a new {@code TimelineMonthView}
	 */
	public static TimelineMonthView newInstance()
	{
		return new TimelineMonthView();
	}

	/**
	 * Constructor
	 */
	private TimelineMonthView()
	{
		super(SchedulerViewType.timelineMonth);
	}
}
