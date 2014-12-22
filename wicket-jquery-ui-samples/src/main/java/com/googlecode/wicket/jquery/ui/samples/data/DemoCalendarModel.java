package com.googlecode.wicket.jquery.ui.samples.data;

import java.util.List;

import org.threeten.bp.LocalDate;

import com.googlecode.wicket.jquery.ui.calendar.CalendarEvent;
import com.googlecode.wicket.jquery.ui.calendar.CalendarModel;
import com.googlecode.wicket.jquery.ui.calendar.ICalendarVisitor;
import com.googlecode.wicket.jquery.ui.samples.data.dao.CalendarDAO;

public class DemoCalendarModel extends CalendarModel implements ICalendarVisitor
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoCalendarEvent> load()
	{
		 LocalDate start = this.getStart();
		 LocalDate end = this.getEnd();
		
		return CalendarDAO.getEvents(start, end);
	}

	// ICalendarVisitor //
	@Override
	public void visit(CalendarEvent event)
	{
		//you can set additional properties to each event retrieved by #load() here
	}
}
