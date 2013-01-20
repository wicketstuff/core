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
package com.googlecode.wicket.jquery.ui.effect;

import com.googlecode.wicket.jquery.ui.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;

/**
 * Provides a specific jQuery behavior for playing effects.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryEffectBehavior extends JQueryAbstractBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "effect";
	private static final int SPEED = 500;

	private final String selector;
	private int speed;
	private String effect;
	private Options options;
	private JQueryAjaxBehavior callback = null;

	/**
	 * Constructor.
	 * @param selector the html selector (ie: '#myId')
	 */
	JQueryEffectBehavior(String selector)
	{
		this(selector, "");
	}

	/**
	 * Constructor, with no option and a default speed of {@link #SPEED}
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 */
	public JQueryEffectBehavior(String selector, String effect)
	{
		this(selector, effect, new Options());
	}

	/**
	 * Constructor, with a default speed of {@link #SPEED}
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 * @param options the options to be applied
	 */
	public JQueryEffectBehavior(String selector, String effect, Options options)
	{
		this(selector, effect, options, SPEED);
	}

	/**
	 * Constructor, with no option
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 * @param speed the speed of the effect
	 */
	public JQueryEffectBehavior(String selector, String effect, int speed)
	{
		this(selector, effect, new Options(), speed);
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 * @param options the options to be applied
	 * @param speed the speed of the effect
	 */
	public JQueryEffectBehavior(String selector, String effect, Options options, int speed)
	{
		super(METHOD + "-" + effect);

		this.selector = selector;
		this.effect = effect;
		this.options = options;
		this.speed = speed;
	}

	/**
	 * Sets the {@link JQueryAjaxBehavior} to callback once the effect completes
	 * @param callback
	 */
	public void setCallback(JQueryAjaxBehavior callback)
	{
		this.callback = callback;
	}


	// Statements //

	@Override
	protected String $()
	{
		return this.$(this.effect, this.options.toString());
	}

	/**
	 * Gets the jQuery statement.
	 * @param effect the effect to be played
	 * @return Statement like 'jQuery(function() { ... })'
	 */
	public String $(String effect)
	{
		return this.$(effect, "''");
	}

	/**
	 * Gets the jQuery statement.
	 * @param effect the effect to be played
	 * @param options the options to be applied
	 * @return Statement like 'jQuery(function() { ... })'
	 */
	public String $(String effect, String options)
	{
		return JQueryEffectBehavior.$(this.selector, effect, options, this.speed, String.format("%s", (this.callback != null ? this.callback.getCallbackScript() : "")));
	}

	/**
	 * Gets the jQuery statement.
	 * @param effect the effect to be played
	 * @param options the options to be applied
	 * @param speed the speed of the effect
	 * @param callback the callback script to run once the effect completes
	 * @return Statement like 'jQuery(function() { ... })'
	 */
	private static String $(String selector, String effect, String options, int speed, String callback)
	{
		return String.format("jQuery(function() { jQuery('%s').%s('%s', %s, %d, function() { %s }); });", selector, METHOD, effect, options, speed, callback);
	}
}
