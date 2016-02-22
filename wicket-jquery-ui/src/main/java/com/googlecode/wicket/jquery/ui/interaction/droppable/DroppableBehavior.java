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
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides a jQuery droppable behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DroppableBehavior extends JQueryUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "droppable";
	
	/** event listener */
	private final IDroppableListener listener; 

	private JQueryAjaxBehavior onDropAjaxBehavior;
	private JQueryAjaxBehavior onOverAjaxBehavior = null;
	private JQueryAjaxBehavior onExitAjaxBehavior = null;

	/** object being dragged */
	private transient Component draggable = null;

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IDroppableListener}
	 */
	public DroppableBehavior(String selector, IDroppableListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IDroppableListener}
	 */
	public DroppableBehavior(String selector, Options options, IDroppableListener listener)
	{
		super(selector, METHOD, options);
		
		this.listener = Args.notNull(listener, "listener");
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

		this.onDropAjaxBehavior = this.newOnDropAjaxBehavior(this);
		component.add(this.onDropAjaxBehavior);

		// these events are not enabled by default to prevent unnecessary server round-trips.
		if (this.listener.isOverEventEnabled())
		{
			this.onOverAjaxBehavior = this.newOnOverAjaxBehavior(this);
			component.add(this.onOverAjaxBehavior);
		}

		if (this.listener.isExitEventEnabled())
		{
			this.onExitAjaxBehavior = this.newOnExitAjaxBehavior(this);
			component.add(this.onExitAjaxBehavior);
		}
	}

	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("drop", this.onDropAjaxBehavior.getCallbackFunction());

		if (this.onOverAjaxBehavior != null)
		{
			this.setOption("over", this.onOverAjaxBehavior.getCallbackFunction());
		}

		if (this.onExitAjaxBehavior != null)
		{
			this.setOption("out", this.onExitAjaxBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DropEvent)
		{
			this.listener.onDrop(target, this.draggable);
		}

		else if (event instanceof OverEvent)
		{
			this.listener.onOver(target, this.draggable);
		}

		else if (event instanceof ExitEvent)
		{
			this.listener.onExit(target, this.draggable);
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'drop' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnDropAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDropAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDropAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'over' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnOverAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnOverAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnOverAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'exit' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnExitAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnExitAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnExitAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'drop' event
	 */
	protected static class OnDropAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnDropAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

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
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'over' event
	 */
	protected static class OnOverAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnOverAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

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
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'exit' event
	 */
	protected static class OnExitAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnExitAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

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
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDropAjaxBehavior} callback
	 */
	protected static class DropEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnOverAjaxBehavior} callback
	 */
	protected static class OverEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnExitAjaxBehavior} callback
	 */
	protected static class ExitEvent extends JQueryEvent
	{
	}
}
