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
package com.googlecode.wicket.kendo.ui.widget.window;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a Kendo UI window behavior.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.17.0
 */
public abstract class WindowBehavior extends KendoUIBehavior implements IJQueryAjaxAware, IWindowListener
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "kendoWindow";

	private JQueryAjaxBehavior onActionAjaxBehavior = null;
	private JQueryAjaxBehavior onCloseAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public WindowBehavior(String selector)
	{
		super(selector, METHOD);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public WindowBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.isActionEventEnabled())
		{
			this.onActionAjaxBehavior = this.newOnActionAjaxBehavior(this);
			component.add(this.onActionAjaxBehavior);
		}

		if (this.isCloseEventEnabled())
		{
			this.onCloseAjaxBehavior = this.newOnCloseAjaxBehavior(this);
			component.add(this.onCloseAjaxBehavior);
		}
	}

	/**
	 * Opens the window in ajax.<br/>
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void open(AjaxRequestTarget target)
	{
		if (this.isCentered())
		{
			target.appendJavaScript(this.widget() + ".center();");
		}

		target.appendJavaScript(this.widget() + ".open();");
	}

	/**
	 * Closes the window in ajax.<br/>
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void close(AjaxRequestTarget target)
	{
		target.prependJavaScript(this.widget() + ".close();");
	}

	// Properties //

	/**
	 * Indicates whether the window is centered
	 *
	 * @return false by default
	 */
	protected boolean isCentered()
	{
		return false;
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (this.onActionAjaxBehavior != null)
		{
			this.on(String.format("%s.wrapper.find('a.k-window-action').click(%s);", this.widget(), this.onActionAjaxBehavior.getCallbackFunction()));
		}

		if (this.onCloseAjaxBehavior != null)
		{
			this.setOption("close", this.onCloseAjaxBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ActionEvent)
		{
			this.onAction(target, ((ActionEvent) event).getAction());
		}

		if (event instanceof CloseEvent)
		{
			this.onClose(target);
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'a.k-window-action click' event, triggered when the user clicks on an action icon
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnActionAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnActionAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnActionAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'close' event, triggered when the user clicks on the X-icon
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnCloseAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnCloseAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnCloseAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'a.k-window-action click' event
	 */
	protected static class OnActionAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnActionAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("action", "jQuery(e.target).attr('class').match(/k-i-(\\w+)/)[1]") // lf
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ActionEvent();
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
		public String getCallbackFunction()
		{
			// prevent a dual call to #onClose if called manually
			return "function(e) { if (e.userTriggered) { " + this.getCallbackScript() + " } }";
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new CloseEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnActionAjaxBehavior} callback
	 */
	protected static class ActionEvent extends JQueryEvent
	{
		private final String action;

		public ActionEvent()
		{
			this.action = RequestCycleUtils.getQueryParameterValue("action").toString();
		}

		public String getAction()
		{
			return this.action;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnCloseAjaxBehavior} callback
	 */
	protected static class CloseEvent extends JQueryEvent
	{
	}
}
