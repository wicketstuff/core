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
package com.googlecode.wicket.jquery.ui.widget.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides the jQuery menu based on a {@link JQueryPanel}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.4.2
 * @since 1.6.2
 */
public class Menu extends JQueryPanel implements IMenuListener
{
	private static final long serialVersionUID = 1L;

	private final List<IMenuItem> items; // first level
	private WebMarkupContainer root;

	/** Keep a reference to the {@link MenuItem}{@code s} hash */
	private Map<String, IMenuItem> map = new HashMap<String, IMenuItem>();

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public Menu(String id)
	{
		this(id, new ArrayList<IMenuItem>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the list of {@link IMenuItem}
	 */
	public Menu(String id, List<IMenuItem> items)
	{
		super(id);

		this.items = Args.notNull(items, "items");
		this.initialize();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public Menu(String id, Options options)
	{
		this(id, new ArrayList<IMenuItem>(), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the list of {@link IMenuItem}
	 * @param options {@link Options}
	 */
	public Menu(String id, List<IMenuItem> items, Options options)
	{
		super(id, options);

		this.items = Args.notNull(items, "items");
		this.initialize();
	}

	/**
	 * Initialization
	 */
	private void initialize()
	{
		this.root = new WebMarkupContainer("root");
		this.root.add(new ListFragment("list", this.items));

		this.add(this.root);
	}

	/**
	 * Gets the menu-item list
	 *
	 * @return the list of {@link IMenuItem}
	 */
	public List<IMenuItem> getItemList()
	{
		return this.items;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this, this.root));
	}

	@Override
	public void onClick(AjaxRequestTarget target, IMenuItem item)
	{
		// noop
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new MenuBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Map<String, IMenuItem> getMenuItemMap()
			{
				return Menu.this.map;
			}

			// Events //
			@Override
			public void onClick(AjaxRequestTarget target, IMenuItem item)
			{
				Menu.this.onClick(target, item);
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

		public MenuFragment(String id, List<IMenuItem> items)
		{
			super(id, "menu-fragment", Menu.this);

			this.add(new ListFragment("list", items));
			this.setVisible(!items.isEmpty());
		}
	}

	/**
	 * Represents a {@link Fragment} of a list of menu-items
	 */
	private class ListFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public ListFragment(String id, List<IMenuItem> items)
		{
			super(id, "list-fragment", Menu.this);

			this.add(new ListView<IMenuItem>("items", items) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<IMenuItem> item)
				{
					IMenuItem menuItem = item.getModelObject();
					String menuItemId = menuItem.getId();

					Menu.this.map.put(menuItemId, menuItem);
					item.add(AttributeModifier.replace("id", menuItemId));

					item.add(new ItemFragment("item", menuItem));

					if (menuItem.isEnabled())
					{
						item.add(new MenuFragment("menu", menuItem.getItems()));
					}
					else
					{
						item.add(new EmptyPanel("menu"));
						item.add(AttributeModifier.append("class", Model.of("ui-state-disabled")));
					}
				}
			});
		}
	}

	/**
	 * Represents a {@link Fragment} of a menu-item
	 */
	private class ItemFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public ItemFragment(String id, IMenuItem item)
		{
			super(id, "item-fragment", Menu.this);

			this.add(new EmptyPanel("icon").add(AttributeModifier.append("class", new PropertyModel<String>(item, "icon"))));
			this.add(new Label("title", item.getTitle()));
		}
	}
}
