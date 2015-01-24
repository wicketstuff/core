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
package com.googlecode.wicket.kendo.ui.console;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

/**
 * Provides the base class for Kendo UI console widget<br/>
 * <b>Note about the capacity:</b> the capacity allows to define a maximum number of messages.<br/>
 * Elder messages will be automatically removed from the model object on insertion.<br/>
 * However, this is only reflected when the component is (re-)rendered ({@link #onBeforeRender()} has to be invoked)
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class AbstractConsole extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	protected ConsoleBehavior consoleBehavior;

	/**
	 * Constructor with a default capacity ({@link ConsoleMessages#CAPACITY})
	 *
	 * @param id the markup id
	 */
	public AbstractConsole(String id)
	{
		this(id, Model.of(new ConsoleMessages()));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param capacity the max capacity
	 */
	public AbstractConsole(String id, int capacity)
	{
		this(id, Model.of(new ConsoleMessages(capacity)));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link ConsoleMessages} model
	 */
	public AbstractConsole(String id, IModel<ConsoleMessages> model)
	{
		super(id, model);

		Args.notNull(model, "model");
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.consoleBehavior = this.newConsoleBehavior());
	}

	// Properties //

	/**
	 * Gets the model object
	 *
	 * @return the model object
	 */
	public ConsoleMessages getModelObject()
	{
		return (ConsoleMessages) this.getDefaultModelObject();
	}

	// Methods //

	/**
	 * Logs a message
	 *
	 * @param message the message to log
	 * @param error indicates whether the message is an error message
	 */
	public void log(Serializable message, boolean error)
	{
		this.getModelObject().put(message, error);
	}

	/**
	 * Logs a message
	 *
	 * @param message the message to log
	 * @param error indicates whether the message is an error message
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void log(Serializable message, boolean error, AjaxRequestTarget target)
	{
		this.log(message, error);

		target.appendJavaScript(this.consoleBehavior.$(message, error));
	}

	/**
	 * Formats the message (escaping, etc)
	 *
	 * @param message the message to format
	 * @param error indicates whether the message is an error message
	 * @return the formated message
	 */
	protected String format(Serializable message, boolean error)
	{
		return String.valueOf(message).replace("'", "\\'");
	}

	/**
	 * Clears console messages
	 */
	public void clear()
	{
		this.getModelObject().clear();
	}

	// Factories //

	/**
	 * Gets a new {@link ConsoleBehavior}
	 *
	 * @return a new {@link ConsoleBehavior}
	 */
	protected ConsoleBehavior newConsoleBehavior()
	{
		return new ConsoleBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected String format(Serializable message, boolean error)
			{
				return AbstractConsole.this.format(message, error);
			}

			@Override
			public String $()
			{
				StringBuilder builder = new StringBuilder();

				for (Map.Entry<Serializable, Boolean> entry : AbstractConsole.this.getModelObject().entrySet())
				{
					builder.append(this.$(entry.getKey(), entry.getValue())).append("\n");
				}

				return builder.toString();
			}
		};
	}
}
