/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.calendar6;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.ui.calendar6.Calendar;
import org.wicketstuff.jquery.ui.calendar6.CalendarView;
import org.wicketstuff.jquery.ui.calendar6.DateTimeDelta;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.samples.component.DemoCalendar6Dialog;
import org.wicketstuff.jquery.ui.samples.data.DemoCalendar6Event;
import org.wicketstuff.jquery.ui.samples.data.DemoCalendar6Model;
import org.wicketstuff.jquery.ui.samples.data.dao.Calendar6DAO;
import org.wicketstuff.jquery.ui.widget.dialog.DialogButton;

public class ExtendedCalendar6Page extends AbstractCalendar6Page // NOSONAR
{
	private static final long serialVersionUID = 1L;

	private Calendar calendar;

	public ExtendedCalendar6Page()
	{
		// Form //
		final Form<Date> form = new Form<Date>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Dialog //
		final DemoCalendar6Dialog dialog = new DemoCalendar6Dialog("dialog", "Event details") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target, DialogButton button)
			{
				DemoCalendar6Event event = this.getModelObject();

				// new event //
				if (Calendar6DAO.isNew(event))
				{
					Calendar6DAO.addEvent(event);
				}

				calendar.refresh(target); // use calendar.refresh(target) instead of target.add(calendar)
			}
		};

		this.add(dialog);

		// Calendar //
		Options options = new Options()
				.set("headerToolbar", "{start: 'title', end: 'dayGridMonth,timeGridWeek,timeGridDay today prev,next'}");

		this.calendar = new Calendar("calendar", new DemoCalendar6Model(), options) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isSelectable()
			{
				return true;
			}

			@Override
			public boolean isDateClickEnabled()
			{
				return true;
			}

			@Override
			public boolean isEventClickEnabled()
			{
				return true;
			}

			@Override
			public boolean isEventDropEnabled()
			{
				return true;
			}

			@Override
			public boolean isEventResizeEnabled()
			{
				return true;
			}

			@Override
			public boolean isViewDidMountEnabled()
			{
				return true;
			}

			@Override
			public void onDateClick(AjaxRequestTarget target, CalendarView view, LocalDateTime date, boolean allDay)
			{
				DemoCalendar6Event event = Calendar6DAO.newEvent(date);
				event.setEnd(date);

				dialog.setModelObject(event);
				dialog.open(target);
			}

			@Override
			public void onSelect(AjaxRequestTarget target, CalendarView view, LocalDateTime start, LocalDateTime end, boolean allDay)
			{
				DemoCalendar6Event event = Calendar6DAO.newEvent(start, end);
				event.setAllDay(allDay);

				dialog.setModelObject(event);
				dialog.open(target);
			}

			@Override
			public void onEventClick(AjaxRequestTarget target, CalendarView view, String eventId)
			{
				DemoCalendar6Event event = Calendar6DAO.getEvent(eventId);

				if (event != null)
				{
					dialog.setModelObject(event);
					dialog.open(target);
				}
			}

			@Override
			public void onEventDrop(AjaxRequestTarget target, String eventId, DateTimeDelta delta, boolean allDay)
			{
				DemoCalendar6Event event = Calendar6DAO.getEvent(eventId);

				if (event != null)
				{
					event.setStart(event.getStart() == null
							? null
							: event.getStart()
									.plus(delta.years(), ChronoUnit.YEARS)
									.plus(delta.months(), ChronoUnit.MONTHS)
									.plus(delta.days(), ChronoUnit.DAYS)
									.plus(delta.millis(), ChronoUnit.MILLIS)); // recompute start date
					event.setEnd(event.getEnd() == null
							? null
							: event.getEnd()
									.plus(delta.years(), ChronoUnit.YEARS)
									.plus(delta.months(), ChronoUnit.MONTHS)
									.plus(delta.days(), ChronoUnit.DAYS)
									.plus(delta.millis(), ChronoUnit.MILLIS)); // recompute end date
					event.setAllDay(allDay);

					this.info(String.format("%s changed to %s", event.getTitle(), event.getStart()));
					target.add(feedback);
				}
			}

			@Override
			public void onEventResize(AjaxRequestTarget target, String eventId, DateTimeDelta delta)
			{
				DemoCalendar6Event event = Calendar6DAO.getEvent(eventId);

				if (event != null)
				{
					LocalDateTime date = event.getEnd() == null ? event.getStart() : event.getEnd();
					event.setEnd(date
							.plus(delta.years(), ChronoUnit.YEARS)
							.plus(delta.months(), ChronoUnit.MONTHS)
							.plus(delta.days(), ChronoUnit.DAYS)
							.plus(delta.millis(), ChronoUnit.MILLIS));

					this.info(String.format("%s now ends the %s", event.getTitle(), event.getEnd()));
					target.add(feedback);
				}
			}
		};

		form.add(this.calendar);
	}
}
