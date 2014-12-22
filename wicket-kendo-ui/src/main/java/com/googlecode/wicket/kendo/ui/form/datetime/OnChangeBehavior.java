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
package com.googlecode.wicket.kendo.ui.form.datetime;

import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.html.form.FormComponent;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxPostBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides a {@link JQueryAjaxPostBehavior} that can be returned by {@link DatePickerBehavior#newOnChangeBehavior()}
 */
public class OnChangeBehavior extends JQueryAjaxPostBehavior
{
	private static final long serialVersionUID = 1L;

	public OnChangeBehavior(final IJQueryAjaxAware source, final FormComponent<?>... components)
	{
		super(source, components);
	}

	@Override
	protected CallbackParameter[] getCallbackParameters()
	{
		// function() { ... }
		return new CallbackParameter[] { CallbackParameter.resolved("value", "this.value()") };
	}

	@Override
	protected JQueryEvent newEvent()
	{
		return new ChangeEvent();
	}

	// Event classes //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxPostBehavior} select callback
	 */
	protected static class ChangeEvent extends JQueryEvent
	{
		private final String date;

		public ChangeEvent()
		{
			this.date = RequestCycleUtils.getPostParameterValue("value").toString();
		}

		public String getDateText()
		{
			return this.date;
		}
	}
}
