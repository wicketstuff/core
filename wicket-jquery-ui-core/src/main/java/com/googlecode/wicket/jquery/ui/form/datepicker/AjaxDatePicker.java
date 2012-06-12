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
package com.googlecode.wicket.jquery.ui.form.datepicker;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxPostBehavior;

/**
 * Provides a jQuery date-picker based on a {@link DateTextField}<br/>
 * This ajax version will post the {@link Component}, using a {@link JQueryAjaxPostBehavior}, when the 'onSelect' javascript method is called.
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class AjaxDatePicker extends DatePicker
{
	private static final long serialVersionUID = 1L;
	
	private JQueryAjaxBehavior selectBehavior;
	
	/**
	 * Constructor
	 * @param id the markup id
	 */
	public AjaxDatePicker(String id)
	{
		super(id);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AjaxDatePicker(String id, IModel<Date> model)
	{
		super(id, model);
		this.init();
	}
	
	/**
	 * Initialization
	 */
	private void init()
	{
		this.selectBehavior = this.newSelectBehavior(this);
	}

	
	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();  

		this.add(this.selectBehavior);
	}
	
	@Override
	protected void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);
		
		behavior.setOption("onSelect", "function( dateText, inst ) { " + this.selectBehavior.getCallbackScript() + "}");
	}
	
	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof SelectEvent)
		{
			SelectEvent payload = (SelectEvent) event.getPayload();

			this.processInput();
			this.onValueChanged(payload.getTarget());
		}
	}
	
	/**
	 * Triggers when the value has changed
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onValueChanged(AjaxRequestTarget target)
	{
	}

	
	// Factories (Ajax behavior) //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'onSelect' javascript method
	 * @param component the {@link FormComponent}
	 * @return the {@link JQueryAjaxPostBehavior}
	 */
	private JQueryAjaxPostBehavior newSelectBehavior(FormComponent<?> component)
	{
		return new JQueryAjaxPostBehavior(component) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new SelectEvent(target);
			}
		};
	}
	
	
	// Event class //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxPostBehavior} 'select' callback
	 */
	public class SelectEvent extends JQueryEvent
	{
		public SelectEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}	
}
