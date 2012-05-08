package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import java.util.Date;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

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
		final Form<Date> form = new Form<Date>("form", new Model<Date>());
		this.add(form);

		// FeedbackPanel //
		form.add(new FeedbackPanel("feedback"));

		// Calendar //
		Calendar calendar = new Calendar("calendar", null);
		form.add(calendar);

		calendar.setOption("theme", true);
		calendar.setOption("weekend", true);
		calendar.addFeed("https://www.google.com/calendar/feeds/qde8vmooe48vsm1ma3i9je88q8%40group.calendar.google.com/public/basic", "event-duchesse");
	}
}
