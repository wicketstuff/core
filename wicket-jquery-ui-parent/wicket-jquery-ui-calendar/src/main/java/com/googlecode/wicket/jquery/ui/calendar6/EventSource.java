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

import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides an event source Object
 *
 * @author Sebastien Briquet - sebfz1
 * @see <a href="https://fullcalendar.io/docs/event-source-object">https://fullcalendar.io/docs/event-source-object</a>
 */
public class EventSource implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private String color = null;
	private String backgroundColor = null;
	private String borderColor = null;
	private String textColor = null;
	private String className = null;
	private Boolean editable = null;
	private Boolean startEditable = null;
	private Boolean durationEditable = null;
	private String rendering = null;
	private Boolean overlap = null;
	private String constraint = null;
	private Boolean defaultAllDay = null;
	private String eventDataTransform = null;

	/**
	 * Constructor
	 */
	public EventSource()
	{
		// noop
	}

	// Properties //

	/**
	 * Gets Event Object's color for this source.
	 *
	 * @return any of the CSS color formats such #f00, #ff0000, rgb(255,0,0), or red.
	 */
	public String getColor()
	{
		return this.color;
	}

	/**
	 * Sets every Event Object's color for this source.
	 *
	 * @param color any of the CSS color formats such #f00, #ff0000, rgb(255,0,0), or red.
	 * @return this, for chaining
	 */
	public EventSource setColor(String color)
	{
		this.color = color;

		return this;
	}

	/**
	 * Gets Event Object's backgroundColor for this source.
	 *
	 * @return any of the CSS color formats such #f00, #ff0000, rgb(255,0,0), or red.
	 */
	public String getBackgroundColor()
	{
		return this.backgroundColor;
	}

	/**
	 * Sets every Event Object's backgroundColor for this source.
	 *
	 * @param color any of the CSS color formats such #f00, #ff0000, rgb(255,0,0), or red.
	 * @return this, for chaining
	 */
	public EventSource setBackgroundColor(String color)
	{
		this.backgroundColor = color;

		return this;
	}

	/**
	 * Gets Event Object's borderColor for this source.
	 *
	 * @return any of the CSS color formats such #f00, #ff0000, rgb(255,0,0), or red.
	 */
	public String getBorderColor()
	{
		return this.borderColor;
	}

	/**
	 * Sets every Event Object's borderColor for this source.
	 *
	 * @param color any of the CSS color formats such #f00, #ff0000, rgb(255,0,0), or red.
	 * @return this, for chaining
	 */
	public EventSource setBorderColor(String color)
	{
		this.borderColor = color;

		return this;
	}

	/**
	 * Gets Event Object's textColor for this source.
	 *
	 * @return any of the CSS color formats such #f00, #ff0000, rgb(255,0,0), or red.
	 */
	public String getTextColor()
	{
		return this.textColor;
	}

	/**
	 * Sets every Event Object's textColor for this source.
	 *
	 * @param color any of the CSS color formats such #f00, #ff0000, rgb(255,0,0), or red.
	 * @return this, for chaining
	 */
	public EventSource setTextColor(String color)
	{
		this.textColor = color;

		return this;
	}

	/**
	 * Gets Event Object's className for this source.
	 *
	 * @return the CSS class name, can be an array.
	 */
	public String getClassName()
	{
		return this.className;
	}

	/**
	 * Sets every Event Object's className for this source.
	 *
	 * @param name the CSS class name, can be an array.
	 * @return this, for chaining
	 */
	public EventSource setClassName(String name)
	{
		this.className = name;

		return this;
	}

	/**
	 * Gets Event Object's editable for this source.
	 *
	 * @return true or false
	 */
	public Boolean getEditable()
	{
		return this.editable;
	}

	/**
	 * Sets every Event Object's editable for this source.
	 *
	 * @param editable true or false
	 * @return this, for chaining
	 */
	public EventSource setEditable(Boolean editable)
	{
		this.editable = editable;

		return this;
	}

	/**
	 * Gets every Event Object's startEditable for this source.
	 *
	 * @return true or false
	 */
	public Boolean getStartEditable()
	{
		return this.startEditable;
	}

	/**
	 * Sets every Event Object's startEditable for this source.
	 *
	 * @param editable true or false
	 * @return this, for chaining
	 */
	public EventSource setStartEditable(Boolean editable)
	{
		this.startEditable = editable;

		return this;
	}

	/**
	 * Gets every Event Object's durationEditable for this source.
	 *
	 * @return true or false
	 */
	public Boolean getDurationEditable()
	{
		return this.durationEditable;
	}

	/**
	 * Sets every Event Object's durationEditable for this source.
	 *
	 * @param editable true or false
	 * @return this, for chaining
	 */
	public EventSource setDurationEditable(Boolean editable)
	{
		this.durationEditable = editable;

		return this;
	}

	/**
	 * Gets Event Object's rendering for this source.
	 *
	 * @return empty, "background", or "inverse-background"
	 */
	public String getRendering()
	{
		return this.rendering;
	}

	/**
	 * Sets every Event Object's rendering for this source.
	 *
	 * @param rendering empty, "background", or "inverse-background"
	 * @return this, for chaining
	 */
	public EventSource setRendering(String rendering)
	{
		this.rendering = rendering;

		return this;
	}

	/**
	 * Gets Event Object's overlap for this source.
	 *
	 * @return true or false
	 */
	public Boolean getOverlap()
	{
		return this.overlap;
	}

	/**
	 * Sets every Event Object's overlap for this source.
	 *
	 * @param overlap true or false
	 * @return this, for chaining
	 */
	public EventSource setOverlap(Boolean overlap)
	{
		this.overlap = overlap;

		return this;
	}

	/**
	 * Gets Event Object's constraint for this source.
	 *
	 * @return an event ID, "businessHours", object.
	 */
	public String getConstraint()
	{
		return this.constraint;
	}

	/**
	 * Sets every Event Object's constraint for this source.
	 *
	 * @param constraint an event ID, "businessHours", object.
	 * @return this, for chaining
	 */
	public EventSource setConstraint(String constraint)
	{
		this.constraint = constraint;

		return this;
	}

	/**
	 * Gets the defaultAllDay option, but only for this source.
	 *
	 * @return true or false
	 */
	public Boolean getDefaultAllDay()
	{
		return this.defaultAllDay;
	}

	/**
	 * Sets the defaultAllDay option, but only for this source.
	 *
	 * @param allDay true or false
	 * @return this, for chaining
	 */
	public EventSource setDefaultAllDay(Boolean allDay)
	{
		this.defaultAllDay = allDay;

		return this;
	}

	/**
	 * Gets the eventDataTransform callback, but only for this source.
	 *
	 * @return a function that must return a new object in the Event Object format.
	 */
	public String getEventDataTransform()
	{
		return this.eventDataTransform;
	}

	/**
	 * Sets the eventDataTransform callback, but only for this source.
	 *
	 * @param function a function that must return a new object in the Event Object format.
	 * @return this, for chaining
	 */
	public EventSource setEventDataTransform(String function)
	{
		this.eventDataTransform = function;

		return this;
	}

	// Methods //

	protected Options createOptions()
	{
		Options options = new Options();

		if (this.color != null)
		{
			options.set("color", Options.asString(this.color));
		}

		if (this.backgroundColor != null)
		{
			options.set("backgroundColor", Options.asString(this.backgroundColor));
		}

		if (this.borderColor != null)
		{
			options.set("borderColor", Options.asString(this.borderColor));
		}

		if (this.textColor != null)
		{
			options.set("textColor", Options.asString(this.textColor));
		}

		if (this.className != null)
		{
			options.set("className", Options.asString(this.className));
		}

		if (this.editable != null)
		{
			options.set("editable", this.editable);
		}

		if (this.startEditable != null)
		{
			options.set("startEditable", this.startEditable);
		}

		if (this.durationEditable != null)
		{
			options.set("durationEditable", this.durationEditable);
		}

		if (this.rendering != null)
		{
			options.set("rendering", Options.asString(this.rendering));
		}

		if (this.overlap != null)
		{
			options.set("overlap", this.overlap);
		}

		if (this.constraint != null)
		{
			options.set("constraint", Options.asString(this.constraint));
		}

		if (this.defaultAllDay != null)
		{
			options.set("defaultAllDay", this.defaultAllDay);
		}

		if (this.eventDataTransform != null)
		{
			options.set("eventDataTransform", Options.asString(this.eventDataTransform));
		}

		return options;
	}

	/**
	 * Gets the JSON representation of this {@link EventSource}
	 */
	@Override
	public String toString()
	{
		return this.createOptions().toString();
	}

	/**
	 * Provides a Google Calendar {@link EventSource}
	 */
	public static class GoogleCalendar extends EventSource
	{
		private static final long serialVersionUID = 1L;

		private final String googleCalendarId;
		private String googleCalendarApiKey = null;
		private String googleCalendarError = null;

		/**
		 * Constructor
		 *
		 * @param calendarId the calendar id
		 */
		public GoogleCalendar(String calendarId)
		{
			this.googleCalendarId = calendarId;
		}

		/**
		 * Constructor
		 *
		 * @param calendarId the calendar id
		 * @param apiKey the calendar api key
		 */
		public GoogleCalendar(String calendarId, String apiKey)
		{
			this(calendarId);

			this.googleCalendarApiKey = apiKey;
		}

		// Properties //

		/**
		 * Gets the calendar id
		 *
		 * @return the calendar id
		 */
		public String getGoogleCalendarId()
		{
			return this.googleCalendarId;
		}


		/**
		 * Gets the calendar api key
		 *
		 * @return the calendar api key
		 */
		public String getGoogleCalendarApiKey()
		{
			return this.googleCalendarApiKey;
		}
		/**
		 * Sets the calendar api key
		 *
		 * @param apiKey the calendar api key
		 * @return this, for chaining
		 */
		public GoogleCalendar setGoogleCalendarApiKey(String apiKey)
		{
			this.googleCalendarApiKey = apiKey;

			return this;
		}

		/**
		 * Gets the error callback
		 *
		 * @return the callback
		 */
		public String getGoogleCalendarError()
		{
			return this.googleCalendarError;
		}

		/**
		 * Sets the error callback
		 *
		 * @param callback the callback, in case of error
		 * @return this, for chaining
		 */
		public GoogleCalendar setGoogleCalendarError(String callback)
		{
			this.googleCalendarError = callback;

			return this;
		}

		// Methods //

		@Override
		protected Options createOptions()
		{
			Options options = super.createOptions();

			options.set("googleCalendarId", Options.asString(this.googleCalendarId));

			if (this.googleCalendarApiKey != null)
			{
				options.set("googleCalendarApiKey", Options.asString(this.googleCalendarApiKey));
			}

			if (this.googleCalendarError != null)
			{
				options.set("googleCalendarError", Options.asString(this.googleCalendarError));
			}

			return options;
		}
	}
}
