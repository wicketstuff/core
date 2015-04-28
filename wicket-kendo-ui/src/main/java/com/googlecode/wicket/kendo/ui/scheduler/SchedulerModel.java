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

import java.util.Date;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Model of {@link SchedulerEvent}{@code s} for the {@link Scheduler}<br/>
 * The inheriting class should be able to {@link #load()} events depending on {@link #getStart()} and {@link #getEnd()} dates.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class SchedulerModel extends LoadableDetachableModel<List<SchedulerEvent>>
{
	private static final long serialVersionUID = 1L;

	private Date start;
	private Date end;

	/**
	 * Constructor
	 */
	public SchedulerModel()
	{
		this.start = null;
		this.end = null;
	}

	/**
	 * Gets the start date, used to {@link #load()} {@link SchedulerEvent}{@code s}
	 *
	 * @return the start date
	 */
	public Date getStart()
	{
		return this.start;
	}

	/**
	 * Sets the start date.
	 *
	 * @param date the start date
	 */
	public void setStart(Date date)
	{
		this.start = date;
	}

	/**
	 * Gets the end date, used to {@link #load()} {@link SchedulerEvent}{@code s}
	 *
	 * @return the start date
	 */
	public Date getEnd()
	{
		return this.end;
	}

	/**
	 * Gets the end date.
	 *
	 * @param date the start date
	 */
	public void setEnd(Date date)
	{
		this.end = date;
	}
}
