package org.wicketstuff.simile.timeline.model;

import net.sf.json.JSONObject;

/**
 * 'model class' that will be converted into a call of a constructor using 'object' as parameter
 * when converted by {@link JSONObject}.fromObject
 * 
 * @author arnouten
 * 
 * @param <T>
 */
public class JsonConstructor<T>
{
	private String name;

	private T object;

	public JsonConstructor(String name, T object)
	{
		this.object = object;
		this.name = name;
	}

	public T getObject()
	{
		return object;
	}

	public String getName()
	{
		return name;
	}
}
