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
package org.wicketstuff.mbeanview;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ObjectViewPanel<T> extends Panel
{
	private static final long serialVersionUID = 1L;

	public static class PropValue implements Serializable
	{
		private static final long serialVersionUID = 1L;
		String property;
		Object value;
	}

	public ObjectViewPanel(String id, T object)
	{
		super(id);
		add(new Label("className", object.getClass().getName()));
		add(new Label("toString", object.toString()));
		ArrayList<PropValue> properties = new ArrayList<PropValue>();
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods)
		{
			if (method.getName().startsWith("get") && method.getParameterTypes().length == 0)
			{
				PropValue prop = new PropValue();
				try
				{
					prop.property = method.getName();
					prop.value = method.invoke(object, (Object[])null);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				properties.add(prop);
			}
		}
		add(new ListView<PropValue>("properties", properties)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<PropValue> item)
			{
				item.add(new Label("property", item.getModelObject().property));
				item.add(new Label("value", item.getModelObject().value == null ? null
					: item.getModelObject().value.toString()));
			}
		});
	}
}
