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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryContainer;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 *
 */
public class Calendar extends JQueryContainer
{
	private static final long serialVersionUID = 1L;

	private final Options options;
	private Map<CharSequence, String> gcals;
	
	private CalendarModelBehavior modelBehavior; // events load
	private JQueryAjaxBehavior eventBehavior; // event click
 
	public Calendar(String id, CalendarModel model)
	{
		this(id, model, new Options());
	}
	
	/**
	 * 
	 * @param id the markyp id
	 * @param model the {@link CalendarModel}
	 * @param options {@link Options}
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
		if (gcals == null)
		{
			this.gcals = new HashMap<CharSequence, String>();
		}

		this.gcals.put(gcal, className);
	}
	

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		
		this.add(this.modelBehavior = new CalendarModelBehavior(this.getModel()));
		this.add(this.eventBehavior = new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public CharSequence getCallbackScript()
			{
				return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl() + "&eventId=' + calEvent.id");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ClickEvent(target);
			}
			
		});
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering 
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 * 
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOptions(this.options);
	}
	
	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof ClickEvent)
		{
			ClickEvent payload = (ClickEvent) event.getPayload();

			this.onClick(payload.getTarget(), payload.getEventId());
		}
	}
	
	
	protected void onClick(AjaxRequestTarget target, int eventId)
	{

	}
	
	
	// IJQueryWidget //
	/**
	 * see {@link JQueryContainer#newWidgetBehavior(String)}
	 */
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new CalendarBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				Calendar.this.onConfigure(this); //set options
				
				/* builds sources */
				StringBuilder sourceBuilder = new StringBuilder();
				sourceBuilder.append("'").append(modelBehavior.getCallbackUrl()).append("'");

				if (Calendar.this.gcals != null)
				{
					for (Entry<CharSequence, String> gcal : Calendar.this.gcals.entrySet())
					{
						sourceBuilder.append(", ");
						sourceBuilder.append("$.fullCalendar.gcalFeed('").append(gcal.getKey()).append("', { className: '").append(gcal.getValue()).append("' })");
					}
				}

				this.setOption("eventSources", String.format("[%s]", sourceBuilder.toString()));

				// behaviors //
				this.setOption("eventClick", "function(calEvent, jsEvent, view) { " + eventBehavior.getCallbackScript() + "}");
			}
		};
	}
	
	// Options //
	/**
	 * Adds or replace an options defined by a key/value pair.<br/>
	 * If for a given key, the value is null, then the pair is removed.
	 */
	public void setOption(String key, Serializable value)
	{
		this.options.set(key, value);
	}
	
	
	/* Event classes */
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'change' callback
	 */
	class ClickEvent extends JQueryEvent
	{
		private int eventId;

		public ClickEvent(AjaxRequestTarget target)
		{
			super(target);
			
			this.eventId = RequestCycleUtils.getQueryParameterValue("eventId").toInt();
		}
		
		public int getEventId()
		{
			return this.eventId;
		}
	}
}
