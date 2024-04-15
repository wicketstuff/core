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

/**
 * Provides a {@value #TYPE} series object
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class BulletSeries extends Series
{
	private static final long serialVersionUID = 1L;
	private static final String TYPE = "verticalBullet";

	private final String categoryField;
	private final String currentField;
	private final String targetField;

	/**
	 * Constructor that takes {@link BulletData} field names
	 */
	public BulletSeries()
	{
		this(BulletData.FIELD_CATEGORY, BulletData.FIELD_CURRENT, BulletData.FIELD_TARGET);
	}

	/**
	 * Constructor
	 * 
	 * @param categoryField the high field
	 * @param currentField the open field
	 * @param targetField the close field
	 */
	public BulletSeries(String categoryField, String currentField, String targetField)
	{
		super(null, TYPE);

		this.categoryField = categoryField;
		this.currentField = currentField;
		this.targetField = targetField;
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
	 * Gets the 'current' field
	 * 
	 * @return the 'current' field
	 */
	public String getCurrentField()
	{
		return this.currentField;
	}

	/**
	 * Gets the 'target' field
	 * 
	 * @return the 'target' field
	 */
	public String getTargetField()
	{
		return this.targetField;
	}

	// classes //

	/**
	 * Provides a data bean for {@link BulletSeries}
	 */
	public static class BulletData implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		public static final String FIELD_CURRENT = "current"; // currentField
		public static final String FIELD_TARGET = "target"; // targetField
		public static final String FIELD_CATEGORY = "category";

		private final Double current;
		private final Double target;
		private final String category;

		/**
		 * Constructor
		 * 
		 * @param category the category
		 * @param current the current value
		 * @param target the target value
		 */
		public BulletData(String category, Double current, Double target)
		{
			this.current = current;
			this.target = target;
			this.category = category;
		}

		/**
		 * Gets the x value
		 * 
		 * @return the x value
		 */
		public Double getCurrent()
		{
			return this.current;
		}

		/**
		 * Gets the target value
		 * 
		 * @return the target value
		 */
		public Double getTarget()
		{
			return this.target;
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
