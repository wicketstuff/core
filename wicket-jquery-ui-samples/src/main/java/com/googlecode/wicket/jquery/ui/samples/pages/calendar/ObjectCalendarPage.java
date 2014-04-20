package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.calendar.Calendar;
import com.googlecode.wicket.jquery.ui.calendar.CalendarEvent;
import com.googlecode.wicket.jquery.ui.calendar.CalendarModel;
import com.googlecode.wicket.jquery.ui.calendar.EventObject;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ObjectCalendarPage extends AbstractCalendarPage
{
	private static final long serialVersionUID = 1L;

	private final List<CalendarEvent> events;
	private final List<MyEvent> objects;

	public ObjectCalendarPage()
	{
		this.events = new ArrayList<CalendarEvent>();

		this.objects = new ArrayList<MyEvent>();
		this.objects.add(new MyEvent("event #1"));
		this.objects.add(new MyEvent("event #2"));

		this.initialize();
	}

	private void initialize()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// EventObjects //
		RepeatingView view = new RepeatingView("object");
		this.add(view);

		for (MyEvent event : this.objects)
		{
			view.add(new EventObject(view.newChildId(), event.toString()) {

				private static final long serialVersionUID = 1L;

				@Override
				public void onConfigure(JQueryBehavior behavior)
				{
					super.onConfigure(behavior);

					// draggable options //
					behavior.setOption("revert", true);
					behavior.setOption("revertDuration", 0);
				}
			});
		}

		// Calendar //
		this.add(new Calendar("calendar", this.newCalendarModel(), new Options("theme", true)) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isObjectDropEnabled()
			{
				return true;
			}

			@Override
			public void onObjectDrop(AjaxRequestTarget target, String title, Date date, boolean allDay)
			{
				CalendarEvent event = new CalendarEvent(0, title, date);
				event.setAllDay(allDay);

				events.add(event); //adds to DAO
				this.refresh(target);

				this.info(String.format("Added %s on %s", event.getTitle(), event.getStart()));
				target.add(feedback);
			}
		});
	}

	private CalendarModel newCalendarModel()
	{
		return new CalendarModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<CalendarEvent> load()
			{
				return events;
			}
		};
	}

	/**
	 * Event object
	 */
	static class MyEvent implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		private String title;

		/**
		 * @param title
		 */
		public MyEvent(String title)
		{
			this.title = title;
		}

		@Override
		public String toString()
		{
			return this.title;
		}
	}
}
