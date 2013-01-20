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

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * Provides the button object that can be used in dialogs
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DialogButton implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private static short sequence = 0;

	/**
	 * Gets the next id-sequence. This is used to generate the markupId
	 * @return 0x0000 to 0x7FFF
	 */
	private static synchronized int nextSequence()
	{
		return (DialogButton.sequence++ % Short.MAX_VALUE);
	}


	private final int id;
	private final String text;
	private boolean enabled;
	private boolean visible = true;

	/**
	 * Constructor
	 * @param text the button's text
	 */
	public DialogButton(String text)
	{
		this(text, true);
	}

	/**
	 * Constructor
	 * @param model the button's text model
	 */
	public DialogButton(final IModel<String> model)
	{
		this(model.getObject(), true);
	}

	/**
	 * Constructor
	 * @param model the button's text model
	 * @param enabled indicates whether the button is enabled
	 */
	public DialogButton(final IModel<String> model, boolean enabled)
	{
		this(model.getObject(), enabled);
	}

	/**
	 * Constructor
	 * @param text the button's text
	 * @param enabled indicates whether the button is enabled
	 */
	public DialogButton(String text, boolean enabled)
	{
		this.id = DialogButton.nextSequence();
		this.text = text;
		this.enabled = enabled;
	}


	// Properties //

	/**
	 * Indicates whether the button is enabled
	 * @return true or false
	 */
	public boolean isEnabled()
	{
		return this.enabled;
	}

	/**
	 * Sets the enable state of the button
	 * @param enabled true or false
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * Sets the enable state of the button
	 * @param enabled true or false
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void setEnabled(boolean enabled, AjaxRequestTarget target)
	{
		if (enabled)
		{
			this.enable(target);
		}
		else
		{
			this.disable(target);
		}
	}

	/**
	 * Sets the visible state of the button
	 * @param visible true or false
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void setVisible(boolean visible, AjaxRequestTarget target)
	{
		if (this.visible != visible)
		{
			this.visible = visible;

			if (this.visible)
			{
				this.show(target);
			}
			else
			{
				this.hide(target);
			}
		}
	}

	/**
	 * Gets the markupId of the specified button.<br/>
	 * This can be used to enable/disable the button
	 *
	 * @return the markupId
	 */
	protected String getMarkupId()
	{
		return String.format("btn%02x", this.id).toLowerCase();
	}


	// Methods //

	/**
	 * Enables the button
	 * @param target the {@link AjaxRequestTarget}
	 */
	private void enable(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("jQuery('#%s').button('enable');", this.getMarkupId()));
	}

	/**
	 * Disables the button
	 * @param target the {@link AjaxRequestTarget}
	 */
	private void disable(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("jQuery('#%s').button('disable');", this.getMarkupId()));
	}

	/**
	 * Shows the button
	 * @param target the {@link AjaxRequestTarget}
	 */
	private void show(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("jQuery('#%s').show();", this.getMarkupId()));
	}

	/**
	 * Hides the button
	 * @param target the {@link AjaxRequestTarget}
	 */
	private void hide(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("jQuery('#%s').hide();", this.getMarkupId()));
	}


	@Override
	public int hashCode()
	{
		return this.id;
	}

	/**
	 * Indicates whether this {@link DialogButton} is equal to another {@link DialogButton}. Are considered equals buttons having the same text representation ({@link #toString()}), which is the text supplied to the constructor (if not overridden).
	 *
	 * @param object either a {@link DialogButton} or a {@link String}
	 * @return true if considered as equal
	 */
	@Override
	public boolean equals(Object object)
	{
		return (object != null) && (object.toString().equals(this.toString()));
	}

	@Override
	public String toString()
	{
		return this.text;
	}
}
