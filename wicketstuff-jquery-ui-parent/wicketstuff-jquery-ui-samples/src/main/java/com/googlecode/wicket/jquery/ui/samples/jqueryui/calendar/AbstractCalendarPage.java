package com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractCalendarPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	/** Wicket jQuery UI Google Calendar API Key */
	static final String GCAL_API_KEY = "AIzaSyD9QzHiSkjGAZ0ajGe-okoxWQ07H39jksU";

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultCalendarPage.class, "Calendar: event sources"), // lf
				new DemoLink(CustomCalendarPage.class, "Calendar: custom events"), // lf
				new DemoLink(ObjectCalendarPage.class, "Calendar: event objects"), // lf
				new DemoLink(ExtendedCalendarPage.class, "Calendar: <b>full demo</b>") // lf
		);
	}
}
