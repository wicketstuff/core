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
package com.googlecode.wicket.jquery.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Provides a default implementation of {@link JQueryAbstractBehavior}.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.0
 */
public class JQueryBehavior extends JQueryAbstractBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String NULL_OPTIONS = "Options have not been defined (null has been supplied to the constructor)";

	protected final String selector;
	protected final String method;
	protected final Options options;

	private List<String> events = null;

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public JQueryBehavior(String selector)
	{
		this(selector, "");
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param method the jquery method
	 */
	public JQueryBehavior(String selector, String method)
	{
		this(selector, method, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param method the jquery method
	 * @param options the {@link Options}
	 */
	public JQueryBehavior(String selector, String method, Options options)
	{
		super(method);

		this.method = method;
		this.options = options;
		this.selector = selector;
	}

	// Methods //
	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		// renders javascript events
		if (this.events != null)
		{
			StringBuilder statements = new StringBuilder("jQuery(function() { ");

			for (String event : this.events)
			{
				statements.append(event);
			}

			statements.append(" });");

			response.renderJavaScript(statements, this.getToken() + "-events");
		}
	}

	// Properties //
	/**
	 * Gets a behavior option, referenced by its key
	 * @param key the option key
	 * @return null if the key does not exists
	 */
	public Object getOption(String key)
	{
		if (this.options == null)
		{
			throw new WicketRuntimeException(NULL_OPTIONS);
		}

		return this.options.get(key);
	}

	/**
	 * Sets a behavior option.
	 * @param key the option key
	 * @param value the option value
	 * @return the {@link JQueryBehavior} (this)
	 */
	public JQueryBehavior setOption(String key, Serializable value)
	{
		if (this.options == null)
		{
			throw new WicketRuntimeException(NULL_OPTIONS);
		}

		this.options.set(key, value);

		return this;
	}

	/**
	 * Adds or replace behavior options
	 * @param options the {@link Options}
	 */
	public void setOptions(Options options)
	{
		for (Entry<String, Serializable> option : options.entries())
		{
			this.setOption(option.getKey(), option.getValue());
		}
	}


	// Statements //
	/**
	 * Registers a jQuery event callback
	 * @param event the jQuery event (ie: "click")
	 * @param callback the jQuery callback
	 */
	protected void on(String event, String callback)
	{
		this.on(this.selector, event, callback);
	}

	/**
	 * Registers a jQuery event callback
	 * @param selector the html selector (ie: "#myId")
	 * @param event the jQuery event (ie: "click")
	 * @param callback the jQuery callback
	 */
	protected synchronized void on(String selector, String event, String callback)
	{
		if (this.events == null)
		{
			this.events = new ArrayList<String>();
		}

		this.events.add(String.format("jQuery('%s').on('%s', %s);", selector, event, callback));
	}

	@Override
	protected String $()
	{
		return JQueryBehavior.$(this.selector, this.method, this.options.toString());
	}

	/**
	 * Gets the jQuery statement.<br/>
	 * <b>Warning: </b> This method is *not* called by this behavior directly (only {@link #$()} is).
	 * @param options the list of options to be supplied to the current method
	 * @return Statement like 'jQuery(function() { ... })'
	 */
	public String $(Object... options)
	{
		return this.$(Options.fromList(options));
	}

	/**
	 * Gets the jQuery statement.<br/>
	 * <b>Warning: </b> This method is *not* called by this behavior directly (only {@link #$()} is).
	 * @param options the options to be supplied to the current method
	 * @return Statement like 'jQuery(function() { ... })'
	 */
	public String $(String options)
	{
		return JQueryBehavior.$(this.selector, this.method, options);
	}

	/**
	 * Gets the jQuery statement.
	 * @param selector the html selector (ie: "#myId")
	 * @param method the jQuery method to invoke
	 * @param options the options to be applied
	 * @return Statement like 'jQuery(function() { ... })'
	 */
	private static String $(String selector, String method, String options)
	{
		return String.format("jQuery(function() { jQuery('%s').%s(%s); });", selector, method, options);
	}
}
