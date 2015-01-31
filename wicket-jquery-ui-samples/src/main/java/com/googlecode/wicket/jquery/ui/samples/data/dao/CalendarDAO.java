package com.googlecode.wicket.jquery.ui.samples.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarEvent;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarEvent.Category;

public class CalendarDAO
{
	private static CalendarDAO instance = null;

	/** new event id */
	private static int ID = -1;

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
		return event != null && event.getId() == ID;
	}

	public static DemoCalendarEvent newEvent(LocalDateTime date)
	{
		return new DemoCalendarEvent(ID, "", Category.PUBLIC, date);
	}

	public static DemoCalendarEvent newEvent(LocalDateTime start, LocalDateTime end)
	{
		return new DemoCalendarEvent(ID, "", Category.PUBLIC, start, end);
	}

	public static DemoCalendarEvent getEvent(int eventId)
	{
		for (DemoCalendarEvent event : get().list)
		{
			if (event.getId() == eventId)
			{
				return event;
			}
		}

		return null;
	}

	public static List<DemoCalendarEvent> getEvents(LocalDate start, LocalDate end)
	{
		List<DemoCalendarEvent> events = new ArrayList<DemoCalendarEvent>();

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
	private int id = 0;

	public CalendarDAO()
	{
		this.list = new ArrayList<DemoCalendarEvent>();
		this.initList();
	}

	private final void initList()
	{
		this.list.add(new DemoCalendarEvent(this.newId(), "Public event", Category.PUBLIC, LocalDateTime.now()));
		this.list.add(new DemoCalendarEvent(this.newId(), "Private event", Category.PRIVATE, LocalDateTime.now()));
	}

	protected final int newId()
	{
		return ++this.id;
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
