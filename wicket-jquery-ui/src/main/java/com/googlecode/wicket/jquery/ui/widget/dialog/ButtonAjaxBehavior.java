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
package com.googlecode.wicket.jquery.ui.widget.dialog;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.head.IHeaderResponse;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.form.button.AjaxIndicatingButtonBehavior;
import com.googlecode.wicket.jquery.ui.form.button.Button;

/**
 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'click' event of {@link DialogButton}{@code s}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class ButtonAjaxBehavior extends JQueryAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final DialogButton button;

	/**
	 * Constructor
	 * 
	 * @param source the {@link IJQueryAjaxAware}
	 * @param button the {@link DialogButton} to attach to the {@link ClickEvent}
	 */
	public ButtonAjaxBehavior(IJQueryAjaxAware source, DialogButton button)
	{
		super(source);

		this.button = button;
	}

	// Properties //

	/**
	 * Gets the {@link DialogButton}
	 * 
	 * @return the {@link DialogButton}
	 */
	public DialogButton getButton()
	{
		return this.button;
	}

	// Methods //

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(AjaxIndicatingButtonBehavior.newIndicatorCssHeaderItem());
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);

		if (this.button.isIndicating())
		{
			AjaxCallListener ajaxCallListener = new AjaxCallListener();
			ajaxCallListener.onBefore(this.getBeforeStatement());
			ajaxCallListener.onSuccess(this.getFinishStatement());
			ajaxCallListener.onFailure(this.getFinishStatement());

			attributes.getAjaxCallListeners().add(ajaxCallListener);
		}
	}

	// Factories //

	/**
	 * Gets the javascript statement that will be called on {@link AjaxCallListener#onBefore(CharSequence)}
	 * 
	 * @return the javascript statement
	 */
	private String getBeforeStatement()
	{
		return String.format("jQuery('#%s').button(%s);", this.button.getMarkupId(), this.newOnClickOptions());
	}

	/**
	 * Gets the javascript statement that will be called on {@link AjaxCallListener#onSuccess(CharSequence)} and {@link AjaxCallListener#onFailure(CharSequence)}
	 * 
	 * @return the javascript statement
	 */
	private String getFinishStatement()
	{
		return String.format("jQuery('#%s').button(%s);", this.button.getMarkupId(), this.newOnAjaxStopOptions());
	}

	/**
	 * Gets the new {@link Button}'s {@link Options} to be used on click
	 *
	 * @return the {@link Options}
	 */
	protected Options newOnClickOptions()
	{
		Options options = new Options();
		options.set("disabled", true);
		options.set("icons", new Options("primary", Options.asString(AjaxIndicatingButtonBehavior.CSS_INDICATOR)));

		return options;
	}

	/**
	 * Gets the new {@link Button}'s {@link Options} to be used on when ajax stops
	 *
	 * @return the {@link Options}
	 */
	protected Options newOnAjaxStopOptions()
	{
		String icon = this.button.getIcon();

		Options options = new Options();
		options.set("disabled", !this.button.isEnabled());
		options.set("icons", new Options("primary", JQueryIcon.isNone(icon) ? "null" : Options.asString(icon)));

		return options;
	}

	@Override
	protected JQueryEvent newEvent()
	{
		return new ClickEvent(this.button);
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link ButtonAjaxBehavior} callback
	 */
	protected static class ClickEvent extends JQueryEvent
	{
		private final DialogButton button;

		public ClickEvent(DialogButton button)
		{
			super();

			this.button = button;
		}

		/**
		 * Get the button that has been attached to this event object
		 * 
		 * @return the button
		 */
		public DialogButton getButton()
		{
			return this.button;
		}
	}
}
