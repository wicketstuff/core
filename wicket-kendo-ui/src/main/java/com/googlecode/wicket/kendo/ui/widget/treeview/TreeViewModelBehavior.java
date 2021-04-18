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

import java.util.List;

import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.util.lang.Args;

import com.github.openjson.JSONArray;
import com.googlecode.wicket.jquery.core.behavior.AjaxCallbackBehavior;

/**
 * Provides the behavior that loads {@link TreeNode}{@code s}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class TreeViewModelBehavior extends AjaxCallbackBehavior
{
	private static final long serialVersionUID = 1L;

	private final TreeViewModel model;
	private final TreeNodeFactory factory;

	/**
	 * Constructor
	 *
	 * @param model the {@link TreeViewModel}
	 * @param factory the {@link TreeNodeFactory}
	 */
	public TreeViewModelBehavior(final TreeViewModel model, TreeNodeFactory factory)
	{
		this.model = model;
		this.factory = Args.notNull(factory, "factory");
	}

	// Properties //

	/**
	 * Gets the {@link TreeNodeFactory}
	 * 
	 * @return the {@code TreeNodeFactory}
	 */
	public TreeNodeFactory getFactory()
	{
		return this.factory;
	}

	@Override
	protected String getResponse(IRequestParameters parameters)
	{
		int nodeId = parameters.getParameterValue(TreeNodeFactory.ID_FIELD).toInt(TreeNode.ROOT);

		final JSONArray payload = new JSONArray();
		
		if (this.model != null)
		{
			this.model.setNodeId(nodeId);
			List<? extends TreeNode<?>> objects = this.model.getObject(); // calls load()
			
			for (int index = 0; index < objects.size(); index++)
			{
				TreeNode<?> object = objects.get(index);

				payload.put(this.factory.toJson(index, object));
			}
		}

		return payload.toString();
	}
}
