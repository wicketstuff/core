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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.time.Duration;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides the jQuery fullCalendar behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class CalendarBehavior extends JQueryBehavior implements IJQueryAjaxAware, ICalendarListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "fullCalendar";

	private JQueryAjaxBehavior onDayClickBehavior; // day click
	private JQueryAjaxBehavior onSelectBehavior = null; // date range-select behavior;

	private JQueryAjaxBehavior onEventClickBehavior; // event click
	private JQueryAjaxBehavior onEventDropBehavior = null; // event drop
	private JQueryAjaxBehavior onEventResizeBehavior = null; // event resize


	public CalendarBehavior(final String selector)
	{
		this(selector, new Options());
	}

	public CalendarBehavior(final String selector, Options options)
	{
		super(selector, METHOD, options);

		this.add(new CssResourceReference(CalendarBehavior.class, "fullcalendar.css"));
		this.add(new JavaScriptResourceReference(CalendarBehavior.class, "fullcalendar.min.js"));
		this.add(new JavaScriptResourceReference(CalendarBehavior.class, "gcal.js"));
	}


	// Methods //
	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.isEditable())
		{
			component.add(this.onDayClickBehavior = this.newOnDayClickBehavior());
			component.add(this.onEventClickBehavior = this.newOnEventClickBehavior());
		}

		if (this.isSelectable())
		{
			component.add(this.onSelectBehavior = this.newOnSelectBehavior());
		}

		if (this.isEventDropEnabled())
		{
			component.add(this.onEventDropBehavior = this.newOnEventDropBehavior());
		}

		if (this.isEventResizeEnabled())
		{
			component.add(this.onEventResizeBehavior = this.newOnEventResizeBehavior());
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);

		/* adds and configure the busy indicator */
		response.render(JavaScriptHeaderItem.forScript("jQuery(function(){ "
				+ "jQuery('<img />')"
				+ ".attr('src', '" + RequestCycle.get().urlFor(handler).toString() + "')"
				+ ".ajaxStart(function() { jQuery(this).show(); })"
				+ ".ajaxStop(function() { jQuery(this).hide(); })"
				+ ".appendTo('.fc-header-center');"
				+ " });", this.getClass().getName()));
	}

	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.options.set("editable", this.isEditable());
		this.options.set("selectable", this.isSelectable());
		this.options.set("selectHelper", this.isSelectable());
		this.options.set("disableDragging", !this.isEventDropEnabled());
		this.options.set("disableResizing", !this.isEventResizeEnabled());

		if (this.isEditable())
		{
			this.setOption("dayClick", this.onDayClickBehavior.getCallbackFunction());
			this.setOption("eventClick", this.onEventClickBehavior.getCallbackFunction());
		}

		if (this.onSelectBehavior != null)
		{
			this.setOption("select", this.onSelectBehavior.getCallbackFunction());
		}

		if (this.onEventDropBehavior != null)
		{
			this.setOption("eventDrop", this.onEventDropBehavior.getCallbackFunction());
		}

		if (this.onEventResizeBehavior != null)
		{
			this.setOption("eventResize", this.onEventResizeBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DayClickEvent)
		{
			DayClickEvent dayClickEvent = (DayClickEvent) event;
			this.onDayClick(target, dayClickEvent.getDate());
		}

		else if (event instanceof SelectEvent)
		{
			SelectEvent selectEvent = (SelectEvent) event;
			this.onSelect(target, selectEvent.getStart(), selectEvent.getEnd(), selectEvent.isAllDay());
		}

		else if (event instanceof ClickEvent)
		{
			ClickEvent clickEvent = (ClickEvent) event;
			this.onEventClick(target, clickEvent.getEventId());
		}

		else if (event instanceof DropEvent)
		{
			DropEvent dropEvent = (DropEvent) event;
			this.onEventDrop(target, dropEvent.getEventId(), dropEvent.getDelta(), dropEvent.isAllDay());
		}

		else if (event instanceof ResizeEvent)
		{
			ResizeEvent resizeEvent = (ResizeEvent) event;
			this.onEventResize(target, resizeEvent.getEventId(), resizeEvent.getDelta());
		}
	}

	// Factories //
	/**
	 * Gets the ajax behavior that will be triggered when the user clicks on a day cell
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnDayClickBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				//http://arshaw.com/fullcalendar/docs/mouse/dayClick/
				//function(date, allDay, jsEvent, view)
				return new CallbackParameter[] {
						CallbackParameter.converted("date", "date.getTime()"),
						CallbackParameter.explicit("allDay"),
						CallbackParameter.context("jsEvent"),
						CallbackParameter.context("view")
				};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new DayClickEvent();
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user select a cell range
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnSelectBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				//http://arshaw.com/fullcalendar/docs/selection/select_callback/
				//function(startDate, endDate, allDay, jsEvent, view) { }
				return new CallbackParameter[] {
						CallbackParameter.converted("start", "start.getTime()"),
						CallbackParameter.converted("end", "end.getTime()"),
						CallbackParameter.explicit("allDay"),
						CallbackParameter.context("jsEvent"),
						CallbackParameter.context("view")
				};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new SelectEvent();
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user clicks on an event
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnEventClickBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				//http://arshaw.com/fullcalendar/docs/mouse/eventClick/
				//function(event, jsEvent, view) { }
				return new CallbackParameter[] {
						CallbackParameter.context("event"),
						CallbackParameter.context("jsEvent"),
						CallbackParameter.context("view"),
						CallbackParameter.resolved("eventId", "event.id")
				};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ClickEvent();
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user moves (drag & drop) an event
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnEventDropBehavior()
	{
		return new EventDeltaBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new DropEvent();
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user resizes an event
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnEventResizeBehavior()
	{
		return new EventDeltaBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new ResizeEvent();
			}
		};
	}


	// Behavior classes //
	/**
	 * Base class for {@link JQueryAjaxBehavior} that will broadcast delta-based events
	 */
	protected abstract class EventDeltaBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public EventDeltaBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			//http://arshaw.com/fullcalendar/docs/event_ui/eventResize/
			//function(event, dayDelta, minuteDelta, revertFunc, jsEvent, ui, view) {  }
			return new CallbackParameter[] {
					CallbackParameter.context("event"),
					CallbackParameter.explicit("dayDelta"), //retrieved
					CallbackParameter.explicit("minuteDelta"), //retrieved
					CallbackParameter.context("revertFunc"),
					CallbackParameter.context("jsEvent"),
					CallbackParameter.context("ui"),
					CallbackParameter.context("view"),
					CallbackParameter.resolved("eventId", "event.id") //retrieved
			};
		}
	}


	// Event classes //
	/**
	 * An event object that will be broadcasted when the user clicks on a day cell
	 */
	protected static class DayClickEvent extends JQueryEvent
	{
		private final Date day;

		/**
		 * Constructor
		 */
		public DayClickEvent()
		{
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
	protected static class SelectEvent extends JQueryEvent
	{
		private final boolean isAllDay;
		private final Date start;
		private final Date end;

		public SelectEvent()
		{
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
	protected static class ClickEvent extends JQueryEvent
	{
		private final int eventId;

		/**
		 * Constructor
		 */
		public ClickEvent()
		{
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
	protected static abstract class DeltaEvent extends JQueryEvent
	{
		private final int eventId;
		private long delta;

		/**
		 * Constructor
		 */
		public DeltaEvent()
		{
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
	protected static class DropEvent extends DeltaEvent
	{
		private final boolean isAllDay;

		/**
		 * Constructor
		 */
		public DropEvent()
		{
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
	protected static class ResizeEvent extends DeltaEvent
	{
	}
}
