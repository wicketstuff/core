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
package com.googlecode.wicket.jquery.ui.form.spinner;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides a jQuery spinner {@link JQueryBehavior}
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class SpinnerBehavior extends JQueryUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "spinner";

	private final ISpinnerListener listener;
	private JQueryAjaxBehavior onSpinAjaxBehavior = null;
	private JQueryAjaxBehavior onStopAjaxBehavior = null;

	public SpinnerBehavior(String selector, ISpinnerListener listener)
	{
		this(selector, listener, new Options());
	}

	public SpinnerBehavior(String selector, ISpinnerListener listener, Options options)
	{
		this(selector, METHOD, listener, options);
	}

	SpinnerBehavior(String selector, String method, ISpinnerListener listener, Options options)
	{
		super(selector, method, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.listener.isOnSpinEventEnabled())
		{
			this.onSpinAjaxBehavior = this.newOnSpinAjaxBehavior(this);
			component.add(this.onSpinAjaxBehavior);
		}

		if (this.listener.isOnStopEventEnabled())
		{
			this.onStopAjaxBehavior = this.newOnStopAjaxBehavior(this);
			component.add(this.onStopAjaxBehavior);
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'stop' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnStopAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnSpinAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnSpinAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'stop' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnStopAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnStopAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnStopAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'spin' event
	 */
	protected static class OnSpinAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnSpinAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.context("ui"), CallbackParameter.resolved("value", "ui.value") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new SpinEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'stop' event
	 */
	protected static class OnStopAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnStopAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.context("ui") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new StopEvent();
		}
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (this.onSpinAjaxBehavior != null)
		{
			this.setOption("spin", this.onSpinAjaxBehavior.getCallbackFunction());
		}

		if (this.onStopAjaxBehavior != null)
		{
			this.setOption("stop", this.onStopAjaxBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof SpinEvent)
		{
			this.listener.onSpin(target, ((SpinEvent) event).getValue());
		}

		if (event instanceof StopEvent)
		{
			this.listener.onStop(target);
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnSpinAjaxBehavior} callback
	 */
	protected static class SpinEvent extends JQueryEvent
	{
		private final String value;

		/**
		 * Constructor
		 */
		public SpinEvent()
		{
			super();

			this.value = RequestCycleUtils.getQueryParameterValue("value").toOptionalString();
		}

		/**
		 * Gets the spin value
		 *
		 * @return the index
		 */
		public String getValue()
		{
			return this.value;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnStopAjaxBehavior} callback
	 */
	protected static class StopEvent extends JQueryEvent
	{
	}

}
