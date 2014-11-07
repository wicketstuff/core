package com.googlecode.wicket.jquery.ui.samples.pages.kendo.scheduler;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;

abstract class AbstractSchedulerPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractSchedulerPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultSchedulerPage.class, "Scheduler"),
				new DemoLink(SingleResourceSchedulerPage.class, "Scheduler, with resources"),
				new DemoLink(MultipleResourceSchedulerPage.class, "Scheduler, with multiple resource types")
			);
	}
}
