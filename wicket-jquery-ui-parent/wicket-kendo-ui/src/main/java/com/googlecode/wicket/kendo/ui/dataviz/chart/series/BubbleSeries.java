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

import org.apache.wicket.util.io.IClusterable;
import com.github.openjson.JSONObject;
import com.github.openjson.JSONString;

/**
 * Provides a {@value #TYPE} series object
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class BubbleSeries extends Series implements JSONString
{
	private static final long serialVersionUID = 1L;
	private static final String TYPE = "bubble";

	private final String xField;
	private final String yField;
	private final String sizeField;
	private final String categoryField;

	/**
	 * Constructor that takes {@link BubbleData} field names
	 */
	public BubbleSeries()
	{
		this(BubbleData.FIELD_CATEGORY, BubbleData.FIELD_X, BubbleData.FIELD_Y, BubbleData.FIELD_SIZE);
	}

	/**
	 * Constructor
	 * 
	 * @param categoryField the high field
	 * @param xField the open field
	 * @param yField the close field
	 * @param sizeField the low field
	 */
	public BubbleSeries(String categoryField, String xField, String yField, String sizeField)
	{
		super(null, TYPE);

		this.xField = xField;
		this.yField = yField;
		this.sizeField = sizeField;
		this.categoryField = categoryField;
	}

	/**
	 * Gets the 'category' field
	 * 
	 * @return the 'category' field
	 */
	public String getCategoryField()
	{
		return this.categoryField;
	}

	/**
	 * Gets the 'size' field
	 * 
	 * @return the 'size' field
	 */
	public String getSizeField()
	{
		return this.sizeField;
	}

	/**
	 * Return the JSON representation of this object<br>
	 * This needs to be done manually because of the xField and yField that are not properly converted (camel case issue)
	 * 
	 * @return the JSON string
	 */
	@Override
	public String toJSONString()
	{
		JSONObject object = new JSONObject(this);
		object.put("xField", this.xField);
		object.put("yField", this.yField);

		return object.toString();
	}

	// classes //

	/**
	 * Provides a data bean for {@link BubbleSeries}
	 */
	public static class BubbleData implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		public static final String FIELD_X = "x"; // xField
		public static final String FIELD_Y = "y"; // yField
		public static final String FIELD_SIZE = "size"; // sizeField
		public static final String FIELD_CATEGORY = "category"; // category

		private final Double x;
		private final Double y;
		private final Double size;
		private final String category;

		/**
		 * Constructor
		 * 
		 * @param category the category
		 * @param x the x value
		 * @param y the y value
		 * @param size the size value
		 */
		public BubbleData(String category, Double x, Double y, Double size)
		{
			this.x = x;
			this.y = y;
			this.size = size;
			this.category = category;
		}

		/**
		 * Gets the x value
		 * 
		 * @return the x value
		 */
		public Double getX()
		{
			return this.x;
		}

		/**
		 * Gets the y value
		 * 
		 * @return the y value
		 */
		public Double getY()
		{
			return this.y;
		}

		/**
		 * Gets the size value
		 * 
		 * @return the size value
		 */
		public Double getSize()
		{
			return this.size;
		}

		/**
		 * Gets the category
		 * 
		 * @return the category
		 */
		public String getCategory()
		{
			return this.category;
		}
	}
}
