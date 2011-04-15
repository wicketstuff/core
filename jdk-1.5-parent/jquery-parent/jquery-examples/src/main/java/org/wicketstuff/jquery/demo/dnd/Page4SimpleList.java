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

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.jquery.demo.PageSupport;
import org.wicketstuff.jquery.dnd.DnDSortableHandler;

@SuppressWarnings("serial")
public class Page4SimpleList extends PageSupport
{
	// Data (for demo purpose)
	protected static List<MyItem> dataList_ = null;

	static
	{
		try
		{
			dataList_ = MyFactory.newMyItemList("simple", 4);
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

	// Component
	public Page4SimpleList() throws Exception
	{
		// define the action on DnD
		final DnDSortableHandler dnd = new DnDSortableHandler("dnd")
		{
			@Override
			public boolean onDnD(AjaxRequestTarget target, MarkupContainer srcContainer,
				int srcPos, MarkupContainer destContainer, int destPos)
			{
				// apply modification on model
				MyItem myItem = dataList_.remove(srcPos);
				dataList_.add(destPos, myItem);

				// update feedback message
				String msg = String.format("move '%s' from %d to %d", myItem.label, srcPos, destPos);
				FeedbackPanel feedback = (FeedbackPanel)Page4SimpleList.this.get("feedback");
				feedback.info(msg);
				if (target != null)
				{
					// target is null in testcase
					target.add(feedback);
				}
				// false = don't need to keep in sync component, markupId on serverside and client
// side
				return false;
			}
		};

		// add the DnD handler to the page
		add(dnd);
		add(new Link<Void>("start_dnd")
		{
			@Override
			protected CharSequence getOnClickScript(CharSequence url)
			{
				return dnd.getJSFunctionName4Start() + "();";
			}

			@Override
			protected CharSequence getURL()
			{
				return "#";
			}

			@Override
			public void onClick()
			{
				throw new UnsupportedOperationException("NOT CALLABLE");
			}

		});
		add(new Link<Void>("stop_dnd")
		{
			@Override
			protected CharSequence getOnClickScript(CharSequence url)
			{
				return dnd.getJSFunctionName4Stop() + "();";
			}

			@Override
			protected CharSequence getURL()
			{
				return "#";
			}

			@Override
			public void onClick()
			{
				throw new UnsupportedOperationException("NOT CALLABLE");
			}

		});

		// create a container
		WebMarkupContainer webList = new WebMarkupContainer("myItemList");
		dnd.registerContainer(webList);
		add(webList);

		// create items (add as children of the container)
		webList.add(new ListView<MyItem>("myItem", dataList_)
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
