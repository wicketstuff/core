package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.calendar.Calendar;
import com.googlecode.wicket.jquery.ui.calendar.EventSource.GoogleCalendar;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultCalendarPage extends AbstractCalendarPage
{
	private static final long serialVersionUID = 1L;

	public DefaultCalendarPage()
	{
		// FeedbackPanel //
		this.add(new JQueryFeedbackPanel("feedback"));

		// Calendar //
		Options options = new Options();
		options.set("theme", true);
		options.set("googleCalendarApiKey", Options.asString(GCAL_API_KEY));

		Calendar calendar = new Calendar("calendar", options);
		this.add(calendar);

		GoogleCalendar gcal1 = new GoogleCalendar("qde8vmooe48vsm1ma3i9je88q8@group.calendar.google.com");
		gcal1.setColor("#993366");
		/* gcal1.setGoogleCalendarApiKey(GCAL_API_KEY); */
		calendar.addSource(gcal1);

		GoogleCalendar gcal2 = new GoogleCalendar("nerseigospses068jd57bk5ar8@group.calendar.google.com" /*, GCAL_API_KEY*/);
		gcal2.setColor("#cc6666");
		calendar.addSource(gcal2);
	}
}
