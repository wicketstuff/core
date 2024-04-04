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

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.calendar.settings.CalendarLibrarySettings;

/**
 * Provides the jQuery fullCalendar behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class CalendarBehavior extends JQueryBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "fullCalendar";

	/** event listener */
	private final ICalendarListener listener;

	/** date range-select behavior */
	private JQueryAjaxBehavior onSelectAjaxBehavior = null;

	/** day click */
	private JQueryAjaxBehavior onDayClickAjaxBehavior;

	/** event click */
	private JQueryAjaxBehavior onEventClickAjaxBehavior;

	/** event drop */
	private JQueryAjaxBehavior onEventDropAjaxBehavior = null;

	/** event resize */
	private JQueryAjaxBehavior onEventResizeAjaxBehavior = null;

	/** event-object drop */
	private JQueryAjaxBehavior onObjectDropAjaxBehavior = null;

	/** view render */
	private JQueryAjaxBehavior onViewRenderAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link ICalendarListener}
	 */
	public CalendarBehavior(final String selector, ICalendarListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link ICalendarListener}
	 */
	public CalendarBehavior(final String selector, Options options, ICalendarListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
		this.initReferences();
	}

	/**
	 * Initializes CSS & JavaScript resource references
	 */
	private void initReferences()
	{
		CalendarLibrarySettings settings = CalendarLibrarySettings.get();

		// fullcalendar.css //
		if (settings.getStyleSheetReference() != null)
		{
			this.add(settings.getStyleSheetReference());
		}

		// fullcalendar.min.js //
		if (settings.getJavaScriptReference() != null)
		{
			this.add(settings.getJavaScriptReference());
		}

		// gcal.js //
		if (settings.getGCalJavaScriptReference() != null)
		{
			this.add(settings.getGCalJavaScriptReference());
		}

		// locale-all.js //
		if (settings.getLocalesJavaScriptReference() != null)
		{
			this.add(settings.getLocalesJavaScriptReference());
		}
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.listener.isSelectable())
		{
			this.onSelectAjaxBehavior = this.newOnSelectAjaxBehavior(this);
			component.add(this.onSelectAjaxBehavior);
		}

		if (this.listener.isDayClickEnabled())
		{
			this.onDayClickAjaxBehavior = this.newOnDayClickAjaxBehavior(this);
			component.add(this.onDayClickAjaxBehavior);
		}

		if (this.listener.isEventClickEnabled())
		{
			this.onEventClickAjaxBehavior = this.newOnEventClickAjaxBehavior(this);
			component.add(this.onEventClickAjaxBehavior);
		}

		if (this.listener.isEventDropEnabled())
		{
			this.onEventDropAjaxBehavior = this.newOnEventDropAjaxBehavior(this, this.listener.getEventDropPrecondition());
			component.add(this.onEventDropAjaxBehavior);
		}

		if (this.listener.isEventResizeEnabled())
		{
			this.onEventResizeAjaxBehavior = this.newOnEventResizeAjaxBehavior(this, this.listener.getEventResizePrecondition());
			component.add(this.onEventResizeAjaxBehavior);
		}

		if (this.listener.isObjectDropEnabled())
		{
			this.onObjectDropAjaxBehavior = this.newOnObjectDropAjaxBehavior(this);
			component.add(this.onObjectDropAjaxBehavior);
		}

		if (this.listener.isViewRenderEnabled())
		{
			this.onViewRenderAjaxBehavior = this.newOnViewRenderAjaxBehavior(this);
			component.add(this.onViewRenderAjaxBehavior);
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		/* adds and configure the busy indicator */
		StringBuilder builder = new StringBuilder();

		builder.append("jQuery(\"<img id='calendar-indicator' src='").append(RequestCycleUtils.getAjaxIndicatorUrl()).append("' />\").appendTo('.fc-header-center');\n"); // allows only one calendar.
		builder.append("jQuery(document).ajaxStart(function() { jQuery('#calendar-indicator').show(); });\n");
		builder.append("jQuery(document).ajaxStop(function() { jQuery('#calendar-indicator').hide(); });\n");

		this.renderOnDomReadyScript(builder.toString(), response);
	}

	// Properties //

	/**
	 * Indicates whether the Calendar will be editable
	 *
	 * @return by default, true if {@link ICalendarListener#isDayClickEnabled()} is true or {@link ICalendarListener#isEventClickEnabled()} is true
	 */
	protected boolean isEditable()
	{
		return (this.onDayClickAjaxBehavior != null) || (this.onEventClickAjaxBehavior != null);
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		this.setOption("editable", this.isEditable());
		this.setOption("selectable", this.listener.isSelectable());
		this.setOption("selectHelper", this.listener.isSelectable());
		this.setOption("disableDragging", !this.listener.isEventDropEnabled());
		this.setOption("disableResizing", !this.listener.isEventResizeEnabled());
		this.setOption("droppable", this.listener.isObjectDropEnabled());

		if (this.onSelectAjaxBehavior != null)
		{
			this.setOption("select", this.onSelectAjaxBehavior.getCallbackFunction());
		}

		if (this.onDayClickAjaxBehavior != null)
		{
			this.setOption("dayClick", this.onDayClickAjaxBehavior.getCallbackFunction());
		}

		if (this.onEventClickAjaxBehavior != null)
		{
			this.setOption("eventClick", this.onEventClickAjaxBehavior.getCallbackFunction());
		}

		if (this.onEventDropAjaxBehavior != null)
		{
			this.setOption("eventDrop", this.onEventDropAjaxBehavior.getCallbackFunction());
		}

		if (this.onEventResizeAjaxBehavior != null)
		{
			this.setOption("eventResize", this.onEventResizeAjaxBehavior.getCallbackFunction());
		}

		if (this.onObjectDropAjaxBehavior != null)
		{
			this.setOption("drop", this.onObjectDropAjaxBehavior.getCallbackFunction());
		}

		if (this.onViewRenderAjaxBehavior != null)
		{
			this.setOption("viewRender", this.onViewRenderAjaxBehavior.getCallbackFunction());
		}

		super.onConfigure(component);
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof SelectEvent)
		{
			SelectEvent selectEvent = (SelectEvent) event;
			this.listener.onSelect(target, selectEvent.getView(), selectEvent.getStart(), selectEvent.getEnd(), selectEvent.isAllDay());
		}

		else if (event instanceof DayClickEvent)
		{
			DayClickEvent dayClickEvent = (DayClickEvent) event;
			this.listener.onDayClick(target, dayClickEvent.getView(), dayClickEvent.getDate(), dayClickEvent.isAllDay());
		}

		else if (event instanceof ClickEvent)
		{
			ClickEvent clickEvent = (ClickEvent) event;
			this.listener.onEventClick(target, clickEvent.getView(), clickEvent.getEventId());
		}

		else if (event instanceof DropEvent)
		{
			DropEvent dropEvent = (DropEvent) event;
			this.listener.onEventDrop(target, dropEvent.getEventId(), dropEvent.getDelta(), dropEvent.isAllDay());
		}

		else if (event instanceof ResizeEvent)
		{
			ResizeEvent resizeEvent = (ResizeEvent) event;
			this.listener.onEventResize(target, resizeEvent.getEventId(), resizeEvent.getDelta());
		}

		else if (event instanceof ObjectDropEvent)
		{
			ObjectDropEvent dropEvent = (ObjectDropEvent) event;
			this.listener.onObjectDrop(target, dropEvent.getTitle(), dropEvent.getDate(), dropEvent.isAllDay());
		}

		else if (event instanceof ViewRenderEvent)
		{
			ViewRenderEvent renderEvent = (ViewRenderEvent) event;
			this.listener.onViewRender(target, renderEvent.getView(), renderEvent.getStart(), renderEvent.getEnd());
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'select' event, triggered when the user select a cell range
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnSelectAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnSelectAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnSelectAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'dayClick' event, triggered when the user clicks on a day cell
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnDayClickAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDayClickAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDayClickAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'eventClick' event, triggered when the user clicks on an event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnEventClickAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnEventClickAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnEventClickAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'eventDrop' event, triggered when the user moves (drag &#38; drop) an event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param precondition the JavaScript precondition
	 * @return a new {@code OnEventDropAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnEventDropAjaxBehavior(IJQueryAjaxAware source, CharSequence precondition)
	{
		return new OnEventDropAjaxBehavior(source, precondition);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'eventResize' event, triggered when the user resizes an event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param precondition the JavaScript precondition
	 * @return a new {@code OnEventResizeAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnEventResizeAjaxBehavior(IJQueryAjaxAware source, CharSequence precondition)
	{
		return new OnEventResizeAjaxBehavior(source, precondition);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'drop' event, triggered when the user drops an event object
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnObjectDropAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnObjectDropAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnObjectDropAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'viewRender' event, triggered when the user changes the view, or when any of the date navigation methods are called.
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnViewRenderAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnViewRenderAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnViewRenderAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'select' event
	 */
	protected static class OnSelectAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnSelectAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			// http://fullcalendar.io/docs/selection/select_callback/
			return new CallbackParameter[] { CallbackParameter.converted("startDate", "startDate.format()"), // retrieved
					CallbackParameter.converted("endDate", "endDate.format()"), // retrieved
					CallbackParameter.resolved("allDay", "!startDate.hasTime()"), // retrieved
					CallbackParameter.context("jsEvent"), // lf
					CallbackParameter.context("view"), // lf
					CallbackParameter.resolved("viewName", "view.name") // retrieved
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new SelectEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'dayClick' event
	 */
	protected static class OnDayClickAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnDayClickAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			// http://fullcalendar.io/docs/mouse/dayClick/
			return new CallbackParameter[] { CallbackParameter.converted("date", "date.format()"), // retrieved
					CallbackParameter.resolved("allDay", "!date.hasTime()"), // retrieved
					CallbackParameter.context("jsEvent"), // lf
					CallbackParameter.context("view"),// lf
					CallbackParameter.resolved("viewName", "view.name") // retrieved
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DayClickEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'eventClick' event
	 */
	protected static class OnEventClickAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnEventClickAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			// http://arshaw.com/fullcalendar/docs/mouse/eventClick/
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("jsEvent"), // lf
					CallbackParameter.context("view"), // lf
					CallbackParameter.resolved("eventId", "event.id"),// retrieved
					CallbackParameter.resolved("viewName", "view.name") // retrieved
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ClickEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'eventDrop' event
	 */
	protected static class OnEventDropAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		private final CharSequence precondition;

		public OnEventDropAjaxBehavior(IJQueryAjaxAware source, CharSequence precondition)
		{
			super(source);

			this.precondition = precondition;
		}

		@Override
		protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
		{
			super.updateAjaxAttributes(attributes);

			if (!Strings.isEmpty(this.precondition))
			{
				AjaxCallListener ajaxCallListener = new AjaxCallListener();
				ajaxCallListener.onPrecondition(this.precondition);

				attributes.getAjaxCallListeners().add(ajaxCallListener);
			}
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			// http://fullcalendar.io/docs/event_ui/eventDrop/
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("delta"), // lf
					CallbackParameter.resolved("millisDelta", "delta.asMilliseconds()"), // retrieved
					CallbackParameter.resolved("allDay", "!event.start.hasTime()"), // retrieved
					CallbackParameter.context("revertFunc"), // lf
					CallbackParameter.context("jsEvent"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.context("view"), // lf
					CallbackParameter.resolved("eventId", "event.id") // retrieved
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DropEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'eventResize' event
	 */
	protected static class OnEventResizeAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		private final CharSequence precondition;

		public OnEventResizeAjaxBehavior(IJQueryAjaxAware source, CharSequence precondition)
		{
			super(source);

			this.precondition = precondition;
		}

		@Override
		protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
		{
			super.updateAjaxAttributes(attributes);

			if (!Strings.isEmpty(this.precondition))
			{
				AjaxCallListener ajaxCallListener = new AjaxCallListener();
				ajaxCallListener.onPrecondition(this.precondition);

				attributes.getAjaxCallListeners().add(ajaxCallListener);
			}
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("delta"), // lf
					CallbackParameter.context("revertFunc"), // lf
					CallbackParameter.context("jsEvent"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.context("view"), // lf
					CallbackParameter.resolved("millisDelta", "delta.asMilliseconds()"), // retrieved
					CallbackParameter.resolved("allDay", "!event.start.hasTime()"), // retrieved
					CallbackParameter.resolved("eventId", "event.id") // retrieved
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ResizeEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'drop' event
	 */
	protected static class OnObjectDropAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnObjectDropAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			// http://fullcalendar.io/docs/dropping/drop/
			return new CallbackParameter[] { CallbackParameter.converted("date", "date.format()"), // retrieved
					CallbackParameter.resolved("allDay", "!date.hasTime()"), // retrieved
					CallbackParameter.context("jsEvent"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("title", "jQuery(this).data('title')") // retrieved
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ObjectDropEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'viewRender' event
	 */
	protected static class OnViewRenderAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnViewRenderAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			// http://arshaw.com/fullcalendar/docs/display/viewRender/
			return new CallbackParameter[] { CallbackParameter.context("view"),// lf
					CallbackParameter.context("element"), // lf
					CallbackParameter.resolved("viewName", "view.name"), // retrieved
					CallbackParameter.resolved("startDate", "view.start.format()"), // retrieved
					CallbackParameter.resolved("endDate", "view.end.format()") }; // retrieved
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ViewRenderEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnSelectAjaxBehavior} callback
	 */
	protected static class SelectEvent extends JQueryEvent
	{
		private final LocalDateTime start;
		private final LocalDateTime end;
		private final boolean isAllDay;
		private final String viewName;

		public SelectEvent()
		{
			this.isAllDay = RequestCycleUtils.getQueryParameterValue("allDay").toBoolean();

			String start = RequestCycleUtils.getQueryParameterValue("startDate").toString();
			this.start = this.isAllDay ? LocalDate.parse(start).atStartOfDay() : LocalDateTime.parse(start);

			String end = RequestCycleUtils.getQueryParameterValue("endDate").toString();
			this.end = this.isAllDay ? LocalDate.parse(end).atStartOfDay() : LocalDateTime.parse(end);

			this.viewName = RequestCycleUtils.getQueryParameterValue("viewName").toString();
		}

		/**
		 * Gets the event start date
		 *
		 * @return the start date
		 */
		public LocalDateTime getStart()
		{
			return this.start;
		}

		/**
		 * Gets the end date
		 *
		 * @return the end date
		 */
		public LocalDateTime getEnd()
		{
			return this.end;
		}

		/**
		 * Indicates whether this event is an 'all-day' event
		 *
		 * @return true or false
		 */
		public boolean isAllDay()
		{
			return this.isAllDay;
		}

		/**
		 * Gets the current {@link CalendarView}
		 *
		 * @return the view name
		 */
		public CalendarView getView()
		{
			return CalendarView.get(this.viewName);
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDayClickAjaxBehavior} callback
	 */
	protected static class DayClickEvent extends JQueryEvent
	{
		private final LocalDateTime day;
		private final boolean isAllDay;
		private final String viewName;

		/**
		 * Constructor
		 */
		public DayClickEvent()
		{
			this.isAllDay = RequestCycleUtils.getQueryParameterValue("allDay").toBoolean();

			String date = RequestCycleUtils.getQueryParameterValue("date").toString();
			this.day = this.isAllDay ? LocalDate.parse(date).atStartOfDay() : LocalDateTime.parse(date);

			this.viewName = RequestCycleUtils.getQueryParameterValue("viewName").toString();
		}

		/**
		 * Gets the event date
		 *
		 * @return the date
		 */
		public LocalDateTime getDate()
		{
			return this.day;
		}

		/**
		 * Indicates whether this event is an 'all-day' event
		 *
		 * @return true or false
		 */
		public boolean isAllDay()
		{
			return this.isAllDay;
		}

		/**
		 * Gets the current {@link CalendarView}
		 *
		 * @return the view name
		 */
		public CalendarView getView()
		{
			return CalendarView.get(this.viewName);
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnEventClickAjaxBehavior} callback
	 */
	protected static class ClickEvent extends JQueryEvent
	{
		private final String eventId;
		private final String viewName;

		/**
		 * Constructor
		 */
		public ClickEvent()
		{
			this.eventId = RequestCycleUtils.getQueryParameterValue("eventId").toString();
			this.viewName = RequestCycleUtils.getQueryParameterValue("viewName").toString();
		}

		/**
		 * Gets the event's id
		 *
		 * @return the event's id
		 */
		public String getEventId()
		{
			return this.eventId;
		}

		/**
		 * Gets the current {@link CalendarView}
		 *
		 * @return the view name
		 */
		public CalendarView getView()
		{
			return CalendarView.get(this.viewName);
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnViewRenderAjaxBehavior} callback
	 */
	protected static class ViewRenderEvent extends JQueryEvent
	{
		private final LocalDate start;
		private final LocalDate end;
		private final String viewName;

		/**
		 * Constructor
		 */
		public ViewRenderEvent()
		{
			String start = RequestCycleUtils.getQueryParameterValue("startDate").toString();
			this.start = LocalDate.parse(start);

			String end = RequestCycleUtils.getQueryParameterValue("endDate").toString();
			this.end = LocalDate.parse(end);

			this.viewName = RequestCycleUtils.getQueryParameterValue("viewName").toString();
		}

		/**
		 * Gets the event start date
		 *
		 * @return the start date
		 */
		public LocalDate getStart()
		{
			return this.start;
		}

		/**
		 * Gets the end date
		 *
		 * @return the end date
		 */
		public LocalDate getEnd()
		{
			return this.end;
		}

		/**
		 * Gets the current {@link CalendarView}
		 *
		 * @return the view name
		 */
		public CalendarView getView()
		{
			return CalendarView.get(this.viewName);
		}
	}

	/**
	 * Provides a base class for {@link CalendarBehavior} event objects that contain a delta time
	 */
	protected abstract static class DeltaEvent extends JQueryEvent
	{
		private final String eventId;
		private final long delta;

		/**
		 * Constructor
		 */
		public DeltaEvent()
		{
			this.eventId = RequestCycleUtils.getQueryParameterValue("eventId").toString();
			this.delta = RequestCycleUtils.getQueryParameterValue("millisDelta").toLong();
		}

		/**
		 * Gets the event's id
		 *
		 * @return the event's id
		 */
		public String getEventId()
		{
			return this.eventId;
		}

		/**
		 * Gets the event's delta time in milliseconds
		 *
		 * @return the event's delta time
		 */
		public long getDelta()
		{
			return this.delta;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnEventDropAjaxBehavior} callback
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
		 * Indicates whether this event is an 'all-day' event
		 *
		 * @return true or false
		 */
		public boolean isAllDay()
		{
			return this.isAllDay;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnEventResizeAjaxBehavior} callback
	 */
	protected static class ResizeEvent extends DeltaEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnObjectDropAjaxBehavior} callback
	 */
	protected static class ObjectDropEvent extends JQueryEvent
	{
		private final LocalDateTime day;
		private final String title;
		private final boolean isAllDay;

		/**
		 * Constructor
		 */
		public ObjectDropEvent()
		{
			this.isAllDay = RequestCycleUtils.getQueryParameterValue("allDay").toBoolean();

			String date = RequestCycleUtils.getQueryParameterValue("date").toString();
			this.day = this.isAllDay ? LocalDate.parse(date).atStartOfDay() : LocalDateTime.parse(date);

			this.title = RequestCycleUtils.getQueryParameterValue("title").toString();
		}

		/**
		 * Gets the event date
		 *
		 * @return the date
		 */
		public LocalDateTime getDate()
		{
			return this.day;
		}

		/**
		 * Gets the event title
		 *
		 * @return the title
		 */
		public String getTitle()
		{
			return this.title;
		}

		/**
		 * Indicates whether this event is an 'all-day' event
		 *
		 * @return true or false
		 */
		public boolean isAllDay()
		{
			return this.isAllDay;
		}
	}
}
