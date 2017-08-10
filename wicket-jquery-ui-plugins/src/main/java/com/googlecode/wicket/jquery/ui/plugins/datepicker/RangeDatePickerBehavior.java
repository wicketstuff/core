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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides the jQuery DatePicker behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class RangeDatePickerBehavior extends JQueryBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "DatePicker";

	private final IRangeDatePickerListener listener;
	private JQueryAjaxBehavior onRangeChangeAjaxBehavior;

	public RangeDatePickerBehavior(final String selector, IRangeDatePickerListener listener)
	{
		this(selector, new Options(), listener);
	}

	public RangeDatePickerBehavior(final String selector, final Options options, IRangeDatePickerListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");

		this.add(new CssResourceReference(RangeDatePickerBehavior.class, "css/base.css"));
		this.add(new CssResourceReference(RangeDatePickerBehavior.class, "css/clean.css"));
		this.add(new JQueryPluginResourceReference(RangeDatePickerBehavior.class, "js/datepicker.js"));
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.onRangeChangeAjaxBehavior = this.newOnRangeChangeAjaxBehavior(this);
		component.add(this.onRangeChangeAjaxBehavior);
	}

	// Events //
	@Override
	public void onConfigure(Component component)
	{
		this.setOption("onRangeChange", this.onRangeChangeAjaxBehavior.getCallbackFunction());

		super.onConfigure(component);
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DateChangeEvent)
		{
			DateChangeEvent ev = (DateChangeEvent) event;

			this.listener.onValueChanged(target, new DateRange(ev.getStart(), ev.getEnd()));
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'onRangeChange' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnRangeChangeAjaxBehavior} by default
	 */
	private JQueryAjaxBehavior newOnRangeChangeAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnRangeChangeAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'onRangeChange' event
	 */
	protected static class OnRangeChangeAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnRangeChangeAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			// function(dates, el) { ... }
			return new CallbackParameter[] { // lf
			CallbackParameter.context("dates"), // lf
					CallbackParameter.context("el"), // lf
					CallbackParameter.resolved("startTime", "dates[0].getTime()"), // lf
					CallbackParameter.resolved("startOffset", "dates[0].getTimezoneOffset()"), // offset from UTC in minutes
					CallbackParameter.resolved("endTime", "dates[1].getTime()"), // lf
					CallbackParameter.resolved("endOffset", "dates[1].getTimezoneOffset()") // offset from UTC in minutes
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DateChangeEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnRangeChangeAjaxBehavior} callback<br>
	 * <br>
	 * <b>Note</b>: The {@code start} and {@code end} dates will be translated to UTC.<br>
	 * ie: if the behavior receives 10/10/2010 0:00:00 CET, it will be translated to 10/10/2010 0:00:00 UTC
	 */
	protected static class DateChangeEvent extends JQueryEvent
	{
		private final long start;
		private final long end;

		public DateChangeEvent()
		{
			long startTime = RequestCycleUtils.getQueryParameterValue("startTime").toLong();
			int startOffset = RequestCycleUtils.getQueryParameterValue("startOffset").toInt(0) * 60 * 1000; // minutes to milliseconds
			this.start = startTime - startOffset;

			long endTime = RequestCycleUtils.getQueryParameterValue("endTime").toLong();
			int endOffset = RequestCycleUtils.getQueryParameterValue("endOffset").toInt(0) * 60 * 1000; // minutes to milliseconds
			this.end = endTime - endOffset;
		}

		/**
		 * Gets the event's UTC start date
		 * 
		 * @return the start date
		 */
		public long getStart()
		{
			return this.start;
		}

		/**
		 * Gets the event's UTC end date
		 * 
		 * @return the end date
		 */
		public long getEnd()
		{
			return this.end;
		}
	}
}
