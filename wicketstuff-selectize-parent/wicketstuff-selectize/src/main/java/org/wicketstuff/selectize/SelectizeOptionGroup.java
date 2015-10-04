package org.wicketstuff.selectize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

import org.apache.wicket.ajax.json.JSONObject;

public class SelectizeOptionGroup extends JSONObject implements Serializable
{

	private static final long serialVersionUID = 1L;

	public static final String OPT_GROUP_FIELD = "groupId";

	public static final String OPT_GROUP_LABEL_FIELD = "text";

	public SelectizeOptionGroup(String groupId, String value, String text)
	{
		put(OPT_GROUP_FIELD, groupId);
		setText(text);
		setValue(value);
	}

	public void setText(String text)
	{
		put(OPT_GROUP_LABEL_FIELD, text);
	}

	public void setValue(String value)
	{
		put("value", value);
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
