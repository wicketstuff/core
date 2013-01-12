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
package com.googlecode.wicket.jquery.ui.event;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponent;

import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxPostBehavior;

/**
 * Provides a new {@link JQueryAjaxPostBehavior} that will (should) be called on 'change' jQuery method
 */
public class JQueryAjaxChangeBehavior extends JQueryAjaxPostBehavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param component the the {@link FormComponent}
	 */
	public JQueryAjaxChangeBehavior(FormComponent<?> component)
	{
		super(component);
	}

	/**
	 * Constructor
	 * @param source the component that will broadcast the event.
	 * @param components the form components to post
	 */
	public JQueryAjaxChangeBehavior(Component source, FormComponent<?>... components)
	{
		super(source, components);
	}

	@Override
	public String getCallbackFunction()
	{
		return "function(event, ui) { " + this.getCallbackScript() + " }";
	}


	// Factories //
	@Override
	protected JQueryEvent newEvent(AjaxRequestTarget target)
	{
		return new ChangeEvent(target);
	}

	// Event Object //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxChangeBehavior}
	 */
	public static class ChangeEvent extends JQueryEvent
	{
		public ChangeEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}
}

