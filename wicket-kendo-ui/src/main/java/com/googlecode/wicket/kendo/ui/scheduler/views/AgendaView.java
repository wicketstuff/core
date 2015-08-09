package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * Defines the 'agenda' scheduler's view
 *
 * @author Patrick Davids - Patrick1701
 *
 */
public class AgendaView extends SchedulerView
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a new instance of {@code AgendaView}
	 *
	 * @return a new {@link AgendaView}
	 */
	public static AgendaView newInstance()
	{
		return new AgendaView();
	}

	/**
	 * Constructor
	 */
	private AgendaView()
	{
		super(SchedulerViewType.agenda);
	}
}
