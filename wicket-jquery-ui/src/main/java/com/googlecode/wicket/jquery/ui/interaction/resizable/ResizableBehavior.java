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
package com.googlecode.wicket.jquery.ui.interaction.resizable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides a jQuery resizable behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class ResizableBehavior extends JQueryBehavior implements IJQueryAjaxAware, IResizableListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "resizable";

	private JQueryAjaxBehavior onResizeStartBehavior = null;
	private JQueryAjaxBehavior onResizeStopBehavior = null;

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public ResizableBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public ResizableBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Methods //
	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// these events are not enabled by default to prevent unnecessary server round-trips.
		if (this.isResizeStartEventEnabled())
		{
			component.add(this.onResizeStartBehavior = this.newOnResizeStartBehavior());
		}

		if (this.isResizeStopEventEnabled())
		{
			component.add(this.onResizeStopBehavior = this.newOnResizeStopBehavior());
		}
	}

	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (this.onResizeStartBehavior != null)
		{
			this.setOption("start", this.onResizeStartBehavior.getCallbackFunction());
		}

		if (this.onResizeStopBehavior != null)
		{
			this.setOption("stop", this.onResizeStopBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ResizeEvent)
		{
			ResizeEvent ev = (ResizeEvent) event;

			if (ev instanceof ResizeStartEvent)
			{
				this.onResizeStart(target, ev.getTop(), ev.getLeft(), ev.getWidth(), ev.getHeight());
			}

			if (ev instanceof ResizeStopEvent)
			{
				this.onResizeStop(target, ev.getTop(), ev.getLeft(), ev.getWidth(), ev.getHeight());
			}
		}
	}

	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'start' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnResizeStartBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] {
						CallbackParameter.context("event"),
						CallbackParameter.context("ui"),
						CallbackParameter.resolved("top", "ui.position.top"),
						CallbackParameter.resolved("left", "ui.position.left"),
						CallbackParameter.resolved("width", "ui.size.width"),
						CallbackParameter.resolved("height", "ui.size.height"),
				};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ResizeStartEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'stop' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnResizeStopBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] {
						CallbackParameter.context("event"),
						CallbackParameter.context("ui"),
						CallbackParameter.resolved("top", "ui.position.top"),
						CallbackParameter.resolved("left", "ui.position.left"),
						CallbackParameter.resolved("width", "ui.size.width"),
						CallbackParameter.resolved("height", "ui.size.height"),
				};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ResizeStopEvent();
			}
		};
	}


	// Event Objects //
	/**
	 * Provides a base class for resize event object
	 */
	protected static class ResizeEvent extends JQueryEvent
	{
		private final int top;
		private final int left;
		private final int width;
		private final int height;

		/**
		 * Constructor.
		 */
		public ResizeEvent()
		{
			this.top = RequestCycleUtils.getQueryParameterValue("top").toInt(-1);
			this.left = RequestCycleUtils.getQueryParameterValue("left").toInt(-1);
			this.width = RequestCycleUtils.getQueryParameterValue("width").toInt(-1);
			this.height = RequestCycleUtils.getQueryParameterValue("height").toInt(-1);
		}

		/**
		 * Gets the position's top value
		 * @return the position's top value
		 */
		public int getTop()
		{
			return this.top;
		}

		/**
		 * Gets the position's left value
		 * @return the position's left value
		 */
		public int getLeft()
		{
			return this.left;
		}

		/**
		 * Gets the size's width value
		 * @return the size's width value
		 */
		public int getWidth()
		{
			return this.width;
		}

		/**
		 * Gets the size's height value
		 * @return the size's height value
		 */
		public int getHeight()
		{
			return this.height;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'start' callback
	 */
	protected static class ResizeStartEvent extends ResizeEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'stop' callback
	 */
	protected static class ResizeStopEvent extends ResizeEvent
	{
	}
}