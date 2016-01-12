/*
 * $Id: SliderPage.java 4820 2006-03-08 08:21:01Z eelco12 $ $Revision: 4820 $
 * $Date: 2006-03-08 16:21:01 +0800 (Wed, 08 Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.examples.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.yui.behavior.dragdrop.YuiDDTarget;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.list.YuiDDListView;
import org.wicketstuff.yui.markup.html.list.YuiDDListViewPanel;

/**
 * 
 * @author Josh
 */
public class ReorderingListPage extends WicketExamplePage
{

	private static final String GROUP_ID = "DDList_animals";

	/**
	 * Construct.
	 */
	@SuppressWarnings("serial")
	public ReorderingListPage()
	{
		// feedback
		final FeedbackPanel feedback;
		add(feedback = new FeedbackPanel("label"));
		feedback.setOutputMarkupId(true);
		feedback.setOutputMarkupPlaceholderTag(true);

		// list 1
		final WebMarkupContainer list1;
		add(list1 = new WebMarkupContainer("list1"));
		list1.setOutputMarkupId(true);

		List<String> list1items = new ArrayList<String>();
		list1items.add("Apples");
		list1items.add("Oranges");
		list1items.add("Pineapples");

		list1.add(new YuiDDListView<String>("items", list1items)
		{
			@Override
			protected void populateItem(ListItem<String> item)
			{
				super.populateItem(item);
				item.add(new Label("item", item.getDefaultModelObjectAsString()));
			}

			@Override
			protected void onAjaxUpdate(AjaxRequestTarget target)
			{
				info("list1 : " + getList());
				target.addComponent(list1);
				target.addComponent(feedback);
			}
		});

		// list 2
		final WebMarkupContainer list2;

		final List<String> list2items = new ArrayList<String>();
		list2items.add("Dogs");
		list2items.add("Cats");

		add(list2 = new WebMarkupContainer("list2"));
		list2.setOutputMarkupId(true);
		 list2.add(new YuiDDTarget(GROUP_ID)
		{

			@SuppressWarnings("unchecked")
			@Override
			public void onDrop(AjaxRequestTarget target, Component newComponent)
			{
				String newItem = ((ListItem<String>)newComponent).getModelObject();
				list2items.add(newItem);

				info("list2 : " + list2items);
				target.addComponent(list2);
				target.addComponent(feedback);
			}

		});

		list2.add(new YuiDDListView<String>("items", list2items)
		{
			@Override
			protected void populateItem(ListItem<String> item)
			{
				super.populateItem(item);
				item.add(new Label("item", item.getDefaultModelObjectAsString()));
			}

			@Override
			protected String getGroupId()
			{
				return GROUP_ID;
			}

			@Override
			protected void onAjaxUpdate(AjaxRequestTarget target)
			{
				info("list2 : " + getList());
				target.addComponent(list2);
				target.addComponent(feedback);
			}
		});

		// list 3
		final WebMarkupContainer list3;
		add(list3 = new WebMarkupContainer("list3"));
		list3.setOutputMarkupId(true);

		List<String> list3items = new ArrayList<String>();
		list3items.add("Squirrels");

		list3.add(new YuiDDListView<String>("items", list3items)
		{
			@Override
			protected void populateItem(ListItem<String> item)
			{
				super.populateItem(item);
				item.add(new Label("item", item.getDefaultModelObjectAsString()));
			}

			@Override
			protected String getGroupId()
			{
				return GROUP_ID;
			}

			@Override
			protected void onAjaxUpdate(AjaxRequestTarget target)
			{
				info("list3 : " + getList());
				target.addComponent(list3);
				target.addComponent(feedback);
			}
		});

		// list 4 and 5
		final List<String> list4items = new ArrayList<String>();
		add(new YuiDDListViewPanel<String>("list4", list4items)
		{
			@Override
			protected Component newListItem(String id, ListItem<String> item)
			{
				return new Label(id, item.getModelObject());
			}

			@Override
			protected String getGroupId()
			{
				return "stationary";
			}

			@Override
			protected void onAjaxUpdate(AjaxRequestTarget target)
			{
				info("list4 : " + getList());
				target.addComponent(feedback);
			}

		});


		List<String> list5items = new ArrayList<String>();
		list5items.add("Books");
		list5items.add("Pencils");
		list5items.add("Paper");
		add(new YuiDDListViewPanel<String>("list5", list5items)
		{
			@Override
			protected Component newListItem(String id, ListItem<String> item)
			{
				return new Label(id, item.getModelObject());
			}

			@Override
			protected String getGroupId()
			{
				return "stationary";
			}

			@Override
			protected void onAjaxUpdate(AjaxRequestTarget target)
			{
				info("list5 : " + getList());
				target.addComponent(feedback);
			}

		});
	}
}
