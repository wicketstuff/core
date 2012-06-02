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
import java.util.Map.Entry;

import org.apache.wicket.WicketRuntimeException;

/**
 * Provides a default implementation of {@link JQueryAbstractBehavior}.
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 * @since 1.0
 */
public class JQueryBehavior extends JQueryAbstractBehavior
{
	private static final long serialVersionUID = 1L;
	
	protected final String selector;
	protected final String method;
	protected final Options options;
	
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
			throw new WicketRuntimeException("Options have not been defined (null has been passed to the constructor)");
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
	@Override
	protected String $()
	{
		return this.$(this.selector, this.method, this.options.toString());
	}

	/**
	 * Gets the jQuery statement.<br/>
	 * <b>Warning:</b> If {@link #$()} is overridden to handle a different statement, this method could be inefficient.
	 * @param options the options to be applied
	 * @return String like '$(function() { ... })'
	 */
	public String $(String options)
	{
		return this.$(this.selector, this.method, options);
	}

	/**
	 * Gets the jQuery statement.
	 * @param selector the html selector (ie: "#myId")
	 * @param method the jQuery method to invoke
	 * @param options the options to be applied
	 * @return String like '$(function() { ... })'
	 */	
	private String $(String selector, String method, String options)
	{
		return String.format("$(function() { $('%s').%s(%s); });", selector, method, options);
	}
}
