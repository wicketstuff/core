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
package com.googlecode.wicket.jquery.ui.calendar6;

import java.util.List;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.calendar6.EventSource.GoogleCalendar;

/**
 * Provides calendar widget, based on the jQuery fullcalendar plugin.
 *
 * @author Sebastien Briquet - sebfz1
 * @author Martin Grigorov - martin-g
 *
 */
public class Calendar extends WebMarkupContainer implements ICalendarListener
{
	private static final long serialVersionUID = 1L;

	private List<EventSource> sources;
	private CalendarModelBehavior modelBehavior; // events load
	private CalendarBehavior behavior;

	protected final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}. Note that 'selectable' and 'selectHelper' options are set by overriding {@link #isSelectable()} (default is false)
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
	 * @param options the {@link Options}. Note that 'selectable' and 'selectHelper' options are set by overriding {@link #isSelectable()} (default is false)
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
			this.sources = Generics.newArrayList();
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
		handler.appendJavaScript(String.format("document.querySelector('%s').calendar.refetchEvents();", JQueryWidget.getSelector(this)));
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.modelBehavior = this.newCalendarModelBehavior(this.getModel());
		this.behavior = new CalendarBehavior(JQueryWidget.getSelector(this), this.options, this);
		this.add(this.behavior, this.modelBehavior);
	}

	@Override
	public void onConfigure()
	{
		super.onConfigure();

		// builds sources //
		StringBuilder sourceBuilder = new StringBuilder();
		sourceBuilder.append("{ url: '" + modelBehavior.getCallbackUrl() + "' }");

		if (this.sources != null)
		{
			for (EventSource source : this.sources)
			{
				sourceBuilder.append(", ");
				sourceBuilder.append(source.toString());
			}
		}

		behavior.setOption("eventSources", "[" + sourceBuilder + "]");
	}

	// IJQueryWidget //

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
