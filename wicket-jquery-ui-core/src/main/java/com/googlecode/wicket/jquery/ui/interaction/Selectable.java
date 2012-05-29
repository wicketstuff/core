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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.string.StringValue;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryContainer;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * Provides a jQuery UI selectable {@link JQueryContainer}.<br/>
 * Children of that container can be selected using the mouse or by pressing ctrl+click<br/>
 * Usage:
 * <pre>
 * &lt;ul wicket:id="selectable"&gt;
 * 	&lt;li wicket:id="items"&gt;
 * 		&lt;span wicket:id="item"&gt;[label]&lt;/span&gt;
 * 	&lt;/li&gt;
 * &lt;/ul&gt;
 * 
 * 
 * final Selectable<String> selectable = new Selectable<String>("selectable", list) {
 * 	
 * 	protected void onSelect(AjaxRequestTarget target, List<String> items)
 * 	{
 * 		//items: gets the selected item
 * 	}
 * };
 * 
 * this.add(selectable);
 * 
 * // ... //
 * 
 * //selectable.getSelectedItems(): gets the selected items
 * </pre>
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 *
 */
public class Selectable<T extends Serializable> extends JQueryContainer
{
	private static final long serialVersionUID = 1L;
	
	private JQueryAjaxBehavior stopBehavior;
	private List<T> items; //selected items

	/**
	 * Constructor
	 * @param id the markup id
	 * @param list the list the {@link Selectable} should observe.
	 */
	public Selectable(String id, List<T> list)
	{
		this(id, new ListModel<T>(list));
	}
	
	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the list the {@link Selectable} should observe.
	 */
	public Selectable(String id, IModel<List<T>> model)
	{
		super(id, model);
	
		this.init();
	}
	
	private void init()
	{
		this.stopBehavior = new JQueryAjaxBehavior(this) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public CharSequence getCallbackScript()
			{
				//by ids:
//				return generateCallbackScript("$( '.ui-selected', this ).each( function() { items+='&item='+Wicket.$(this).id; } ); wicketAjaxGet('" + getCallbackUrl() + "'+items");

				//by indexes
				String selector = JQueryWidget.getSelector(Selectable.this) + " li";
				return generateCallbackScript("$('.ui-selected', this).each( function() { indexes += '&index=' + $('" + selector + "').index(this); } ); wicketAjaxGet('" + getCallbackUrl() + "' + indexes");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new StopEvent(target);
			}
		};
	}

	// Getter //
	@SuppressWarnings("unchecked")
	public List<T> getModelObject()
	{
		return (List<T>) this.getDefaultModelObject();
	}

	/**
	 * Get the selected items
	 * @return selected items
	 */
	public List<T> getSelectedItems()
	{
		return this.items;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();  

		this.add(this.stopBehavior);
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
		//sets options here
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof Selectable.StopEvent)
		{
			StopEvent payload = (StopEvent) event.getPayload();
			
			this.items = new ArrayList<T>();
			List<T> list = this.getModelObject();

			for (int index : payload.getIndexes())
			{
				if (index >= 0 && index <= list.size())
				{
					this.items.add(list.get(index));
				}
			}

			this.onSelect(payload.getTarget(), this.items);
		}
	}

	/**
	 * Triggered when a selection has been made (stops)
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param list the {@link List} of selected items
	 */
	protected void onSelect(AjaxRequestTarget target, List<T> list)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SelectableBehavior(selector) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				Selectable.this.onConfigure(this);
				
				this.options.set("stop", "function() { var indexes=''; " + stopBehavior.getCallbackScript() + " }");
			}
		};
	}
	
	// DraggableFactory methods //
	/**
	 * Creates a {@link Draggable} object that is related to this {@link Selectable}.<br/>
	 * Uses a default factory that will create a {@link Draggable} with a <code>ui-icon-arrow-4-diag</code> icon 
	 * 
	 * @param id the markup id
	 * @return the {@link Draggable}
	 */
	public Draggable<String> createDraggable(String id)
	{
		return this.createDraggable(id, new DefaultDraggableFactory());
	}

	/**
	 * Creates a {@link Draggable} object that is related to this {@link Selectable}
	 * 
	 * @param id the markup id
	 * @param factory usually a {@link SelectableDraggableFactory} instance
	 * @return the {@link Draggable}
	 */
	public Draggable<String> createDraggable(String id, AbstractDraggableFactory<T> factory)
	{
		return factory.create(id, JQueryWidget.getSelector(this)); //let throw a NPE if no factory is defined
	}

	// Event class //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'stop' callback
	 */
	public class StopEvent extends JQueryEvent
	{
		private final List<Integer> indexes;

		public StopEvent(AjaxRequestTarget target)
		{
			super(target);
			
			this.indexes = new ArrayList<Integer>();
			
			List<StringValue> values = RequestCycleUtils.getQueryParameterValues("index");
			
			if (values != null)
			{
				for(StringValue value : values)
				{
					this.indexes.add(value.toInt());
				}
			}
		}

		public List<Integer> getIndexes()
		{
			return this.indexes;
		}
	}

	// Default Draggable Factory //
	/**
	 * Default {@link SelectableDraggableFactory} implementation which will create a {@link Draggable} with a <code>ui-icon-arrow-4-diag</code> icon 
	 * 
	 * @author Sebastien Briquet - sebastien@7thweb.net
	 */
	class DefaultDraggableFactory extends SelectableDraggableFactory<T>
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected Draggable<String> create(String id, String selector, final String helper)
		{
			Draggable<String> draggable = new Draggable<String>(id) {

				private static final long serialVersionUID = 1L;
				
				@Override
				protected void onConfigure(JQueryBehavior behavior)
				{
					super.onConfigure(behavior);

					behavior.setOption("helper", helper);
				}
			};

			draggable.add(AttributeModifier.append("class", "ui-icon ui-icon-arrow-4-diag"));
			draggable.add(AttributeModifier.append("style", "display: inline-block; background-position: -16px -80px !important;")); // The background position is the same as ui-icon-arrow-4-diag. It is marked as important for the icon to not disappear while selecting over it.  

			return draggable;
		}
	}
}