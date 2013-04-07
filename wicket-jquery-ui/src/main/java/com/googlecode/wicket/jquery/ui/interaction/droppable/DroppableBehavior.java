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
package com.googlecode.wicket.jquery.ui.interaction.droppable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;

/**
 * Provides a jQuery droppable behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DroppableBehavior extends JQueryBehavior implements IJQueryAjaxAware, IDroppableListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "droppable";

	private JQueryAjaxBehavior onDropBehavior;
	private JQueryAjaxBehavior onOverBehavior = null;
	private JQueryAjaxBehavior onExitBehavior = null;

	private transient Component draggable = null;  /* object being dragged */

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public DroppableBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public DroppableBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Properties //
	public void setDraggable(Component draggable)
	{
		this.draggable = draggable;
	}

	// Methods //
	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.add(this.onDropBehavior = this.newOnDropBehavior());

		// these events are not enabled by default to prevent unnecessary server round-trips.
		if (this.isOverEventEnabled())
		{
			component.add(this.onOverBehavior = this.newOnOverBehavior());
		}

		if (this.isExitEventEnabled())
		{
			component.add(this.onExitBehavior = this.newOnExitBehavior());
		}
	}

	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("drop", this.onDropBehavior.getCallbackFunction());

		if (this.onOverBehavior != null)
		{
			this.setOption("over", this.onOverBehavior.getCallbackFunction());
		}

		if (this.onExitBehavior != null)
		{
			this.setOption("out", this.onExitBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DropEvent)
		{
			this.onDrop(target, this.draggable);
		}

		else if (event instanceof OverEvent)
		{
			this.onOver(target, this.draggable);
		}

		else if (event instanceof ExitEvent)
		{
			this.onExit(target, this.draggable);
		}
	}

	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'drop' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnDropBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("event"), CallbackParameter.context("ui") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new DropEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'over' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnOverBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("event"), CallbackParameter.context("ui") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new OverEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'exit' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnExitBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("event"), CallbackParameter.context("ui") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ExitEvent();
			}
		};
	}

	// Event classes //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'drop' callback
	 */
	protected static class DropEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'over' callback
	 */
	protected static class OverEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'exit' callback
	 */
	protected static class ExitEvent extends JQueryEvent
	{
	}
}