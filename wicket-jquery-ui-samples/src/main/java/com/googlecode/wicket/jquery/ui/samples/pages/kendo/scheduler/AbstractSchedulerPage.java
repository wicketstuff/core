package com.googlecode.wicket.jquery.ui.samples.pages.kendo.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;
import com.googlecode.wicket.jquery.ui.samples.data.dao.SchedulerDAO;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerModel;

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
				new DemoLink(DefaultSchedulerPage.class, "Scheduler")
//				new DemoLink(ResourceSchedulerPage.class, "Scheduler, with resources")
			);
	}

	// Factories //
	static SchedulerModel newSchedulerModel()
	{
		//ISchedulerVisitor
		return new SchedulerModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<SchedulerEvent> load()
			{
				Date start = this.getStart();
				Date end = this.getEnd();

				return SchedulerDAO.get().getEvents(start, end);
			}
		};
	}
}
