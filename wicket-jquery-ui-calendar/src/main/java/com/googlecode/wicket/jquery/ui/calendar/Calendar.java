/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.jquery.ui.calendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.string.Strings;
import org.threeten.bp.LocalDateTime;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides calendar widget, based on the jQuery fullcalendar plugin.
 *
 * @author Sebastien Briquet - sebfz1
 * @author Martin Grigorov - martin-g
 *
 */
public class Calendar extends JQueryContainer implements ICalendarListener
{
	private static final long serialVersionUID = 1L;
	private static final String MISSING_API_KEY = "No Google Calendar API Key has been supplied";

	private final Options options;
	private Map<CharSequence, String> gcals;
	private String gcalApiKey = null; // TODO this might be per calendar option !!!!
	private CalendarModelBehavior modelBehavior; // events load

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options {@link Options}. Note that 'selectable' and 'selectHelper' options are set by overriding {@link #isSelectable()} (default is false)
	 */
	public Calendar(String id, Options options)
	{
		super(id);

		this.options = options;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link CalendarModel}
	 */
	public Calendar(String id, CalendarModel model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link CalendarModel}
	 * @param options {@link Options}. Note that 'selectable' and 'selectHelper' options are set by overriding {@link #isSelectable()} (default is false)
	 */
	public Calendar(String id, CalendarModel model, Options options)
	{
		super(id, model);

		this.options = options;
	}

	/**
	 * Gets the calendar's model
	 *
	 * @return a {@link CalendarModel}
	 */
	public CalendarModel getModel()
	{
		return (CalendarModel) this.getDefaultModel();
	}

	// Methods //
	/**
	 * Sets the Google Calendar API key<br/>
	 * 
	 * @return the API key
	 * @see <a href="http://fullcalendar.io/docs/google_calendar/">http://fullcalendar.io/docs/google_calendar/</a>
	 */
	public String getGoogleCalendarApiKey()
	{
		return this.gcalApiKey;
	}

	/**
	 * Sets the Google Calendar API key<br/>
	 * 
	 * @param key the API key
	 * @see <a href="http://fullcalendar.io/docs/google_calendar/">http://fullcalendar.io/docs/google_calendar/</a>
	 */
	public void setGoogleCalendarApiKey(String key)
	{
		this.gcalApiKey = key;
	}

	/**
	 * Adds a Google Calendar Feed
	 *
	 * @param gcal url to xml feed
	 */
	public synchronized void addFeed(CharSequence gcal)
	{
		this.addFeed(gcal, "");
	}

	/**
	 * Adds a Google Calendar Feed
	 *
	 * @param gcal url to xml feed
	 * @param className css class to be used
	 */
	public void addFeed(CharSequence gcal, String className)
	{
		if (this.gcals == null)
		{
			synchronized (this)
			{
				this.gcals = new HashMap<CharSequence, String>();
			}
		}

		this.gcals.put(gcal, className);
	}

	/**
	 * Refetches and refreshes the events currently available in the selected view.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void refresh(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("jQuery('%s').fullCalendar('refetchEvents');", JQueryWidget.getSelector(this)));
	}

	// Properties //
	@Override
	public boolean isSelectable()
	{
		return false;
	}

	@Override
	public boolean isDayClickEnabled()
	{
		return false;
	}

	@Override
	public boolean isEventClickEnabled()
	{
		return false;
	}

	@Override
	public boolean isEventDropEnabled()
	{
		return false;
	}

	@Override
	public boolean isEventResizeEnabled()
	{
		return false;
	}

	@Override
	public boolean isObjectDropEnabled()
	{
		return false;
	}

	@Override
	public boolean isViewRenderEnabled()
	{
		return false;
	}

	@Override
	public CharSequence getEventDropPrecondition()
	{
		return "";
	}

	@Override
	public CharSequence getEventResizePrecondition()
	{
		return "";
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.modelBehavior = this.newCalendarModelBehavior(this.getModel()));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		// builds sources //
		StringBuilder sourceBuilder = new StringBuilder();
		sourceBuilder.append(String.format("{url: '%s'}", Calendar.this.modelBehavior.getCallbackUrl()));

		if (Calendar.this.gcals != null)
		{
			if (Strings.isEmpty(this.gcalApiKey))
			{
				throw new WicketRuntimeException(MISSING_API_KEY);
			}
			
			behavior.setOption("googleCalendarApiKey", Options.asString(Calendar.this.gcalApiKey));

			for (Entry<CharSequence, String> gcal : Calendar.this.gcals.entrySet())
			{
				sourceBuilder.append(", ");
				sourceBuilder.append(String.format("{ googleCalendarId: '%s', className: '%s' }", gcal.getKey(), gcal.getValue()));
			}
		}

		behavior.setOption("eventSources", String.format("[%s]", sourceBuilder.toString()));
	}

	@Override
	public void onSelect(AjaxRequestTarget target, CalendarView view, LocalDateTime start, LocalDateTime end, boolean allDay)
	{
		// noop
	}

	@Override
	public void onDayClick(AjaxRequestTarget target, CalendarView view, LocalDateTime date, boolean allDay)
	{
		// noop
	}

	@Override
	public void onEventClick(AjaxRequestTarget target, CalendarView view, int eventId)
	{
		// noop
	}

	@Override
	public void onEventDrop(AjaxRequestTarget target, int eventId, long delta, boolean allDay)
	{
		// noop
	}

	@Override
	public void onEventResize(AjaxRequestTarget target, int eventId, long delta)
	{
		// noop
	}

	@Override
	public void onObjectDrop(AjaxRequestTarget target, String title, LocalDateTime date, boolean allDay)
	{
		// noop
	}

	@Override
	public void onViewRender(AjaxRequestTarget target, CalendarView view, LocalDateTime start, LocalDateTime end)
	{
		// noop
	}

	// IJQueryWidget //
	/**
	 * see {@link JQueryContainer#newWidgetBehavior(String)}
	 */
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new CalendarBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isSelectable()
			{
				return Calendar.this.isSelectable();
			}

			@Override
			public boolean isDayClickEnabled()
			{
				return Calendar.this.isDayClickEnabled();
			}

			@Override
			public boolean isEventClickEnabled()
			{
				return Calendar.this.isEventClickEnabled();
			}

			@Override
			public boolean isEventDropEnabled()
			{
				return Calendar.this.isEventDropEnabled();
			}

			@Override
			public boolean isEventResizeEnabled()
			{
				return Calendar.this.isEventResizeEnabled();
			}

			@Override
			public boolean isObjectDropEnabled()
			{
				return Calendar.this.isObjectDropEnabled();
			}

			@Override
			public boolean isViewRenderEnabled()
			{
				return Calendar.this.isViewRenderEnabled();
			}

			@Override
			public CharSequence getEventDropPrecondition()
			{
				return Calendar.this.getEventDropPrecondition();
			}

			@Override
			public CharSequence getEventResizePrecondition()
			{
				return Calendar.this.getEventResizePrecondition();
			}

			@Override
			public void onSelect(AjaxRequestTarget target, CalendarView view, LocalDateTime start, LocalDateTime end, boolean allDay)
			{
				Calendar.this.onSelect(target, view, start, end, allDay);
			}

			@Override
			public void onDayClick(AjaxRequestTarget target, CalendarView view, LocalDateTime date, boolean allDay)
			{
				Calendar.this.onDayClick(target, view, date, allDay);
			}

			@Override
			public void onEventClick(AjaxRequestTarget target, CalendarView view, int eventId)
			{
				Calendar.this.onEventClick(target, view, eventId);
			}

			@Override
			public void onEventDrop(AjaxRequestTarget target, int eventId, long delta, boolean allDay)
			{
				Calendar.this.onEventDrop(target, eventId, delta, allDay);
			}

			@Override
			public void onEventResize(AjaxRequestTarget target, int eventId, long delta)
			{
				Calendar.this.onEventResize(target, eventId, delta);
			}

			@Override
			public void onObjectDrop(AjaxRequestTarget target, String title, LocalDateTime date, boolean allDay)
			{
				Calendar.this.onObjectDrop(target, title, date, allDay);
			}

			@Override
			public void onViewRender(AjaxRequestTarget target, CalendarView view, LocalDateTime start, LocalDateTime end)
			{
				Calendar.this.onViewRender(target, view, start, end);
			}
		};
	}

	// Factory methods //

	/**
	 * Gets a new {@link CalendarModelBehavior}
	 *
	 * @param model the {@link CalendarModel}
	 * @return the {@link CalendarModelBehavior}
	 */
	protected CalendarModelBehavior newCalendarModelBehavior(final CalendarModel model)
	{
		return new CalendarModelBehavior(model);
	}
}
