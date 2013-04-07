package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.calendar.Calendar;
import com.googlecode.wicket.jquery.ui.calendar.CalendarEvent;
import com.googlecode.wicket.jquery.ui.calendar.CalendarModel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class CustomCalendarPage extends AbstractCalendarPage
{
	private static final long serialVersionUID = 1L;

	private final List<CalendarEvent> events;

	public CustomCalendarPage()
	{
		this.events = new ArrayList<CalendarEvent>();
		this.events.add(new CalendarEvent(1, "Today's event", new Date()));
		this.events.add(new CalendarEvent(2, "Another event", new Date()));

		this.init();
	}

	private void init()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Calendar (read only) //
		this.add(new Calendar("calendar", this.newCalendarModel(), new Options("theme", true)));
	}

	private CalendarModel newCalendarModel()
	{
		return new CalendarModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<CalendarEvent> load()
			{
				// Loads events from the data access layer, using:
				// Date start = this.getStart();
				// Date end = this.getEnd();

				return events;
			}
		};
	}
}
