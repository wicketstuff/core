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

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryPanel;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides the jQuery menu based on a {@link JQueryPanel}, adapted for Superfish
 *
 * @author Ludger Kluitmann - JavaLuigi
 * @author Sebastien Briquet - sebfz1
 * @since 6.12.0
 */
public class SfMenu extends JQueryPanel
{
	private static final long serialVersionUID = 1L;

	private final List<ISfMenuItem> items; // first level
	private final boolean isVertical;
	private WebMarkupContainer root;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the menu-items
	 */
	public SfMenu(String id, List<ISfMenuItem> items)
	{
		this(id, items, false);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the menu-items
	 * @param isVertical Vertical Superfish Menu if true
	 */
	public SfMenu(String id, List<ISfMenuItem> items, Boolean isVertical)
	{
		super(id);

		this.items = Args.notNull(items, "items");
		this.isVertical = isVertical;

		this.initialize();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the menu-items
	 * @param options the {@link Options}
	 */
	public SfMenu(String id, List<ISfMenuItem> items, Options options)
	{
		this(id, items, options, false);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param items the menu-items
	 * @param options the {@link Options}
	 * @param isVertical Vertical Superfish Menu if true
	 */
	public SfMenu(String id, List<ISfMenuItem> items, Options options, Boolean isVertical)
	{
		super(id, options);

		this.items = Args.notNull(items, "items");
		this.isVertical = isVertical;

		this.initialize();
	}

	/**
	 * Initialization
	 */
	private void initialize()
	{
		this.root = new WebMarkupContainer("root");
		this.root.add(new ListFragment("list", this.items));

		if (this.isVertical)
		{
			this.root.add(new AttributeModifier("class", "sf-menu sf-vertical"));
		}

		this.add(this.root);
	}

	/**
	 * Gets the menu-item list
	 *
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
		return new SfMenuBehavior(selector, this.options, this.isVertical);
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
			this.setVisible(!items.isEmpty());
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

					if (menuItem.isEnabled())
					{
						item.add(new ItemFragment("item", menuItem));
						item.add(new MenuFragment("menu", menuItem.getItems()));
					}
					else
					{
						item.add(new DisabledItemFragment("item", menuItem));
						item.add(new EmptyPanel("menu"));
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

		public ItemFragment(String id, final ISfMenuItem item)
		{
			super(id, "item-link-fragment", SfMenu.this);

			Link<?> itemLink = new Link<Void>("item-link") {

				private static final long serialVersionUID = 1L;

				@Override
				protected CharSequence getURL()
				{
					if (item.getPageClass() != null)
					{
						return urlFor(item.getPageClass(), item.getPageParameters());
					}

					if (!Strings.isEmpty(item.getPageUrl()))
					{
						return item.getPageUrl();
					}

					return "javascript:;";
				}

				@Override
				public void onClick()
				{
					// No operation. Only to satisfy the interface.
				}
			};

			if(item.isOpenInNewWindow())
			{
				itemLink.add(new AttributeModifier("target", "_blank"));
			}
			itemLink.add(new Label("title", item.getTitle()));
			this.add(itemLink);
		}
	}

	/**
	 * Represents a {@link Fragment} of a disabled menu-item
	 */
	private class DisabledItemFragment extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public DisabledItemFragment(String id, final ISfMenuItem item)
		{
			super(id, "item-disable-fragment", SfMenu.this);

			Label label = new Label("title", item.getTitle());
			label.add(new AttributeModifier("class", "disabled"));

			this.add(label);
		}
	}
}
