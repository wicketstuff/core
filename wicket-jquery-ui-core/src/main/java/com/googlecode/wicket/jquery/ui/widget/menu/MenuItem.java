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
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * TODO javadoc
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.4.2
 * @since 6.2.2
 */
public class MenuItem implements IClusterable
{
	private static final long serialVersionUID = 1L;
	private final String title;
	private List<MenuItem> items;

	/**
	 * Constructor
	 *
	 * @param title IModel used to represent the title of the tab.
	 */
	public MenuItem(String title)
	{
		this.title = title;
		this.items = new ArrayList<MenuItem>();
	}

	public MenuItem(String title, List<MenuItem> items)
	{
		this.title = title;
		this.items = items;
	}

	public String getId()
	{
		return "menuitem-" + this.hashCode();
	}

	public String getTitle()
	{
		return this.title;
	}

	public List<MenuItem> getItems()
	{
		return this.items;
	}

	public boolean addItem(MenuItem item)
	{
		return this.items.add(item);
	}

	protected void onClick(AjaxRequestTarget target)
	{

	}
}
