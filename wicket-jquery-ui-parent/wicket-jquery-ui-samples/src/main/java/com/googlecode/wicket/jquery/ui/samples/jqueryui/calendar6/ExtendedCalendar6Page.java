package com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar6;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.calendar6.Calendar;
import com.googlecode.wicket.jquery.ui.calendar6.CalendarView;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.DemoCalendar6Dialog;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendar6Event;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendar6Model;
import com.googlecode.wicket.jquery.ui.samples.data.dao.Calendar6DAO;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;

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
				.set("headerToolbar", "{ left: 'title', right: 'dayGridMonth,timeGridWeek,timeGridDay, today, prev,next' }")
				.set("timeZone", Options.asString("UTC"));

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
			public void onEventDrop(AjaxRequestTarget target, String eventId, long delta, boolean allDay)
			{
				DemoCalendar6Event event = Calendar6DAO.getEvent(eventId);

				if (event != null)
				{
					event.setStart(event.getStart() != null ? event.getStart().plus(delta, ChronoUnit.MILLIS) : null); // recompute start date
					event.setEnd(event.getEnd() != null ? event.getEnd().plus(delta, ChronoUnit.MILLIS) : null); // recompute end date
					event.setAllDay(allDay);

					this.info(String.format("%s changed to %s", event.getTitle(), event.getStart()));
					target.add(feedback);
				}
			}

			@Override
			public void onEventResize(AjaxRequestTarget target, String eventId, long delta)
			{
				DemoCalendar6Event event = Calendar6DAO.getEvent(eventId);

				if (event != null)
				{
					LocalDateTime date = event.getEnd() == null ? event.getStart() : event.getEnd();
					event.setEnd(date.plus(delta, ChronoUnit.MILLIS));

					this.info(String.format("%s now ends the %s", event.getTitle(), event.getEnd()));
					target.add(feedback);
				}
			}
		};

		form.add(this.calendar);
	}
}
