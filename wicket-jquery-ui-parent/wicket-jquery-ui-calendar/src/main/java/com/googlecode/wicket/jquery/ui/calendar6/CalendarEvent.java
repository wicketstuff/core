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

import java.io.Serializable;
import java.time.LocalDateTime;

import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a base bean that can be used with a {@link CalendarModel}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class CalendarEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String id;
	private String title;

	private LocalDateTime start;
	private LocalDateTime end;

	private CharSequence url = null;
	private String source = null;
	private Boolean allDay = true;
	private Boolean editable = null;

	// styling //
	private String className = null;
	private String color = null;
	private String backgroundColor = null;
	private String borderColor = null;
	private String textColor = null;

	public CalendarEvent(String title, LocalDateTime date)
	{
		this(null, title, date, null);
	}

	public CalendarEvent(String title, LocalDateTime start, LocalDateTime end)
	{
		this(null, title, start, end);
	}

	public CalendarEvent(String id, String title, LocalDateTime date)
	{
		this(id, title, date, null);
	}

	public CalendarEvent(String id, String title, LocalDateTime start, LocalDateTime end)
	{
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public CharSequence getUrl()
	{
		return this.url;
	}

	public void setUrl(CharSequence url)
	{
		this.url = url;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSource()
	{
		return this.source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public Boolean isAllDay()
	{
		return this.allDay;
	}

	public void setAllDay(Boolean allDay)
	{
		this.allDay = allDay;
	}

	public LocalDateTime getStart()
	{
		return this.start;
	}

	public void setStart(LocalDateTime start)
	{
		this.start = start;
	}

	public LocalDateTime getEnd()
	{
		return this.end;
	}

	public void setEnd(LocalDateTime end)
	{
		this.end = end;
	}

	/**
	 * Overrides the master editable option for this single event.
	 *
	 * @return true or false
	 */
	public Boolean isEditable()
	{
		return this.editable;
	}

	public void setEditable(Boolean editable)
	{
		this.editable = editable;
	}

	// styling //
	public String getClassName()
	{
		return this.className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String getColor()
	{
		return this.color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public void setColor(String backgroundColor, String borderColor)
	{
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
	}

	public String getBackgroundColor()
	{
		return this.backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public String getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(String borderColor)
	{
		this.borderColor = borderColor;
	}

	public String getTextColor()
	{
		return this.textColor;
	}

	public void setTextColor(String textColor)
	{
		this.textColor = textColor;
	}

	/**
	 * Create an {@link Options} JSON-object that will embed the (default) properties of this {@link CalendarEvent}
	 *
	 * @return a new {@code Options} object
	 */
	protected Options createOptions()
	{
		Options options = new Options();

		options.set("id", this.id);

		if (this.title != null)
		{
			options.set("title", Options.asString(this.title));
		}

		if (this.start != null)
		{
			options.set("start", Options.asString(this.start));
		}

		if (this.end != null)
		{
			options.set("end", Options.asString(this.end));
		}

		if (this.url != null)
		{
			options.set("url", Options.asString(this.url));
		}

		if (this.source != null)
		{
			options.set("source", Options.asString(this.source));
		}

		if (this.allDay != null)
		{
			options.set("allDay", this.allDay);
		}

		if (this.editable != null)
		{
			options.set("editable", editable);
		}

		// styling //
		if (this.className != null)
		{
			options.set("className", Options.asString(this.className));
		}

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

		return options;
	}

	/**
	 * Gets the JSON representation of this {@link CalendarEvent}
	 */
	@Override
	public String toString()
	{
		return this.createOptions().toString();
	}

	/**
	 * Visitor accept method
	 *
	 * @param visitor the {@link ICalendarVisitor}
	 */
	public final void accept(ICalendarVisitor visitor)
	{
		visitor.visit(this);
	}
}
