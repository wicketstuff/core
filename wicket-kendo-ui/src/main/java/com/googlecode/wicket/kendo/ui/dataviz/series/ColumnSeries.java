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
 * Provides a <i>column</i> series object.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class ColumnSeries extends Series
{
	private static final long serialVersionUID = 1L;
	private static final String TYPE = "column";

	private final String field;
	private String stack = null;

	/**
	 * Constructor
	 * 
	 * @param name the series name
	 * @param field the series field, it should correspond to a model object's property
	 */
	public ColumnSeries(String name, String field)
	{
		super(name, TYPE);

		this.field = field;
	}

	/**
	 * Constructor
	 * 
	 * @param name the series name
	 * @param field the series field, it should correspond to a model object's property
	 * @param axis the axis on which the series should be placed
	 */
	public ColumnSeries(String name, String field, String axis)
	{
		super(name, TYPE, axis);

		this.field = field;
	}

	/**
	 * Gets the series field
	 * 
	 * @return the series field
	 */
	public String getField()
	{
		return this.field;
	}

	/**
	 * Gets the stack the series belongs to
	 * 
	 * @return the stack
	 */
	public String getStack()
	{
		return this.stack;
	}

	/**
	 * Sets the stack the series belongs to
	 * 
	 * @param stack the stack
	 * @return this, for chaining
	 */
	public ColumnSeries setStack(String stack)
	{
		this.stack = stack;

		return this;
	}
}
