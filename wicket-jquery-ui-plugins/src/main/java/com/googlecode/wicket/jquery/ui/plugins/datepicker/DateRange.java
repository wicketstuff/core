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
package com.googlecode.wicket.jquery.ui.plugins.datepicker;

import java.util.Date;

import org.apache.wicket.util.io.IClusterable;

/**
 * Provides the value type to be used as model object for {@link RangeDatePicker} and {@link RangeDatePickerTextField}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DateRange implements IClusterable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a default {@link DateRange} with start-date and end-date are set to today.
	 * @return the {@link DateRange}
	 */
	public static DateRange today()
	{
		return new DateRange(new Date(), new Date());
	}


	private Date start;
	private Date end;

	/**
	 * Constructor
	 * @param start the start date
	 * @param end the end date
	 */
	public DateRange(Date start, Date end)
	{
		this.start = start;
		this.end = end;
	}

	/**
	 * Gets the start date
	 * @return the start date
	 */
	public final Date getStart()
	{
		return this.start;
	}

	/**
	 * Sets the start date.<br/>
	 * @param date the start date
	 */
	public void setStart(Date date)
	{
		this.start = date;
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
	 * Sets the end date.<br/>
	 * @param date the end date
	 */
	public void setEnd(Date date)
	{
		this.end = date;
	}
}
