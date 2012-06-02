package com.googlecode.wicket.jquery.ui.samples.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarEvent.Category;

public class DemoCalendarDAO
{
	private static DemoCalendarDAO instance = null;
	public static int EMPTY_ID = -1;

	private static synchronized DemoCalendarDAO get()
	{
		if (instance == null)
		{
			instance = new DemoCalendarDAO();
		}

		return instance;
	}
	
	public static boolean isNew(DemoCalendarEvent event)
	{
		return (event != null && event.getId() == EMPTY_ID);
	}

	public static DemoCalendarEvent emptyEvent(Date date)
	{
		return new DemoCalendarEvent(EMPTY_ID, "", Category.PUBLIC, date);
	}

	public static DemoCalendarEvent emptyEvent(Date start, Date end)
	{
		return new DemoCalendarEvent(EMPTY_ID, "", Category.PUBLIC, start, end);
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

	public static List<DemoCalendarEvent> getEvents(Date start, Date end)
	{
		List<DemoCalendarEvent> events = new ArrayList<DemoCalendarEvent>();
		
		DemoCalendarDAO dao = get();

		for (DemoCalendarEvent event : dao.list)
		{
			if (dao.isInRange(event, start, end))
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

	public DemoCalendarDAO()
	{
		this.list = new ArrayList<DemoCalendarEvent>();
		this.initList();
	}

	private void initList()
	{
		this.list.add(new DemoCalendarEvent(this.newId(), "Public event", Category.PUBLIC, new Date()));
		this.list.add(new DemoCalendarEvent(this.newId(), "Private event", Category.PRIVATE, new Date()));				
	}

	protected int newId()
	{
		return ++this.id;
	}

	/**
	 * Helper that indicates whether an event is in the given date range (between start date & end date)
	 * 
	 * @return true or false
	 */
	protected boolean isInRange(DemoCalendarEvent event, Date start, Date end)
	{
		Date date = event.getStart();

		return (date != null && start.compareTo(date) <= 0 && end.compareTo(date) >= 0);
	}
}
