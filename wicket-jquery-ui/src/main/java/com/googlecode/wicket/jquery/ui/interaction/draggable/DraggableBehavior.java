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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.interaction.droppable.DroppableBehavior;

/**
 * Provides a jQuery draggable behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DraggableBehavior extends JQueryBehavior implements IJQueryAjaxAware, IDraggableListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "draggable";

	private JQueryAjaxBehavior onDragStartBehavior;
	private JQueryAjaxBehavior onDragStopBehavior = null;
	private Component component;

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public DraggableBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public DraggableBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Methods //
	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.add(this.onDragStartBehavior = this.newOnDragStartBehavior());

		// these events are not enabled by default to prevent unnecessary server round-trips.
		if (this.isStopEventEnabled())
		{
			component.add(this.onDragStopBehavior = this.newOnDragStopBehavior());
		}

		this.component = component; //warning, not thread-safe: the instance of this behavior should only be used once
	}

	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("start", this.onDragStartBehavior.getCallbackFunction());

		if (this.onDragStopBehavior != null)
		{
			this.setOption("stop", this.onDragStopBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DragStartEvent)
		{
			this.onDragStart(target);

			// register to all DroppableBehavior(s) //
			target.getPage().visitChildren(this.newDroppableBehaviorVisitor());
		}

		else if (event instanceof DragStopEvent)
		{
			this.onDragStop(target);
		}
	}

	// Factories //
	/**
	 * TODO javadoc
	 * @return
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
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'start' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnDragStartBehavior()
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
				return new DragStartEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'stop' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnDragStopBehavior()
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
				return new DragStopEvent();
			}
		};
	}


	// Events classes //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'start' callback
	 */
	protected static class DragStartEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'stop' callback
	 */
	protected static class DragStopEvent extends JQueryEvent
	{
	}
}