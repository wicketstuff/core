/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.data.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.jquery.ui.samples.data.DemoCalendarEvent;
import org.wicketstuff.jquery.ui.samples.data.DemoCalendarEvent.Category;

public class CalendarDAO
{
	private static CalendarDAO instance = null;

	private static synchronized CalendarDAO get()
	{
		if (instance == null)
		{
			instance = new CalendarDAO();
		}

		return instance;
	}

	public static boolean isNew(DemoCalendarEvent event)
	{
		return event != null && event.getId() == null;
	}

	public static DemoCalendarEvent newEvent(LocalDateTime date)
	{
		return new DemoCalendarEvent("", Category.PUBLIC, date);
	}

	public static DemoCalendarEvent newEvent(LocalDateTime start, LocalDateTime end)
	{
		return new DemoCalendarEvent("", Category.PUBLIC, start, end);
	}

	public static DemoCalendarEvent getEvent(String eventId)
	{
		if (eventId != null)
		{
			for (DemoCalendarEvent event : get().list)
			{
				if (eventId.equals(event.getId())) // event.getId() may be null
				{
					return event;
				}
			}
		}

		return null;
	}

	public static List<DemoCalendarEvent> getEvents(LocalDate start, LocalDate end)
	{
		List<DemoCalendarEvent> events = Generics.newArrayList();

		CalendarDAO dao = get();

		for (DemoCalendarEvent event : dao.list)
		{
			if (dao.isInRange(event, start.atStartOfDay(), end.atStartOfDay()))
			{
				events.add(event);
			}
		}

		return events;
	}

	public static void addEvent(DemoCalendarEvent event)
	{
		if (event != null)
		{
			if (isNew(event))
			{
				event.setId(get().newId());
			}

			get().list.add(event);
		}
	}

	private final List<DemoCalendarEvent> list;
	private final AtomicInteger sequence = new AtomicInteger(0);

	public CalendarDAO()
	{
		this.list = Generics.newArrayList();
		this.initList();
	}

	private final void initList()
	{
		this.list.add(new DemoCalendarEvent(this.newId(), "Public event", Category.PUBLIC, LocalDateTime.now()));
		this.list.add(new DemoCalendarEvent(this.newId(), "Private event", Category.PRIVATE, LocalDateTime.now()));
	}

	protected final String newId()
	{
		return String.valueOf(this.sequence.incrementAndGet());
	}

	/**
	 * Helper that indicates whether an event is in the given date range (between start date & end date)
	 *
	 * @param event
	 * @param start
	 * @param end
	 * @return true or false
	 */
	protected boolean isInRange(DemoCalendarEvent event, LocalDateTime start, LocalDateTime end)
	{
		LocalDateTime dateS = event.getStart();
		LocalDateTime dateE = event.getEnd();

		return dateS != null && start.compareTo(dateS) <= 0 && end.compareTo(dateS) >= 0 && // lf
				(dateE == null || (start.compareTo(dateE) <= 0 && end.compareTo(dateE) >= 0));
	}
}
