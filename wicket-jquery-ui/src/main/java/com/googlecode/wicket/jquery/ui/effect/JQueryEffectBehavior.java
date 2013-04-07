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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.core.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;

/**
 * Provides a specific jQuery behavior for playing effects.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryEffectBehavior extends JQueryAbstractBehavior implements IJQueryAjaxAware, IEffectListener
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

	// Properties //
	@Override
	public boolean isCallbackEnabled()
	{
		return false;
	}

	// Methods //
	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.isCallbackEnabled())
		{
			component.add(this.callback = this.newCallbackBehavior());
		}
	}

	// Events //
	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof CallbackEvent)
		{
			this.onEffectComplete(target);
		}
	}

	@Override
	public void onEffectComplete(AjaxRequestTarget target)
	{
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

	// Factories //
	/**
	 * Gets the ajax behavior that will be triggered when the user has selected items
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newCallbackBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new CallbackEvent();
			}
		};
	}


	// Event class //
	/**
	 * Provides the event object that will be broadcasted by the {@link JQueryAjaxBehavior} callback
	 */
	protected static class CallbackEvent extends JQueryEvent
	{
	}
}
