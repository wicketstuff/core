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
package com.googlecode.wicket.jquery.ui.form.slider;

import org.apache.wicket.util.io.IClusterable;

/**
 * Provides the value type to be used as model object for {@link RangeSlider}
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class RangeValue implements IClusterable /*, Comparable<RangeValue> */
{
	private static final long serialVersionUID = 1L;

	private Integer lower;
	private Integer upper;

	/**
	 * Constructor
	 * Initialize the lower and the upper value to 0
	 */
	public RangeValue()
	{
		this(0, 0);
	}
	
	/**
	 * Constructor
	 * @param lower the lower value
	 * @param upper the upper value
	 */
	public RangeValue(Integer lower, Integer upper)
	{
		this.lower = lower;
		this.upper = upper;
	}

	/**
	 * Gets the lower value
	 * @return an {@link Integer}
	 */
	public Integer getLower()
	{
		return this.lower;
	}

	/**
	 * Sets the lower value
	 * @param lower the lower value
	 */
	public void setLower(Integer lower)
	{
		this.lower = lower;
	}

	/**
	 * Gets the upper value
	 * @return an {@link Integer}
	 */
	public Integer getUpper()
	{
		return this.upper;
	}

	/**
	 * Sets the upper value
	 * @param upper the upper value
	 */
	public void setUpper(Integer upper)
	{
		this.upper = upper;
	}
	
	@Override
	public String toString()
	{
		return String.format("[%d, %d]", this.getLower(), this.getUpper());
	}

	// Comparable<RangeValue> //
//	@Override
//	public int compareTo(RangeValue value)
//	{
//		if (this.lower == value.lower && this.upper == value.upper)
//		{
//			return 0;
//		}
//		
//		if (this.lower < value.lower)
//		{
//			return -1;
//		}
//		
//		if (this.upper > value.upper)
//		{
//			return 1;
//		}
//	}
}
