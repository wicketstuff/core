package com.googlecode.wicket.jquery.ui.samples.data.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendar6Event;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendar6Event.Category;

public class Calendar6DAO
{
	private static Calendar6DAO instance = null;

	private static synchronized Calendar6DAO get()
	{
		if (instance == null)
		{
			instance = new Calendar6DAO();
		}

		return instance;
	}

	public static boolean isNew(DemoCalendar6Event event)
	{
		return event != null && event.getId() == null;
	}

	public static DemoCalendar6Event newEvent(LocalDateTime date)
	{
		return new DemoCalendar6Event("", Category.PUBLIC, date);
	}

	public static DemoCalendar6Event newEvent(LocalDateTime start, LocalDateTime end)
	{
		return new DemoCalendar6Event("", Category.PUBLIC, start, end);
	}

	public static DemoCalendar6Event getEvent(String eventId)
	{
		if (eventId != null)
		{
			for (DemoCalendar6Event event : get().list)
			{
				if (eventId.equals(event.getId())) // event.getId() may be null
				{
					return event;
				}
			}
		}

		return null;
	}

	public static List<DemoCalendar6Event> getEvents(LocalDate start, LocalDate end)
	{
		List<DemoCalendar6Event> events = Generics.newArrayList();

		Calendar6DAO dao = get();

		for (DemoCalendar6Event event : dao.list)
		{
			if (dao.isInRange(event, start.atStartOfDay(), end.atStartOfDay()))
			{
				events.add(event);
			}
		}

		return events;
	}

	public static void addEvent(DemoCalendar6Event event)
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

	private final List<DemoCalendar6Event> list;
	private final AtomicInteger sequence = new AtomicInteger(0);

	public Calendar6DAO()
	{
		this.list = Generics.newArrayList();
		this.initList();
	}

	private final void initList()
	{
		this.list.add(new DemoCalendar6Event(this.newId(), "Public event", Category.PUBLIC, LocalDateTime.now()));
		this.list.add(new DemoCalendar6Event(this.newId(), "Private event", Category.PRIVATE, LocalDateTime.now()));
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
	protected boolean isInRange(DemoCalendar6Event event, LocalDateTime start, LocalDateTime end)
	{
		LocalDateTime dateS = event.getStart();
		LocalDateTime dateE = event.getEnd();

		return dateS != null && start.compareTo(dateS) <= 0 && end.compareTo(dateS) >= 0 && // lf
				(dateE == null || (start.compareTo(dateE) <= 0 && end.compareTo(dateE) >= 0));
	}
}
