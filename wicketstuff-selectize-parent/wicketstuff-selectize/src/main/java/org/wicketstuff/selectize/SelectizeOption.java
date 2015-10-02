package org.wicketstuff.selectize;

import org.apache.wicket.ajax.json.JSONObject;

public class SelectizeOption extends JSONObject
{

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
}
