package com.googlecode.wicket.jquery.ui.samples.data.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public class SchedulerDAO
{
	private static SchedulerDAO instance = null;

	public static synchronized SchedulerDAO get()
	{
		if (instance == null)
		{
			instance = new SchedulerDAO();
		}

		return instance;
	}

	public static SchedulerEvent newEvent(Date date)
	{
		return new SchedulerEvent(SchedulerEvent.NEW_ID, "", date);
	}

	public static SchedulerEvent newEvent(Date start, Date end)
	{
		return new SchedulerEvent(SchedulerEvent.NEW_ID, "", start, end);
	}

	/**
	 * Helper that indicates whether an event is in the given date range (between start date & end date)
	 *
	 * @param event
	 * @param start
	 * @param end
	 * @return true or false
	 */
	public static boolean isInRange(SchedulerEvent event, Date start, Date end)
	{
		Date date = event.getStart();

		return date != null && start.compareTo(date) <= 0 && end.compareTo(date) >= 0;
	}


	private final List<SchedulerEvent> list;
	private int id = SchedulerEvent.NEW_ID;

	public SchedulerDAO()
	{
		this.list = new ArrayList<SchedulerEvent>();
		this.initList();
	}

	private final void initList()
	{
		this.list.add(new SchedulerEvent(this.newId(), "Public event", new Date()));
		this.list.add(new SchedulerEvent(this.newId(), "Private event", new Date()));
	}

	protected final int newId()
	{
		return ++this.id;
	}

	public SchedulerEvent getEvent(int eventId)
	{
		for (SchedulerEvent event : this.list)
		{
			if (event.getId() == eventId)
			{
				return event;
			}
		}

		return null;
	}

	public List<SchedulerEvent> getEvents(Date start, Date end)
	{
		List<SchedulerEvent> events = new ArrayList<SchedulerEvent>();

		for (SchedulerEvent task : this.list)
		{
			if (SchedulerDAO.isInRange(task, start, end))
			{
				events.add(task);
			}
		}

		return events;
	}

	public void createEvent(SchedulerEvent event)
	{
		if (SchedulerEvent.isNew(event))
		{
			event.setId(this.newId());
			this.list.add(event);
		}
	}

	public void updateEvent(SchedulerEvent event) {

		SchedulerEvent e = this.getEvent(event.getId());

		if (e != null)
		{
			e.setTitle(event.getTitle());
			e.setStart(event.getStart());
			e.setEnd(event.getEnd());
			e.setAllDay(event.isAllDay());
		}
	}
}
