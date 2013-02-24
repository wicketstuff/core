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

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;

/**
 * Base class for {@link Menu} item
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.4.2
 * @since 6.2.2
 */
public abstract class AbstractMenuItem implements IMenuItem
{
	private static final long serialVersionUID = 1L;

	private IModel<String> title;
	private String iconCss;
	private boolean enabled = true;

	/**
	 * Constructor
	 * @param title {@link IModel} that represent the title of the menu-item
	 */
	public AbstractMenuItem(IModel<String> title)
	{
		this(title, "");
	}

	/**
	 * Constructor
	 * @param title {@link IModel} that represent the title of the menu-item
	 * @param iconCss the icon css class (ie: my-ui-icon-custom)
	 */
	public AbstractMenuItem(IModel<String> title, String iconCss)
	{
		this.title = title;
		this.iconCss = iconCss;
	}

	@Override
	public String getId()
	{
		return "menuitem-" + this.hashCode();
	}

	@Override
	public IModel<String> getTitle()
	{
		return this.title;
	}

	/**
	 * Sets the menu-item title
	 * @param title the menu-item title
	 */
	public void setTitle(IModel<String> title)
	{
		this.title = title;
	}

	@Override
	public String getIconClass()
	{
		return this.iconCss;
	}

	/**
	 * Sets the icon css class being displayed in the {@link Menu} (ie: my-ui-icon-custom)
	 * @param iconClass the icon css class
	 */
	public void setIconClass(String iconClass)
	{
		this.iconCss = iconClass;
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	/**
	 * Sets whether the menu-item is enabled
	 * @param enabled true or false
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public List<IMenuItem> getItems()
	{
		return Collections.emptyList();
	}
}
