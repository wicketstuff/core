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
package com.googlecode.wicket.jquery.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.util.lang.Args;

/**
 * Provides a default implementation of {@link JQueryAbstractBehavior}.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.0
 */
public class JQueryBehavior extends JQueryAbstractBehavior
{
	private static final long serialVersionUID = 1L;

	protected String selector = null;
	protected final String method;
	protected final Options options;

	private List<String> events = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public JQueryBehavior(String selector)
	{
		this(selector, "");
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param method the jquery method
	 */
	public JQueryBehavior(String selector, String method)
	{
		this(selector, method, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param method the jquery method
	 * @param options the {@link Options}
	 */
	public JQueryBehavior(String selector, String method, Options options)
	{
		super(method);

		Args.notNull(options, "options");

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

			response.render(JavaScriptHeaderItem.forScript(statements, this.getToken() + "-events"));
		}
	}

	/**
	 * Removes the element matching the current selector from the DOM.<br/>
	 * The {@code #detach} method is the same as {@code #remove}, except that {@code #detach} keeps all jQuery data associated with the removed elements.<br/>
	 * This method is useful when removed elements are to be reinserted into the DOM at a later time.
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @see #remove(AjaxRequestTarget)
	 */
	public void detach(AjaxRequestTarget target)
	{
		target.prependJavaScript(String.format("jQuery('%s').detach();", this.selector));
	}

	/**
	 * Removes the element matching the current selector from the DOM.<br/>
	 * Use {@code #remove} when you want to remove the element itself, as well as everything inside it.<br/>
	 * In addition to the elements themselves, all bound events and jQuery data associated with the elements are removed.<br/>
	 * To remove the elements without removing data and events, use {@code #detach} instead.
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @see #detach(AjaxRequestTarget)
	 */
	public void remove(AjaxRequestTarget target)
	{
		target.prependJavaScript(String.format("jQuery('%s').remove();", this.selector));
	}

	// Properties //

	/**
	 * Gets the selector
	 *
	 * @return the selector
	 */
	public String getSelector()
	{
		return this.selector;
	}

	/**
	 * Sets the selector
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	protected void setSelector(String selector)
	{
		this.selector = selector;
	}

	/**
	 * Gets the jQuery method
	 *
	 * @return the method
	 */
	public String getMethod()
	{
		return this.method;
	}

	/**
	 * Gets a behavior option, referenced by its key
	 *
	 * @param key the option key
	 * @return null if the key does not exists
	 */
	public <T extends Object> T getOption(String key)
	{
		return this.options.get(key);
	}

	/**
	 * Sets a behavior option.
	 *
	 * @param key the option key
	 * @param value the option value
	 * @return the {@link JQueryBehavior} (this)
	 */
	public JQueryBehavior setOption(String key, Object value)
	{
		this.options.set(key, value);

		return this;
	}

	/**
	 * Sets a behavior option, with multiple values.
	 *
	 * @param key the option key
	 * @param values the option values
	 * @return the {@link JQueryBehavior} (this)
	 */
	public JQueryBehavior setOption(String key, Object... values)
	{
		this.options.set(key, values);

		return this;
	}

	/**
	 * Sets a behavior option, with multiple values.
	 *
	 * @param key the option key
	 * @param values the option values
	 * @return the {@link JQueryBehavior} (this)
	 */
	public JQueryBehavior setOption(String key, List<?> values)
	{
		this.options.set(key, values);

		return this;
	}

	/**
	 * Gets the {@link Options}
	 *
	 * @return the {@link Options}
	 */
	public Options getOptions()
	{
		return this.options;
	}

	/**
	 * Adds or replace behavior options
	 *
	 * @param options the {@link Options}
	 */
	public void setOptions(Options options)
	{
		for (Entry<String, Object> option : options.entries())
		{
			this.setOption(option.getKey(), option.getValue());
		}
	}

	// Statements //

	/**
	 * Registers a jQuery event callback
	 *
	 * @param event the jQuery event (ie: "click")
	 * @param callback the jQuery callback
	 */
	protected void on(String event, String callback)
	{
		this.on(this.selector, event, callback);
	}

	/**
	 * Registers a jQuery event callback
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param event the jQuery event (ie: "click")
	 * @param callback the jQuery callback
	 */
	protected void on(String selector, String event, String callback)
	{
		this.on(String.format("jQuery('%s').on('%s', %s);", selector, event, callback));
	}

	/**
	 * Registers a jQuery event statement
	 *
	 * @param statement the jQuery statement (ie: "jQuery('#myId').on('click', function() {});")
	 */
	protected void on(String statement)
	{
		if (this.events == null)
		{
			synchronized (this)
			{
				this.events = new ArrayList<String>();
			}
		}

		this.events.add(statement);
	}

	@Override
	protected String $()
	{
		return JQueryBehavior.$(this.selector, this.method, this.options.toString());
	}

	/**
	 * Gets the jQuery statement.<br/>
	 * <b>Warning: </b> This method is *not* called by the behavior directly (only {@link #$()} is).
	 *
	 * @param options the {@link Options} to be supplied to the current method
	 * @return the jQuery statement
	 */
	public String $(Options options)
	{
		return this.$(options.toString());
	}

	/**
	 * Gets the jQuery statement.<br/>
	 * <b>Warning: </b> This method is *not* called by the behavior directly (only {@link #$()} is).
	 *
	 * @param options the list of options to be supplied to the current method
	 * @return the jQuery statement
	 */
	public String $(Object... options)
	{
		return this.$(Options.fromArray(options));
	}

	/**
	 * Gets the jQuery statement.<br/>
	 * <b>Warning: </b> This method is *not* called by the behavior directly (only {@link #$()} is).
	 *
	 * @param options the options to be supplied to the current method
	 * @return the jQuery statement
	 */
	public String $(String options)
	{
		return JQueryBehavior.$(this.selector, this.method, options);
	}

	/**
	 * Gets the jQuery statement.
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param method the jQuery method to invoke
	 * @param options the options to be applied
	 * @return the jQuery statement
	 */
	private static String $(String selector, String method, String options)
	{
		return String.format("jQuery('%s').%s(%s);", selector, method, options);
	}

	// Events //

	/**
	 * {@inheritDoc} <br/>
	 * Also, {@link #onConfigure(Component)} will call {@link IJQueryWidget#onConfigure(JQueryBehavior)} (if the component IS-A {@link IJQueryWidget})<br/>
	 * If a property set is in {@link #onConfigure(Component)} is needed in {@link IJQueryWidget#onConfigure(JQueryBehavior)}, {@code super.onConfigure(component)} should be the last statement.
	 */
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (component instanceof IJQueryWidget)
		{
			((IJQueryWidget) component).onConfigure(this);
		}
	}

	/**
	 * {@inheritDoc} <br/>
	 * Also, {@link #beforeRender(Component)} will call {@link IJQueryWidget#onBeforeRender(JQueryBehavior)} (if the component IS-A {@link IJQueryWidget})<br/>
	 * If a property set is in {@link #beforeRender(Component)} is needed in {@link IJQueryWidget#onBeforeRender(JQueryBehavior)}, {@code super.beforeRender(component)} should be the last statement.
	 */
	@Override
	public void beforeRender(Component component)
	{
		super.beforeRender(component);

		if (component instanceof IJQueryWidget)
		{
			((IJQueryWidget) component).onBeforeRender(this);
		}
	}
}
