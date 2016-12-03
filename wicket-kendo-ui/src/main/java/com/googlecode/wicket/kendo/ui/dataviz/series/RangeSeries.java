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
package com.googlecode.wicket.kendo.ui.dataviz.series;

/**
 * Provides a <i>rangeColumn</i> series object.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class RangeSeries extends Series
{
	private static final long serialVersionUID = 1L;
	private static final String TYPE = "rangeColumn";

	private final String toField;
	private final String fromField;

	/**
	 * Constructor
	 * 
	 * @param name the series name
	 * @param fromField the series from-field
	 * @param toField the series to-field
	 */
	public RangeSeries(String name, String fromField, String toField)
	{
		super(name, TYPE);

		this.fromField = fromField;
		this.toField = toField;
	}

	/**
	 * Constructor
	 * 
	 * @param name the series name
	 * @param fromField the series from-field
	 * @param toField the series to-field
	 * @param axis the axis on which the series should be placed
	 */
	public RangeSeries(String name, String fromField, String toField, String axis)
	{
		super(name, TYPE, axis);

		this.fromField = fromField;
		this.toField = toField;
	}

	/**
	 * Gets the series from-field
	 * 
	 * @return the series from-field
	 */
	public String getFromField()
	{
		return this.fromField;
	}

	/**
	 * Gets the series to-field
	 * 
	 * @return the series to-field
	 */
	public String getToField()
	{
		return this.toField;
	}
}
