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

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a jQuery draggable element based on a {@link JQueryContainer}
 *
 * @param <T> the object model type
 * @author Sebastien Briquet - sebfz1
 */
public class Draggable<T> extends JQueryContainer implements IDraggableListener
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

	// Properties //
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

	@Override
	public boolean isStopEventEnabled()
	{
		return false;
	}

	// Events //
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
	public void onDragStart(AjaxRequestTarget target)
	{
	}

	@Override
	public void onDragStop(AjaxRequestTarget target)
	{
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

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new DraggableBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				Draggable.this.onConfigure(this);
			}

			@Override
			public boolean isStopEventEnabled()
			{
				return Draggable.this.isStopEventEnabled();
			}

			@Override
			public void onDragStart(AjaxRequestTarget target)
			{
				Draggable.this.onDragStart(target);
			}

			@Override
			public void onDragStop(AjaxRequestTarget target)
			{
				Draggable.this.onDragStop(target);
			}
		};
	}
}
