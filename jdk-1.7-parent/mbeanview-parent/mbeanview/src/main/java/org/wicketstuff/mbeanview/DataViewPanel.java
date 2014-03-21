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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class DataViewPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	private static final String OUTPUT_ID = "output";

	public DataViewPanel(String id, IModel<?> value)
	{
		super(id);

		ListView<Object> contentsRepeater = new ListView<Object>("contents", new ValueModel(value))
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Object> item)
			{
				Object value = item.getModelObject();
				if (value == null || value instanceof String || value.getClass().isPrimitive())
				{
					item.add(new Label(OUTPUT_ID, value == null ? null : value.toString()));
				}
				else
				{
					item.add(new ObjectViewPanel<Object>(OUTPUT_ID, value));
				}
			}
		};
		add(contentsRepeater);
	}

	private static class ValueModel extends AbstractReadOnlyModel<List<Object>>
	{

		private IModel<?> value;

		public ValueModel(IModel<?> value)
		{
			this.value = value;
		}

		@Override
		public void detach()
		{
			super.detach();

			this.value.detach();
		}

		@Override
		public List<Object> getObject()
		{
			List<Object> contents = null;

			Object temp = value.getObject();
			if (temp == null)
			{
				//
			}
			else if (temp instanceof List)
			{
				contents = (List<Object>)temp;
			}
			else if (temp.getClass().isArray())
			{
				int length = Array.getLength(temp);
				if (length > 0)
				{
					contents = new ArrayList<Object>();
					for (int i = 0; i < length; i++)
					{
						contents.add(Array.get(temp, i));
					}
				}
			}
			else
			{
				contents = new ArrayList<Object>();
				contents.add(temp);
			}

			return contents;
		}
	}
}
