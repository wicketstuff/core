package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.util.Calendar;
import java.util.Date;

import com.googlecode.wicket.jquery.core.utils.DateUtils;
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
		Calendar calendar = Calendar.getInstance(DateUtils.UTC);
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date1 = calendar.getTime();

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date date2 = calendar.getTime();

		// events //
		super.list.add(new SchedulerEvent(newId(), "An event", date1));

		SchedulerEvent event = new SchedulerEvent(newId(), "Meeting", date2);
		event.setRecurrenceRule("FREQ=WEEKLY;COUNT=10;BYDAY=MO;WKST=SU"); // rfc5545 specification
		super.list.add(event);
	}
}
