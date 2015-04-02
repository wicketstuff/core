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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.kendo.ui.scheduler.resource.Id;

/**
 * Provides a base Bean that can be used with a {@link SchedulerModel}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_RANGE = 1; // hour

	/** new event id */
	public static final int NEW_ID = 0;

	public static boolean isNew(SchedulerEvent event)
	{
		return event != null && event.id == NEW_ID;
	}

	private int id;
	private String title;
	private String description;

	private Date start;
	private Date end;
	private boolean allDay;

	private String recurrenceId;
	private String recurrenceRule;
	private String recurrenceException;

	/** server side */
	private boolean visible = true;

	/** resources map */
	private Map<String, Object> resources = new HashMap<String, Object>();

	/**
	 * Constructor
	 */
	public SchedulerEvent()
	{
		this(NEW_ID, "", new Date());
	}

	/**
	 * Constructor<br/>
	 * The end date will be the start date + {@value #DEFAULT_RANGE} hour(s)
	 *
	 * @param id - the event id
	 * @param title - the event title
	 * @param start - the start date
	 */
	public SchedulerEvent(int id, String title, long start)
	{
		this(id, title, new Date(start));
	}

	/**
	 * Constructor<br/>
	 * The end date will be the start date + {@value #DEFAULT_RANGE} hour(s)
	 *
	 * @param id - the event id
	 * @param title - the event title
	 * @param start - the start date
	 */
	public SchedulerEvent(int id, String title, Date start)
	{
		this(id, title, start, DateUtils.addHours(start, DEFAULT_RANGE));
	}

	/**
	 * Constructor
	 *
	 * @param id - the event id
	 * @param title - the event title
	 * @param start - the start date
	 * @param end - the end date
	 */
	public SchedulerEvent(int id, String title, long start, long end)
	{
		this(id, title, new Date(start), new Date(end));
	}

	/**
	 * Constructor
	 *
	 * @param id - the event id
	 * @param title - the event title
	 * @param start - the start date
	 * @param end - the end date
	 */
	public SchedulerEvent(int id, String title, Date start, Date end)
	{
		this.id = id;
		this.title = title;
		this.description = null;

		this.start = start;
		this.end = end;
		this.allDay = false;

		this.recurrenceId = null;
		this.recurrenceRule = null;
		this.recurrenceException = null;
	}

	// Properties //

	/**
	 * Gets the unique identifier of the scheduler event
	 *
	 * @return the id
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * Sets the unique identifier of the scheduler event
	 *
	 * @param id - the id
	 */
	public void setId(int id)
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
	 * @param title - the title
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
	 * @param description - the description
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
		return this.start;
	}

	/**
	 * Sets the date at which the event starts
	 *
	 * @param date - the start date
	 */
	public void setStart(Date date)
	{
		this.start = date;
	}

	/**
	 * Sets the date at which the event starts
	 *
	 * @param date - the start date
	 */
	public void setStart(long date)
	{
		this.start = new Date(date);
	}

	/**
	 * Sets the date at which the event ends
	 *
	 * @return the end date
	 */
	public Date getEnd()
	{
		return this.end;
	}

	/**
	 * Gets the date at which the event ends
	 *
	 * @param date - the end date
	 */
	public void setEnd(Date date)
	{
		this.end = date;
	}

	/**
	 * Gets the date at which the event ends
	 *
	 * @param date - the end date
	 */
	public void setEnd(long date)
	{
		this.end = new Date(date);
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
	 * @param allDay - true or false
	 */
	public void setAllDay(boolean allDay)
	{
		this.allDay = allDay;
	}

	/**
	 * Indicates whether the event is visible<br/>
	 * If <tt>false</tt>, it will not be added to the JSON response
	 * 
	 * @return <tt>true</tt> or <tt>false</tt>
	 */
	public boolean isVisible()
	{
		return this.visible;
	}

	/**
	 * Sets whether the event is visible
	 * 
	 * @param visible <tt>true</tt> or <tt>false</tt>
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
	 * @param id - the id of the recurrence parent
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
	 * @param rule - the recurrence rule
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
	 * Gets the resources related fields
	 *
	 * @return the resources related fields
	 */
	public Set<String> getFields()
	{
		return this.resources.keySet();
	}

	/**
	 * Gets a resource value, identified by its field
	 *
	 * @param field - the resource field (ie: 'resourceId')
	 * @return the value, which is either a <tt>String</tt>, an <tt>Integer</tt>, a <tt>List&lt;String&gt;</tt> or a <tt>List&lt;Integer&gt;</tt>
	 */
	@SuppressWarnings("unchecked")
	public final Object getValue(String field)
	{
		Object object = this.resources.get(field); // either an Id or a List<Id<?>>

		if (object instanceof Id<?>)
		{
			return ((Id<?>) object).get();
		}

		if (object instanceof List<?>)
		{
			List<Object> list = new ArrayList<Object>();

			for (Id<?> id : (List<Id<?>>) object)
			{
				list.add(id.get());
			}

			return list;
		}

		return null;
	}

	/**
	 * Sets a resource value
	 *
	 * @param field - the resource field (ie: 'resourceId')
	 * @param id - the id-value
	 */
	public final void setResource(String field, String id)
	{
		this.resources.put(field, Id.valueOf(id));
	}

	/**
	 * Sets a resource value
	 *
	 * @param field - the resource field (ie: 'resourceId')
	 * @param id - the id-value
	 */
	public final void setResource(String field, Integer id)
	{
		this.resources.put(field, Id.valueOf(id));
	}

	/**
	 * Sets a resource value
	 *
	 * @param field - the resource field (ie: 'resourceId')
	 * @param ids - the id-values
	 */
	public final void setResource(String field, List<?> ids)
	{
		this.resources.put(field, Id.valueOf(ids));
	}

	// methods //

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
}
