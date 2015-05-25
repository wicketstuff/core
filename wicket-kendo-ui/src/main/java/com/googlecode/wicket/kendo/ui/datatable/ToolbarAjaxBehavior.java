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
package com.googlecode.wicket.kendo.ui.datatable;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.string.StringValue;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides the {@link JQueryAjaxBehavior} being called by the toolbar button(s).
 */
public class ToolbarAjaxBehavior extends JQueryAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the self executing function statement to retrieve selected values
	 *
	 * @param grid the kendoGrid object
	 * @param property the property used to retrieve the row's object value
	 *
	 * @return the function statement
	 */
	public static String getValuesFunction(String grid, String property)
	{
		return String.format("function() { var values = []; var grid = %s; grid.select().each(function(index, row) { values.push(grid.dataItem(row)['%s']); }); return values; }()", grid, property);
	}

	private String property;

	/**
	 * Constructor
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param property the property used to retrieve the row's object value
	 */
	public ToolbarAjaxBehavior(IJQueryAjaxAware source, String property)
	{
		super(source);

		this.property = property;
	}

	@Override
	protected CallbackParameter[] getCallbackParameters()
	{
		return new CallbackParameter[] { // lf
				CallbackParameter.context("e"), // lf
				CallbackParameter.resolved("button", "jQuery(e.target).attr('class').match(/k-grid-(\\w+)/)[1]"), // lf
				CallbackParameter.resolved("values", getValuesFunction("jQuery(e.target).closest('.k-grid').data('kendoGrid')", this.property)) // lf
		};
	}

	// Factories //

	@Override
	protected JQueryEvent newEvent()
	{
		return new ToolbarClickEvent();
	}

	// Event objects //

	/**
	 * Provides a click event that will be transmitted to the {@link DataTable}
	 */
	protected static class ToolbarClickEvent extends JQueryEvent
	{
		private final String button;
		private final List<String> values;

		public ToolbarClickEvent()
		{
			super();

			this.button = RequestCycleUtils.getQueryParameterValue("button").toString();
			this.values = new ArrayList<String>();

			StringValue values = RequestCycleUtils.getQueryParameterValue("values");

			if (values != null)
			{
				for (String value : values.toString().split(","))
				{
					this.values.add(value);
				}
			}
		}

		/**
		 * Gets the button that has been attached to this event object
		 *
		 * @return the button
		 */
		public String getButton()
		{
			return this.button;
		}

		/**
		 * Gets values from selected rows, as strings
		 *
		 * @return values from the table
		 */
		public List<String> getValues()
		{
			return this.values;
		}
	}
}
