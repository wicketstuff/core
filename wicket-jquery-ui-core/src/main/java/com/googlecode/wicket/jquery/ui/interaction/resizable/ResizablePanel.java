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
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.JQueryPanel;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * Provides a jQuery UI resizable {@link JQueryPanel}.<br/>
 * <br/>
 * This class is marked as <code>abstract</code> because no markup is associated with this panel. It is up to the user to supply the corresponding markup.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class ResizablePanel extends JQueryPanel
{
	private static final long serialVersionUID = 1L;

	private JQueryAjaxBehavior onResizeStartBehavior;
	private JQueryAjaxBehavior onResizeStopBehavior;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public ResizablePanel(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public ResizablePanel(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public ResizablePanel(String id, IModel<?> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public ResizablePanel(String id, IModel<?> model, Options options)
	{
		super(id, model, options);
	}


	// Properties //
	/**
	 * Indicates whether the 'start' event is enabled.<br />
	 * If true, the {@link #onResizeStart(AjaxRequestTarget, int, int, int, int)} event will be triggered.
	 *
	 * @return false by default
	 */
	protected boolean isResizeStartEventEnabled()
	{
		return false;
	}

	/**
	 * Indicates whether the 'stop' event is enabled.<br />
	 * If true, the {@link #onResizeStop(AjaxRequestTarget, int, int, int, int)} event will be triggered.
	 *
	 * @return false by default
	 */
	protected boolean isResizeStopEventEnabled()
	{
		return false;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.onResizeStartBehavior = this.newOnResizeStartBehavior());
		this.add(this.onResizeStopBehavior = this.newOnResizeStopBehavior());

		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
		//sets options here
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		super.onEvent(event);

		if (event.getPayload() instanceof ResizeEvent)
		{
			ResizeEvent payload = (ResizeEvent) event.getPayload();
			AjaxRequestTarget target = payload.getTarget();

			if (payload instanceof ResizeStartEvent)
			{
				this.onResizeStart(target, payload.getTop(), payload.getLeft(), payload.getWidth(), payload.getHeight());
			}

			if (payload instanceof ResizeStopEvent)
			{
				this.onResizeStop(target, payload.getTop(), payload.getLeft(), payload.getWidth(), payload.getHeight());
			}
		}
	}

	/**
	 * Triggered when the resize event starts
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param top the position's top value
	 * @param left the position's left value
	 * @param width the size's width value
	 * @param height the size's height value
	 *
	 * @see #isResizeStartEventEnabled()
	 */
	protected void onResizeStart(AjaxRequestTarget target, int top, int left, int width, int height)
	{
	}

	/**
	 * Triggered when the resize event stops
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param top the position's top value
	 * @param left the position's left value
	 * @param width the size's width value
	 * @param height the size's height value
	 *
	 * @see #isResizeStopEventEnabled()
	 */
	protected void onResizeStop(AjaxRequestTarget target, int top, int left, int width, int height)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ResizableBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				ResizablePanel.this.onConfigure(this);

				if (ResizablePanel.this.isResizeStartEventEnabled())
				{
					this.setOption("start", ResizablePanel.this.onResizeStartBehavior.getCallbackFunction());
				}

				if (ResizablePanel.this.isResizeStopEventEnabled())
				{
					this.setOption("stop", ResizablePanel.this.onResizeStopBehavior.getCallbackFunction());
				}
			}
		};
	}


	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'start' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnResizeStartBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&top=' + ui.position.top + '&left=' + ui.position.left + '&width=' + ui.size.width + '&height=' + ui.size.height");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ResizeStartEvent(target);
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'stop' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnResizeStopBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&top=' + ui.position.top + '&left=' + ui.position.left + '&width=' + ui.size.width + '&height=' + ui.size.height");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ResizeStopEvent(target);
			}
		};
	}


	// Event Objects //
	/**
	 * Provides a base class for resize event object
	 */
	private class ResizeEvent extends JQueryEvent
	{
		private final int top;
		private final int left;
		private final int width;
		private final int height;

		/**
		 * Constructor.
		 * @param target the {@link AjaxRequestTarget}
		 */
		public ResizeEvent(AjaxRequestTarget target)
		{
			super(target);

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
	protected class ResizeStartEvent extends ResizeEvent
	{

		/**
		 * Constructor.
		 * @param target the {@link AjaxRequestTarget}
		 */
		public ResizeStartEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'stop' callback
	 */
	protected class ResizeStopEvent extends ResizeEvent
	{
		/**
		 * Constructor.
		 * @param target the {@link AjaxRequestTarget}
		 */
		public ResizeStopEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}
}
