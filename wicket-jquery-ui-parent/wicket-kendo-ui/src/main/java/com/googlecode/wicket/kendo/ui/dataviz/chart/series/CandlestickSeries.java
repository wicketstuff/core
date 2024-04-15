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
package com.googlecode.wicket.kendo.ui.dataviz.chart.series;

/**
 * Provides a {@value #TYPE} series object
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
// TODO CandlestickData
public class CandlestickSeries extends Series
{
	private static final long serialVersionUID = 1L;
	private static final String TYPE = "candlestick";

	private final String closeField;
	private final String openField;
	private final String lowField;
	private final String highField;

	/**
	 * Constructor
	 * 
	 * @param name the series name
	 * @param openField the open field
	 * @param closeField the close field
	 * @param lowField the low field
	 * @param highField the high field
	 */
	public CandlestickSeries(String name, String openField, String closeField, String lowField, String highField)
	{
		super(name, TYPE);

		this.openField = openField;
		this.closeField = closeField;
		this.lowField = lowField;
		this.highField = highField;
	}

	/**
	 * Constructor
	 * 
	 * @param name the series name
	 * @param openField the open field
	 * @param closeField the close field
	 * @param lowField the low field
	 * @param highField the high field
	 * @param axis the axis on which the series should be placed
	 */
	public CandlestickSeries(String name, String openField, String closeField, String lowField, String highField, String axis)
	{
		super(name, TYPE, axis);

		this.openField = openField;
		this.closeField = closeField;
		this.lowField = lowField;
		this.highField = highField;
	}

	/**
	 * Gets the 'open' field
	 * 
	 * @return the 'open' field
	 */
	public String getOpenField()
	{
		return this.openField;
	}

	/**
	 * Gets the 'close' field
	 * 
	 * @return the 'close' field
	 */
	public String getCloseField()
	{
		return this.closeField;
	}

	/**
	 * Gets the 'low' field
	 * 
	 * @return the 'low' field
	 */
	public String getLowField()
	{
		return this.lowField;
	}

	/**
	 * Gets the 'high' field
	 * 
	 * @return the 'high' field
	 */
	public String getHighField()
	{
		return this.highField;
	}
}
