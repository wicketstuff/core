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
package com.googlecode.wicket.kendo.ui.datatable.button;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.kendo.ui.datatable.DataTable;

/**
 * Provides a base button object that can be used in {@link DataTable}
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class AbstractButton implements IClusterable
{
	private static final long serialVersionUID = 1L;
	private static short sequence = 0;

	/**
	 * Gets the next id-sequence. This is used to generate the markup id
	 *
	 * @return 0x0000 to 0x7FFF
	 */
	private static synchronized int nextSequence()
	{
		return AbstractButton.sequence++ % Short.MAX_VALUE;
	}

	public static final String EDIT = "edit";
	public static final String SAVE = "save";
	public static final String CREATE = "create";
	public static final String CANCEL = "cancel";
	public static final String DESTROY = "destroy";

	private final int id;
	private final String name;
	private final IModel<String> text;
	private final String property;

	/**
	 * Constructor with default property to {@code null})
	 *
	 * @param name the button's name
	 */
	public AbstractButton(String name)
	{
		this(name, Model.of(name), null);
	}

	/**
	 * Constructor with default property to {@code null})
	 *
	 * @param name the button's name
	 * @param text the button's text
	 */
	public AbstractButton(String name, IModel<String> text)
	{
		this(name, text, null);
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param property the property used to retrieve the row's object value
	 */
	public AbstractButton(String name, String property)
	{
		this(name, Model.of(name), property);
	}

	/**
	 * Constructor
	 *
	 * @param name the button's name
	 * @param text the button's text
	 * @param property the property used to retrieve the row's object value
	 */
	public AbstractButton(String name, IModel<String> text, String property)
	{
		this.id = AbstractButton.nextSequence();
		this.name = name;
		this.text = text;
		this.property = property;
	}

	/**
	 * Gets the button name
	 *
	 * @return the button name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Gets the button text
	 *
	 * @return the button text
	 */
	public IModel<String> getText()
	{
		return this.text;
	}

	/**
	 * Gets the button property
	 *
	 * @return the button property
	 */
	public String getProperty()
	{
		return this.property;
	}

	/**
	 * Indicates whether the button should be visible
	 * 
	 * @return {@code true} by default
	 */
	public boolean isVisible()
	{
		return true;
	}

	// Methods //

	@Override
	public int hashCode()
	{
		return this.id;
	}

	/**
	 * Indicates whether the button acts as a built-in one (like create, edit, update, destroy)
	 * 
	 * @return true if the button is a built-in one
	 */
	public abstract boolean isBuiltIn();

	/**
	 * Indicates whether this {@link AbstractButton} is equal to another {@link AbstractButton}. Are considered equals buttons having the same name.
	 *
	 * @param object a {@link AbstractButton} to compare to
	 * @return true if considered as equal
	 */
	@Override
	public boolean equals(Object object)
	{
		if (object instanceof AbstractButton)
		{
			return this.match(((AbstractButton) object).getName());
		}

		return super.equals(object);
	}

	/**
	 * Indicates whether this {@link AbstractButton} name match to the supplied name.
	 *
	 * @param name the name to compare to
	 * @return true if equal
	 */
	public boolean match(String name)
	{
		return Strings.isEqual(name, this.name);
	}
}
