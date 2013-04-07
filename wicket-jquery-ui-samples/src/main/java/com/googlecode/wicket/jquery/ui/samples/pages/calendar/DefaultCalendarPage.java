package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.calendar.Calendar;

public class DefaultCalendarPage extends AbstractCalendarPage
{
	private static final long serialVersionUID = 1L;

	public DefaultCalendarPage()
	{
		this.init();
	}

	private void init()
	{
		// FeedbackPanel //
		this.add(new FeedbackPanel("feedback"));

		// Calendar //
		Calendar calendar = new Calendar("calendar", new Options("theme", true));
		calendar.addFeed("https://www.google.com/calendar/feeds/qde8vmooe48vsm1ma3i9je88q8%40group.calendar.google.com/public/basic", "event-duchesse");
		this.add(calendar);
	}
}
