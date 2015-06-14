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
package com.googlecode.wicket.kendo.ui.interaction.draggable;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.apache.wicket.util.visit.Visits;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.interaction.droppable.DroppableBehavior;

/**
 * Provides a Kendo UI draggable behavior<br/>
 * <b>Note:</b> This behavior should be attached directly to the component to be dragged. Therefore the 'filter' option will not work here.<br/>
 * <b>Warning:</b> not thread-safe: the instance of this behavior should only be used once
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DraggableBehavior extends KendoUIBehavior implements IJQueryAjaxAware, IDraggableListener
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoDraggable";

	public static final String CSS_HIDE = "kendoDraggable-hide";
	public static final String CSS_CLONE = "kendoDraggable-clone";

	/** default hint */
	public static final String HINT = "function(element) { return element.clone().addClass('" + CSS_CLONE + "'); }";

	private JQueryAjaxBehavior onDragStartAjaxBehavior;
	private JQueryAjaxBehavior onDragStopAjaxBehavior = null;
	private JQueryAjaxBehavior onDragCancelAjaxBehavior = null;

	private Component component = null;

	/**
	 * Constructor
	 */
	public DraggableBehavior()
	{
		this(null, new Options());
	}

	/**
	 * Constructor
	 * 
	 * @param options the {@link Options}
	 */
	public DraggableBehavior(Options options)
	{
		this(null, options);
	}

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 */
	protected DraggableBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	protected DraggableBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
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

		this.onDragStopAjaxBehavior = this.newOnDragStopAjaxBehavior(this);
		this.component.add(this.onDragStopAjaxBehavior);

		// this event is not enabled by default to prevent unnecessary server round-trips.
		if (this.isCancelEventEnabled())
		{
			this.onDragCancelAjaxBehavior = this.newOnDragCancelAjaxBehavior(this);
			this.component.add(this.onDragCancelAjaxBehavior);
		}
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// options //

		if (this.getOption("hint") == null)
		{
			this.setOption("hint", HINT);
		}

		// behaviors //

		this.setOption("dragstart", this.onDragStartAjaxBehavior.getCallbackFunction());
		this.setOption("dragend", this.onDragStopAjaxBehavior.getCallbackFunction());

		if (this.onDragCancelAjaxBehavior != null)
		{
			this.setOption("dragcancel", this.onDragCancelAjaxBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DraggableEvent)
		{
			DraggableEvent e = (DraggableEvent) event;

			if (e instanceof DragStartEvent)
			{
				// register to all DroppableBehavior(s) //
				Visits.visit(target.getPage(), this.newDroppableBehaviorVisitor());

				this.onDragStart(target, e.getTop(), e.getLeft());
			}

			else if (e instanceof DragStopEvent)
			{
				this.onDragStop(target, e.getTop(), e.getLeft());
			}

			else if (e instanceof DragCancelEvent)
			{
				this.onDragCancel(target, e.getTop(), e.getLeft());
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
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'dragstart' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnDragStartAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDragStartAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDragStartAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'dragend' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnDragStopAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDragStopAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDragStopAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'dragcancel' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnDragCancelAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDragCancelAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDragCancelAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'dragstart' event
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
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("top", "e.sender.hintOffset.top | 0"), // cast to int, no rounding
					CallbackParameter.resolved("left", "e.sender.hintOffset.left | 0") // cast to int, no rounding
			};
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			String statement = "this.element.addClass('" + CSS_HIDE + "');";
			return statement + super.getCallbackFunctionBody(parameters);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DragStartEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'dragend' event
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
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("top", "e.sender.hintOffset.top | 0"), // cast to int, no rounding
					CallbackParameter.resolved("left", "e.sender.hintOffset.left | 0") // cast to int, no rounding
			};
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			String statement = "this.element.removeClass('" + CSS_HIDE + "');";
			return statement + super.getCallbackFunctionBody(parameters);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DragStopEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'dragcancel' event
	 */
	protected static class OnDragCancelAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnDragCancelAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("top", "e.sender.hintOffset.top | 0"), // cast to int, no rounding
					CallbackParameter.resolved("left", "e.sender.hintOffset.left | 0") // cast to int, no rounding
			};
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			String statement = "this.element.removeClass('" + CSS_HIDE + "');";
			return statement + super.getCallbackFunctionBody(parameters);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DragCancelEvent();
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

		/**
		 * Constructor.
		 */
		public DraggableEvent()
		{
			this.top = RequestCycleUtils.getQueryParameterValue("top").toInt(-1);
			this.left = RequestCycleUtils.getQueryParameterValue("left").toInt(-1);
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

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDragCancelAjaxBehavior} callback
	 */
	protected static class DragCancelEvent extends DraggableEvent
	{
	}
}
