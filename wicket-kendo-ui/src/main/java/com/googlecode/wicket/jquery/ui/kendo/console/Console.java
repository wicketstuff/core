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
package com.googlecode.wicket.jquery.ui.kendo.console;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Provides a Kendo UI console widget
 *
 * @author Sebastien Briquet - sebfz1
 */
public class Console extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	private ConsoleBehavior consoleBehavior;

	/**
	 * Constructor with a default capacity ({@value ConsoleQueue#CAPACITY}) {@link ConsoleQueue}
	 *
	 * @param id the markup id
	 */
	public Console(String id)
	{
		this(id, Model.of(new ConsoleQueue()));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link ConsoleQueue} model
	 */
	public Console(String id, IModel<ConsoleQueue> model)
	{
		super(id, model);

		this.setEscapeModelStrings(false);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.consoleBehavior = this.newConsoleBehavior());
	}

	// Properties //

	public ConsoleQueue getModelObject()
	{
		return (ConsoleQueue) this.getDefaultModelObject();
	}

	// Methods //

	/**
	 * Logs a message
	 *
	 * @param message the message to log
	 * @param error indicates whether the message is an error message
	 */
	public void log(String message, boolean error)
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
	public void log(String message, boolean error, AjaxRequestTarget target)
	{
		this.log(message, error);

		target.appendJavaScript(this.consoleBehavior.$(message, error));
	}

	/**
	 * Helper method that logs an info message
	 *
	 * @param message the message to log
	 */
	public void info(String message)
	{
		this.log(message, false);
	}

	/**
	 * Helper method that logs an info message
	 *
	 * @param message the message to log
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void info(String message, AjaxRequestTarget target)
	{
		this.info(message);

		target.appendJavaScript(this.consoleBehavior.$(message, false));
	}

	/**
	 * Helper method that logs an error message
	 *
	 * @param message the message to log
	 */
	public void error(String message)
	{
		this.log(message, true);
	}

	/**
	 * Helper method that logs an error message
	 *
	 * @param message the message to log
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void error(String message, AjaxRequestTarget target)
	{
		this.error(message);

		target.appendJavaScript(this.consoleBehavior.$(message, true));
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
			protected String $()
			{
				StringBuilder builder = new StringBuilder("jQuery(function() {\n");

				for (Map.Entry<String, Boolean> entry : Console.this.getModelObject().entrySet())
				{
					builder.append(this.$(entry.getKey(), entry.getValue())).append("\n");
				}

				builder.append("});");

				return builder.toString();
			}
		};
	}

	/**
	 * Provides the {@link Console} model-object
	 */
	public static class ConsoleQueue extends LinkedHashMap<String, Boolean>
	{
		private static final long serialVersionUID = 1L;

		/** Default capacity */
		private static final int CAPACITY = 50;

		private final int capacity;

		public ConsoleQueue()
		{
			this.capacity = CAPACITY;
		}

		public ConsoleQueue(int capacity)
		{
			this.capacity = capacity;
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest)
		{
			return this.size() > this.capacity;
		}
	}
}