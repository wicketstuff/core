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

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.model.IModel;

/**
 * Provides a Kendo UI console widget<br>
 * <br>
 * <b>Note about the capacity:</b> the capacity allows to define a maximum number of messages.<br>
 * Elder messages will be automatically removed from the model object on insertion.<br>
 * However, this is only reflected when the component is (re-)rendered ({@link #onBeforeRender()} has to be invoked)
 *
 * @author Sebastien Briquet - sebfz1
 */
public class Console extends AbstractConsole
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with a default capacity ({@link ConsoleMessages#CAPACITY})
	 *
	 * @param id the markup id
	 */
	public Console(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param capacity the max capacity
	 */
	public Console(String id, int capacity)
	{
		super(id, capacity);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link ConsoleMessages} model
	 */
	public Console(String id, IModel<ConsoleMessages> model)
	{
		super(id, model);
	}

	// Method //

	/**
	 * Helper method that logs an info message
	 *
	 * @param message the message to log
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void info(IPartialPageRequestHandler handler, Serializable message)
	{
		this.log(message, false);

		handler.appendJavaScript(this.consoleBehavior.$(message, false));
	}

	/**
	 * Helper method that logs an error message
	 *
	 * @param message the message to log
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void error(IPartialPageRequestHandler handler, Serializable message)
	{
		this.log(message, true);

		handler.appendJavaScript(this.consoleBehavior.$(message, true));
	}
}
