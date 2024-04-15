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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.openjson.JSONObject;

/**
 * Defines a resource for the Kendo UI Scheduler<br>
 * Kendo UI Scheduler supports assigning scheduler events to a set of predefined resources.<br>
 * The scheduler widget supports more than one kind of resource.<br>
 * Multiple instances of the same resource type can be assigned to a scheduler event.<br>
 * The scheduler widget allows the user to assign resources via the scheduler event edit form.<br>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Resource implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final Object id;
	private String text;
	private String color;

	/** optional fields */
	private final Map<String, Object> fields = new HashMap<String, Object>();

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 */
	protected Resource(String id)
	{
		this(id, null, null);
	}

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 */
	protected Resource(Object id)
	{
		this(id, null, null);
	}

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 * @param text - the text (ie: the name of the resource)
	 */
	public Resource(String id, String text)
	{
		this(id, text, null);
	}

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 * @param text - the text (ie: the name of the resource)
	 */
	public Resource(Object id, String text)
	{
		this(id, text, null);
	}

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 * @param text - the text (ie: the name of the resource)
	 * @param color - the color (ie: #336699)
	 */
	public Resource(String id, String text, String color)
	{
		this.id = id;
		this.text = text;
		this.color = color;
	}

	/**
	 * Constructor
	 *
	 * @param id - the resource id
	 * @param text - the text (ie: the name of the resource)
	 * @param color - the color (ie: #336699)
	 */
	public Resource(Object id, String text, String color)
	{
		this.id = id;
		this.text = text;
		this.color = color;
	}

	// Properties //

	/**
	 * Gets the resource id<br>
	 * <b>Caution:</b> not type-safe
	 *
	 * @param <T> the object type
	 * @return the resource id
	 */
	@SuppressWarnings("unchecked")
	public <T> T getId()
	{
		return (T) this.id;
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

	// Methods //

	/**
	 * Sets a field's value
	 * 
	 * @param field the field name
	 * @param value the value
	 */
	public void set(String field, Object value)
	{
		this.fields.put(field, value);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object object)
	{
		if (this == object)
		{
			return true;
		}

		if (object == null)
		{
			return false;
		}

		if (!(object instanceof Resource))
		{
			return false;
		}

		Resource other = (Resource) object;

		if (this.id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!this.id.equals(other.id))
		{
			return false;
		}

		return true;
	}
	
	/**
	 * Gets the JSON representation of this {@link Resource}
	 */
	@Override
	public String toString()
	{
		return toJSONObject(this).toString();
	}
	
	// statics //
	
	/**
	 * Gets this {@link Resource} as {@link JSONObject}
	 * 
	 * @return a {@link JSONObject}
	 */
	public static JSONObject toJSONObject(Resource resource)
	{
		final JSONObject object = new JSONObject();

		if (resource != null)
		{
			object.put("value", resource.id);
			object.put("text", resource.text);
			object.putOpt("color", resource.color); // may be null
	
			for (Entry<String, Object> entry : resource.fields.entrySet())
			{
				object.put(entry.getKey(), entry.getValue());
			}
		}

		return object;
	}	
}
