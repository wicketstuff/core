package org.wicketstuff.selectize;

import org.apache.wicket.ajax.json.JSONObject;

public class SelectizeOptionGroup extends JSONObject
{

	public static final String OPT_GROUP_FIELD = "groupId";

	public static final String OPT_GROUP_LABEL_FIELD = "text";
	
	public SelectizeOptionGroup(String groupId,String value, String text)
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
}
