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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
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
 * final Selectable&lt;String&gt; selectable = new Selectable&lt;String&gt;("selectable", list) {
 *
 * 	protected void onSelect(AjaxRequestTarget target, List&lt;String&gt; items)
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
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Selectable<T extends Serializable> extends JQueryContainer
{
	private static final long serialVersionUID = 1L;

	private JQueryAjaxBehavior onStopBehavior;
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
	}

	// Getters //
	@SuppressWarnings("unchecked")
	public List<T> getModelObject()
	{
		return (List<T>) this.getDefaultModelObject();
	}

	/**
	 * Gets the selected items
	 * @return selected items
	 */
	public List<T> getSelectedItems()
	{
		return this.items;
	}

	/**
	 * Gets the selector that identifies the selectable item within a {@link Selectable}<br/>
	 * The selector should be the path from the {@link Selectable} to the item (for instance '#myUL LI', where '#myUL' is the {@link Selectable}'s selector)
	 *
	 * @return "li" by default
	 */
	protected String getItemSelector()
	{
		return "li";
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.onStopBehavior = this.newOnStopBehavior());
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("filter", Options.asString(this.getItemSelector()));
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
				// defensive, if the item-selector is miss-configured, this can result in an OutOfBoundException
				if (index < list.size())
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

				this.setOption("stop", Selectable.this.onStopBehavior.getCallbackFunction());
			}
		};
	}


	// Behavior factory //
	/**
	 * Gets the ajax behavior that will be triggered when the user has selected items
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnStopBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl() + "&indexes=' + indexes");
			}

			@Override
			protected IAjaxCallDecorator getAjaxCallDecorator()
			{
				return new AjaxCallDecorator() {

					private static final long serialVersionUID = 1L;

					@Override
					public CharSequence decorateScript(Component c, CharSequence script)
					{
						String selector = String.format("%s %s", JQueryWidget.getSelector(Selectable.this), Selectable.this.getItemSelector());
						String indexes = "var indexes=[]; jQuery('.ui-selected', this).each( function() { indexes.push(jQuery('" + selector + "').index(this)); } ); ";

						return indexes + script;
					}
				};
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new StopEvent(target);
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
	public Draggable<?> createDraggable(String id)
	{
		return this.createDraggable(id, new DefaultDraggableFactory());
	}

	/**
	 * Creates a {@link Draggable} object that is related to this {@link Selectable}
	 *
	 * @param id the markup id
	 * @param factory the {@link SelectableDraggableFactory} instance
	 * @return the {@link Draggable}
	 */
	public Draggable<?> createDraggable(String id, SelectableDraggableFactory factory)
	{
		return factory.create(id, JQueryWidget.getSelector(this)); //let throw a NPE if no factory is defined
	}


	// Event class //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'stop' callback
	 */
	protected class StopEvent extends JQueryEvent
	{
		private final List<Integer> indexes;

		public StopEvent(AjaxRequestTarget target)
		{
			super(target);

			this.indexes = new ArrayList<Integer>();
			StringValue values = RequestCycleUtils.getQueryParameterValue("indexes");

			if (values != null)
			{
				Pattern pattern = Pattern.compile("(\\d+)");
				Matcher matcher = pattern.matcher(values.toString());

				while (matcher.find())
				{
					this.indexes.add(Integer.valueOf(matcher.group()));
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
	 */
	class DefaultDraggableFactory extends SelectableDraggableFactory
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected Draggable<?> create(String id, String selector, final String helper)
		{
			Draggable<T> draggable = new Draggable<T>(id) {

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