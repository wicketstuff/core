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

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.jquery.ui.interaction.selectable.SelectableBehavior;

/**
 * Provides a jQuery sortable behavior
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public abstract class SortableBehavior<T> extends JQueryBehavior implements IJQueryAjaxAware, ISortableListener<T>
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "sortable";

	private JQueryAjaxBehavior onStopBehavior;

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public SortableBehavior(String selector)
	{
		super(selector, METHOD, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public SortableBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	/**
	 * Gets the reference list of all sortable items.<br/>
	 * Usually the model object of the component on which this {@link SelectableBehavior} is bound to.
	 *
	 * @return the {@link List}
	 */
	protected abstract List<T> getItemList();


	// Methods //
	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.add(this.onStopBehavior = this.newOnStopBehavior());
	}


	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("stop", this.onStopBehavior.getCallbackFunction());
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof StopEvent)
		{
			T item = null;

			StopEvent ev = (StopEvent)event;
			int hash = ev.getHash();
			int index = ev.getIndex();

			for (T t : this.getItemList())
			{
				if (hash == t.hashCode())
				{
					item = t;
				}
			}

			this.onSort(target, item, index);
		}
	}


	// Factories //
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
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] {
						CallbackParameter.context("event"),
						CallbackParameter.context("ui"),
						CallbackParameter.resolved("hash", "ui.item.data('hash')"),
						CallbackParameter.resolved("index", "ui.item.index()") };
			}

			@Override
			public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
			{
				return super.getCallbackFunctionBody(parameters);
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new StopEvent();
			}
		};
	}


	// Event Objects //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'stop' callback
	 */
	protected static class StopEvent extends JQueryEvent
	{
		private final int hash;
		private final int index;

		public StopEvent()
		{
			this.hash = RequestCycleUtils.getQueryParameterValue("hash").toInt(0);
			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt(0);
		}

		public int getHash()
		{
			return this.hash;
		}

		public int getIndex()
		{
			return this.index;
		}
	}
}