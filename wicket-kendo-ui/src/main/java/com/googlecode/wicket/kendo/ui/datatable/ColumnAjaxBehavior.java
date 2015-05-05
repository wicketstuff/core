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

import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides the {@link JQueryAjaxBehavior} being called by the column button(s).
 */
public class ColumnAjaxBehavior extends JQueryAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final ColumnButton button;

	/**
	 * Constructor
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the {@link ColumnButton} to attach to the {@link ClickEvent}
	 */
	public ColumnAjaxBehavior(IJQueryAjaxAware source, ColumnButton button)
	{
		super(source);

		this.button = button;
	}

	@Override
	protected CallbackParameter[] getCallbackParameters()
	{
		return new CallbackParameter[] { // lf
				CallbackParameter.context("e"), // lf
				CallbackParameter.resolved("value", String.format("this.dataItem(jQuery(e.target).closest('tr'))['%s']", this.button.getProperty())) // lf
		};
	}

	/**
	 * Gets the {@link ColumnButton}
	 *
	 * @return the {@link ColumnButton}
	 */
	public ColumnButton getButton()
	{
		return this.button;
	}

	@Override
	protected JQueryEvent newEvent()
	{
		return new ClickEvent(this.button);
	}

	// Event class //

	/**
	 * Provides a click event that will be transmitted to the {@link DataTable}
	 */
	protected static class ClickEvent extends JQueryEvent
	{
		private final ColumnButton button;
		private final String value;

		public ClickEvent(ColumnButton button)
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
		public ColumnButton getButton()
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
