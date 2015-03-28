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
public abstract class SortableBehavior<T> extends JQueryUIBehavior implements IJQueryAjaxAware, ISortableListener<T>
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "sortable";

	private JQueryAjaxBehavior onUpdateBehavior;
	private JQueryAjaxBehavior onReceiveBehavior = null;
	private JQueryAjaxBehavior onRemoveBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public SortableBehavior(String selector)
	{
		super(selector, METHOD, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public SortableBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Properties //

	/**
	 * Gets the reference list of all sortable items.<br/>
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

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.add(this.onUpdateBehavior = this.newOnUpdateBehavior());

		if (this.isOnReceiveEnabled())
		{
			component.add(this.onReceiveBehavior = this.newOnReceiveBehavior());
		}

		if (this.isOnRemoveEnabled())
		{
			component.add(this.onRemoveBehavior = this.newOnRemoveBehavior());
		}
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("update", this.onUpdateBehavior.getCallbackFunction());

		if (this.onReceiveBehavior != null)
		{
			this.setOption("receive", this.onReceiveBehavior.getCallbackFunction());
		}

		if (this.onRemoveBehavior != null)
		{
			this.setOption("remove", this.onRemoveBehavior.getCallbackFunction());
		}
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof SortableEvent)
		{
			SortableEvent ev = (SortableEvent) event;
			int hash = ev.getHash();
			int index = ev.getIndex();

			if (event instanceof UpdateEvent)
			{
				List<T> list = this.getItemList();

				if (list != null)
				{
					this.onUpdate(target, ListUtils.fromHash(hash, list), index);
				}
			}

			if (event instanceof ReceiveEvent)
			{
				List<T> list = this.getConnectedList();

				if (!list.isEmpty())
				{
					this.onReceive(target, ListUtils.fromHash(hash, list), index);
				}
			}

			if (event instanceof RemoveEvent)
			{
				List<T> list = this.getItemList();

				if (list != null)
				{
					this.onRemove(target, ListUtils.fromHash(hash, list));
				}
			}
		}
	}

	// Factories //

	/**
	 * Gets the ajax behavior that will be triggered when the user has selected items
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnUpdateBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("event"), CallbackParameter.context("ui"), CallbackParameter.resolved("hash", "ui.item.data('hash')"), CallbackParameter.resolved("index", "ui.item.index()") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new UpdateEvent();
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when a connected sortable list has received an item from another list.
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnReceiveBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("event"), CallbackParameter.context("ui"), CallbackParameter.resolved("hash", "ui.item.data('hash')"), CallbackParameter.resolved("index", "ui.item.index()") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new ReceiveEvent();
			}
		};
	}

	/**
	 * Gets the ajax behavior that will be triggered when a sortable item has been dragged out from the list and into another.
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnRemoveBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("event"), CallbackParameter.context("ui"), CallbackParameter.resolved("hash", "ui.item.data('hash')") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new RemoveEvent();
			}
		};
	}

	// Event Objects //

	/**
	 * A base event object for sortable
	 */
	protected static class SortableEvent extends JQueryEvent
	{
		private final int hash;
		private final int index;

		public SortableEvent()
		{
			this.hash = RequestCycleUtils.getQueryParameterValue("hash").toInt(0);
			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt(-1); // remove-behavior will default to -1
		}

		/**
		 * Gets the hash
		 *
		 * @return the hash
		 */
		public int getHash()
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
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'update' callback
	 */
	protected static class UpdateEvent extends SortableEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'receive' callback
	 */
	protected static class ReceiveEvent extends SortableEvent
	{
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'remove' callback
	 */
	protected static class RemoveEvent extends SortableEvent
	{
	}
}
