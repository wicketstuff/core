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
package com.googlecode.wicket.jquery.ui.plugins.datepicker;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides the jQuery fullCalendar behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class RangeDatePickerBehavior extends JQueryBehavior implements IJQueryAjaxAware, IRangeDatePickerListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "DatePicker";

	private JQueryAjaxBehavior onChangeBehavior;

	public RangeDatePickerBehavior(final String selector)
	{
		this(selector, new Options());
	}

	public RangeDatePickerBehavior(final String selector, final Options options)
	{
		super(selector, METHOD, options);

		this.add(new CssResourceReference(RangeDatePickerBehavior.class, "css/base.css"));
		this.add(new CssResourceReference(RangeDatePickerBehavior.class, "css/clean.css"));
		this.add(new JavaScriptResourceReference(RangeDatePickerBehavior.class, "js/datepicker.js"));
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.add(this.onChangeBehavior = this.newOnChangeBehavior());
	}


	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("onChange", this.onChangeBehavior.getCallbackFunction());
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DateChangeEvent)
		{
			DateChangeEvent ev = (DateChangeEvent)event;
			this.onValueChanged(target, new DateRange(ev.getStart(), ev.getEnd()));
		}
	}


	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'change' javascript callback
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnChangeBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				//function(dates, el) { ... }
				return new CallbackParameter[] {
						CallbackParameter.context("dates"),
						CallbackParameter.context("el"),
						CallbackParameter.resolved("start", "dates[0].getTime()"),
						CallbackParameter.resolved("end", "dates[1].getTime()")
				};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new DateChangeEvent();
			}
		};
	}

	// Event class //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'change' callback
	 */
	protected static class DateChangeEvent extends JQueryEvent
	{
		private final Date start;
		private final Date end;

		public DateChangeEvent()
		{
			long start = RequestCycleUtils.getQueryParameterValue("start").toLong();
			this.start = new Date(start);

			long end = RequestCycleUtils.getQueryParameterValue("end").toLong();
			this.end = new Date(end);
		}

		/**
		 * Gets the event's start date
		 * @return the start date
		 */
		public Date getStart()
		{
			return this.start;
		}

		/**
		 * Gets the event's end date
		 * @return the end date
		 */
		public Date getEnd()
		{
			return this.end;
		}
	}
}
