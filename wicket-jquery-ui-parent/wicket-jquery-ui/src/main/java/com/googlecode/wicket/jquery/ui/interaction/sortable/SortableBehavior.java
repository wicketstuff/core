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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;
import com.googlecode.wicket.jquery.ui.interaction.selectable.SelectableBehavior;

/**
 * Provides a jQuery sortable behavior
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public abstract class SortableBehavior<T> extends JQueryUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "sortable";

	/** event listener */
	private final ISortableListener<T> listener;

	private JQueryAjaxBehavior onUpdateAjaxBehavior;
	private JQueryAjaxBehavior onReceiveAjaxBehavior = null;
	private JQueryAjaxBehavior onRemoveAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link ISortableListener}
	 */
	public SortableBehavior(String selector, ISortableListener<T> listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link ISortableListener}
	 */
	public SortableBehavior(String selector, Options options, ISortableListener<T> listener)
	{
		super(selector, METHOD, options);
		
		this.listener = Args.notNull(listener, "listener");
	}

	// Properties //

	/**
	 * Gets the reference list of all sortable items.<br>
	 * Usually the model object of the component on which this {@link SelectableBehavior} is bound to.
	 *
	 * @return the {@link List}
	 */
	protected abstract List<T> getItemList();

	/**
	 * Gets the list of the connected sortable
	 *
	 * @return null by default
	 */
	protected List<T> getConnectedList()
	{
		return Collections.emptyList();
	}

	@Override
	public boolean isEnabled(Component component)
	{
		return component.isEnabledInHierarchy();
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.onUpdateAjaxBehavior = this.newOnUpdateAjaxBehavior(this);
		component.add(this.onUpdateAjaxBehavior);

		if (this.listener.isOnReceiveEnabled())
		{
			this.onReceiveAjaxBehavior = this.newOnReceiveAjaxBehavior(this);
			component.add(this.onReceiveAjaxBehavior);
		}

		if (this.listener.isOnRemoveEnabled())
		{
			this.onRemoveAjaxBehavior = this.newOnRemoveAjaxBehavior(this);
			component.add(this.onRemoveAjaxBehavior);
		}
	}

	/**
	 * Helper method to locate an item in a list by identifier.<br>
	 * By default, uses item's hashcode as identifier.
	 *
	 * @param id the item id
	 * @param list the list of items
	 * @return the item with that identifier or {@code null} if there is no such
	 * @see Sortable#findItem(String, List)
	 */
	protected T findItem(String id, List<T> list)
	{
		return ListUtils.fromHash(Integer.parseInt(id), list);
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		if (this.isEnabled(component))
		{
			this.setOption("update", this.onUpdateAjaxBehavior.getCallbackFunction());

			if (this.onReceiveAjaxBehavior != null)
			{
				this.setOption("receive", this.onReceiveAjaxBehavior.getCallbackFunction());
			}

			if (this.onRemoveAjaxBehavior != null)
			{
				this.setOption("remove", this.onRemoveAjaxBehavior.getCallbackFunction());
			}
		}

		super.onConfigure(component);
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof SortableEvent)
		{
			SortableEvent ev = (SortableEvent) event;
			String hash = ev.getHash();
			int index = ev.getIndex();

			if (event instanceof UpdateEvent)
			{
				List<T> list = this.getItemList();

				if (list != null)
				{
					this.listener.onUpdate(target, findItem(hash, list), index);
				}
			}

			if (event instanceof ReceiveEvent)
			{
				List<T> list = this.getConnectedList();

				if (!list.isEmpty())
				{
					this.listener.onReceive(target, findItem(hash, list), index);
				}
			}

			if (event instanceof RemoveEvent)
			{
				List<T> list = this.getItemList();

				if (list != null)
				{
					this.listener.onRemove(target, findItem(hash, list));
				}
			}
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'update' event, triggered when the user stopped sorting
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnUpdateAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnUpdateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnUpdateAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'receive' event, triggered when a connected sortable list has received an item from another list.
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnReceiveAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnReceiveAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnReceiveAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'remove' event, triggered when a sortable item has been dragged out from the list and into another.
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnRemoveAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnRemoveAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnRemoveAjaxBehavior(source);
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'update' event
	 */
	protected static class OnUpdateAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnUpdateAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("hash", "ui.item.data('hash')"), // lf
					CallbackParameter.resolved("index", "ui.item.index()") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new UpdateEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'receive' event
	 */
	protected static class OnReceiveAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnReceiveAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("hash", "ui.item.data('hash')"), // lf
					CallbackParameter.resolved("index", "ui.item.index()") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ReceiveEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'remove' event
	 */
	protected static class OnRemoveAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnRemoveAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("hash", "ui.item.data('hash')") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new RemoveEvent();
		}
	}

	// Event objects //

	/**
	 * Provides a base class for {@link SortableBehavior} event objects
	 */
	protected static class SortableEvent extends JQueryEvent
	{
		private final String hash;
		private final int index;

		public SortableEvent()
		{
			this.hash = RequestCycleUtils.getQueryParameterValue("hash").toString();
			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt(-1); // remove-behavior will default to -1
		}

		/**
		 * Gets the hash
		 *
		 * @return the hash
		 */
		public String getHash()
		{
			return this.hash;
		}

		/**
		 * Gets the index
		 *
		 * @return the index
		 */
		public int getIndex()
		{
			return this.index;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnUpdateAjaxBehavior} callback
	 */
	protected static class UpdateEvent extends SortableEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnReceiveAjaxBehavior} callback
	 */
	protected static class ReceiveEvent extends SortableEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnRemoveAjaxBehavior} callback
	 */
	protected static class RemoveEvent extends SortableEvent
	{
	}
}
