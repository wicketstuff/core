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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
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

	private final Options options;
	private Map<CharSequence, String> gcals; //TODO: move gcals to CalendarBehavior?
	private CalendarModelBehavior modelBehavior; // events load

	/**
	 * Constructor
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
	 * @param id the markup id
	 * @param model the {@link CalendarModel}
	 */
	public Calendar(String id, CalendarModel model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
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
	 * @return a {@link CalendarModel}
	 */
	public CalendarModel getModel()
	{
		return (CalendarModel) this.getDefaultModel();
	}

	// Methods //
	/**
	 * Adds a Google Calendar Feed
	 * @param gcal url to xml feed
	 */
	public void addFeed(CharSequence gcal)
	{
		this.addFeed(gcal, "");
	}

	/**
	 * Adds a Google Calendar Feed
	 * @param gcal url to xml feed
	 * @param className css class to be used
	 */
	public void addFeed(CharSequence gcal, String className)
	{
		if (this.gcals == null)
		{
			this.gcals = new HashMap<CharSequence, String>();
		}

		this.gcals.put(gcal, className);
	}

	/**
	 * Refreshes the events currently available in the selected view.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void refresh(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("jQuery('%s').fullCalendar('refetchEvents');", JQueryWidget.getSelector(this)));
	}

	// Properties //
	@Override
	public boolean isEditable()
	{
		return false;
	}

	@Override
	public boolean isSelectable()
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

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.modelBehavior = new CalendarModelBehavior(this.getModel()));
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	public void onSelect(AjaxRequestTarget target, Date start, Date end, boolean allDay)
	{
	}

	@Override
	public void onDayClick(AjaxRequestTarget target, Date date)
	{
	}

	@Override
	public void onEventClick(AjaxRequestTarget target, int eventId)
	{
	}

	@Override
	public void onEventDrop(AjaxRequestTarget target, int eventId, long delta, boolean allDay)
	{
	}

	@Override
	public void onEventResize(AjaxRequestTarget target, int eventId, long delta)
	{
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
			public boolean isEditable()
			{
				return Calendar.this.isEditable();
			}

			@Override
			public boolean isSelectable()
			{
				return Calendar.this.isSelectable();
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
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				// lazy options //
				Calendar.this.onConfigure(this);

				// builds sources //
				StringBuilder sourceBuilder = new StringBuilder();
				sourceBuilder.append("'").append(Calendar.this.modelBehavior.getCallbackUrl()).append("'");

				if (Calendar.this.gcals != null)
				{
					for (Entry<CharSequence, String> gcal : Calendar.this.gcals.entrySet())
					{
						sourceBuilder.append(", ");
						sourceBuilder.append("jQuery.fullCalendar.gcalFeed('").append(gcal.getKey()).append("', { className: '").append(gcal.getValue()).append("' })");
					}
				}

				this.setOption("eventSources", String.format("[%s]", sourceBuilder.toString()));
			}

			@Override
			public void onSelect(AjaxRequestTarget target, Date start, Date end, boolean allDay)
			{
				Calendar.this.onSelect(target, start, end, allDay);
			}

			@Override
			public void onDayClick(AjaxRequestTarget target, Date date)
			{
				Calendar.this.onDayClick(target, date);
			}

			@Override
			public void onEventClick(AjaxRequestTarget target, int eventId)
			{
				Calendar.this.onEventClick(target, eventId);
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
		};
	}
}
