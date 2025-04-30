/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.calendar6;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.lang.Generics;

import org.wicketstuff.jquery.ui.calendar6.Calendar;
import org.wicketstuff.jquery.ui.calendar6.CalendarEvent;
import org.wicketstuff.jquery.ui.calendar6.CalendarModel;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;

public class CustomCalendar6Page extends AbstractCalendar6Page
{
	private static final long serialVersionUID = 1L;

	private final List<CalendarEvent> events;

	public CustomCalendar6Page()
	{
		this.events = Generics.newArrayList();
		this.events.add(new CalendarEvent("1", "Today's event", LocalDateTime.now()));
		this.events.add(new CalendarEvent("2", "Another event", LocalDateTime.now()));

		this.initialize();
	}

	private void initialize()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Calendar (read only) //
		this.add(new Calendar("calendar", this.newCalendarModel()));
	}

	private CalendarModel newCalendarModel()
	{
		return new CalendarModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<CalendarEvent> load()
			{
				return events;
			}
		};
	}
}
