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
package com.googlecode.wicket.kendo.ui.interaction.droppable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.interaction.draggable.DraggableBehavior;

/**
 * Provides a Kendo UI droppable (drop target) behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class DroppableBehavior extends KendoUIBehavior implements IJQueryAjaxAware, IDroppableListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoDropTarget";

	private JQueryAjaxBehavior onDropAjaxBehavior;
	private JQueryAjaxBehavior onDragEnterAjaxBehavior = null;
	private JQueryAjaxBehavior onDragLeaveAjaxBehavior = null;

	/** object being dragged */
	private transient Component draggable = null;

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 */
	public DroppableBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * 
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

	/**
	 * Gets the javascript statement that will we executed on 'dragenter' event
	 * The event variable is {@code 'e'}
	 * 
	 * @return the javascript statement
	 */
	protected String getOnDragEnterStatement()
	{
		return "";
	}

	/**
	 * Gets the javascript statement that will we executed on 'dragleave' event
	 * The event variable is {@code 'e'}
	 * 
	 * @return the javascript statement
	 */
	protected String getOnDragLeaveStatement()
	{
		return "";
	}

	/**
	 * Gets the javascript statement that will we executed on 'drop' event<br/>
	 * The event variable is {@code 'e'}
	 * 
	 * @return the javascript statement
	 */
	protected String getOnDropStatement()
	{
		StringBuilder statement = new StringBuilder();

		statement.append("var $clone = jQuery(e.target),");
		statement.append("$element = e.draggable.element;");
		statement.append("$element.removeClass('").append(DraggableBehavior.CSS_HIDE).append("');");
		statement.append("$element.offset($clone.offset());");
//		statement.append("e.draggable.destroy();"); // prevent dragStop to be fired (dual round-trip)

		return statement.toString();
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.onDropAjaxBehavior = this.newOnDropAjaxBehavior(this);
		component.add(this.onDropAjaxBehavior);

		// these events are not enabled by default to prevent unnecessary server round-trips.
		if (this.isDragEnterEventEnabled())
		{
			this.onDragEnterAjaxBehavior = this.newOnDragEnterAjaxBehavior(this);
			component.add(this.onDragEnterAjaxBehavior);
		}

		if (this.isDragLeaveEventEnabled())
		{
			this.onDragLeaveAjaxBehavior = this.newOnDragLeaveAjaxBehavior(this);
			component.add(this.onDragLeaveAjaxBehavior);
		}
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("drop", this.onDropAjaxBehavior.getCallbackFunction());

		if (this.onDragEnterAjaxBehavior != null)
		{
			this.setOption("dragenter", this.onDragEnterAjaxBehavior.getCallbackFunction());
		}

		if (this.onDragLeaveAjaxBehavior != null)
		{
			this.setOption("dragleave", this.onDragLeaveAjaxBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof DropEvent)
		{
			this.onDrop(target, this.draggable);
		}

		else if (event instanceof DragEnterEvent)
		{
			this.onDragEnter(target, this.draggable);
		}

		else if (event instanceof DragLeaveEvent)
		{
			this.onDragLeave(target, this.draggable);
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'dragenter' javascript event
	 * 
	 * @param source the {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnDragEnterAjaxBehavior(IJQueryAjaxAware source)
	{
		return new JQueryAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("e") };
			}

			@Override
			public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
			{
				return DroppableBehavior.this.getOnDragEnterStatement() + super.getCallbackFunctionBody(parameters);
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new DragEnterEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'dragleave' javascript event
	 * 
	 * @param source the {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnDragLeaveAjaxBehavior(IJQueryAjaxAware source)
	{
		return new JQueryAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("e") };
			}

			@Override
			public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
			{
				return DroppableBehavior.this.getOnDragLeaveStatement() + super.getCallbackFunctionBody(parameters);
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new DragLeaveEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'drop' javascript event
	 * 
	 * @param source the {@link IJQueryAjaxAware}
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnDropAjaxBehavior(IJQueryAjaxAware source)
	{
		return new JQueryAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("e") };
			}

			@Override
			public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
			{
				return DroppableBehavior.this.getOnDropStatement() + super.getCallbackFunctionBody(parameters);
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new DropEvent();
			}
		};
	}

	// Event classes //

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'over' callback
	 */
	protected static class DragEnterEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'exit' callback
	 */
	protected static class DragLeaveEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'drop' callback
	 */
	protected static class DropEvent extends JQueryEvent
	{
	}
}