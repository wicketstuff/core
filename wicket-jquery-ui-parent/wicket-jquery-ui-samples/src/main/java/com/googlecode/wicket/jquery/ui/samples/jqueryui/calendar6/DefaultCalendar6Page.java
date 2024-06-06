package com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar6;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.calendar6.Calendar;
import com.googlecode.wicket.jquery.ui.calendar6.EventSource.GoogleCalendar;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultCalendar6Page extends AbstractCalendar6Page
{
	private static final long serialVersionUID = 1L;

	public DefaultCalendar6Page()
	{
		// FeedbackPanel //
		this.add(new JQueryFeedbackPanel("feedback"));

		// Calendar //
		Options options = new Options();
		//options.set("googleCalendarApiKey", Options.asString(GCAL_API_KEY));
		options.set("googleCalendarApiKey", Options.asString("AIzaSyA1CBXoDsLDmv-lbMXUjTLjc-IPkKbYFLM"));

		Calendar calendar = new Calendar("calendar", options) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isSelectable()
			{
				return true;
			}

			@Override
			public boolean isEventDropEnabled()
			{
				return true;
			}

			@Override
			public boolean isEventResizeEnabled()
			{
				return true;
			}
		};

		this.add(calendar);

		/*
		GoogleCalendar gcal1 = new GoogleCalendar("qde8vmooe48vsm1ma3i9je88q8@group.calendar.google.com");
		/* gcal1.setGoogleCalendarApiKey(GCAL_API_KEY); */
		/*calendar.addSource(gcal1.setColor("#993366"));

		GoogleCalendar gcal2 = new GoogleCalendar("nerseigospses068jd57bk5ar8@group.calendar.google.com" /* , GCAL_API_KEY *//* );
		/*calendar.addSource(gcal2.setColor("#cc6666"));

		GoogleCalendar gcal3 = new GoogleCalendar("p2v08r7sgvos2dnkkchreur9u8@group.calendar.google.com"); // Wicket jQuery UI Calendar
		gcal3.setEditable(true);
		gcal3.setColor("#6666cc");
		calendar.addSource(gcal3);
		*/
		// Public, Football: Angelo State Rams
		GoogleCalendar gcal1 = new GoogleCalendar("6meogmd7saubf676hmcemcen7g@group.calendar.google.com");
		calendar.addSource(gcal1.setColor("#993366"));
	}
}
