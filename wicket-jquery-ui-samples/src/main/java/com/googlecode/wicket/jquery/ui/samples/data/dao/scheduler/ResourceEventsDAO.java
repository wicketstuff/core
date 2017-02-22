package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.util.Date;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public class ResourceEventsDAO extends AbstractSchedulerEventsDAO
{
	public static final String AGENDA_ID = "agendaId";
	public static final String AGENDA_1 = "agenda1";
	public static final String AGENDA_2 = "agenda2";

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
		SchedulerEvent event1 = new SchedulerEvent(newId(), "Event #1", new Date());
		event1.setValue(AGENDA_ID, AGENDA_1);
		this.list.add(event1);

		SchedulerEvent event2 = new SchedulerEvent(newId(), "Event #2", new Date());
		event2.setValue(AGENDA_ID, AGENDA_2);
		this.list.add(event2);
	}

	@Override
	public SchedulerEvent update(SchedulerEvent event)
	{
		SchedulerEvent e = super.update(event);

		if (e != null)
		{
			e.setValue(AGENDA_ID, event.getValue(AGENDA_ID));
		}

		return e;
	}
}
