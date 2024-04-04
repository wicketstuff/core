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
import java.util.List;

import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.lang.Generics;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

/**
 * Provides a {@link ListModel} of {@link ResourceList}{@code s} (a {@code List} of {@code List})
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class ResourceListModel extends ListModel<ResourceList>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public ResourceListModel()
	{
		super(new ArrayList<ResourceList>());
	}

	/**
	 * Constructor
	 * 
	 * @param list the {@link ResourceList}
	 */
	public ResourceListModel(ResourceList list)
	{
		this();
		this.add(list);
	}

	/**
	 * Clears the {@code ResourceList} {@code List}
	 */
	public void clear()
	{
		this.getObject().clear();
	}

	/**
	 * Adds a {@code ResourceList} to the model object
	 *
	 * @param list the {@link ResourceList}
	 */
	public void add(ResourceList list)
	{
		this.getObject().add(list);
	}

	/**
	 * Get all field names contained in this collection of ResourceList
	 *
	 * @return all field names
	 */
	public List<String> getFields()
	{
		List<String> fields = Generics.newArrayList();

		for (ResourceList list : this.getObject())
		{
			fields.add(list.getField());
		}

		return fields;
	}

	/**
	 * Get all group names contained in this collection of ResourceList
	 *
	 * @return all group names
	 */
	public List<String> getGroups()
	{
		List<String> groups = Generics.newArrayList();

		for (ResourceList list : this.getObject())
		{
			String group = list.getGroup();

			if (group != null)
			{
				groups.add(group);
			}
		}

		return groups;
	}

	@Override
	public String toString()
	{
		final JSONArray array = new JSONArray();

		for (ResourceList list : this.getObject())
		{
			final JSONObject object = new JSONObject();
			array.put(object);

			object.put("field", list.getField());

			if (list.getGroup() != null)
			{
				object.put("name", list.getGroup());
			}

			object.put("title", list.getTitle());
			object.put("multiple", list.isMultiple());

			final JSONArray resources = new JSONArray();
			object.put("dataSource", resources);

			for (Resource resource : list)
			{
				resources.put(Resource.toJSONObject(resource));
			}
		}

		return array.toString();
	}
}
