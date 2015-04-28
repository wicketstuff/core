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

import com.googlecode.wicket.jquery.core.Options;

/**
 * INTERNAL USE<br/>
 * {@link ListModel} of {@link ResourceList} (a {@code List} of {@code List})
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
		List<String> fields = new ArrayList<String>();

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
		List<String> groups = new ArrayList<String>();

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
		StringBuilder builder = new StringBuilder("[ ");

		int i = 0;
		for (ResourceList list : this.getObject())
		{
			if (i++ > 0)
			{
				builder.append(", ");
			}

			builder.append("{ ");
			Options.append(builder, "field", list.getField());
			builder.append(", ");

			if (list.getGroup() != null)
			{
				Options.append(builder, "name", list.getGroup());
				builder.append(", ");
			}

			Options.append(builder, "title", list.getTitle());
			builder.append(", ");
			Options.append(builder, "multiple", list.isMultiple());
			builder.append(", ");
			builder.append("dataSource: [ ");

			int j = 0;
			for (Resource resource : list)
			{
				if (j++ > 0)
				{
					builder.append(", ");
				}

				builder.append(resource.toString());
			}

			builder.append(" ]");
			builder.append(" }");
		}

		return builder.append(" ]").toString();
	}
}
