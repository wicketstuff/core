package com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.lang.Generics;

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
