package com.googlecode.wicket.kendo.ui.scheduler.views;

/**
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public final class AgendaView extends SchedulerView
{

	private static final long serialVersionUID = 1L;

	private AgendaView()
	{
		super(SchedulerViewType.agenda);
	}

	public static AgendaView newInstance()
	{
		return new AgendaView();
	}
}