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
import com.googlecode.wicket.jquery.core.JQueryGenericContainer;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a jQuery draggable element based on a {@link JQueryGenericContainer}
 *
 * @param <T> the object model type
 * @author Sebastien Briquet - sebfz1
 */
public class Draggable<T> extends JQueryGenericContainer<T> implements IDraggableListener
{
	private static final long serialVersionUID = 1L;

	public enum Axis
	{
		X("'x'"), // lf
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

	public enum Containment
	{
		Parent("'parent'"), // lf
		Document("'document'"), // lf
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
	 * 
	 * @param id the markup id
	 */
	public Draggable(String id)
	{
		super(id);

		this.initialize();
	}

	/**
	 * Contructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Draggable(String id, IModel<T> model)
	{
		super(id, model);

		this.initialize();
	}

	// Methods //

	/**
	 * Initialization
	 */
	private void initialize()
	{
		this.options = new Options();
	}

	// Properties //

	@Override
	public boolean isStopEventEnabled()
	{
		return false;
	}

	// Events //

	@Override
	public void onDragStart(AjaxRequestTarget target, int top, int left)
	{
		// noop
	}

	@Override
	public void onDragStop(AjaxRequestTarget target, int top, int left)
	{
		// noop
	}

	// Options //

	/**
	 * Sets the {@link Axis} on which it is possible to drag the component
	 * 
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
	 * 
	 * @param grid a [x, y] {@link List}, assuming its toString() method returns [x, y] (like ArrayList)
	 * @return the {@link Draggable}
	 */
	public Draggable<T> setGrid(List<Integer> grid)
	{
		if (grid.size() == 2)
		{
			this.options.set("grid", grid.toString());
		}

		return this;
	}

	/**
	 * Sets the container on which this component is allowed to move.
	 * 
	 * @param component a {@link Component}
	 * @return the {@link Draggable}
	 */
	public Draggable<T> setContainment(Component component)
	{
		return this.setContainment(JQueryWidget.getSelector(component));
	}

	/**
	 * Sets the container, specified by its selector, on which this component is allowed to move.
	 * 
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
	 * 
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
	 * 
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
			public boolean isStopEventEnabled()
			{
				return Draggable.this.isStopEventEnabled();
			}

			@Override
			public void onDragStart(AjaxRequestTarget target, int top, int left)
			{
				Draggable.this.onDragStart(target, top, left);
			}

			@Override
			public void onDragStop(AjaxRequestTarget target, int top, int left)
			{
				Draggable.this.onDragStop(target, top, left);
			}
		};
	}
}
