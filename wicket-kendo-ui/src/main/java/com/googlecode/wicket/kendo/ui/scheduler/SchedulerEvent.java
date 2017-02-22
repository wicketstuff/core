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
package com.googlecode.wicket.kendo.ui.scheduler;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.googlecode.wicket.jquery.core.utils.DateUtils;

/**
 * Provides a scheduler event that can be used with a {@link SchedulerModel}<br>
 * If the IDs are not numbers, the datasource's schema need to reflect the type. ie:
 * 
 * <pre>
 * <code>
 * // Scheduler
 * protected void onConfigure(SchedulerDataSource dataSource)
 * {
 * 	super.onConfigure(dataSource);
 * 
 * 	Options options = SchedulerDataSource.newSchemaFields();
 * 	options.set("id", "{ type: 'string' }"); // override default type for event-id
 * 
 * 	dataSource.set("schema", String.format("{ model: { fields: %s } }", options));
 * }
 *
 * </code>
 * </pre>
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_RANGE = 1; // hour

	private Object id;
	private String title;
	private long start;
	private long end;

	private boolean allDay = false;
	private String description = null;
	private String recurrenceId = null;
	private String recurrenceRule = null;
	private String recurrenceException = null;

	/** server side */
	private boolean visible = true;

	/** fields map */
	private final Map<String, Object> fields = new HashMap<String, Object>();

	/**
	 * Constructor
	 */
	public SchedulerEvent()
	{
		this((Object) null, "", new Date());
	}

	// Constructor (Object) //

	/**
	 * Constructor<br>
	 * The end date will be the start date + {@link #DEFAULT_RANGE} hour(s)<br>
	 * <b>Caution:</b> if the id is not a number, the datasource's schema need to reflect the type
	 *
	 * @param id the event id
	 * @param title the event title
	 * @param start the start date
	 */
	public SchedulerEvent(Object id, String title, Date start)
	{
		this(id, title, start.getTime());
	}

	/**
	 * Constructor<br>
	 * The end date will be the start date + {@link #DEFAULT_RANGE} hour(s)<br>
	 * <b>Caution:</b> if the id is not a number, the datasource's schema need to reflect the type
	 *
	 * @param id the event id
	 * @param title the event title
	 * @param start the start date
	 */
	public SchedulerEvent(Object id, String title, long start)
	{
		this(id, title, start, DateUtils.addHours(start, DEFAULT_RANGE));
	}

	/**
	 * Constructor<br>
	 * <b>Caution:</b> if the id is not a number, the datasource's schema need to reflect the type
	 *
	 * @param id the event id
	 * @param title the event title
	 * @param start the start date
	 * @param end the end date
	 */
	public SchedulerEvent(Object id, String title, Date start, Date end)
	{
		this(id, title, start.getTime(), end.getTime());
	}

	/**
	 * Constructor<br>
	 * <b>Caution:</b> if the id is not a number, the datasource's schema need to reflect the type
	 *
	 * @param id the event id
	 * @param title the event title
	 * @param start the start date
	 * @param end the end date
	 */
	public SchedulerEvent(Object id, String title, long start, long end)
	{
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}

	// Constructor (Number) //

	/**
	 * Constructor<br>
	 * The end date will be the start date + {@link #DEFAULT_RANGE} hour(s)
	 *
	 * @param id the event id
	 * @param title the event title
	 * @param start the start date
	 */
	public SchedulerEvent(Number id, String title, Date start)
	{
		this(id, title, start.getTime());
	}

	/**
	 * Constructor<br>
	 * The end date will be the start date + {@link #DEFAULT_RANGE} hour(s)
	 *
	 * @param id the event id
	 * @param title the event title
	 * @param start the start date
	 */
	public SchedulerEvent(Number id, String title, long start)
	{
		this(id, title, start, DateUtils.addHours(start, DEFAULT_RANGE));
	}

	/**
	 * Constructor
	 *
	 * @param id the event id
	 * @param title the event title
	 * @param start the start date
	 * @param end the end date
	 */
	public SchedulerEvent(Number id, String title, Date start, Date end)
	{
		this(id, title, start.getTime(), end.getTime());
	}

	/**
	 * Constructor
	 *
	 * @param id the event id
	 * @param title the event title
	 * @param start the start date
	 * @param end the end date
	 */
	public SchedulerEvent(Number id, String title, long start, long end)
	{
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}

	// Properties //

	/**
	 * Gets the unique identifier of the scheduler event
	 *
	 * @return the id
	 */
	public Object getId()
	{
		return this.id;
	}

	/**
	 * Gets the unique identifier of the scheduler event<br>
	 * <b>Caution:</b> not type-safe
	 *
	 * @param type the class type
	 * @param <T> the type
	 * @return the id, casted to the supplied type (unchecked)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getId(Class<T> type)
	{
		return (T) this.id;
	}

	/**
	 * Sets the unique identifier of the scheduler event
	 *
	 * @param id the id
	 */
	public void setId(Object id)
	{
		this.id = id;
	}

	/**
	 * Gets the title of the event which is displayed in the scheduler views
	 *
	 * @return the title
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * Sets the title of the event which is displayed in the scheduler views
	 *
	 * @param title the title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Gets the text description of the scheduler event
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return this.description;
	}

	/**
	 * Sets the text description of the scheduler event
	 *
	 * @param description the description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Sets the date at which the event starts
	 *
	 * @return the start date
	 */
	public Date getStart()
	{
		return new Date(this.start);
	}

	/**
	 * Sets the date at which the event starts
	 *
	 * @param date the start date
	 */
	public void setStart(Date date)
	{
		this.setStart(date.getTime());
	}

	/**
	 * Sets the date at which the event starts
	 *
	 * @param date the start date
	 */
	public void setStart(long date)
	{
		this.start = date;
	}

	/**
	 * Sets the date at which the event ends
	 *
	 * @return the end date
	 */
	public Date getEnd()
	{
		return new Date(this.end);
	}

	/**
	 * Gets the date at which the event ends
	 *
	 * @param date the end date
	 */
	public void setEnd(Date date)
	{
		this.setEnd(date.getTime());
	}

	/**
	 * Gets the date at which the event ends
	 *
	 * @param date the end date
	 */
	public void setEnd(long date)
	{
		this.end = date;
	}

	/**
	 * Indicates whether the event is "all day" or not
	 *
	 * @return true or false
	 */
	public boolean isAllDay()
	{
		return this.allDay;
	}

	/**
	 * Sets if the event is "all day" or not
	 *
	 * @param allDay true or false
	 */
	public void setAllDay(boolean allDay)
	{
		this.allDay = allDay;
	}

	/**
	 * Indicates whether the event is visible<br>
	 * If {@code false}, it will not be added to the JSON response
	 * 
	 * @return {@code true} or {@code false}
	 */
	public boolean isVisible()
	{
		return this.visible;
	}

	/**
	 * Sets whether the event is visible
	 * 
	 * @param visible {@code true} or {@code false}
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	// recurrence //

	/**
	 * Gets the id of the recurrence parent.
	 *
	 * @return the id of the recurrence parent
	 */
	public String getRecurrenceId()
	{
		return recurrenceId;
	}

	/**
	 * Sets the id of the recurrence parent. If set the current event is a recurrence exception.
	 *
	 * @param id the id of the recurrence parent
	 */
	public void setRecurrenceId(String id)
	{
		this.recurrenceId = id;
	}

	/**
	 * Gets the recurrence rule which describes the repetition pattern of the event. Follows the rfc5545 specification.
	 *
	 * @return the recurrence rule
	 */
	public String getRecurrenceRule()
	{
		return recurrenceRule;
	}

	/**
	 * Sets the recurrence rule which describes the repetition pattern of the event. Follows the rfc5545 specification.
	 *
	 * @param rule the recurrence rule
	 */
	public void setRecurrenceRule(String rule)
	{
		this.recurrenceRule = rule;
	}

	/**
	 * Gets the recurrence exception
	 *
	 * @return the recurrence exception
	 */
	public String getRecurrenceException()
	{
		return recurrenceException;
	}

	/**
	 * Sets the recurrence exception
	 *
	 * @param exception the recurrence exception
	 */
	public void setRecurrenceException(String exception)
	{
		this.recurrenceException = exception;
	}

	// resources //

	/**
	 * Gets the event related fields
	 *
	 * @return the event related fields
	 */
	public Set<String> getFields()
	{
		return this.fields.keySet();
	}

	/**
	 * Gets a field value
	 *
	 * @param field the field (ie: 'resourceId')
	 * @return the value, which is either a {@code String} (default) or an {@code Object}
	 */
	public final Object getValue(String field)
	{
		return this.fields.get(field);
	}

	/**
	 * Sets a resource value
	 *
	 * @param field the field
	 * @param value the value
	 */
	public final void setValue(String field, Object value)
	{
		this.fields.put(field, value);
	}

	// Methods //

	/**
	 * Visitor accept method
	 *
	 * @param visitor the {@link ISchedulerVisitor}
	 */
	public final void accept(ISchedulerVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return this.title;
	}

	// Static //

	public static boolean isNew(SchedulerEvent event)
	{
		return event.id == null;
	}
}
