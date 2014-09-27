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
package com.googlecode.wicket.kendo.ui.scheduler.resource;

import java.io.Serializable;

import com.googlecode.wicket.jquery.core.Options;

/**
 * Defines a resource for the Kendo UI Scheduler<br/>
 * Kendo UI Scheduler supports assigning scheduler events to a set of predefined resources.<br/>
 * The scheduler widget supports more than one kind of resource.<br/>
 * Multiple instances of the same resource type can be assigned to a scheduler event.<br/>
 * The scheduler widget allows the user to assign resources via the scheduler event edit form.<br/>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Resource implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final Id<?> id;
	private String text;
	private String color;

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 */
	protected Resource(String id)
	{
		this(Id.valueOf(id), null, null);
	}

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 */
	protected Resource(Integer id)
	{
		this(Id.valueOf(id), null, null);
	}

	/**
	 * Constructor<br/>
	 * <b>Caution:</b> string-numbers as will be interpreted as integers
	 *
	 * @param id - the resource id
	 * @param text - the text (ie: the name of the resource)
	 */
	public Resource(String id, String text)
	{
		this(Id.valueOf(id), text, null);
	}

	/**
	 * Constructor<br/>
	 * <b>Caution:</b> string-numbers as will be interpreted as integers
	 *
	 * @param id - the resource id
	 * @param text - the text (ie: the name of the resource)
	 * @param color - the color (ie: #336699)
	 */
	public Resource(String id, String text, String color)
	{
		this(Id.valueOf(id), text, color);
	}

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 * @param text - the text (ie: the name of the resource)
	 */
	public Resource(Integer id, String text)
	{
		this(Id.valueOf(id), text, null);
	}

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 * @param text - the text (ie: the name of the resource)
	 * @param color - the color (ie: #336699)
	 */
	public Resource(Integer id, String text, String color)
	{
		this(Id.valueOf(id), text, color);
	}

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 * @param text - the text (ie: the name of the resource)
	 * @param color - the color (ie: #336699)
	 */
	Resource(Id<?> id, String text, String color)
	{
		this.id = id;
		this.text = text;
		this.color = color;
	}

	/**
	 * Gets the resource id
	 *
	 * @return the resource id
	 */
	@SuppressWarnings("unchecked")
	public <T> T getId()
	{
		return (T) this.id.get();
	}

	/**
	 * Gets the text of the resource (ie: the name of the resource)
	 *
	 * @return the text
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * Sets the text of the resource (ie: the name of the resource)
	 *
	 * @param text - the text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Gets the color of the resource (ie: #336699)
	 *
	 * @return color - the color
	 */
	public String getColor()
	{
		return this.color;
	}

	/**
	 * Sets the color of the resource (ie: #336699)
	 *
	 * @param color - the color
	 */
	public void setColor(String color)
	{
		this.color = color;
	}

	/**
	 * Gets the JSON representation of this {@link Resource}
	 */
	@Override
	public String toString()
	{
		Options value = new Options();

		value.set("value", this.id.getValue());
		value.set("text", Options.asString(this.text));

		if (this.color != null)
		{
			value.set("color", Options.asString(this.color));
		}

		return value.toString();
	}
}
