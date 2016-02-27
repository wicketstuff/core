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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;

/**
 * Event listener shared by the {@link AjaxTreeView} widget and the {@link AjaxTreeViewBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface ITreeViewListener extends IClusterable
{
	/**
	 * Indicates whether the 'expand' event is enabled.<br />
	 * If true, the {@link #onExpand(AjaxRequestTarget, int)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isExpandEventEnabled();

	/**
	 * Indicates whether the 'select' event is enabled.<br />
	 * If true, the {@link #onSelect(AjaxRequestTarget, int, String)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isSelectEventEnabled();

	/**
	 * Triggered when a node is expanding
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param nodeId the node-id
	 */
	void onExpand(AjaxRequestTarget target, int nodeId);

	/**
	 * Triggered when a node is selected
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param nodeId the node-id
	 * @param nodePath the node path as array, ie [1,2,3]
	 */
	void onSelect(AjaxRequestTarget target, int nodeId, String nodePath);
}
