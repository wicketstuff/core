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
package com.googlecode.wicket.jquery.core.event;

import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxPostBehavior;

/**
 * Provides a new {@link JQueryAjaxPostBehavior} that is designed to be called on 'change' jQuery event<br/>
 * It will broadcast a {@link ChangeEvent} (by default)
 */
public class JQueryAjaxChangeBehavior extends JQueryAjaxPostBehavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param source the {@link Behavior} that will broadcast the event.
	 * @param components the form components to post
	 */
	public JQueryAjaxChangeBehavior(IJQueryAjaxAware source, FormComponent<?>... components)
	{
		super(source, components);
	}

	@Override
	protected CallbackParameter[] getCallbackParameters()
	{
		return new CallbackParameter[] { CallbackParameter.context("event"), CallbackParameter.context("ui") };
	}


	// Factories //
	@Override
	protected JQueryEvent newEvent()
	{
		return new ChangeEvent();
	}


	// Event Object //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxChangeBehavior}
	 */
	public static class ChangeEvent extends JQueryEvent
	{
	}
}

