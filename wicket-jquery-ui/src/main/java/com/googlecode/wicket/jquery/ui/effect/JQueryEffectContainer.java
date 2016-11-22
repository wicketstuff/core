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
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides a {@link WebMarkupContainer} on which effect can be played
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryEffectContainer extends WebMarkupContainer implements IEffectListener
{
	private static final long serialVersionUID = 1L;

	private JQueryEffectBehavior effectBehavior;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 */
	public JQueryEffectContainer(String id)
	{
		super(id);
	}

	// Properties //

	@Override
	public boolean isCallbackEnabled()
	{
		return true;
	}

	// Methods //

	/**
	 * Plays the specified effect.
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param effect the effect to be played
	 */
	public void play(IPartialPageRequestHandler handler, Effect effect)
	{
		if (effect != null)
		{
			this.play(handler, effect.toString());
		}
	}

	/**
	 * Plays the specified effect.
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param effect the effect to be played
	 */
	public void play(IPartialPageRequestHandler handler, String effect)
	{
		handler.appendJavaScript(this.effectBehavior.$(effect));
	}

	/**
	 * Shows the container by playing the 'fadeIn' effect.
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void show(IPartialPageRequestHandler handler)
	{
		JQueryBehavior behavior = new JQueryUIBehavior(JQueryWidget.getSelector(this), "fadeIn");
		handler.appendJavaScript(behavior.toString());
	}

	/**
	 * Hides the container by playing the 'fadeOut' effect.
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void hide(IPartialPageRequestHandler handler)
	{
		JQueryBehavior behavior = new JQueryUIBehavior(JQueryWidget.getSelector(this), "fadeOut");
		handler.appendJavaScript(behavior.toString());
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// cannot be in ctor as the markupId may be set manually afterward
		this.effectBehavior = this.newEffectBehavior(JQueryWidget.getSelector(this));
		this.add(this.effectBehavior);
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryEffectBehavior behavior)
	{
		// noop
	}

	@Override
	public void onEffectComplete(AjaxRequestTarget target)
	{
		// noop
	}

	// Factories //
	/**
	 * Gets a new {@link JQueryEffectBehavior}
	 * 
	 * @param selector
	 * @return the widget behavior
	 */
	protected JQueryEffectBehavior newEffectBehavior(String selector)
	{
		return new JQueryEffectBehavior(selector, this) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				JQueryEffectContainer.this.onConfigure(this);
			}
		};
	}
}
