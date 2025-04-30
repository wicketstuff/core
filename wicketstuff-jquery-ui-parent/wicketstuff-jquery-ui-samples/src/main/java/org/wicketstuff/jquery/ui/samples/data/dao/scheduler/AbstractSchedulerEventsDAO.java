/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.data.dao.scheduler;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.kendo.ui.scheduler.SchedulerEvent;

public abstract class AbstractSchedulerEventsDAO
{
	protected static AtomicInteger sequence = new AtomicInteger();

	protected final List<SchedulerEvent> list;

	protected AbstractSchedulerEventsDAO()
	{
		this.list = Generics.newArrayList();
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

	public List<SchedulerEvent> getEvents(ZonedDateTime start, ZonedDateTime until)
	{
		List<SchedulerEvent> events = Generics.newArrayList();

		for (SchedulerEvent task : this.list)
		{
			if (isInRange(task, start, until))
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
			e.setUntil(event.getUntil());
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
	public static boolean isInRange(SchedulerEvent event, ZonedDateTime start, ZonedDateTime until)
	{
		ZonedDateTime date = event.getStart();

		return date != null && start.compareTo(date) <= 0 && until.compareTo(date) >= 0;
	}
}
