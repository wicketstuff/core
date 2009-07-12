/*
 * Copyright 2009 Michael Würtinger (mwuertinger@users.sourceforge.net)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.ddcalendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.wicketstuff.yui.behavior.dragdrop.YuiDDTarget;


public abstract class WicketCalendar<T extends CalendarEvent> extends Panel {
	private class CalendarDDTarget extends YuiDDTarget {
		private static final long serialVersionUID = 1L;
		private final WicketCalendar<T> calendar;
		private final int dayOfWeek;
		private final int hourOfDay;
		
		public CalendarDDTarget(final  WicketCalendar<T> calendar, final int dayOfWeek, final int hourOfDay) {
			super(DD_GROUP);
			this.calendar = calendar;
			this.dayOfWeek = dayOfWeek;
			this.hourOfDay = hourOfDay;
		}

		@Override
		public void onDrop(AjaxRequestTarget target, Component component) {
			@SuppressWarnings("unchecked")
			T object = (T) component.getDefaultModelObject();
			calendar.onDrop(dayOfWeek, hourOfDay, object, target);
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(WicketCalendar.class);

	public static final String DD_GROUP = WicketCalendar.class.getName();
	
	private final int hourHeight;
	private final List<String> hourLabels;
	private final CalendarModel model;
	private final WebMarkupContainer calendar;
	private final ListView<CalendarEvent>[] dayEventContainers = new ListView[7];

	public WicketCalendar(final String id, final CalendarModel model, final int hourHeight, final List<String> hourLabels, boolean showWeekNavigation) {
		super(id);
		setOutputMarkupId(true);
		
		this.model = model;
		this.hourHeight = hourHeight;
		this.hourLabels = hourLabels;
		
		calendar = new WebMarkupContainer("calendar");
		add(calendar);
		
		if(showWeekNavigation)
			addWeekNavigationBar();
		else
			addWeekNavigationBarDummy();
		
		addCalendarHead();
		addCalendarBody();
	}
	
	private void addWeekNavigationBar() {
		final Label weekLabel = new Label("weekLabel", new LoadableDetachableModel<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected String load() {
				final DateTimeFormatter weekFormatter = DateTimeFormat.forPattern("d.M.");
				
				String firstDay = weekFormatter.print(model.getWeek().getFirstDay());
				String lastDay = weekFormatter.print(model.getWeek().getLastDay());
				
				return String.format("Woche %d/%d (%s - %s)", model.getWeek().getWeek(), model.getWeek().getYear(), firstDay, lastDay);
			}
		});
		
		weekLabel.setOutputMarkupId(true);
		calendar.add(weekLabel);

		calendar.add(new AjaxFallbackLink<String>("weekBack") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				model.setPreviousWeek();
				if(target != null) {
					target.addComponent(weekLabel);
					target.addComponent(WicketCalendar.this);
				}
			}
		});
		
		calendar.add(new AjaxFallbackLink<String>("weekNext") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				model.setNextWeek();
				if(target != null) {
					target.addComponent(weekLabel);
					target.addComponent(WicketCalendar.this);
				}
			}
		});
	}
	
	protected void addWeekNavigationBarDummy() {
		calendar.add(new WebMarkupContainer("weekLabel").setVisible(false));
		calendar.add(new WebMarkupContainer("weekBack").setVisible(false));
		calendar.add(new WebMarkupContainer("weekNext").setVisible(false));		
	}

	private void addCalendarHead() {
		WebMarkupContainer calendarHead = new WebMarkupContainer("calendarHead");
		calendar.add(calendarHead);
		
		RepeatingView dayHeads = new RepeatingView("dayHead");
		calendarHead.add(dayHeads);
		
		final DateTimeFormatter dayOfWeekFormatter = DateTimeFormat.forPattern("E");
		
		for(int i=0; i<7; i++)
			dayHeads.add(new Label(""+i, dayOfWeekFormatter.print(model.getWeek().getFirstDay().plusDays(i))));
	}

	private void addCalendarBody() {
		WebMarkupContainer calendarBody = new WebMarkupContainer("calendarBody");
		calendar.add(calendarBody);

		addHourLabels(calendarBody);
		addDays(calendarBody);
	}
	
	private void addHourLabels(WebMarkupContainer calendarBody) {
		WebMarkupContainer calendarTime = new WebMarkupContainer("calendarTime");
		calendarBody.add(calendarTime);

		RepeatingView calendarTimeEntries = new RepeatingView("calendarTimeEntry");
		calendarTime.add(calendarTimeEntries);

		int hour = 0;
		for(String hourLabel : hourLabels) {
			calendarTimeEntries.add(new Label(""+hour, hourLabel).add(new SimpleAttributeModifier("style", "height: "+hourHeight+"px")));
			hour++;
		}
	}
	
	private void addDays(WebMarkupContainer calendarBody) {
		RepeatingView days = new RepeatingView("day");
		calendarBody.add(days);
		
		// Create days
		for(int day=0; day<7; day++) {
			final int currentDay = day;
			WebMarkupContainer dayContainer = new WebMarkupContainer(""+day);
			days.add(dayContainer);
			
			RepeatingView dayHours = new RepeatingView("dayHour");
			dayContainer.add(dayHours);
			
			// Create hours
			for(int hour=0; hour<hourLabels.size(); hour++) {
				final WebMarkupContainer dayHour = new WebMarkupContainer(""+hour);
				dayHour.setOutputMarkupId(true);
				dayHour.add(new SimpleAttributeModifier("style", "height: "+hourHeight+"px"));
				dayHour.add(new CalendarDDTarget(this, day, hour));
				dayHours.add(dayHour);
			}
			
			IModel<List<CalendarEvent>> dayEventsModel = new LoadableDetachableModel<List<CalendarEvent>>() {
				private static final long serialVersionUID = 1L;

				@Override
				protected List<CalendarEvent> load() {
					Set<CalendarEvent> weekEvents = model.getEventsForCurrentWeek();
					List<CalendarEvent> dayEvents = new ArrayList<CalendarEvent>();
					for(CalendarEvent event : weekEvents) {
						if(currentDay+1 == event.getDay())
							dayEvents.add(event);
					}
					return dayEvents;
				}
			};
			
			dayEventContainers[day] = new ListView<CalendarEvent>("dayEvent", dayEventsModel) {
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<CalendarEvent> item) {
					@SuppressWarnings("unchecked")
					T event = (T) item.getDefaultModelObject();
					EventPanel<T> eventPanel = new EventPanel<T>("eventPanel", WicketCalendar.this, event, hourHeight, true);
					item.add(eventPanel);
				}
			};
			
			dayContainer.add(dayEventContainers[day]);
		}
	}
	
	protected abstract void onDrop(final int dayOfWeek, final int hourOfDay, final T object, final AjaxRequestTarget target);
}
