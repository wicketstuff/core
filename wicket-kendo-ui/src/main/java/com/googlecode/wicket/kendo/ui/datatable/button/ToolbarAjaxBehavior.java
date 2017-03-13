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
package com.googlecode.wicket.kendo.ui.datatable.button;

import java.util.List;

import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Generics;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;

/**
 * Provides the {@link JQueryAjaxBehavior} being called by the toolbar button(s).
 */
public class ToolbarAjaxBehavior extends JQueryAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final ToolbarButton button;

	/**
	 * Constructor
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 */
	public ToolbarAjaxBehavior(IJQueryAjaxAware source)
	{
		this(source, null);
	}

	/**
	 * Constructor
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ToolbarClickEvent}
	 */
	public ToolbarAjaxBehavior(IJQueryAjaxAware source, ToolbarButton button)
	{
		super(source);

		this.button = Args.notNull(button, "button");
	}

	/**
	 * Gets the {@link ToolbarButton} name
	 *
	 * @return the button name
	 */
	public String getButtonName()
	{
		return this.button.getName();
	}

	@Override
	protected CallbackParameter[] getCallbackParameters()
	{
		return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.resolved("values", "values") };
	}

	@Override
	public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
	{
		String property = this.button.getProperty();
		String statement = "var values = [];\n";

		if (property != null)
		{
			statement += "var $grid = jQuery(e.target).closest('.k-grid').data('kendoGrid');\n"; // lf
			statement += "jQuery.each($grid.select(), function(index, row) { values.push($grid.dataItem(row)." + property + "); }"; // lf
			statement += ");\n";
		}

		return statement + super.getCallbackFunctionBody(parameters);
	}

	// Factories //

	@Override
	protected JQueryEvent newEvent()
	{
		return new ToolbarClickEvent(this.button);
	}

	// Event objects //

	/**
	 * Provides a click event that will be transmitted to the {@link DataTable}
	 */
	public static class ToolbarClickEvent extends JQueryEvent
	{
		private final ToolbarButton button;
		private final List<String> values;

		public ToolbarClickEvent(ToolbarButton button)
		{
			super();

			this.button = button;
			this.values = Generics.newArrayList();

			String values = RequestCycleUtils.getQueryParameterValue("values").toString("");

			if (!Strings.isEmpty(values))
			{
				for (String value : values.split(","))
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
		public ToolbarButton getButton()
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
