package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public class SchedulerEventsDAO extends AbstractSchedulerEventsDAO
{
	private static SchedulerEventsDAO instance = null;

	public static synchronized SchedulerEventsDAO get()
	{
		if (instance == null)
		{
			instance = new SchedulerEventsDAO();
		}

		return instance;
	}

	protected SchedulerEventsDAO()
	{
		// dates //
		// event 1 //
		final ZonedDateTime date1 = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0, 0), ZoneOffset.UTC);
		final SchedulerEvent event1 = new SchedulerEvent(newId(), "An event", date1);
		super.list.add(event1);

		// event 2 //
		final ZonedDateTime date2 = ZonedDateTime.of(LocalDate.now(), LocalTime.of(16, 0, 0), ZoneOffset.UTC);
		final SchedulerEvent event2 = new SchedulerEvent(newId(), "Meeting", date2);
		event2.setRecurrenceRule("FREQ=WEEKLY;COUNT=10;BYDAY=MO;WKST=SU"); // rfc5545 specification
		super.list.add(event2);
	}
}
