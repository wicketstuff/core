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
package com.googlecode.wicket.kendo.ui.scheduler.resource;

import java.util.ArrayList;

/**
 * Defines a list of (specific) resources
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class ResourceList extends ArrayList<Resource>
{
	private static final long serialVersionUID = 1L;

	private final String title;
	private final String field;
	private final String name;

	private boolean multiple;

	/**
	 * Constructor
	 *
	 * @param title the title of the resource list that will serves as the label in the 'edit' form (ie: 'Resource')
	 * @param field the field (ie: 'resourceId')
	 */
	public ResourceList(String title, String field)
	{
		this(title, field, null, false);
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the resource list that will serves as the label in the 'edit' form (ie: 'Resource')
	 * @param field the field of the resource list (ie: 'resourceId')
	 * @param multiple whether an event can have multiple resources for the given field
	 */
	public ResourceList(String title, String field, boolean multiple)
	{
		this(title, field, null, multiple);
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the resource list that will serves as the label in the 'edit' form (ie: 'Resource')
	 * @param field the field (ie: 'resourceId')
	 * @param name of the resource list
	 */
	public ResourceList(String title, String field, String name)
	{
		this(title, field, name, false);
	}

	/**
	 * Constructor
	 *
	 * @param title the title of the resource list that will serves as the label in the 'edit' form (ie: 'Resource')
	 * @param field the field of the resource list (ie: 'resourceId')
	 * @param name the resource to enable the scheduler to group by this resource
	 * @param multiple whether an event can have multiple resources for the given field
	 */
	public ResourceList(String title, String field, String name, boolean multiple)
	{
		this.title = title;
		this.field = field;
		this.name = name;
		this.multiple = multiple;
	}

	/**
	 * Gets the title of the resource list that will serves as the label in the 'edit' form (ie: 'Resource')
	 *
	 * @return the title
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * Gets the field of the resource list
	 *
	 * @return the field
	 */
	public String getField()
	{
		return this.field;
	}

	/**
	 * Gets the name of the resource list
	 *
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Indicates whether an event can have multiple resources for the given field
	 *
	 * @return true or false
	 */
	public boolean isMultiple()
	{
		return this.multiple;
	}
}
