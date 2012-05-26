package com.googlecode.wicket.jquery.ui.samples.data;

import java.util.Date;
import java.util.List;

import com.googlecode.wicket.jquery.ui.calendar.CalendarModel;

public class DemoCalendarModel extends CalendarModel
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoCalendarEvent> load()
	{
		 Date start = this.getStart();
		 Date end = this.getEnd();
		
		return DemoCalendarDAO.getEvents(start, end);
	}
}
