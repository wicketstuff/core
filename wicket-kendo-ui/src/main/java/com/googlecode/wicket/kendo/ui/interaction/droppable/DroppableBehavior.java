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
	 * Gets the javascript statement that will we executed on 'dragenter' event<br/>
	 * The event variable is {@code 'e'}
	 * 
	 * @return the javascript statement
	 */
	protected String getOnDragEnterStatement()
	{
		return "";
	}

	/**
	 * Gets the javascript statement that will we executed on 'dragleave' event<br/>
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
		// statement.append("e.draggable.destroy();"); // prevent dragStop to be fired (dual round-trip)

		return statement.toString();
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.onDropAjaxBehavior = this.newOnDropAjaxBehavior(this, this.getOnDropStatement());
		component.add(this.onDropAjaxBehavior);

		// these events are not enabled by default to prevent unnecessary server round-trips.
		if (this.isDragEnterEventEnabled())
		{
			this.onDragEnterAjaxBehavior = this.newOnDragEnterAjaxBehavior(this, this.getOnDragEnterStatement());
			component.add(this.onDragEnterAjaxBehavior);
		}

		if (this.isDragLeaveEventEnabled())
		{
			this.onDragLeaveAjaxBehavior = this.newOnDragLeaveAjaxBehavior(this, this.getOnDragLeaveStatement());
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
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'dragenter' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param statement the statement to execute just before the ajax call
	 * @return a new {@link OnDragEnterAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDragEnterAjaxBehavior(IJQueryAjaxAware source, String statement)
	{
		return new OnDragEnterAjaxBehavior(source, statement);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'dragleave' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param statement the statement to execute just before the ajax call
	 * @return a new {@link OnDragLeaveAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDragLeaveAjaxBehavior(IJQueryAjaxAware source, String statement)
	{
		return new OnDragLeaveAjaxBehavior(source, statement);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'drop' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @param statement the statement to execute just before the ajax call
	 * @return a new {@link OnDropAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDropAjaxBehavior(IJQueryAjaxAware source, String statement)
	{
		return new OnDropAjaxBehavior(source, statement);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'dragenter' event
	 */
	protected static class OnDragEnterAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		private final String statement;

		public OnDragEnterAjaxBehavior(IJQueryAjaxAware source, String statement)
		{
			super(source);

			this.statement = statement;
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e") };
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			return this.statement + super.getCallbackFunctionBody(parameters);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DragEnterEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'dragleave' event
	 */
	protected static class OnDragLeaveAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		private final String statement;

		public OnDragLeaveAjaxBehavior(IJQueryAjaxAware source, String statement)
		{
			super(source);

			this.statement = statement;
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e") };
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			return this.statement + super.getCallbackFunctionBody(parameters);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DragLeaveEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'drop' event
	 */
	protected static class OnDropAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		private final String statement;

		public OnDropAjaxBehavior(IJQueryAjaxAware source, String statement)
		{
			super(source);

			this.statement = statement;
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e") };
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			return this.statement + super.getCallbackFunctionBody(parameters);
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new DropEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDragEnterAjaxBehavior} callback
	 */
	protected static class DragEnterEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDragLeaveAjaxBehavior} callback
	 */
	protected static class DragLeaveEvent extends JQueryEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDropAjaxBehavior} callback
	 */
	protected static class DropEvent extends JQueryEvent
	{
	}
}
