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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.util.lang.Args;
import org.threeten.bp.LocalDateTime;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.calendar.EventSource.GoogleCalendar;

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
	private List<EventSource> sources;
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

		this.options = Args.notNull(options, "options");
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

		this.options = Args.notNull(options, "options");
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
	 * Adds a {@link EventSource}, can be a {@link GoogleCalendar}
	 *
	 * @param source the {@link EventSource}, 
	 */
	public void addSource(EventSource source)
	{
		if (this.sources == null)
		{
			this.sources = new ArrayList<>();
		}

		this.sources.add(source);
	}

	/**
	 * Re-fetches and refreshes the events currently available in the selected view.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void refresh(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("jQuery('%s').fullCalendar('refetchEvents');", JQueryWidget.getSelector(this)));
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

		this.modelBehavior = this.newCalendarModelBehavior(this.getModel());
		this.add(this.modelBehavior);
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		// builds sources //
		StringBuilder sourceBuilder = new StringBuilder();
		sourceBuilder.append(String.format("{ url: '%s' }", Calendar.this.modelBehavior.getCallbackUrl()));

		if (Calendar.this.sources != null)
		{
			for (EventSource source : Calendar.this.sources)
			{
				sourceBuilder.append(", ");
				sourceBuilder.append(source.toString());
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
		return new CalendarBehavior(selector, this.options, this);
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
