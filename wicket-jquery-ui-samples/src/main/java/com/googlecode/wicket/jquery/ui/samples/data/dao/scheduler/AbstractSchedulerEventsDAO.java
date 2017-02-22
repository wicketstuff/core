package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public abstract class AbstractSchedulerEventsDAO
{
	protected static AtomicInteger sequence = new AtomicInteger();

	protected final List<SchedulerEvent> list;

	protected AbstractSchedulerEventsDAO()
	{
		this.list = new ArrayList<SchedulerEvent>();
	}

	public SchedulerEvent getEvent(Integer eventId)
	{
		for (SchedulerEvent event : this.list)
		{
			if (eventId.equals(event.getId(Integer.class)))
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
			if (isInRange(task, start, end))
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
			event.setId(newId());
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
		Integer eventId = event.getId(Integer.class);
		SchedulerEvent e = this.getEvent(eventId);

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
		Integer eventId = event.getId(Integer.class);
		SchedulerEvent e = this.getEvent(eventId);

		if (e != null)
		{
			this.list.remove(e);
		}
	}

	// static //

	protected static synchronized int newId()
	{
		return sequence.incrementAndGet();
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
}
