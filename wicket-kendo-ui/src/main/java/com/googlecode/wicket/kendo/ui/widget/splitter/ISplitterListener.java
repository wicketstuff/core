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
package com.googlecode.wicket.kendo.ui.widget.splitter;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;

/**
 * Event listener shared by the {@link BorderLayout} widget and the {@link SplitterBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface ISplitterListener extends IClusterable
{
	/**
	 * Indicates whether the 'expand' event is enabled.<br>
	 * If true, the {@link #onExpand(AjaxRequestTarget, String)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isExpandEventEnabled();

	/**
	 * Indicates whether the 'collapse' event is enabled.<br>
	 * If true, the {@link #onCollapse(AjaxRequestTarget, String)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isCollapseEventEnabled();

	/**
	 * Triggered when a panel expands
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param paneId the panel id (html-id)
	 */
	void onExpand(AjaxRequestTarget target, String paneId);

	/**
	 * Triggered when a panel collapses
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param paneId the panel id (html-id)
	 */
	void onCollapse(AjaxRequestTarget target, String paneId);
}
