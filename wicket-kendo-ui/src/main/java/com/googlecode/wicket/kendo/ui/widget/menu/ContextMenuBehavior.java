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
package com.googlecode.wicket.kendo.ui.widget.menu;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;

/**
 * Provides a Kendo UI context menu widget
 *
 * @author Martin Grigorov - martin-g
 * @since 6.20.0
 */
public abstract class ContextMenuBehavior extends MenuBehavior implements IContextMenuListener
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "kendoContextMenu";

	private JQueryAjaxBehavior onOpenAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public ContextMenuBehavior(String selector)
	{
		super(selector, METHOD);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public ContextMenuBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.isOpenEventEnabled())
		{
			this.onOpenAjaxBehavior = this.newOnOpenAjaxBehavior(this);
			component.add(this.onOpenAjaxBehavior);
		}
	}

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (this.onOpenAjaxBehavior != null)
		{
			this.setOption("open", this.onOpenAjaxBehavior.getCallbackFunction());
		}
	}

	// Events //

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		super.onAjax(target, event);

		if (event instanceof OpenEvent)
		{
			this.onOpen(target);
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'open' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnOpenAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnOpenAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnOpenAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'open' event
	 */
	public static class OnOpenAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnOpenAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new OpenEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnOpenAjaxBehavior} callback
	 */
	protected static class OpenEvent extends JQueryEvent
	{
	}
}
