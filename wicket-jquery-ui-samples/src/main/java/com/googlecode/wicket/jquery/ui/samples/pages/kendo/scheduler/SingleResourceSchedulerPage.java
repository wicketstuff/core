package com.googlecode.wicket.jquery.ui.samples.pages.kendo.scheduler;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler.ResourceEventsDAO;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.scheduler.Scheduler;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerModel;
import com.googlecode.wicket.kendo.ui.scheduler.resource.Resource;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;

public class SingleResourceSchedulerPage extends AbstractSchedulerPage
{
	private static final long serialVersionUID = 1L;

	public SingleResourceSchedulerPage()
	{
		// Form //
		final Form<Date> form = new Form<Date>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Scheduler //
		Options options = new Options();
		options.set("date", "Date.now()");
		options.set("editable", true); // default
		options.set("views", "[ { type: 'day', showWorkHours: true }, { type: 'week', showWorkHours: true }, { type: 'month', selected: true } ]");
		options.set("workDayStart", "new Date('2014/1/1 08:00 AM')");
		options.set("workDayEnd", "new Date('2014/1/1 6:00 PM')");

		form.add(new Scheduler("scheduler", newSchedulerModel(), newResourceList(), options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onCreate(AjaxRequestTarget target, SchedulerEvent event)
			{
				ResourceEventsDAO.get().create(event);

				this.info("Created: " + event);
				target.add(feedback);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, SchedulerEvent event)
			{
				ResourceEventsDAO.get().update(event);

				this.info("Updated: " + event);
				target.add(feedback);
			}

			@Override
			public void onDelete(AjaxRequestTarget target, SchedulerEvent event)
			{
				ResourceEventsDAO.get().delete(event);

				this.info("Deleted: " + event);
				target.add(feedback);
			}
		});
	}

	// Factories //

	static SchedulerModel newSchedulerModel()
	{
		// ISchedulerVisitor
		return new SchedulerModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<SchedulerEvent> load()
			{
				return ResourceEventsDAO.get().getEvents(this.getStart(), this.getEnd());
			}
		};
	}

	static ResourceList newResourceList()
	{
		ResourceList list = new ResourceList("Agenda", "agendaId");
		list.add(new Resource(1, "Sebastien", "#6699cc"));
		list.add(new Resource(2, "Emilie", "#cc6699"));

		return list;
	}
}
