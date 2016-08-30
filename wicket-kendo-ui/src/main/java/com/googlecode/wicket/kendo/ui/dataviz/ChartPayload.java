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
package com.googlecode.wicket.kendo.ui.dataviz;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.ajax.AjaxPayload;

/**
 * Provides a convenient {@link AjaxPayload} that can be used to broadcast a point information
 * 
 * @see Chart#onSeriesClick(AjaxRequestTarget, String, String, String, long)
 * @author Sebastien Briquet - sebfz1
 *
 */
public class ChartPayload extends AjaxPayload implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private final long value;
	private final String category;
	private final String seriesName;
	private final String seriesField;

	/**
	 * Constructor
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param seriesName the series name
	 * @param seriesField the series field
	 * @param category the category
	 * @param value the value of the point
	 */
	public ChartPayload(AjaxRequestTarget target, String seriesName, String seriesField, String category, long value)
	{
		super(target);

		this.value = value;
		this.category = category;
		this.seriesName = seriesName;
		this.seriesField = seriesField;
	}

	/**
	 * Gets the value
	 * 
	 * @return the value
	 */
	public long getValue()
	{
		return this.value;
	}

	/**
	 * Get the category
	 * 
	 * @return the category
	 */
	public String getCategory()
	{
		return this.category;
	}

	/**
	 * Gets the series name
	 * 
	 * @return the series name
	 */
	public String getSeriesName()
	{
		return this.seriesName;
	}

	/**
	 * Gets the series field
	 * 
	 * @return the series field
	 */
	public String getSeriesField()
	{
		return this.seriesField;
	}
}
