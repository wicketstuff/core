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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryGenericContainer;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a Kendo UI draggable element based on a {@link JQueryGenericContainer}
 *
 * @param <T> the object model type
 * @author Sebastien Briquet - sebfz1
 */
public class Draggable<T> extends JQueryGenericContainer<T> implements IDraggableListener
{
	private static final long serialVersionUID = 1L;

	public enum Axis
	{
		X("x"), // lf
		Y("y");

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

	private Options options;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 */
	public Draggable(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public Draggable(String id, Options options)
	{
		super(id);

		this.options = Args.notNull(options, "options");
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Draggable(String id, IModel<T> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public Draggable(String id, IModel<T> model, Options options)
	{
		super(id, model);

		this.options = Args.notNull(options, "options");
	}

	// Properties //

	@Override
	public boolean isCancelEventEnabled()
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

	@Override
	public void onDragCancel(AjaxRequestTarget target, int top, int left)
	{
		// noop
	}

	// Options //

	/**
	 * Constrains the hint movement to either the horizontal (x) or vertical (y) axis.
	 * 
	 * @param axis the {@link Axis} value
	 * @return this, for chaining
	 */
	public Draggable<T> setAxis(Axis axis)
	{
		this.options.set("axis", Options.asString(axis));

		return this;
	}

	/**
	 * Provides a way for customization of the drag indicator
	 * 
	 * @param function the javascript function
	 * @return this, for chaining
	 */
	protected Draggable<T> getHint(String function)
	{
		this.options.set("hint", function);

		return this;
	}

	/**
	 * Sets the required distance that the mouse should travel in order to initiate a drag.
	 * 
	 * @param distance the distance
	 * @return this, for chaining
	 */
	public Draggable<T> setDistance(Integer distance)
	{
		this.options.set("distance", distance);

		return this;
	}

	/**
	 * If set, the hint movement is constrained to the container boundaries.
	 * 
	 * @param component a {@link Component}
	 * @return this, for chaining
	 */
	public Draggable<T> setContainer(Component component)
	{
		return this.setContainer(JQueryWidget.getSelector(component));
	}

	/**
	 * If set, the hint movement is constrained to the container boundaries.
	 * 
	 * @param selector the container selector (ie: '#myId')
	 * @return this, for chaining
	 */
	public Draggable<T> setContainer(String selector)
	{
		this.options.set("container", String.format("jQuery(%s)", Options.asString(selector)));

		return this;
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new DraggableBehavior(selector, this.options, this);
	}
}
