package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public abstract class AbstractSchedulerEventsDAO
{
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

	private int id = SchedulerEvent.NEW_ID;
	protected final List<SchedulerEvent> list;

	protected AbstractSchedulerEventsDAO()
	{
		this.list = new ArrayList<SchedulerEvent>();
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
			if (AbstractSchedulerEventsDAO.isInRange(task, start, end))
			{
				events.add(task);
			}
		}

		return events;
	}

	public void create(SchedulerEvent event)
	{
		if (SchedulerEvent.isNew(event))
		{
			event.setId(this.newId());
			this.list.add(event);
		}
	}

	/**
	 * Updates and returns the DAO's SchedulerEvent
	 *
	 * @param event the incoming event containing the modifications
	 * @return the updated DAO's SchedulerEvent
	 */
	public SchedulerEvent update(SchedulerEvent event)
	{
		SchedulerEvent e = this.getEvent(event.getId());

		if (e != null)
		{
			e.setTitle(event.getTitle());
			e.setDescription(event.getDescription());

			e.setStart(event.getStart());
			e.setEnd(event.getEnd());
			e.setAllDay(event.isAllDay());

			e.setRecurrenceId(event.getRecurrenceId());
			e.setRecurrenceRule(event.getRecurrenceRule());
			e.setRecurrenceException(event.getRecurrenceException());
		}

		return e;
	}

	public void delete(SchedulerEvent event)
	{
		SchedulerEvent e = this.getEvent(event.getId());

		if (e != null)
		{
			this.list.remove(e);
		}
	}
}
