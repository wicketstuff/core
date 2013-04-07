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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.string.StringValue;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;

/**
 * Provides a jQuery selectable behavior
 *
 * @author Sebastien Briquet - sebfz1
 * @param <T>
 */
public abstract class SelectableBehavior<T extends Serializable> extends JQueryBehavior implements IJQueryAjaxAware, ISelectableListener<T>
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "selectable";

	private JQueryAjaxBehavior onStopBehavior;

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public SelectableBehavior(String selector)
	{
		super(selector, METHOD, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public SelectableBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}


	// Properties //
	/**
	 * Gets the reference list of all selectable items.<br/>
	 * Usually the model object of the component on which this {@link SelectableBehavior} is bound to.
	 *
	 * @return the {@link List}
	 */
	protected abstract List<T> getItemList();

	/**
	 * Gets the selector that identifies the selectable item within a selectable item list<br/>
	 * The selector should be the path from the selectable component to the item (for instance '#myUL LI', where '#myUL' is the selectable's selector)
	 *
	 * @return "li" by default
	 */
	protected abstract String getItemSelector();

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
		this.setOption("filter", Options.asString(this.getItemSelector()));
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof StopEvent)
		{
			List<T> items = new ArrayList<T>();
			List<T> list = this.getItemList();

			for (int index : ((StopEvent)event).getIndexes())
			{
				// defensive, if the item-selector is miss-configured, this can result in an OutOfBoundException
				if (index < list.size())
				{
					items.add(list.get(index));
				}
			}

			this.onSelect(target, items);
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
				return new CallbackParameter[] { CallbackParameter.resolved("indexes", "indexes") };
			}

			@Override
			public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
			{
				//build indexes array, ie: 'indexes=[1,2,3]'
				String selector = String.format("%s %s", SelectableBehavior.this.selector, SelectableBehavior.this.getItemSelector());
				String indexes = "var indexes=[]; jQuery('.ui-selected', this).each( function() { indexes.push(jQuery('" + selector + "').index(this)); } ); ";

				return indexes + super.getCallbackFunctionBody(parameters);
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
		private final List<Integer> indexes;

		public StopEvent()
		{
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
}