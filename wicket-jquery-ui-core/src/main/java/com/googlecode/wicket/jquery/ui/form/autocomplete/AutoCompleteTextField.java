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
package com.googlecode.wicket.jquery.ui.form.autocomplete;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * Provides a jQuery auto-complete widget
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 *
 * @param <T> the type of the model object
 */
public abstract class AutoCompleteTextField<T extends Serializable> extends TextField<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;	
	private static final String METHOD = "autocomplete";
	
	/**
	 * Behavior that will called when the user selects a value from results
	 */
	private JQueryAjaxBehavior selectBehavior;
	
	/**
	 * Behavior that will be called when the user enters an input
	 */
	private AutoCompleteBehavior<T> sourceBehavior;
	
	/**
	 * Cache of current choices, needed to retrieve the user selected object
	 */
	private List<T> choices;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public AutoCompleteTextField(String id)
	{
		super(id);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AutoCompleteTextField(String id, IModel<T> model)
	{
		super(id, model);
		this.init();
	}
	
	/**
	 * Initialization
	 */
	private void init()
	{
		this.sourceBehavior = this.newAutoCompleteBehavior();
		this.selectBehavior = this.newSelectBehavior(this);
	}

	
	// Methods //
	/**
	 * Gets choices matching the provided input
	 * @param input String that represent the query
	 * @return the list of choices
	 */
	protected abstract List<T> getChoices(String input);
	
	/**
	 * Call {@link #getChoices(String)} and cache the result<br/>
	 * Internal use only
	 * @param input String that represent the query
	 * @return the list of choices
	 */
	private List<T> internalGetChoices(String input)
	{
		this.choices = this.getChoices(input);

		return this.choices;
	}
	
	// before enabling this method, make sure 'source' option cannot be used.
//	public FormComponent<T> setOption(String key, Serializable value)
//	{
//		if (this.widgetBehavior != null)
//		{
//			this.widgetBehavior.setOption(key, value);
//		}
//		
//		return this;
//	}
	
	
	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		
		this.add(this.sourceBehavior);
		this.add(this.selectBehavior);
		this.add(JQueryWidget.newWidgetBehavior(this)); //cannot be in ctor as the markupId may be set manually afterward
	}

	
	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.put("autocomplete", "off"); // disable browser's autocomplete
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof AutoCompleteTextField.SelectEvent)
		{
			SelectEvent payload = (SelectEvent) event.getPayload(); 
		
			int index = payload.getIndex();
			if (index < this.choices.size())
			{
				T choice = AutoCompleteTextField.this.choices.get(index);
				
				this.setModelObject(choice);
				this.onSelected(payload.getTarget());
			}
		}
	}
	
	/**
	 * Triggered when the user selects an item from results that matched its input
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onSelected(AjaxRequestTarget target)
	{
		
	}

	
	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryBehavior(selector, METHOD) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component) 
			{
				this.setOption("source", Options.asString(sourceBehavior.getCallbackUrl()));
				this.setOption("select", "function( event, ui ) { " + selectBehavior.getCallbackScript() + " }");
//				minLength: 2,
			}
		};
	}
	
	
	// Factories //
	/**
	 * Gets a new {@link AutoCompleteBehavior}
	 * @return
	 */
	private AutoCompleteBehavior<T> newAutoCompleteBehavior()
	{
		return new AutoCompleteBehavior<T>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected List<T> getChoices(String input)
			{
				return AutoCompleteTextField.this.internalGetChoices(input);
			}
		};
	}
	
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'select' javascript method
	 * @param source {@link Component} to which the event returned by {@link #newEvent(AjaxRequestTarget)} will be broadcasted.
	 * @return the {@link JQueryAjaxBehavior}
	 * 
	 */
	private JQueryAjaxBehavior newSelectBehavior(Component source)
	{
		return new JQueryAjaxBehavior(source) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public CharSequence getCallbackScript()
			{
				return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl() + "&index=' + ui.item.id");
			}

			
			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new SelectEvent(target);
			}
		};
	}
	

	// Event classes //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} select callback
	 *
	 */
	class SelectEvent extends JQueryEvent
	{
		private final int index;

		public SelectEvent(AjaxRequestTarget target)
		{
			super(target);
			
			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt(1) - 1;
		}

		public int getIndex()
		{
			return this.index;
		}
	}
}
