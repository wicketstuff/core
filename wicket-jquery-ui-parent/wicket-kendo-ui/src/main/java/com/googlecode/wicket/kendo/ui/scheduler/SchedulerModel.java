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

import java.time.ZonedDateTime;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Model of {@link SchedulerEvent}{@code s} for the {@link Scheduler}<br>
 * The inheriting class should be able to {@link #load()} events depending on {@link #getStart()} and {@link #getUntil()} dates.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class SchedulerModel extends LoadableDetachableModel<List<SchedulerEvent>>
{
	private static final long serialVersionUID = 1L;

	private ZonedDateTime start;
	private ZonedDateTime until;

	/**
	 * Constructor
	 */
	public SchedulerModel()
	{
	}
	
	// Methods //

	@Override
	protected final List<SchedulerEvent> load()
	{
		return this.load(this.getStart(), this.getUntil());
	}
	
	/**
	 * Loads the {@link SchedulerEvent}{@code s}
	 * @param start the start date
	 * @param until the until/end date
	 * @return the {@link List} of {@link SchedulerEvent}{@code s}
	 */
	public abstract List<SchedulerEvent> load(ZonedDateTime start, ZonedDateTime until);

	// Properties //


	/**
	 * Gets the start date, used to load {@link SchedulerEvent}{@code s}
	 *
	 * @return the start date
	 */
	public ZonedDateTime getStart()
	{
		return this.start;
	}

	/**
	 * Sets the start date.
	 *
	 * @param date the start date
	 */
	public void setStart(ZonedDateTime date)
	{
		this.start = date;
	}

	/**
	 * Gets the end date, used to load {@link SchedulerEvent}{@code s}
	 *
	 * @return the start date
	 */
	public ZonedDateTime getUntil()
	{
		return this.until;
	}

	/**
	 * Gets the until/end date.
	 *
	 * @param date the start date
	 */
	public void setUntil(ZonedDateTime date)
	{
		this.until = date;
	}
}
