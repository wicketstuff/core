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
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides a jQuery resizable behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public class ResizableBehavior extends JQueryUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "resizable";

	/** event listener */
	private final IResizableListener listener;

	private JQueryAjaxBehavior onResizeStartAjaxBehavior = null;
	private JQueryAjaxBehavior onResizeStopAjaxBehavior = null;

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IResizableListener}
	 */
	public ResizableBehavior(String selector, IResizableListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IResizableListener}
	 */
	public ResizableBehavior(String selector, Options options, IResizableListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// these events are not enabled by default to prevent unnecessary server round-trips.
		if (this.listener.isResizeStartEventEnabled())
		{
			this.onResizeStartAjaxBehavior = this.newOnResizeStartAjaxBehavior(this);
			component.add(this.onResizeStartAjaxBehavior);
		}

		if (this.listener.isResizeStopEventEnabled())
		{
			this.onResizeStopAjaxBehavior = this.newOnResizeStopAjaxBehavior(this);
			component.add(this.onResizeStopAjaxBehavior);
		}
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		if (this.onResizeStartAjaxBehavior != null)
		{
			this.setOption("start", this.onResizeStartAjaxBehavior.getCallbackFunction());
		}

		if (this.onResizeStopAjaxBehavior != null)
		{
			this.setOption("stop", this.onResizeStopAjaxBehavior.getCallbackFunction());
		}

		super.onConfigure(component);
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ResizeEvent)
		{
			ResizeEvent ev = (ResizeEvent) event;

			if (ev instanceof ResizeStartEvent)
			{
				this.listener.onResizeStart(target, ev.getTop(), ev.getLeft(), ev.getWidth(), ev.getHeight());
			}

			if (ev instanceof ResizeStopEvent)
			{
				this.listener.onResizeStop(target, ev.getTop(), ev.getLeft(), ev.getWidth(), ev.getHeight());
			}
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'start' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnResizeStartAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnResizeStartAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnResizeStartAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'stop' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnResizeStopAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnResizeStopAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnResizeStopAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'start' event
	 */
	protected static class OnResizeStartAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnResizeStartAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("top", "ui.position.top"), // lf
					CallbackParameter.resolved("left", "ui.position.left"), // lf
					CallbackParameter.resolved("width", "ui.size.width"), // lf
					CallbackParameter.resolved("height", "ui.size.height") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ResizeStartEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'stop' event
	 */
	protected static class OnResizeStopAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnResizeStopAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("top", "ui.position.top"), // lf
					CallbackParameter.resolved("left", "ui.position.left"), // lf
					CallbackParameter.resolved("width", "ui.size.width"), // lf
					CallbackParameter.resolved("height", "ui.size.height") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ResizeStopEvent();
		}
	}

	// Event objects //

	/**
	 * Provides a base class for {@link ResizableBehavior} event objects
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
		 * 
		 * @return the position's top value
		 */
		public int getTop()
		{
			return this.top;
		}

		/**
		 * Gets the position's left value
		 * 
		 * @return the position's left value
		 */
		public int getLeft()
		{
			return this.left;
		}

		/**
		 * Gets the size's width value
		 * 
		 * @return the size's width value
		 */
		public int getWidth()
		{
			return this.width;
		}

		/**
		 * Gets the size's height value
		 * 
		 * @return the size's height value
		 */
		public int getHeight()
		{
			return this.height;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnResizeStartAjaxBehavior} callback
	 */
	protected static class ResizeStartEvent extends ResizeEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnResizeStopAjaxBehavior} callback
	 */
	protected static class ResizeStopEvent extends ResizeEvent
	{
	}
}
