package com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar6;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.calendar6.Calendar;
import com.googlecode.wicket.jquery.ui.calendar6.CalendarEvent;
import com.googlecode.wicket.jquery.ui.calendar6.CalendarModel;
import com.googlecode.wicket.jquery.ui.calendar6.EventObject;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ObjectCalendar6Page extends AbstractCalendar6Page
{
	private static final long serialVersionUID = 1L;

	private final List<CalendarEvent> events;
	private final List<MyEvent> objects;

	public ObjectCalendar6Page()
	{
		this.events = Generics.newArrayList();

		this.objects = Generics.newArrayList();
		this.objects.add(new MyEvent("event #1"));
		this.objects.add(new MyEvent("event #2"));

		this.initialize();
	}

	// Methods //

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
			view.add(new EventObject(view.newChildId(), event.toString())
			{
				private static final long serialVersionUID = 1L;

				@Override
				public Options onConfigure(Options options) {
					return options.set("create", false);
				}
			});
		}

		// Calendar //
		Options options = new Options("revertDuration", 0)
				.set("timeZone", Options.asString("UTC"));
		this.add(new Calendar("calendar", this.newCalendarModel(), options) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isObjectDropEnabled()
			{
				return true;
			}

			@Override
			public void onObjectDrop(AjaxRequestTarget target, String title, LocalDateTime date, boolean allDay)
			{
				CalendarEvent event = new CalendarEvent(title, date);
				event.setAllDay(allDay);

				events.add(event); // adds to DAO
				this.refresh(target);

				this.info(String.format("Added %s on %s", event.getTitle(), event.getStart()));
				target.add(feedback);
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(ObjectCalendar6Page.class));
	}

	// Factories //

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

	// Classes //

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
