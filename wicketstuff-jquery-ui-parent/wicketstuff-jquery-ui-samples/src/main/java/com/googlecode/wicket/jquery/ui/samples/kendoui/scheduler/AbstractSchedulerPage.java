package com.googlecode.wicket.jquery.ui.samples.kendoui.scheduler;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractSchedulerPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultSchedulerPage.class, "Scheduler"), // lf
				new DemoLink(SingleResourceSchedulerPage.class, "Scheduler, with resources"), // lf
				new DemoLink(MultipleResourceSchedulerPage.class, "Scheduler, with multiple resource types") // lf
		);
	}
}
