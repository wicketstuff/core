package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.calendar.Calendar;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.DemoCalendarDialog;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarDAO;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarEvent;
import com.googlecode.wicket.jquery.ui.samples.data.DemoCalendarModel;

public class ExtendedCalendarPage extends AbstractCalendarPage
{
	private static final long serialVersionUID = 1L;

	private Calendar calendar;

	public ExtendedCalendarPage()
	{
		this.init();
	}

	private void init()
	{
		// Form //
		final Form<Date> form = new Form<Date>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Dialog //
		final DemoCalendarDialog dialog = new DemoCalendarDialog("dialog", "Event details") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target)
			{
				DemoCalendarEvent event = this.getModelObject();

				// new event //
				if (DemoCalendarDAO.isNew(event))
				{
					DemoCalendarDAO.addEvent(event);
				}

				calendar.refresh(target); //use calendar.refresh(target) instead of target.add(calendar)
			}
		};

		this.add(dialog);

		// Calendar //
		Options options = new Options();
		options.set("theme", true);
		options.set("header", "{ left: 'title', right: 'month,agendaWeek,agendaDay, today, prev,next' }");

		this.calendar = new Calendar("calendar", new DemoCalendarModel(), options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isSelectable()
			{
				return true;
			}

			@Override
			protected boolean isEditable()
			{
				return true;
			}

			@Override
			protected boolean isEventDropEnabled()
			{
				return true;
			}

			@Override
			protected boolean isEventResizeEnabled()
			{
				return true;
			}

			@Override
			protected void onDayClick(AjaxRequestTarget target, Date date)
			{
				DemoCalendarEvent event = DemoCalendarDAO.emptyEvent(date);

				dialog.setModelObject(event);
				dialog.open(target);
			}

			@Override
			protected void onSelect(AjaxRequestTarget target, Date start, Date end, boolean allDay)
			{
				DemoCalendarEvent event = DemoCalendarDAO.emptyEvent(start, end);
				event.setAllDay(allDay);

				dialog.setModelObject(event);
				dialog.open(target);
			}

			@Override
			protected void onEventClick(AjaxRequestTarget target, int eventId)
			{
				DemoCalendarEvent event = DemoCalendarDAO.getEvent(eventId);

				if (event != null)
				{
					dialog.setModelObject(event);
					dialog.open(target);
				}
			}

			@Override
			protected void onEventDrop(AjaxRequestTarget target, int eventId, long delta, boolean allDay)
			{
				DemoCalendarEvent event = DemoCalendarDAO.getEvent(eventId);

				if (event != null)
				{
					event.setStart(event.getStart() != null ? new Date(event.getStart().getTime() + delta) : null);	//recompute start date
					event.setEnd(event.getEnd() != null ? new Date(event.getEnd().getTime() + delta) : null);	// recompute end date
					event.setAllDay(allDay);

					this.info(String.format("%s changed to %s", event.getTitle(), event.getStart()));
					target.add(feedback);
				}
			}

			@Override
			protected void onEventResize(AjaxRequestTarget target, int eventId, long delta)
			{
				DemoCalendarEvent event = DemoCalendarDAO.getEvent(eventId);

				if (event != null)
				{
					Date date = (event.getEnd() == null ? event.getStart() : event.getEnd());
					event.setEnd(new Date(date.getTime() + delta));

					this.info(String.format("%s now ends the %s", event.getTitle(), event.getEnd()));
					target.add(feedback);
				}
			}
		};

		form.add(this.calendar);
	}
}
