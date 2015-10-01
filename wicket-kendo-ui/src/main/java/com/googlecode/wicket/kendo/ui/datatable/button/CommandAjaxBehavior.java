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

import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.datatable.DataTable;

/**
 * Provides the {@link JQueryAjaxBehavior} being called by the column button(s).
 */
public class CommandAjaxBehavior extends JQueryAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final CommandButton button;

	/**
	 * Constructor
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the {@link CommandButton} to attach to the {@link ClickEvent}
	 */
	public CommandAjaxBehavior(IJQueryAjaxAware source, CommandButton button)
	{
		super(source);

		this.button = button;
	}

	/**
	 * Gets the {@link CommandButton}
	 *
	 * @return the {@link CommandButton}
	 */
	public CommandButton getButton()
	{
		return this.button;
	}

	@Override
	protected CallbackParameter[] getCallbackParameters()
	{
		return new CallbackParameter[] { // lf
				CallbackParameter.context("e"), // lf
				CallbackParameter.resolved("value", String.format("this.dataItem(jQuery(e.target).closest('tr'))['%s']", this.button.getProperty())) // lf
		};
	}
	
	@Override
	public String getCallbackFunction()
	{
		if (this.button.isBuiltIn())
		{
			return null;
		}

		return super.getCallbackFunction();
	}

	@Override
	protected JQueryEvent newEvent()
	{
		return new ClickEvent(this.button);
	}

	// Event objects //

	/**
	 * Provides a click event that will be transmitted to the {@link DataTable}
	 */
	public static class ClickEvent extends JQueryEvent
	{
		private final CommandButton button;
		private final String value;

		public ClickEvent(CommandButton button)
		{
			super();

			this.button = button;
			this.value = RequestCycleUtils.getQueryParameterValue("value").toString();
		}

		/**
		 * Gets the button that has been attached to this event object
		 *
		 * @return the button
		 */
		public CommandButton getButton()
		{
			return this.button;
		}

		/**
		 * Gets the value from the row
		 *
		 * @return the value from the row
		 */
		public String getValue()
		{
			return this.value;
		}
	}
}
