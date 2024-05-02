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

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Model of {@link TreeNode}{@code s} for the {@link AjaxTreeView}
 * 
 * @see TreeNodeFactory
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class TreeViewModel extends LoadableDetachableModel<List<? extends TreeNode<?>>>
{
	private static final long serialVersionUID = 1L;

	/** root node */
	private int nodeId = TreeNode.ROOT;

	/**
	 * Constructor
	 */
	public TreeViewModel()
	{
		// noop
	}

	@Override
	protected final List<? extends TreeNode<?>> load()
	{
		return this.load(this.nodeId);
	}

	/**
	 * Loads children nodes of specified {@code nodeId}
	 * 
	 * @param nodeId the (parent) node-id
	 * @return the list of children {@code TreeNode}
	 */
	protected abstract List<? extends TreeNode<?>> load(int nodeId);

	/**
	 * Sets the node-id
	 *
	 * @param nodeId the node-id
	 */
	public void setNodeId(int nodeId)
	{
		this.nodeId = nodeId;
	}
}
