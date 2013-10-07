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

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;
import org.apache.wicket.util.time.Duration;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.calendar.settings.CalendarLibrarySettings;
import com.googlecode.wicket.jquery.ui.calendar.settings.ICalendarLibrarySettings;

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

	/**
	 * Gets the {@link ICalendarLibrarySettings}
	 *
	 * @return Default internal {@link ICalendarLibrarySettings} instance if {@link Application}'s {@link IJavaScriptLibrarySettings} is not an instance of {@link ICalendarLibrarySettings}
	 */
	private static ICalendarLibrarySettings getLibrarySettings()
	{
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof ICalendarLibrarySettings))
		{
			return (ICalendarLibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return CalendarLibrarySettings.get();
	}


	private JQueryAjaxBehavior onDayClickBehavior; // day click
	private JQueryAjaxBehavior onSelectBehavior = null; // date range-select behavior;

	private JQueryAjaxBehavior onEventClickBehavior; // event click
	private JQueryAjaxBehavior onEventDropBehavior = null; // event drop
	private JQueryAjaxBehavior onEventResizeBehavior = null; // event resize

	private JQueryAjaxBehavior onViewRenderBehavior = null; // view render


	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public CalendarBehavior(final String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public CalendarBehavior(final String selector, Options options)
	{
		super(selector, METHOD, options);

		this.initReferences();
	}

	/**
	 * Initializes CSS & JavaScript resource references
	 */
	private void initReferences()
	{
		ICalendarLibrarySettings settings = getLibrarySettings();

		// fullcalendar.css //
		if (settings.getCalendarStyleSheetReference() != null)
		{
			this.add(settings.getCalendarStyleSheetReference());
		}

		// fullcalendar.min.js //
		if (settings.getCalendarJavaScriptReference() != null)
		{
			this.add(settings.getCalendarJavaScriptReference());
		}

		// gcal.js //
		if (settings.getGCalJavaScriptReference() != null)
		{
			this.add(settings.getGCalJavaScriptReference());
		}
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

		if (this.isViewRenderEnabled())
		{
			component.add(this.onViewRenderBehavior = this.newOnViewRenderBehavior());
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);

		/* adds and configure the busy indicator */
		StringBuilder builder = new StringBuilder();

		builder.append("jQuery(function(){\n");
		builder.append("jQuery(\"<img id='calendar-indicator' src='").append(RequestCycle.get().urlFor(handler)).append("' />\").appendTo('.fc-header-center');\n"); //allows only one calendar.
		builder.append("jQuery(document).ajaxStart(function() { jQuery('#calendar-indicator').show(); });\n");
		builder.append("jQuery(document).ajaxStop(function() { jQuery('#calendar-indicator').hide(); });\n");
		builder.append("});\n");

		response.render(JavaScriptHeaderItem.forScript(builder, this.getClass().getSimpleName() + "-indicator"));
	}


	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("editable", this.isEditable());
		this.setOption("selectable", this.isSelectable());
		this.setOption("selectHelper", this.isSelectable());
		this.setOption("disableDragging", !this.isEventDropEnabled());
		this.setOption("disableResizing", !this.isEventResizeEnabled());

		if (this.onDayClickBehavior != null)
		{
			this.setOption("dayClick", this.onDayClickBehavior.getCallbackFunction());
		}

		if (this.onSelectBehavior != null)
		{
			this.setOption("select", this.onSelectBehavior.getCallbackFunction());
		}

		if (this.onEventClickBehavior != null)
		{
			this.setOption("eventClick", this.onEventClickBehavior.getCallbackFunction());
		}

		if (this.onEventDropBehavior != null)
		{
			this.setOption("eventDrop", this.onEventDropBehavior.getCallbackFunction());
		}

		if (this.onEventResizeBehavior != null)
		{
			this.setOption("eventResize", this.onEventResizeBehavior.getCallbackFunction());
		}

		if (this.onViewRenderBehavior != null)
		{
			this.setOption("viewRender", this.onViewRenderBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DayClickEvent)
		{
			DayClickEvent dayClickEvent = (DayClickEvent) event;
			this.onDayClick(target, dayClickEvent.getView(), dayClickEvent.getDate(), dayClickEvent.isAllDay());
		}

		else if (event instanceof SelectEvent)
		{
			SelectEvent selectEvent = (SelectEvent) event;
			this.onSelect(target, selectEvent.getView(), selectEvent.getStart(), selectEvent.getEnd(), selectEvent.isAllDay());
		}

		else if (event instanceof ClickEvent)
		{
			ClickEvent clickEvent = (ClickEvent) event;
			this.onEventClick(target, clickEvent.getView(), clickEvent.getEventId());
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

		else if (event instanceof ViewRenderEvent)
		{
			ViewRenderEvent renderEvent = (ViewRenderEvent) event;
			this.onViewRender(target, renderEvent.getView());
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
						CallbackParameter.context("view"),
						CallbackParameter.resolved("viewName", "view.name")
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
						CallbackParameter.converted("startDate", "startDate.getTime()"),
						CallbackParameter.converted("endDate", "endDate.getTime()"),
						CallbackParameter.explicit("allDay"),
						CallbackParameter.context("jsEvent"),
						CallbackParameter.context("view"),
						CallbackParameter.resolved("viewName", "view.name")
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
						CallbackParameter.resolved("eventId", "event.id"),
						CallbackParameter.resolved("viewName", "view.name")
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
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				//http://arshaw.com/fullcalendar/docs/event_ui/eventResize/
				//function(event, dayDelta, minuteDelta, allDay, revertFunc, jsEvent, ui, view) {  }
				return new CallbackParameter[] {
						CallbackParameter.context("event"),
						CallbackParameter.explicit("dayDelta"), //retrieved
						CallbackParameter.explicit("minuteDelta"), //retrieved
						CallbackParameter.explicit("allDay"), //retrieved
						CallbackParameter.context("revertFunc"),
						CallbackParameter.context("jsEvent"),
						CallbackParameter.context("ui"),
						CallbackParameter.context("view"),
						CallbackParameter.resolved("eventId", "event.id") //retrieved
				};
			}

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
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

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

			@Override
			protected JQueryEvent newEvent()
			{
				return new ResizeEvent();
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user changes the view, or when any of the date navigation methods are called.
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnViewRenderBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				//http://arshaw.com/fullcalendar/docs/display/viewRender/
				//function(view, element) { }
				return new CallbackParameter[] {
						CallbackParameter.context("view"),
						CallbackParameter.context("element"),
						CallbackParameter.resolved("viewName", "view.name")
				};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ViewRenderEvent();
			}
		};
	}


	// Event classes //
	/**
	 * An event object that will be broadcasted when the user clicks on a day cell
	 */
	protected static class DayClickEvent extends JQueryEvent
	{
		private final Date day;
		private final boolean isAllDay;
		private final String viewName;

		/**
		 * Constructor
		 */
		public DayClickEvent()
		{
			long date = RequestCycleUtils.getQueryParameterValue("date").toLong();
			this.day = new Date(date);

			this.isAllDay = RequestCycleUtils.getQueryParameterValue("allDay").toBoolean();
			this.viewName = RequestCycleUtils.getQueryParameterValue("viewName").toString();
		}

		/**
		 * Gets the event date
		 * @return the date
		 */
		public Date getDate()
		{
			return this.day;
		}

		/**
		 * Indicated whether this event is an 'all-day' event
		 * @return true or false
		 */
		public boolean isAllDay()
		{
			return this.isAllDay;
		}

		/**
		 * Gets the current {@link CalendarView}
		 * @return the view name
		 */
		public CalendarView getView()
		{
	        return CalendarView.get(this.viewName);
	    }
	}

	/**
	 * An event object that will be broadcasted when the user select a cell range
	 */
	protected static class SelectEvent extends JQueryEvent
	{
		private final Date start;
		private final Date end;
		private final boolean isAllDay;
		private final String viewName;

		public SelectEvent()
		{
			long start = RequestCycleUtils.getQueryParameterValue("startDate").toLong();
			this.start = new Date(start);

			long end = RequestCycleUtils.getQueryParameterValue("endDate").toLong();
			this.end = new Date(end);

			this.isAllDay = RequestCycleUtils.getQueryParameterValue("allDay").toBoolean();
			this.viewName = RequestCycleUtils.getQueryParameterValue("viewName").toString();
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

		/**
		 * Gets the current {@link CalendarView}
		 * @return the view name
		 */
		public CalendarView getView()
		{
			return CalendarView.get(this.viewName);
		}
	}

	/**
	 * An event object that will be broadcasted when the user clicks on an event
	 */
	protected static class ClickEvent extends JQueryEvent
	{
		private final int eventId;
		private final String viewName;

		/**
		 * Constructor
		 */
		public ClickEvent()
		{
			this.eventId = RequestCycleUtils.getQueryParameterValue("eventId").toInt();
			this.viewName = RequestCycleUtils.getQueryParameterValue("viewName").toString();
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
		 * Gets the current {@link CalendarView}
		 * @return the view name
		 */
		public CalendarView getView()
		{
			return CalendarView.get(this.viewName);
		}
	}

	/**
	 * An event object that will be broadcasted when the calendar loads and every time a different date-range is displayed.
	 */
	protected static class ViewRenderEvent extends JQueryEvent
	{
		private final String viewName;

		/**
		 * Constructor
		 */
		public ViewRenderEvent()
		{
			this.viewName = RequestCycleUtils.getQueryParameterValue("viewName").toString();
		}

		/**
		 * Gets the current {@link CalendarView}
		 * @return the view name
		 */
		public CalendarView getView()
		{
	        return CalendarView.get(this.viewName);
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
