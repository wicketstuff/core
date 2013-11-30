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
package com.googlecode.wicket.jquery.ui.calendar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a simple draggable event object based on {@link Label}.
 *
 * @author Sebastien Briquet - sebfz1
 */
public class EventObject extends Label implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	private final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the event title
	 */
	public EventObject(String id, String title)
	{
		this(id, Model.of(title), new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the event title
	 * @param options the draggable options
	 */
	public EventObject(String id, String title, Options options)
	{
		this(id, Model.of(title), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the event title
	 */
	public EventObject(String id, IModel<String> title)
	{
		this(id, title, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param title the event title
	 * @param options the draggable options
	 */
	public EventObject(String id, IModel<String> title, Options options)
	{
		super(id, title);

		this.options = options;
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	// Events //
	@Override
	protected void onConfigure()
	{
		super.onConfigure();

		this.add(AttributeModifier.replace("data-title", this.getDefaultModel()));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryBehavior(selector, "draggable", this.options);
	}
}
