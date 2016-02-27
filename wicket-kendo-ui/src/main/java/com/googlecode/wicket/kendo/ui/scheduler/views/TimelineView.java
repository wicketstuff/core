package com.googlecode.wicket.kendo.ui.scheduler.views;



/**
 * Defines the 'timeline' scheduler's view
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class TimelineView extends SchedulerView
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a new instance of {@code AgendaView}
	 *
	 * @return a new {@code TimelineView}
	 */
	public static TimelineView newInstance()
	{
		return new TimelineView();
	}

	/**
	 * Constructor
	 */
	private TimelineView()
	{
		super(SchedulerViewType.timeline);
	}
}
