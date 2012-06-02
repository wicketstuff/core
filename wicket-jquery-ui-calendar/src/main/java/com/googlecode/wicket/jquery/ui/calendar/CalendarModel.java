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
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Base class for implementing the list model of {@link CalendarEvent} to be retrieved.<br/>
 * {@link Calendar} widget takes those model in constructor; the inheriting class should be able to {@link #load()} events depending on the interval defined by {@link #getStart()} and {@link #getEnd()} dates.
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 *
 */
public abstract class CalendarModel extends LoadableDetachableModel<List<? extends CalendarEvent>>
{
	private static final long serialVersionUID = 1L;

	private Date start;
	private Date end;
	
	/**
	 * Constructor
	 */
	public CalendarModel()
	{
		this.start = null;
		this.end = null;
	}
	
	/**
	 * Gets the start date, to be used to {@link #load()} {@link CalendarEvent}<code>s</code>
	 * @return the start date
	 */
	public final Date getStart()
	{
		return this.start;
	}
	
	/**
	 * Sets the start date.<br/>
	 * This method has package visibility, it is only called by {@link CalendarModelBehavior}
	 * @param date the start date
	 */
	void setStart(Date date)
	{
		this.start = date;
	}
	
	/**
	 * Gets the end date, to be used to {@link #load()} {@link CalendarEvent}<code>s</code>
	 * @return the start date
	 */
	public Date getEnd()
	{
		return this.end;
	}
	
	/**
	 * Gets the end date.<br/>
	 * This method has package visibility, it is only called by {@link CalendarModelBehavior}
	 * @param date the start date
	 */
	void setEnd(Date date)
	{
		this.end = date;
	}
}
