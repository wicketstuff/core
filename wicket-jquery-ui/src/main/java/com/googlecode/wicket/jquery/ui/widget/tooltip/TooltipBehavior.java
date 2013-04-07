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
package com.googlecode.wicket.jquery.ui.widget.tooltip;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides the jQuery tooltip behavior.<br/>
 * The {@link TooltipBehavior} apply to a page and therefore needs to be added once:<br/>
 * <pre>
 * Java:
 * <code>
 * public class MyPage extends WebPage
 * {
 *	public DefaultAutoCompletePage()
 *	{
 *		// Tooltip Behavior //
 *		this.add(new TooltipBehavior());
 *	}
 * }
 * </code>
 * HTML:
 * <code>
 * &lt;input wicket:id="myinput" type="text" title="enter your criteria here"&gt;&lt;/input&gt;
 * </code>
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.4.0
 * @since 6.2.0
 */
public class TooltipBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "tooltip";

	/**
	 * Default constructor<br/>
	 * {@link TooltipBehavior} will apply on <code>document</code>
	 */
	public TooltipBehavior()
	{
		super(null, METHOD);
	}

	/**
	 * Constructor<br/>
	 * {@link TooltipBehavior} will apply on <code>document</code>
	 *
	 * @param options the {@link Options}
	 */
	public TooltipBehavior(Options options)
	{
		super(null, METHOD, options);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public TooltipBehavior(String selector)
	{
		super(selector, METHOD);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public TooltipBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	@Override
	protected String $()
	{
		if (this.selector == null)
		{
			return TooltipBehavior.$(this.method, options.toString());
		}

		return super.$();
	}

	/**
	 * Gets the jQuery statement.
	 *
	 * @param method the jQuery method to invoke
	 * @param options the options to be applied
	 * @return Statement like 'jQuery(function() { ... })'
	 */
	private static String $(String method, String options)
	{
		return String.format("jQuery(function() { jQuery(document).%s(%s); });", method, options);
	}
}
