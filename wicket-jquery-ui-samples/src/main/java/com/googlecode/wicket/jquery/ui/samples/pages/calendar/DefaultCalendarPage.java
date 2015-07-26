package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.calendar.Calendar;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultCalendarPage extends AbstractCalendarPage
{
	private static final long serialVersionUID = 1L;

	public DefaultCalendarPage()
	{
		// FeedbackPanel //
		this.add(new JQueryFeedbackPanel("feedback"));

		// Calendar //
		Calendar calendar = new Calendar("calendar", new Options("theme", true));
		calendar.setGoogleCalendarApiKey("no-key"); // FIXME: correct KEY should be set here
		calendar.addFeed("qde8vmooe48vsm1ma3i9je88q8@group.calendar.google.com", "event-duchesse");
		this.add(calendar);
	}
}
