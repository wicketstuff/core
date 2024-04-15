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
package com.googlecode.wicket.kendo.ui.utils;

import java.util.List;

import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.kendo.ui.widget.treeview.TreeNode;

public class TreeNodeUtils
{
	/**
	 * Utility class
	 */
	private TreeNodeUtils()
	{
		// noop
	}

	/**
	 * Retrieves a {@link TreeNode} with a {@code List}
	 * 
	 * @param id the node-id
	 * @param nodes the {@code List} of nodes
	 * @return the node with the specified id or {@code null} is not found
	 */
	public static TreeNode<?> get(int id, List<TreeNode<?>> nodes)
	{
		for (TreeNode<?> node : nodes)
		{
			if (node.getId() == id)
			{
				return node;
			}
		}

		return null;
	}

	/**
	 * Retrieves children {@link TreeNode}{@code s} for a specified parent node
	 * 
	 * @param parentId the node parent id
	 * @param nodes the {@code List} of nodes
	 * @return the children nodes
	 */
	public static List<? extends TreeNode<?>> getChildren(int parentId, List<TreeNode<?>> nodes)
	{
		List<TreeNode<?>> list = Generics.newArrayList();

		for (TreeNode<?> node : nodes)
		{
			if (node.getParentId() == parentId)
			{
				list.add(node);
			}
		}

		return list;
	}
}
