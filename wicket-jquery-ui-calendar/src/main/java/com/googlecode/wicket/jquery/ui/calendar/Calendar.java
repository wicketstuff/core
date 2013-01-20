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
import org.apache.wicket.event.IEvent;
import org.apache.wicket.util.time.Duration;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryContainer;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * Provides calendar widget, based on the jQuery fullcalendar plugin.
 *
 * @author Sebastien Briquet - sebfz1
 * @author Martin Grigorov - martin-g
 *
 */
public class Calendar extends JQueryContainer
{
	private static final long serialVersionUID = 1L;

	private final Options options;

	private Map<CharSequence, String> gcals;

	private CalendarModelBehavior modelBehavior; // events load

	private JQueryAjaxBehavior onDayClickBehavior; // day click
	private JQueryAjaxBehavior onSelectBehavior; // date range-select behavior;

	private JQueryAjaxBehavior onEventClickBehavior; // event click
	private JQueryAjaxBehavior onEventDropBehavior; // event drop
	private JQueryAjaxBehavior onEventResizeBehavior; // event resize

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
	/**
	 * Indicated whether a cell can be selected.<br />
	 * If true, the {@link #onSelect(AjaxRequestTarget, Date, Date, boolean)} event will be triggered
	 *
	 * @return false by default
	 */
	protected boolean isSelectable()
	{
		return false;
	}

	/**
	 * Indicates whether the event can be edited (ie, clicked).<br/>
	 * IIF true, an event can override this global setting to false by using CalendarEvent#setEditable(boolean);<br/>
	 * If true, the {@link #onEventClick(AjaxRequestTarget, int)} event and {@link #onDayClick(AjaxRequestTarget, Date)} event will be triggered<br/>
	 *
	 * @return false by default
	 */
	protected boolean isEditable()
	{
		return false;
	}

	/**
	 * Indicates whether the event can be dragged &#38; dropped.
	 * If true, the {@link #onEventDrop(AjaxRequestTarget, int, long, boolean)} event will be triggered
	 *
	 * @return false by default
	 */
	protected boolean isEventDropEnabled()
	{
		return false;
	}

	/**
	 * Indicates whether the event can be resized.
	 * If true, the {@link #onEventResize(AjaxRequestTarget, int, long)} event will be triggered
	 *
	 * @return false by default
	 */
	protected boolean isEventResizeEnabled()
	{
		return false;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.modelBehavior = new CalendarModelBehavior(this.getModel()));

		// event behaviors //
		this.add(this.onDayClickBehavior = this.newOnDayClickBehavior());
		this.add(this.onSelectBehavior = this.newOnSelectBehavior());
		this.add(this.onEventClickBehavior = this.newOnEventClickBehavior());
		this.add(this.onEventDropBehavior = this.newOnEventDropBehavior());
		this.add(this.onEventResizeBehavior = this.newOnEventResizeBehavior());
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
		this.options.set("editable", this.isEditable());
		this.options.set("selectable", this.isSelectable());
		this.options.set("selectHelper", this.isSelectable());
		this.options.set("disableDragging", !this.isEventDropEnabled());
		this.options.set("disableResizing", !this.isEventResizeEnabled());

		behavior.setOptions(this.options);
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		Object payload = event.getPayload();

		if (payload instanceof DayClickEvent)
		{
			DayClickEvent dayClickEvent = (DayClickEvent) payload;
			this.onDayClick(dayClickEvent.getTarget(), dayClickEvent.getDate());
		}

		else if (payload instanceof SelectEvent)
		{
			SelectEvent selectEvent = (SelectEvent) payload;
			this.onSelect(selectEvent.getTarget(), selectEvent.getStart(), selectEvent.getEnd(), selectEvent.isAllDay());
		}

		else if (payload instanceof ClickEvent)
		{
			ClickEvent clickEvent = (ClickEvent) payload;
			this.onEventClick(clickEvent.getTarget(), clickEvent.getEventId());
		}

		else if (payload instanceof DropEvent)
		{
			DropEvent dropEvent = (DropEvent) payload;
			this.onEventDrop(dropEvent.getTarget(), dropEvent.getEventId(), dropEvent.getDelta(), dropEvent.isAllDay());
		}

		else if (payload instanceof ResizeEvent)
		{
			ResizeEvent resizeEvent = (ResizeEvent) payload;
			this.onEventResize(resizeEvent.getTarget(), resizeEvent.getEventId(), resizeEvent.getDelta());
		}
	}

	/**
	 * Triggered when a calendar day is clicked
	 * @param target the {@link AjaxRequestTarget}
	 * @param date the day
	 */
	protected void onDayClick(AjaxRequestTarget target, Date date)
	{
	}

	/**
	 * Triggered when an cell is selected.<br/>
	 * {@link #isSelectable()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param start the event start {@link Date}
	 * @param end the event end {@link Date}
	 * @param allDay the event all-day property
	 */
	protected void onSelect(AjaxRequestTarget target, Date start, Date end, boolean allDay)
	{
	}

	/**
	 * Triggered when an event is clicked.<br/>
	 * {@link #isEditable()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param eventId the {@link CalendarEvent} id
	 */
	protected void onEventClick(AjaxRequestTarget target, int eventId)
	{
	}

	/**
	 * Triggered when an event is dropped (after being dragged).<br/>
	 * {@link #isEventDropEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param eventId the {@link CalendarEvent} id
	 * @param delta the delta (time) with the original event date
	 * @param allDay the event all-day property
	 */
	protected void onEventDrop(AjaxRequestTarget target, int eventId, long delta, boolean allDay)
	{
	}

	/**
	 * Triggered when an event is dropped (after being dragged).<br/>
	 * {@link #isEventResizeEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param eventId the {@link CalendarEvent} id
	 * @param delta the delta (time) with the original event date
	 */
	protected void onEventResize(AjaxRequestTarget target, int eventId, long delta)
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

				// behaviors //
				if (Calendar.this.isEditable())
				{
					this.setOption("dayClick", Calendar.this.onDayClickBehavior.getCallbackFunction());
					this.setOption("eventClick", Calendar.this.onEventClickBehavior.getCallbackFunction());
				}

				if (Calendar.this.isSelectable())
				{
					this.setOption("select", Calendar.this.onSelectBehavior.getCallbackFunction());
				}

				if (Calendar.this.isEventDropEnabled())
				{
					this.setOption("eventDrop", Calendar.this.onEventDropBehavior.getCallbackFunction());
				}

				if (Calendar.this.isEventResizeEnabled())
				{
					this.setOption("eventResize", Calendar.this.onEventResizeBehavior.getCallbackFunction());
				}
			}
		};
	}


	// Factories //
	/**
	 * Gets the ajax behavior that will be triggered when the user clicks on a day cell
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnDayClickBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(date, allDay, jsEvent, view) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&date=' + date.getTime()");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new DayClickEvent(target);
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user select a cell range
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnSelectBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(start, end, allDay) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&start=' + start.getTime() + '&end=' + end.getTime() + '&allDay=' + allDay");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new SelectEvent(target);
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user clicks on an event
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnEventClickBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, jsEvent, view) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&eventId=' + event.id");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ClickEvent(target);
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user moves (drag & drop) an event
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnEventDropBehavior()
	{
		return new EventDeltaBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new DropEvent(target);
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user resizes an event
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnEventResizeBehavior()
	{
		return new EventDeltaBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ResizeEvent(target);
			}
		};
	}


	// Behavior classes //
	/**
	 * Base class for {@link JQueryAjaxBehavior} that will broadcast delta-based events
	 */
	private abstract class EventDeltaBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public EventDeltaBehavior(Component source)
		{
			super(source);
		}

		@Override
		public String getCallbackFunction()
		{
			return "function(event, dayDelta, minuteDelta, allDay, revertFunc, jsEvent, ui, view) { " + this.getCallbackScript() + " }";
		}

		@Override
		public CharSequence getCallbackScript()
		{
			return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&eventId=' + event.id + '&dayDelta=' + dayDelta + '&minuteDelta=' + minuteDelta + '&allDay=' + allDay");
		}
	}


	// Event classes //
	/**
	 * An event object that will be broadcasted when the user clicks on a day cell
	 */
	private class DayClickEvent extends JQueryEvent
	{
		private final Date day;

		/**
		 * Constructor
		 * @param target the {@link AjaxRequestTarget}
		 */
		public DayClickEvent(AjaxRequestTarget target)
		{
			super(target);

			long date = RequestCycleUtils.getQueryParameterValue("date").toLong();
			this.day = new Date(date);
		}

		/**
		 * Gets the event date
		 * @return the date
		 */
		public Date getDate()
		{
			return this.day;
		}
	}

	/**
	 * An event object that will be broadcasted when the user select a cell range
	 */
	private class SelectEvent extends JQueryEvent
	{
		private final boolean isAllDay;
		private final Date start;
		private final Date end;

		public SelectEvent(AjaxRequestTarget target)
		{
			super(target);

			long start = RequestCycleUtils.getQueryParameterValue("start").toLong();
			this.start = new Date(start);

			long end = RequestCycleUtils.getQueryParameterValue("end").toLong();
			this.end = new Date(end);

			this.isAllDay = RequestCycleUtils.getQueryParameterValue("allDay").toBoolean();
		}

		/**
		 * Gets the event start date
		 * @return the start date
		 */
		public Date getStart()
		{
			return this.start;
		}

		/**
		 * Gets the end date
		 * @return the end date
		 */
		public Date getEnd()
		{
			return this.end;
		}

		/**
		 * Indicated whether this event is an 'all-day' event
		 * @return true or false
		 */
		public boolean isAllDay()
		{
			return this.isAllDay;
		}
	}

	/**
	 * An event object that will be broadcasted when the user clicks on an event
	 */
	private class ClickEvent extends JQueryEvent
	{
		private final int eventId;

		/**
		 * Constructor
		 * @param target the {@link AjaxRequestTarget}
		 */
		public ClickEvent(AjaxRequestTarget target)
		{
			super(target);

			this.eventId = RequestCycleUtils.getQueryParameterValue("eventId").toInt();
		}

		/**
		 * Gets the event's id
		 * @return the event's id
		 */
		public int getEventId()
		{
			return this.eventId;
		}
	}

	/**
	 * A base event object that contains a delta time
	 */
	private abstract class DeltaEvent extends JQueryEvent
	{
		private final int eventId;
		private long delta;

		/**
		 * Constructor
		 * @param target the {@link AjaxRequestTarget}
		 */
		public DeltaEvent(AjaxRequestTarget target)
		{
			super(target);

			this.eventId = RequestCycleUtils.getQueryParameterValue("eventId").toInt();

			int dayDelta = RequestCycleUtils.getQueryParameterValue("dayDelta").toInt();
			int minuteDelta = RequestCycleUtils.getQueryParameterValue("minuteDelta").toInt();
			this.delta = (dayDelta * Duration.ONE_DAY.getMilliseconds()) + (minuteDelta * Duration.ONE_MINUTE.getMilliseconds());
		}

		/**
		 * Gets the event's id
		 * @return the event's id
		 */
		public int getEventId()
		{
			return this.eventId;
		}

		/**
		 * Gets the event's delta time in milliseconds
		 * @return the event's delta time
		 */
		public long getDelta()
		{
			return this.delta;
		}
	}

	/**
	 * An event object that will be broadcasted when the user moves (drag & drop) an event
	 */
	private class DropEvent extends DeltaEvent
	{
		private final boolean isAllDay;

		/**
		 * Constructor
		 * @param target the {@link AjaxRequestTarget}
		 */
		public DropEvent(AjaxRequestTarget target)
		{
			super(target);

			this.isAllDay = RequestCycleUtils.getQueryParameterValue("allDay").toBoolean();
		}

		/**
		 * Indicated whether this event is an 'all-day' event
		 * @return true or false
		 */
		public boolean isAllDay()
		{
			return this.isAllDay;
		}
	}

	/**
	 * An event object that will be broadcasted when the user resizes an event
	 */
	private class ResizeEvent extends DeltaEvent
	{
		/**
		 * Constructor
		 * @param target the {@link AjaxRequestTarget}
		 */
		public ResizeEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}
}
