package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractCalendarPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractCalendarPage()
	{

	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultCalendarPage.class, "Calendar: public feeds"),
				new DemoLink(CustomCalendarPage.class, "Calendar: custom events"),
				new DemoLink(ObjectCalendarPage.class, "Calendar: event objects"),
				new DemoLink(ExtendedCalendarPage.class, "Calendar: <b>full demo</b>")
			);
	}
}
