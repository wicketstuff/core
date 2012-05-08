package com.googlecode.wicket.jquery.ui.samples.pages.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.calendar.Calendar;
import com.googlecode.wicket.jquery.ui.calendar.CalendarEvent;
import com.googlecode.wicket.jquery.ui.calendar.CalendarModel;
import com.googlecode.wicket.jquery.ui.samples.component.CalendarDialog;

public class CustomCalendarPage extends AbstractCalendarPage
{
	private static final long serialVersionUID = 1L;

	private final List<CalendarEvent> events;
	
	public CustomCalendarPage()
	{
		this.events = new ArrayList<CalendarEvent>();
		this.events.add(new CalendarEvent(1, "Custom event", new Date()));
		this.events.add(new CalendarEvent(2, "Another event", new Date()));

		this.init();
	}

	private void init()
	{
		final Form<Date> form = new Form<Date>("form", new Model<Date>());
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));
		
		// Dialog //
		final CalendarDialog dialog = new CalendarDialog("dialog", "Event details") {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit(AjaxRequestTarget target)
			{
				//CalendarEvent event = this.getModelObject();
				
				target.add(form); //reloads the calendar with changes.
			}
		};

		this.add(dialog);

		// Calendar //
		Calendar calendar = new Calendar("calendar", this.newCalendarModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target, int eventId)
			{
				CalendarEvent event = null;

				// Code bellow is for demo purpose only.
				// You better have to retrieve the event from your data access layer (ie, DAO) instead of from the model
				for (CalendarEvent e : events)
				{
					if (e.getId() == eventId)
					{
						event = e;
						break;
					}
				}

				if (event != null)
				{
					dialog.setModelObject(event);
					dialog.open(target);
				}
			}
		};
		
		form.add(calendar);

		calendar.setOption("theme", true);
		calendar.setOption("weekend", true);
	}

	private CalendarModel newCalendarModel()
	{
		return new CalendarModel() {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected List<CalendarEvent> load()
			{
				// Loads events from the data access layer, using:
				// Date start = this.getStart();
				// Date end = this.getEnd();
				
				return events;
			}
		};
	}
}
