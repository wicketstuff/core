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
package com.googlecode.wicket.jquery.ui.kendo.datatable;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

/**
 * Provides the button object that can be used in {@link DataTable}
 *
 * @author Sebastien Briquet - sebfz1
 */
//rename to button?
public class ColumnButton implements IClusterable
{
	private static final long serialVersionUID = 1L;

	private static short sequence = 0;

	/**
	 * Gets the next id-sequence. This is used to generate the markupId
	 * @return 0x0000 to 0x7FFF
	 */
	private static synchronized int nextSequence()
	{
		return ColumnButton.sequence++ % Short.MAX_VALUE;
	}


	private final int id;
	private final String text;
	private final String property;
//	private String icon;
//	private boolean enabled;
//	private boolean visible = true;

	/**
	 * Constructor
	 * @param text the button's text
	 * @param property the property used to retrieve the row's object value
	 */
	public ColumnButton(String text, String property)
	{
//		this(text, property, null, true);
		this.id = ColumnButton.nextSequence();
		this.text = text;
		this.property = property;
	}

//	/**
//	 * Constructor
//	 * @param text the button's text
//	 * @param icon the button's icon
//	 */
//	public ColumnButton(String text, String property, String icon)
//	{
//		this(text, property, icon, true);
//	}

//	/**
//	 * Constructor
//	 * @param text the button's text
//	 * @param enabled indicates whether the button is enabled
//	 */
//	public ColumnButton(String text, String property, boolean enabled)
//	{
//		this(text, property, null, enabled);
//	}

//	/**
//	 * Main constructor
//	 * @param text the button's text
//	 * @param icon the button's icon
//	 * @param enabled indicates whether the button is enabled
//	 */
//	public ColumnButton(String text, String property, String icon, boolean enabled)
//	{
//		this.id = ColumnButton.nextSequence();
//		this.text = text;
//		this.property = property;
//		this.icon = icon;
//		this.enabled = enabled;
//	}

	/**
	 * Constructor
	 * @param model the button's text model
	 * @param property the property used to retrieve the row's object value
	 */
	public ColumnButton(final IModel<String> model, String property)
	{
//		this(model.getObject(), property, null, true);
		this(model.getObject(), property);
	}

//	/**
//	 * Constructor
//	 * @param model the button's text model
//	 * @param icon the button's icon
//	 */
//	public ColumnButton(final IModel<String> model, String property, String icon)
//	{
//		this(model.getObject(), property, icon, true);
//	}
//
//	/**
//	 * Constructor
//	 * @param model the button's text model
//	 * @param enabled indicates whether the button is enabled
//	 */
//	public ColumnButton(final IModel<String> model, String property, boolean enabled)
//	{
//		this(model.getObject(), property, null, enabled);
//	}
//
//	/**
//	 * Constructor
//	 * @param model the button's text model
//	 * @param icon the button's icon
//	 * @param enabled indicates whether the button is enabled
//	 */
//	public ColumnButton(final IModel<String> model, String property, String icon, boolean enabled)
//	{
//		this(model.getObject(), icon, enabled);
//	}


	// Properties //
//	/**
//	 * Gets the button's icon
//	 * @return the button's icon
//	 */
//	public String getIcon()
//	{
//		return this.icon;
//	}
//
//	/**
//	 * Sets the button's icon
//	 * @param icon the css class (ie: ui-my-icon)
//	 */
//	public void setIcon(String icon)
//	{
//		this.icon = icon;
//	}
//
//	/**
//	 * Indicates whether the button is enabled
//	 * @return true or false
//	 */
//	public boolean isEnabled()
//	{
//		return this.enabled;
//	}
//
//	/**
//	 * Sets the enable state of the button
//	 * @param enabled true or false
//	 */
//	public void setEnabled(boolean enabled)
//	{
//		this.enabled = enabled;
//	}
//
//	/**
//	 * Sets the enable state of the button
//	 * @param enabled true or false
//	 * @param target the {@link AjaxRequestTarget}
//	 */
//	public void setEnabled(boolean enabled, AjaxRequestTarget target)
//	{
//		if (enabled)
//		{
//			this.enable(target);
//		}
//		else
//		{
//			this.disable(target);
//		}
//	}
//
//	/**
//	 * Sets the visible state of the button
//	 * @param visible true or false
//	 * @param target the {@link AjaxRequestTarget}
//	 */
//	public void setVisible(boolean visible, AjaxRequestTarget target)
//	{
//		if (this.visible != visible)
//		{
//			this.visible = visible;
//
//			if (this.visible)
//			{
//				this.show(target);
//			}
//			else
//			{
//				this.hide(target);
//			}
//		}
//	}

	public String getProperty()
	{
		return this.property;
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

//	/**
//	 * Enables the button
//	 * @param target the {@link AjaxRequestTarget}
//	 */
//	private void enable(AjaxRequestTarget target)
//	{
//		target.appendJavaScript(String.format("jQuery('#%s').button('enable');", this.getMarkupId()));
//	}
//
//	/**
//	 * Disables the button
//	 * @param target the {@link AjaxRequestTarget}
//	 */
//	private void disable(AjaxRequestTarget target)
//	{
//		target.appendJavaScript(String.format("jQuery('#%s').button('disable');", this.getMarkupId()));
//	}
//
//	/**
//	 * Shows the button
//	 * @param target the {@link AjaxRequestTarget}
//	 */
//	private void show(AjaxRequestTarget target)
//	{
//		target.appendJavaScript(String.format("jQuery('#%s').show();", this.getMarkupId()));
//	}
//
//	/**
//	 * Hides the button
//	 * @param target the {@link AjaxRequestTarget}
//	 */
//	private void hide(AjaxRequestTarget target)
//	{
//		target.appendJavaScript(String.format("jQuery('#%s').hide();", this.getMarkupId()));
//	}

	@Override
	public int hashCode()
	{
		return this.id;
	}

	/**
	 * Indicates whether this {@link ColumnButton} is equal to another {@link ColumnButton}.<br/>
	 * Are considered equals buttons having the same text representation, which is the text supplied to the constructor (if {@link #toString()} is not overridden).
	 *
	 * @param object a {@link ColumnButton} to compare to
	 * @return true if considered as equal
	 */
	@Override
	public boolean equals(Object object)
	{
		if (object instanceof ColumnButton)
		{
			return this.match(object.toString());
		}

		return super.equals(object);
	}

	/**
	 * Indicates whether this {@link ColumnButton} text representation ({@link #toString()}) match to the supplied text.
	 *
	 * @param text the text to compare to
	 * @return true if equal
	 */
	public boolean match(String text)
	{
		return text.equals(this.toString()); // let throw a NPE
	}

	@Override
	public String toString()
	{
		return this.text;
	}
}
