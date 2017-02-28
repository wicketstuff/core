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
public class DonutSeries extends Series
{
	private static final long serialVersionUID = 1L;
	private static final String TYPE = "donut";

	private final String field;
	private final String categoryField;
	private final String explodeField;

	/**
	 * Constructor that takes {@link DonutData} field names
	 */
	public DonutSeries()
	{
		this(DonutData.FIELD, DonutData.FIELD_CATEGORY, DonutData.FIELD_EXPLODE);
	}

	/**
	 * Constructor
	 * 
	 * @param field the open field
	 * @param categoryField the high field
	 * @param explodeField the close field
	 */
	public DonutSeries(String field, String categoryField, String explodeField)
	{
		super(null, TYPE);

		this.field = field;
		this.categoryField = categoryField;
		this.explodeField = explodeField;
	}


	/**
	 * Gets the field name
	 * 
	 * @return the field name
	 */
	public String getField()
	{
		return this.field;
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
	 * Gets the 'explode' field
	 * 
	 * @return the 'explode' field
	 */
	public String getExplodeField()
	{
		return this.explodeField;
	}

	// classes //

	/**
	 * Provides a data bean for {@link DonutSeries}
	 */
	public static class DonutData implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		public static final String FIELD = "value";
		public static final String FIELD_CATEGORY = "category"; // categoryField
		public static final String FIELD_EXPLODE = "explode";  // explodeField

		private final Double value;
		private final boolean explode;
		private final String category;

		/**
		 * Constructor
		 * 
		 * @param category the category
		 * @param value the value
		 * @param explode the explode flag
		 */
		public DonutData(String category, Double value, boolean explode)
		{
			this.value = value;
			this.explode = explode;
			this.category = category;
		}

		/**
		 * Gets the value
		 * 
		 * @return the value
		 */
		public Double getValue()
		{
			return this.value;
		}

		/**
		 * Gets the explode flag
		 * 
		 * @return the explode flag
		 */
		public boolean isExplode()
		{
			return this.explode;
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
