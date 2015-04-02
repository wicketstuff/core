package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.util.Date;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public class ResourceEventsDAO extends AbstractSchedulerEventsDAO
{
	public static final String AGENDA_ID = "agendaId";

	private static ResourceEventsDAO instance = null;

	public static synchronized ResourceEventsDAO get()
	{
		if (instance == null)
		{
			instance = new ResourceEventsDAO();
		}

		return instance;
	}

	protected ResourceEventsDAO()
	{
		SchedulerEvent event1 = new SchedulerEvent(this.newId(), "Event #1", new Date());
		event1.setResource(AGENDA_ID, 1);
		super.list.add(event1);

		SchedulerEvent event2 = new SchedulerEvent(this.newId(), "Event #2", new Date());
		event2.setResource(AGENDA_ID, 2);
		super.list.add(event2);
	}

	@Override
	public SchedulerEvent update(SchedulerEvent event)
	{
		SchedulerEvent e = super.update(event);

		if (e != null)
		{
			e.setResource(AGENDA_ID, (Integer) event.getValue(AGENDA_ID));
		}

		return e;
	}
}
