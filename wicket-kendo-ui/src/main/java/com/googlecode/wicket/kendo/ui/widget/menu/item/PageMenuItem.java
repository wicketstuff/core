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

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.wicket.kendo.ui.KendoIcon;

/**
 * Provides a menu-item that redirect to a {@link Page}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.15.0
 */
public class PageMenuItem extends UrlMenuItem
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 * @param pageClass the {@link Page} class
	 */
	public PageMenuItem(String title, Class<? extends Page> pageClass)
	{
		this(Model.of(title), pageClass);
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 * @param pageClass the {@link Page} class
	 */
	public PageMenuItem(String title, String icon, Class<? extends Page> pageClass)
	{
		this(Model.of(title), icon, pageClass);
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 * @param pageClass the {@link Page} class
	 * @param parameters the {@link PageParameters}
	 */
	public PageMenuItem(String title, Class<? extends Page> pageClass, PageParameters parameters)
	{
		this(Model.of(title), pageClass, parameters);
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 * @param pageClass the {@link Page} class
	 * @param parameters the {@link PageParameters}
	 */
	public PageMenuItem(String title, String icon, Class<? extends Page> pageClass, PageParameters parameters)
	{
		this(Model.of(title), icon, pageClass, parameters);
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 * @param pageClass the {@link Page} class
	 */
	public PageMenuItem(IModel<String> title, Class<? extends Page> pageClass)
	{
		this(title, KendoIcon.NONE, pageClass, new PageParameters());
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 * @param pageClass the {@link Page} class
	 */
	public PageMenuItem(IModel<String> title, String icon, Class<? extends Page> pageClass)
	{
		this(title, icon, pageClass, new PageParameters());
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 * @param pageClass the {@link Page} class
	 * @param parameters the {@link PageParameters}
	 */
	public PageMenuItem(IModel<String> title, Class<? extends Page> pageClass, PageParameters parameters)
	{
		this(title, KendoIcon.NONE, pageClass, parameters);
	}

	/**
	 * Constructor
	 *
	 * @param title IModel that represent the title of the menu-item
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 * @param pageClass the {@link Page} class
	 * @param parameters the {@link PageParameters}
	 */
	public PageMenuItem(IModel<String> title, String icon, Class<? extends Page> pageClass, PageParameters parameters)
	{
		super(title, icon, RequestCycle.get().urlFor(pageClass, parameters));
	}
}
