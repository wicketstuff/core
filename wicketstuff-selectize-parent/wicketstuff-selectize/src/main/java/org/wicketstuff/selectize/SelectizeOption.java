package org.wicketstuff.selectize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

import org.apache.wicket.ajax.json.JSONObject;

public class SelectizeOption extends JSONObject implements Serializable
{
	private static final long serialVersionUID = 1L;

	public SelectizeOption(String value, String text)
	{
		this(value, text, null);
	}

	public SelectizeOption(String value, String text, String groupId)
	{
		setText(text);
		setValue(value);
		if (groupId != null)
		{
			setGroup(groupId);
		}

	}

	public void setText(String text)
	{
		put("text", text);
	}

	public void setValue(String value)
	{
		put("value", value);
	}

	public void setGroup(String groupId)
	{
		put(SelectizeOptionGroup.OPT_GROUP_FIELD, groupId);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException
	{
		out.writeObject(toString());
	}

	@SuppressWarnings("rawtypes")
	private void readObject(ObjectInputStream in) throws IOException,
		ClassNotFoundException
	{
		String read = (String)in.readObject();
		JSONObject jsonObject = new JSONObject(read);
		Iterator iterator = jsonObject.keySet().iterator();
		while(iterator.hasNext()){
			Object key = iterator.next();
			this.put((String)key, jsonObject.get((String)key));
		}
	}
}
