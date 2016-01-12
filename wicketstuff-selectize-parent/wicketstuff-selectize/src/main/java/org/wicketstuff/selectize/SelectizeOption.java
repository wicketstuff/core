/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.selectize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

import org.apache.wicket.ajax.json.JSONObject;

/**
 * Provides the model of the selectize component and can be customize by invoking
 * put("key","value"); with custom values. Those values can be used within the
 * Handlebars-Template-Panel.
 * 
 * @author Tobias Soloschenko
 *
 */
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
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		String read = (String)in.readObject();
		JSONObject jsonObject = new JSONObject(read);
		Iterator iterator = jsonObject.keys();
		while (iterator.hasNext())
		{
			Object key = iterator.next();
			this.put((String)key, jsonObject.get((String)key));
		}
	}
}
