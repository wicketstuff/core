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
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wicket.jquery.ui.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;

/**
 * Provides a {@link WebMarkupContainer} on which effect can be played
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryEffectContainer extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;
	
	private JQueryAjaxBehavior callback;
	private JQueryEffectBehavior widgetBehavior;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public JQueryEffectContainer(String id)
	{
		super(id);
		
		this.init();
	}

	
	// Methods //

	/**
	 * Initialization
	 */
	private void init()
	{
		this.callback = new JQueryAjaxBehavior(this) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new CallbackEvent(target);
			}
		};
	}

	public void play(AjaxRequestTarget target, Effect effect)
	{
		if (effect != null)
		{
			this.play(target, effect.toString());
		}
	}

	/**
	 * Plays the specified effect.
	 * @param target the {@link AjaxRequestTarget}
	 * @param effect the effect to be played
	 */
	public void play(AjaxRequestTarget target, String effect)
	{
		target.appendJavaScript(this.widgetBehavior.$(effect));
	}
	
	/**
	 * Shows the container by playing the 'fadeIn' effect.
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void show(AjaxRequestTarget target)
	{
		JQueryBehavior behavior = new JQueryBehavior(JQueryWidget.getSelector(this), "fadeIn");
		target.appendJavaScript(behavior.toString());
	}
	
	/**
	 * Hides the container by playing the 'fadeOut' effect.
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void hide(AjaxRequestTarget target)
	{
		JQueryBehavior behavior = new JQueryBehavior(JQueryWidget.getSelector(this), "fadeOut");
		target.appendJavaScript(behavior.toString());
	}

	
	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		
		this.add(this.callback);
		this.add(this.widgetBehavior = this.newWidgetBehavior(JQueryWidget.getSelector(this))); //cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof CallbackEvent)
		{
			JQueryEvent payload = (JQueryEvent) event.getPayload();  
			this.onComplete(payload.getTarget());
		}
	}
	
	/**
	 * Triggered when the effect completes
	 */
	protected void onComplete(AjaxRequestTarget target)
	{
	}
	
	
	// Not IJQueryWidget //
	/**
	 * Gets a new {@link JQueryEffectBehavior}
	 * @param selector
	 * @return the widget behavior
	 */
	public JQueryEffectBehavior newWidgetBehavior(String selector)
	{
		return new JQueryEffectBehavior(selector) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				this.setCallback(callback);
			}
		};
	}


	// Event class //
	/**
	 * Provides the event object that will be broadcasted by the {@link JQueryAjaxBehavior} callback
	 */
	class CallbackEvent extends JQueryEvent
	{
		public CallbackEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}	
}
