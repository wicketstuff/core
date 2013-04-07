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
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.interaction.draggable.Draggable;

/**
 * Provides a jQuery droppable area, on which {@link Draggable}<code>s</code> could be dropped.
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public abstract class Droppable<T> extends JQueryContainer implements IDroppableListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public Droppable(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Droppable(String id, IModel<T> model)
	{
		super(id, model);
	}

	// Properties //
	@Override
	public boolean isOverEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isExitEventEnabled()
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
	public abstract void onDrop(AjaxRequestTarget target, Component component);

	@Override
	public void onOver(AjaxRequestTarget target, Component component)
	{
	}

	@Override
	public void onExit(AjaxRequestTarget target, Component component)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new DroppableBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				Droppable.this.onConfigure(this);
			}

			@Override
			public boolean isOverEventEnabled()
			{
				return Droppable.this.isOverEventEnabled();
			}

			@Override
			public boolean isExitEventEnabled()
			{
				return Droppable.this.isExitEventEnabled();
			}

			@Override
			public void onDrop(AjaxRequestTarget target, Component component)
			{
				Droppable.this.onDrop(target, component);
			}

			@Override
			public void onOver(AjaxRequestTarget target, Component component)
			{
				Droppable.this.onOver(target, component);
			}

			@Override
			public void onExit(AjaxRequestTarget target, Component component)
			{
				Droppable.this.onExit(target, component);
			}
		};
	}
}
