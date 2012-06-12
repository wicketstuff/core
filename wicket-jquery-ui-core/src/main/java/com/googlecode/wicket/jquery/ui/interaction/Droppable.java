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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryContainer;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;

/**
 * Provides a jQuery droppable area, on which {@link Draggable}<code>s</code> could be dropped.
 * 
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public abstract class Droppable<T> extends JQueryContainer
{
	private static final long serialVersionUID = 1L;

	private JQueryAjaxBehavior dropBehavior;
	private JQueryAjaxBehavior overBehavior;
	private JQueryAjaxBehavior exitBehavior;
	private transient Draggable<?> draggable = null;  /* object being dragged */

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public Droppable(String id)
	{
		super(id);
		
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Droppable(String id, IModel<T> model)
	{
		super(id, model);
		
		this.init();
	}
	
	private void init()
	{
		this.dropBehavior = new JQueryAjaxBehavior(this) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new DropEvent(target);
			}
		};
		
		this.overBehavior = new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new OverEvent(target);
			}
		};

		this.exitBehavior = new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ExitEvent(target);
			}
		};
	}
	
	// Getters / Setters //
	/**
	 * Indicates whether the 'over' event is enabled.<br />
	 * If false, the {@link #onOver(AjaxRequestTarget, Draggable)} event will not be triggered.
	 * @return false by default
	 */
	protected boolean isOverEventEnabled()
	{
		return false;
	}
	
	/**
	 * Indicates whether the 'exit' (or 'out') event is enabled.<br />
	 * If false, the {@link #onExit(AjaxRequestTarget, Draggable)} event will not be triggered.
	 * @return false by default
	 */
	protected boolean isExitEventEnabled()
	{
		return false;
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.dropBehavior);
		this.add(this.overBehavior);
		this.add(this.exitBehavior);
	}

	/**
	 * Triggered by JQueryAjaxBehavior#respond
	 */
	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof JQueryEvent)
		{
			JQueryEvent payload = (JQueryEvent) event.getPayload();
			
			// registers the draggable object that starts
			if (payload instanceof Draggable.DragStartEvent)
			{
				this.draggable = (Draggable<?>) event.getSource();
			}

			else if (payload instanceof Droppable.DropEvent)
			{
				this.onDrop(payload.getTarget(), this.draggable);
			}

			else if (payload instanceof Droppable.OverEvent)
			{
				this.onOver(payload.getTarget(), this.draggable);
			}

			else if (payload instanceof Droppable.ExitEvent)
			{
				this.onExit(payload.getTarget(), this.draggable);
			}
		}
	}

	/**
	 * Triggered when a {@link Draggable} has been dropped
	 * @param target the {@link AjaxRequestTarget}
	 * @param draggable the {@link Draggable} object
	 */	
	protected abstract void onDrop(AjaxRequestTarget target, Draggable<?> draggable);

	/**
	 * Triggered when a {@link Draggable} overs the droppable area
	 * @param target the {@link AjaxRequestTarget}
	 * @param draggable the {@link Draggable} object
	 * @see #isOverEventEnabled()
	 */	
	protected void onOver(AjaxRequestTarget target, Draggable<?> draggable)
	{
	}
	
	/**
	 * Triggered when a {@link Draggable} exits the droppable area
	 * @param target the {@link AjaxRequestTarget}
	 * @param draggable the {@link Draggable} object
	 * @see #isExitEventEnabled()
	 */	
	protected void onExit(AjaxRequestTarget target, Draggable<?> draggable)
	{
	}


	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryBehavior(selector, "droppable") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				this.setOption("drop", "function( event, ui ) { " + dropBehavior.getCallbackScript() + " }");
				
				if (Droppable.this.isOverEventEnabled())
				{
					this.setOption("over", "function( event, ui ) { " + overBehavior.getCallbackScript() + " }");
				}
				
				if (Droppable.this.isExitEventEnabled())
				{
					this.setOption("out", "function( event, ui ) { " + exitBehavior.getCallbackScript() + " }");
				}
			}
		};
	}
	
	
	// Event classes //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'drop' callback
	 */
	public class DropEvent extends JQueryEvent
	{
		public DropEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}
	
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'over' callback
	 */
	public class OverEvent extends JQueryEvent
	{
		public OverEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}
	
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'exit' callback
	 */
	public class ExitEvent extends JQueryEvent
	{
		public ExitEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}
}
