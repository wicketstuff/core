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
package com.googlecode.wicket.jquery.ui.interaction.draggable;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.apache.wicket.util.visit.Visits;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;
import com.googlecode.wicket.jquery.ui.interaction.droppable.DroppableBehavior;

/**
 * Provides a jQuery draggable behavior<br/>
 * <br/>
 * <b>Warning:</b> not thread-safe: the instance of this behavior should only be used once
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DraggableBehavior extends JQueryUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "draggable";

	/** event listener */
	private final IDraggableListener listener;

	private JQueryAjaxBehavior onDragStartAjaxBehavior;
	private JQueryAjaxBehavior onDragStopAjaxBehavior = null;
	private Component component = null;

	/**
	 * Constructor
	 * 
	 * @param listener the {@link IDraggableListener}
	 */
	public DraggableBehavior(IDraggableListener listener)
	{
		this(null, new Options(), listener);
	}

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IDraggableListener}
	 */
	public DraggableBehavior(String selector, IDraggableListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 * 
	 * @param options the {@link Options}
	 * @param listener the {@link IDraggableListener}
	 */
	public DraggableBehavior(Options options, IDraggableListener listener)
	{
		this(null, options, listener);
	}

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IDraggableListener}
	 */
	public DraggableBehavior(String selector, Options options, IDraggableListener listener)
	{
		super(selector, METHOD, options);
		
		this.listener = Args.notNull(listener, "listener");
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.component != null)
		{
			throw new WicketRuntimeException("Behavior is already bound to another component.");
		}

		this.component = component; // warning, not thread-safe: the instance of this behavior should only be used once

		if (this.selector == null)
		{
			this.selector = JQueryWidget.getSelector(this.component);
		}

		this.onDragStartAjaxBehavior = this.newOnDragStartAjaxBehavior(this);
		this.component.add(this.onDragStartAjaxBehavior);

		// this event is not enabled by default to prevent unnecessary server round-trips.
		if (this.listener.isStopEventEnabled())
		{
			this.onDragStopAjaxBehavior = this.newOnDragStopAjaxBehavior(this);
			this.component.add(this.onDragStopAjaxBehavior);
		}
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("start", this.onDragStartAjaxBehavior.getCallbackFunction());

		if (this.onDragStopAjaxBehavior != null)
		{
			this.setOption("stop", this.onDragStopAjaxBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DraggableEvent)
		{
			DraggableEvent ev = (DraggableEvent) event;

			if (ev instanceof DragStartEvent)
			{
				// register to all DroppableBehavior(s) //
				Visits.visit(target.getPage(), this.newDroppableBehaviorVisitor());

				this.listener.onDragStart(target, ev.getTop(), ev.getLeft());
			}

			else if (ev instanceof DragStopEvent)
			{
				this.listener.onDragStop(target, ev.getTop(), ev.getLeft());
			}
		}
	}

	// Factories //

	/**
	 * Gets a new {@link DroppableBehavior} visitor.<br/>
	 * This {@link IVisitor} is responsible for register the {@link DroppableBehavior} to this {@link DraggableBehavior}
	 *
	 * @return a {@link IVisitor}
	 */
	private IVisitor<Component, ?> newDroppableBehaviorVisitor()
	{
		return new IVisitor<Component, Void>() {

			@Override
			public void component(Component component, IVisit<Void> visit)
			{
				for (DroppableBehavior behavior : component.getBehaviors(DroppableBehavior.class))
				{
					behavior.setDraggable(DraggableBehavior.this.component);
				}
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'start' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnDragStartAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDragStartAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDragStartAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'stop' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnDragStopAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDragStopAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDragStopAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'start' event
	 */
	protected static class OnDragStartAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnDragStartAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("top", "ui.position.top | 0"), // cast to int, no rounding
					CallbackParameter.resolved("left", "ui.position.left | 0"), // cast to int, no rounding
					CallbackParameter.resolved("offsetTop", "ui.offset.top | 0"), // cast to int, no rounding
					CallbackParameter.resolved("offsetLeft", "ui.offset.left | 0") // cast to int, no rounding
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DragStartEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'stop' event
	 */
	protected static class OnDragStopAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnDragStopAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("top", "ui.position.top | 0"), // cast to int, no rounding
					CallbackParameter.resolved("left", "ui.position.left | 0"), // cast to int, no rounding
					CallbackParameter.resolved("offsetTop", "ui.offset.top | 0"), // cast to int, no rounding
					CallbackParameter.resolved("offsetLeft", "ui.offset.left | 0") // cast to int, no rounding
			};
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DragStopEvent();
		}
	}

	// Event objects //

	/**
	 * Provides a base class for {@link DraggableBehavior} event objects
	 */
	protected static class DraggableEvent extends JQueryEvent
	{
		private final int top;
		private final int left;
		private final int offsetTop;
		private final int offsetLeft;

		/**
		 * Constructor.
		 */
		public DraggableEvent()
		{
			this.top = RequestCycleUtils.getQueryParameterValue("top").toInt(-1);
			this.left = RequestCycleUtils.getQueryParameterValue("left").toInt(-1);
			this.offsetTop = RequestCycleUtils.getQueryParameterValue("offsetTop").toInt(-1);
			this.offsetLeft = RequestCycleUtils.getQueryParameterValue("offsetLeft").toInt(-1);
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
		 * Gets the offset's top value
		 * 
		 * @return the offset's top value
		 */
		public int getOffsetTop()
		{
			return this.offsetTop;
		}

		/**
		 * Gets the offset's left value
		 * 
		 * @return the offset's left value
		 */
		public int getOffsetLeft()
		{
			return this.offsetLeft;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDragStartAjaxBehavior} callback
	 */
	protected static class DragStartEvent extends DraggableEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDragStopAjaxBehavior} callback
	 */
	protected static class DragStopEvent extends DraggableEvent
	{
	}
}
