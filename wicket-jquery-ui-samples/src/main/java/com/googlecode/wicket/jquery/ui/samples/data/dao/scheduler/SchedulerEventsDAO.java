package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.util.Date;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public class SchedulerEventsDAO extends AbstractSchedulerEventsDAO
{
	private static SchedulerEventsDAO instance = null;

	public static synchronized SchedulerEventsDAO get()
	{
		if (instance == null)
		{
			instance = new SchedulerEventsDAO();
		}

		return instance;
	}

	protected SchedulerEventsDAO()
	{
		super();

		super.list.add(new SchedulerEvent(this.newId(), "Public event", new Date()));
		super.list.add(new SchedulerEvent(this.newId(), "Private event", new Date()));
	}

	@Override
	public void update(SchedulerEvent event)
	{
		SchedulerEvent e = this.getEvent(event.getId());

		if (e != null)
		{
			e.setTitle(event.getTitle());
			e.setStart(event.getStart());
			e.setEnd(event.getEnd());
			e.setAllDay(event.isAllDay());
			e.setDescription(event.getDescription());
		}
	}
}
