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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.calendar6.settings.CalendarLibrarySettings;

/**
 * Provides the jQuery fullCalendar behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class CalendarBehavior extends Behavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "fullCalendar";
	private final Options options;
	private final String selector;

	/** event listener */
	private final ICalendarListener listener;

	/** date range-select behavior */
	private JQueryAjaxBehavior onSelectAjaxBehavior = null;

	/** day click */
	private JQueryAjaxBehavior onDateClickAjaxBehavior;

	/** event click */
	private JQueryAjaxBehavior onEventClickAjaxBehavior;

	/** event drop */
	private JQueryAjaxBehavior onEventDropAjaxBehavior = null;

	/** event resize */
	private JQueryAjaxBehavior onEventResizeAjaxBehavior = null;

	/** event-object drop */
	private JQueryAjaxBehavior onObjectDropAjaxBehavior = null;

	/** view render */
	private JQueryAjaxBehavior onViewDidMountAjaxBehavior = null;

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
		super();
		this.selector = Args.notNull(selector, "selector");
		this.options = Args.notNull(options, "options");
		this.listener = Args.notNull(listener, "listener");
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

		if (this.listener.isDateClickEnabled())
		{
			this.onDateClickAjaxBehavior = this.newOnDateClickAjaxBehavior(this);
			component.add(this.onDateClickAjaxBehavior);
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

		if (this.listener.isViewDidMountEnabled())
		{
			this.onViewDidMountAjaxBehavior = this.newViewDidMountAjaxBehavior(this);
			component.add(this.onViewDidMountAjaxBehavior);
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		CalendarLibrarySettings settings = CalendarLibrarySettings.get();

		// fullcalendar.min.js //
		if (settings.getJavaScriptReference() != null)
		{
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getJavaScriptReference())));
		}

		// gcal.js //
		if (settings.getGCalJavaScriptReference() != null)
		{
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getGCalJavaScriptReference())));
		}

		// locale-all.js //
		if (settings.getLocalesJavaScriptReference() != null)
		{
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getLocalesJavaScriptReference())));
		}

		/* adds and configure the busy indicator */
		StringBuilder builder = new StringBuilder();
		builder.append("const el = document.querySelector('").append(this.selector).append("');")
				.append("el.calendar = new FullCalendar.Calendar(el, ").append(options).append(");")
				.append("el.calendar.render();");

		builder.append("jQuery(\"<img id='calendar-indicator' src='").append(RequestCycleUtils.getAjaxIndicatorUrl()).append("' />\").appendTo('.fc-header-center');\n"); // allows only one calendar.
		builder.append("jQuery(document).ajaxStart(function() { jQuery('#calendar-indicator').show(); });\n");
		builder.append("jQuery(document).ajaxStop(function() { jQuery('#calendar-indicator').hide(); });\n");

		response.render(OnDomReadyHeaderItem.forScript(builder));
	}

	// Properties //

	/**
	 * Indicates whether the Calendar will be editable
	 *
	 * @return by default, true if {@link ICalendarListener#isDateClickEnabled()} is true or {@link ICalendarListener#isEventClickEnabled()} is true
	 */
	protected boolean isEditable()
	{
		return (this.onDateClickAjaxBehavior != null) || (this.onEventClickAjaxBehavior != null);
	}

	// Events //
	/**
	 * Gets a behavior option, referenced by its key
	 *
	 * @param <T> the object type
	 * @param key the option key
	 * @return {@code null} if the key does not exists
	 */
	public <T> T getOption(String key)
	{
		return this.options.get(key);
	}

	/**
	 * Sets a behavior option.
	 *
	 * @param key the option key
	 * @param value the option value
	 * @return the {@link CalendarBehavior} (this)
	 */
	public CalendarBehavior setOption(String key, Object value)
	{
		this.options.set(key, value);

		return this;
	}

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

		if (this.onDateClickAjaxBehavior != null)
		{
			this.setOption("dateClick", this.onDateClickAjaxBehavior.getCallbackFunction());
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

		if (this.onViewDidMountAjaxBehavior != null)
		{
			this.setOption("viewDidMount", this.onViewDidMountAjaxBehavior.getCallbackFunction());
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

		else if (event instanceof DateClickEvent)
		{
			DateClickEvent dayClickEvent = (DateClickEvent) event;
			this.listener.onDateClick(target, dayClickEvent.getView(), dayClickEvent.getDate(), dayClickEvent.isAllDay());
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

		else if (event instanceof ViewDidMountEvent)
		{
			ViewDidMountEvent renderEvent = (ViewDidMountEvent) event;
			this.listener.onViewDidMount(target, renderEvent.getView(), renderEvent.getStart(), renderEvent.getEnd());
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
	 * @return a new {@code OnDateClickAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDateClickAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDateClickAjaxBehavior(source);
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
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'viewDidMount' event, triggered when the user changes the view, or when any of the date navigation methods are called.
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnViewDidMountAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newViewDidMountAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnViewDidMountAjaxBehavior(source);
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
			// https://fullcalendar.io/docs/select-callback
			return new CallbackParameter[] {
					CallbackParameter.context("info"), // lf
					CallbackParameter.resolved("startDate", "info.start.toISOString()"), // retrieved
					CallbackParameter.resolved("endDate", "info.end.toISOString()"), // retrieved
					CallbackParameter.resolved("allDay", "info.allDay"), // retrieved
					CallbackParameter.resolved("viewName", "info.view.type") // retrieved
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new SelectEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'dateClick' event
	 */
	protected static class OnDateClickAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnDateClickAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			// https://fullcalendar.io/docs/dateClick
			return new CallbackParameter[] {
					CallbackParameter.context("info"), // lf
					CallbackParameter.resolved("date", "info.date.toISOString()"), // retrieved
					CallbackParameter.resolved("allDay", "info.allDay"), // retrieved
					CallbackParameter.resolved("viewName", "info.view.type") // retrieved
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DateClickEvent();
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
			// https://fullcalendar.io/docs/eventClick
			return new CallbackParameter[] {
					CallbackParameter.context("info"), // lf
					CallbackParameter.resolved("eventId", "info.event.id"),// retrieved
					CallbackParameter.resolved("viewName", "info.view.type") // retrieved
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
			// https://fullcalendar.io/docs/eventDrop
			return new CallbackParameter[] {
					CallbackParameter.context("info"), // lf
					CallbackParameter.resolved("millisDelta", "info.delta.milliseconds"), // retrieved
					CallbackParameter.resolved("allDay", "info.event.allDay"), // retrieved
					CallbackParameter.resolved("eventId", "info.event.id") // retrieved
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
			return new CallbackParameter[] {
					CallbackParameter.context("info"), // lf
					CallbackParameter.resolved("millisDelta", "info.endDelta.milliseconds"), // retrieved
					CallbackParameter.resolved("allDay", "info.event.allDay"), // retrieved
					CallbackParameter.resolved("eventId", "info.event.id") // retrieved
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
			// https://fullcalendar.io/docs/drop
			return new CallbackParameter[] {
					CallbackParameter.context("info"), // lf
					CallbackParameter.resolved("date", "info.date.toISOString()"), // retrieved
					CallbackParameter.resolved("allDay", "info.allDay"), // retrieved
					CallbackParameter.resolved("title", "info.draggedEl.getAttribute('data-title')"), // retrieved
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ObjectDropEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'viewDidMount' event
	 */
	protected static class OnViewDidMountAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnViewDidMountAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			// https://fullcalendar.io/docs/view-render-hooks
			return new CallbackParameter[] {
					CallbackParameter.context("info"),// lf
					CallbackParameter.resolved("viewName", "info.view.type"), // retrieved
					CallbackParameter.resolved("startDate", "info.view.activeStart.toISOString()"), // retrieved
					CallbackParameter.resolved("endDate", "info.view.activeEnd.toISOString()") }; // retrieved
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ViewDidMountEvent();
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
			this.start = parse(start, this.isAllDay);

			String end = RequestCycleUtils.getQueryParameterValue("endDate").toString();
			this.end = parse(end, this.isAllDay);

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
	 * Provides an event object that will be broadcasted by the {@link OnDateClickAjaxBehavior} callback
	 */
	protected static class DateClickEvent extends JQueryEvent
	{
		private final LocalDateTime day;
		private final boolean isAllDay;
		private final String viewName;

		/**
		 * Constructor
		 */
		public DateClickEvent()
		{
			this.isAllDay = RequestCycleUtils.getQueryParameterValue("allDay").toBoolean();

			String date = RequestCycleUtils.getQueryParameterValue("date").toString();
			this.day = parse(date, this.isAllDay);

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
	 * Provides an event object that will be broadcasted by the {@link OnViewDidMountAjaxBehavior} callback
	 */
	protected static class ViewDidMountEvent extends JQueryEvent
	{
		private final LocalDate start;
		private final LocalDate end;
		private final CalendarView viewName;

		/**
		 * Constructor
		 */
		public ViewDidMountEvent()
		{
			String start = RequestCycleUtils.getQueryParameterValue("startDate").toString();
			this.start = parseDate(start);

			String end = RequestCycleUtils.getQueryParameterValue("endDate").toString();
			this.end = parseDate(end);

			this.viewName = CalendarView.get(RequestCycleUtils.getQueryParameterValue("viewName").toString());
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
			return this.viewName;
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
			this.day = parse(date, this.isAllDay);

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

	protected static LocalDate parseDate(String dateStr) {
		return LocalDate.parse(dateStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	protected static LocalDateTime parse(String dateStr, boolean allDay) {
		return allDay
				? parseDate(dateStr).atStartOfDay()
				: LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
}
