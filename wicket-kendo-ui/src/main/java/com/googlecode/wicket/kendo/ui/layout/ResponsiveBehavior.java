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
package com.googlecode.wicket.kendo.ui.layout;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a {@value #METHOD} behavior
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.21.0
 * @since 7.1.0
 */
public class ResponsiveBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoResponsivePanel";

	private final IResponsiveListener listener;
	private JQueryAjaxBehavior onOpenAjaxBehavior = null;
	private JQueryAjaxBehavior onCloseAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IResponsiveListener}
	 */
	public ResponsiveBehavior(String selector, IResponsiveListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IResponsiveListener}
	 */
	public ResponsiveBehavior(String selector, Options options, IResponsiveListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.listener.isOpenEventEnabled())
		{
			this.onOpenAjaxBehavior = this.newOnOpenAjaxBehavior(this);
			component.add(this.onOpenAjaxBehavior);
		}

		if (this.listener.isCloseEventEnabled())
		{
			this.onCloseAjaxBehavior = this.newOnCloseAjaxBehavior(this);
			component.add(this.onCloseAjaxBehavior);
		}
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (this.onOpenAjaxBehavior != null)
		{
			this.setOption("open", this.onOpenAjaxBehavior.getCallbackFunction());
		}

		if (this.onCloseAjaxBehavior != null)
		{
			this.setOption("close", this.onCloseAjaxBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof OpenEvent)
		{
			this.listener.onOpen(target);
		}

		if (event instanceof CloseEvent)
		{
			this.listener.onClose(target);
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'open' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnOpenAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnOpenAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnOpenAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'close' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnCloseAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnCloseAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnCloseAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'open' event
	 */
	protected static class OnOpenAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnOpenAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new OpenEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'close' event
	 */
	protected static class OnCloseAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnCloseAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new CloseEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnOpenAjaxBehavior} callback
	 */
	protected static class OpenEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnCloseAjaxBehavior} callback
	 */
	protected static class CloseEvent extends JQueryEvent
	{
	}
}
