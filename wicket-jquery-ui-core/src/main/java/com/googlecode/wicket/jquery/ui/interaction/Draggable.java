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
package com.googlecode.wicket.jquery.ui.interaction;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSink;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryContainer;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;

/**
 * Provides a jQuery draggable element based on a {@link JQueryContainer}
 *
 * @param <T> the object model type
 * @author Sebastien Briquet - sebfz1
 */
public class Draggable<T> extends JQueryContainer
{
	private static final long serialVersionUID = 1L;

	public enum Axis {
		X("'x'"),
		Y("'y'");

		private String axis;

		private Axis(String axis)
		{
			this.axis = axis;
		}

		@Override
		public String toString()
		{
			return this.axis;
		}
	}

	public enum Containment {
		Parent("'parent'"),
		Document("'document'"),
		Window("'window'");

		private String containment;

		private Containment(String containment)
		{
			this.containment = containment;
		}

		@Override
		public String toString()
		{
			return this.containment;
		}
	}


	private JQueryAjaxBehavior onDragStartBehavior;
	private JQueryAjaxBehavior onDragStopBehavior;
	private Options options;

	/**
	 * Contructor
	 * @param id the markup id
	 */
	public Draggable(String id)
	{
		super(id);
		this.init();
	}

	/**
	 * Contructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Draggable(String id, IModel<T> model)
	{
		super(id, model);
		this.init();
	}


	// Methods //
	/**
	 * Initialization
	 */
	private void init()
	{
		this.options = new Options();
	}


	// Getters / Setters //
	/**
	 * Gets the model
	 * @return {@link IModel}
	 */
	@SuppressWarnings("unchecked")
	public IModel<T> getModel()
	{
		return (IModel<T>) this.getDefaultModel();
	}

	/**
	 * Gets the model object
	 * @return the model object
	 */
	@SuppressWarnings("unchecked")
	public T getModelObject()
	{
		return (T)this.getDefaultModelObject();
	}

	/**
	 * Indicates whether the 'stop' event is enabled. If false, the {@link #onDragStop(AjaxRequestTarget)} event will not be triggered.
	 * @return false by default
	 */
	protected boolean isStopEventEnabled()
	{
		return false;
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.onDragStartBehavior = this.newOnDragStartBehavior());
		this.add(this.onDragStopBehavior = this.newOnDragStopBehavior());
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		// dragStartBehavior is multicasted; need to check that 'this' is the expected source (in case of several Draggables)
		if ((event.getPayload() instanceof JQueryEvent) && (event.getSource() == this))
		{
			JQueryEvent payload = (JQueryEvent) event.getPayload();

			if (payload instanceof Draggable.DragStartEvent)
			{
				this.onDragStart(payload.getTarget());
			}

			if (payload instanceof Draggable.DragStopEvent)
			{
				this.onDragStop(payload.getTarget());
			}
		}
	}

	/**
	 * Triggered when the drag starts
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onDragStart(AjaxRequestTarget target)
	{
	}

	/**
	 * Triggered when the drag stops<br />
	 * @param target the {@link AjaxRequestTarget}
	 * @see #isStopEventEnabled()
	 */
	protected void onDragStop(AjaxRequestTarget target)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryBehavior(selector, "draggable", this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				Draggable.this.onConfigure(this);

				this.setOption("start", Draggable.this.onDragStartBehavior.getCallbackFunction());

				if (Draggable.this.isStopEventEnabled())
				{
					this.setOption("stop", Draggable.this.onDragStopBehavior.getCallbackFunction());
				}
			}
		};
	}


	// Options //
	/**
	 * Sets the {@link Axis} on which it is possible to drag the component
	 * @param axis the {@link Axis} value
	 * @return the {@link Draggable}
	 */
	public Draggable<T> setAxis(Axis axis)
	{
		this.options.set("axis", axis);
		return this;
	}

	/**
	 * Sets the grid on which snapping the component
	 * @param grid a [x, y] {@link List}, assuming its toString() method returns [x, y] (like ArrayList)
	 * @return the {@link Draggable}
	 */
	public Draggable<T> setGrid(List<Integer> grid)
	{
		if(grid.size() == 2)
		{
			this.options.set("grid", grid.toString());
		}

		return this;
	}

	/**
	 * Sets the container on which this component is allowed to move.
	 * @param component a {@link Component}
	 * @return the {@link Draggable}
	 */
	public Draggable<T> setContainment(Component component)
	{
		return this.setContainment(JQueryWidget.getSelector(component));
	}

	/**
	 * Sets the container, specified by its selector, on which this component is allowed to move.
	 * @param selector the container selector (ie: '#myId')
	 * @return the {@link Draggable}
	 */
	public Draggable<T> setContainment(String selector)
	{
		this.options.set("containment", Options.asString(selector));
		return this;
	}

	/**
	 * Sets the container, specified by a {@link Containment}, on which this component is allowed to move.
	 * @param containment the {@link Containment} value
	 * @return the {@link Draggable}
	 */
	public Draggable<T> setContainment(Containment containment)
	{
		this.options.set("containment", containment);
		return this;
	}

	/**
	 * Set whether the component should revert to its original position
	 * @param revert yes/no
	 * @return the {@link Draggable}
	 */
	public Draggable<T> setRevert(Boolean revert)
	{
		this.options.set("revert", revert);
		return this;
	}


	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'dragStart' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnDragStartBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Broadcast getBroadcast()
			{
				return Broadcast.BREADTH; //start from the page (see #getSink()) and go deeper, this is important for the Droppable to be notified
			}

			@Override
			protected IEventSink getSink()
			{
				return Draggable.this.getPage();
			}

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new DragStartEvent(target);
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'dragStop' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnDragStopBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new DragStopEvent(target);
			}
		};
	}


	// Events classes //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'start' callback
	 */
	public class DragStartEvent extends JQueryEvent
	{
		public DragStartEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'stop' callback
	 */
	public class DragStopEvent extends JQueryEvent
	{
		public DragStopEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}
}
