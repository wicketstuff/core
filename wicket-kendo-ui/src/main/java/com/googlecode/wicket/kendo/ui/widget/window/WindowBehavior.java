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
	private static final String METHOD = "kendoWindow";

	private JQueryAjaxBehavior onActionBehavior = null;
	private JQueryAjaxBehavior onCloseBehavior = null;

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
			component.add(this.onActionBehavior = this.newActionBehavior());
		}

		if (this.isCloseEventEnabled())
		{
			component.add(this.onCloseBehavior = this.newCloseBehavior());
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

		if (this.onActionBehavior != null)
		{
			this.on(String.format("%s.wrapper.find('a.k-window-action').click(%s);", this.widget(), this.onActionBehavior.getCallbackFunction()));
		}

		if (this.onCloseBehavior != null)
		{
			this.setOption("close", this.onCloseBehavior.getCallbackFunction());
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

	protected JQueryAjaxBehavior newActionBehavior()
	{
		return new JQueryAjaxBehavior(this) {
			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				// function(e) { }
				return new CallbackParameter[] { // lf
						CallbackParameter.context("e"), // lf
						CallbackParameter.resolved("action", "jQuery(e.target).attr('class').match(/k-i-(\\w+)/)[1]") // lf
				};
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ActionEvent();
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when the user clicks on the X-icon
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newCloseBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

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
		};
	}

	// Event class //

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
	 * An event object that will be broadcasted when the user clicks on the X-icon
	 */
	protected static class CloseEvent extends JQueryEvent
	{
	}
}
