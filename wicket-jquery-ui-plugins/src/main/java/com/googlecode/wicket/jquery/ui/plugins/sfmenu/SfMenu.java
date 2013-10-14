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
package com.googlecode.wicket.jquery.ui.plugins.sfmenu;

import java.awt.MenuItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Fragment;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides the jQuery menu based on a {@link JQueryPanel}
 *
 * Adapted for Superfish by
 * @author Ludger Kluitmann - JavaLuigi
 *
 * @author Sebastien Briquet - sebfz1
 */
public class SfMenu extends JQueryPanel
{
	private static final long serialVersionUID = 1L;

	private final List<ISfMenuItem> items; //first level
	private WebMarkupContainer root;
	private Boolean verticalSfMenu = false;

	/**
	 * Keep a reference to the {@link MenuItem}<code>s</code> hash
	 */
	private Map<String, ISfMenuItem> map = new HashMap<String, ISfMenuItem>();

	/**
	 * Constructor
	 * @param id the markup id
	 * @param items the menu-items
	 */
	public SfMenu(String id, List<ISfMenuItem> items)
	{
		super(id);

		this.items = items;
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param items the menu-items
	 * @param verticalSfMenu Vertical Superfish Menu if true
	 */
	public SfMenu(String id, List<ISfMenuItem> items, Boolean verticalSfMenu)
	{
		super(id);

		this.items = items;
		this.verticalSfMenu = verticalSfMenu;
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param items the menu-items
	 * @param options {@link Options}
	 */
	public SfMenu(String id, List<ISfMenuItem> items, Options options)
	{
		super(id, options);

		this.items = items;
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param items the menu-items
	 * @param options {@link Options}
	 * @param verticalSfMenu Vertical Superfish Menu if true
	 */
	public SfMenu(String id, List<ISfMenuItem> items, Options options, Boolean verticalSfMenu)
	{
		super(id, options);

		this.items = items;
		this.verticalSfMenu = verticalSfMenu;
		this.init();

	}

	/**
	 * Initialization
	 */
	private void init()
	{
		this.root = new WebMarkupContainer("root");
		if(verticalSfMenu)
		{
			this.root.add(new AttributeModifier("class", "sf-menu sf-vertical"));
		}
		this.root.add(new ListFragment("list", this.items));

		this.add(this.root);
	}


	/**
	 * Gets the menu-item list
	 * @return the menu-item {@link List}
	 */
	public List<ISfMenuItem> getItemList()
	{
		return this.items;

	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this, JQueryWidget.getSelector(this.root)));
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SfMenuBehavior(selector, this.options, verticalSfMenu) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Map<String, ISfMenuItem> getMenuItemMap()
			{
				return SfMenu.this.map;
			}
		};
	}


	// Fragments //
	/**
	 * Represents a menu {@link Fragment}. Could be either the root or a sub-menu
	 */
	private class MenuFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public MenuFragment(String id, List<ISfMenuItem> items)
		{
			super(id, "menu-fragment", SfMenu.this);

			this.add(new ListFragment("list", items));
			this.setVisible(items.size() > 0);
		}
	}

	/**
	 * Represents a {@link Fragment} of a list of menu-items
	 */
	private class ListFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public ListFragment(String id, List<ISfMenuItem> items)
		{
			super(id, "list-fragment", SfMenu.this);

			this.add(new ListView<ISfMenuItem>("items", items) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<ISfMenuItem> item)
				{
					ISfMenuItem menuItem = item.getModelObject();

					if(menuItem.isEnabled())
					{
						item.add(new ItemLinkFragment("item", menuItem));
						item.add(new MenuFragment("menu", menuItem.getItems()));
					}
					else
					{
						item.add(new ItemDisableFragment("item", menuItem));
						item.add(new EmptyPanel("menu"));
					}
				}
			});
		}
	}

	/**
	 * Represents a {@link Fragment} of a menu-item
	 */
	private class ItemLinkFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public ItemLinkFragment(String id, final ISfMenuItem item)
		{
			super(id, "item-link-fragment", SfMenu.this);

			Link<Object> itemLink = new Link<Object>("item-link") {

				private static final long serialVersionUID = 1L;

				@Override
				protected CharSequence getURL()
				{
					if(item.getPageClass() != null)
					{
						return urlFor(item.getPageClass(), getPage().getPageParameters());
					}
					return "javascript:;";
				}

				// No operation. Only to satisfy the interface.
				@Override
				public void onClick()
				{
				}
			};
			itemLink.add(new Label("title", item.getTitle()));
			add(itemLink);
		}
	}

	private class ItemDisableFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public ItemDisableFragment(String id, final ISfMenuItem item)
		{
			super(id,"item-disable-fragment", SfMenu.this);
			add(new Label("title", item.getTitle()).add(new AttributeModifier("class", "disabled")));
		}

	}
}
