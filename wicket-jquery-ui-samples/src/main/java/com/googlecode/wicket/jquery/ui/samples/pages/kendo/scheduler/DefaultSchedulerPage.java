package com.googlecode.wicket.jquery.ui.samples.pages.kendo.scheduler;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler.SchedulerEventsDAO;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.scheduler.Scheduler;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;
import com.googlecode.wicket.kendo.ui.scheduler.SchedulerModel;
import com.googlecode.wicket.kendo.ui.scheduler.views.AgendaView;
import com.googlecode.wicket.kendo.ui.scheduler.views.DayView;
import com.googlecode.wicket.kendo.ui.scheduler.views.MonthView;
import com.googlecode.wicket.kendo.ui.scheduler.views.TimelineView;
import com.googlecode.wicket.kendo.ui.scheduler.views.WeekView;
import com.googlecode.wicket.kendo.ui.scheduler.views.WorkWeekView;

public class DefaultSchedulerPage extends AbstractSchedulerPage
{
	private static final long serialVersionUID = 1L;

	public DefaultSchedulerPage()
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
		options.set("views", // lf
				DayView.newInstance().setShowWorkHours(true), // lf
				WeekView.newInstance().setShowWorkHours(true), // lf
				WorkWeekView.newInstance(), // lf
				MonthView.newInstance().setSelected(true), // lf
				AgendaView.newInstance(), // lf
				TimelineView.newInstance().setShowWorkHours(true));

		options.set("workDayStart", "new Date('2014/1/1 08:00 AM')");
		options.set("workDayEnd", "new Date('2014/1/1 6:00 PM')");

		form.add(new Scheduler("scheduler", newSchedulerModel(), options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onCreate(AjaxRequestTarget target, SchedulerEvent event)
			{
				SchedulerEventsDAO.get().create(event);

				this.info(String.format("Created: %s, %s -%s", event, event.getStart(), event.getEnd()));
				target.add(feedback);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, SchedulerEvent event)
			{
				SchedulerEventsDAO.get().update(event);

				this.info(String.format("Updated: %s, %s -%s", event, event.getStart(), event.getEnd()));
				target.add(feedback);
			}

			@Override
			public void onDelete(AjaxRequestTarget target, SchedulerEvent event)
			{
				SchedulerEventsDAO.get().delete(event);

				this.info("Deleted: " + event);
				target.add(feedback);
			}
		});
	}

	// Factories //

	private static SchedulerModel newSchedulerModel()
	{
		// ISchedulerVisitor
		return new SchedulerModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<SchedulerEvent> load()
			{
				return SchedulerEventsDAO.get().getEvents(this.getStart(), this.getEnd());
			}
		};
	}
}
