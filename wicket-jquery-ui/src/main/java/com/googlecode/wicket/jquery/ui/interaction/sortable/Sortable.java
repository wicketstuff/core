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
package com.googlecode.wicket.jquery.ui.interaction.sortable;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryGenericContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides a jQuery UI sortable {@link JQueryGenericContainer}.<br/>
 * The {@code Sortable} is usually associated to an &lt;UL&gt; element.
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class Sortable<T> extends JQueryGenericContainer<List<T>> implements ISortableListener<T>
{
	private static final long serialVersionUID = 1L;

	private final Options options;

	/**
	 * The {@link Sortable} that requested to be connected to this {@link Sortable}<br/>
	 * In other words, the {@link Sortable} that called {@link #connectWith(Sortable)}
	 */
	private Sortable<T> connectedSortable = null;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param list the list the {@link Sortable} should observe.
	 */
	public Sortable(String id, List<T> list)
	{
		this(id, new ListModel<T>(list), new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param list the list the {@link Sortable} should observe.
	 * @param options the {@link Options}
	 */
	public Sortable(String id, List<T> list, Options options)
	{
		this(id, new ListModel<T>(list), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list the {@link Sortable} should observe.
	 */
	public Sortable(String id, IModel<List<T>> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list the {@link Sortable} should observe.
	 * @param options the {@link Options}
	 */
	public Sortable(String id, IModel<List<T>> model, Options options)
	{
		super(id, model);

		this.options = Args.notNull(options, "options");
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.newListView(this.getModel()));
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getSource() instanceof Sortable<?>)
		{
			AjaxRequestTarget target = RequestCycleUtils.getAjaxRequestTarget();

			if (target != null)
			{
				@SuppressWarnings("unchecked")
				T item = (T) event.getPayload();

				this.onRemove(target, item);
			}
		}
	}

	@Override
	public void onUpdate(AjaxRequestTarget target, T item, int index)
	{
		this.modelChanging();
		ListUtils.move(item, index, this.getModelObject()); // why is it called by sender if moving to receiver?
		this.modelChanged();
	}

	@Override
	public void onReceive(AjaxRequestTarget target, T item, int index)
	{
		this.modelChanging();
		this.getModelObject().add(index, item);
		this.modelChanged();

		// broadcast to the connected sortable for removing the item
		this.send(this.connectedSortable, Broadcast.EXACT, item);
	}

	@Override
	public void onRemove(AjaxRequestTarget target, T item)
	{
		this.modelChanging();
		this.getModelObject().remove(item);
		this.modelChanged();
	}

	// Properties //

	@Override
	public boolean isOnReceiveEnabled()
	{
		return this.connectedSortable != null;
	}

	@Override
	public boolean isOnRemoveEnabled()
	{
		return false; // 'remove' will be handled after 'receive' by the event bus because there is a risk the item is removed before being received (leading to a NPE)
	}

	// Methods //

	/**
	 * Connects with another {@link Sortable}<br/>
	 * The specified {@link Sortable} will keep a reference to the caller ({@code this}).
	 *
	 * @param sortable the {@link Sortable} to connect with
	 * @return this, for chaining
	 */
	public Sortable<T> connectWith(Sortable<T> sortable)
	{
		Args.notNull(sortable, "sortable");

		sortable.connect(this); // eq. to sortable.connectedSortable = this;

		return this.connectWith(JQueryWidget.getSelector(sortable));
	}

	/**
	 * Sets the '{@code connectWith}' options
	 *
	 * @param selector the html selector
	 * @return this, for chaining
	 */
	private Sortable<T> connectWith(String selector)
	{
		this.options.set("connectWith", Options.asString(selector));

		return this;
	}

	/**
	 * Sets the connected {@link Sortable} reference.<br/>
	 * Supplying a non-null {@link Sortable} will activate {@link #isOnReceiveEnabled()}
	 *
	 * @param sortable the {@link Sortable}
	 * @see #isOnReceiveEnabled()
	 */
	private void connect(Sortable<T> sortable)
	{
		this.connectedSortable = sortable;
	}

	/**
	 * Helper method to locate an item in a list by identifier.<br />
	 * By default, uses item's hashcode as identifier.
	 *
	 * @param id the item id
	 * @param list the list of items
	 * @return the item with that identifier or {@code null} if there is no such
	 * @see SortableBehavior#findItem(String, List)
	 */
	protected T findItem(String id, List<T> list)
	{
		return ListUtils.fromHash(Integer.parseInt(id), list);
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SortableBehavior<T>(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isOnReceiveEnabled()
			{
				return Sortable.this.isOnReceiveEnabled();
			}

			@Override
			public boolean isOnRemoveEnabled()
			{
				return Sortable.this.isOnRemoveEnabled();
			}

			@Override
			protected List<T> getItemList()
			{
				return Sortable.this.getModelObject();
			}

			@Override
			protected List<T> getConnectedList()
			{
				if (Sortable.this.connectedSortable != null)
				{
					return Sortable.this.connectedSortable.getModelObject();
				}

				return Collections.emptyList();
			}

			@Override
			protected T findItem(String id, List<T> list)
			{
				return Sortable.this.findItem(id, list);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, T item, int position)
			{
				Sortable.this.onUpdate(target, item, position);
			}

			@Override
			public void onReceive(AjaxRequestTarget target, T item, int index)
			{
				Sortable.this.onReceive(target, item, index);
			}

			@Override
			public void onRemove(AjaxRequestTarget target, T item)
			{
				Sortable.this.onRemove(target, item);
			}
		};
	}

	/**
	 * Gets a new {@link HashListView}
	 *
	 * @param model the {@link IModel} that <i>should</i> be used
	 * @return the {@link HashListView}
	 */
	protected abstract HashListView<T> newListView(IModel<List<T>> model);

	/**
	 * Provides the {@link ListView} to be used within the {@link Sortable}
	 *
	 * @param <T> the type of the model object
	 */
	public abstract static class HashListView<T> extends ListView<T>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor
		 *
		 * @param id the markup id
		 */
		public HashListView(String id)
		{
			super(id);
		}

		/**
		 * Constructor
		 *
		 * @param id the markup id
		 * @param list the {@link List}
		 */
		public HashListView(String id, List<? extends T> list)
		{
			super(id, list);
		}

		/**
		 * Constructor
		 *
		 * @param id the markup id
		 * @param model the {@link IModel}
		 */
		public HashListView(String id, IModel<? extends List<? extends T>> model)
		{
			super(id, model);
		}

		@Override
		protected void onBeginPopulateItem(ListItem<T> item)
		{
			super.onBeginPopulateItem(item);

			item.add(AttributeModifier.replace("data-hash", item.getModelObject().hashCode()));
		}
	}
}
