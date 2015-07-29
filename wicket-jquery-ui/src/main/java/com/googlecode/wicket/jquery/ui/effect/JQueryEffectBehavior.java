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
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides a specific jQuery behavior for playing effects.
 *
 * @author Sebastien Briquet - sebfz1
 */
public class JQueryEffectBehavior extends JQueryUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "effect";
	private static final int SPEED = 500;

	/**
	 * Helper method that returns the {@link JQueryEffectBehavior} string representation
	 * 
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 * @return the effect javascript statement
	 */
	public static String toString(String selector, Effect effect)
	{
		return JQueryEffectBehavior.toString(selector, effect.toString());
	}

	/**
	 * Helper method that returns the {@link JQueryEffectBehavior} string representation
	 * 
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 * @return the effect javascript statement
	 */
	public static String toString(String selector, String effect)
	{
		return new JQueryEffectBehavior(selector, effect, new EffectAdapter()).toString();
	}

	private final IEffectListener listener;
	private int speed;
	private String effect;

	private JQueryAjaxBehavior callback = null;

	/**
	 * Constructor.
	 *
	 * @param selector the html selector (ie: '#myId')
	 * @param listener the {@link IEffectListener}
	 */
	JQueryEffectBehavior(String selector, IEffectListener listener)
	{
		this(selector, "", new Options(), listener);
	}

	/**
	 * Constructor, with no option and a default speed of {@link #SPEED}
	 *
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 * @param listener the {@link IEffectListener}
	 */
	public JQueryEffectBehavior(String selector, String effect, IEffectListener listener)
	{
		this(selector, effect, new Options(), listener);
	}

	/**
	 * Constructor, with a default speed of {@link #SPEED}
	 *
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 * @param options the options to be applied
	 * @param listener the {@link IEffectListener}
	 */
	public JQueryEffectBehavior(String selector, String effect, Options options, IEffectListener listener)
	{
		this(selector, effect, options, SPEED, listener);
	}

	/**
	 * Constructor, with no option
	 *
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 * @param speed the speed of the effect
	 * @param listener the {@link IEffectListener}
	 */
	public JQueryEffectBehavior(String selector, String effect, int speed, IEffectListener listener)
	{
		this(selector, effect, new Options(), speed, listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: '#myId')
	 * @param effect the effect to be played
	 * @param options the options to be applied
	 * @param speed the speed of the effect
	 * @param listener the {@link IEffectListener}
	 */
	public JQueryEffectBehavior(String selector, String effect, Options options, int speed, IEffectListener listener)
	{
		super(selector, METHOD + "-" + effect, options);

		this.listener = Args.notNull(listener, "listener");
		this.effect = effect;
		this.speed = speed;
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		if (this.listener.isCallbackEnabled())
		{
			this.callback = this.newCallbackBehavior();
			component.add(this.callback);
		}
	}

	// Events //

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof CallbackEvent)
		{
			this.listener.onEffectComplete(target);
		}
	}

	// Statements //

	@Override
	protected String $()
	{
		return this.$(this.effect, this.options.toString());
	}

	/**
	 * Gets the jQuery statement.
	 *
	 * @param effect the effect to be played
	 * @return the jQuery statement
	 */
	@Override
	public String $(String effect)
	{
		return this.$(effect, Options.asString(""));
	}

	/**
	 * Gets the jQuery statement.
	 *
	 * @param effect the effect to be played
	 * @param options the options to be applied
	 * @return the jQuery statement
	 */
	public String $(String effect, String options)
	{
		return JQueryEffectBehavior.$(this.selector, effect, options, this.speed, String.format("%s", this.callback != null ? this.callback.getCallbackScript() : ""));
	}

	/**
	 * Gets the jQuery statement.
	 *
	 * @param effect the effect to be played
	 * @param options the options to be applied
	 * @param speed the speed of the effect
	 * @param callback the callback script to run once the effect completes
	 * @return the jQuery statement
	 */
	private static String $(String selector, String effect, String options, int speed, String callback)
	{
		return String.format("jQuery('%s').%s('%s', %s, %d, function() { %s });", selector, METHOD, effect, options, speed, callback);
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

	// Event objects //

	/**
	 * Provides the event object that will be broadcasted by the {@link JQueryAjaxBehavior} callback
	 */
	protected static class CallbackEvent extends JQueryEvent
	{
	}
}
