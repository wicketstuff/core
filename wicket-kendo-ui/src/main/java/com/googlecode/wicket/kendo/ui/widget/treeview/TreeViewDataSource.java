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
package com.googlecode.wicket.kendo.ui.widget.treeview;

import org.apache.wicket.Component;

import com.googlecode.wicket.kendo.ui.KendoDataSource;

/**
 * Provide the hierarchical data-source for the {@link AjaxTreeView}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class TreeViewDataSource extends KendoDataSource
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which create a JSON based data-source
	 *
	 * @param name the data-source name
	 */
	public TreeViewDataSource(String name)
	{
		this(name, TYPE);
	}

	/**
	 * Constructor
	 *
	 * @param component the hosting component (used to get the name)
	 */
	public TreeViewDataSource(Component component)
	{
		super(component);
	}

	/**
	 * Constructor
	 *
	 * @param name the data-source name
	 * @param type the response type (json, xml)
	 */
	public TreeViewDataSource(String name, String type)
	{
		super(name, type);
	}

	/**
	 * Constructor
	 *
	 * @param component the hosting component (used to get the name)
	 * @param type the response data type (json, xml)
	 */
	public TreeViewDataSource(Component component, String type)
	{
		super(component, type);
	}

	// Properties //

	@Override
	public String toScript()
	{
		return String.format("jQuery(function() { %s = new kendo.data.HierarchicalDataSource(%s); });", this.getName(), this.build());
	}
}
