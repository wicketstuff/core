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
package com.googlecode.wicket.jquery.ui.interaction.selectable;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.core.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.interaction.draggable.Draggable;

/**
 * Provides a jQuery UI selectable {@link JQueryContainer}.<br/>
 * Children of that container can be selected using the mouse or by pressing ctrl+click<br/>
 * Usage:
 *
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
 * 	protected void onSelect(AjaxRequestTarget target)
 * 	{
 * 		//this.getModelObject(): gets the selected items
 * 	}
 * };
 *
 * this.add(selectable);
 * </pre>
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Selectable<T extends Serializable> extends JQueryContainer
{
	private static final long serialVersionUID = 1L;

	/** The list of selectable items */
	private IModel<? extends List<T>> items;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the list the {@link Selectable} should observe.
	 */
	public Selectable(String id, List<T> items)
	{
		this(id, new ListModel<T>(items));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the list the {@link Selectable} should observe.
	 */
	public Selectable(String id, IModel<? extends List<T>> items)
	{
		super(id, new ListModel<T>());

		this.items = wrap(items);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list of selected items
	 * @param items the list the {@link Selectable} should observe.
	 */
	public Selectable(String id, IModel<? extends List<T>> model, List<T> items)
	{
		this(id, model, new ListModel<T>(items));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list of selected items
	 * @param items the list the {@link Selectable} should observe.
	 */
	public Selectable(String id, IModel<? extends List<T>> model, IModel<? extends List<T>> items)
	{
		super(id, model);

		this.items = wrap(items);
	}

	// Properties //

	/**
	 * Gets the model object
	 *
	 * @return the list of selected items
	 */
	@SuppressWarnings("unchecked")
	public IModel<? extends List<T>> getModel()
	{
		return (IModel<? extends List<T>>) this.getDefaultModel();
	}

	/**
	 * Gets the model object
	 *
	 * @return the list of selected items
	 */
	@SuppressWarnings("unchecked")
	public List<T> getModelObject()
	{
		return (List<T>) this.getDefaultModelObject();
	}

	/**
	 * Sets the model object
	 *
	 * @param list the list of selected items
	 */
	public void setModelObject(List<T> list)
	{
		this.setDefaultModelObject(list);
	}

	/**
	 * Gets the reference list of all selectable items.
	 *
	 * @return the list of all selectable items.
	 */
	protected List<T> getItemList()
	{
		return this.items.getObject();
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
	protected void onConfigure()
	{
		super.onConfigure();

		this.add(this.newSelectedBehavior());
	}

	/**
	 * Triggered when a selection has been made (stops)
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void onSelect(AjaxRequestTarget target)
	{
		// noop
	}

	@Override
	protected void onDetach()
	{
		super.onDetach();

		this.items.detach();
	}

	// Factories //

	/**
	 * Gets the JQueryAbstractBehavior in charge of selecting default items (matching model object)
	 *
	 * @return the {@link JQueryAbstractBehavior}
	 */
	protected JQueryAbstractBehavior newSelectedBehavior()
	{
		return new JQueryAbstractBehavior("selected") {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isTemporary(Component component)
			{
				return true;
			}

			private String getSelector()
			{
				return String.format("%s %s", JQueryWidget.getSelector(Selectable.this), Selectable.this.getItemSelector());
			}

			@Override
			public String $()
			{
				StringBuilder statement = new StringBuilder("");
				List<T> list = Selectable.this.getModelObject();

				if (list != null)
				{
					List<T> items = Selectable.this.getItemList();

					for (T item : list)
					{
						int index = items.indexOf(item); // use #equals

						if (index > -1)
						{
							statement.append("jQuery('").append(this.getSelector()).append("').eq(").append(index).append(").addClass('ui-selected');");
						}
					}
				}

				return statement.toString();
			}
		};
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SelectableBehavior<T>(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<T> getItemList()
			{
				return Selectable.this.getItemList();
			}

			@Override
			protected String getItemSelector()
			{
				return Selectable.this.getItemSelector();
			}

			@Override
			public void onSelect(AjaxRequestTarget target, List<T> items)
			{
				Selectable.this.setModelObject(items);
				Selectable.this.onSelect(target);
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
		return factory.create(id, JQueryWidget.getSelector(this)); // let throw a NPE if no factory is defined
	}

	// Default Draggable Factory //

	/**
	 * Default {@link SelectableDraggableFactory} implementation which will create a {@link Draggable} with a {@link JQueryIcon#ARROW_4_DIAG} icon
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
				public void onConfigure(JQueryBehavior behavior)
				{
					super.onConfigure(behavior);

					behavior.setOption("helper", helper);
				}

				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);

					tag.append("class", "ui-icon " + JQueryIcon.ARROW_4_DIAG, " ");
					tag.append("style", "display: inline-block; background-position: -16px -80px !important;", ";");
					// The background position is the same as ui-icon-arrow-4-diag.
					// It is marked as important for the icon to not disappear while selecting over it.
				}
			};

			return draggable;
		}
	}
}
