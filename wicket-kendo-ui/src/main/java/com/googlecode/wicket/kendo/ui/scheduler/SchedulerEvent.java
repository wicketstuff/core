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

import org.apache.wicket.ajax.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.utils.DateUtils;

/**
 * Provides a base Bean that can be used with a {@link SchedulerModel}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerEvent extends JQueryEvent implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerEvent.class);

	private static final int DEFAULT_RANGE = 1; // hour

	/** new event id */
	public static int NEW_ID = 0;

	public static boolean isNew(SchedulerEvent event)
	{
		return event != null && event.id == NEW_ID;
	}

	// id Number - the unique identifier of the scheduler event. Events whose id is not set are considered as "new".
	// title String - the title of the event which is displayed in the scheduler views.
	// start Date - the date at which the event starts.
	// end Date - the date at which the event ends.
	// isAllDay Boolean - if the event is "all day" or not.

	// description String - the text description of the scheduler event.
	// recurrenceException String - the recurrence exceptions.
	// recurrenceId String|Number|Object - the id of the recurrence parent. If set the current event is a recurrence exception.
	// recurrenceRule String - the recurrence rule which describes the repetition pattern of the event. Follows the rfc5545 specification.

	private int id;
	private String title;

	private Date start;
	private Date end;
	private boolean allDay = false;

	/**
	 * Constructor
	 */
	protected SchedulerEvent()
	{

	}

	/**
	 * Constructor<br/>
	 * The end date will be the start date + {@value #DEFAULT_RANGE} hour(s)
	 *
	 * @param id the event id
	 * @param title the event title
	 * @param start the start date
	 */
	public SchedulerEvent(int id, String title, Date start)
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
	public SchedulerEvent(int id, String title, Date start, Date end)
	{
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Date getStart()
	{
		return this.start;
	}

	public void setStart(Date start)
	{
		this.start = start;
	}

	public void setStart(long start)
	{
		this.start = new Date(start);
	}

	public Date getEnd()
	{
		return this.end;
	}

	public void setEnd(Date end)
	{
		this.end = end;
	}

	public void setEnd(long end)
	{
		this.end = new Date(end);
	}

	public boolean isAllDay()
	{
		return this.allDay;
	}

	public void setAllDay(boolean allDay)
	{
		this.allDay = allDay;
	}

	/**
	 * Gets the JSON representation of this {@link SchedulerEvent}
	 */
	@Override
	public String toString()
	{
		try
		{
			return SchedulerEventFactory.toJson(this);
		}
		catch (JSONException e)
		{
			LOG.error(e.getMessage(), e);
		}

		return "";
	}

	/**
	 * Visitor accept method
	 *
	 * @param visitor the {@link ISchedulerVisitor}
	 */
	public final void accept(ISchedulerVisitor visitor)
	{
		visitor.visit(this);
	}
}
