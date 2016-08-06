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

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.string.Strings;

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
	 *
	 * @return 0x0000 to 0x7FFF
	 */
	private static synchronized int nextSequence()
	{
		return DialogButton.sequence++ % Short.MAX_VALUE;
	}

	private final int id;
	private String name;
	private String icon;
	private boolean enabled;

	private final IModel<String> model;

	/**
	 * Constructor
	 *
	 * @param text the button's text
	 */
	public DialogButton(String name, String text)
	{
		this(name, Model.of(text), null, true);
	}

	/**
	 * Constructor
	 *
	 * @param text the button's text
	 * @param icon the button's icon
	 */
	public DialogButton(String name, String text, String icon)
	{
		this(name, Model.of(text), icon, true);
	}

	/**
	 * Constructor
	 *
	 * @param text the button's text
	 * @param enabled indicates whether the button is enabled
	 */
	public DialogButton(String name, String text, boolean enabled)
	{
		this(name, Model.of(text), null, enabled);
	}

	/**
	 * Constructor
	 *
	 * @param text the button's text
	 * @param icon the button's icon
	 * @param enabled indicates whether the button is enabled
	 */
	public DialogButton(String name, String text, String icon, boolean enabled)
	{
		this(name, Model.of(text), icon, enabled);
	}

	/**
	 * Constructor
	 *
	 * @param model the button's text model
	 */
	public DialogButton(String name, final IModel<String> model)
	{
		this(name, model, null, true);
	}

	/**
	 * Constructor
	 *
	 * @param model the button's text model
	 * @param icon the button's icon
	 */
	public DialogButton(String name, final IModel<String> model, String icon)
	{
		this(name, model, icon, true);
	}

	/**
	 * Constructor
	 *
	 * @param model the button's text model
	 * @param enabled indicates whether the button is enabled
	 */
	public DialogButton(String name, final IModel<String> model, boolean enabled)
	{
		this(name, model, null, enabled);
	}

	/**
	 * Constructor
	 *
	 * @param model the button's text model
	 * @param icon the button's icon
	 * @param enabled indicates whether the button is enabled
	 */
	public DialogButton(String name, final IModel<String> model, String icon, boolean enabled)
	{
		this.id = DialogButton.nextSequence();
		this.name = name;
		this.model= model;
		this.icon = icon;
		this.enabled = enabled;
	}

	// Properties //

	/**
	 * Gets the button's name
	 *
	 * @return the button's name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * INTERNAL USE<br/>
	 * Gets the model of the button's text<br/>
	 * <br/>
	 * <b>Warning: </b> It may throw an Exception if not wrapped to a component
	 *
	 * @return the {@link IModel}
	 */
	IModel<String> getModel()
	{
		return this.model;
	}

	/**
	 * Gets the button's icon
	 *
	 * @return the button's icon
	 */
	public String getIcon()
	{
		return this.icon;
	}

	/**
	 * Sets the button's icon
	 *
	 * @param icon the css class (ie: ui-my-icon)
	 */
	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	/**
	 * Indicates whether the button is enabled
	 *
	 * @return true or false
	 */
	public boolean isEnabled()
	{
		return this.enabled;
	}

	/**
	 * Sets the enable state of the button
	 *
	 * @param enabled true or false
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * Sets the enable state of the button
	 *
	 * @param enabled true or false
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void setEnabled(boolean enabled, IPartialPageRequestHandler handler)
	{
		if (enabled)
		{
			this.enable(handler);
		}
		else
		{
			this.disable(handler);
		}
	}

	/**
	 * Sets the visible state of the button
	 *
	 * @param visible true or false
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void setVisible(boolean visible, IPartialPageRequestHandler handler)
	{
		if (visible)
		{
			this.show(handler);
		}
		else
		{
			this.hide(handler);
		}
	}

	/**
	 * Indicates whether the button has a busy indicator
	 * 
	 * @return {@code false} by default
	 */
	public boolean isIndicating()
	{
		return false;
	}

	/**
	 * Gets the markupId of the specified button.<br/>
	 * This can be used to enable/disable the button
	 *
	 * @return the markupId
	 */
	public String getMarkupId()
	{
		return String.format("btn%02x", this.id).toLowerCase();
	}

	// Methods //

	/**
	 * Gets the javascript statement that will generate an ajax GET request to the behavior for this assigned button
	 *
	 * @param behavior the {@link ButtonAjaxBehavior}
	 * @return the javascript statement
	 */
	protected CharSequence getCallbackScript(ButtonAjaxBehavior behavior)
	{
		return behavior.getCallbackScript();
	}

	/**
	 * Enables the button
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	private void enable(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("jQuery('#%s').button('enable');", this.getMarkupId()));
	}

	/**
	 * Disables the button
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	private void disable(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("jQuery('#%s').button('disable');", this.getMarkupId()));
	}

	/**
	 * Shows the button
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	private void show(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("jQuery('#%s').show();", this.getMarkupId()));
	}

	/**
	 * Hides the button
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	private void hide(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("jQuery('#%s').hide();", this.getMarkupId()));
	}

	@Override
	public int hashCode()
	{
		return this.id;
	}

	/**
	 * Indicates whether this {@link DialogButton} is equal to another {@link DialogButton}.<br/>
	 * Are considered equals buttons having the same text representation, which is the text supplied to the constructor (if {@link #toString()} is not overridden).
	 *
	 * @param object the {@link DialogButton} to compare to
	 * @return true if considered as equal
	 */
	@Override
	public boolean equals(Object object)
	{
		if (object instanceof DialogButton)
		{
			return this.match(((DialogButton) object).getName());
		}

		return super.equals(object);
	}

	/**
	 * Indicates whether this {@link DialogButton} name match the supplied name.
	 *
	 * @param name the name to compare to
	 * @return true if equal
	 */
	public boolean match(String name)
	{
		return Strings.isEqual(name, this.name);
	}

	@Override
	public String toString()
	{
		return this.model.getObject();
	}
}
