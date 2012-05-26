package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.calendar.Calendar;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.CalendarDialog;
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
		final Form<Date> form = new Form<Date>("form", new Model<Date>());
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Dialog //
		final CalendarDialog<DemoCalendarEvent> dialog = new CalendarDialog<DemoCalendarEvent>("dialog", "Event details") {

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

				calendar.refresh(target); //use calendar.refresh() instead of target.add(calendar)
			}
		};

		this.add(dialog);

		// Calendar //
		this.calendar = new Calendar("calendar", new DemoCalendarModel(), new Options("theme", true)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isSelectable()
			{
				return false;
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
				super.onDayClick(target, date);

				this.info("Selected date: " + date);
				target.add(feedback);

				dialog.setModelObject(DemoCalendarDAO.emptyEvent(date));
				dialog.open(target);
			}

			@Override
			protected void onSelect(AjaxRequestTarget target, Date start, Date end, boolean allDay)
			{
				super.onSelect(target, start, end, allDay);
				
				DemoCalendarEvent event = DemoCalendarDAO.emptyEvent(start, end);
				event.setAllDay(allDay);

				dialog.setModelObject(event);
				dialog.open(target);
			}

			@Override
			protected void onEventClick(AjaxRequestTarget target, int eventId)
			{
				super.onEventClick(target, eventId);

				DemoCalendarEvent event = DemoCalendarDAO.getEvent(eventId);

				if (event != null)
				{
					dialog.setModelObject(event);
					dialog.open(target);
				}
			}

			@Override
			protected void onEventDrop(AjaxRequestTarget target, int eventId, long delta, boolean isAllDay)
			{
				super.onEventDrop(target, eventId, delta, isAllDay);

				DemoCalendarEvent event = DemoCalendarDAO.getEvent(eventId);

				if (event != null)
				{
					event.setStart(event.getStart() != null ? new Date(event.getStart().getTime() + delta) : null);
					event.setEnd(event.getEnd() != null ? new Date(event.getEnd().getTime() + delta) : null);
					
					this.info(String.format("%s changed to %s", event.getTitle(), event.getStart()));
					target.add(feedback);
				}
			}

			@Override
			protected void onEventResize(AjaxRequestTarget target, int eventId, long delta)
			{
				super.onEventResize(target, eventId, delta);

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
