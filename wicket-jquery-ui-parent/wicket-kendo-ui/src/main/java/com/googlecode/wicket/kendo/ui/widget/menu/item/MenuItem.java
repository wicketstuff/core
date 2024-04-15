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
package com.googlecode.wicket.kendo.ui.widget.menu.item;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.kendo.ui.KendoIcon;

/**
 * Provides a standard menu-item that supports sub-menus
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.15.0
 */
public class MenuItem extends AbstractMenuItem
{
	private static final long serialVersionUID = 1L;

	/** children items */
	private final List<IMenuItem> items;

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 */
	public MenuItem(String title)
	{
		this(Model.of(title), KendoIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 */
	public MenuItem(String title, String icon)
	{
		this(Model.of(title), icon);
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 */
	public MenuItem(IModel<String> title)
	{
		this(title, KendoIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 */
	public MenuItem(IModel<String> title, String icon)
	{
		super(title, icon);

		this.items = Generics.newArrayList();
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 * @param items the sub-menu items
	 *
	 */
	public MenuItem(String title, List<IMenuItem> items)
	{
		this(Model.of(title), KendoIcon.NONE, items);
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 * @param items the sub-menu items
	 */
	public MenuItem(String title, String icon, List<IMenuItem> items)
	{
		this(Model.of(title), icon, items);
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 * @param items the sub-menu items
	 */
	public MenuItem(IModel<String> title, List<IMenuItem> items)
	{
		this(title, KendoIcon.NONE, items);
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 * @param items the sub-menu items
	 */
	public MenuItem(IModel<String> title, String icon, List<IMenuItem> items)
	{
		super(title, icon);

		this.items = items;
	}

	// Properties //
	@Override
	public List<IMenuItem> getItems()
	{
		return this.items;
	}

	// Methods //
	/**
	 * Adds an menu-item as child of this menu-item
	 *
	 * @param item the {@link IMenuItem}
	 * @return true (as specified by {@link Collection#add(Object)})
	 */
	public boolean addItem(IMenuItem item)
	{
		return this.items.add(item);
	}

	// Events //
	@Override
	public void onClick(AjaxRequestTarget target)
	{
		// noop
	}
}
