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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Provides a standard menu-item that supports sub-menus
 *
 * Adapted for Superfish by
 * @author Ludger Kluitmann - JavaLuigi
 *
 * @author Sebastien Briquet - sebfz1
 */
public class SfMenuItem extends AbstractSfMenuItem
{
	private static final long serialVersionUID = 1L;

	/** children items */
	private final List<ISfMenuItem> items;

	/**
	 * Constructor
	 * @param title the title of the menu-item
	 */
	public SfMenuItem(String title, Class<? extends WebPage> pageClass)
	{
		this(Model.of(title), pageClass);
	}

	/**
	 * Constructor
	 * @param title IModel that represent the title of the menu-item
	 */
	public SfMenuItem(IModel<String> title, Class<? extends WebPage> pageClass)
	{
		super(title, pageClass);

		this.items = new ArrayList<ISfMenuItem>();
	}

	/**
	 * Constructor
	 * @param title the title of the menu-item
	 * @param items the sub-menu items
	 *
	 */
	public SfMenuItem(String title, Class<? extends WebPage> pageClass, List<ISfMenuItem> items)
	{
		this(Model.of(title), pageClass, items);
	}


	/**
	 * Constructor
	 * @param title IModel that represent the title of the menu-item
	 * @param items the sub-menu items
	 */
	public SfMenuItem(IModel<String> title, Class<? extends WebPage> pageClass, List<ISfMenuItem> items)
	{
		super(title, pageClass);

		this.items = items;
	}


	// Properties //
	@Override
	public List<ISfMenuItem> getItems()
	{
		return this.items;
	}

	// Methods //
	public boolean addItem(ISfMenuItem item)
	{
		return this.items.add(item);
	}

}
