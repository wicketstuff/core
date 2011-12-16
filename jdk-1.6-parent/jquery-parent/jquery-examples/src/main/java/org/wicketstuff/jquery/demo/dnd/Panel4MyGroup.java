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
package org.wicketstuff.jquery.demo.dnd;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.jquery.dnd.DnDSortableHandler;

@SuppressWarnings("serial")
public class Panel4MyGroup extends Panel
{

	public Panel4MyGroup(String id, MyGroup myGroup, final DnDSortableHandler dnd) throws Exception
	{
		super(id);
		add(new Label("label", myGroup.label));
		add(new Label("itemCnt", String.valueOf(myGroup.items.size())).setOutputMarkupId(true));
		add(new Label("actionCnt", "0").setOutputMarkupId(true));

		// create a container
		WebMarkupContainer container = new WebMarkupContainer("myItemContainer", Model.of(myGroup));
		dnd.registerContainer(container);
		add(container);

		// create items (add as children of the container)
		container.add(new ListView<MyItem>("myItem", myGroup.items)
		{
			@Override
			protected void populateItem(ListItem<MyItem> listitem)
			{
				try
				{
					listitem.add(new Label("myItemLabel", new PropertyModel<String>(
						listitem.getModelObject(), "label")));
					dnd.registerItem(listitem);
				}
				catch (RuntimeException exc)
				{
					throw exc;
				}
				catch (Exception exc)
				{
					throw new RuntimeException("wrap: " + exc.getMessage(), exc);
				}
			}
		});
	}
}
