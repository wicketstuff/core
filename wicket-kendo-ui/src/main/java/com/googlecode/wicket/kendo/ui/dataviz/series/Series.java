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

import java.io.Serializable;

import com.googlecode.wicket.kendo.ui.dataviz.Chart;

/**
 * Provides the base chart series object.<br/>
 * <b>Note:</b> The field should correspond to a property of the {@link Chart}'s model object
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class Series implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final String name;
	private final String type;
	private final String axis;

	private String color = null;
	private String tooltip = null;

	/**
	 * Constructor
	 * 
	 * @param name the series name
	 */
	public Series(String name, String type)
	{
		this(name, type, null);
	}

	/**
	 * Constructor
	 * 
	 * @param name the series name
	 * @param type the series type (line, column, etc)
	 * @param axis the axis on which the series should be placed
	 */
	public Series(String name, String type, String axis)
	{
		this.name = name;
		this.type = type;
		this.axis = axis;
	}

	/**
	 * Gets the series name
	 * 
	 * @return the series name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Gets the series type (line, column, etc)
	 * 
	 * @return the series type
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Gets the axis on which the series should be placed
	 * 
	 * @return the axis
	 */
	public String getAxis()
	{
		return this.axis;
	}

	/**
	 * Gets the series color
	 * 
	 * @return the series color
	 */
	public String getColor()
	{
		return this.color;
	}

	/**
	 * Sets the series color
	 * 
	 * @param color the series color
	 * @return this, for chaining
	 */
	public Series setColor(String color)
	{
		this.color = color;

		return this;
	}

	/**
	 * Sets the series tooltip (as JSON string)
	 * 
	 * @return the series tooltip
	 * @see http://docs.telerik.com/kendo-ui/api/javascript/dataviz/ui/chart#configuration-series.tooltip.format
	 */
	public String getTooltip()
	{
		return this.tooltip;
	}

	/**
	 * Sets the series tooltip (as JSON string)<br/>
	 * <b>caution: </b> FIXME does not seems to work
	 * 
	 * @param tooltip the series tooltip JSON object
	 * @return this, for chaining
	 */
	public Series setTooltip(String tooltip)
	{
		this.tooltip = tooltip;

		return this;
	}
}
