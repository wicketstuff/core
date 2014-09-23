package org.wicketstuff.egrid.attribute;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Options implements Serializable
{

	private static final long serialVersionUID = 1L;

	private final Map<String, String> options = new LinkedHashMap<String, String>();

	public Options()
	{
		super();
	}

	public boolean isEmpty()
	{
		return this.options.isEmpty();
	}

	public String get(String key)
	{
		return options.get(key);
	}

	public int getInt(String key)
	{
		return Integer.valueOf(this.options.get(key));
	}


	public short getShort(String key)
	{
		return Short.valueOf(this.options.get(key));
	}

	public float getFloat(String key)
	{
		return Float.valueOf(this.options.get(key));
	}

	public boolean getBoolean(String key)
	{

		return Boolean.valueOf(this.options.get(key));
	}

	public Options put(String key, String value)
	{
		this.options.put(key, value);
		return this;
	}

	public Options put(String key, int value)
	{
		this.options.put(key, String.valueOf(value));
		return this;
	}

	public Options put(String key, float value)
	{
		this.options.put(key, String.valueOf(value));
		return this;
	}

	public Options put(String key, boolean value)
	{
		this.options.put(key, String.valueOf(value));
		return this;
	}

	public void removeOption(String key)
	{
		this.options.remove(key);
	}

	public String getJavaScriptOptions()
	{
		StringBuffer sb = new StringBuffer("{");
		int count = 0;

		for (Entry<String, String> entry : this.options.entrySet())
		{
			String key = entry.getKey();
			sb.append(key);
			sb.append(":");
			sb.append(entry.getValue());
			if (count < this.options.size() - 1)
			{
				sb.append(",\n");
			}
			count++;
		}

		return sb.append("}").toString();
	}

	public String getCSSOptions()
	{
		StringBuffer sb = new StringBuffer();
		int count = 0;

		for (Entry<String, String> entry : this.options.entrySet())
		{
			String key = entry.getKey();
			sb.append(key);
			sb.append(":");
			sb.append(entry.getValue());
			if (count < this.options.size() - 1)
			{
				sb.append("; ");
			}
			count++;
		}
		return sb.toString();
	}

	public boolean containsKey(Object key)
	{
		return this.options.containsKey(key);
	}


}