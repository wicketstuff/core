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
 * Children of that container could be selected using the mouse or ctrl+click<br/>
 * 
 * TODO: Selectable: sample of usage (with list ul/li) and other (like div)
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
				return generateCallbackScript("$( '.ui-selected', this ).each( function() { indexes+='&index='+$('" + JQueryWidget.getSelector(Selectable.this) + " li').index(this); } ); wicketAjaxGet('" + getCallbackUrl() + "'+indexes");
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
	 * TODO: javadoc
	 * @param target
	 * @param list
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
	
	// Event class //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'stop' callback
	 */
	public class StopEvent extends JQueryEvent
	{
//		private final List<String> items;
		private final List<Integer> indexes;

		public StopEvent(AjaxRequestTarget target)
		{
			super(target);
			
//			this.items = new ArrayList<String>();
//			
//			for(StringValue value : RequestCycleUtils.getQueryParameterValues("item"))
//			{
//				this.items.add(value.toString());
//			}

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

//		public List<String> getItems()
//		{
//			return this.items;
//		}

		public List<Integer> getIndexes()
		{
			return this.indexes;
		}
	}
}