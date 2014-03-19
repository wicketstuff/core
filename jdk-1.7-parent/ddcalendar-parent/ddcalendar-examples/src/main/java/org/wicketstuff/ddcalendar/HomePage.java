package org.wicketstuff.ddcalendar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;

/**
 * Homepage
 */
public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private List<MyCalendarEvent> unscheduledEvents = new ArrayList<MyCalendarEvent>();
	private List<MyCalendarEvent> scheduledEvents = new ArrayList<MyCalendarEvent>();

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public HomePage(final PageParameters parameters) {
    	unscheduledEvents.add(new MyCalendarEvent(this, "Item A"));
    	unscheduledEvents.add(new MyCalendarEvent(this, "Item B"));
    	unscheduledEvents.add(new MyCalendarEvent(this, "Item C"));
    	
		List<String> hours = new ArrayList<String>();
		for(int hour=0; hour<24; hour++)
			hours.add(String.format("%02d:00", hour));

		CalendarModel model = new CalendarModelImpl(new CalendarWeek()) {
			private static final long serialVersionUID = 1L;

			@Override
			public Set<CalendarEvent> getEvents(CalendarWeek week) {
				Set<CalendarEvent> events = new HashSet<CalendarEvent>();
				events.addAll(scheduledEvents);
				return events;
			}
		};
		
		add(new DDCalendarPanel<MyCalendarEvent>("ddcalendar", hours, 30, model, true, "MyItems", false) {
			private static final long serialVersionUID = 1L;

			@Override
			public List<MyCalendarEvent> getItems() {
				return unscheduledEvents;
				
			}

			@Override
			protected void onDrop(int dayOfWeek, int hourOfDay, MyCalendarEvent object, AjaxRequestTarget target) {
				object.schedule(dayOfWeek+1, hourOfDay);
				unscheduledEvents.remove(object);
				scheduledEvents.add(object);
				target.addComponent(this);
			}
		});
    }

	public void unschedule(MyCalendarEvent myCalendarEvent) {
		scheduledEvents.remove(myCalendarEvent);
		unscheduledEvents.add(myCalendarEvent);
	}
}
