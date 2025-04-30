/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.data;

import java.util.List;

import org.wicketstuff.jquery.ui.calendar6.CalendarEvent;
import org.wicketstuff.jquery.ui.calendar6.CalendarModel;
import org.wicketstuff.jquery.ui.calendar6.ICalendarVisitor;
import org.wicketstuff.jquery.ui.samples.data.dao.Calendar6DAO;

public class DemoCalendar6Model extends CalendarModel implements ICalendarVisitor
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoCalendar6Event> load()
	{
		return Calendar6DAO.getEvents(this.getStart(), this.getEnd());
	}

	// ICalendarVisitor //
	@Override
	public void visit(CalendarEvent event)
	{
		//you can set additional properties to each event retrieved by #load() here
	}
}
